# Course Progress Update — Requirements

**Spec:** course-progress-update
**Phase:** Phase Group 2 — Architecture + Kiro Specs
**Status:** Draft — pending scaffold implementation
**Jira Epic:** LEARNTRACK-EPIC-COURSE-PROGRESS (CSV only; not a live Jira key)

---

## 1. Introduction

This document defines functional requirements, non-functional requirements, acceptance criteria, edge cases, and data-preservation constraints for the **Course Progress Update** feature of the LearnTrack Android application.

The feature delivers a course progress tracker for the **AI & Python Technology Course** with an authoritative topic distribution of 22 completed topics, 19 not-started topics, 41 total topics, and 53.7% overall completion.

The app is in a pre-scaffold state. All implementation, build files, and source code must be created. This requirements document governs what is built.

---

## 2. Functional Requirements

### FR-001 — Create learntrack_topics.json

**Summary:** Create the authoritative course data file with the specified topic distribution.

**Description:** The file `app/src/main/assets/learntrack_topics.json` must be created from scratch. It must contain exactly 41 topic objects for the AI & Python Technology Course. Each topic has a unique non-empty `id`, a non-empty `name`, and a `status` of either `"completed"` or `"not_started"`.

**Acceptance Criteria:**
- File exists at `app/src/main/assets/learntrack_topics.json`
- File contains exactly 41 topic objects in a top-level `topics` array
- Every topic has a non-empty unique `id` field
- Every topic has a non-empty `name` field
- Every topic `status` field is either `"completed"` or `"not_started"` — no other values permitted
- File parses as valid JSON without errors
- No topic id or name is silently altered after first creation

**Jira:** LT-005
**Design reference:** `docs/architecture/learntrack-data-flow.md` § 8

---

### FR-002 — Authoritative completed count is 22

**Summary:** Exactly 22 topics must be marked `"completed"`.

**Acceptance Criteria:**
- Exactly 22 topics in `learntrack_topics.json` have `"status": "completed"`
- Automated regression test asserts this count on every CI run
- Hard-coded count of 22 is never used in application logic — count is derived from the data

**Jira:** LT-005
**Design reference:** `docs/architecture/learntrack-data-flow.md` § 5

---

### FR-003 — Authoritative not-started count is 19

**Summary:** Exactly 19 topics must be marked `"not_started"`.

**Acceptance Criteria:**
- Exactly 19 topics in `learntrack_topics.json` have `"status": "not_started"`
- Automated regression test asserts this count on every CI run

**Jira:** LT-005
**Design reference:** `docs/architecture/learntrack-data-flow.md` § 5

---

### FR-004 — Calculate completion percentage from live data

**Summary:** The completion percentage is always calculated from the topic list — never hard-coded.

**Description:** The formula is `(completed / total) * 100`. The result is displayed rounded to one decimal place. With 22 completed out of 41 total the result is 53.658...% which displays as 53.7%.

**Acceptance Criteria:**
- `CalculateProgressUseCase` accepts `List<Topic>` and returns `CourseProgress`
- `CourseProgress` includes `total`, `completed`, `notStarted`, `completionPercentage`
- Percentage is a `Float` computed at runtime, not a constant
- Displayed using `"%.1f%%".format(completionPercentage)`
- Test vector: 22 completed / 41 total → 53.7%

**Jira:** LT-006
**Design reference:** `docs/architecture/learntrack-data-flow.md` § 5

---

### FR-005 — Display course dashboard with progress percentage

**Summary:** The course dashboard screen must show the completion percentage prominently.

**Acceptance Criteria:**
- `CourseProgressScreen` renders the completion percentage as text (e.g., "53.7%")
- A progress bar or equivalent visualization reflects the same percentage
- The percentage text is accessible (contentDescription or semantic node)

**Jira:** LT-007
**Design reference:** `docs/design/figma/learntrack-screen-inventory.md`

---

### FR-006 — Display completed count and total on dashboard

**Summary:** The dashboard must show both the completed topic count (22) and total topic count (41).

**Acceptance Criteria:**
- Dashboard displays "22 of 41" or equivalent phrasing
- Counts are derived from live data, not constants
- Both counts update correctly if the underlying data changes

**Jira:** LT-007
**Design reference:** `docs/design/figma/learntrack-screen-inventory.md`

---

### FR-007 — Topic list showing all 41 topics with status badges

**Summary:** The topic list screen must display all 41 topics, each with a status indicator.

**Acceptance Criteria:**
- `TopicListScreen` renders a scrollable list of 41 topic rows
- Each row shows the topic name and a status badge
- Completed topics show a visually distinct "Completed" indicator
- Not-started topics show a visually distinct "Not Started" indicator
- List is scrollable when content exceeds screen height

**Jira:** LT-007
**Design reference:** `docs/design/figma/learntrack-screen-inventory.md`

---

### FR-008 — Topic detail screen

**Summary:** Tapping a topic row must navigate to a detail screen showing the topic's name and status.

**Acceptance Criteria:**
- Tapping any topic row opens `TopicDetailScreen`
- Detail screen shows the topic name
- Detail screen shows the topic status
- Back navigation returns to the topic list without data loss

**Jira:** LT-007
**Design reference:** `docs/design/figma/learntrack-screen-inventory.md`

---

### FR-009 — Validate JSON schema on app startup

**Summary:** The app must validate the JSON file's syntax and schema when loading course data.

**Acceptance Criteria:**
- `CourseRepositoryImpl` validates every topic's `id`, `name`, and `status` fields
- Unknown status values cause an `Error` state, not a crash
- Duplicate `id` values are detected and cause an `Error` state

**Jira:** LT-005
**Design reference:** `docs/architecture/learntrack-data-flow.md` § 7

---

### FR-010 — Loading state while data is fetched

**Summary:** The dashboard must display a loading indicator while data is being loaded.

**Acceptance Criteria:**
- `CourseUiState.Loading` is emitted before any data is available
- A loading indicator is displayed in that state (e.g., `CircularProgressIndicator`)
- The loading state transitions to `Success` or `Error` once loading completes

**Jira:** LT-007
**Design reference:** `docs/design/google-stitch/course-progress-prompt.md`

---

### FR-011 — Error state for missing or invalid JSON

**Summary:** If `learntrack_topics.json` is missing, malformed, or fails schema validation, the app must display a user-friendly error state.

**Acceptance Criteria:**
- `CourseUiState.Error` is emitted for IOException, ParseException, or ValidationException
- A user-visible error message is displayed
- The app does not crash
- Technical exception details are logged but not shown in the UI

**Jira:** LT-005
**Design reference:** `docs/design/google-stitch/course-progress-prompt.md`

---

### FR-012 — Empty state for empty topic list

**Summary:** If the loaded topic list is empty (e.g., topics array is present but has zero entries), the topic list screen must display an empty state.

**Acceptance Criteria:**
- `CourseUiState.Empty` is emitted when the topic list has zero entries
- An empty state composable is shown in that case
- The empty state does not display fabricated progress data

**Jira:** LT-007
**Design reference:** `docs/design/google-stitch/course-progress-prompt.md`

---

### FR-013 — Progress bar matches percentage

**Summary:** The progress bar (or equivalent visual) on the dashboard must visually represent 53.7%.

**Acceptance Criteria:**
- A `LinearProgressIndicator` or equivalent is present with `progress = completionPercentage / 100f`
- The visual fill matches the calculated percentage
- The progress bar includes an accessibility label

**Jira:** LT-007
**Design reference:** `docs/design/figma/learntrack-design-system.md`

---

### FR-014 — Back navigation from detail to list

**Summary:** The back action (system back gesture or back button) on the topic detail screen must return the user to the topic list.

**Acceptance Criteria:**
- Back navigation from `TopicDetailScreen` navigates to `TopicListScreen`
- Topic list is not reloaded (uses cached/shared state from ViewModel)
- No data loss occurs during navigation

**Jira:** LT-007
**Design reference:** `docs/design/figma/learntrack-screen-inventory.md`

---

### FR-015 — Preserve topic ids and names

**Summary:** Once `learntrack_topics.json` is created, no topic `id` or `name` may be silently altered.

**Acceptance Criteria:**
- Any change to an existing topic's `id` or `name` requires an explicit decision and re-validation
- Automated validation detects unexpected changes before commit
- No tooling silently rewrites or sorts the JSON in ways that change identity fields

**Jira:** LT-005
**Design reference:** `docs/architecture/learntrack-data-flow.md` § 4

---

## 3. Non-Functional Requirements

### NFR-001 — Performance

- Course data must load and render within 2 seconds on a mid-range Android device (Pixel 3 class hardware, Android 10+).
- Asset file reads are performed on a background dispatcher (`Dispatchers.IO`), not on the main thread.
- No network calls are made during data loading.

### NFR-002 — Accessibility

- All interactive UI elements must have content descriptions.
- The completion percentage must be communicated as text, not only as a graphical element.
- The app must be navigable using TalkBack (Android's screen reader).
- Text contrast ratios must meet WCAG 2.1 AA minimum (4.5:1 for normal text, 3:1 for large text).

### NFR-003 — Data Integrity

- Topic count and status distribution are asserted by an automated regression test on every CI run.
- No progress value is hard-coded in application logic.
- The JSON file is the single source of truth; no in-memory patching of counts is permitted.

### NFR-004 — Crash Prevention

- The application must not crash due to any data loading error.
- All exceptions from JSON parsing and asset loading are caught and converted to `Error` UI states.

### NFR-005 — No Hard-Coded Progress

- The value 53.7%, 22, 19, and 41 must not appear as constants in application logic.
- These values may appear only in: test assertions, comments, and documentation.

### NFR-006 — Test Coverage

- Unit tests must exist for: `CalculateProgressUseCase`, `CourseRepositoryImpl`, `CourseProgressViewModel`.
- UI tests must exist for: `CourseProgressScreen` (dashboard), `TopicListScreen`.
- A regression test must assert the 22/19/41 distribution.
- All tests must pass on `./gradlew testDebugUnitTest`.

### NFR-007 — Build Reproducibility

- `./gradlew assembleDebug` must complete without errors on a clean checkout.
- All dependency versions are pinned exactly in `gradle/libs.versions.toml`.

### NFR-008 — Lint

- `./gradlew lintDebug` must report no errors (warnings are acceptable with documented exceptions).

---

## 4. Edge Cases

| Edge Case | Expected Behavior |
|---|---|
| `learntrack_topics.json` is missing from assets | `CourseUiState.Error("Course data could not be loaded.")` |
| JSON file exists but has invalid syntax | `CourseUiState.Error("Course data is invalid.")` |
| JSON parses but a topic is missing the `id` field | `CourseUiState.Error("Course data has an unexpected format.")` |
| JSON parses but a topic has an unknown status value | `CourseUiState.Error("Course data has an unexpected format.")` |
| `topics` array is present but empty | `CourseUiState.Empty` |
| All 41 topics are `completed` | Progress = 100.0%, displayed as "100.0%" |
| All 41 topics are `not_started` | Progress = 0.0%, displayed as "0.0%" |
| Two topics share the same `id` | `CourseUiState.Error("Course data has duplicate topic identifiers.")` |
| Topics array has 40 entries instead of 41 | `CourseUiState.Error("Unexpected topic count.")` in strict mode |
| Progress percentage is exactly 50.0% | Displayed as "50.0%" (no rounding artifacts) |

---

## 5. Data Preservation Constraints

1. Once `learntrack_topics.json` is created with 41 topics, no topic `id` or `name` may be changed without an explicit, documented decision.
2. Status assignments (22 completed / 19 not_started) are the authoritative distribution for this phase. They may only change if the user explicitly approves a new distribution.
3. Any validation script or tool must detect unexpected changes to the topic count or status distribution and fail loudly (non-zero exit code) rather than silently accepting drift.
4. Automated tests (regression test) serve as the enforcement mechanism for data preservation.

---

## 6. References

- Google Stitch prompts: `docs/design/google-stitch/learntrack-stitch-master-prompt.md`, `docs/design/google-stitch/course-progress-prompt.md`
- Figma handoff: `docs/design/figma/learntrack-figma-handoff.md`
- Figma design system: `docs/design/figma/learntrack-design-system.md`
- Jira stories CSV: `docs/jira/learntrack-stories.csv` (proposed keys LT-001 through LT-011; CSV only — not live Jira issues)
- Jira traceability CSV: `docs/jira/learntrack-traceability.csv`
- Architecture: `docs/architecture/learntrack-architecture.md`
- Data flow: `docs/architecture/learntrack-data-flow.md`
- Discovery report: `docs/discovery/learntrack-discovery-report.md`
- Scope doc: `docs/scope/learntrack-scope.md`
- Kiro design spec: `.kiro/specs/course-progress-update/design.md`
- Kiro tasks spec: `.kiro/specs/course-progress-update/tasks.md`
