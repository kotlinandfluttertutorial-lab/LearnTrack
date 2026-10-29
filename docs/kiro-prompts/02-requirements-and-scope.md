# Prompt 02 — Requirements and Scope

## Prerequisites

- Prompt 01 complete: `docs/discovery/learntrack-discovery-report.md` exists and identifies the platform.
- `docs/learntrack-ai-sdlc-master-prompt.md` has been read.

## Specific Task

Using the discovery report and master prompt as inputs, produce two documents:

1. **Scope document** — `docs/scope/learntrack-scope.md`
2. **Kiro requirements spec** — `.kiro/specs/course-progress-update/requirements.md`

Read the discovery report first. Use the confirmed platform and stack to ensure all requirements and scope items are platform-appropriate (Android / Kotlin / Compose — or whatever the discovery report confirms).

### Scope Document

The scope document must include:

- Project purpose and summary
- In-scope features (course data, UI screens, validation, testing, documentation)
- Out-of-scope features with reasons
- Discovered platform and its implications
- Additional scope discovered during repository analysis (with disposition)
- Acceptance criteria for the Course Progress Update feature (data integrity, calculation, dashboard, topic list, detail, accessibility, regression, build/test)
- Constraints
- Assumptions
- Open decisions

### Requirements Spec

The requirements spec must include functional requirements, non-functional requirements, acceptance criteria, edge cases, and data-preservation constraints for the Course Progress Update feature.

Authoritative data for all documents:

- Course: AI & Python Technology Course
- Total topics: 41
- Completed: 22
- Not-started: 19
- Completion percentage: 53.7% (calculated as `22/41 * 100`, rounded to 1 decimal)

## Allowed Scope

- Read `docs/discovery/learntrack-discovery-report.md` and `docs/learntrack-ai-sdlc-master-prompt.md`.
- Write to `docs/scope/learntrack-scope.md`.
- Write to `.kiro/specs/course-progress-update/requirements.md`.
- Do not modify any other file.

## Required Outputs

- `docs/scope/learntrack-scope.md`
- `.kiro/specs/course-progress-update/requirements.md`

## Acceptance Criteria

- [ ] Scope document lists all in-scope features from the master prompt (§ 12).
- [ ] Scope document lists all out-of-scope items with explicit reasons.
- [ ] Requirements spec contains at minimum FR-001 through FR-015 (or equivalent coverage of all feature behaviors).
- [ ] Requirements spec includes non-functional requirements for performance, accessibility, data integrity, and test coverage.
- [ ] Requirements spec includes all edge cases from the master prompt.
- [ ] All documents use the authoritative distribution: 41/22/19/53.7%.
- [ ] Both files reference `docs/discovery/learntrack-discovery-report.md` as a source.

## Stop Conditions

- Stop if `docs/discovery/learntrack-discovery-report.md` does not exist — run Prompt 01 first.
- Stop if platform determination in the discovery report is ambiguous — resolve it before writing platform-specific requirements.
- Do not begin design or architecture work — that is Prompts 03–05.
