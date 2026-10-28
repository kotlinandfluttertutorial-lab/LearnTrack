---
name: learntrack-jira-traceability
description: Link LearnTrack requirements to Jira CSV work items, specifications, implementation files, and tests; validate Jira CSV structure; prevent fabricated issue IDs and incorrect completion reports.
version: 1.0.0
tags: [learntrack, jira, traceability, csv, requirements]
---

## Purpose

Maintain a verified chain of traceability from every requirement through design, Jira work items, implementation files, and automated tests. Ensure Jira CSV files are structurally valid and that no issue ID, completion status, or test result is invented or assumed.

---

## When to Use

- When creating or updating Jira CSV files (`docs/jira/`).
- When reviewing a Jira CSV before import to confirm it does not contain fabricated keys.
- When verifying that every functional requirement maps to at least one Jira story and one test.
- When producing the traceability matrix (`docs/traceability/learntrack-requirement-traceability.md`).
- When a downstream phase reports a Jira key that was not previously defined in the CSV.

---

## Required Inputs

| Input | Source | Required? |
|---|---|---|
| Requirements document | `.kiro/specs/course-progress-update/requirements.md` | Yes |
| Jira epics CSV | `docs/jira/learntrack-epics.csv` | Yes |
| Jira stories CSV | `docs/jira/learntrack-stories.csv` | Yes |
| Jira subtasks CSV | `docs/jira/learntrack-subtasks.csv` | Yes |
| Jira traceability CSV | `docs/jira/learntrack-traceability.csv` | Yes |
| Traceability matrix | `docs/traceability/learntrack-requirement-traceability.md` | Yes |
| Scope document | `docs/scope/learntrack-scope.md` | Yes |

---

## Step-by-Step Procedure

### Step 1 — Validate CSV file structure

For each Jira CSV file, check:

- File parses as valid CSV (RFC 4180 compliant: quoted fields, escaped commas, consistent column count).
- Header row is present and matches the expected schema for its type (see Step 2).
- No empty rows between data rows.
- All required fields are present and non-empty for every row.

Report: `PASS` / `FAIL — <file>: <violation>` for each file.

### Step 2 — Validate required CSV schemas

**`learntrack-epics.csv`** required columns:

`Issue Type, Summary, Description, Priority, Labels, Acceptance Criteria`

**`learntrack-stories.csv`** required columns:

`Issue Key, Issue Type, Epic Link, Summary, Description, Acceptance Criteria, Priority, Labels, Story Points, Dependencies, Spec Reference, Design Reference, Test Reference`

**`learntrack-subtasks.csv`** required columns:

`Issue Key, Issue Type, Parent, Summary, Description, Acceptance Criteria, Priority, Assignee, Labels`

**`learntrack-traceability.csv`** required columns:

`Requirement ID, Requirement Summary, Jira Issue Key, Spec File, Design Reference, Implementation File, Test File, Status`

Report any missing column as `FAIL — <file>: missing column "<column name>"`.

### Step 3 — Validate Jira issue key format

All issue keys in the CSV files must follow the pattern `LT-NNN` (e.g., `LT-001`, `LT-011`).

Permitted keys for this phase: `LT-001` through `LT-011` (11 stories, as defined in the master prompt).

- If a key outside this range appears: report `WARN — unexpected key <key>; confirm this was intentionally added`.
- If a key uses a different project prefix (e.g., `LEARNTRACK-001`, `LT-100`): report `FAIL — non-standard key format <key>`.
- If a key is referenced in the traceability CSV but not defined in the stories CSV: report `FAIL — orphaned reference <key>`.

### Step 4 — Validate no fabricated issue keys

Cross-reference every Jira key used across all files:

- Build a master list of keys defined in `learntrack-stories.csv` and `learntrack-epics.csv`.
- Check every key reference in `learntrack-subtasks.csv` and `learntrack-traceability.csv` against the master list.
- Any key that appears as a reference but is not defined in the master list is fabricated: report `FAIL — fabricated issue key <key> referenced in <file>`.

### Step 5 — Validate requirement coverage

For every Functional Requirement (FR-001 through FR-015) in `.kiro/specs/course-progress-update/requirements.md`:

- Confirm at least one row in `learntrack-traceability.csv` maps to that requirement ID.
- Confirm the mapped Jira key exists in the stories CSV.
- Confirm the `Test File` field in the traceability row is non-empty (or explicitly marked `N/A — data only` with justification).

Report any unmapped requirement as: `WARN — FR-NNN has no Jira traceability entry`.

### Step 6 — Validate completion status accuracy

The `Status` column in `learntrack-traceability.csv` must reflect actual state:

- `Not Started` — work not yet begun.
- `In Progress` — actively being worked.
- `Done` — implemented, tested, and verified.
- `Blocked` — cannot proceed; reason documented in Description.

Rules:
- Do not mark a requirement `Done` unless the implementation file exists AND the test file exists AND the test passes.
- Do not mark a test `Done` unless the test has been executed and the result is documented.
- Do not mark a Jira story `Done` unless all its subtasks are done.

If a `Done` status is found but the linked implementation or test file does not exist: report `FAIL — <Req ID> marked Done but <file> not found`.

### Step 7 — Validate traceability chain completeness

For each row in `learntrack-traceability.csv`, check that the chain is complete:

`Requirement → Jira Story → Spec File → Implementation File → Test File`

- `Spec File`: must reference a file in `.kiro/specs/` or `docs/`.
- `Implementation File`: must reference a file in `app/src/main/java/` (or marked `Pending` if not yet created).
- `Test File`: must reference a file in `app/src/test/` or `app/src/androidTest/` (or marked `Pending`).

Flag broken links (referenced files that do not exist and are not marked Pending) as: `WARN — <Req ID>: linked file <path> not found and not marked Pending`.

### Step 8 — Report result

```
Jira Traceability Validation
════════════════════════════════════════════════
Check                       Result    Notes
────────────────────────────────────────────────
Epics CSV structure         PASS/FAIL
Stories CSV structure       PASS/FAIL
Subtasks CSV structure      PASS/FAIL
Traceability CSV structure  PASS/FAIL
Issue key format            PASS/FAIL
No fabricated keys          PASS/FAIL
Requirement coverage        PASS/N warnings
Completion status accuracy  PASS/FAIL
Traceability chain          PASS/N warnings
────────────────────────────────────────────────
Overall                     PASS/FAIL
════════════════════════════════════════════════
```

---

## Constraints

- **Never invent Jira keys.** Only keys defined in the CSV files are valid. Do not reference `LT-012` or higher unless explicitly added by the user.
- **Never mark a Jira story Done based on documentation alone.** Implementation and test execution must both be verified.
- **CSVs represent local work items only.** These are not live Jira issues. Do not claim issues have been created in a Jira instance unless the user has explicitly confirmed that import.
- **Do not modify CSV files during validation.** Validation is read-only. Propose corrections separately.
- **Scope is LT-001 through LT-011 for this phase.** Any deviation requires explicit user approval.

---

## Expected Outputs

| Output | Description |
|---|---|
| Validation report | Structured table as shown in Step 8 |
| Fabricated key list | Any key found that is not defined in the stories CSV |
| Unmapped requirements | FR IDs with no traceability entry |
| Incorrect Done status list | Items marked Done with missing files or unexecuted tests |
| Broken link list | Referenced files that don't exist and aren't marked Pending |

---

## Verification Criteria

- [ ] Validation catches a key `LT-099` that appears in traceability but not in stories.
- [ ] Validation catches `FR-005` having no traceability row.
- [ ] Validation catches a story marked `Done` where the implementation file does not exist.
- [ ] Validation passes on a complete, correct set of CSVs with all 11 stories and all 15 FRs mapped.
- [ ] Validation does not modify any CSV file during execution.
- [ ] Report clearly distinguishes FAIL (blocking) from WARN (non-blocking but must be acknowledged).
