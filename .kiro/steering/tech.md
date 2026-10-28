# LearnTrack — Technology Steering

## Verified Technology Stack

| Dimension | Value | Confidence | Basis |
|---|---|---|---|
| Platform | Android | High | `.gitignore` Android Studio template; `AndroidStudioProjects` path |
| Language | Kotlin only | High | `.kotlin/` in `.gitignore`; org pattern (MediHistory-AI) |
| UI framework | Jetpack Compose (Material 3) | High | Org-standard; no Views code exists |
| Build system | Gradle with Kotlin DSL | High | `.gradle/`, `build/` in `.gitignore` |
| Dependency injection | Hilt | High | Org pattern |
| Async / reactive | Kotlin Coroutines + Flow | High | Org pattern |
| Architecture | MVVM + Repository | High | Org pattern |
| Persistence (current) | `assets/` JSON (read-only) | Certain | Requirement: JSON is static bundled asset |
| Persistence (future) | Room (if mutable) | Design intent | Architecture doc § 3 |
| Test frameworks | JUnit 4/5, MockK, Compose UI Test, Robolectric | High | Org pattern |
| IDE | Android Studio | Certain | `.idea/`, `*.iml` in `.gitignore` |
| Version catalog | `gradle/libs.versions.toml` | High | Org pattern; all versions pinned exactly |

---

## Coding Standards

### General Kotlin

- Kotlin only — no Java source files anywhere in `app/src/`.
- Follow [Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html): `PascalCase` for classes, `camelCase` for functions and properties, `UPPER_SNAKE_CASE` for constants.
- Keep functions short and single-purpose. Prefer expression bodies for simple functions.
- Use `data class` for models. Use `sealed class` for exhaustive state representations (`UiState`, `Result`).
- Prefer `val` over `var`. Avoid nullable types when a non-null default is possible.
- Use coroutines and `Flow` for async operations. Never block the main thread.
- All coroutine work on the repository and use-case layers uses `Dispatchers.IO` via `withContext` or `flowOn`.

### Jetpack Compose

- Composables are stateless; all state is hoisted to the ViewModel.
- Each screen composable accepts only its `UiState` type and callbacks — no direct ViewModel access inside composables.
- Use `collectAsStateWithLifecycle()` (not `collectAsState()`) in the screen-level composable that receives the ViewModel.
- Apply `Modifier` as the last positional parameter. Never hard-code pixel sizes; use `dp`/`sp` from Material 3 tokens.
- All interactive composables must have a `contentDescription` or semantic annotation for accessibility.
- Use `LinearProgressIndicator` / `CircularProgressIndicator` from Material 3 for progress and loading states.

### Architecture

- Layer order: UI → ViewModel → Use Case → Repository → Data Source.
- No Android framework imports below the ViewModel layer (pure Kotlin use cases and models).
- Repository exposes `Flow<Result<T>>` or `suspend fun` returning `Result<T>`.
- ViewModel exposes `StateFlow<UiState>`.
- Hilt injects all dependencies; no manual DI wiring.

### Naming

| Element | Convention | Example |
|---|---|---|
| Kotlin source files | `PascalCase.kt` | `CourseProgressViewModel.kt` |
| Packages | lowercase, dots | `com.learntrack.data.repository` |
| JSON data files | `snake_case.json` | `learntrack_topics.json` |
| Documentation | `kebab-case.md` | `learntrack-architecture.md` |
| Kiro skills | `kebab-case/SKILL.md` | `learntrack-json-integrity/SKILL.md` |
| Kiro specs | `kebab-case/{req,design,tasks}.md` | `course-progress-update/requirements.md` |

---

## JSON Validation Requirements

- `learntrack_topics.json` is the single source of truth for course topic data.
- Every topic must have: `id` (non-empty string, unique), `name` (non-empty string), `status` (`"completed"` or `"not_started"` only).
- Any unrecognized status value causes an `Error` UI state — never a silent default.
- Duplicate `id` values cause an `Error` UI state.
- The file must parse as valid JSON with no trailing commas or comments.
- Schema validation runs at app startup via `CourseRepositoryImpl`.
- Pre-push scripts validate JSON syntax using PowerShell `Test-Json` and a count-assertion step.
- Authoritative distribution that pre-push scripts verify: 41 total, 22 completed, 19 not-started.

---

## Security Practices

- **No secrets in source.** API keys, tokens, passwords, and signing credentials must not appear in any committed file. Use `local.properties` (git-ignored) for local overrides. Reference secrets via environment variables in CI.
- **`local.properties` is git-ignored.** Never add it to version control.
- **`google-services.json` is git-ignored.** Add only if Firebase is introduced (not in scope for this phase).
- **Data preservation.** Topic IDs and names in `learntrack_topics.json` must not be silently altered. Any change to existing topic identity fields requires an explicit documented decision.
- **No runtime writes to `assets/`.** The JSON file is read-only at runtime. No code path writes to it.
- **Log guarding.** Use `BuildConfig.DEBUG` to gate verbose log statements. Never log raw JSON content in non-debug builds. Use `Log.e("LearnTrack", ...)` for errors; never expose exception stack traces in UI.
- **Input validation.** All data from the JSON asset is validated before use. Never trust raw parsed values without schema validation.

---

## Testing Requirements

| Test type | Framework | Location | Requirement |
|---|---|---|---|
| Unit — use cases / models | JUnit 4/5 | `app/src/test/` | Must exist; all edge cases covered |
| Unit — repository | JUnit + MockK | `app/src/test/` | Valid JSON, missing file, malformed JSON |
| Unit — ViewModel | JUnit + MockK | `app/src/test/` | State transitions Loading→Success/Error |
| Integration — JSON schema | JUnit | `app/src/test/` | Assert 41 topics, 22 completed, 19 not-started |
| UI — Compose screens | Compose UI Test | `app/src/androidTest/` | Dashboard, topic list |
| Regression — distribution | JUnit | `app/src/test/` | Assert 22/19/41 on every CI run |

- **All unit tests must pass** on `./gradlew testDebugUnitTest` before merging.
- **No hard-coded progress values** (22, 19, 41, 53.7%) in application logic. These values may appear only in test assertions, comments, and documentation.
- Tests must fail loudly with a clear message if the distribution drifts.

---

## Dependency Management Conventions

- All dependency versions are pinned exactly in `gradle/libs.versions.toml`. No `+`, `latest.release`, or open range operators.
- Add new dependencies only when required by an approved feature. Do not add speculative dependencies.
- Prefer well-known, actively maintained Jetpack/Google libraries. Flag any unusual dependency name to the user before adding.
- Keep `debugImplementation` and `testImplementation` dependencies separate from `implementation`.
- Gradle wrapper version is committed in `gradle/wrapper/gradle-wrapper.properties`.

---

## Platform-Specific Best Practices (Kotlin / Android)

- **Minimum SDK:** API 26 (Android 8.0) is the working default until overridden. Target SDK tracks the latest stable API level.
- **Asset loading:** Read `learntrack_topics.json` via `context.assets.open("learntrack_topics.json")`. Always close the stream in a `use {}` block.
- **Background threading:** Asset file reads run on `Dispatchers.IO`. Never open asset streams on the main thread.
- **Configuration changes:** Use `hiltViewModel()` — Hilt-managed ViewModels survive configuration changes automatically.
- **Lifecycle-aware collection:** Use `collectAsStateWithLifecycle()` in composables to avoid collecting from `Flow` when the UI is in the background.
- **Material 3 theming:** Define colors, typography, and shapes in a `MaterialTheme` wrapper. Never hard-code color values outside the theme.
- **Accessibility:** Set `contentDescription` on images, progress indicators, and status badges. Use `Modifier.semantics {}` for custom accessibility labels.
- **Lint:** `./gradlew lintDebug` must report zero errors. Suppress lint warnings only with `@SuppressLint` and a documented reason.
- **Build reproducibility:** `./gradlew assembleDebug` must succeed on a clean checkout. No manual setup steps beyond `local.properties` (SDK path).
