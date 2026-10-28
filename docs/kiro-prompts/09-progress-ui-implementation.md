# Prompt 09 — Progress UI Implementation

## Prerequisites

- Prompt 08 complete: `app/src/main/assets/learntrack_topics.json` exists and passes validation.
- Prompt 05 complete: `docs/architecture/learntrack-architecture.md` and `learntrack-data-flow.md` exist.
- Prompt 04 complete: `docs/design/figma/learntrack-screen-inventory.md` and `learntrack-design-system.md` exist.
- Prompt 07 complete: `.kiro/specs/course-progress-update/design.md` and `tasks.md` exist.
- Android project scaffold exists: `app/build.gradle.kts`, `settings.gradle.kts`, `gradlew`.

## Specific Task

Implement the full course progress feature following the architecture, design spec, and Kiro spec. Read all reference documents before writing any code.

### Implementation Order

Implement in dependency order — lower layers first:

**1. Data model** (`app/src/main/java/com/learntrack/data/model/`)
- `Topic.kt` — data class with `id: String`, `name: String`, `status: TopicStatus`
- `TopicStatus.kt` — enum with `COMPLETED`, `NOT_STARTED`

**2. Domain model** (`app/src/main/java/com/learntrack/domain/model/`)
- `CourseProgress.kt` — data class with `total: Int`, `completed: Int`, `notStarted: Int`, `completionPercentage: Float`

**3. Repository** (`app/src/main/java/com/learntrack/data/repository/`)
- `CourseRepository.kt` — interface with `fun getTopics(): Flow<Result<List<Topic>>>`
- `CourseRepositoryImpl.kt` — reads `learntrack_topics.json` from `AssetManager` on `Dispatchers.IO`; validates schema; emits `Result.success` or `Result.failure`

**4. Use cases** (`app/src/main/java/com/learntrack/domain/usecase/`)
- `GetCourseProgressUseCase.kt` — calls repository, maps to `CourseProgress`
- `CalculateProgressUseCase.kt` — pure function: `fun calculate(topics: List<Topic>): CourseProgress`

**5. UI state** (`app/src/main/java/com/learntrack/ui/progress/`)
- `CourseUiState.kt` — sealed class: `Loading`, `Success(progress, topics)`, `Error(message)`, `Empty`

**6. ViewModel** (`app/src/main/java/com/learntrack/ui/progress/`)
- `CourseProgressViewModel.kt` — `@HiltViewModel`; exposes `StateFlow<CourseUiState>`; collects from use case on `viewModelScope`

**7. UI screens**
- `CourseProgressScreen.kt` — stateless composable; handles all four states; collects via `collectAsStateWithLifecycle()`
- `TopicListScreen.kt` — `LazyColumn` of topic rows with status badges
- `TopicDetailScreen.kt` — shows single topic name and status; back navigation

**8. DI** (`app/src/main/java/com/learntrack/di/`)
- `AppModule.kt` — provides `AssetManager`
- `DataModule.kt` — binds `CourseRepository` to `CourseRepositoryImpl`

**9. Theme** (`app/src/main/java/com/learntrack/theme/`)
- `Color.kt`, `Theme.kt`, `Type.kt` — Material 3 theme tokens from `learntrack-design-system.md`

### Key Implementation Rules

- Never hard-code `53.7`, `22`, `19`, or `41` in application logic.
- All asset reads on `Dispatchers.IO`.
- `collectAsStateWithLifecycle()` not `collectAsState()`.
- `Modifier` as last positional parameter on all composables.
- All interactive elements have `contentDescription`.
- `LinearProgressIndicator` progress = `completionPercentage / 100f`.
- Format percentage: `"%.1f%%".format(completionPercentage)`.

## Allowed Scope

- Write to `app/src/main/java/com/learntrack/` (all subdirectories listed above).
- Write `AndroidManifest.xml` and `MainActivity.kt` if scaffold requires them.
- Do not modify `learntrack_topics.json`.
- Do not modify documentation files.
- Do not add dependencies not already in `gradle/libs.versions.toml`.

## Required Outputs

All Kotlin source files listed in the Implementation Order above, at their specified paths.

## Acceptance Criteria

- [ ] All source files compile without errors (`./gradlew assembleDebug` passes).
- [ ] `CourseProgressScreen` renders percentage as `"53.7%"` when data is 22/41.
- [ ] `LinearProgressIndicator` receives `progress = 0.537...f` (not `53.7f`).
- [ ] No hard-coded progress constants in any Kotlin source file.
- [ ] All four `CourseUiState` branches are handled in each screen composable.
- [ ] No Android framework imports in `domain.*` packages.
- [ ] No composable imports directly from `data.*` packages.
- [ ] Hilt provides `CourseRepositoryImpl` without manual DI wiring.
- [ ] `./gradlew lintDebug` reports no new errors.

## Stop Conditions

- Stop if `learntrack_topics.json` does not pass validation — UI built on invalid data is not acceptable.
- Stop if any compilation error cannot be resolved — report the error and the attempted fix before stopping.
- Stop if a required dependency is not in `libs.versions.toml` — do not add it without flagging it to the user.
- Do not write test files — that is Prompt 10.
