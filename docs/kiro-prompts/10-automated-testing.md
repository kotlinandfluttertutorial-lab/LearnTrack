# Prompt 10 — Automated Testing

## Prerequisites

- Prompt 09 complete: all source files in `app/src/main/java/com/learntrack/` compile successfully.
- `app/src/main/assets/learntrack_topics.json` passes validation (41/22/19).
- `./gradlew assembleDebug` passes.

## Specific Task

Write all automated tests for the LearnTrack course progress feature, then run them and record the actual results. Do not claim tests pass without executing them.

### Unit Tests (`app/src/test/java/com/learntrack/`)

**`domain/ProgressCalculatorTest.kt`**

Test `CalculateProgressUseCase` (or the progress calculation function):

| Test | Input | Expected output |
|---|---|---|
| Authoritative distribution | 22 completed / 41 total | `completionPercentage ≈ 53.658f`, displayed as `"53.7%"` |
| All completed | 41 completed / 41 total | `completionPercentage = 100.0f` |
| None completed | 0 completed / 41 total | `completionPercentage = 0.0f` |
| Empty list | 0 topics | `completionPercentage = 0.0f`, `total = 0` |
| Single topic completed | 1 completed / 1 total | `completionPercentage = 100.0f` |
| Partial — half | 20 completed / 40 total | `completionPercentage = 50.0f` |

Also verify `total`, `completed`, and `notStarted` fields match inputs.

**`data/repository/CourseRepositoryImplTest.kt`**

Test `CourseRepositoryImpl` with mocked `AssetManager` (using MockK or equivalent):

- Valid JSON with 3 topics → emits `Result.success(listOf(...))`
- Missing file (IOException) → emits `Result.failure(IOException)`
- Malformed JSON (ParseException) → emits `Result.failure(...)`
- Unknown status value → emits `Result.failure(ValidationException)`
- Duplicate topic IDs → emits `Result.failure(ValidationException)`
- Empty topics array → emits `Result.success(emptyList())`

**`regression/TopicDistributionRegressionTest.kt`**

Test using the real `learntrack_topics.json` (loaded from the test resources or assets):

```kotlin
@Test
fun `topic distribution matches authoritative values`() {
    val topics = loadTopicsFromAsset() // reads the real file
    val completed = topics.count { it.status == TopicStatus.COMPLETED }
    val notStarted = topics.count { it.status == TopicStatus.NOT_STARTED }
    assertEquals("Expected 41 total topics", 41, topics.size)
    assertEquals("Expected 22 completed topics", 22, completed)
    assertEquals("Expected 19 not-started topics", 19, notStarted)
}
```

This test must fail loudly with a descriptive message if any count drifts.

### UI Tests (`app/src/androidTest/java/com/learntrack/ui/`)

**`CourseProgressScreenTest.kt`**

- Loading state: verify `CircularProgressIndicator` is shown.
- Success state with 22/41: verify "53.7%" text is displayed; verify progress bar is present with semantic label.
- Error state: verify error message text is displayed; verify percentage text is not shown.
- Empty state: verify empty state composable is shown.

**`TopicListScreenTest.kt`**

- Success state with 41 topics: verify all 41 rows are rendered (scroll through LazyColumn).
- Each row shows topic name and a status badge.
- Completed topic shows "Completed" label.
- Not-started topic shows "Not Started" label.

### Run Tests and Record Results

After writing all tests, run:

```
./gradlew testDebugUnitTest
```

Record the exact output: total tests, passed, failed, skipped.

If a device or emulator is available, also run:
```
./gradlew connectedDebugAndroidTest
```

Record UI test results or mark as SKIPPED with reason if no device is available.

## Allowed Scope

- Write to `app/src/test/java/com/learntrack/` (unit tests).
- Write to `app/src/androidTest/java/com/learntrack/` (UI tests).
- Copy `learntrack_topics.json` to `app/src/test/resources/` if needed for the regression test.
- Do not modify source code under `app/src/main/` unless a bug is found during testing (if so, fix the bug and re-run).
- Do not modify documentation or JSON data files.

## Required Outputs

- `app/src/test/java/com/learntrack/domain/ProgressCalculatorTest.kt`
- `app/src/test/java/com/learntrack/data/repository/CourseRepositoryImplTest.kt`
- `app/src/test/java/com/learntrack/regression/TopicDistributionRegressionTest.kt`
- `app/src/androidTest/java/com/learntrack/ui/CourseProgressScreenTest.kt`
- `app/src/androidTest/java/com/learntrack/ui/TopicListScreenTest.kt`
- Actual test execution results (pass/fail counts from Gradle output)

## Acceptance Criteria

- [ ] All three unit test files exist with the required test cases.
- [ ] Regression test loads the real `learntrack_topics.json` and asserts 41/22/19.
- [ ] Both UI test files exist with loading, success, error, and (where applicable) empty state tests.
- [ ] `./gradlew testDebugUnitTest` passes with 0 failures.
- [ ] Regression test fails if `learntrack_topics.json` is temporarily modified to 21/20/41 (verified manually or in a dry run).
- [ ] No hard-coded progress constants in any test beyond the assertion values themselves.
- [ ] Test results are recorded with actual Gradle output, not estimated.

## Stop Conditions

- Stop if Prompt 09 source files do not compile — tests cannot run against broken code.
- Stop and report if any unit test fails unexpectedly — do not suppress failures.
- Do not modify `learntrack_topics.json` to make tests pass — fix the code or the test logic.
- Do not proceed to Prompt 11 with failing unit tests.
