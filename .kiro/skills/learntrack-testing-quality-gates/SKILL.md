---
name: learntrack-testing-quality-gates
description: Execute applicable automated tests and build checks for LearnTrack, detect regressions in the 22/19/41 topic distribution, verify data preservation, and report actual results without claiming unexecuted checks passed.
version: 1.0.0
tags: [learntrack, testing, quality-gates, android, regression]
---

## Purpose

Run the full set of applicable automated checks for LearnTrack and produce an accurate, factual result report. Every check listed must either be executed and its result recorded, or explicitly marked as skipped with the reason. Claiming a check passed without executing it is a critical failure of this skill.

---

## When to Use

- Before pushing any branch that modifies source code, JSON data, or build files.
- As part of the pre-push quality gate (`scripts/check-before-push.ps1` / `.sh`).
- After any change to `learntrack_topics.json`.
- After any change to progress calculation logic, repository, or ViewModel.
- After any change to UI screens.
- As part of CI on every pull request.

---

## Required Inputs

| Input | Source | Required? |
|---|---|---|
| Working Android project scaffold | `app/build.gradle.kts`, `gradlew` | Yes — checks cannot run without a scaffold |
| `learntrack_topics.json` | `app/src/main/assets/learntrack_topics.json` | Yes |
| Unit test suite | `app/src/test/java/com/learntrack/` | Yes |
| UI test suite | `app/src/androidTest/java/com/learntrack/` | Yes (for UI checks) |
| Pre-push scripts | `scripts/check-before-push.ps1` / `.sh` | Yes |

---

## Step-by-Step Procedure

### Step 1 — Pre-flight: confirm prerequisites

Before running any check:

- [ ] `gradlew` or `gradlew.bat` exists at repository root.
- [ ] `app/build.gradle.kts` exists.
- [ ] `learntrack_topics.json` exists at `app/src/main/assets/learntrack_topics.json`.

If any prerequisite is missing, report `BLOCKED — <item> not found. Cannot execute checks.` and stop.

### Step 2 — JSON validation

Run the JSON validation checks from the `learntrack-json-integrity` skill (or equivalent pre-push script step):

- Syntax check: `Test-Json` (PowerShell) or `python -m json.tool` (Bash).
- Count check: 41 total, 22 completed, 19 not-started.

Record result: `PASS` / `FAIL` / `SKIPPED (reason)`.

### Step 3 — Unit tests

Run:

```
./gradlew testDebugUnitTest
```

Record:
- Total tests run.
- Passed count.
- Failed count.
- Skipped count.
- Any failing test name and error message.

Do not mark this step PASS if the command was not executed. Do not estimate or assume results.

### Step 4 — Regression test

Verify the regression test for the 22/19/41 distribution runs and passes as part of Step 3.

The regression test must:
- Load the real `learntrack_topics.json` (not a mock or fixture).
- Assert `completed == 22`.
- Assert `notStarted == 19`.
- Assert `total == 41`.

If the regression test is absent from the test suite: report `FAIL — regression test TopicDistributionRegressionTest not found`.

If it exists but failed: report `FAIL — regression test failed: <message>`.

### Step 5 — Lint

Run:

```
./gradlew lintDebug
```

Record:
- Error count (must be 0).
- Warning count (acceptable; document any suppressed warnings).
- Any new errors introduced by recent changes.

Report `FAIL` if lint errors > 0.

### Step 6 — Debug build

Run:

```
./gradlew assembleDebug
```

Record: `PASS` / `FAIL`. On failure, record the Gradle error summary.

### Step 7 — UI tests (conditional)

UI tests require a connected Android device or emulator.

If a device or emulator is available, run:

```
./gradlew connectedDebugAndroidTest
```

If no device is available, record: `SKIPPED — no device or emulator connected`. Do not claim UI tests passed.

### Step 8 — Produce result report

Format the result as a table:

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

**Overall is PASS only when every non-skipped check is PASS.**

---

## Constraints

- **Never claim a check passed without executing it.** If a prerequisite (device, scaffold, test file) is missing, mark the check SKIPPED or BLOCKED with the reason.
- **Never fabricate test counts or results.** Record the actual output from Gradle, not an estimate.
- **Do not fix failures silently.** If a check fails, report the failure. Fixing is a separate task that requires re-running all affected checks.
- **Do not run in watch mode.** Use single-run commands only. Never use `--continuous` or equivalent.
- **Do not push, deploy, or delete files.** Quality gate execution is read/run only.
- **Report all failures, not just the first.** Run all checks (unless a failure makes a subsequent check unsafe) and collect all results before reporting.
- **Scope is LearnTrack only.** Do not run checks from sibling projects (MediHistory-AI, etc.).

---

## Expected Outputs

| Output | Description |
|---|---|
| Quality gate report | Structured pass/fail table as shown in Step 8 |
| Failing test details | Name and error message for each failing test |
| Lint error list | File, line, and rule for each lint error |
| Build error summary | Gradle error output if build fails |
| Skipped check log | Reason for each skipped check |

---

## Verification Criteria

- [ ] Report table lists every check in Step 8 with an explicit PASS, FAIL, or SKIPPED status.
- [ ] No check is reported as PASS without the corresponding command having been executed.
- [ ] Regression test result is listed separately (not merged with general unit tests).
- [ ] A lint error count > 0 causes Overall FAIL.
- [ ] A failing unit test causes Overall FAIL.
- [ ] A build failure causes Overall FAIL.
- [ ] Skipped UI tests do not cause Overall FAIL when no device is available.
- [ ] Report includes the actual Gradle output summary (not a paraphrase).
