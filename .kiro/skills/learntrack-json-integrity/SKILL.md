---
name: learntrack-json-integrity
description: Validate learntrack_topics.json syntax and schema, verify the authoritative topic distribution (22 completed / 19 not-started / 41 total / 53.7%), preserve all identifiers and names, and detect unexpected modifications before accepting changes.
version: 1.0.0
tags: [learntrack, json, validation, data-integrity, android]
---

## Purpose

Ensure `learntrack_topics.json` is syntactically valid, schema-compliant, and contains exactly the authoritative topic distribution. Catch any silent drift in topic counts, IDs, names, or status values before a change is committed or accepted.

---

## When to Use

- Before and after any edit to `learntrack_topics.json`.
- During the pre-push quality gate script execution.
- When a downstream phase reports unexpected progress values.
- When reviewing a proposed change to topic status assignments.
- As part of any CI validation step that touches course data.

---

## Required Inputs

| Input | Source | Required? |
|---|---|---|
| `learntrack_topics.json` | `app/src/main/assets/learntrack_topics.json` | Yes |
| Authoritative distribution | This skill (hardcoded reference values) | Yes |
| Previous known-good snapshot (for diff check) | Git history or prior validation run | No — used when detecting unexpected modifications |

---

## Step-by-Step Procedure

### Step 1 — Locate the file

Confirm `learntrack_topics.json` exists at `app/src/main/assets/learntrack_topics.json`.

- If absent: report `FAIL — learntrack_topics.json not found at expected path`. Stop.
- If present at an unexpected path: report the actual path and flag it as a structural violation against `structure.md`.

### Step 2 — Validate JSON syntax

Parse the file as JSON.

- The file must parse without errors. No trailing commas, no comments, no BOM.
- If parsing fails: report `FAIL — JSON syntax error at line N: <message>`. Stop.

### Step 3 — Validate top-level structure

The top-level value must be a JSON object containing a `topics` key whose value is a JSON array.

```json
{
  "topics": [ ... ]
}
```

- If `topics` key is absent: report `FAIL — missing top-level "topics" array`.
- If `topics` is not an array: report `FAIL — "topics" is not an array`.

### Step 4 — Validate total topic count

Count all entries in the `topics` array.

- **Expected:** exactly 41.
- If count ≠ 41: report `FAIL — topic count is N, expected 41`.

### Step 5 — Validate each topic object

For every topic in the array, check:

| Field | Rule |
|---|---|
| `id` | Present, non-empty string |
| `name` | Present, non-empty string |
| `status` | Present, value is exactly `"completed"` or `"not_started"` |

Report every violation found (do not stop at first):

- `FAIL — topic[N].id is missing or empty`
- `FAIL — topic[N].name is missing or empty`
- `FAIL — topic[N].status is "<value>", expected "completed" or "not_started"`

### Step 6 — Validate unique IDs

Collect all `id` values. Check for duplicates.

- If any `id` appears more than once: report `FAIL — duplicate topic id "<id>" at indices N and M`.

### Step 7 — Validate status distribution

Count topics by status:

```
completed_count  = count where status == "completed"
not_started_count = count where status == "not_started"
```

- **Expected completed:** exactly 22. If ≠ 22: `FAIL — completed count is N, expected 22`.
- **Expected not-started:** exactly 19. If ≠ 19: `FAIL — not-started count is N, expected 19`.
- Verify: `completed_count + not_started_count == total_count`. If not: `FAIL — status counts do not sum to total`.

### Step 8 — Validate percentage (informational)

Compute: `(22 / 41) * 100 = 53.658...%`, displayed as `53.7%`.

This step is informational — it confirms the expected displayed value matches the authoritative distribution. No file change is required here.

### Step 9 — Detect unexpected identity modifications (diff check)

If a prior known-good snapshot is available (from git or a prior run):

- Compare every topic's `id` and `name` against the snapshot.
- If any `id` or `name` has changed: report `WARN — topic[N] id/name changed from "<old>" to "<new>"`. This requires an explicit documented decision before acceptance.
- Do not block on this step if no snapshot is available; record "diff check skipped — no baseline".

### Step 10 — Report result

Produce a validation summary:

```
learntrack_topics.json validation
─────────────────────────────────
Syntax:       PASS
Structure:    PASS
Total topics: 41 ✓
Completed:    22 ✓
Not-started:  19 ✓
Unique IDs:   PASS
Schema:       PASS
Diff check:   PASS / SKIPPED / N warnings

Overall: PASS / FAIL
```

If any step produced a FAIL, the overall result is FAIL. A WARN does not cause FAIL but must be acknowledged before the change is merged.

---

## Constraints

- **Never modify the JSON file** during validation. This skill is read-only with respect to `learntrack_topics.json`.
- **Report all failures**, not just the first. A single validation run should surface every problem.
- **Do not accept a partial count as passing.** If completed = 21 and not-started = 20, that is two failures, not one.
- **Do not silently coerce or fix the file.** If the file is invalid, report it and stop. Fixing is a separate task.
- **The authoritative distribution is fixed for this phase:** 41 total, 22 completed, 19 not-started. Any change to these reference values requires explicit user approval and an update to this skill.

---

## Expected Outputs

| Output | Description |
|---|---|
| Validation summary | Structured pass/fail report as shown in Step 10 |
| Failure details | Specific error messages for every violation found |
| Diff warnings | List of any identity field changes detected vs. baseline |
| Overall result | `PASS` or `FAIL` (single exit status for scripting) |

---

## Verification Criteria

- [ ] Validation correctly identifies a file with 21 completed topics as FAIL.
- [ ] Validation correctly identifies a file with 20 not-started topics as FAIL.
- [ ] Validation correctly identifies a topic with `status: "in_progress"` as FAIL.
- [ ] Validation correctly identifies a topic with a missing `id` as FAIL.
- [ ] Validation correctly identifies duplicate IDs as FAIL.
- [ ] Validation passes on a well-formed file with exactly 41/22/19 distribution.
- [ ] Validation does not modify the file under any circumstances.
- [ ] Overall PASS is only reported when all required checks pass.
