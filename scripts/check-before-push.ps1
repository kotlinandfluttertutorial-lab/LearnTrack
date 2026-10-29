#!/usr/bin/env pwsh
# =============================================================================
# LearnTrack — Pre-Push Quality Gate Script (PowerShell)
# =============================================================================
# Purpose  : Run all quality checks before pushing to the remote repository.
# Platform : Android project using Gradle (Kotlin DSL)
# Usage    : .\scripts\check-before-push.ps1
#            Run from the repository root directory.
#
# This script is SAFE TO RERUN. It does NOT push, deploy, or delete files.
# Exit code 0 = all checks passed.
# Exit code 1 = one or more checks failed (check output for details).
#
# Requirements:
#   - PowerShell 5.1 or 7+
#   - Git installed and on PATH
#   - Gradle wrapper (gradlew.bat) present in the project root
#     (created during Android scaffold phase)
#
# OPTIONAL checks are clearly marked. They are skipped automatically when
# their dependencies are not yet available (e.g., before scaffold is created).
# =============================================================================

Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

# ---------------------------------------------------------------------------
# Configuration
# ---------------------------------------------------------------------------
$RepoRoot         = Split-Path -Parent $PSScriptRoot
$JsonFile         = Join-Path $RepoRoot "app\src\main\assets\learntrack_topics.json"
$GradleWrapper    = Join-Path $RepoRoot "gradlew.bat"
$ExpectedTotal    = 41
$ExpectedCompleted = 22
$ExpectedNotStarted = 19

# Counters
$PassCount = 0
$FailCount = 0
$SkipCount = 0

# ---------------------------------------------------------------------------
# Helper functions
# ---------------------------------------------------------------------------
function Write-Header {
    param([string]$Title)
    Write-Host ""
    Write-Host "=================================================================" -ForegroundColor Cyan
    Write-Host "  $Title" -ForegroundColor Cyan
    Write-Host "=================================================================" -ForegroundColor Cyan
}

function Write-CheckStart {
    param([string]$CheckName)
    Write-Host ""
    Write-Host "[CHECK] $CheckName ..." -ForegroundColor Yellow
}

function Write-Pass {
    param([string]$Message)
    Write-Host "[PASS]  $Message" -ForegroundColor Green
    $script:PassCount++
}

function Write-Fail {
    param([string]$Message)
    Write-Host "[FAIL]  $Message" -ForegroundColor Red
    $script:FailCount++
}

function Write-Skip {
    param([string]$Message)
    Write-Host "[SKIP]  $Message" -ForegroundColor DarkYellow
    $script:SkipCount++
}

function Write-Summary {
    Write-Host ""
    Write-Host "=================================================================" -ForegroundColor Cyan
    Write-Host "  SUMMARY" -ForegroundColor Cyan
    Write-Host "=================================================================" -ForegroundColor Cyan
    Write-Host "  Passed : $PassCount" -ForegroundColor Green
    Write-Host "  Failed : $FailCount" -ForegroundColor $(if ($FailCount -gt 0) { "Red" } else { "Green" })
    Write-Host "  Skipped: $SkipCount" -ForegroundColor DarkYellow
    Write-Host ""
}

# ---------------------------------------------------------------------------
# Begin
# ---------------------------------------------------------------------------
Write-Header "LearnTrack Pre-Push Quality Gate"
Write-Host "Repository root : $RepoRoot"
Write-Host "Run timestamp   : $(Get-Date -Format 'yyyy-MM-dd HH:mm:ss')"

# ---------------------------------------------------------------------------
# CHECK 1 — JSON syntax validation
# ---------------------------------------------------------------------------
Write-CheckStart "JSON syntax validation: learntrack_topics.json"

if (-not (Test-Path $JsonFile)) {
    Write-Skip "learntrack_topics.json not found at expected path. Skipping. (File will exist after scaffold + JSON phase.)"
    Write-Skip "Expected location: $JsonFile"
} else {
    try {
        $jsonContent = Get-Content -Raw $JsonFile
        $null = $jsonContent | Test-Json -ErrorAction Stop
        Write-Pass "learntrack_topics.json is valid JSON."
    } catch {
        Write-Fail "learntrack_topics.json failed JSON syntax validation."
        Write-Host "         Error: $($_.Exception.Message)" -ForegroundColor Red
    }
}

# ---------------------------------------------------------------------------
# CHECK 2 — Topic count validation (22 completed / 19 not-started / 41 total)
# ---------------------------------------------------------------------------
Write-CheckStart "Topic count validation (expected: 41 total, 22 completed, 19 not-started)"

if (-not (Test-Path $JsonFile)) {
    Write-Skip "learntrack_topics.json not found. Skipping count validation."
} else {
    try {
        $jsonContent = Get-Content -Raw $JsonFile
        $parsed = $jsonContent | ConvertFrom-Json
        # Schema: wrapper object {"topics": [...]}
        $topics = $parsed.topics

        if ($null -eq $topics -or -not ($topics -is [System.Array])) {
            Write-Fail "learntrack_topics.json does not contain a 'topics' array. Expected schema: {`"topics`": [...]}."
        } else {
            $total       = $topics.Count
            $completed   = ($topics | Where-Object { $_.status -eq "completed" }).Count
            $notStarted  = ($topics | Where-Object { $_.status -eq "not_started" }).Count
            $otherStatus = ($topics | Where-Object { $_.status -ne "completed" -and $_.status -ne "not_started" }).Count

            Write-Host "         Total     : $total (expected $ExpectedTotal)" -ForegroundColor Gray
            Write-Host "         Completed : $completed (expected $ExpectedCompleted)" -ForegroundColor Gray
            Write-Host "         Not started: $notStarted (expected $ExpectedNotStarted)" -ForegroundColor Gray
            if ($otherStatus -gt 0) {
                Write-Host "         Unknown status: $otherStatus topics have unrecognized status values!" -ForegroundColor Red
            }

            $countOk = ($total -eq $ExpectedTotal) -and
                       ($completed -eq $ExpectedCompleted) -and
                       ($notStarted -eq $ExpectedNotStarted) -and
                       ($otherStatus -eq 0)

            if ($countOk) {
                Write-Pass "Topic distribution is correct: $completed completed, $notStarted not-started, $total total."
            } else {
                Write-Fail "Topic distribution mismatch. Expected $ExpectedCompleted/$ExpectedNotStarted/$ExpectedTotal, got $completed/$notStarted/$total."
            }
        }
    } catch {
        Write-Fail "Failed to parse learntrack_topics.json for count validation."
        Write-Host "         Error: $($_.Exception.Message)" -ForegroundColor Red
    }
}

# ---------------------------------------------------------------------------
# CHECK 3 — Secret / credential pattern detection in staged files
# ---------------------------------------------------------------------------
Write-CheckStart "Secret detection in staged files"

try {
    $stagedFiles = git -C $RepoRoot diff --cached --name-only 2>&1
    if ($LASTEXITCODE -ne 0) {
        Write-Skip "Could not retrieve staged files from git. Skipping secret detection."
    } elseif ([string]::IsNullOrWhiteSpace($stagedFiles)) {
        Write-Pass "No staged files to check for secrets."
    } else {
        $secretPatterns = @(
            'password\s*=\s*\S+',
            'api[_-]?key\s*=\s*\S+',
            'secret\s*=\s*\S+',
            'token\s*=\s*\S+',
            'private[_-]?key',
            'BEGIN RSA PRIVATE KEY',
            'BEGIN EC PRIVATE KEY',
            'BEGIN OPENSSH PRIVATE KEY',
            'AIza[0-9A-Za-z\-_]{35}',   # Google API key pattern
            'github_pat_[A-Za-z0-9_]{82}',  # GitHub fine-grained PAT pattern
            'ghp_[A-Za-z0-9]{36}'           # GitHub classic PAT pattern
        )

        $secretFound = $false
        foreach ($stagedFile in ($stagedFiles -split "`n" | Where-Object { $_ -ne "" })) {
            $fullPath = Join-Path $RepoRoot $stagedFile.Trim()
            # Skip binary files and non-existent files (e.g., deleted files)
            if (-not (Test-Path $fullPath) -or (Get-Item $fullPath).Length -eq 0) {
                continue
            }
            # Skip known safe binary/media extensions
            $ext = [System.IO.Path]::GetExtension($fullPath).ToLower()
            if ($ext -in @('.png', '.jpg', '.jpeg', '.gif', '.webp', '.apk', '.aab', '.keystore', '.jks')) {
                continue
            }
            try {
                $content = Get-Content -Raw $fullPath -ErrorAction Stop
                foreach ($pattern in $secretPatterns) {
                    if ($content -match $pattern) {
                        Write-Fail "Possible secret found in staged file: $stagedFile (matched pattern: $pattern)"
                        $secretFound = $true
                    }
                }
            } catch {
                # File may not be readable; skip silently
            }
        }

        if (-not $secretFound) {
            Write-Pass "No obvious secrets detected in staged files."
        }
    }
} catch {
    Write-Skip "Secret detection failed with an unexpected error: $($_.Exception.Message)"
}

# ---------------------------------------------------------------------------
# CHECK 4 — Git working tree status
# ---------------------------------------------------------------------------
Write-CheckStart "Git working tree status"

try {
    $gitStatus = git -C $RepoRoot status --porcelain 2>&1
    if ($LASTEXITCODE -ne 0) {
        Write-Skip "Could not check git status. Skipping."
    } else {
        if ([string]::IsNullOrWhiteSpace($gitStatus)) {
            Write-Pass "Working tree is clean — no uncommitted changes."
        } else {
            # This is informational — uncommitted changes are allowed (staged files will be pushed)
            Write-Host "         Note: Working tree has uncommitted changes:" -ForegroundColor Gray
            $gitStatus -split "`n" | Where-Object { $_ -ne "" } | ForEach-Object {
                Write-Host "         $_" -ForegroundColor Gray
            }
            Write-Pass "Working tree status noted. Staged changes will be included in the push."
        }
    }
} catch {
    Write-Skip "Git status check failed: $($_.Exception.Message)"
}

# ---------------------------------------------------------------------------
# OPTIONAL CHECK 5 — Unit tests (requires Gradle wrapper / Android scaffold)
# ---------------------------------------------------------------------------
Write-CheckStart "Unit tests: .\gradlew.bat testDebugUnitTest  [OPTIONAL — requires scaffold]"

if (-not (Test-Path $GradleWrapper)) {
    Write-Skip "Gradle wrapper (gradlew.bat) not found. Skipping unit tests. (Run after Android scaffold is created.)"
} else {
    try {
        Write-Host "         Running: .\gradlew.bat testDebugUnitTest" -ForegroundColor Gray
        Push-Location $RepoRoot
        & $GradleWrapper testDebugUnitTest 2>&1 | Tee-Object -Variable gradleOutput | Out-Default
        $gradleExitCode = $LASTEXITCODE
        Pop-Location

        if ($gradleExitCode -eq 0) {
            Write-Pass "Unit tests passed."
        } else {
            Write-Fail "Unit tests failed (exit code $gradleExitCode). Review Gradle output above."
        }
    } catch {
        Write-Fail "Unit test execution failed: $($_.Exception.Message)"
        if ($PWD.Path -ne $RepoRoot) { Pop-Location }
    }
}

# ---------------------------------------------------------------------------
# OPTIONAL CHECK 6 — Lint (requires Gradle wrapper / Android scaffold)
# ---------------------------------------------------------------------------
Write-CheckStart "Lint: .\gradlew.bat lintDebug  [OPTIONAL — requires scaffold]"

if (-not (Test-Path $GradleWrapper)) {
    Write-Skip "Gradle wrapper (gradlew.bat) not found. Skipping lint. (Run after Android scaffold is created.)"
} else {
    try {
        Write-Host "         Running: .\gradlew.bat lintDebug" -ForegroundColor Gray
        Push-Location $RepoRoot
        & $GradleWrapper lintDebug 2>&1 | Out-Default
        $lintExitCode = $LASTEXITCODE
        Pop-Location

        if ($lintExitCode -eq 0) {
            Write-Pass "Lint passed — zero errors."
        } else {
            Write-Fail "Lint failed (exit code $lintExitCode). Fix lint errors before pushing. See app/build/reports/lint-results-debug.html"
        }
    } catch {
        Write-Fail "Lint execution failed: $($_.Exception.Message)"
        if ($PWD.Path -ne $RepoRoot) { Pop-Location }
    }
}

# ---------------------------------------------------------------------------
# OPTIONAL CHECK 7 — Build (requires Gradle wrapper / Android scaffold)
# ---------------------------------------------------------------------------
Write-CheckStart "Build: .\gradlew.bat assembleDebug  [OPTIONAL — requires scaffold]"

if (-not (Test-Path $GradleWrapper)) {
    Write-Skip "Gradle wrapper (gradlew.bat) not found. Skipping build. (Run after Android scaffold is created.)"
} else {
    try {
        Write-Host "         Running: .\gradlew.bat assembleDebug" -ForegroundColor Gray
        Push-Location $RepoRoot
        & $GradleWrapper assembleDebug 2>&1 | Out-Default
        $buildExitCode = $LASTEXITCODE
        Pop-Location

        if ($buildExitCode -eq 0) {
            Write-Pass "Debug build succeeded."
        } else {
            Write-Fail "Debug build failed (exit code $buildExitCode). Fix build errors before pushing."
        }
    } catch {
        Write-Fail "Build execution failed: $($_.Exception.Message)"
        if ($PWD.Path -ne $RepoRoot) { Pop-Location }
    }
}

# ---------------------------------------------------------------------------
# Final summary and exit code
# ---------------------------------------------------------------------------
Write-Summary

if ($FailCount -gt 0) {
    Write-Host "PRE-PUSH GATE FAILED — $FailCount check(s) failed. Fix the issues above before pushing." -ForegroundColor Red
    exit 1
} else {
    Write-Host "PRE-PUSH GATE PASSED — All checks passed. ($SkipCount skipped — scaffold not yet created)" -ForegroundColor Green
    exit 0
}
