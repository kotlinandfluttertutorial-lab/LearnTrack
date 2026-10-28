# Prompt 07 — Kiro Specs and Skills

## Prerequisites

- Prompt 02 complete: `.kiro/specs/course-progress-update/requirements.md` exists.
- Prompt 05 complete: all three architecture docs exist in `docs/architecture/`.
- Prompt 06 complete: all four Jira CSV files exist in `docs/jira/`.
- Prompt 04 complete: all three Figma design files exist in `docs/design/figma/`.

## Specific Task

Create or update all Kiro specification files and all six Kiro skill files. Read each existing file before overwriting it. Do not remove content that is already correct.

### Kiro Spec Files

**`.kiro/specs/course-progress-update/requirements.md`** — Update if needed to reflect architecture and design decisions from Prompts 04 and 05. Do not remove existing requirements; only add or refine.

**`.kiro/specs/course-progress-update/design.md`** — Create or update with:
- Architecture overview (layers, key classes, data flow)
- UI component design (screens, states, composable signatures)
- Data model (`Topic`, `TopicStatus`, `CourseProgress`, `CourseUiState`)
- JSON validation approach
- Progress calculation approach
- Error handling strategy
- Testing strategy (which tests cover which requirements)
- References to Figma design system, Jira stories, and architecture docs

**`.kiro/specs/course-progress-update/tasks.md`** — Create or update with independently verifiable implementation tasks broken into small steps. Each task must specify:
- Task ID and title
- Input artifacts
- Output artifacts
- Acceptance criteria (verifiable, not vague)
- Reference to requirement(s) it satisfies
- Estimated effort (S/M/L)

Suggested task breakdown: scaffold setup, JSON file creation, data model, repository, use cases, ViewModel, UI screens (3), DI wiring, unit tests, UI tests, regression test, lint/build pass.

### Kiro Skill Files

Create all six skill files if they do not exist. If they already exist, read them first and update only what is incorrect or missing.

- `.kiro/skills/learntrack-repository-analysis/SKILL.md`
- `.kiro/skills/learntrack-json-integrity/SKILL.md`
- `.kiro/skills/learntrack-progress-validation/SKILL.md`
- `.kiro/skills/learntrack-ui-implementation/SKILL.md`
- `.kiro/skills/learntrack-testing-quality-gates/SKILL.md`
- `.kiro/skills/learntrack-jira-traceability/SKILL.md`

Each SKILL.md must follow this format:
```
---
name: <skill-name>
description: <one-sentence description>
version: 1.0.0
tags: [learntrack, ...]
---

## Purpose
## When to Use
## Required Inputs
## Step-by-Step Procedure
## Constraints
## Expected Outputs
## Verification Criteria
```

## Allowed Scope

- Read all prerequisite files listed above.
- Write to `.kiro/specs/course-progress-update/` (three files).
- Write to `.kiro/skills/learntrack-*/SKILL.md` (six files).
- Do not modify steering files, source code, or documentation outside these paths.

## Required Outputs

- `.kiro/specs/course-progress-update/requirements.md` (updated)
- `.kiro/specs/course-progress-update/design.md` (created/updated)
- `.kiro/specs/course-progress-update/tasks.md` (created/updated)
- All six SKILL.md files

## Acceptance Criteria

- [ ] `design.md` exists and references the Figma design system.
- [ ] `tasks.md` exists and contains at least 12 independently verifiable tasks.
- [ ] Each task in `tasks.md` references at least one FR or NFR from `requirements.md`.
- [ ] All six SKILL.md files exist with all required sections.
- [ ] No skill file contains medical or other domain-specific rules from sibling projects.
- [ ] All files reference the authoritative distribution: 41/22/19/53.7%.

## Stop Conditions

- Stop if architecture docs from Prompt 05 are missing — design spec depends on confirmed architecture.
- Stop if Jira CSV from Prompt 06 is missing — task IDs reference Jira story keys.
- Do not begin topic JSON implementation — that is Prompt 08.
