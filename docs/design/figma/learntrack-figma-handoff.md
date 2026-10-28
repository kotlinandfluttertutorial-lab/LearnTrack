# LearnTrack — Figma Design Handoff

**Tool:** Figma (manual setup required)
**MCP status:** Figma MCP was not verified as available during this phase. No Figma file IDs, project links, or export URLs have been generated. All setup and file creation must be performed manually following the instructions in this document.
**Platform:** Android mobile (Jetpack Compose, Material 3)
**Source designs:** Google Stitch — see `docs/design/google-stitch/learntrack-stitch-master-prompt.md`

---

## 1. Figma Setup Instructions

### 1.1 Create the Figma Project (Manual Steps)

1. Sign in to [figma.com](https://figma.com).
2. In your workspace, create a new **Project** named **LearnTrack**.
3. Inside the project, create a new **Design File** named **LearnTrack — UI Designs**.
4. Create a second **Design File** named **LearnTrack — Design System**.
5. Record both file URLs here after creation (do not leave placeholder text in committed documentation):

| File | URL |
|---|---|
| LearnTrack — UI Designs | _(paste URL after creation)_ |
| LearnTrack — Design System | _(paste URL after creation)_ |

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

## 6. Notes on Figma MCP

The Figma MCP server was not verified as available for this workflow step. As a result:

- No Figma file IDs have been generated or recorded.
- No design exports have been created.
- No component code has been auto-generated from Figma.

When Figma MCP is available and configured:
1. Run `figma::create_new_file` to create the design files.
2. Run `figma::create_session` with the returned file key.
3. Use `figma::use_figma` or `figma::generate_figma_design` to push the design specifications.
4. Use `figma::get_code_connect_map` to verify Code Connect mappings.
5. Update this document with real file IDs and URLs — never fabricate them.

The implementation team can proceed with this handoff document as the authoritative specification. The Figma file, when created, must match these specifications exactly. Any deviation from the specs in this document requires a documented design change request.
