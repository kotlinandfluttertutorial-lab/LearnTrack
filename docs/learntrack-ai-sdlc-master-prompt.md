# LearnTrack — Extended AI-SDLC Master Prompt
## Google Stitch + Figma + Jira + Kiro + Skills + Quality Gates

### 11. End-to-End AI-SDLC Workflow

Extend the existing LearnTrack implementation with a complete AI-SDLC workflow.

The mandatory workflow is:

Requirements → Repository Discovery → Google Stitch → Figma → Architecture & Data Design → Jira → Kiro Specs → Kiro Skills → Implementation → Testing → Quality Gates → Release Readiness.

For every phase:

- Define inputs, outputs, and acceptance criteria.
- Preserve the existing LearnTrack architecture and functionality.
- Generate reusable artifacts instead of relying on chat-only instructions.
- Maintain traceability between requirements, designs, Jira issues, implementation, and tests.
- Do not mark a phase complete until its deliverables have been verified.
- Do not claim an external tool was used unless the action was actually performed.
- If an external integration is unavailable, generate copy-ready prompts and document the required manual steps.

### 12. Scope Management

Create a scope document before implementation.

**In scope**

- Update `learntrack_topics.json` using the specified topic status assignments.
- Preserve existing topic IDs, names, and unrelated tracking fields.
- Calculate completed, not-started, total-topic, and completion-percentage values from authoritative data.
- Update relevant course progress screens.
- Support validation, initialization, persistence, testing, and regression protection.
- Prepare the complete AI-SDLC documentation and tool handoffs.

**Out of scope unless the existing product requires them**

- Unrelated new course-management functionality.
- Unrequested authentication, payment, subscription, or administrative features.
- Unnecessary framework migrations or database replacements.
- Unrequested production deployment.
- Destructive changes to existing course records.

Document any additional scope discovered during repository analysis. Obtain approval before introducing significant new functionality.

### 12a. Pre-Phase Access Verification (NEW — added after LearnTrack retrospective)

Before starting the Figma phase, the Stitch phase, or the Jira phase, perform these checks:

**Figma access:**
1. Call `figma::whoami` to confirm the authenticated account and seat type.
2. Confirm the seat is **Full** (not View-only). View-only seats cannot use MCP write tools.
3. If no Figma file exists yet, call `figma::create_new_file` to create it — do not fabricate a file ID.
4. Record the real file key in all Figma docs immediately.

**Jira access:**
1. Call `atlassian::getAccessibleAtlassianResources` to get the real `cloudId`.
2. Search for the project (`project = <KEY> ORDER BY created`) to confirm the project key exists.
3. Use the actual project key (e.g., `LEAR`) in all CSVs and issue creation — do not use placeholder keys like `LT-001` as if they were real Jira keys.
4. Create issues via MCP when access is available. Clearly state "CSV only" when issues are not created live.

**Google Stitch access:**
1. Stitch does not have a verified MCP integration. Always treat Stitch as requiring manual execution.
2. Write copy-ready prompts. Do not claim a Stitch project was run unless you have the project URL and export files on disk.

**Topic names:**
Before scaffolding the Android project, confirm the exact names for all topics with the user. Topic names are a **required blocker** for creating `learntrack_topics.json`. Document confirmed names in the scope doc and tasks spec before T-005 begins.

---

### 13. Google Stitch — UI Generation

Create Google Stitch prompts based on the verified LearnTrack requirements and existing application platform.

First inspect whether LearnTrack is an Android app, web application, or another supported interface. Generate designs appropriate to that platform instead of assuming a mobile or web implementation.

Create:

`docs/design/google-stitch/learntrack-stitch-master-prompt.md`

`docs/design/google-stitch/course-progress-prompt.md`

`docs/design/google-stitch/design-review-checklist.md`

The prompts must specify:

- Product name: LearnTrack.
- Course name: AI & Python Technology Course.
- Completed topics: 22.
- Not-started topics: 19.
- Total topics: 41.
- Completion: 53.7%.
- Clear progress visualization.
- Topic-list status indicators.
- Consistent typography, spacing, colors, and component behavior.
- Loading, empty, error, and invalid-data states where applicable.
- Accessibility, readable contrast, and responsive behavior.
- Search, filtering, and topic-detail behavior only where consistent with existing functionality.

Design the course dashboard, progress summary, topic list, and relevant topic-detail states.

Do not fabricate lesson content or change the status rules to make a design look better.

**Deliverable:** Stitch prompts, generated-design references or exports when available, and a review checklist.

If Stitch access is unavailable, prepare the prompts for manual execution and document the handoff.

**LearnTrack Stitch project:** ID `683813527608164394` — recorded in `docs/design/google-stitch/learntrack-stitch-master-prompt.md`. HTML code exports are present in `docs/design/google-stitch/exports/`. PNG image files require re-export from the live Stitch project (PNG fetch failed during automated export).
- Course Dashboard (light + dark mode)
- Progress Summary (separate from dashboard — this is often missed)
- Topic List (light + dark mode)
- Topic Detail (completed variant + not-started variant)
- All edge states: loading skeleton, error, empty, invalid data

**Lesson learned (LearnTrack):** Progress Summary was not exported as a separate screen — only embedded as part of the dark-mode dashboard scroll. Always require it as an independent export.

### 14. Figma — Design System and Handoff

Use the approved Stitch concepts to prepare a Figma handoff.

Create:

`docs/design/figma/learntrack-figma-handoff.md`

`docs/design/figma/learntrack-design-system.md`

`docs/design/figma/learntrack-screen-inventory.md`

Define:

- Design tokens for colors, typography, spacing, borders, and component states.
- Reusable progress cards, progress bars, topic rows, status badges, and relevant navigation components.
- Completed, not-started, loading, empty, error, and accessibility states.
- Responsive layouts appropriate to the existing application platform.
- Component naming and variant conventions.
- Screen specifications and interaction notes.
- Design-to-implementation mapping.
- Design acceptance criteria.

Use Figma MCP only when the required server is available, correctly configured, and authorized.

If Figma MCP is unavailable, provide the exact Figma setup instructions and copy-ready design specifications. Do not invent Figma file IDs, links, exported assets, or successful MCP operations.

**Acceptance criteria**

- Every approved screen maps to one or more functional requirements.
- UI components use a consistent design system.
- The 53.7% progress state matches the authoritative course data.
- The implementation does not introduce visual or behavioral regressions.
- Dark mode variants exist for **all** primary screens — not only the dashboard.
- The Figma Design System file is created as a **separate file** from the UI Designs file. Color tokens, typography, and component specs live there.
- All Figma node IDs used in MCP calls are recorded in `learntrack-figma-handoff.md` so implementation agents can call `figma::get_design_context` without re-discovering them.

**Lesson learned (LearnTrack):** Dark mode variants were only created for the dashboard. Require dark mode for all screens up front. Also, record all node IDs in the handoff doc immediately after MCP inspection — saves re-discovery time in the implementation phase.

### 15. Architecture and Data Design

Create:

`docs/architecture/learntrack-architecture.md`

`docs/architecture/learntrack-data-flow.md`

`docs/architecture/learntrack-environments.md`

Inspect the existing architecture before making recommendations.

Document:

- Application layers and their responsibilities.
- Topic data model and status representation.
- Data source of truth.
- JSON import and validation workflow.
- Progress calculation flow.
- Persistence and synchronization behavior, if applicable.
- UI state management.
- Error handling and logging.
- Testing boundaries and integration points.
- Development, staging, and production configuration, where applicable.

Use the current framework, language, state-management approach, and persistence technology unless an approved requirement justifies a change.

Do not introduce a backend or database solely to implement the progress update.

### 16. Jira — Epics, Stories, Subtasks, and CSV

Create Jira-ready work items covering the complete feature lifecycle.

Proposed epic:

`LEARNTRACK-EPIC-COURSE-PROGRESS`

Suggested stories and tasks:

| Work item | Purpose |
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

These are proposed identifiers, not existing Jira issue keys. Use actual Jira keys if issues already exist.

**Important (lesson learned from LearnTrack):** Always call `atlassian::getAccessibleAtlassianResources` first to get the real `cloudId` and confirm the project key. Create issues via MCP when available — CSV-only is a fallback, not the default. The scaffold stories (T-001 Android project setup, T-002 navigation, T-003 Hilt) should have their own Jira stories, not just subtasks — they are significant enough to track independently.

Create:

`docs/jira/learntrack-epics.csv`

`docs/jira/learntrack-stories.csv`

`docs/jira/learntrack-subtasks.csv`

`docs/jira/learntrack-traceability.csv`

Each work item should include, where supported:

- Issue type
- Summary
- Description
- Acceptance criteria
- Priority
- Dependencies
- Labels
- Skills
- Relevant design references
- Specification reference
- Test requirements
- Definition of Done

Use valid CSV escaping and ensure the files can be imported into Jira with appropriate field mapping.

Map every requirement to its corresponding Jira work item, Kiro specification, implementation area, and verification test.

Do not invent remote Jira issue keys or claim issues have been created when only CSV files have been generated.

### 17. Kiro Specifications

Create or update:

`.kiro/specs/course-progress-update/requirements.md`

`.kiro/specs/course-progress-update/design.md`

`.kiro/specs/course-progress-update/tasks.md`

Requirements must contain functional and non-functional requirements, acceptance criteria, edge cases, and data-preservation constraints.

Design must document the actual architecture, data flow, UI changes, error handling, and testing strategy.

Tasks must be broken into small, independently verifiable implementation steps.

Include references to:

- Google Stitch prompts.
- Figma handoff and design system.
- Jira issues.
- Source files.
- Automated tests.
- Quality-gate documentation.

Use existing project conventions and update existing specifications when appropriate rather than duplicating them.

### 18. Kiro Steering Files

Inspect the existing `.kiro/steering/` directory before adding files.

Create or update the following where appropriate:

- `.kiro/steering/product.md`
- `.kiro/steering/tech.md`
- `.kiro/steering/structure.md`

Product steering must define LearnTrack's purpose, supported course-progress behavior, scope, and non-goals.

Technology steering must document the verified technology stack, coding standards, validation requirements, and security practices.

Structure steering must document actual repository organization, naming conventions, module boundaries, and artifact locations.

Keep project-wide steering concise and reusable. Feature-specific rules belong in the feature specification or skills.

### 19. Reusable Kiro Skills

Create reusable skills following the skill format supported by the installed Kiro version.

Inspect the repository and Kiro documentation before choosing exact frontmatter or configuration fields.

Suggested skills:

`.kiro/skills/learntrack-repository-analysis/SKILL.md`

Responsibilities:
- Analyze repository structure.
- Identify the authoritative data source.
- Detect relevant architecture and existing conventions.
- Produce a discovery report without modifying files prematurely.

`.kiro/skills/learntrack-json-integrity/SKILL.md`

Responsibilities:
- Validate JSON syntax and schema.
- Verify topic counts and status distribution.
- Preserve identifiers, names, and unrelated fields.
- Detect unexpected modifications before accepting changes.

`.kiro/skills/learntrack-progress-validation/SKILL.md`

Responsibilities:
- Calculate progress from source data.
- Verify the completed and not-started counts.
- Check percentage rounding.
- Validate consistency between the UI and underlying data.

`.kiro/skills/learntrack-ui-implementation/SKILL.md`

Responsibilities:
- Follow approved Stitch and Figma designs.
- Reuse existing components and design tokens.
- Implement accessible loading, empty, error, and progress states.
- Avoid unrelated UI redesigns.

`.kiro/skills/learntrack-testing-quality-gates/SKILL.md`

Responsibilities:
- Execute applicable automated tests and build checks.
- Detect regressions.
- Verify data preservation.
- Report actual results without claiming unexecuted checks passed.

`.kiro/skills/learntrack-jira-traceability/SKILL.md`

Responsibilities:
- Link requirements to Jira, specifications, code, and tests.
- Validate Jira CSV structure.
- Prevent fabricated issue IDs and incorrect completion reports.

Every skill must include its purpose, when to use it, required inputs, step-by-step procedure, constraints, expected outputs, and verification criteria.

Skills must be actionable, reusable, and specific to LearnTrack. Avoid copying unrelated MediHistory AI medical rules into these skills.

### 20. Kiro Implementation Prompt Chain

Create copy-ready prompts in:

`docs/kiro-prompts/`

Required prompts:

1. `01-repository-discovery.md`
2. `02-requirements-and-scope.md`
3. `03-google-stitch-ui-design.md`
4. `04-figma-design-handoff.md`
5. `05-architecture-and-data-design.md`
6. `06-jira-work-breakdown.md`
7. `07-kiro-specs-and-skills.md`
8. `08-topic-json-update.md`
9. `09-progress-ui-implementation.md`
10. `10-automated-testing.md`
11. `11-build-and-quality-gates.md`
12. `12-release-readiness.md`

Each prompt must include its prerequisites, specific task, allowed scope, required outputs, acceptance criteria, and stop conditions.

Later prompts must reference artifacts produced by earlier prompts. Avoid generating independent prompts that contradict each other.

### 21. MCP Configuration

Inspect the existing `.kiro/settings/mcp.json`.

Determine whether the project requires GitHub, Figma, documentation, Git/filesystem, or other MCP integrations based on actual workflows.

Create or update:

`docs/setup/kiro-mcp-setup.md`

If necessary, prepare a project-specific MCP configuration using only supported server configurations and verified documentation.

Requirements:

- Use least-privilege access.
- Never include access tokens, passwords, or credentials in committed configuration.
- Prefer environment-variable references and approved authentication flows.
- Keep write access disabled or restricted when read-only access is sufficient.
- Document the purpose of each MCP server.
- Validate JSON syntax and server configuration.
- Separate development-only database access from production.
- Do not add a database MCP unless the existing architecture and task justify it.
- Do not assume Stitch or Figma MCP access exists without verification.

MCP configuration must be optional for ordinary implementation and testing when the repository can be handled locally.

### 22. Build Variants and Environments

Create:

`docs/build-variants/learntrack-build-variants.md`

`docs/build-variants/learntrack-environment-configuration.md`

Inspect the existing build system first.

Document applicable environments such as:

- Local development.
- Local containerized development, if supported.
- Staging, if configured.
- Production, if configured.

For each existing environment, record:

- Purpose.
- Build or launch commands.
- Configuration source.
- Environment-variable names.
- API endpoint selection, if applicable.
- Logging and debug settings.
- Data source and persistence behavior.
- Test execution requirements.
- Deployment restrictions.

If LearnTrack is an Android project, document existing Gradle build types and product flavors where appropriate.

If LearnTrack is a web or backend project, document the equivalent supported build and runtime configuration.

Do not create unnecessary flavors, cloud services, or deployment environments just to match another project's architecture.

Keep development and production configuration separate. Never embed secrets in source code.

### 23. Pre-Push Quality Gates

Create or update:

- `scripts/check-before-push.ps1`
- `scripts/check-before-push.sh`
- `docs/quality-gates/learntrack-quality-gates.md`

Inspect the existing CI pipeline and available tooling before implementing the scripts.

The scripts must run the relevant project checks, which may include:

1. JSON syntax and schema validation.
2. Topic status and count validation.
3. Unit tests.
4. Integration tests.
5. UI tests where supported.
6. Lint and formatting checks.
7. Type checking where supported.
8. Application build.
9. Diff and secret checks using existing tooling.
10. Git working-tree and commit-readiness checks.

Requirements:

- Use commands supported by the actual repository.
- Fail when a required check fails.
- Return a non-zero exit code on failure.
- Report which check failed.
- Stop subsequent checks when continuing would be unsafe.
- Avoid silently skipping required checks.
- Support Windows PowerShell and Bash where feasible.
- Do not automatically push, deploy, or delete files.
- Clearly document optional checks and unavailable dependencies.
- Ensure scripts are safe to rerun.

If an existing check-before-push script exists, extend it rather than replacing it without review.

### 24. CI/CD and Release Readiness

Inspect the existing CI/CD configuration.

Update the pipeline only where required to enforce the agreed quality gates.

The release-readiness process must:

- Validate the JSON and topic distribution.
- Run automated tests.
- Build the application.
- Check relevant dependency and configuration issues.
- Verify that the approved design is reflected in the UI.
- Confirm that all acceptance criteria are addressed.
- Produce a report of passed, failed, skipped, and blocked checks.
- Keep deployment separate from validation.
- Require explicit approval for production deployment or destructive data operations.

Do not introduce a new cloud platform or CI provider without a demonstrated requirement.

### 25. End-to-End Traceability

Create:

`docs/traceability/learntrack-requirement-traceability.md`

Maintain the following chain:

Requirement ID
→ Stitch screen or design requirement
→ Figma component or screen
→ Jira issue
→ Kiro specification and task
→ Implementation file
→ Automated test
→ Quality-gate result.

Not every requirement needs a UI design. Data integrity and backend requirements may link directly to architecture, code, and tests.

Flag missing links rather than inventing them.

### 26. Final Deliverables Checklist

Before reporting completion, verify the applicable deliverables.

- [ ] Corrected `learntrack_topics.json`.
- [ ] Repository discovery report.
- [ ] Requirements, design, and task specifications.
- [ ] Google Stitch prompts and design review checklist.
- [ ] Figma design handoff and design-system documentation.
- [ ] Jira epics, stories, subtasks, and traceability CSV files.
- [ ] Reusable Kiro skills.
- [ ] Kiro implementation prompt chain.
- [ ] Updated steering files.
- [ ] Architecture and data-flow documentation.
- [ ] Build variants and environment documentation.
- [ ] PowerShell pre-push quality gate.
- [ ] Bash pre-push quality gate.
- [ ] Relevant CI/CD checks.
- [ ] Automated tests and actual test results.
- [ ] Requirement-to-design-to-code-to-test traceability.
- [ ] Release-readiness report.

Mark a deliverable complete only when its actual file or external artifact has been verified. Clearly distinguish generated prompts from designs that have actually been created, and Jira CSV files from issues actually created in Jira.

### 27. Final Execution Instructions

Start with repository discovery. Then proceed incrementally through requirements, design, planning, implementation, testing, and release readiness.

At each phase:

1. Read existing project guidance and related files.
2. Reuse existing artifacts where possible.
3. Produce the phase deliverables.
4. Validate the deliverables.
5. Record unresolved decisions and dependencies.
6. Proceed when required prerequisites are satisfied.

For external tools that require user access or manual action, prepare the handoff and wait for the necessary access rather than claiming completion.

Do not stop at documentation when the actual implementation can safely proceed. Do not modify unrelated features, overwrite existing user data, fabricate external resources, or deploy without authorization.

The final report must summarize actual changes, verified results, missing integrations, outstanding approvals, and the remaining work required for release.

---

### 28. Lessons Learned — Append After Each Project (NEW)

After completing a project with this prompt, append findings here so the next project benefits.

**LearnTrack — October 2026:**

1. **Figma MCP seat requirement:** MCP write tools require a Full seat. Always check `figma::whoami` seat field before the Figma phase. View-only accounts silently fail.

2. **Topic names are a blocker:** The topic list for the JSON file was not confirmed before documentation. This created a dependency gap noted in the discovery report. Always get content confirmed before T-005.

3. **Progress Summary needs its own Stitch export:** Stitch exported it only as part of the long dashboard scroll. Explicitly request it as a standalone screen.

4. **Jira project key must be discovered first:** Using placeholder keys like `LT-001` in docs before knowing the real Jira project key (e.g., `LEAR`) creates confusion. Discover the key in the pre-phase check and use it everywhere from the start.

5. **Android scaffold is a story, not just subtasks:** T-001 (Gradle scaffold), T-002 (theme/nav), T-003 (Hilt) are substantial enough for their own Jira stories. Subtasks alone make them invisible on the board.

6. **JSON schema wrapper object:** The pre-push scripts initially assumed a flat JSON array. The actual schema `{"topics": [...]}` requires `.topics` access. Always specify the exact schema shape in the quality gate scripts and test them against a real file before committing.

7. **Record Figma node IDs immediately:** After MCP inspection, record all node IDs (`page:section`, `frame`) in the handoff doc. Implementation agents need these to call `figma::get_design_context` without re-discovery.

8. **Dark mode for all screens:** Only the dashboard got a dark variant in Figma. Require dark mode frames for all screens in the Stitch prompt and Figma handoff from the start.
