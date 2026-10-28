# Prompt 12 — Release Readiness

## Prerequisites

- All Prompts 01–11 complete.
- `./gradlew testDebugUnitTest` passing with 0 failures.
- `./gradlew lintDebug` reporting 0 errors.
- `./gradlew assembleDebug` succeeding.
- `learntrack_topics.json` validated at 41/22/19.

## Specific Task

Verify that every deliverable from the master prompt exists and passes its acceptance criteria. Produce a release-readiness report. Update the deliverables checklist. Do not claim a deliverable is complete if its file does not exist or its acceptance criteria have not been verified.

### Step 1 — Verify all deliverables

Check each item in the master prompt's deliverables list (§ 26). For each item, record:
- **File path(s)** expected
- **Exists?** (yes / no)
- **Acceptance criteria met?** (yes / no / partial — with specific gaps)

Use this checklist:

| # | Deliverable | File(s) | Exists | AC Met |
|---|---|---|---|---|
| 1 | `learntrack_topics.json` | `app/src/main/assets/learntrack_topics.json` | | |
| 2 | Discovery report | `docs/discovery/learntrack-discovery-report.md` | | |
| 3 | Requirements spec | `.kiro/specs/course-progress-update/requirements.md` | | |
| 4 | Design spec | `.kiro/specs/course-progress-update/design.md` | | |
| 5 | Tasks spec | `.kiro/specs/course-progress-update/tasks.md` | | |
| 6 | Scope document | `docs/scope/learntrack-scope.md` | | |
| 7 | Stitch master prompt | `docs/design/google-stitch/learntrack-stitch-master-prompt.md` | | |
| 8 | Stitch course prompt | `docs/design/google-stitch/course-progress-prompt.md` | | |
| 9 | Design review checklist | `docs/design/google-stitch/design-review-checklist.md` | | |
| 10 | Figma handoff | `docs/design/figma/learntrack-figma-handoff.md` | | |
| 11 | Figma design system | `docs/design/figma/learntrack-design-system.md` | | |
| 12 | Figma screen inventory | `docs/design/figma/learntrack-screen-inventory.md` | | |
| 13 | Architecture doc | `docs/architecture/learntrack-architecture.md` | | |
| 14 | Data flow doc | `docs/architecture/learntrack-data-flow.md` | | |
| 15 | Environments doc | `docs/architecture/learntrack-environments.md` | | |
| 16 | Jira epics CSV | `docs/jira/learntrack-epics.csv` | | |
| 17 | Jira stories CSV | `docs/jira/learntrack-stories.csv` | | |
| 18 | Jira subtasks CSV | `docs/jira/learntrack-subtasks.csv` | | |
| 19 | Jira traceability CSV | `docs/jira/learntrack-traceability.csv` | | |
| 20 | Repo analysis skill | `.kiro/skills/learntrack-repository-analysis/SKILL.md` | | |
| 21 | JSON integrity skill | `.kiro/skills/learntrack-json-integrity/SKILL.md` | | |
| 22 | Progress validation skill | `.kiro/skills/learntrack-progress-validation/SKILL.md` | | |
| 23 | UI implementation skill | `.kiro/skills/learntrack-ui-implementation/SKILL.md` | | |
| 24 | Testing quality gates skill | `.kiro/skills/learntrack-testing-quality-gates/SKILL.md` | | |
| 25 | Jira traceability skill | `.kiro/skills/learntrack-jira-traceability/SKILL.md` | | |
| 26 | Steering: product | `.kiro/steering/product.md` | | |
| 27 | Steering: tech | `.kiro/steering/tech.md` | | |
| 28 | Steering: structure | `.kiro/steering/structure.md` | | |
| 29 | Kiro prompt chain | `docs/kiro-prompts/01-*.md` through `12-*.md` | | |
| 30 | Build variants doc | `docs/build-variants/learntrack-build-variants.md` | | |
| 31 | Environment config doc | `docs/build-variants/learntrack-environment-configuration.md` | | |
| 32 | Quality gates doc | `docs/quality-gates/learntrack-quality-gates.md` | | |
| 33 | MCP setup doc | `docs/setup/kiro-mcp-setup.md` | | |
| 34 | Traceability matrix | `docs/traceability/learntrack-requirement-traceability.md` | | |
| 35 | PowerShell pre-push script | `scripts/check-before-push.ps1` | | |
| 36 | Bash pre-push script | `scripts/check-before-push.sh` | | |
| 37 | GitHub Actions CI | `.github/workflows/*.yml` | | |
| 38 | Unit tests | `app/src/test/java/com/learntrack/` | | |
| 39 | UI tests | `app/src/androidTest/java/com/learntrack/` | | |
| 40 | Test results | Actual Gradle output from Prompt 10/11 | | |

### Step 2 — Run final quality gate

Re-run the pre-push quality gate script:

```powershell
scripts/check-before-push.ps1
```

Record the output verbatim.

### Step 3 — Produce release-readiness report

Write a release-readiness report to `docs/learntrack-deliverables-checklist.md` with:
- Summary: total deliverables, complete, partial, missing.
- Quality gate: overall PASS/FAIL with all check results.
- Outstanding items: list any deliverable that is missing or has unmet AC, with the specific gap.
- Remaining work: list any action required before the feature can be considered release-ready.
- Explicit statements for: "JSON data validated", "Unit tests passing", "Build passing", "No hard-coded progress constants".
- Any integrations that are manual-only (Figma, Stitch, Jira live import) — distinguished clearly from automated deliverables.

### Step 4 — Final data integrity confirmation

Verify one last time:
- `learntrack_topics.json` has 41 topics, 22 completed, 19 not-started.
- No application source file contains the literal values `53.7`, `22f`, `19f`, or `41` as constants (grep `app/src/main/`).
- The displayed percentage in the running app will be "53.7%" derived from live data.

## Allowed Scope

- Read all files listed in Step 1.
- Run scripts and Gradle commands.
- Write to `docs/learntrack-deliverables-checklist.md`.
- Do not modify source code, JSON data, or specification files.
- Do not deploy the application or push to remote.

## Required Outputs

- `docs/learntrack-deliverables-checklist.md` — completed checklist with actual pass/fail/pending status for all 40 deliverables.
- Release-readiness summary (overall ready / not ready, with outstanding items).

## Acceptance Criteria

- [ ] Deliverables checklist covers all 40 items with actual status (not assumed).
- [ ] Final quality gate run produces a documented result.
- [ ] Any missing deliverable is flagged by name — not silently omitted.
- [ ] Report clearly distinguishes "complete" from "pending manual action" (e.g., Figma file manually created vs. automated).
- [ ] No item is marked complete unless its file exists and its acceptance criteria are verified.
- [ ] Report states whether the feature is release-ready, and if not, what remains.

## Stop Conditions

- This is the final prompt in the chain. Do not begin new feature work.
- If critical items are missing (JSON invalid, tests failing, build broken), mark the feature NOT release-ready and list the blockers.
- Do not claim release readiness unless the quality gate overall result is PASS.
