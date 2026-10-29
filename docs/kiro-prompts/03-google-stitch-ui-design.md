# Prompt 03 — Google Stitch UI Design

## Prerequisites

- Prompt 01 complete: `docs/discovery/learntrack-discovery-report.md` exists.
- Prompt 02 complete: `docs/scope/learntrack-scope.md` and `.kiro/specs/course-progress-update/requirements.md` exist.
- Platform confirmed as Android (or whatever the discovery report determined — use that platform for all design decisions).

## Specific Task

Create three Google Stitch prompt files for the LearnTrack course progress feature. These are copy-ready prompts to be submitted to Google Stitch (manually, if Stitch is not available via MCP). Do not claim designs have been generated unless you have actually submitted the prompts and received output.

### Files to Create

**1. `docs/design/google-stitch/learntrack-stitch-master-prompt.md`**

A master Stitch prompt covering the full LearnTrack app context:
- Product name: LearnTrack
- Platform: Android mobile (portrait primary)
- Course: AI & Python Technology Course
- Data: 22 completed, 19 not-started, 41 total, 53.7% completion
- Design language: Material 3
- Required screens: Course Dashboard, Topic List, Topic Detail
- Required states: Loading, Empty, Error, Success
- Typography, spacing, color, and component conventions

**2. `docs/design/google-stitch/course-progress-prompt.md`**

A focused Stitch prompt for the Course Progress Dashboard screen:
- Completion percentage display: "53.7%"
- Progress bar visualization
- "22 of 41 topics" count display
- Navigation to topic list
- Loading state (spinner)
- Error state (friendly message, no technical details)
- Empty state (no fabricated progress data)
- Accessibility notes (content descriptions, contrast)

**3. `docs/design/google-stitch/design-review-checklist.md`**

A checklist for reviewing generated Stitch designs:
- Does the dashboard show 53.7% (not a different value)?
- Does the progress bar fill match the percentage?
- Are loading, empty, and error states distinct and complete?
- Are all 41 topic rows visible in the topic list?
- Do status badges clearly distinguish Completed from Not Started?
- Does the design use Material 3 components?
- Are touch targets at least 48 × 48 dp?
- Is contrast ratio sufficient for WCAG 2.1 AA?
- Does the design avoid fabricated lesson content?

## Allowed Scope

- Read `docs/discovery/learntrack-discovery-report.md`, `docs/scope/learntrack-scope.md`, and `.kiro/specs/course-progress-update/requirements.md`.
- Write to `docs/design/google-stitch/` (three files only).
- Do not create any other files. Do not modify existing files.

## Required Outputs

- `docs/design/google-stitch/learntrack-stitch-master-prompt.md`
- `docs/design/google-stitch/course-progress-prompt.md`
- `docs/design/google-stitch/design-review-checklist.md`

## Acceptance Criteria

- [ ] All three files exist under `docs/design/google-stitch/`.
- [ ] Master prompt specifies platform, course name, and all four screen states.
- [ ] Course progress prompt specifies the authoritative values: 53.7%, 22, 41.
- [ ] Design review checklist covers all required states and all visual accuracy checks.
- [ ] No prompt claims to have generated a design — they are copy-ready inputs for manual execution.
- [ ] No fabricated Stitch export IDs, image URLs, or generated-design references.

## Stop Conditions

- Stop if Prompt 02 outputs are missing — requirements must exist before UI prompts can be written.
- If Google Stitch MCP is available and configured, note that in the master prompt file; otherwise treat as manual-execution-only.
- Do not begin Figma work — that is Prompt 04.
