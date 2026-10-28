# Prompt 11 — Build and Quality Gates

## Prerequisites

- Prompt 10 complete: all tests written and passing (`./gradlew testDebugUnitTest` = 0 failures).
- Prompt 09 complete: all source files compile.
- Prompt 08 complete: `learntrack_topics.json` passes validation.
- `scripts/check-before-push.ps1` and `scripts/check-before-push.sh` exist (or must be created as part of this prompt).

## Specific Task

Run all quality gate checks, fix any issues found, and record the actual results. Also ensure the pre-push scripts are complete and correct.

### Step 1 — Verify or create pre-push scripts

Read `scripts/check-before-push.ps1` and `scripts/check-before-push.sh`. If they do not exist or are incomplete, update them to run the following checks in order:

**`scripts/check-before-push.ps1` (PowerShell / Windows)**:
1. JSON syntax check: `Get-Content app/src/main/assets/learntrack_topics.json | Test-Json`
2. JSON count check: parse JSON; assert total=41, completed=22, not_started=19
3. Unit tests: `./gradlew testDebugUnitTest`
4. Lint: `./gradlew lintDebug`
5. Debug build: `./gradlew assembleDebug`

**`scripts/check-before-push.sh` (Bash / Linux / macOS)**:
1. JSON syntax check: `python3 -m json.tool app/src/main/assets/learntrack_topics.json > /dev/null`
2. JSON count check: use `python3` or `jq` to assert total=41, completed=22, not_started=19
3. Unit tests: `./gradlew testDebugUnitTest`
4. Lint: `./gradlew lintDebug`
5. Debug build: `./gradlew assembleDebug`

Both scripts must:
- Exit with code 1 on any failure.
- Print which check failed.
- Not push, deploy, or delete files.
- Be safe to re-run.

### Step 2 — Run JSON validation

Execute the JSON validation from Step 1 (PowerShell on Windows). Record result: PASS / FAIL.

### Step 3 — Run unit tests

```
./gradlew testDebugUnitTest
```

Record: total, passed, failed, skipped. Overall: PASS (0 failures) / FAIL.

### Step 4 — Run lint

```
./gradlew lintDebug
```

Record: error count (must be 0), warning count. Overall: PASS (0 errors) / FAIL.

If errors exist, fix them before proceeding.

### Step 5 — Run debug build

```
./gradlew assembleDebug
```

Record: PASS / FAIL. On failure, record the Gradle error summary and fix before proceeding.

### Step 6 — Run UI tests (conditional)

If a device or emulator is connected:
```
./gradlew connectedDebugAndroidTest
```
Record results. If no device: record SKIPPED.

### Step 7 — Produce quality gate report

```
LearnTrack Quality Gate Report
═══════════════════════════════════════════════════
Check                   Result    Notes
───────────────────────────────────────────────────
JSON validation         PASS/FAIL
Unit tests              PASS/FAIL  N passed, N failed
Regression test (dist.) PASS/FAIL
Lint                    PASS/FAIL  N errors, N warnings
Debug build             PASS/FAIL
UI tests                PASS/FAIL/SKIPPED
───────────────────────────────────────────────────
Overall                 PASS/FAIL
═══════════════════════════════════════════════════
```

Update `docs/quality-gates/learntrack-quality-gates.md` with this report.

## Allowed Scope

- Read and update `scripts/check-before-push.ps1` and `scripts/check-before-push.sh`.
- Run Gradle commands and record output.
- Fix lint errors and compile errors in `app/src/main/java/` if discovered.
- Write quality gate report to `docs/quality-gates/learntrack-quality-gates.md`.
- Do not modify `learntrack_topics.json` or test files.

## Required Outputs

- Updated `scripts/check-before-push.ps1`
- Updated `scripts/check-before-push.sh`
- `docs/quality-gates/learntrack-quality-gates.md` with actual check results
- All checks PASS (or SKIPPED with documented reason)

## Acceptance Criteria

- [ ] Both pre-push scripts exist and run without errors on their respective platforms.
- [ ] JSON validation step in scripts asserts 41/22/19 with non-zero exit on failure.
- [ ] `./gradlew testDebugUnitTest` passes with 0 failures.
- [ ] `./gradlew lintDebug` reports 0 errors.
- [ ] `./gradlew assembleDebug` succeeds.
- [ ] Quality gate report in `docs/quality-gates/learntrack-quality-gates.md` shows actual results.
- [ ] No check is reported as PASS without having been executed.

## Stop Conditions

- Stop if unit tests fail — do not mark the gate PASS with failing tests.
- Stop if the debug build fails and cannot be fixed — report the exact error.
- Do not proceed to Prompt 12 with any non-skipped check in FAIL state.
- Do not skip checks by commenting them out of the scripts without documenting why.
