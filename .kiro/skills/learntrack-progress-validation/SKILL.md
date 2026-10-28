---
name: learntrack-progress-validation
description: Calculate course progress from learntrack_topics.json, verify the 22 completed / 19 not-started / 41 total / 53.7% distribution, check percentage rounding, and validate consistency between the UI and the underlying data.
version: 1.0.0
tags: [learntrack, progress, validation, calculation, android]
---

## Purpose

Verify that the progress values displayed in the LearnTrack UI exactly match those derived from `learntrack_topics.json`. Catch any mismatch between the data layer and the UI layer — including hard-coded values, rounding errors, and stale cached counts.

---

## When to Use

- After any change to `learntrack_topics.json` to confirm the UI will reflect correct values.
- After any change to `CalculateProgressUseCase`, `CourseRepositoryImpl`, or `CourseProgressViewModel`.
- When reviewing a pull request that touches progress display or data loading.
- As part of the pre-push quality gate.
- When a reported UI value (e.g., percentage, count) does not match expectations.

---

## Required Inputs

| Input | Source | Required? |
|---|---|---|
| `learntrack_topics.json` | `app/src/main/assets/learntrack_topics.json` | Yes |
| `CalculateProgressUseCase` source | `app/src/main/java/com/learntrack/domain/usecase/CalculateProgressUseCase.kt` | Yes (when validating logic) |
| `CourseProgressViewModel` source | `app/src/main/java/com/learntrack/ui/progress/CourseProgressViewModel.kt` | Yes (when validating UI binding) |
| Unit test results | Output of `./gradlew testDebugUnitTest` | Yes (when validating test coverage) |

---

## Step-by-Step Procedure

### Step 1 — Derive expected values from source data

Read `learntrack_topics.json` and compute:

```
total          = count(topics)
completed      = count(topics where status == "completed")
not_started    = count(topics where status == "not_started")
percentage_raw = (completed / total) * 100        // floating point
percentage_1dp = round(percentage_raw, 1 decimal) // display value
```

For the authoritative distribution:

| Variable | Expected value |
|---|---|
| `total` | 41 |
| `completed` | 22 |
| `not_started` | 19 |
| `percentage_raw` | 53.658536...% |
| `percentage_1dp` | 53.7% |

### Step 2 — Validate the calculation logic

Read `CalculateProgressUseCase.kt` (or the equivalent object containing the calculation):

- Confirm the formula is `(completed.toFloat() / total) * 100f` — not a hard-coded constant.
- Confirm the result is returned as a `Float` in a `CourseProgress` data class.
- Confirm `total`, `completed`, and `notStarted` are derived from the input `List<Topic>`, not from constants.
- Confirm no fallback constant (22, 19, 41, 53.7) appears in the calculation path.

If any hard-coded progress constant is found: report `FAIL — hard-coded value <N> found in <file>:<line>`.

### Step 3 — Validate display formatting

Confirm the UI formats the percentage using `"%.1f%%".format(completionPercentage)` or equivalent.

- For input `53.658...f`: the formatted output must be `"53.7%"`.
- For input `100.0f`: the formatted output must be `"100.0%"`.
- For input `0.0f`: the formatted output must be `"0.0%"`.
- For input `50.0f`: the formatted output must be `"50.0%"` (no rounding artifact to `"49.9%"` or `"50.1%"`).

If the formatting pattern differs from `%.1f%%`, verify it produces identical output for all test vectors above.

### Step 4 — Validate UI binding

Read `CourseProgressViewModel.kt`:

- Confirm `uiState` is a `StateFlow<CourseUiState>` (not `LiveData`, not a plain variable).
- Confirm the `Success` state carries a `CourseProgress` object derived from the repository, not from a constant.
- Confirm `completionPercentage` in `CourseProgress` is the calculated float, not a hard-coded string.

Read `CourseProgressScreen.kt`:

- Confirm the percentage text is bound to `progress.completionPercentage` formatted at render time.
- Confirm the `LinearProgressIndicator` (or equivalent) uses `progress.completionPercentage / 100f` as its `progress` parameter.
- Confirm the "X of Y" display uses `progress.completed` and `progress.total` from the data object.

### Step 5 — Validate unit test coverage

Verify unit tests exist for progress calculation:

- Test: 22 completed / 41 total → 53.658...% → displays as `"53.7%"`.
- Test: 0 completed / 41 total → 0.0% → displays as `"0.0%"`.
- Test: 41 completed / 41 total → 100.0% → displays as `"100.0%"`.
- Test: empty list → 0.0%.
- Test: `CourseProgress` fields `total`, `completed`, `notStarted` match input data.

If any required test vector is missing: report `WARN — missing test vector: <description>`.

### Step 6 — Check regression test

Verify a regression test exists that:

- Loads `learntrack_topics.json` (real file, not a mock).
- Asserts `completed == 22`, `notStarted == 19`, `total == 41`.
- Fails loudly with a descriptive message if any count drifts.

If absent: report `FAIL — regression test for 22/19/41 distribution not found`.

### Step 7 — Report result

```
progress validation
───────────────────────────────────────
Source data:      41 topics, 22 completed, 19 not-started
Calculated %:     53.658...% → "53.7%" ✓
Calculation logic: PASS / FAIL
Display format:   PASS / FAIL
UI binding:       PASS / FAIL
Unit test vectors: PASS / N warnings
Regression test:  PASS / FAIL

Overall: PASS / FAIL
```

---

## Constraints

- **No hard-coded values in application logic.** The values 22, 19, 41, and 53.7 may appear only in test assertions, comments, and documentation.
- **Percentage is always computed at runtime** from `List<Topic>`. Any deviation is a FAIL.
- **Do not accept display-layer formatting that rounds incorrectly.** `53.658%` must display as `53.7%`, not `54%` or `53.66%`.
- **Do not modify any source file during validation.** Validation is read-only. Fixes are a separate task.
- **Scope is LearnTrack only.** Do not apply medical or other domain-specific validation logic from sibling projects.

---

## Expected Outputs

| Output | Description |
|---|---|
| Derived values table | Computed total, completed, not-started, raw %, display % |
| Logic check result | PASS/FAIL for calculation formula |
| Format check result | PASS/FAIL for `%.1f%%` formatting and all test vectors |
| UI binding result | PASS/FAIL for ViewModel and composable binding |
| Test coverage report | List of required test vectors and their status |
| Regression test status | PASS/FAIL/MISSING |
| Overall result | `PASS` or `FAIL` |

---

## Verification Criteria

- [ ] Skill correctly identifies `53.658%` as the raw value and `53.7%` as the display value for 22/41.
- [ ] Skill fails when a hard-coded `53.7` constant is found in application logic.
- [ ] Skill fails when the percentage is formatted as `"%.0f%%"` (no decimal place).
- [ ] Skill fails when `completionPercentage` is bound as a static string instead of a computed float.
- [ ] Skill passes on a correct implementation with all test vectors present.
- [ ] Skill identifies missing regression test as a FAIL.
- [ ] Skill does not modify any source file during execution.
