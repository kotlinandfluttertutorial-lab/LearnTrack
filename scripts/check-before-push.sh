#!/usr/bin/env bash
# =============================================================================
# LearnTrack — Pre-Push Quality Gate Script (Bash)
# =============================================================================
# Purpose  : Run all quality checks before pushing to the remote repository.
# Platform : Android project using Gradle (Kotlin DSL)
# Usage    : bash scripts/check-before-push.sh
#            Run from the repository root directory.
#
# To make this script directly executable:
#   chmod +x scripts/check-before-push.sh
#   ./scripts/check-before-push.sh
#
# This script is SAFE TO RERUN. It does NOT push, deploy, or delete files.
# Exit code 0 = all checks passed.
# Exit code 1 = one or more checks failed (check output for details).
#
# Requirements:
#   - bash 4+
#   - git installed and on PATH
#   - Gradle wrapper (gradlew) present in the project root
#     (created during Android scaffold phase)
#   - jq (optional — used for JSON validation; falls back to Python if unavailable)
#
# OPTIONAL checks are clearly marked. They are skipped automatically when
# their dependencies are not yet available (e.g., before scaffold is created).
# =============================================================================

set -euo pipefail

# ---------------------------------------------------------------------------
# Configuration
# ---------------------------------------------------------------------------
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"
JSON_FILE="$REPO_ROOT/app/src/main/assets/learntrack_topics.json"
GRADLE_WRAPPER="$REPO_ROOT/gradlew"
EXPECTED_TOTAL=41
EXPECTED_COMPLETED=22
EXPECTED_NOT_STARTED=19

# Counters
PASS_COUNT=0
FAIL_COUNT=0
SKIP_COUNT=0

# ---------------------------------------------------------------------------
# ANSI colour codes
# ---------------------------------------------------------------------------
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
CYAN='\033[0;36m'
GRAY='\033[0;37m'
RESET='\033[0m'

# ---------------------------------------------------------------------------
# Helper functions
# ---------------------------------------------------------------------------
print_header() {
    echo ""
    echo -e "${CYAN}=================================================================${RESET}"
    echo -e "${CYAN}  $1${RESET}"
    echo -e "${CYAN}=================================================================${RESET}"
}

print_check_start() {
    echo ""
    echo -e "${YELLOW}[CHECK] $1 ...${RESET}"
}

print_pass() {
    echo -e "${GREEN}[PASS]  $1${RESET}"
    PASS_COUNT=$((PASS_COUNT + 1))
}

print_fail() {
    echo -e "${RED}[FAIL]  $1${RESET}"
    FAIL_COUNT=$((FAIL_COUNT + 1))
}

print_skip() {
    echo -e "${YELLOW}[SKIP]  $1${RESET}"
    SKIP_COUNT=$((SKIP_COUNT + 1))
}

print_info() {
    echo -e "${GRAY}         $1${RESET}"
}

print_summary() {
    echo ""
    echo -e "${CYAN}=================================================================${RESET}"
    echo -e "${CYAN}  SUMMARY${RESET}"
    echo -e "${CYAN}=================================================================${RESET}"
    echo -e "${GREEN}  Passed : $PASS_COUNT${RESET}"
    if [ "$FAIL_COUNT" -gt 0 ]; then
        echo -e "${RED}  Failed : $FAIL_COUNT${RESET}"
    else
        echo -e "${GREEN}  Failed : $FAIL_COUNT${RESET}"
    fi
    echo -e "${YELLOW}  Skipped: $SKIP_COUNT${RESET}"
    echo ""
}

# ---------------------------------------------------------------------------
# Begin
# ---------------------------------------------------------------------------
print_header "LearnTrack Pre-Push Quality Gate"
echo "Repository root : $REPO_ROOT"
echo "Run timestamp   : $(date '+%Y-%m-%d %H:%M:%S')"

# ---------------------------------------------------------------------------
# CHECK 1 — JSON syntax validation
# ---------------------------------------------------------------------------
print_check_start "JSON syntax validation: learntrack_topics.json"

if [ ! -f "$JSON_FILE" ]; then
    print_skip "learntrack_topics.json not found at expected path. Skipping."
    print_skip "Expected location: $JSON_FILE"
    print_skip "(File will exist after scaffold + JSON phase.)"
else
    # Prefer jq; fall back to python3 if jq is not installed
    if command -v jq &>/dev/null; then
        if jq empty "$JSON_FILE" 2>/dev/null; then
            print_pass "learntrack_topics.json is valid JSON (validated with jq)."
        else
            print_fail "learntrack_topics.json failed JSON syntax validation (jq)."
        fi
    elif command -v python3 &>/dev/null; then
        if python3 -c "import json,sys; json.load(open('$JSON_FILE'))" 2>/dev/null; then
            print_pass "learntrack_topics.json is valid JSON (validated with python3)."
        else
            print_fail "learntrack_topics.json failed JSON syntax validation (python3)."
        fi
    else
        print_skip "Neither jq nor python3 is available. Cannot validate JSON syntax."
        print_skip "Install jq (https://stedolan.github.io/jq/) and rerun."
    fi
fi

# ---------------------------------------------------------------------------
# CHECK 2 — Topic count validation (22 completed / 19 not-started / 41 total)
# ---------------------------------------------------------------------------
print_check_start "Topic count validation (expected: $EXPECTED_TOTAL total, $EXPECTED_COMPLETED completed, $EXPECTED_NOT_STARTED not-started)"

if [ ! -f "$JSON_FILE" ]; then
    print_skip "learntrack_topics.json not found. Skipping count validation."
else
    COUNT_OK=true

    if command -v jq &>/dev/null; then
        TOTAL=$(jq '. | length' "$JSON_FILE" 2>/dev/null || echo "0")
        COMPLETED=$(jq '[.[] | select(.status == "completed")] | length' "$JSON_FILE" 2>/dev/null || echo "0")
        NOT_STARTED=$(jq '[.[] | select(.status == "not_started")] | length' "$JSON_FILE" 2>/dev/null || echo "0")
        OTHER=$(jq '[.[] | select(.status != "completed" and .status != "not_started")] | length' "$JSON_FILE" 2>/dev/null || echo "0")
    elif command -v python3 &>/dev/null; then
        TOTAL=$(python3 -c "
import json
data = json.load(open('$JSON_FILE'))
print(len(data))
" 2>/dev/null || echo "0")
        COMPLETED=$(python3 -c "
import json
data = json.load(open('$JSON_FILE'))
print(sum(1 for t in data if t.get('status') == 'completed'))
" 2>/dev/null || echo "0")
        NOT_STARTED=$(python3 -c "
import json
data = json.load(open('$JSON_FILE'))
print(sum(1 for t in data if t.get('status') == 'not_started'))
" 2>/dev/null || echo "0")
        OTHER=$(python3 -c "
import json
data = json.load(open('$JSON_FILE'))
print(sum(1 for t in data if t.get('status') not in ('completed','not_started')))
" 2>/dev/null || echo "0")
    else
        print_skip "Neither jq nor python3 is available. Cannot validate topic counts."
        COUNT_OK=false
        TOTAL=0; COMPLETED=0; NOT_STARTED=0; OTHER=0
    fi

    if [ "$COUNT_OK" = true ]; then
        print_info "Total      : $TOTAL (expected $EXPECTED_TOTAL)"
        print_info "Completed  : $COMPLETED (expected $EXPECTED_COMPLETED)"
        print_info "Not started: $NOT_STARTED (expected $EXPECTED_NOT_STARTED)"
        [ "$OTHER" -gt 0 ] && print_info "Unknown status: $OTHER topics have unrecognized status values!"

        if [ "$TOTAL" -eq "$EXPECTED_TOTAL" ] && \
           [ "$COMPLETED" -eq "$EXPECTED_COMPLETED" ] && \
           [ "$NOT_STARTED" -eq "$EXPECTED_NOT_STARTED" ] && \
           [ "$OTHER" -eq 0 ]; then
            print_pass "Topic distribution is correct: $COMPLETED completed, $NOT_STARTED not-started, $TOTAL total."
        else
            print_fail "Topic distribution mismatch. Expected $EXPECTED_COMPLETED/$EXPECTED_NOT_STARTED/$EXPECTED_TOTAL, got $COMPLETED/$NOT_STARTED/$TOTAL."
        fi
    fi
fi

# ---------------------------------------------------------------------------
# CHECK 3 — Secret / credential pattern detection in staged files
# ---------------------------------------------------------------------------
print_check_start "Secret detection in staged files"

# Get list of staged files
if STAGED_FILES=$(git -C "$REPO_ROOT" diff --cached --name-only 2>/dev/null); then
    if [ -z "$STAGED_FILES" ]; then
        print_pass "No staged files to check for secrets."
    else
        SECRET_FOUND=false
        # Patterns that may indicate a committed secret
        SECRET_PATTERNS=(
            'password\s*=\s*\S+'
            'api[_-]?key\s*=\s*\S+'
            'secret\s*=\s*\S+'
            'token\s*=\s*\S+'
            'private[_-]?key'
            'BEGIN RSA PRIVATE KEY'
            'BEGIN EC PRIVATE KEY'
            'BEGIN OPENSSH PRIVATE KEY'
            'AIza[0-9A-Za-z_-]{35}'    # Google API key pattern
            'github_pat_[A-Za-z0-9_]{82}'  # GitHub fine-grained PAT
            'ghp_[A-Za-z0-9]{36}'          # GitHub classic PAT
        )

        SKIP_EXTENSIONS=('.png' '.jpg' '.jpeg' '.gif' '.webp' '.apk' '.aab' '.keystore' '.jks')

        while IFS= read -r staged_file; do
            [ -z "$staged_file" ] && continue
            full_path="$REPO_ROOT/$staged_file"
            [ ! -f "$full_path" ] && continue  # File may have been deleted

            # Skip binary/media files by extension
            ext="${staged_file##*.}"
            ext=".$ext"
            skip=false
            for skip_ext in "${SKIP_EXTENSIONS[@]}"; do
                if [ "$ext" = "$skip_ext" ]; then
                    skip=true
                    break
                fi
            done
            $skip && continue

            # Check for secret patterns
            for pattern in "${SECRET_PATTERNS[@]}"; do
                if grep -qiE "$pattern" "$full_path" 2>/dev/null; then
                    print_fail "Possible secret found in staged file: $staged_file (matched pattern: $pattern)"
                    SECRET_FOUND=true
                fi
            done
        done <<< "$STAGED_FILES"

        if [ "$SECRET_FOUND" = false ]; then
            print_pass "No obvious secrets detected in staged files."
        fi
    fi
else
    print_skip "Could not retrieve staged files from git. Skipping secret detection."
fi

# ---------------------------------------------------------------------------
# CHECK 4 — Git working tree status
# ---------------------------------------------------------------------------
print_check_start "Git working tree status"

if GIT_STATUS=$(git -C "$REPO_ROOT" status --porcelain 2>/dev/null); then
    if [ -z "$GIT_STATUS" ]; then
        print_pass "Working tree is clean — no uncommitted changes."
    else
        print_info "Note: Working tree has uncommitted changes:"
        while IFS= read -r line; do
            print_info "  $line"
        done <<< "$GIT_STATUS"
        print_pass "Working tree status noted. Staged changes will be included in the push."
    fi
else
    print_skip "Could not check git status. Skipping."
fi

# ---------------------------------------------------------------------------
# OPTIONAL CHECK 5 — Unit tests (requires Gradle wrapper / Android scaffold)
# ---------------------------------------------------------------------------
print_check_start "Unit tests: ./gradlew testDebugUnitTest  [OPTIONAL — requires scaffold]"

if [ ! -f "$GRADLE_WRAPPER" ]; then
    print_skip "Gradle wrapper (gradlew) not found. Skipping unit tests."
    print_skip "(Run after Android scaffold is created.)"
else
    chmod +x "$GRADLE_WRAPPER"
    print_info "Running: ./gradlew testDebugUnitTest"
    if (cd "$REPO_ROOT" && ./gradlew testDebugUnitTest); then
        print_pass "Unit tests passed."
    else
        print_fail "Unit tests failed. Review Gradle output above."
    fi
fi

# ---------------------------------------------------------------------------
# OPTIONAL CHECK 6 — Lint (requires Gradle wrapper / Android scaffold)
# ---------------------------------------------------------------------------
print_check_start "Lint: ./gradlew lintDebug  [OPTIONAL — requires scaffold]"

if [ ! -f "$GRADLE_WRAPPER" ]; then
    print_skip "Gradle wrapper (gradlew) not found. Skipping lint."
    print_skip "(Run after Android scaffold is created.)"
else
    print_info "Running: ./gradlew lintDebug"
    if (cd "$REPO_ROOT" && ./gradlew lintDebug); then
        print_pass "Lint passed — zero errors."
    else
        print_fail "Lint failed. Fix lint errors before pushing."
        print_info "See: app/build/reports/lint-results-debug.html"
    fi
fi

# ---------------------------------------------------------------------------
# OPTIONAL CHECK 7 — Build (requires Gradle wrapper / Android scaffold)
# ---------------------------------------------------------------------------
print_check_start "Build: ./gradlew assembleDebug  [OPTIONAL — requires scaffold]"

if [ ! -f "$GRADLE_WRAPPER" ]; then
    print_skip "Gradle wrapper (gradlew) not found. Skipping build."
    print_skip "(Run after Android scaffold is created.)"
else
    print_info "Running: ./gradlew assembleDebug"
    if (cd "$REPO_ROOT" && ./gradlew assembleDebug); then
        print_pass "Debug build succeeded."
    else
        print_fail "Debug build failed. Fix build errors before pushing."
    fi
fi

# ---------------------------------------------------------------------------
# Final summary and exit code
# ---------------------------------------------------------------------------
print_summary

if [ "$FAIL_COUNT" -gt 0 ]; then
    echo -e "${RED}PRE-PUSH GATE FAILED — $FAIL_COUNT check(s) failed. Fix the issues above before pushing.${RESET}"
    exit 1
else
    echo -e "${GREEN}PRE-PUSH GATE PASSED — All checks passed. ($SKIP_COUNT skipped — scaffold not yet created)${RESET}"
    exit 0
fi
