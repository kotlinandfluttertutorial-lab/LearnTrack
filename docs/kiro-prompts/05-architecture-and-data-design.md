# Prompt 05 — Architecture and Data Design

## Prerequisites

- Prompt 01 complete: `docs/discovery/learntrack-discovery-report.md` exists.
- Prompt 02 complete: `.kiro/specs/course-progress-update/requirements.md` exists.
- Prompt 04 complete: `docs/design/figma/learntrack-screen-inventory.md` exists.

## Specific Task

Create three architecture documents. Read the discovery report and requirements before writing anything. Do not invent architecture that contradicts the discovered stack; use the confirmed platform (Android / Kotlin / Compose / MVVM) or whatever the discovery report determined.

### Files to Create

**1. `docs/architecture/learntrack-architecture.md`**

Document the full application architecture:
- Application layers and responsibilities (UI → ViewModel → Use Case → Repository → Data Source)
- Layer dependency diagram (Mermaid or ASCII)
- Topic data model (`Topic`, `TopicStatus` enum, `CourseProgress`)
- Status representation in JSON vs. Kotlin enum
- Authoritative distribution table (41/22/19/53.7%)
- Data source of truth (`learntrack_topics.json` location and rationale)
- JSON import and validation workflow (flowchart)
- Validation rules table (id, name, status, uniqueness, counts)
- Progress calculation formula and code example
- UI state management: `CourseUiState` sealed class, `StateFlow` pattern
- Error handling categories and user-visible messages
- Logging approach (`android.util.Log` with `BuildConfig.DEBUG` guard)
- Testing boundaries and test type inventory
- Key file paths (target state)

**2. `docs/architecture/learntrack-data-flow.md`**

Document the runtime data flow:
- Cold start sequence: app launch → asset load → parse → validate → calculate → emit state
- Success path: valid JSON → `List<Topic>` → `CourseProgress` → `CourseUiState.Success`
- Error paths: missing file, malformed JSON, schema violation, duplicate IDs
- Empty path: valid JSON, zero topics → `CourseUiState.Empty`
- Navigation flow: Dashboard → Topic List → Topic Detail → back
- State transitions diagram for `CourseUiState`
- Background threading model (which operations run on `Dispatchers.IO`)

**3. `docs/architecture/learntrack-environments.md`**

Document the build and environment configuration:
- Build types: `debug` and `release`
- No product flavors required for this phase (single variant is sufficient)
- Local development setup: `local.properties`, SDK path, no secrets in source
- Quality gate commands: `./gradlew assembleDebug`, `testDebugUnitTest`, `lintDebug`
- CI environment: GitHub Actions (to be configured); environment variables for signing (if needed for release)
- No staging or production server — app is entirely local/asset-based
- Minimum SDK, target SDK, compile SDK recommendations

## Allowed Scope

- Read `docs/discovery/learntrack-discovery-report.md`, `.kiro/specs/course-progress-update/requirements.md`, and `docs/design/figma/learntrack-screen-inventory.md`.
- Write to `docs/architecture/` (three files only).
- Do not modify source code, JSON data, or Kiro specs.

## Required Outputs

- `docs/architecture/learntrack-architecture.md`
- `docs/architecture/learntrack-data-flow.md`
- `docs/architecture/learntrack-environments.md`

## Acceptance Criteria

- [ ] All three files exist under `docs/architecture/`.
- [ ] Architecture doc includes the full layer diagram and all six layer responsibilities.
- [ ] Architecture doc specifies the `CourseUiState` sealed class with all four states.
- [ ] Data flow doc covers all four paths: Success, Error (3 variants), and Empty.
- [ ] Architecture doc confirms progress is calculated with `(completed / total) * 100f`, never hard-coded.
- [ ] Environments doc lists all three Gradle quality gate commands.
- [ ] All documents are consistent with the confirmed technology stack from the discovery report.

## Stop Conditions

- Stop if the discovery report is absent — architecture must be grounded in confirmed platform facts.
- Do not introduce a backend, database, or network layer unless a specific requirement justifies it.
- Do not begin Jira work — that is Prompt 06.
