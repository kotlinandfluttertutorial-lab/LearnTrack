# Prompt 04 — Figma Design Handoff

## Prerequisites

- Prompt 03 complete: all three Stitch prompt files exist in `docs/design/google-stitch/`.
- `docs/scope/learntrack-scope.md` exists (for platform and component conventions).
- `.kiro/specs/course-progress-update/requirements.md` exists (for screen and state requirements).

## Specific Task

Create three Figma design handoff documents that translate the approved Stitch concepts into a complete design specification for the Android engineering team.

**Do not claim a Figma file has been created unless you have used a live, authenticated Figma MCP connection and received a real file key in return. If Figma MCP is unavailable, produce copy-ready specification documents only.**

### Files to Create

**1. `docs/design/figma/learntrack-figma-handoff.md`**

Handoff document covering:
- Design intent and source (Stitch prompts in `docs/design/google-stitch/`)
- Screen list with Figma frame names (or proposed names if MCP unavailable)
- Component inventory: progress card, progress bar, topic row, status badge, loading indicator, error state, empty state
- Design-to-implementation mapping (Figma component → Compose composable)
- Interaction notes (tap targets, navigation transitions)
- Handoff instructions for engineers (how to inspect spacing, color values, typography)
- Design acceptance criteria

**2. `docs/design/figma/learntrack-design-system.md`**

Design system specification covering:
- Color tokens: primary, secondary, surface, on-surface, error, completed-status, not-started-status — with Material 3 role names and suggested hex values
- Typography tokens: display, headline, body, label — with size, weight, and line height
- Spacing: 4 dp base grid; named steps (xs=4, sm=8, md=16, lg=24, xl=32)
- Shape: corner radius for cards (12 dp), chips (8 dp), progress bar (4 dp full-round)
- Component states: default, focused, disabled, loading, error
- Dark mode / light mode token pairs
- Accessibility notes per token (contrast ratio targets)

**3. `docs/design/figma/learntrack-screen-inventory.md`**

Screen-by-screen specification covering:

- **CourseProgressScreen (Dashboard):**
  - Layout zones: app bar, progress card, topic count, navigation CTA
  - Success state: percentage text, LinearProgressIndicator, "X of Y" text
  - Loading state: CircularProgressIndicator centered
  - Error state: error icon + message text
  - Empty state: illustration + message

- **TopicListScreen:**
  - LazyColumn of topic rows
  - Each row: topic name (body text) + status badge (chip)
  - Completed badge: green surface, "Completed" label
  - Not-started badge: neutral surface, "Not Started" label
  - Scroll behavior: fading edge at bottom

- **TopicDetailScreen:**
  - Topic name as headline
  - Status badge (same design as list row)
  - Back navigation affordance

## Allowed Scope

- Read all three files in `docs/design/google-stitch/` and `docs/scope/learntrack-scope.md`.
- Write to `docs/design/figma/` (three files only).
- Do not modify existing files. Do not create files outside `docs/design/figma/`.

## Required Outputs

- `docs/design/figma/learntrack-figma-handoff.md`
- `docs/design/figma/learntrack-design-system.md`
- `docs/design/figma/learntrack-screen-inventory.md`

## Acceptance Criteria

- [ ] All three files exist under `docs/design/figma/`.
- [ ] Design system defines color, typography, spacing, and shape tokens.
- [ ] Screen inventory specifies all four UI states (loading, success, error, empty) for each data-driven screen.
- [ ] Handoff document maps every Figma component to its corresponding Compose composable.
- [ ] Progress percentage in all specifications is 53.7% (not any other value).
- [ ] No fabricated Figma file IDs, export URLs, or live MCP operation results (unless MCP was actually used).

## Stop Conditions

- Stop if Prompt 03 outputs are missing — design specs must be based on reviewed Stitch concepts.
- If Figma MCP is available and authenticated, use it and record the real file key; otherwise document the manual setup steps in the handoff file.
- Do not begin architecture or data design work — that is Prompt 05.
