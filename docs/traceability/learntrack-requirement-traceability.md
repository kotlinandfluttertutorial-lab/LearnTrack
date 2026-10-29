# LearnTrack — End-to-End Requirement Traceability

**Document type:** End-to-End Traceability Matrix  
**Phase:** Phase Group 5 — Traceability + learntrack_topics.json Update  
**Status:** Pre-implementation — project is in pre-scaffold state; no Android source code exists yet  
**Last updated:** Phase Group 5  
**Workspace root:** `j:\Android\AndroidStudioProjects\Kiro\LearnTrack`

---

## 1. Document Purpose

This document maintains the full chain from each requirement through design, planning, implementation, and testing. It is the authoritative traceability record for the LearnTrack Course Progress Update feature.

Every row in the traceability matrix maps one requirement to:

- The Stitch screen or design requirement that defines its visual contract
- The Figma component or screen that specifies the implementation target
- The Jira issue (CSV-only; not a live Jira key) that tracks delivery
- The Kiro spec section (requirements, design, tasks) that governs it
- The implementation file (actual path if the file exists; predicted path if not yet created)
- The automated test (actual path if the file exists; predicted path if not yet created)
- The quality-gate check that enforces it

Missing links are flagged explicitly. No links are fabricated.

---

## 2. Repository State at Time of Document Creation

| Item | Status |
|---|---|
| Android project scaffold (`app/`, `build.gradle.kts`, etc.) | **NOT YET CREATED** |
| `learntrack_topics.json` | **NOT YET CREATED** (see § 9) |
| Source files (Kotlin, layout, etc.) | **NOT YET CREATED** |
| Unit test files | **NOT YET CREATED** |
| UI / instrumented test files | **NOT YET CREATED** |
| Jira issues (live) | **NOT CREATED** — CSV files only |
| Figma design file | **NOT CREATED** — handoff doc only |
| Google Stitch designs | **NOT CREATED** — prompts only |
| GitHub Actions CI | **NOT CREATED** |

The discovery report (`docs/discovery/learntrack-discovery-report.md`) and subsequent documentation phases have produced all design, specification, and planning artifacts. Implementation has not yet begun.

---

## 3. Traceability Matrix — Functional Requirements

Each row traces one requirement through the full chain.

### FR-001 — Create learntrack_topics.json

| Chain element | Reference |
|---|---|
| **Stitch screen** | `docs/design/google-stitch/course-progress-prompt.md` — data values section (course name, counts, percentage) |
| **Figma component** | `docs/design/figma/learntrack-figma-handoff.md` § 2 Dashboard — data values table |
| **Jira issue** | LT-005 (CSV only — `docs/jira/learntrack-stories.csv`) |
| **Kiro spec — requirements** | `.kiro/specs/course-progress-update/requirements.md` § FR-001 |
| **Kiro spec — design** | `.kiro/specs/course-progress-update/design.md` § 3 (schema + update plan) |
| **Kiro spec — tasks** | `.kiro/specs/course-progress-update/tasks.md` Task T-005 |
| **Implementation file** | `app/src/main/assets/learntrack_topics.json` _(PREDICTED — not yet created)_ |
| **Automated test** | `app/src/test/java/com/learntrack/data/TopicDistributionRegressionTest.kt` _(PREDICTED)_ |
| **Quality gate** | QG-001 (JSON syntax), QG-002 (distribution regression) — `docs/quality-gates/learntrack-quality-gates.md` |
| **Status** | MISSING: implementation file; MISSING: test file |

---

### FR-002 — Exactly 22 topics marked completed

| Chain element | Reference |
|---|---|
| **Stitch screen** | `docs/design/google-stitch/course-progress-prompt.md` — stats row "22 / Completed" |
| **Figma component** | `docs/design/figma/learntrack-figma-handoff.md` § 2 Dashboard — Stats Row column 1 |
| **Jira issue** | LT-005 (CSV only) |
| **Kiro spec — requirements** | `.kiro/specs/course-progress-update/requirements.md` § FR-002 |
| **Kiro spec — design** | `.kiro/specs/course-progress-update/design.md` § 3 |
| **Kiro spec — tasks** | `.kiro/specs/course-progress-update/tasks.md` Task T-005 |
| **Implementation file** | `app/src/main/assets/learntrack_topics.json` _(PREDICTED)_ |
| **Automated test** | `app/src/test/java/com/learntrack/data/TopicDistributionRegressionTest.kt` _(PREDICTED)_ |
| **Quality gate** | QG-002 (distribution regression) |
| **Status** | MISSING: implementation file; MISSING: test file |

---

### FR-003 — Exactly 19 topics marked not_started

| Chain element | Reference |
|---|---|
| **Stitch screen** | `docs/design/google-stitch/course-progress-prompt.md` — stats row "19 / Not Started" |
| **Figma component** | `docs/design/figma/learntrack-figma-handoff.md` § 2 Dashboard — Stats Row column 3 |
| **Jira issue** | LT-005 (CSV only) |
| **Kiro spec — requirements** | `.kiro/specs/course-progress-update/requirements.md` § FR-003 |
| **Kiro spec — design** | `.kiro/specs/course-progress-update/design.md` § 3 |
| **Kiro spec — tasks** | `.kiro/specs/course-progress-update/tasks.md` Task T-005 |
| **Implementation file** | `app/src/main/assets/learntrack_topics.json` _(PREDICTED)_ |
| **Automated test** | `app/src/test/java/com/learntrack/data/TopicDistributionRegressionTest.kt` _(PREDICTED)_ |
| **Quality gate** | QG-002 (distribution regression) |
| **Status** | MISSING: implementation file; MISSING: test file |

---

### FR-004 — Calculate completion percentage from live data

| Chain element | Reference |
|---|---|
| **Stitch screen** | `docs/design/google-stitch/course-progress-prompt.md` — "53.7%" center text and linear bar fill |
| **Figma component** | `docs/design/figma/learntrack-figma-handoff.md` § 3 — `CircularProgressIndicator`, `LinearProgressBar`; `docs/design/figma/learntrack-design-system.md` |
| **Jira issue** | LT-006 (CSV only) |
| **Kiro spec — requirements** | `.kiro/specs/course-progress-update/requirements.md` § FR-004 |
| **Kiro spec — design** | `.kiro/specs/course-progress-update/design.md` § 2 (data flow), § 4 (domain model) |
| **Kiro spec — tasks** | `.kiro/specs/course-progress-update/tasks.md` Task T-007 |
| **Implementation file** | `app/src/main/java/com/learntrack/domain/usecase/CalculateProgressUseCase.kt` _(PREDICTED)_ |
| **Automated test** | `app/src/test/java/com/learntrack/domain/ProgressCalculatorTest.kt` _(PREDICTED)_ |
| **Quality gate** | QG-003 (unit tests pass via `./gradlew testDebugUnitTest`) |
| **Status** | MISSING: implementation file; MISSING: test file |

---

### FR-005 — Display course dashboard with progress percentage

| Chain element | Reference |
|---|---|
| **Stitch screen** | `docs/design/google-stitch/course-progress-prompt.md` — Component 1 (Progress Hero Card), State A (default) |
| **Figma component** | `docs/design/figma/learntrack-figma-handoff.md` § 2 Screen 1 (Course Dashboard) — Hero Section; `docs/design/figma/learntrack-screen-inventory.md` |
| **Jira issue** | LT-007 (CSV only) |
| **Kiro spec — requirements** | `.kiro/specs/course-progress-update/requirements.md` § FR-005 |
| **Kiro spec — design** | `.kiro/specs/course-progress-update/design.md` § 7 (CourseProgressScreen) |
| **Kiro spec — tasks** | `.kiro/specs/course-progress-update/tasks.md` Task T-009 |
| **Implementation file** | `app/src/main/java/com/learntrack/ui/progress/CourseProgressScreen.kt` _(PREDICTED)_ |
| **Automated test** | `app/src/androidTest/java/com/learntrack/ui/CourseProgressScreenTest.kt` _(PREDICTED)_ |
| **Quality gate** | QG-004 (UI test: asserts "53.7%" text present) |
| **Status** | MISSING: implementation file; MISSING: test file |

---

### FR-006 — Display completed count and total on dashboard

| Chain element | Reference |
|---|---|
| **Stitch screen** | `docs/design/google-stitch/course-progress-prompt.md` — Component 1, three-column stats row ("22 / Completed", "41 / Total") |
| **Figma component** | `docs/design/figma/learntrack-figma-handoff.md` § 2 Dashboard — Stats Row |
| **Jira issue** | LT-007 (CSV only) |
| **Kiro spec — requirements** | `.kiro/specs/course-progress-update/requirements.md` § FR-006 |
| **Kiro spec — design** | `.kiro/specs/course-progress-update/design.md` § 7 |
| **Kiro spec — tasks** | `.kiro/specs/course-progress-update/tasks.md` Task T-009 |
| **Implementation file** | `app/src/main/java/com/learntrack/ui/progress/CourseProgressScreen.kt` _(PREDICTED)_ |
| **Automated test** | `app/src/androidTest/java/com/learntrack/ui/CourseProgressScreenTest.kt` _(PREDICTED)_ |
| **Quality gate** | QG-004 (UI test: asserts "22" and "41" text present) |
| **Status** | MISSING: implementation file; MISSING: test file |

---

### FR-007 — Topic list showing all 41 topics with status badges

| Chain element | Reference |
|---|---|
| **Stitch screen** | `docs/design/google-stitch/learntrack-stitch-master-prompt.md` — topic list screen; filter chips "All (41)", "Completed (22)", "Not Started (19)" |
| **Figma component** | `docs/design/figma/learntrack-figma-handoff.md` § 2 Screen 3 (Topic List) — TopicRow, StatusBadge; `docs/design/figma/learntrack-screen-inventory.md` |
| **Jira issue** | LT-007 (CSV only) |
| **Kiro spec — requirements** | `.kiro/specs/course-progress-update/requirements.md` § FR-007 |
| **Kiro spec — design** | `.kiro/specs/course-progress-update/design.md` § 7 (TopicListScreen) |
| **Kiro spec — tasks** | `.kiro/specs/course-progress-update/tasks.md` Task T-009 |
| **Implementation file** | `app/src/main/java/com/learntrack/ui/topics/TopicListScreen.kt` _(PREDICTED)_ |
| **Automated test** | `app/src/androidTest/java/com/learntrack/ui/TopicListScreenTest.kt` _(PREDICTED)_ |
| **Quality gate** | QG-004 (UI test: asserts 41 rows and both badge types present) |
| **Status** | MISSING: implementation file; MISSING: test file |

---

### FR-008 — Topic detail screen

| Chain element | Reference |
|---|---|
| **Stitch screen** | `docs/design/google-stitch/learntrack-stitch-master-prompt.md` — topic detail states |
| **Figma component** | `docs/design/figma/learntrack-figma-handoff.md` § 2 Screen 4 (Topic Detail) — Hero Card, StatusBadge; `docs/design/figma/learntrack-screen-inventory.md` |
| **Jira issue** | LT-007 (CSV only) |
| **Kiro spec — requirements** | `.kiro/specs/course-progress-update/requirements.md` § FR-008 |
| **Kiro spec — design** | `.kiro/specs/course-progress-update/design.md` § 7 (TopicDetailScreen), § 7 (Navigation) |
| **Kiro spec — tasks** | `.kiro/specs/course-progress-update/tasks.md` Task T-009 |
| **Implementation file** | `app/src/main/java/com/learntrack/ui/topics/TopicDetailScreen.kt` _(PREDICTED)_ |
| **Automated test** | `app/src/androidTest/java/com/learntrack/ui/TopicDetailScreenTest.kt` _(PREDICTED)_ |
| **Quality gate** | QG-004 (UI test: asserts topic name/status visible, back nav works) |
| **Status** | MISSING: implementation file; MISSING: test file |

---

### FR-009 — Validate JSON schema on app startup

| Chain element | Reference |
|---|---|
| **Stitch screen** | `docs/design/google-stitch/course-progress-prompt.md` — State C (Error), State D (Invalid data) |
| **Figma component** | `docs/design/figma/learntrack-figma-handoff.md` § 2 Screen 1 States — `dashboard/light/error` |
| **Jira issue** | LT-005 (CSV only) |
| **Kiro spec — requirements** | `.kiro/specs/course-progress-update/requirements.md` § FR-009 |
| **Kiro spec — design** | `.kiro/specs/course-progress-update/design.md` § 5 (repository), § 9 (error handling) |
| **Kiro spec — tasks** | `.kiro/specs/course-progress-update/tasks.md` Task T-006 |
| **Implementation file** | `app/src/main/java/com/learntrack/data/repository/CourseRepositoryImpl.kt` _(PREDICTED)_ |
| **Automated test** | `app/src/test/java/com/learntrack/data/repository/CourseRepositoryImplTest.kt` _(PREDICTED)_ |
| **Quality gate** | QG-003 (unit test: invalid JSON, missing file, duplicate IDs all produce Error state) |
| **Status** | MISSING: implementation file; MISSING: test file |

---

### FR-010 — Loading state while data is fetched

| Chain element | Reference |
|---|---|
| **Stitch screen** | `docs/design/google-stitch/course-progress-prompt.md` — State B (Loading) |
| **Figma component** | `docs/design/figma/learntrack-figma-handoff.md` § 2 Screen 1 States — `dashboard/light/loading`; `docs/design/figma/learntrack-design-system.md` — LoadingSkeleton component |
| **Jira issue** | LT-007 (CSV only) |
| **Kiro spec — requirements** | `.kiro/specs/course-progress-update/requirements.md` § FR-010 |
| **Kiro spec — design** | `.kiro/specs/course-progress-update/design.md` § 6 (ViewModel — initial Loading state), § 8 (UI state management) |
| **Kiro spec — tasks** | `.kiro/specs/course-progress-update/tasks.md` Task T-008, T-009 |
| **Implementation file** | `app/src/main/java/com/learntrack/ui/progress/CourseProgressScreen.kt` _(PREDICTED)_ |
| **Automated test** | `app/src/androidTest/java/com/learntrack/ui/CourseProgressScreenTest.kt` _(PREDICTED)_ |
| **Quality gate** | QG-003 (unit test: Loading→Success / Loading→Error transitions in ViewModel) |
| **Status** | MISSING: implementation file; MISSING: test file |

---

### FR-011 — Error state for missing or invalid JSON

| Chain element | Reference |
|---|---|
| **Stitch screen** | `docs/design/google-stitch/course-progress-prompt.md` — State C (Error): error icon, "Couldn't load progress", Retry button |
| **Figma component** | `docs/design/figma/learntrack-figma-handoff.md` § 2 Screen 1 States — `dashboard/light/error`; `docs/design/figma/learntrack-design-system.md` — ErrorState component |
| **Jira issue** | LT-005 (CSV only) |
| **Kiro spec — requirements** | `.kiro/specs/course-progress-update/requirements.md` § FR-011 |
| **Kiro spec — design** | `.kiro/specs/course-progress-update/design.md` § 9 (error handling strategy) |
| **Kiro spec — tasks** | `.kiro/specs/course-progress-update/tasks.md` Task T-006, T-008, T-009 |
| **Implementation file** | `app/src/main/java/com/learntrack/ui/progress/CourseProgressScreen.kt` _(PREDICTED)_ |
| **Automated test** | `app/src/androidTest/java/com/learntrack/ui/CourseProgressScreenTest.kt` _(PREDICTED)_; `app/src/test/java/com/learntrack/data/repository/CourseRepositoryImplTest.kt` _(PREDICTED)_ |
| **Quality gate** | QG-003 (unit test: IOException, SerializationException, ValidationException → Error state) |
| **Status** | MISSING: implementation file; MISSING: test files |

---

### FR-012 — Empty state for empty topic list

| Chain element | Reference |
|---|---|
| **Stitch screen** | `docs/design/google-stitch/course-progress-prompt.md` — not explicitly designed; implied by State D (invalid/degraded data) |
| **Figma component** | `docs/design/figma/learntrack-figma-handoff.md` § 2 Screen 3 States — `topic-list/light/empty-search`; `docs/design/figma/learntrack-design-system.md` — EmptyState component |
| **Jira issue** | LT-007 (CSV only) |
| **Kiro spec — requirements** | `.kiro/specs/course-progress-update/requirements.md` § FR-012 |
| **Kiro spec — design** | `.kiro/specs/course-progress-update/design.md` § 6 (ViewModel — Empty state), § 9 |
| **Kiro spec — tasks** | `.kiro/specs/course-progress-update/tasks.md` Task T-008, T-009 |
| **Implementation file** | `app/src/main/java/com/learntrack/ui/topics/TopicListScreen.kt` _(PREDICTED)_ |
| **Automated test** | `app/src/androidTest/java/com/learntrack/ui/TopicListScreenTest.kt` _(PREDICTED)_ |
| **Quality gate** | QG-003 (unit test: empty topic list → Empty state in ViewModel) |
| **Status** | MISSING: implementation file; MISSING: test file |

---

### FR-013 — Progress bar matches percentage

| Chain element | Reference |
|---|---|
| **Stitch screen** | `docs/design/google-stitch/course-progress-prompt.md` — Component 1 linear progress bar (53.7% fill, animated) |
| **Figma component** | `docs/design/figma/learntrack-figma-handoff.md` § 2 Screen 2 (Progress Summary) — LinearProgressBar; `docs/design/figma/learntrack-design-system.md` — LinearProgressBar component |
| **Jira issue** | LT-007 (CSV only) |
| **Kiro spec — requirements** | `.kiro/specs/course-progress-update/requirements.md` § FR-013 |
| **Kiro spec — design** | `.kiro/specs/course-progress-update/design.md` § 7 (progress bar spec: `progress = completionPercentage / 100f`) |
| **Kiro spec — tasks** | `.kiro/specs/course-progress-update/tasks.md` Task T-009, T-010 |
| **Implementation file** | `app/src/main/java/com/learntrack/ui/progress/CourseProgressScreen.kt` _(PREDICTED)_ |
| **Automated test** | `app/src/androidTest/java/com/learntrack/ui/CourseProgressScreenTest.kt` _(PREDICTED)_ |
| **Quality gate** | QG-004 (UI test: progress bar semantic node present with correct value) |
| **Status** | MISSING: implementation file; MISSING: test file |

---

### FR-014 — Back navigation from topic detail to topic list

| Chain element | Reference |
|---|---|
| **Stitch screen** | `docs/design/google-stitch/learntrack-stitch-master-prompt.md` — back arrow on Top App Bar |
| **Figma component** | `docs/design/figma/learntrack-figma-handoff.md` § 2 Screen 4 — Top App Bar with back arrow; `docs/design/figma/learntrack-screen-inventory.md` |
| **Jira issue** | LT-007 (CSV only) |
| **Kiro spec — requirements** | `.kiro/specs/course-progress-update/requirements.md` § FR-014 |
| **Kiro spec — design** | `.kiro/specs/course-progress-update/design.md` § 7 (Navigation: `NavHost` with `TopicDetail/{topicId}`) |
| **Kiro spec — tasks** | `.kiro/specs/course-progress-update/tasks.md` Task T-002, T-009 |
| **Implementation file** | `app/src/main/java/com/learntrack/ui/topics/TopicDetailScreen.kt` _(PREDICTED)_; `app/src/main/java/com/learntrack/navigation/LearnTrackNavHost.kt` _(PREDICTED)_ |
| **Automated test** | `app/src/androidTest/java/com/learntrack/ui/TopicDetailScreenTest.kt` _(PREDICTED)_ |
| **Quality gate** | QG-004 (UI test: back navigation from TopicDetail returns to TopicList) |
| **Status** | MISSING: implementation files; MISSING: test file |

---

### FR-015 — Preserve topic ids and names

| Chain element | Reference |
|---|---|
| **Stitch screen** | Not applicable — data integrity requirement, no UI design |
| **Figma component** | Not applicable |
| **Jira issue** | LT-005 (CSV only) |
| **Kiro spec — requirements** | `.kiro/specs/course-progress-update/requirements.md` § FR-015, § 5 (data preservation constraints) |
| **Kiro spec — design** | `.kiro/specs/course-progress-update/design.md` § 3 (update plan: "topic ids and names must not be changed") |
| **Kiro spec — tasks** | `.kiro/specs/course-progress-update/tasks.md` Task T-005 |
| **Implementation file** | `app/src/main/assets/learntrack_topics.json` _(PREDICTED)_; `scripts/check-before-push.ps1` (ACTUAL — exists) |
| **Automated test** | `app/src/test/java/com/learntrack/data/TopicDistributionRegressionTest.kt` _(PREDICTED)_ |
| **Quality gate** | QG-001 (JSON syntax), QG-002 (distribution regression) |
| **Status** | MISSING: JSON asset; MISSING: regression test |

---

## 4. Traceability Matrix — Non-Functional Requirements

### NFR-001 — Performance: data loads within 2 seconds

| Chain element | Reference |
|---|---|
| **Stitch/Figma** | Not applicable — performance criterion |
| **Jira issue** | LT-007 (CSV only) |
| **Kiro spec** | `.kiro/specs/course-progress-update/requirements.md` § NFR-001 |
| **Kiro design** | `.kiro/specs/course-progress-update/design.md` § 5 (`.flowOn(Dispatchers.IO)`) |
| **Implementation file** | `app/src/main/java/com/learntrack/data/repository/CourseRepositoryImpl.kt` _(PREDICTED)_ |
| **Automated test** | `app/src/androidTest/java/com/learntrack/ui/CourseProgressScreenTest.kt` _(PREDICTED — performance assertion)_ |
| **Quality gate** | MISSING: no automated performance gate defined yet |
| **Status** | MISSING: implementation file; MISSING: performance test; MISSING: performance quality gate |

---

### NFR-002 — Accessibility: content descriptions on all interactive elements

| Chain element | Reference |
|---|---|
| **Stitch screen** | `docs/design/google-stitch/course-progress-prompt.md` — Accessibility section (contentDescription specs for ring, bar, stats, retry button) |
| **Figma component** | `docs/design/figma/learntrack-design-system.md` — Accessibility section; `docs/design/figma/learntrack-figma-handoff.md` § DA-7, DA-8, DA-9 |
| **Jira issue** | LT-007 (CSV only) |
| **Kiro spec** | `.kiro/specs/course-progress-update/requirements.md` § NFR-002 |
| **Kiro design** | `.kiro/specs/course-progress-update/tasks.md` Task T-010 |
| **Implementation file** | `app/src/main/java/com/learntrack/ui/progress/CourseProgressScreen.kt` _(PREDICTED)_; `app/src/main/java/com/learntrack/ui/components/StatusBadge.kt` _(PREDICTED)_ |
| **Automated test** | `app/src/androidTest/java/com/learntrack/ui/AccessibilityTest.kt` _(PREDICTED)_ |
| **Quality gate** | MISSING: no automated accessibility quality gate defined; manual TalkBack review required |
| **Status** | MISSING: implementation files; MISSING: automated accessibility test; accessibility requires manual validation with TalkBack |

---

### NFR-003 — Text contrast meets WCAG 2.1 AA

| Chain element | Reference |
|---|---|
| **Stitch screen** | `docs/design/google-stitch/course-progress-prompt.md` — Colors section (all hex values specified) |
| **Figma component** | `docs/design/figma/learntrack-design-system.md` § 1 (color tokens with contrast ratios); `docs/design/figma/learntrack-figma-handoff.md` § DA-9 |
| **Jira issue** | LT-007 (CSV only) |
| **Kiro spec** | `.kiro/specs/course-progress-update/requirements.md` § NFR-002 (4.5:1 normal, 3:1 large) |
| **Kiro design** | `.kiro/specs/course-progress-update/design.md` § 7 |
| **Implementation file** | `app/src/main/java/com/learntrack/ui/theme/Color.kt` _(PREDICTED)_ |
| **Automated test** | Manual review required — contrast ratio validation is not automated in this stack |
| **Quality gate** | MISSING: no automated contrast quality gate; manual design review against `docs/design/google-stitch/design-review-checklist.md` |
| **Status** | MISSING: theme file; contrast verification requires manual review |

---

### NFR-004 — App never crashes due to data loading errors

| Chain element | Reference |
|---|---|
| **Stitch/Figma** | Error state designs in `docs/design/google-stitch/course-progress-prompt.md` (State C) |
| **Jira issue** | LT-005 (CSV only) |
| **Kiro spec** | `.kiro/specs/course-progress-update/requirements.md` § NFR-004 |
| **Kiro design** | `.kiro/specs/course-progress-update/design.md` § 9 (all exceptions caught and converted to `Result.failure`) |
| **Implementation file** | `app/src/main/java/com/learntrack/data/repository/CourseRepositoryImpl.kt` _(PREDICTED)_ |
| **Automated test** | `app/src/test/java/com/learntrack/data/repository/CourseRepositoryImplTest.kt` _(PREDICTED)_ |
| **Quality gate** | QG-003 (unit test: all exception paths produce Error state, not crash) |
| **Status** | MISSING: implementation file; MISSING: test file |

---

### NFR-005 — No hard-coded progress values in application logic

| Chain element | Reference |
|---|---|
| **Stitch/Figma** | Not applicable — code constraint |
| **Jira issue** | LT-006 (CSV only) |
| **Kiro spec** | `.kiro/specs/course-progress-update/requirements.md` § NFR-005 |
| **Kiro design** | `.kiro/specs/course-progress-update/design.md` § 4 (domain model — `completionPercentage: Float` computed at runtime) |
| **Implementation file** | `app/src/main/java/com/learntrack/domain/usecase/CalculateProgressUseCase.kt` _(PREDICTED)_ |
| **Automated test** | `app/src/test/java/com/learntrack/domain/ProgressCalculatorTest.kt` _(PREDICTED)_ |
| **Quality gate** | MISSING: no static analysis rule enforcing this yet; lint / code review only |
| **Status** | MISSING: implementation file; MISSING: test file; MISSING: automated no-hard-code gate |

---

### NFR-006 — All unit tests pass on `./gradlew testDebugUnitTest`

| Chain element | Reference |
|---|---|
| **Stitch/Figma** | Not applicable |
| **Jira issue** | LT-008 (CSV only) |
| **Kiro spec** | `.kiro/specs/course-progress-update/requirements.md` § NFR-006 |
| **Kiro tasks** | `.kiro/specs/course-progress-update/tasks.md` Task T-011, T-012 |
| **Implementation file** | All test files under `app/src/test/` _(PREDICTED)_ |
| **Automated test** | `./gradlew testDebugUnitTest` — CI-enforced |
| **Quality gate** | QG-003 (unit test pass gate) — `scripts/check-before-push.ps1` CHECK 5, `scripts/check-before-push.sh` |
| **Status** | MISSING: scaffold; MISSING: test files; quality gate scripts exist but Gradle checks are skipped until scaffold exists |

---

### NFR-007 — `assembleDebug` completes without errors

| Chain element | Reference |
|---|---|
| **Stitch/Figma** | Not applicable |
| **Jira issue** | LT-010 (CSV only) |
| **Kiro spec** | `.kiro/specs/course-progress-update/requirements.md` § NFR-007 |
| **Kiro tasks** | `.kiro/specs/course-progress-update/tasks.md` Task T-001, T-013 |
| **Implementation file** | `build.gradle.kts`, `app/build.gradle.kts`, `gradle/libs.versions.toml` _(all PREDICTED)_ |
| **Automated test** | `./gradlew assembleDebug` — CI-enforced |
| **Quality gate** | QG-005 (build gate) — `scripts/check-before-push.ps1` CHECK 7; `docs/quality-gates/learntrack-quality-gates.md` |
| **Status** | MISSING: Android scaffold entirely; quality gate scripts exist but build check skipped until scaffold |

---

### NFR-008 — `lintDebug` reports no errors

| Chain element | Reference |
|---|---|
| **Stitch/Figma** | Not applicable |
| **Jira issue** | LT-010 (CSV only) |
| **Kiro spec** | `.kiro/specs/course-progress-update/requirements.md` § NFR-008 |
| **Kiro tasks** | `.kiro/specs/course-progress-update/tasks.md` Task T-013 |
| **Implementation file** | All source files; `app/build.gradle.kts` _(PREDICTED)_ |
| **Automated test** | `./gradlew lintDebug` — CI-enforced |
| **Quality gate** | QG-004 (lint gate) — `scripts/check-before-push.ps1` CHECK 6; `docs/quality-gates/learntrack-quality-gates.md` |
| **Status** | MISSING: scaffold; quality gate scripts exist but lint check skipped until scaffold |

---

## 5. Acceptance Criteria Traceability

| AC ID | Description | Jira | Implementation file / artifact | Test / verification | Status |
|---|---|---|---|---|---|
| AC-001 | 22/19/41 topic distribution never drifts | LT-008 | `app/src/main/assets/learntrack_topics.json` _(PREDICTED)_ | `TopicDistributionRegressionTest.kt` _(PREDICTED)_ + QG-002 | MISSING |
| AC-002 | Repository discovery report exists and is complete | LT-001 | `docs/discovery/learntrack-discovery-report.md` **(ACTUAL)** | Manual review | **COMPLETE** |
| AC-003 | Scope document exists and is approved | LT-002 | `docs/scope/learntrack-scope.md` **(ACTUAL)** | Manual review | **COMPLETE** |
| AC-004 | Kiro specs exist: requirements.md, design.md, tasks.md | LT-009 | `.kiro/specs/course-progress-update/*.md` **(ACTUAL)** | File existence check | **COMPLETE** |
| AC-005 | Jira CSV files generated (not live Jira issues) | LT-009 | `docs/jira/learntrack-epics.csv`, `learntrack-stories.csv`, `learntrack-subtasks.csv`, `learntrack-traceability.csv` **(ACTUAL)** | File existence check | **COMPLETE** |
| AC-006 | Architecture docs exist: architecture, data-flow, environments | LT-001 | `docs/architecture/learntrack-architecture.md`, `learntrack-data-flow.md`, `learntrack-environments.md` **(ACTUAL)** | File existence check | **COMPLETE** |
| AC-007 | Google Stitch prompts exist | LT-003 | `docs/design/google-stitch/learntrack-stitch-master-prompt.md`, `course-progress-prompt.md`, `design-review-checklist.md` **(ACTUAL)** | File existence check | **COMPLETE** (prompts only — designs not yet executed in Stitch) |
| AC-008 | Figma handoff and design system docs exist | LT-004 | `docs/design/figma/learntrack-figma-handoff.md`, `learntrack-design-system.md`, `learntrack-screen-inventory.md` **(ACTUAL)** | File existence check | **COMPLETE** (spec docs only — no live Figma file created) |
| AC-009 | Kiro skills created (all 6) | LT-009 | `.kiro/skills/learntrack-*/SKILL.md` (6 files) **(ACTUAL)** | File existence check | **COMPLETE** |
| AC-010 | Pre-push quality gate scripts exist | LT-010 | `scripts/check-before-push.ps1`, `scripts/check-before-push.sh` **(ACTUAL)** | Manual execution | **COMPLETE** (scripts exist; Gradle-dependent checks are skipped until scaffold) |
| AC-011 | This traceability document exists | LT-009 | `docs/traceability/learntrack-requirement-traceability.md` **(ACTUAL — this file)** | File existence check | **COMPLETE** |

---

## 6. Design-to-Implementation Mapping

### 6.1 Google Stitch → Figma → Android Component

| Stitch element | Figma component | Android composable | File (predicted) |
|---|---|---|---|
| Progress Hero Card (Component 1) | `ProgressHeroCard` | `@Composable ProgressHeroCard(state)` | `ui/components/ProgressHeroCard.kt` |
| Circular progress ring | `CircularProgressIndicator` variant | `@Composable CircularProgress(percentage)` | `ui/components/CircularProgress.kt` |
| Linear progress bar | `LinearProgressBar` | `LinearProgressIndicator` (Material 3) | `ui/progress/CourseProgressScreen.kt` |
| Three-column stats row | `StatsGrid` | `@Composable StatsGrid(completed, notStarted, total)` | `ui/components/StatsGrid.kt` |
| Topic row (list) | `TopicRow` | `@Composable TopicRow(topic, onClick)` | `ui/components/TopicRow.kt` |
| Status badge (Completed / Not Started) | `StatusBadge` | `@Composable StatusBadge(status)` | `ui/components/StatusBadge.kt` |
| Course Dashboard screen | Page 3 in Figma Design File | `@Composable CourseProgressScreen(uiState, onTopicClick)` | `ui/progress/CourseProgressScreen.kt` |
| Topic List screen | Page 4 in Figma Design File | `@Composable TopicListScreen(uiState, onTopicClick, onBack)` | `ui/topics/TopicListScreen.kt` |
| Topic Detail screen | Page 5 in Figma Design File | `@Composable TopicDetailScreen(topic, onBack)` | `ui/topics/TopicDetailScreen.kt` |
| Progress Summary bottom sheet | Page 6 in Figma Design File | `@Composable ProgressSummaryBottomSheet(state, onDismiss)` | `ui/components/ProgressSummaryBottomSheet.kt` |
| Loading state (skeleton shimmer) | `LoadingSkeleton` | `CircularProgressIndicator` (centered) | `ui/progress/CourseProgressScreen.kt` |
| Error state | `ErrorState` | `@Composable ErrorMessage(message, onRetry)` | `ui/components/ErrorMessage.kt` |
| Empty state | `EmptyState` | `@Composable EmptyState(message)` | `ui/components/EmptyState.kt` |
| Material 3 color tokens | Design System — Color Tokens | `Color.kt` MaterialTheme extension | `ui/theme/Color.kt` |
| Typography scale | Design System — Typography | `Type.kt` MaterialTheme extension | `ui/theme/Type.kt` |

### 6.2 Data Layer Mapping

| Spec section | Class | File (predicted) |
|---|---|---|
| design.md § 3 — JSON schema | `Topic`, `TopicStatus`, `TopicContainer` | `data/model/Topic.kt`, `data/model/TopicStatus.kt`, `data/model/TopicContainer.kt` |
| design.md § 4 — domain model | `CourseProgress` | `domain/model/CourseProgress.kt` |
| design.md § 5 — repository | `CourseRepository` (interface) | `data/repository/CourseRepository.kt` |
| design.md § 5 — repository impl | `CourseRepositoryImpl` | `data/repository/CourseRepositoryImpl.kt` |
| design.md § 5 — validation | `TopicSchemaValidator`, `ValidationException` | `data/validation/TopicSchemaValidator.kt`, `data/validation/ValidationException.kt` |
| design.md § 2 — use cases | `GetCourseProgressUseCase`, `CalculateProgressUseCase` | `domain/usecase/GetCourseProgressUseCase.kt`, `domain/usecase/CalculateProgressUseCase.kt` |
| design.md § 6 — ViewModel | `CourseProgressViewModel`, `CourseUiState` | `ui/progress/CourseProgressViewModel.kt`, `ui/progress/CourseUiState.kt` |
| design.md § 7 — navigation | `LearnTrackNavHost`, `Screen` | `navigation/LearnTrackNavHost.kt`, `navigation/Screen.kt` |
| tasks.md T-003 — DI | `AppModule`, `DataModule` | `di/AppModule.kt`, `di/DataModule.kt` |
| tasks.md T-002 — theme | `LearnTrackTheme` | `ui/theme/Theme.kt` |

---

## 7. Test Coverage Summary

### 7.1 Unit Tests (predicted — `app/src/test/`)

| Test file | What it covers | Requirements covered | Status |
|---|---|---|---|
| `domain/ProgressCalculatorTest.kt` | `CalculateProgressUseCase` edge cases | FR-004, NFR-005 | MISSING |
| `data/repository/CourseRepositoryImplTest.kt` | Valid JSON, missing file, malformed JSON | FR-009, FR-011, NFR-004 | MISSING |
| `ui/progress/CourseProgressViewModelTest.kt` | Loading→Success, Loading→Error, Loading→Empty transitions | FR-010, FR-011, FR-012 | MISSING |
| `data/validation/TopicSchemaValidatorTest.kt` | Missing fields, invalid status, duplicate IDs | FR-009 | MISSING |
| `data/TopicDistributionRegressionTest.kt` | Asserts 22/19/41 from actual asset | FR-001, FR-002, FR-003, FR-015, AC-001 | MISSING |

### 7.2 UI / Instrumented Tests (predicted — `app/src/androidTest/`)

| Test file | What it covers | Requirements covered | Status |
|---|---|---|---|
| `ui/CourseProgressScreenTest.kt` | "53.7%" text, "22"/"41" text, progress bar, loading/error states | FR-005, FR-006, FR-010, FR-011, FR-013, NFR-001 | MISSING |
| `ui/TopicListScreenTest.kt` | 41 rows, "Completed"/"Not Started" badges, empty state | FR-007, FR-012 | MISSING |
| `ui/TopicDetailScreenTest.kt` | Topic name/status visible, back navigation | FR-008, FR-014 | MISSING |
| `ui/AccessibilityTest.kt` | Content descriptions, semantic nodes | NFR-002 | MISSING |

### 7.3 Test Coverage Gaps

- **All test files are missing** — the Android scaffold has not been created yet.
- **NFR-001 (performance):** No performance test is defined. Once implemented, add a timed assertion in `CourseProgressScreenTest`.
- **NFR-003 (contrast):** Automated contrast checking is not supported in this stack. Manual review required against Figma design system tokens.
- **NFR-005 (no hard-coded values):** No static analysis rule enforces this. Relies on code review during implementation.

---

## 8. Outstanding Gaps and Remediation

### GAP-001 — No Android project scaffold (BLOCKING)

**Impact:** All implementation tasks (T-001 through T-014) are blocked. No Kotlin source, build files, or test infrastructure exists.  
**Blocks:** FR-001 through FR-015, all NFRs, all ACs except AC-002 through AC-011.  
**Remediation:** Execute prompt `docs/kiro-prompts/01-repository-discovery.md` through `docs/kiro-prompts/09-progress-ui-implementation.md`. Create scaffold per tasks.md T-001.  
**Owner:** Developer / Kiro agent (Phase Group 6)

---

### GAP-002 — `learntrack_topics.json` does not exist (BLOCKING)

**Impact:** FR-001, FR-002, FR-003, FR-015 cannot be verified. QG-001 and QG-002 skip the file check. No regression test can run.  
**Blocks:** T-005, T-006 (depends on JSON), T-011 (regression test), QG-001, QG-002.  
**Remediation:** Execute prompt `docs/kiro-prompts/08-topic-json-update.md` after scaffold is created. The topic names for all 41 entries must be confirmed (Gap-006 in discovery report).  
**Owner:** Developer / product owner (topic names) + Kiro agent (JSON creation)

---

### GAP-003 — No live Jira issues (informational)

**Impact:** All Jira references in this document are CSV-only (LT-001 through LT-011). No live Jira issue keys exist.  
**Remediation:** Import `docs/jira/learntrack-epics.csv`, `learntrack-stories.csv`, `learntrack-subtasks.csv` into Jira. Update this document with live issue keys.  
**Owner:** Project manager / developer

---

### GAP-004 — No Figma design file (informational)

**Impact:** Design acceptance criteria DA-1 through DA-12 cannot be verified. Implementation must proceed from the handoff document specification.  
**Remediation:** Follow setup instructions in `docs/design/figma/learntrack-figma-handoff.md` § 1.1. If Figma MCP becomes available, use `figma::create_new_file` and `figma::generate_figma_design`.  
**Owner:** Designer / developer

---

### GAP-005 — No Google Stitch designs executed (informational)

**Impact:** Stitch-generated design references cannot be linked. Implementation must use the prompt specifications directly.  
**Remediation:** Execute prompts in `docs/design/google-stitch/learntrack-stitch-master-prompt.md` and `docs/design/google-stitch/course-progress-prompt.md` manually in Google Stitch.  
**Owner:** Designer

---

### GAP-006 — No GitHub Actions CI pipeline (non-blocking)

**Impact:** Automated CI on push/PR does not exist. Quality gates run locally only.  
**Remediation:** Execute tasks.md T-013. Use `.github/workflows/ci.yml` as described in `docs/quality-gates/learntrack-quality-gates.md`.  
**Owner:** Developer (Phase Group 6)

---

### GAP-007 — No automated accessibility test (non-blocking for MVP)

**Impact:** NFR-002 (content descriptions) and NFR-003 (contrast) are not enforced by automation.  
**Remediation:** Add `AccessibilityTest.kt` during T-012. Manual TalkBack review required before release.  
**Owner:** Developer / QA

---

### GAP-008 — No automated performance assertion (non-blocking for MVP)

**Impact:** NFR-001 (2-second load time) is not enforced by automation.  
**Remediation:** Add a timed assertion in `CourseProgressScreenTest.kt` during T-012.  
**Owner:** Developer / QA

---

### GAP-009 — `learntrack_topics.json` update not performed this phase

**Impact:** Deliverable 2 of this prompt (update the JSON if it exists) could not be executed because the file does not exist at any location in the repository.  
**Note:** The discovery report (§ 5) confirms the file was never created. The file is created in implementation phase (tasks.md T-005, kiro-prompt 08).  
**Remediation:** No action required at this stage. Create the file per T-005 after Gap-001 (scaffold) and Gap-002 (topic names) are resolved.  
**Owner:** Developer (Phase Group 6)

---

## 9. learntrack_topics.json — Status and Update Note

Per the discovery report (`docs/discovery/learntrack-discovery-report.md` § 5) and confirmed by a full repository file scan at the time this document was created:

**`learntrack_topics.json` does not exist anywhere in the repository.**

- Checked location: `app/src/main/assets/learntrack_topics.json` — **NOT FOUND** (`app/` directory does not exist)
- Checked entire repository tree — **no JSON file with this name at any path**

Because the file does not exist, Deliverable 2 of this phase (JSON status update) cannot be performed. This is consistent with the project's pre-scaffold state:

- The Android `app/` module directory has not been created yet (GAP-001)
- The topics data was never populated (GAP-002)

When the file is created (tasks.md T-005), it must conform to the schema in `.kiro/specs/course-progress-update/design.md` § 3 and satisfy the distribution verified by `scripts/check-before-push.ps1` checks QG-001 and QG-002.

---

## 10. Key Artifact Index

All currently-existing artifacts produced through Phase Group 5:

| Artifact | Path | Status |
|---|---|---|
| Discovery report | `docs/discovery/learntrack-discovery-report.md` | ACTUAL |
| Master prompt | `docs/learntrack-ai-sdlc-master-prompt.md` | ACTUAL |
| Scope document | `docs/scope/learntrack-scope.md` | ACTUAL |
| Architecture | `docs/architecture/learntrack-architecture.md` | ACTUAL |
| Data flow | `docs/architecture/learntrack-data-flow.md` | ACTUAL |
| Environments | `docs/architecture/learntrack-environments.md` | ACTUAL |
| Stitch master prompt | `docs/design/google-stitch/learntrack-stitch-master-prompt.md` | ACTUAL |
| Stitch screen prompt | `docs/design/google-stitch/course-progress-prompt.md` | ACTUAL |
| Stitch design review checklist | `docs/design/google-stitch/design-review-checklist.md` | ACTUAL |
| Figma handoff | `docs/design/figma/learntrack-figma-handoff.md` | ACTUAL |
| Figma design system | `docs/design/figma/learntrack-design-system.md` | ACTUAL |
| Figma screen inventory | `docs/design/figma/learntrack-screen-inventory.md` | ACTUAL |
| Jira epics CSV | `docs/jira/learntrack-epics.csv` | ACTUAL |
| Jira stories CSV | `docs/jira/learntrack-stories.csv` | ACTUAL |
| Jira subtasks CSV | `docs/jira/learntrack-subtasks.csv` | ACTUAL |
| Jira traceability CSV | `docs/jira/learntrack-traceability.csv` | ACTUAL |
| Build variants doc | `docs/build-variants/learntrack-build-variants.md` | ACTUAL |
| Environment configuration doc | `docs/build-variants/learntrack-environment-configuration.md` | ACTUAL |
| Quality gates doc | `docs/quality-gates/learntrack-quality-gates.md` | ACTUAL |
| MCP setup doc | `docs/setup/kiro-mcp-setup.md` | ACTUAL |
| Traceability document (this file) | `docs/traceability/learntrack-requirement-traceability.md` | ACTUAL |
| Kiro requirements spec | `.kiro/specs/course-progress-update/requirements.md` | ACTUAL |
| Kiro design spec | `.kiro/specs/course-progress-update/design.md` | ACTUAL |
| Kiro tasks spec | `.kiro/specs/course-progress-update/tasks.md` | ACTUAL |
| Kiro skill — repository analysis | `.kiro/skills/learntrack-repository-analysis/SKILL.md` | ACTUAL |
| Kiro skill — JSON integrity | `.kiro/skills/learntrack-json-integrity/SKILL.md` | ACTUAL |
| Kiro skill — progress validation | `.kiro/skills/learntrack-progress-validation/SKILL.md` | ACTUAL |
| Kiro skill — UI implementation | `.kiro/skills/learntrack-ui-implementation/SKILL.md` | ACTUAL |
| Kiro skill — testing quality gates | `.kiro/skills/learntrack-testing-quality-gates/SKILL.md` | ACTUAL |
| Kiro skill — Jira traceability | `.kiro/skills/learntrack-jira-traceability/SKILL.md` | ACTUAL |
| Kiro steering — product | `.kiro/steering/product.md` | ACTUAL |
| Kiro steering — tech | `.kiro/steering/tech.md` | ACTUAL |
| Kiro steering — structure | `.kiro/steering/structure.md` | ACTUAL |
| Implementation prompt chain (12 prompts) | `docs/kiro-prompts/01-*.md` through `12-*.md` | ACTUAL |
| Pre-push gate (PowerShell) | `scripts/check-before-push.ps1` | ACTUAL |
| Pre-push gate (Bash) | `scripts/check-before-push.sh` | ACTUAL |
| README | `README.md` | ACTUAL (stub) |
| **learntrack_topics.json** | `app/src/main/assets/learntrack_topics.json` | **NOT YET CREATED** |
| Android scaffold | `app/`, `build.gradle.kts`, `settings.gradle.kts`, etc. | **NOT YET CREATED** |
| Kotlin source files | `app/src/main/java/com/learntrack/**` | **NOT YET CREATED** |
| Unit test files | `app/src/test/java/com/learntrack/**` | **NOT YET CREATED** |
| UI test files | `app/src/androidTest/java/com/learntrack/**` | **NOT YET CREATED** |
| GitHub Actions CI | `.github/workflows/ci.yml` | **NOT YET CREATED** |

---

## 11. Next Steps to Close Gaps

In priority order:

1. **Create Android project scaffold** (tasks.md T-001) — use `docs/kiro-prompts/09-progress-ui-implementation.md`
2. **Confirm 41 topic names** for the AI & Python Technology Course (product owner decision — see Gap-002 and Gap-006 in discovery report)
3. **Create `learntrack_topics.json`** (tasks.md T-005) — use `docs/kiro-prompts/08-topic-json-update.md`
4. **Implement domain model and repository** (tasks.md T-002 through T-006)
5. **Implement use cases and ViewModel** (tasks.md T-007, T-008)
6. **Implement UI screens** (tasks.md T-009, T-010)
7. **Write and run unit tests** (tasks.md T-011)
8. **Write and run UI tests** (tasks.md T-012)
9. **Create GitHub Actions CI** (tasks.md T-013)
10. **Run release-readiness verification** (tasks.md T-014) — update this document with actual file paths and test results

---

*This document is generated from the actual repository state and must be updated as implementation progresses. Do not add predicted file paths to the ACTUAL column until the files have been verified to exist.*
