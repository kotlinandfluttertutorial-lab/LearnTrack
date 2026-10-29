# LearnTrack — Figma Design Handoff

**Tool:** Figma
**MCP status:** ✅ Connected — authenticated as Firoj (firoj.md2002@gmail.com), Full seat, editor access confirmed
**File status:** ✅ File created and inspected via MCP
**File key:** `5dEMGt9iynTXgVGqjxd9IM`
**Platform:** Android mobile (Jetpack Compose, Material 3)
**Source designs:** Google Stitch — see `docs/design/google-stitch/learntrack-stitch-master-prompt.md`
**Last updated:** October 28, 2026

---

## 1. Figma Setup Instructions

### 1.1 Figma Files

| File | URL | Status |
|---|---|---|
| LearnTrack — UI Designs | https://www.figma.com/design/5dEMGt9iynTXgVGqjxd9IM/LearnTrack-%E2%80%94-UI-Designs | ✅ Created & MCP-accessible |
| LearnTrack — Design System | _(create separately — see Section 1.1a)_ | 🔲 Pending |

**File key (for MCP tools):** `5dEMGt9iynTXgVGqjxd9IM`

### Actual Page Structure (verified via MCP — October 28, 2026)

| Page ID | Page Name | Status |
|---|---|---|
| `0:1` | Cover | ✅ Exists (empty) |
| `3:4` | Stitch Import | ✅ Exists — contains live design content |
| `1:2` | UI Screens | ✅ Exists — sections created |

### Actual Sections on "UI Screens" Page (node `1:2`)

| Section ID | Section Name | Position |
|---|---|---|
| `3:5` | Course Dashboard | x=0, y=0 |
| `3:6` | Topic List | x=1200, y=0 |
| `3:7` | Topic Detail | x=2400, y=0 |
| `3:8` | Progress Summary | x=0, y=1100 |
| `3:9` | States | x=1200, y=1100 |

### Actual Frames on "Stitch Import" Page (node `3:4`)

The Stitch Import page (Section `4:1011` — "Section 1") contains a fully designed **dark mode** screen:

| Frame ID | Frame Name | Dimensions |
|---|---|---|
| `4:3` | Course Progress (Dark Mode) | 390 × 1789.5 dp |

This is the **authoritative reference design** — inspected via MCP and confirmed against screenshot review.

### 1.1a Create the Design System File (Pending)

1. Sign in to [figma.com](https://figma.com).
2. In the same **LearnTrack** project, create a new **Design File** named **LearnTrack — Design System**.
3. Record the URL here once created.

> ✅ **Done — October 28, 2026:** Design System file created via Figma MCP.
> **URL:** https://www.figma.com/design/AbG7UxbofJuAvBFCcZW0pv
> **File key:** `AbG7UxbofJuAvBFCcZW0pv`
> **Pages:** Cover + Color Tokens · Typography + Spacing + Elevation · Components

### 1.2 Import Stitch Designs

If Google Stitch exported images or a plugin-compatible file:
1. In the LearnTrack — UI Designs file, create a page named **Stitch Import**.
2. Import or paste the exported Stitch designs onto this page.
3. Use the Stitch output as a reference layer; do not build implementation-ready components from it directly.

If Stitch exported only image files:
1. Place images on the Stitch Import page.
2. Recreate each screen as a Figma frame using native Figma components.

### 1.3 File Structure

Organize the LearnTrack — UI Designs file with the following pages:

```
Page 1: Cover
Page 2: Stitch Import          (reference only)
Page 3: Course Dashboard       (LT-007, LT-006)
Page 4: Topic List             (LT-007)
Page 5: Topic Detail           (LT-007)
Page 6: Progress Summary       (LT-006, LT-007)
Page 7: States & Edge Cases    (loading, error, empty, invalid-data)
Page 8: Annotations & Specs    (measurement, interaction notes)
```

Organize the LearnTrack — Design System file with the following pages:

```
Page 1: Cover
Page 2: Color Tokens
Page 3: Typography Scale
Page 4: Spacing & Grid
Page 5: Elevation & Shadow
Page 6: Components
Page 7: Icons Reference
```

### 1.4 Frame Setup

Create frames with the following dimensions for Android phone:

| Frame name | Width | Height | Notes |
|---|---|---|---|
| Compact Phone Portrait | 360 dp | 800 dp | Primary target |
| Compact Phone Tall | 390 dp | 844 dp | iPhone-equivalent size, acceptable for Android review |
| Medium Phone | 412 dp | 917 dp | Pixel 6 dimensions |

Scale: 1x (design in dp, not px).

---

## 2. Screen-by-Screen Specifications

### Screen 1 — Course Dashboard

**File page:** Page 3 — Course Dashboard
**Frame name:** `dashboard/light` and `dashboard/dark`
**Jira issues:** LT-006, LT-007
**Entry points:** App launch, Bottom nav "Home" tab

**Layout specification (top to bottom, 360 dp wide):**

```
[Status bar — system, 24 dp]
[Top App Bar — 64 dp]
  Title: "LearnTrack"
  Leading: none (no back arrow on root)
  Trailing: Settings icon (24 dp, 48×48 dp touch target)

[Hero Section — ~220 dp]
  Course name: "AI & Python Technology Course"
  Typography: Title Large (22sp, medium weight)
  Margin: 16 dp horizontal, 16 dp top

  Circular progress indicator: 120×120 dp, centered
    Ring stroke: 12 dp
    Fill arc: 53.7% (≈193°), Primary #1565C0
    Empty arc: Surface Variant #E3E8F0
    Center label: "53.7%" (Headline Large, 32sp, bold)
    Below ring: "Complete" (Label Medium, 12sp, muted)

  Progress label below ring: "22 of 41 topics completed"
  Typography: Body Medium (14sp), muted color
  Margin: 8 dp top

[Stats Row — 64 dp]
  Three equal columns, 16 dp horizontal margin, 8 dp vertical padding
  Column 1: "22" (Headline Small, green #2E7D32) / "Completed" (Label Small, muted)
  Column 2: "41" (Headline Small, on-surface) / "Total" (Label Small, muted)
  Column 3: "19" (Headline Small, amber #F57F17) / "Not Started" (Label Small, muted)

[CTA Section — 72 dp]
  Primary button: "View All Topics" (filled, pill, full-width minus 32 dp margin)
  Margin: 16 dp horizontal, 16 dp top

[Continue Learning Card — ~80 dp]
  Card: corner 12 dp, tonal surface
  Label: "Continue Learning" (Label Large, primary color)
  Topic name: first not-started topic (placeholder: "Next: Introduction to AI")
  Trailing: chevron-right icon
  Margin: 16 dp horizontal

[Bottom Navigation Bar — 80 dp]
  Items: Home (selected), Topics, Progress
  Active indicator: pill background behind icon
```

**States:**
- `dashboard/light/default` — 53.7% progress, all data loaded
- `dashboard/light/loading` — skeleton shimmer on hero + stats
- `dashboard/light/error` — error icon + "Couldn't load course data" + Retry button
- `dashboard/dark/default` — same as light/default but dark theme
- `dashboard/dark/loading`
- `dashboard/dark/error`

**Interaction notes:**
- Tapping stats row or hero → opens Progress Summary bottom sheet
- "View All Topics" → navigates to Topic List
- "Continue Learning" card → navigates to Topic Detail for first not-started topic
- Settings icon → navigates to Settings (out of scope; show as tappable with no destination for now)

---

### Screen 2 — Progress Summary

**File page:** Page 6 — Progress Summary
**Frame name:** `progress-summary/light` and `progress-summary/dark`
**Jira issues:** LT-006
**Entry points:** Dashboard hero tap, Dashboard stats row tap

**Layout specification (bottom sheet, 360 dp wide, ~520 dp tall):**

```
[Handle — 8 dp] centered pill (32×4 dp, surface variant)
[Title Row — 56 dp]
  Title: "Your Progress" (Title Large, 22sp)
  Trailing: ✕ close icon (48×48 dp touch target)
  Padding: 16 dp horizontal
[Divider — 1 dp]
[Progress Bar Section — 56 dp]
  Linear bar: full width minus 32 dp, 12 dp tall, 6 dp corner radius
  Fill: 53.7%, Primary #1565C0
  Background: Surface Variant
  Label below: "53.7% complete" (Body Medium, muted, left-aligned)
  Padding: 16 dp horizontal, 12 dp vertical
[Stats Grid — 144 dp]
  2×2 grid, 16 dp gaps
  Cell 1: ✓ icon (green) + "22" (Headline Small) + "Completed" (Label Small)
  Cell 2: ○ icon (grey) + "19" (Headline Small) + "Not Started" (Label Small)
  Cell 3: ⊞ icon (blue) + "41" (Headline Small) + "Total Topics" (Label Small)
  Cell 4: 🏆 icon (amber) + "53.7%" (Headline Small) + "Completion" (Label Small)
  Padding: 16 dp all sides
[Divider — 1 dp]
[Motivation Text — 48 dp]
  "You're more than halfway there! Keep going."
  Body Medium (14sp), italic, muted, centered
  Padding: 16 dp horizontal, 12 dp vertical
[Primary Button — 56 dp]
  "View All Topics" (filled, full-width minus 32 dp)
  Padding: 16 dp horizontal, 8 dp bottom
[Bottom safe area — variable]
```

**States:**
- `progress-summary/light/default`
- `progress-summary/light/empty` — "No topics available" message with reload action
- `progress-summary/light/invalid-data` — amber warning banner + data at 0.6 alpha
- `progress-summary/dark/default`

---

### Screen 3 — Topic List

**File page:** Page 4 — Topic List
**Frame name:** `topic-list/light` and `topic-list/dark`
**Jira issues:** LT-007
**Entry points:** Dashboard "View All Topics", Bottom nav "Topics" tab

**Layout specification (360 dp wide):**

```
[Status bar — 24 dp]
[Top App Bar — 64 dp]
  Title: "Topics" (Title Large)
  Trailing 1: Search icon (48×48 dp touch target)
  Trailing 2: Filter icon (48×48 dp touch target)
[Filter Chips Row — 48 dp]
  Horizontal scroll, 16 dp left margin, 8 dp gap between chips
  Chip 1: "All (41)" — active by default (filled)
  Chip 2: "Completed (22)" — inactive (outlined)
  Chip 3: "Not Started (19)" — inactive (outlined)
[Topic List — scrollable, fills remaining height]
  Topic rows (56–72 dp each):
    Leading icon (24 dp):
      Completed: filled green circle + checkmark
      Not Started: outlined grey circle
    Topic name: Title Medium (16sp, medium weight)
    Trailing: chevron-right icon (24 dp)
    Horizontal padding: 16 dp
    Divider: 1 dp between rows (or card-style rows with 8 dp gap)
  41 rows total
[Bottom Navigation Bar — 80 dp]
  Items: Home, Topics (selected), Progress
```

**States:**
- `topic-list/light/default` — all 41 topics, "All" filter active
- `topic-list/light/completed-filter` — 22 topics, "Completed" filter active
- `topic-list/light/not-started-filter` — 19 topics, "Not Started" filter active
- `topic-list/light/loading` — 5 skeleton rows with shimmer
- `topic-list/light/error` — error card + Retry
- `topic-list/light/empty-search` — "No topics found" + "Clear search"
- `topic-list/dark/default`
- `topic-list/dark/loading`

---

### Screen 4 — Topic Detail

**File page:** Page 5 — Topic Detail
**Frame name:** `topic-detail/light/completed`, `topic-detail/light/not-started`, `topic-detail/dark/completed`
**Jira issues:** LT-007
**Entry points:** Topic List row tap

**Layout specification (360 dp wide):**

```
[Status bar — 24 dp]
[Top App Bar — 64 dp]
  Leading: back arrow (48×48 dp touch target)
  Title: topic name (truncated if > 30 characters) (Title Large)
[Hero Card — ~180 dp]
  Card: 16 dp margin, corner 12 dp, tonal surface
  Topic name: Headline Medium (28sp, full, 3 lines max)
  Status badge: chip (Completed = green, Not Started = grey)
  Status icon: 48×48 dp (checkmark or clock)
  Padding: 16 dp
[Info Section — ~80 dp]
  Label row: "Status:" (Label Large, muted) + value (Body Large)
  Label row: "Topic ID:" (Label Large, muted) + value (Body Large, monospace)
  Padding: 16 dp horizontal, 12 dp top
[Description Area — ~80 dp]
  Body text (Body Large): "No description available." (placeholder)
  Padding: 16 dp
[Action Row — 56 dp] (if not started)
  Primary button: "Mark as Started" (disabled state — future feature)
[Navigation Row — 56 dp]
  Left: "← Previous Topic" (text button, left-aligned)
  Right: "Next Topic →" (text button, right-aligned)
  Padding: 16 dp horizontal, 8 dp bottom
[Bottom safe area]
```

**States:**
- `topic-detail/light/completed` — green badge, checkmark icon, no action button
- `topic-detail/light/not-started` — grey badge, clock icon, disabled "Mark as Started"
- `topic-detail/light/loading` — skeleton placeholder
- `topic-detail/light/error` — error + back button
- `topic-detail/light/not-found` — "Topic not found" + back button
- `topic-detail/dark/completed`
- `topic-detail/dark/not-started`

---

## 3. Component Specifications

See `docs/design/figma/learntrack-design-system.md` for complete component definitions.

| Component | Screen(s) used | Variants |
|---|---|---|
| ProgressHeroCard | Dashboard | default, loading, error |
| CircularProgressIndicator | Dashboard | default, loading |
| LinearProgressBar | Progress Summary | default, loading, invalid-data |
| StatsGrid | Progress Summary | default, empty |
| TopicRow | Topic List | completed, not-started, loading-skeleton |
| StatusBadge | Topic Detail, Topic List | completed, not-started |
| FilterChip | Topic List | active, inactive |
| ErrorState | Dashboard, Topic List, Topic Detail | — |
| EmptyState | Topic List, Progress Summary | — |
| LoadingSkeleton | All screens | — |
| WarningBanner | Progress Summary | dismissible |

---

## 4. Design-to-Implementation Mapping

| Figma component / layer | Android implementation | File (to be created) |
|---|---|---|
| ProgressHeroCard | `@Composable ProgressHeroCard(state: ProgressState)` | `app/src/main/java/com/learntrack/ui/components/ProgressHeroCard.kt` |
| CircularProgressIndicator | `@Composable CircularProgress(percentage: Float)` | `app/src/main/java/com/learntrack/ui/components/CircularProgress.kt` |
| LinearProgressBar | `@Composable LinearProgress(percentage: Float)` | `app/src/main/java/com/learntrack/ui/components/LinearProgress.kt` |
| StatsGrid | `@Composable StatsGrid(completed: Int, notStarted: Int, total: Int)` | `app/src/main/java/com/learntrack/ui/components/StatsGrid.kt` |
| TopicRow | `@Composable TopicRow(topic: Topic, onClick: () -> Unit)` | `app/src/main/java/com/learntrack/ui/components/TopicRow.kt` |
| StatusBadge | `@Composable StatusBadge(status: TopicStatus)` | `app/src/main/java/com/learntrack/ui/components/StatusBadge.kt` |
| Dashboard screen | `@Composable DashboardScreen(viewModel: DashboardViewModel)` | `app/src/main/java/com/learntrack/ui/screens/DashboardScreen.kt` |
| Topic List screen | `@Composable TopicListScreen(viewModel: TopicListViewModel)` | `app/src/main/java/com/learntrack/ui/screens/TopicListScreen.kt` |
| Topic Detail screen | `@Composable TopicDetailScreen(topicId: String, viewModel: TopicDetailViewModel)` | `app/src/main/java/com/learntrack/ui/screens/TopicDetailScreen.kt` |
| Progress Summary | `@Composable ProgressSummaryBottomSheet(state: ProgressState, onDismiss: () -> Unit)` | `app/src/main/java/com/learntrack/ui/screens/ProgressSummaryBottomSheet.kt` |
| Design tokens (colors) | `Color.kt` (MaterialTheme extension) | `app/src/main/java/com/learntrack/ui/theme/Color.kt` |
| Design tokens (typography) | `Type.kt` (MaterialTheme extension) | `app/src/main/java/com/learntrack/ui/theme/Type.kt` |
| Design tokens (shapes) | `Shape.kt` (MaterialTheme extension) | `app/src/main/java/com/learntrack/ui/theme/Shape.kt` |

---

## 5. Design Acceptance Criteria

The following criteria must be met before the Figma design is approved for implementation.

| # | Criterion |
|---|---|
| DA-1 | Every screen in the screen inventory (`learntrack-screen-inventory.md`) is represented in Figma |
| DA-2 | Every screen maps to one or more functional requirements (LT-001 through LT-011) |
| DA-3 | All data values are authoritative: 22 completed, 19 not-started, 41 total, 53.7% |
| DA-4 | All components use design tokens from the design system (no hard-coded hex values in layers) |
| DA-5 | Light mode and dark mode variants exist for all primary screens |
| DA-6 | All four required states are designed: default, loading, error, empty |
| DA-7 | Status indicators use both color and text/icon (WCAG non-color redundancy) |
| DA-8 | All interactive elements have minimum 48×48 dp touch targets |
| DA-9 | Text contrast meets WCAG 2.1 AA (4.5:1 for body, 3:1 for large text) |
| DA-10 | Component naming convention matches the design system (`ComponentName/variant/state`) |
| DA-11 | The design system file is published as a shared library in Figma (so tokens are reusable) |
| DA-12 | No Figma layers are named with auto-generated names ("Frame 1", "Rectangle 3", etc.) |

---

## 6. Actual Design Tokens Extracted from Figma (MCP-verified)

The following values were extracted directly from the Figma file via `figma::get_design_context` on node `4:3` (Course Progress — Dark Mode frame, Stitch Import page). These are the **authoritative values** to use in implementation.

### 6.1 Colors — Extracted from File

#### Background Colors
| Token | Hex | Usage in design |
|---|---|---|
| Primary | `#1565C0` | Progress ring fill, primary button, active states |
| Primary Dark | `#004D99` | Progress ring track fill (dark), header accent |
| Primary Container | `#D6E3FF` | Chip active background, tonal containers |
| Surface | `#FBF9F9` | Card backgrounds (light) |
| Surface Variant | `#F5F3F3` | Secondary card backgrounds |
| Surface Tint | `#EFEDED` | Dividers, inactive rows |
| Surface Dim | `#E9E8E7` | Skeleton/loading placeholder |
| Surface Neutral | `#E3E2E2` | Border, outline |
| Completed Green | `#A0F399` | Completed status badge background |
| Error Container | `#FFDAD6` | Error state background |
| Warning Container | `#FFDCC7` | Warning/amber banner background |

#### Text Colors
| Token | Hex | Usage in design |
|---|---|---|
| On Surface | `#1B1C1C` | Primary text |
| On Surface Variant | `#424752` | Secondary text, labels |
| Muted | `#727783` | Caption text, disabled states |
| Primary Text | `#004D99` | Links, primary-colored text |
| Completed Text | `#1B6D24` | Completed status text (dark on green) |
| Completed Alt | `#217128` | Completed icon color |
| Warning Text | `#A24F00` | Warning banner text |
| Warning Dark | `#723600` | Warning icon color |
| Error Dark | `#311300` | Error text |

### 6.2 Typography — Extracted from File

| Size | Line height | Usage |
|---|---|---|
| 32px | 40px | `53.7%` progress percentage — Headline Large |
| 24px | 30px | Stats numbers (22, 41, 19) — Headline Small |
| 22px | 28px | Course title, screen titles — Title Large |
| 20px | 28px | Section headings — Title Medium large |
| 16px | 24px | Body text, topic names — Body Large / Title Medium |
| 14px | 20px | Secondary text, bar labels — Body Medium |
| 12px | 16px | Captions, badge labels — Label Medium |
| 11px | 16px | Stats category labels ("Completed", "Total") — Label Small |

### 6.3 Shape / Border Radius — Extracted from File

| Value | Usage |
|---|---|
| `9999px` (pill) | Buttons, progress bar ends, status badges, circular elements |
| `12px` | Cards, bottom sheet, hero sections |
| `8px` | Chips, filter chips, inline cards |

### 6.4 Actual Components Found in File

These component names match exactly what was found in the Figma node tree:

| Figma component name | Maps to Composable |
|---|---|
| `Section - COMPONENT 1: Progress Hero Card (Default State)` | `ProgressHeroCard` |
| `Section - COMPONENT 2: In-Page Expandable Summary Sheet (Native Drawer Pattern)` | `ProgressSummaryBottomSheet` |
| `Circular Progress Indicator & Metric Highlight` | `CircularProgressRing` |
| `Full Linear Progress Bar with exact label` | `LinearProgressBar` |
| `3-Column Stats Row` | `StatsRow` |
| `2x2 Grid of Progress Metrics` | `StatsGrid` |
| `Top Motivation & Glance Strip` | `ContinueLearningCard` |
| `Learning Momentum Visual Banner` | `DailyGoalBanner` |
| `SYSTEM EDGE STATES DEMONSTRATION` | Error / Warning state components |
| `hero-progress-ring` | `CircularProgressRing` SVG gauge |
| `SVG Gauge: 120dp viewport with 12dp stroke` | Custom `Canvas`-drawn ring, 120dp × 12dp stroke |
| `Full-Width Pill Action Button` | `FilledButton` (pill shape) |
| `Motivational Callout` | Inline `Text` composable |
| `Nav` | `NavigationBar` (Dashboard, Topics, Profile tabs) |

### 6.5 Actual UI Text Labels Found in File

These are the exact text strings present in the design — use them verbatim in implementation:

| UI string | Where used |
|---|---|
| `"IN PROGRESS TRACK"` | Dashboard hero chip label |
| `"AI & Python Technology Course"` | Course name |
| `"53.7%"` | Circular ring center label |
| `"COMPLETE"` | Below ring center |
| `"22 of 41 Topics"` | Progress sub-label |
| `"22"` / `"41"` / `"19"` | Stats row values |
| `"Completed"` / `"Total"` / `"Not Started"` | Stats row labels |
| `"Syllabus Depth"` | Progress bar label |
| `"View All Topics →"` | CTA button |
| `"DAILY GOAL UNLOCKED"` | Learning momentum banner chip |
| `"Your Progress"` | Bottom sheet title |
| `"53.7% complete"` | Linear progress bar label |
| `"Target: 100% by Nov 30"` | Progress target label |
| `"You're more than halfway there! Keep going."` | Motivational callout |
| `"Dashboard"` / `"Topics"` / `"Profile"` | Bottom nav labels |
| `"Unable to sync module records"` | Error state headline |
| `"Retry Sync"` | Error state action button |
| `"Discrepancy Detected in Progress Log"` | Warning state headline |
| `"Reconcile Ledger"` | Warning state action button |

### 6.6 Edge States Found in File

The design includes a **SYSTEM EDGE STATES DEMONSTRATION** section with:

| State | Headline | Action |
|---|---|---|
| Loading skeleton | _(shimmer, no text)_ | — |
| Error | "Unable to sync module records" | "Retry Sync" button |
| Warning / Invalid data | "Discrepancy Detected in Progress Log" | "Reconcile Ledger" button |

> Note: "Reconcile Ledger" and "Retry Sync" are the design's action labels. The implementation equivalent is `onRetry: () -> Unit` callback on the error/warning state composables.

---

## 7. Notes on Figma MCP

The Figma MCP is **fully operational** for this file as of October 28, 2026.

**Authenticated account:** Firoj (firoj.md2002@gmail.com) — Full seat
**File:** https://www.figma.com/design/5dEMGt9iynTXgVGqjxd9IM/LearnTrack-%E2%80%94-UI-Designs
**File key:** `5dEMGt9iynTXgVGqjxd9IM`

**Available MCP operations (confirmed working):**
```
figma::create_session  fileKey: "5dEMGt9iynTXgVGqjxd9IM"
figma::get_metadata    nodeId: "3:4"   — Stitch Import page (has full design)
figma::get_metadata    nodeId: "1:2"   — UI Screens page (sections created)
figma::get_metadata    nodeId: "4:3"   — Course Progress Dark Mode frame
figma::get_design_context  nodeId: "4:3"  — Full component tree + code
```

**Key node IDs for implementation:**

| Node | ID | Contents |
|---|---|---|
| Stitch Import page | `3:4` | Full dark-mode dashboard design |
| Section 1 (Stitch) | `4:1011` | Outer section wrapper |
| Course Progress (Dark Mode) | `4:3` | Main 390×1789dp frame |
| UI Screens page | `1:2` | Section placeholders for all 4 screens |
| Course Dashboard section | `3:5` | To be populated |
| Topic List section | `3:6` | To be populated |
| Topic Detail section | `3:7` | To be populated |
| Progress Summary section | `3:8` | To be populated |
| States section | `3:9` | To be populated |

The implementation team can use `figma::get_design_context` on any node ID above to extract Jetpack Compose-ready component code. The dark-mode frame (`4:3`) is the most complete reference available.
