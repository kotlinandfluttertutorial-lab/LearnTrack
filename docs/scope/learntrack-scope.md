# LearnTrack — Scope Document

**Phase:** Scope Definition (Phase Group 1)
**Created:** Phase 1 — Scope + Google Stitch + Figma Handoff
**Status:** Approved for downstream phases

---

## 1. Project Purpose and Summary

LearnTrack is a new Android application for tracking course progress. The product allows learners to view their progress through a structured course, see which topics are completed and which are not yet started, and stay motivated through clear progress visualization.

The immediate deliverable is a fully functional course progress tracking feature for the **AI & Python Technology Course**, reflecting an authoritative topic distribution of 22 completed topics, 19 not-started topics, 41 total topics, and 53.7% overall completion.

The project is in its pre-scaffold state: the git repository exists with a `.gitignore` and stub `README.md`, but no Android source code, build files, or topic data has been committed yet. All application scaffolding, data files, design documents, specifications, and CI/CD infrastructure must be created from scratch.

---

## 2. In Scope

### 2.1 Course Data — `learntrack_topics.json`

- **Create** `learntrack_topics.json` with 41 topics for the AI & Python Technology Course.
- **Status distribution:** 22 topics marked `completed`, 19 topics marked `not_started`.
- **Preserve** topic IDs, topic names, and any unrelated tracking fields once the schema is established. No topic ID or name may be silently altered after first creation.
- **Validate** syntax, schema, topic count, and status distribution before and after every change.
- **Completion percentage** must be calculated from authoritative data: `(completed / total) * 100 = 53.66% ≈ 53.7%`.

### 2.2 Course Progress Screens (UI)

- **Course Dashboard** — entry screen showing overall course progress (53.7%), topic counts, and navigation to the topic list.
- **Progress Summary** — compact or embedded view of the completion state: completed count, not-started count, percentage bar.
- **Topic List** — scrollable list of all 41 topics with per-topic status badge (`Completed` / `Not Started`).
- **Topic Detail** — screen showing a single topic's name, status, and any available metadata.

All screens must use Material 3 components, respect system dark/light mode, and expose loading, empty, error, and invalid-data states.

### 2.3 Validation, Initialization, and Persistence

- JSON asset loading with schema validation on app startup.
- Progress calculation verified at runtime, not hard-coded.
- Graceful degradation if the JSON is malformed or missing (error state shown to user).
- Local persistence strategy consistent with the chosen architecture (assets for read-only, Room for mutable data).

### 2.4 Testing and Regression Protection

- Unit tests for progress calculation logic.
- Unit tests for JSON parsing and schema validation.
- UI tests for course dashboard and topic list screens.
- Regression test to verify topic count and status distribution never drift from authoritative values.

### 2.5 AI-SDLC Documentation and Tool Handoffs

- Scope document (this file).
- Google Stitch UI generation prompts and design review checklist.
- Figma design handoff, design system, and screen inventory.
- Jira epics, stories, subtasks, and traceability CSVs.
- Kiro specification files (requirements, design, tasks).
- Kiro steering files (product, tech, structure).
- Kiro reusable skills.
- Kiro implementation prompt chain.
- Architecture and data-flow documentation.
- Build variant and environment configuration documentation.
- Pre-push quality gate scripts (PowerShell and Bash).
- CI/CD pipeline (GitHub Actions).
- Requirement-to-test traceability matrix.
- Release-readiness report.

---

## 3. Out of Scope

The following items are explicitly excluded unless a future approved requirement introduces them.

| Item | Reason excluded |
|---|---|
| Unrelated course-management functionality | Not requested; no existing functionality to extend |
| Authentication, registration, or login | Not requested; no user account model defined |
| Payment, subscription, or in-app purchase | Not requested |
| Administrative or instructor dashboards | Not requested |
| Multi-course enrollment or course catalog | Not requested for this phase |
| Backend API or remote data synchronization | Not justified by current requirements; asset-based JSON is sufficient |
| Database migration tooling | No existing database to migrate |
| Framework migration (e.g., switching from Compose to Views) | Not requested; Compose is the established org pattern |
| Unnecessary database replacement | Room is added only if mutable persistence is required |
| Unrequested production deployment | Deployment requires explicit approval |
| Destructive changes to existing course records | The JSON file is authoritative; no records may be destroyed without explicit instruction |
| Push notifications or background sync | Not requested |
| Analytics or crash reporting integration | Not requested for this phase |
| Localization or multi-language support | Not requested for this phase |

---

## 4. Discovered Platform and Implications

### Platform: Android (pre-scaffold)

**Evidence from repository analysis:**
- `.gitignore` uses the canonical Android Studio template (`.gradle/`, `build/`, `*.apk`, `*.aab`, `captures/`, etc.).
- Repository is located under `j:\Android\AndroidStudioProjects\`, the default Android Studio project path.
- The `.kotlin/` entry in `.gitignore` implies Kotlin as the intended language.
- The sibling `MediHistory-AI` project on the same machine establishes the organization's Android tech stack.

**Implications for this project:**

| Implication | Detail |
|---|---|
| UI framework | Jetpack Compose (Material 3) — org-standard; no existing Views code to migrate |
| Language | Kotlin only |
| Build system | Gradle with Kotlin DSL (`build.gradle.kts`, `settings.gradle.kts`) |
| Dependency injection | Hilt |
| Async | Kotlin Coroutines + Flow |
| Architecture | MVVM + Repository pattern |
| Local persistence | Room (if mutable) or `assets/` JSON (if read-only) |
| State management | `StateFlow<UiState>` in ViewModels; sealed classes for Loading/Success/Error |
| Test frameworks | JUnit 4/5, MockK, Compose UI Test, Robolectric |
| Design targets | Android phone (portrait primary); Material 3 design language |
| Google Stitch prompt target | Android mobile UI |
| Figma layout grid | 4 dp base grid, 360 dp minimum width, standard Android breakpoints |

**Critical gap:** No Android scaffold exists. The `app/` module, `build.gradle.kts`, `settings.gradle.kts`, `gradlew`, and all source directories must be created before implementation phases can run.

---

## 5. Additional Scope Discovered During Repository Analysis

The following items were identified during repository discovery and are added to scope with low implementation risk.

| Item | Discovery basis | Disposition |
|---|---|---|
| Android project scaffold must be created | No source files found | **In scope — required** |
| `learntrack_topics.json` must be created from scratch | File does not exist | **In scope — required; topic names to be defined** |
| All Kiro configuration must be authored from scratch | No `.kiro/` directory | **In scope — required** |
| GitHub Actions CI/CD pipeline must be created | No `.github/workflows/` | **In scope — required** |
| Pre-push quality gate scripts must be created | No `scripts/` directory | **In scope — required** |
| MCP configuration must be authored | No `.kiro/settings/mcp.json` | **In scope — required** |
| Figma and Google Stitch used via manual prompts | MCP availability not confirmed | **In scope — prompts only; no live tool access assumed** |

---

## 6. Acceptance Criteria — Course Progress Update Feature

The following criteria must all pass before the feature is considered complete.

### AC-1: Data Integrity
- `learntrack_topics.json` contains exactly 41 topics.
- Exactly 22 topics have status `completed`.
- Exactly 19 topics have status `not_started`.
- No topic has an undefined, null, or unexpected status value.
- Every topic has a unique, non-empty `id` field.
- Every topic has a non-empty `name` field.
- The JSON file parses without error against the defined schema.

### AC-2: Progress Calculation
- The app calculates completion percentage from live data: `completed / total * 100`.
- The displayed percentage equals 53.7% (rounded to one decimal place) when data matches the authoritative distribution.
- Progress recalculates correctly if the underlying data is updated.
- Hard-coded progress values are not acceptable.

### AC-3: Course Dashboard
- The dashboard loads without crashing.
- Overall completion percentage (53.7%) is displayed.
- Completed topic count (22) and total topic count (41) are displayed.
- A progress bar or equivalent visualization matches the percentage.
- A loading state is shown while data loads.
- An error state is shown when data cannot be loaded.

### AC-4: Topic List
- All 41 topics are displayed in the list.
- Each topic shows its name and status badge.
- Completed topics show a visually distinct `Completed` indicator.
- Not-started topics show a visually distinct `Not Started` indicator.
- The list is scrollable.
- An empty state is displayed if no topics are returned.

### AC-5: Topic Detail
- Tapping a topic row opens the detail screen.
- The detail screen shows the topic name and status.
- Back navigation returns to the topic list.

### AC-6: Accessibility
- All interactive elements have content descriptions.
- Text contrast meets WCAG 2.1 AA minimum (4.5:1 for normal text).
- The UI is navigable using TalkBack.
- Progress percentage is communicated as text (not just graphical).

### AC-7: Regression Protection
- No existing documentation files or committed data are removed or altered.
- The feature does not affect any functionality outside the course progress screens.
- Automated tests detect any drift in topic count or status distribution.

### AC-8: Build and Test
- `./gradlew assembleDebug` completes without errors.
- `./gradlew testDebugUnitTest` passes all tests.
- `./gradlew lintDebug` reports no errors (warnings are acceptable with documented exceptions).

---

## 7. Constraints

| Constraint | Detail |
|---|---|
| No live Figma MCP access confirmed | Design files must be set up manually using the handoff document |
| No live Google Stitch access | Stitch prompts must be executed manually |
| No existing Android code | Implementation must create the scaffold; "preserve existing architecture" applies to conventions, not files |
| No existing `learntrack_topics.json` | Topic names must be defined; authoritative status distribution is given (22/19/41) |
| No Jira instance access from this agent | Jira deliverables are CSV files only; no issues are created live |
| Topic names not documented anywhere | Must be proposed or sourced; proposal requires confirmation before the JSON file is finalized |
| Secrets must not be committed | `local.properties`, API keys, tokens out of source control |
| No production deployment without explicit approval | Quality gates run locally and in CI; deployment is a separate approval gate |

---

## 8. Assumptions

| Assumption | Justification |
|---|---|
| Kotlin + Jetpack Compose + MVVM is the correct stack | Established pattern in sibling MediHistory-AI project; org standard |
| `learntrack_topics.json` is read-only (not user-editable at runtime) | Master prompt describes "updating" it as a development task, not an in-app edit |
| Assets directory (`app/src/main/assets/`) is the correct location for the JSON | Standard Android pattern for bundled JSON data |
| Single-module app structure (`app/`) is sufficient | No microservice or multi-module requirement specified |
| Material 3 is the target design language | Org pattern; Compose default |
| English is the only required language | No localization requirement stated |
| Minimum Android SDK version is TBD | Will be confirmed when scaffold is created; API 26+ is reasonable for Compose |
| 53.7% is the permanent authoritative completion state for this phase | Not expected to change during the current AI-SDLC workflow execution |

---

## 9. Open Decisions

| Decision | Owner | Blocking? |
|---|---|---|
| Exact topic names for all 41 topics in the AI & Python Technology Course | User / product | Yes — blocks `learntrack_topics.json` creation |
| Minimum Android SDK version | Architect | Low — scaffolding default (API 26) is acceptable until overridden |
| Whether topic status is mutable at runtime (Room vs assets) | Architect | Medium — affects persistence design |
| Color scheme / brand palette | Designer | Low — proposed palette in design system document; override anytime |
