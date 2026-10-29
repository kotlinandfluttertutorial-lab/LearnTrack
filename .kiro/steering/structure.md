# LearnTrack — Structure Steering

## Repository Organization

LearnTrack is an Android application project. The repository is in a pre-scaffold state: no `app/` module, build files, or source code exist yet. The structure below documents (a) what currently exists and (b) the target layout that all implementation phases must follow.

### Current State (post-documentation phases)

```
j:\Android\AndroidStudioProjects\Kiro\LearnTrack\
├── .git/
├── .gitignore                          Android Studio template
├── .kiro/
│   ├── specs/
│   │   └── course-progress-update/
│   │       ├── requirements.md
│   │       ├── design.md
│   │       └── tasks.md
│   ├── steering/
│   │   ├── product.md
│   │   ├── tech.md
│   │   └── structure.md               (this file)
│   └── skills/
│       ├── learntrack-repository-analysis/SKILL.md
│       ├── learntrack-json-integrity/SKILL.md
│       ├── learntrack-progress-validation/SKILL.md
│       ├── learntrack-ui-implementation/SKILL.md
│       ├── learntrack-testing-quality-gates/SKILL.md
│       └── learntrack-jira-traceability/SKILL.md
├── docs/
│   ├── learntrack-ai-sdlc-master-prompt.md
│   ├── learntrack-deliverables-checklist.md
│   ├── discovery/
│   │   └── learntrack-discovery-report.md
│   ├── scope/
│   │   └── learntrack-scope.md
│   ├── architecture/
│   │   ├── learntrack-architecture.md
│   │   ├── learntrack-data-flow.md
│   │   └── learntrack-environments.md
│   ├── design/
│   │   ├── google-stitch/
│   │   │   ├── learntrack-stitch-master-prompt.md
│   │   │   ├── course-progress-prompt.md
│   │   │   └── design-review-checklist.md
│   │   └── figma/
│   │       ├── learntrack-figma-handoff.md
│   │       ├── learntrack-design-system.md
│   │       └── learntrack-screen-inventory.md
│   ├── jira/
│   │   ├── learntrack-epics.csv
│   │   ├── learntrack-stories.csv
│   │   ├── learntrack-subtasks.csv
│   │   └── learntrack-traceability.csv
│   ├── kiro-prompts/
│   │   ├── 01-repository-discovery.md
│   │   ├── 02-requirements-and-scope.md
│   │   ├── 03-google-stitch-ui-design.md
│   │   ├── 04-figma-design-handoff.md
│   │   ├── 05-architecture-and-data-design.md
│   │   ├── 06-jira-work-breakdown.md
│   │   ├── 07-kiro-specs-and-skills.md
│   │   ├── 08-topic-json-update.md
│   │   ├── 09-progress-ui-implementation.md
│   │   ├── 10-automated-testing.md
│   │   ├── 11-build-and-quality-gates.md
│   │   └── 12-release-readiness.md
│   ├── build-variants/
│   │   ├── learntrack-build-variants.md
│   │   └── learntrack-environment-configuration.md
│   ├── quality-gates/
│   │   └── learntrack-quality-gates.md
│   ├── setup/
│   │   └── kiro-mcp-setup.md
│   └── traceability/
│       └── learntrack-requirement-traceability.md
├── README.md
└── scripts/
    ├── check-before-push.ps1
    └── check-before-push.sh
```

### Target State (after scaffold + implementation phases)

> ✅ **Implementation complete as of October 29, 2026.** The layout below reflects the **actual implemented structure**, including packages added during implementation that were not in the original spec.

```
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── assets/
│   │   │   │   └── learntrack_topics.json
│   │   │   ├── java/com/learntrack/
│   │   │   │   ├── MainActivity.kt
│   │   │   │   ├── LearnTrackApplication.kt
│   │   │   │   ├── data/
│   │   │   │   │   ├── model/
│   │   │   │   │   │   ├── Topic.kt
│   │   │   │   │   │   ├── TopicStatus.kt
│   │   │   │   │   │   └── TopicContainer.kt         ← JSON wrapper object
│   │   │   │   │   ├── repository/
│   │   │   │   │   │   ├── CourseRepository.kt
│   │   │   │   │   │   └── CourseRepositoryImpl.kt
│   │   │   │   │   └── validation/
│   │   │   │   │       ├── TopicSchemaValidator.kt
│   │   │   │   │       └── ValidationException.kt
│   │   │   │   ├── domain/
│   │   │   │   │   ├── model/
│   │   │   │   │   │   └── CourseProgress.kt
│   │   │   │   │   └── usecase/
│   │   │   │   │       ├── GetCourseProgressUseCase.kt
│   │   │   │   │       └── CalculateProgressUseCase.kt
│   │   │   │   ├── ui/
│   │   │   │   │   ├── progress/
│   │   │   │   │   │   ├── CourseProgressScreen.kt
│   │   │   │   │   │   ├── CourseProgressViewModel.kt
│   │   │   │   │   │   └── CourseUiState.kt
│   │   │   │   │   ├── topics/
│   │   │   │   │   │   ├── TopicListScreen.kt
│   │   │   │   │   │   ├── TopicDetailScreen.kt
│   │   │   │   │   │   └── StatusBadge.kt            ← shared status component
│   │   │   │   │   └── theme/                        ← actual package: com.learntrack.ui.theme
│   │   │   │   │       ├── Color.kt
│   │   │   │   │       ├── Theme.kt
│   │   │   │   │       ├── Type.kt
│   │   │   │   │       └── Shape.kt
│   │   │   │   ├── navigation/                       ← added during implementation
│   │   │   │   │   ├── LearnTrackNavHost.kt
│   │   │   │   │   └── Screen.kt
│   │   │   │   └── di/
│   │   │   │       ├── AppModule.kt
│   │   │   │       └── DataModule.kt
│   │   │   └── AndroidManifest.xml
│   │   ├── test/java/com/learntrack/
│   │   │   ├── domain/
│   │   │   │   └── ProgressCalculatorTest.kt
│   │   │   ├── data/repository/
│   │   │   │   └── CourseRepositoryImplTest.kt
│   │   │   └── regression/
│   │   │       └── TopicDistributionRegressionTest.kt
│   │   └── androidTest/java/com/learntrack/
│   │       └── ui/
│   │           ├── CourseProgressScreenTest.kt
│   │           └── TopicListScreenTest.kt
│   └── build.gradle.kts
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── gradle/
│   ├── libs.versions.toml
│   └── wrapper/
│       └── gradle-wrapper.properties
├── gradlew
└── gradlew.bat
```

---

## Naming Conventions

### Files

| Type | Convention | Example |
|---|---|---|
| Kotlin source | `PascalCase.kt` | `CourseProgressViewModel.kt` |
| JSON data | `snake_case.json` | `learntrack_topics.json` |
| Markdown docs | `kebab-case.md` | `learntrack-architecture.md` |
| Kiro skill dirs | `kebab-case/` | `learntrack-json-integrity/` |
| Kiro skill files | `SKILL.md` (uppercase) | `SKILL.md` |
| Kiro spec files | lowercase | `requirements.md`, `design.md`, `tasks.md` |
| Kiro steering files | `kebab-case.md` | `product.md`, `tech.md` |
| Scripts | `kebab-case.ps1` / `kebab-case.sh` | `check-before-push.ps1` |
| CSV exports | `kebab-case.csv` | `learntrack-stories.csv` |

### Kotlin Identifiers

| Element | Convention | Example |
|---|---|---|
| Classes / objects | `PascalCase` | `CourseProgressViewModel` |
| Interfaces | `PascalCase` | `CourseRepository` |
| Functions / properties | `camelCase` | `getTopics()`, `completionPercentage` |
| Constants (companion/top-level) | `UPPER_SNAKE_CASE` | `TAG` |
| Packages | `lowercase.dots` | `com.learntrack.data.model` |
| Composables | `PascalCase` function | `CourseProgressScreen()` |

### Package Structure

Root package: `com.learntrack`

| Sub-package | Contents |
|---|---|
| `com.learntrack.data.model` | `Topic`, `TopicStatus`, `TopicContainer` data classes |
| `com.learntrack.data.repository` | Repository interface and implementation |
| `com.learntrack.data.validation` | `TopicSchemaValidator`, `ValidationException` |
| `com.learntrack.domain.model` | `CourseProgress` domain model |
| `com.learntrack.domain.usecase` | `CalculateProgressUseCase`, `GetCourseProgressUseCase` |
| `com.learntrack.ui.progress` | Dashboard screen, ViewModel, UiState |
| `com.learntrack.ui.topics` | Topic list, detail screens, `StatusBadge` |
| `com.learntrack.ui.theme` | Material 3 theme, colors, typography, shapes |
| `com.learntrack.navigation` | `LearnTrackNavHost`, `Screen` route definitions |
| `com.learntrack.di` | Hilt modules (`AppModule`, `DataModule`) |

---

## Module Boundaries

| Layer | Allowed dependencies | Prohibited dependencies |
|---|---|---|
| `ui.*` | ViewModel, UiState, domain models | Repository, data source, Android context (except via Hilt) |
| `domain.*` | Pure Kotlin; no Android imports | Android framework, repository impl |
| `data.repository` | Android `AssetManager`, JSON parser | UI layer |
| `data.model` | Pure Kotlin | All other layers |
| `di.*` | All layers (wiring only) | Business logic |

Composables must not import from `data.*` packages directly. All data access flows through the ViewModel.

---

## Artifact Locations

| Artifact | Location |
|---|---|
| Course topic data (JSON) | `app/src/main/assets/learntrack_topics.json` |
| Documentation root | `docs/` |
| Discovery report | `docs/discovery/learntrack-discovery-report.md` |
| Scope document | `docs/scope/learntrack-scope.md` |
| Architecture docs | `docs/architecture/` |
| Design prompts (Stitch) | `docs/design/google-stitch/` |
| Design handoff (Figma) | `docs/design/figma/` |
| Jira CSV exports | `docs/jira/` |
| Kiro implementation prompts | `docs/kiro-prompts/` |
| Build variant docs | `docs/build-variants/` |
| Quality gate docs | `docs/quality-gates/` |
| MCP setup doc | `docs/setup/kiro-mcp-setup.md` |
| Traceability matrix | `docs/traceability/` |
| Deliverables checklist | `docs/learntrack-deliverables-checklist.md` |
| Pre-push scripts | `scripts/` |
| GitHub Actions CI | `.github/workflows/` |
| Kiro specs | `.kiro/specs/course-progress-update/` |
| Kiro skills | `.kiro/skills/learntrack-*/SKILL.md` |
| Kiro steering | `.kiro/steering/` |
| MCP config | `.kiro/settings/mcp.json` |

---

## Where Kiro Specs, Skills, and Steering Live

```
.kiro/
├── specs/
│   └── course-progress-update/
│       ├── requirements.md     Functional + non-functional requirements, AC, edge cases
│       ├── design.md           Architecture, data flow, UI changes, testing strategy
│       └── tasks.md            Independently verifiable implementation steps
├── steering/
│   ├── product.md              Purpose, scope, non-goals, UX principles
│   ├── tech.md                 Stack, coding standards, security, testing
│   └── structure.md            Repo layout, naming, module boundaries, artifact locations
└── skills/
    ├── learntrack-repository-analysis/SKILL.md
    ├── learntrack-json-integrity/SKILL.md
    ├── learntrack-progress-validation/SKILL.md
    ├── learntrack-ui-implementation/SKILL.md
    ├── learntrack-testing-quality-gates/SKILL.md
    └── learntrack-jira-traceability/SKILL.md
```

Steering files are project-wide and concise. Feature-specific rules belong in the relevant spec or skill, not in steering.

---

## Prohibited Patterns

| Pattern | Reason |
|---|---|
| Java source files in `app/src/` | Kotlin-only codebase |
| Hard-coded progress values (22, 19, 41, 53.7%) in application logic | Violates NFR-005; values must come from data |
| Mutable `var` for ViewModel-owned data | Use `StateFlow`; composables are read-only observers |
| Android framework imports in `domain.*` | Breaks layer isolation; use cases must be pure Kotlin |
| Composables importing from `data.*` directly | All data access flows through ViewModel |
| Open version ranges in `libs.versions.toml` (`+`, `latest.release`) | Breaks reproducible builds |
| Secrets or credentials in any committed file | Security violation |
| Writing to `app/src/main/assets/` at runtime | Assets are read-only; no code path may write to them |
| Skipping `--no-verify` on git hooks | Only with explicit user permission |
| Silent JSON parsing without schema validation | All parsed topics must pass validation before use |
| Creating files outside the documented artifact locations without approval | Maintains navigable structure |
| Modifying files outside the course-progress screens during implementation | Scope boundary |
