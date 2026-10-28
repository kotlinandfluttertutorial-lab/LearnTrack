# LearnTrack — Google Stitch Master Prompt

**Tool:** Google Stitch (manual execution required)
**Access status:** Google Stitch MCP access was not available during this phase. This document contains copy-ready prompts for manual execution in Google Stitch.
**Platform target:** Android mobile application (Jetpack Compose, Material 3)

---

## Handoff Instructions

1. Open Google Stitch at [stitch.withgoogle.com](https://stitch.withgoogle.com).
2. Create a new project named **LearnTrack**.
3. Copy and paste the master prompt below into the Stitch prompt field.
4. Generate the design.
5. Review the output against the Design Review Checklist (`docs/design/google-stitch/design-review-checklist.md`).
6. Export design assets to `docs/design/google-stitch/exports/` (create directory when available).
7. Record the Stitch project URL and any generated file IDs in this document under "Generated Design References" (do not fabricate these values).

---

## Generated Design References

> **Note:** No Stitch designs have been generated yet. This section must be updated after manual execution.
> Do not invent file IDs, project links, or export URLs.

| Item | Value |
|---|---|
| Stitch Project URL | _(to be filled after manual execution)_ |
| Export directory | `docs/design/google-stitch/exports/` |
| Generation date | _(to be filled)_ |
| Reviewer | _(to be filled)_ |

---

## Master Prompt — Copy and Paste into Google Stitch

```
Design a complete Android mobile application UI for LearnTrack, a course progress
tracking app. Use Material Design 3 with a clean, modern aesthetic. The application
is a native Android app using Jetpack Compose.

PRODUCT: LearnTrack
COURSE: AI & Python Technology Course
PLATFORM: Android mobile (portrait orientation, primary breakpoint 360 dp width)

COURSE DATA (authoritative — do not change these values):
- Completed topics: 22
- Not-started topics: 19
- Total topics: 41
- Completion percentage: 53.7%

SCREENS TO DESIGN:
1. Course Dashboard (home screen)
2. Progress Summary (embedded card or modal)
3. Topic List (full scrollable list)
4. Topic Detail (single topic view)

---

SCREEN 1 — COURSE DASHBOARD

Purpose: Entry screen for the learner. Shows overall course progress at a glance.

Layout:
- Top app bar: "LearnTrack" title, settings icon (trailing)
- Hero section below app bar:
  - Course name: "AI & Python Technology Course"
  - Large circular progress indicator showing 53.7%
  - "53.7% Complete" label centered below indicator
  - Subtitle row: "22 of 41 topics completed"
- Progress stats row (three chips or info cards):
  - Completed: 22
  - Not Started: 19
  - Total: 41
- Primary CTA button: "View All Topics" → navigates to Topic List
- Secondary section: "Continue Learning" (most recent not-started topic)
- Bottom navigation bar with: Home (selected), Topics, Progress

States to show:
- Default (data loaded, 53.7% progress)
- Loading (skeleton shimmer over the hero section and stats)
- Error (icon + message "Could not load course data" + Retry button)

---

SCREEN 2 — PROGRESS SUMMARY

Purpose: Detailed progress breakdown, accessible from the dashboard or a bottom sheet.

Layout:
- Card or bottom sheet with handle
- Title: "Your Progress"
- Horizontal linear progress bar: 53.7% filled
  - Filled segment: primary color
  - Unfilled segment: surface variant color
  - Percentage label at the right end: "53.7%"
- Stats grid (2×2):
  - Completed: 22 (green check icon)
  - Not Started: 19 (clock icon)
  - Total Topics: 41 (book icon)
  - Completion: 53.7% (trophy icon)
- Section divider
- Motivation copy (one line): "You're more than halfway there!"
- Close / Dismiss action

States to show:
- Default (populated data)
- Empty (no topics available — show "No topics found" with a reload action)
- Invalid data (percentage outside 0–100 — show warning banner "Data integrity error")

---

SCREEN 3 — TOPIC LIST

Purpose: Show all 41 topics with status indicators. Allow the learner to browse and
navigate to individual topics.

Layout:
- Top app bar: "Topics" title, search icon (trailing), filter icon (trailing)
- Filter chips below app bar (horizontal scroll):
  - All (41)
  - Completed (22)
  - Not Started (19)
- Lazy list of topic rows (see Topic Row component spec below)
- Each row is tappable → navigates to Topic Detail
- Section headers optional (group completed vs not-started)

Topic Row component:
- Leading: status icon
  - Completed: filled green circle with checkmark
  - Not Started: outlined grey circle
- Topic name: body text, medium weight
- Trailing: chevron-right icon
- Divider between rows (optional, or use card-based rows)
- Row height: 56–72 dp

Status badge (chip, not full row):
- Completed: green background (#2E7D32 or Material 3 green), white text "Completed"
- Not Started: grey background (surface variant), medium-emphasis text "Not Started"

States to show:
- Default (41 rows loaded)
- Loading (3–5 skeleton rows with shimmer)
- Empty (no topics match filter — "No topics found" message with clear-filter link)
- Error (load failure — error card with Retry button)
- Search active (rows filtered to matching names, empty search restores full list)

---

SCREEN 4 — TOPIC DETAIL

Purpose: Show a single topic's full details.

Layout:
- Top app bar: topic name (truncated if needed), back arrow (leading)
- Hero card:
  - Topic name (large, headline style)
  - Status badge (Completed or Not Started)
  - Status icon (large, 48×48 dp)
- Info section:
  - Label "Status:" + value
  - Label "Topic ID:" + value (e.g., "topic_01")
- Description area (placeholder text if no description exists: "No description available.")
- If status is "Not Started": primary button "Mark as Started" (future feature — show as
  disabled or omit if not yet implemented)
- If status is "Completed": secondary chip "Completed ✓"
- Bottom: "← Previous Topic" and "Next Topic →" navigation row

States to show:
- Default (topic loaded)
- Loading (skeleton placeholder)
- Error (could not load topic — back button always active)
- Not found (invalid topic ID — "Topic not found" with back button)

---

TYPOGRAPHY

Use Material 3 type scale:
- Display Large: Course name on dashboard (57sp)
- Headline Large: Progress percentage "53.7%" (32sp, bold)
- Headline Medium: Section titles (28sp)
- Title Large: Topic names in list (22sp)
- Title Medium: Card titles, app bar text (16sp, medium weight)
- Body Large: Primary body text, stats values (16sp)
- Body Medium: Secondary body text, descriptions (14sp)
- Label Large: Buttons, chips, badges (14sp, medium weight)
- Label Medium: Supporting info, captions (12sp)

Font: System default (Roboto on Android)

---

COLORS (Material 3 dynamic color — propose a seed color, generate full palette)

Seed color: #1565C0 (deep blue — learning / technology theme)

Expected palette roles:
- Primary: #1565C0 (deep blue)
- On Primary: #FFFFFF
- Primary Container: #D0E4FF
- On Primary Container: #001D36
- Secondary: #00695C (teal — progress / completion)
- On Secondary: #FFFFFF
- Secondary Container: #B2DFDB
- Tertiary: #F57F17 (amber — not-started / in-progress accent)
- Background: #FAFAFA (light mode) / #121212 (dark mode)
- Surface: #FFFFFF (light) / #1E1E1E (dark)
- Surface Variant: #E3E8F0 (light) / #2D3748 (dark)
- Error: #B00020
- On Error: #FFFFFF

Support both light mode and dark mode. Show both variants for at least the dashboard
and topic list screens.

Status colors:
- Completed: #2E7D32 (green) — accessible against white and dark backgrounds
- Not Started: #757575 (grey, medium emphasis)

---

SPACING AND LAYOUT

Base grid: 4 dp
Minimum touch target: 48×48 dp
Content margins: 16 dp (left/right), 8–16 dp (top/bottom)
Card internal padding: 16 dp
List item padding: 16 dp horizontal, 12 dp vertical
Progress bar height: 8 dp (thin) or 12 dp (standard)
Circular progress indicator diameter: 120 dp (dashboard hero)
Section spacing: 24 dp

---

COMPONENT BEHAVIOR

Progress bar (linear):
- Animated fill from 0 to target on first load (300 ms ease-in-out)
- Accessible: role="progressbar", aria-valuenow=53.7, aria-valuemin=0, aria-valuemax=100
- Show exact percentage as text adjacent to or below the bar

Circular progress indicator (dashboard):
- Animated sweep on first load
- Center label: "53.7%"
- Ring stroke width: 10–14 dp
- Ring color: primary for filled arc, surface variant for unfilled arc

Topic row:
- Tap feedback: ripple effect
- Long-press: no action (keep simple for this phase)
- Swipe: no action (keep simple for this phase)

Filter chips:
- Single-select (only one active at a time)
- Active state: filled chip (primary container)
- Inactive state: outlined chip

---

ACCESSIBILITY REQUIREMENTS

- Minimum text contrast ratio: 4.5:1 (WCAG 2.1 AA) for all body text
- Minimum large text contrast ratio: 3:1 (WCAG 2.1 AA) for text ≥18sp
- All images, icons, and progress indicators must have content descriptions
- Progress percentage must be readable as text (not only graphical)
- Status badges use both color and text/icon (not color alone)
- Touch targets minimum 48×48 dp
- Focus order follows visual reading order (top-to-bottom, left-to-right)
- Support for system text size adjustments (no hard pixel font sizes in components)

---

RESPONSIVE BEHAVIOR

Primary target: 360 dp width (compact phone, portrait)
Secondary target: 600 dp width (large phone or small tablet)

At 600 dp:
- Dashboard: two-column layout for stats row
- Topic list: two-column grid instead of single-column list

---

LOADING AND ERROR PATTERNS

Loading state: Use shimmer/skeleton placeholders that match the shape of the loaded
content. Never show a spinner over blank space for list screens.

Error state:
- Icon: Material "error_outline" or "cloud_off"
- Headline: context-specific (e.g., "Couldn't load topics")
- Body: brief explanation
- Primary action: "Retry" button
- Secondary action: "Go back" link (where applicable)

Empty state:
- Icon: context-specific (e.g., "search_off" for empty search)
- Headline: "No topics found"
- Body: "Try adjusting your filter or search term"
- Action: "Clear filter" button

Invalid data state:
- Yellow/amber warning banner at top of screen
- Message: "Some course data appears invalid. Progress may be inaccurate."
- Dismiss action (X)

---

DESIGN STYLE GUIDELINES

- Clean, minimal, professional — suitable for an educational tool
- Use cards with subtle elevation (Material 3 tonal elevation)
- Rounded corners: 12 dp for cards, 8 dp for chips, 16 dp for buttons (pill)
- Avoid decorative illustrations (keep focus on data and progress)
- Icons: Material Symbols Rounded style
- No gradients in data-display areas (gradients acceptable in hero illustration only)
- Consistent spacing — every spacing value is a multiple of 4 dp

Do not add fictional lesson content, instructors, prices, dates, or data not specified
above. Do not show more or fewer than 22 completed and 19 not-started topics.
```

---

## Supplementary Notes for the Designer

- The topic names for all 41 topics are defined in `learntrack_topics.json` (to be created). Use placeholder names (e.g., "Topic 01 — Introduction to Python") in the design if the final names are not yet available.
- The "Mark as Started" and similar progression actions are future features. Show them as disabled or omit them from the initial design pass.
- The "Continue Learning" section on the dashboard should show the first not-started topic. Use a placeholder name if actual data is not yet available.
- Dark mode is required. Generate at minimum the dashboard and topic list in dark mode.
- All color values in this prompt are proposals derived from the LearnTrack brand direction (education, technology, progress). They may be refined after design review.
