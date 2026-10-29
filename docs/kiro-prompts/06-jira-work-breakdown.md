# Prompt 06 — Jira Work Breakdown

## Prerequisites

- Prompt 02 complete: `.kiro/specs/course-progress-update/requirements.md` exists.
- Prompt 05 complete: `docs/architecture/learntrack-architecture.md` exists.
- `docs/scope/learntrack-scope.md` exists.

## Specific Task

Create four Jira-ready CSV files covering the complete feature lifecycle for the LearnTrack Course Progress Update feature. These are local CSV files only — no live Jira instance is accessed. Do not claim issues have been created in Jira; the deliverable is importable CSV files.

### Jira Key Scheme

Use exactly these keys (proposed identifiers, not live Jira keys):

| Key | Purpose |
|---|---|
| LT-001 | Repository discovery and baseline verification |
| LT-002 | Requirements and acceptance criteria |
| LT-003 | Google Stitch design preparation |
| LT-004 | Figma design system and handoff |
| LT-005 | Topic JSON update and validation |
| LT-006 | Progress calculation and data integrity |
| LT-007 | Course dashboard and topic-list UI |
| LT-008 | Automated tests and regression coverage |
| LT-009 | Kiro skills and implementation documentation |
| LT-010 | Build variants and quality gates |
| LT-011 | Release-readiness verification |

### Files to Create

**1. `docs/jira/learntrack-epics.csv`**

One epic row: `LEARNTRACK-EPIC-COURSE-PROGRESS`

Columns: `Issue Type, Summary, Description, Priority, Labels, Acceptance Criteria`

**2. `docs/jira/learntrack-stories.csv`**

One row per story (LT-001 through LT-011).

Columns: `Issue Key, Issue Type, Epic Link, Summary, Description, Acceptance Criteria, Priority, Labels, Story Points, Dependencies, Spec Reference, Design Reference, Test Reference`

Each story's Acceptance Criteria must reference the relevant requirements (FR-NNN or NFR-NNN) from `.kiro/specs/course-progress-update/requirements.md`.

**3. `docs/jira/learntrack-subtasks.csv`**

At least 2–3 subtasks per story where appropriate. Focus subtasks on stories LT-005 (JSON), LT-006 (calculation), LT-007 (UI), and LT-008 (testing).

Columns: `Issue Key, Issue Type, Parent, Summary, Description, Acceptance Criteria, Priority, Assignee, Labels`

Subtask keys follow pattern: `LT-005-1`, `LT-005-2`, etc.

**4. `docs/jira/learntrack-traceability.csv`**

One row per functional requirement (FR-001 through FR-015).

Columns: `Requirement ID, Requirement Summary, Jira Issue Key, Spec File, Design Reference, Implementation File, Test File, Status`

- `Status` for all rows: `Not Started` (implementation has not begun).
- `Implementation File` and `Test File`: use the target paths from `docs/architecture/learntrack-architecture.md` § Key File Paths; mark as `Pending`.

## Allowed Scope

- Read `.kiro/specs/course-progress-update/requirements.md`, `docs/scope/learntrack-scope.md`, and `docs/architecture/learntrack-architecture.md`.
- Write to `docs/jira/` (four files only).
- Do not modify any other file.

## Required Outputs

- `docs/jira/learntrack-epics.csv`
- `docs/jira/learntrack-stories.csv`
- `docs/jira/learntrack-subtasks.csv`
- `docs/jira/learntrack-traceability.csv`

## Acceptance Criteria

- [ ] All four CSV files exist under `docs/jira/`.
- [ ] Each CSV file has a valid header row and consistent column counts.
- [ ] Stories CSV contains exactly 11 rows (LT-001 through LT-011).
- [ ] Every story references the epic `LEARNTRACK-EPIC-COURSE-PROGRESS`.
- [ ] Traceability CSV contains one row for each of FR-001 through FR-015.
- [ ] No issue key outside LT-001 to LT-011 appears in any CSV.
- [ ] All Status values in traceability CSV are `Not Started`.
- [ ] All CSV files use proper quoting for fields containing commas.

## Stop Conditions

- Stop if requirements spec does not exist — stories cannot be written without requirements.
- Do not invent Jira keys beyond LT-011 without explicit user approval.
- Do not claim any CSV has been imported into a live Jira instance.
- Do not begin Kiro specs and skills work — that is Prompt 07.
