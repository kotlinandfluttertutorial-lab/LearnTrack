# LearnTrack — Repository Discovery Report

**Generated:** Phase 1 — Repository Discovery  
**Workspace root:** `j:\Android\AndroidStudioProjects\Kiro\LearnTrack`  
**Git remote:** `https://github.com/kotlinandfluttertutorial-lab/LearnTrack.git`  
**Branch:** `main`  
**Last commit:** `b8a0991` — "Initial commit" (Oct 28 2026, by kotlinandfluttertutorial-lab)

---

## 1. Platform Determination

**Platform: Android application (not yet scaffolded)**

Evidence:
- `.gitignore` contains Android-specific patterns: `.gradle/`, `build/`, `*.apk`, `*.aab`, `captures/`, `.externalNativeBuild/`, `.cxx/`, `*.iml`, `*.hprof`, `local.properties`, `google-services.json`, `output-metadata.json`. This is the canonical Android Studio `.gitignore` template.
- The repository lives under `j:\Android\AndroidStudioProjects\`, the default Android Studio project location on this machine.
- The git remote is under the `kotlinandfluttertutorial-lab` GitHub organization, which owns the MediHistory-AI Android project on the same machine.
- The `README.md` contains only the project name (`# LearnTrack`) — no description yet.
- **No Android source code exists yet.** The repository contains only the `.gitignore`, `README.md`, and a `docs/` directory added after the initial commit. No `build.gradle.kts`, `settings.gradle.kts`, `app/` module, or any source files are present.

**Conclusion:** LearnTrack is an Android application project in its pre-scaffold state. The `.gitignore` establishes the intended platform but no source files, build files, or application code have been committed.

---

## 2. Technology Stack

| Dimension | Determined Value | Confidence | Basis |
|---|---|---|---|
| Platform | Android | High | `.gitignore` patterns, directory location, GitHub org |
| Language | Not yet established (Kotlin expected, per org pattern) | Inferred | `.gitignore` includes `.kotlin/` — a Kotlin compiler cache directory |
| Build system | Not yet established (Gradle Kotlin DSL expected) | Inferred | `.gitignore` excludes `.gradle/` and `build/` |
| UI framework | Not yet established | Unknown | No source files |
| Architecture | Not yet established | Unknown | No source files |
| State management | Not yet established | Unknown | No source files |
| Persistence | Not yet established | Unknown | No source files |
| Dependencies | None declared | Certain | No `libs.versions.toml`, no `build.gradle.kts` |
| Test framework | None declared | Certain | No test directories |
| CI/CD | None configured | Certain | No `.github/`, no `Jenkinsfile`, no other CI files |

**Note on inferences:** The `.kotlin/` entry in `.gitignore` strongly implies Kotlin as the intended language. The presence of `*.iml` (IntelliJ Module files) and `.idea/` confirms Android Studio as the IDE. All other stack details are inferences from the `.gitignore` template — nothing has been verified from actual source code.

---

## 3. Directory Tree

Full listing of all files and directories found (excluding `.git` internals):

```
j:\Android\AndroidStudioProjects\Kiro\LearnTrack\
├── .git/                          (git repository metadata — not inspected in detail)
├── docs/
│   ├── learntrack-ai-sdlc-master-prompt.md   (AI-SDLC master prompt — 27 sections)
│   └── discovery/                 (created by this step)
│       └── learntrack-discovery-report.md    (this file)
├── .gitignore                     (Android Studio template gitignore)
└── README.md                      (stub — contains only "# LearnTrack")
```

**Absent directories and files that downstream phases will need to create:**

- `app/` — Android application module
- `build.gradle.kts` — root Gradle build file
- `settings.gradle.kts` — Gradle settings
- `gradle/libs.versions.toml` — version catalog
- `gradlew` / `gradlew.bat` — Gradle wrapper scripts
- `gradle/wrapper/gradle-wrapper.properties`
- `learntrack_topics.json` — course data (referenced in master prompt)
- `.kiro/` — Kiro configuration directory
- `scripts/` — pre-push quality gate scripts
- `.github/workflows/` — CI/CD pipeline

---

## 4. Existing Kiro Configuration

**No `.kiro/` directory exists.** All Kiro configuration must be created from scratch.

| Path | Status |
|---|---|
| `.kiro/steering/product.md` | Does not exist |
| `.kiro/steering/tech.md` | Does not exist |
| `.kiro/steering/structure.md` | Does not exist |
| `.kiro/specs/course-progress-update/requirements.md` | Does not exist |
| `.kiro/specs/course-progress-update/design.md` | Does not exist |
| `.kiro/specs/course-progress-update/tasks.md` | Does not exist |
| `.kiro/skills/` (any) | Does not exist |
| `.kiro/settings/mcp.json` | Does not exist |
| `.kiro/hooks/` | Does not exist |
| `.kiro/agents/` | Does not exist |

**Reference pattern available:** The sibling project `MediHistory-AI` (same machine, same GitHub org) has a fully populated `.kiro/` directory with steering files (`product.md`, `tech.md`, `structure.md`, `testing.md`, `engineering.md`, `medical-safety.md`, `privacy-security.md`), specs (foundation, medihistory-mvp, android-ui, and several domain specs each with `requirements.md`, `design.md`, `tasks.md`), and skills (android-development, backend-development, testing-and-quality, and domain-specific skills). The SKILL.md format used there is the confirmed format for this Kiro installation.

---

## 5. learntrack_topics.json Status

**Not found.** No `learntrack_topics.json` file exists anywhere in the repository.

The master prompt (`docs/learntrack-ai-sdlc-master-prompt.md`) references this file extensively:

- It should contain course topics for an "AI & Python Technology Course."
- The master prompt specifies the authoritative distribution: **22 completed**, **19 not-started**, **41 total**, **53.7% completion**.
- Relevant fields referenced: topic ID, name, status, and "unrelated tracking fields" (exact schema unknown — the file does not yet exist to inspect).
- The master prompt instructs that this file must be updated (not replaced) with correct status assignments.

**Decision needed by downstream phases:** The `learntrack_topics.json` file must be created with the 41 topics. The exact topic names, IDs, and any additional fields are not documented anywhere in the repository. The master prompt specifies only the status distribution (22 completed, 19 not-started) and the course name. The actual topic list must be sourced or defined before the JSON update phase can proceed.

---

## 6. CI/CD Status

**No CI/CD configuration exists.**

Checked locations:
- `.github/workflows/` — directory does not exist
- `.gitlab-ci.yml` — does not exist
- `Jenkinsfile` — does not exist
- `azure-pipelines.yml` — does not exist
- `bitrise.yml` — does not exist
- `fastlane/` — does not exist

No quality gate scripts exist:
- `scripts/check-before-push.ps1` — does not exist
- `scripts/check-before-push.sh` — does not exist
- `check-before-push.ps1` (root) — does not exist
- `check-before-push.sh` (root) — does not exist

**Reference pattern:** The sibling `MediHistory-AI` project has `check-before-push.ps1` and `check-before-push.sh` at its root (not in `scripts/`), and a `.github/` directory. That project's scripts and workflow structure can serve as a reference template.

---

## 7. Scripts Status

**No `scripts/` directory exists.** No scripts of any kind are present in the repository.

---

## 8. Gaps and Decisions Needed

The following items are ambiguous or missing and must be resolved before or during downstream phases.

### Gap 1 — No Android project scaffold
**Severity: Blocking for implementation phases**  
The repository contains no `build.gradle.kts`, `settings.gradle.kts`, `app/` module, or any source code. An Android project scaffold must be created before any implementation can proceed. The master prompt says "preserve existing architecture" but there is no existing architecture to preserve.  
**Decision needed:** Create a new Android application scaffold using Kotlin, Jetpack Compose, and MVVM (matching the org's established pattern from MediHistory-AI). Confirm before creating.

### Gap 2 — learntrack_topics.json does not exist
**Severity: Blocking for data phases**  
The file is central to the entire feature (updating topic statuses, calculating progress). It does not exist. The topic names for the "AI & Python Technology Course" are not documented in the repository.  
**Decision needed:** Define the 41 topic names and IDs, then create the initial JSON. The master prompt specifies 22 completed and 19 not-started — the assignment of which topics belong to each status needs to be specified or inferred.

### Gap 3 — No Kiro configuration exists
**Severity: Non-blocking (must be created)**  
All steering files, specs, and skills must be authored from scratch. The MediHistory-AI SKILL.md format is the confirmed reference.

### Gap 4 — No CI/CD pipeline
**Severity: Non-blocking (must be created)**  
GitHub Actions is the obvious choice given the `github.com` remote, but no workflow exists. Must be created per Phase 24 of the master prompt.

### Gap 5 — Platform not confirmed by source code
**Severity: Low (high-confidence inference)**  
The platform is determined from `.gitignore` patterns, not from actual source files. This is a near-certain Android inference but has not been confirmed by inspecting a `build.gradle.kts` or `AndroidManifest.xml`.

### Gap 6 — Exact topic list for the AI & Python Technology Course
**Severity: Blocking for topics JSON creation**  
The master prompt names the course "AI & Python Technology Course" with 41 topics (22 completed, 19 not-started). The actual topic names are not present in any file in this repository. This data must be provided by the user or sourced from another authoritative location before the JSON can be created.

### Gap 7 — MCP configuration
**Severity: Non-blocking**  
`.kiro/settings/mcp.json` does not exist. The master prompt instructs that Figma MCP and GitHub MCP should be configured if available. No MCP servers are currently configured for this project.

---

## 9. Recommended Conventions

Based on the confirmed platform (Android, `.gitignore` template) and the established pattern from the sibling MediHistory-AI project on this machine, the following conventions apply.

### Build System
- **Gradle with Kotlin DSL** (`build.gradle.kts`, `settings.gradle.kts`)
- **Version catalog** at `gradle/libs.versions.toml` — all dependency versions pinned exactly (no `+` or range operators)
- Standard single-module structure: root project + `:app` module
- `gradlew` / `gradlew.bat` wrapper scripts committed to repo

### Language and Framework
- **Kotlin only** — no Java source files
- **Jetpack Compose** for all UI (Material 3)
- **MVVM + Repository pattern** — ViewModels, Repository interfaces, Use Cases for business logic
- **Hilt** for dependency injection
- **Kotlin Coroutines + Flow** for async and reactive data

### Persistence
- **Room** for structured local data (if a database is needed)
- **SharedPreferences** (or DataStore) for simple key-value settings
- JSON assets (`assets/` directory) for bundled static data like `learntrack_topics.json`
- If `learntrack_topics.json` is mutable at runtime, Room is the appropriate storage; if read-only or updated via app updates, `assets/` is sufficient

### State Management
- `StateFlow<UiState>` exposed from every ViewModel
- `UiState` as a sealed class with `Loading`, `Success`, `Error` variants
- Composables are stateless — collect state from ViewModel

### Testing
- **JUnit 4/5** for unit tests
- **MockK** or **Mockito-Kotlin** for mocking
- **Compose UI testing** (`androidx.compose.ui:ui-test-junit4`) for UI tests
- **Robolectric** for unit tests requiring Android context
- Test files in `app/src/test/` (unit) and `app/src/androidTest/` (instrumented)

### Quality Gates (to be created)
- Gradle build: `./gradlew assembleDebug`
- Unit tests: `./gradlew testDebugUnitTest`
- Lint: `./gradlew lintDebug`
- Instrumented tests: `./gradlew connectedDebugAndroidTest` (requires device/emulator)
- JSON validation: PowerShell `Test-Json` or a custom script

### File and Directory Naming
- Kotlin source files: `PascalCase.kt`
- Package: `com.learntrack` (to be confirmed when scaffold is created)
- JSON data files: `snake_case.json`
- Documentation: `kebab-case.md`
- Kiro skills: `kebab-case/SKILL.md`
- Kiro specs: `kebab-case/{requirements,design,tasks}.md`
- Kiro steering: `kebab-case.md`

### Secrets and Configuration
- `local.properties` for SDK path and any local overrides (git-ignored)
- No API keys, tokens, or secrets in source files
- Build config values from `local.properties` injected via `BuildConfig`

---

## Summary

LearnTrack is a new Android application project at its earliest possible stage — a git repository with only a `.gitignore` and stub `README.md`. No source code, no build system, no topics data, and no Kiro configuration exist. The `docs/learntrack-ai-sdlc-master-prompt.md` file defines an ambitious 12-phase AI-SDLC workflow for building a course progress tracking app with 41 topics (22 completed, 19 not-started, 53.7% completion rate).

The two critical blockers before implementation can begin are:
1. **Android project scaffold** must be created (no existing code to preserve).
2. **learntrack_topics.json topic list** must be defined (41 topic names are not documented anywhere in the repository).

All other phases (Kiro specs, steering, skills, CI/CD, quality gates, design prompts) can proceed in parallel with or after these two items are resolved.
