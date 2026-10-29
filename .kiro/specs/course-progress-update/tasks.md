# Course Progress Update — Implementation Tasks

**Spec:** course-progress-update
**Phase:** Phase Group 2 — Architecture + Kiro Specs
**Status:** Ready for implementation — pending Android scaffold creation
**Jira Epic:** LEARNTRACK-EPIC-COURSE-PROGRESS (CSV only; not a live Jira key)

---

## Overview

Tasks are ordered so that each is independently verifiable before the next begins. Each task lists its prerequisites, the files it modifies, its verification criteria, and its Jira issue reference.

Tasks T-001 through T-003 create the Android scaffold. Tasks T-004 through T-010 implement the feature. Tasks T-011 through T-013 cover testing and quality gates.

---

## T-001 — Create Android Project Scaffold

**Description:** Create the minimal Android project structure using Kotlin, Jetpack Compose, and Hilt. This task unblocks all implementation tasks.

**Prerequisite tasks:** None

**Files to create:**
- `settings.gradle.kts` — project name and module declaration
- `build.gradle.kts` (root) — plugin declarations
- `gradle/libs.versions.toml` — version catalog with pinned versions
- `gradle/wrapper/gradle-wrapper.properties` — Gradle wrapper version
- `gradlew` / `gradlew.bat` — Gradle wrapper scripts
- `app/build.gradle.kts` — app module build config (debug + release build types, Hilt, Compose)
- `app/src/main/AndroidManifest.xml` — minimal manifest
- `app/src/main/java/com/learntrack/MainActivity.kt` — entry point
- `app/src/main/java/com/learntrack/LearnTrackApplication.kt` — Hilt application class
- `app/src/main/res/values/strings.xml` — app name
- `app/src/main/res/values/themes.xml` — Material 3 theme

**Verification criteria:**
- `./gradlew assembleDebug` completes without errors
- APK is produced at `app/build/outputs/apk/debug/app-debug.apk`
- `./gradlew lintDebug` reports no errors

**Jira:** LT-001 (scaffold sub-task)

---

## T-002 — Create Theme and Navigation Shell

**Description:** Set up Material 3 theme tokens and Jetpack Navigation Compose NavHost with placeholder screens.

**Prerequisite tasks:** T-001

**Files to create:**
- `app/src/main/java/com/learntrack/ui/theme/Color.kt` — Material 3 color scheme
- `app/src/main/java/com/learntrack/ui/theme/Typography.kt` — type scale
- `app/src/main/java/com/learntrack/ui/theme/Theme.kt` — `LearnTrackTheme` composable
- `app/src/main/java/com/learntrack/navigation/LearnTrackNavHost.kt` — NavHost with routes
- `app/src/main/java/com/learntrack/navigation/Screen.kt` — sealed class for routes

**Files to modify:**
- `app/src/main/java/com/learntrack/MainActivity.kt` — wrap content in `LearnTrackTheme`

**Verification criteria:**
- `./gradlew assembleDebug` succeeds
- Navigation routes compile without errors
- Theme composable applies Material 3 without runtime exceptions

**Jira:** LT-007

---

## T-003 — Set Up Hilt Dependency Injection

**Description:** Create Hilt application class, application module, and data module. Wire `CourseRepository` binding.

**Prerequisite tasks:** T-001

**Files to create:**
- `app/src/main/java/com/learntrack/di/AppModule.kt` — application-level Hilt module
- `app/src/main/java/com/learntrack/di/DataModule.kt` — binds `CourseRepository` to `CourseRepositoryImpl`

**Files to modify:**
- `app/src/main/java/com/learntrack/LearnTrackApplication.kt` — annotate with `@HiltAndroidApp`
- `app/src/main/java/com/learntrack/MainActivity.kt` — annotate with `@AndroidEntryPoint`

**Verification criteria:**
- `./gradlew assembleDebug` succeeds with Hilt code generation
- No `@Inject` binding errors at build time

**Jira:** LT-001 (scaffold sub-task)

---

## T-004 — Create Domain Model

**Description:** Define `Topic`, `TopicStatus`, and `CourseProgress` data classes and enums.

**Prerequisite tasks:** T-001

**Files to create:**
- `app/src/main/java/com/learntrack/data/model/Topic.kt`
- `app/src/main/java/com/learntrack/data/model/TopicStatus.kt`
- `app/src/main/java/com/learntrack/domain/model/CourseProgress.kt`
- `app/src/main/java/com/learntrack/data/model/TopicContainer.kt` — wrapper for JSON `{ "topics": [...] }` root

**Verification criteria:**
- Classes compile without errors
- `TopicStatus` maps `"completed"` → `COMPLETED` and `"not_started"` → `NOT_STARTED` via `@SerialName`
- `CourseProgress` holds `total`, `completed`, `notStarted`, `completionPercentage`

**Jira:** LT-006

---

## T-005 — Create learntrack_topics.json

**Description:** Create the authoritative course data file with 41 topics, 22 completed, and 19 not_started.

**Prerequisite tasks:** T-001 (asset directory must exist)
**Prerequisite decision:** Topic names for all 41 topics must be confirmed before this task begins (open decision — see scope doc § 9).

**Files to create:**
- `app/src/main/assets/learntrack_topics.json`

**Verification criteria:**
- File exists at the correct path
- `Test-Json` (PowerShell) or `python -m json.tool` parses file without errors
- Manual count: exactly 41 objects in `topics` array
- Manual count: exactly 22 with `"status": "completed"`
- Manual count: exactly 19 with `"status": "not_started"`
- No duplicate `id` values
- All `id` and `name` fields are non-empty strings

**Jira:** LT-005

---

## T-006 — Implement JSON Parsing and Schema Validation

**Description:** Implement `CourseRepositoryImpl` with full JSON parsing and validation. Implement `TopicSchemaValidator`.

**Prerequisite tasks:** T-003, T-004, T-005

**Files to create:**
- `app/src/main/java/com/learntrack/data/repository/CourseRepository.kt` — interface
- `app/src/main/java/com/learntrack/data/repository/CourseRepositoryImpl.kt` — implementation
- `app/src/main/java/com/learntrack/data/validation/TopicSchemaValidator.kt` — validation logic
- `app/src/main/java/com/learntrack/data/validation/ValidationException.kt` — custom exception

**Verification criteria:**
- Repository reads from `assets/learntrack_topics.json` successfully
- `IOException` → `Result.failure(IOException)`
- `SerializationException` → `Result.failure(SerializationException)`
- Validation failure → `Result.failure(ValidationException)`
- `./gradlew assembleDebug` succeeds

**Jira:** LT-005, LT-006

---

## T-007 — Implement Progress Calculation

**Description:** Implement `CalculateProgressUseCase` and `GetCourseProgressUseCase`.

**Prerequisite tasks:** T-004, T-006

**Files to create:**
- `app/src/main/java/com/learntrack/domain/usecase/CalculateProgressUseCase.kt`
- `app/src/main/java/com/learntrack/domain/usecase/GetCourseProgressUseCase.kt`

**Verification criteria:**
- `CalculateProgressUseCase(topics)` with 22/41 returns `completionPercentage == 53.658...f`
- `"%.1f%%".format(53.658)` → `"53.7%"`
- Edge case: empty list returns `completionPercentage == 0.0f`
- No hard-coded numeric literals for 22, 19, 41, or 53.7

**Jira:** LT-006

---

## T-008 — Implement CourseProgressViewModel

**Description:** Implement `CourseProgressViewModel` and `CourseUiState`.

**Prerequisite tasks:** T-007

**Files to create:**
- `app/src/main/java/com/learntrack/ui/progress/CourseUiState.kt` — sealed class
- `app/src/main/java/com/learntrack/ui/progress/CourseProgressViewModel.kt`

**Verification criteria:**
- Initial state is `Loading`
- Successful repo response → `Success(progress, topics)`
- Repository failure → `Error(message)`
- Empty topic list → `Empty`
- `./gradlew assembleDebug` succeeds

**Jira:** LT-007

---

## T-009 — Implement UI Screens

**Description:** Implement `CourseProgressScreen`, `TopicListScreen`, and `TopicDetailScreen` composables.

**Prerequisite tasks:** T-002, T-008

**Files to create:**
- `app/src/main/java/com/learntrack/ui/progress/CourseProgressScreen.kt`
- `app/src/main/java/com/learntrack/ui/topics/TopicListScreen.kt`
- `app/src/main/java/com/learntrack/ui/topics/TopicDetailScreen.kt`
- `app/src/main/java/com/learntrack/ui/components/StatusBadge.kt` — shared component
- `app/src/main/java/com/learntrack/ui/components/LoadingIndicator.kt`
- `app/src/main/java/com/learntrack/ui/components/ErrorMessage.kt`
- `app/src/main/java/com/learntrack/ui/components/EmptyState.kt`

**Files to modify:**
- `app/src/main/java/com/learntrack/navigation/LearnTrackNavHost.kt` — replace placeholders with real screens

**Verification criteria:**
- Dashboard shows "53.7%", "22 of 41", progress bar, loading and error states
- Topic list shows 41 rows with status badges when connected to real ViewModel
- Detail screen shows topic name and status
- Dark mode renders without crashes
- `./gradlew assembleDebug` succeeds
- `./gradlew lintDebug` reports no errors

**Jira:** LT-007
**Design references:** `docs/design/figma/learntrack-screen-inventory.md`, `docs/design/figma/learntrack-design-system.md`

---

## T-010 — Add Accessibility Attributes

**Description:** Add `contentDescription` to all interactive and informational UI elements. Ensure progress is communicated as text.

**Prerequisite tasks:** T-009

**Files to modify:**
- `app/src/main/java/com/learntrack/ui/progress/CourseProgressScreen.kt`
- `app/src/main/java/com/learntrack/ui/topics/TopicListScreen.kt`
- `app/src/main/java/com/learntrack/ui/topics/TopicDetailScreen.kt`
- `app/src/main/java/com/learntrack/ui/components/StatusBadge.kt`

**Verification criteria:**
- All `IconButton`, `LinearProgressIndicator`, and navigation elements have `contentDescription`
- Progress percentage is in a `Text` node readable by TalkBack
- No accessibility lint errors

**Jira:** LT-007

---

## T-011 — Write Unit Tests

**Description:** Write all unit tests for progress calculation, repository, ViewModel, and validator.

**Prerequisite tasks:** T-006, T-007, T-008

**Files to create:**
- `app/src/test/java/com/learntrack/domain/ProgressCalculatorTest.kt`
- `app/src/test/java/com/learntrack/data/repository/CourseRepositoryImplTest.kt`
- `app/src/test/java/com/learntrack/ui/progress/CourseProgressViewModelTest.kt`
- `app/src/test/java/com/learntrack/data/validation/TopicSchemaValidatorTest.kt`
- `app/src/test/java/com/learntrack/data/TopicDistributionRegressionTest.kt`

**Verification criteria:**
- `./gradlew testDebugUnitTest` passes with all tests green
- `ProgressCalculatorTest` covers: 22/41=53.7%, 0/41=0.0%, 41/41=100.0%, empty=0.0%, 1/2=50.0%
- `CourseRepositoryImplTest` covers: valid JSON, missing file, malformed JSON
- `CourseProgressViewModelTest` covers: Loading→Success, Loading→Error, Loading→Empty
- `TopicDistributionRegressionTest` asserts 22/19/41 from the actual asset

**Jira:** LT-008

---

## T-012 — Write UI and Integration Tests

**Description:** Write Compose UI tests for dashboard and topic list. Write integration test for topic distribution.

**Prerequisite tasks:** T-009, T-011

**Files to create:**
- `app/src/androidTest/java/com/learntrack/ui/CourseProgressScreenTest.kt`
- `app/src/androidTest/java/com/learntrack/ui/TopicListScreenTest.kt`
- `app/src/androidTest/java/com/learntrack/ui/TopicDetailScreenTest.kt`

**Verification criteria:**
- `./gradlew connectedDebugAndroidTest` passes (requires device or emulator)
- `CourseProgressScreenTest` asserts: "53.7%" text exists, "22" and "41" text exist, progress bar exists
- `TopicListScreenTest` asserts: 41 topic rows rendered, "Completed" and "Not Started" badges present
- `TopicDetailScreenTest` asserts: topic name visible, status badge visible, back navigation works

**Jira:** LT-008

---

## T-013 — Build Variants, Quality Gate Scripts, and CI

**Description:** Configure release build type, create pre-push quality gate scripts, and create GitHub Actions CI pipeline.

**Prerequisite tasks:** T-001, T-011

**Files to create:**
- `scripts/check-before-push.ps1` — PowerShell quality gate
- `scripts/check-before-push.sh` — Bash quality gate
- `.github/workflows/ci.yml` — GitHub Actions CI pipeline
- `proguard-rules.pro` — ProGuard rules for release build
- `docs/quality-gates/learntrack-quality-gates.md`
- `docs/build-variants/learntrack-build-variants.md`

**Files to modify:**
- `app/build.gradle.kts` — add release build type with ProGuard and signing config

**Verification criteria:**
- `scripts/check-before-push.ps1` runs without errors and exits 0 on a clean workspace
- `scripts/check-before-push.ps1` exits non-zero when unit tests fail
- `.github/workflows/ci.yml` passes on a push to a feature branch
- `./gradlew assembleRelease` builds (may require local signing setup)

**Jira:** LT-010

---

## T-014 — Release Readiness Verification

**Description:** Run all acceptance criteria (AC-1 through AC-8), verify traceability, and produce the release-readiness report.

**Prerequisite tasks:** T-005 through T-013 (all feature and test tasks complete)

**Files to create:**
- `docs/release/learntrack-release-readiness-report.md`

**Verification criteria:**
- All AC-1 through AC-8 items listed with pass/fail/skip
- Traceability CSV has no empty Implementation File or Test File cells for in-scope requirements
- No secrets committed (verify with `git log --all -- '*.properties'` and manual review)
- Release-readiness report is committed to the repository

**Jira:** LT-011

---

## Task Dependency Graph

```
T-001 (scaffold)
   ├── T-002 (theme + nav)
   ├── T-003 (hilt)  ──────────────────────┐
   └── T-004 (domain model)                │
                                           ▼
T-005 (JSON file) ──────────► T-006 (repo + validator)
                                           │
                                           ▼
                              T-007 (progress use case)
                                           │
                                           ▼
                              T-008 (viewmodel)
                                           │
                              ┌────────────┘
                              ▼
                  T-002 ──► T-009 (UI screens)
                              │
                              ▼
                           T-010 (accessibility)
                              │
              ┌───────────────┼────────────────────────┐
              ▼               ▼                        ▼
         T-011 (unit tests) T-012 (UI tests)    T-013 (build / CI)
              │               │                        │
              └───────────────┴────────────────────────┘
                                      │
                                      ▼
                               T-014 (release readiness)
```

---

## Open Decisions Blocking Tasks

| Decision | Blocking task | Owner |
|---|---|---|
| Exact topic names for all 41 topics | T-005 | User / product |
| Minimum Android SDK version | T-001 | Architect (default: API 26) |
| Whether topic status becomes mutable at runtime | T-006, T-007 | Architect |
| Release keystore credentials | T-013 | DevOps / developer |

---

## References

- Kiro requirements spec: `.kiro/specs/course-progress-update/requirements.md`
- Kiro design spec: `.kiro/specs/course-progress-update/design.md`
- Architecture doc: `docs/architecture/learntrack-architecture.md`
- Data flow doc: `docs/architecture/learntrack-data-flow.md`
- Environments doc: `docs/architecture/learntrack-environments.md`
- Jira stories CSV: `docs/jira/learntrack-stories.csv`
- Jira traceability CSV: `docs/jira/learntrack-traceability.csv`
- Scope doc: `docs/scope/learntrack-scope.md`
