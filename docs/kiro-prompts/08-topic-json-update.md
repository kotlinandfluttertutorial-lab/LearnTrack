# Prompt 08 — Topic JSON Update

## Prerequisites

- Prompt 01 complete: `docs/discovery/learntrack-discovery-report.md` identifies the JSON file location (or confirms it does not exist).
- Prompt 02 complete: `.kiro/specs/course-progress-update/requirements.md` defines FR-001, FR-002, FR-003.
- Prompt 05 complete: `docs/architecture/learntrack-architecture.md` defines the JSON schema.

## Specific Task

Create or update `learntrack_topics.json` so it contains exactly 41 topics for the AI & Python Technology Course, with exactly 22 marked `completed` and 19 marked `not_started`.

### If the file does not exist

Create it at `app/src/main/assets/learntrack_topics.json` with 41 topic objects.

The schema is:
```json
{
  "topics": [
    { "id": "topic-001", "name": "<topic name>", "status": "completed" },
    ...
  ]
}
```

Topic IDs must follow a consistent pattern (e.g., `topic-001` through `topic-041`). Topic names must be meaningful course topic titles for an AI & Python Technology Course. Suggested topic areas to draw from:

- Python fundamentals (variables, data types, control flow, functions, OOP)
- Python data structures (lists, dicts, sets, tuples)
- File I/O and modules
- NumPy and Pandas
- Data visualization (Matplotlib, Seaborn)
- Machine learning concepts (supervised, unsupervised)
- scikit-learn (regression, classification, clustering)
- Neural networks and deep learning basics
- TensorFlow or PyTorch introduction
- Natural language processing basics
- Model evaluation and validation
- AI ethics and responsible AI
- Capstone and project topics

Assign the first 22 topics (by ID order) status `"completed"` and the remaining 19 status `"not_started"`. This assignment is authoritative for this phase.

### If the file already exists

- Read the file first.
- Preserve every existing topic `id` and `name` exactly as-is. Do not rename, reorder, or reassign IDs.
- Update only the `status` fields to achieve the required distribution: 22 `completed`, 19 `not_started`.
- Do not add or remove topics unless the current count is not 41.
- If the count is not 41, stop and report the discrepancy — do not silently add or remove topics.

### After writing the file

Run the JSON integrity validation (from the `learntrack-json-integrity` skill or equivalent):

1. Parse as valid JSON — no syntax errors.
2. Top-level `topics` array present.
3. Exactly 41 entries.
4. Each entry has non-empty `id`, non-empty `name`, and `status` in `{"completed", "not_started"}`.
5. No duplicate `id` values.
6. Exactly 22 `completed`, exactly 19 `not_started`.
7. Computed percentage: `22 / 41 * 100 = 53.658...% → 53.7%`.

Record all validation results.

## Allowed Scope

- Read `docs/discovery/learntrack-discovery-report.md` and `docs/architecture/learntrack-architecture.md`.
- Write to `app/src/main/assets/learntrack_topics.json` only.
- Do not modify any other file, including source code or documentation.

## Required Outputs

- `app/src/main/assets/learntrack_topics.json` containing 41 topics (22 completed, 19 not-started).
- Inline validation report confirming all 7 checks pass.

## Acceptance Criteria

- [ ] File exists at `app/src/main/assets/learntrack_topics.json`.
- [ ] File parses as valid JSON.
- [ ] Exactly 41 topics in the `topics` array.
- [ ] Every topic has a unique non-empty `id`.
- [ ] Every topic has a non-empty `name`.
- [ ] Every topic `status` is `"completed"` or `"not_started"`.
- [ ] Exactly 22 topics have `status: "completed"`.
- [ ] Exactly 19 topics have `status: "not_started"`.
- [ ] If the file previously existed, no `id` or `name` was altered.
- [ ] Validation report shows all 7 checks as PASS.

## Stop Conditions

- Stop if `app/src/main/assets/` directory does not exist — the Android scaffold (Prompt scaffold task) must be created first, or the directory must be created as part of this step.
- Stop and report if the existing file has a topic count other than 41 — do not silently adjust.
- Stop and report if any validation check fails after writing — do not proceed to Prompt 09 with a failing JSON file.
- Do not modify source code — that is Prompt 09.
