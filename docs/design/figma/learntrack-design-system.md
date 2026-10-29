# LearnTrack — Design System

**Platform:** Android (Jetpack Compose, Material 3)
**Color palette status:** ✅ Verified — extracted from Figma node `4:3` (Course Progress Dark Mode) via MCP on October 28, 2026. Values below supersede the original proposed palette.
**Figma Design System file:** [LearnTrack — Design System](https://www.figma.com/design/AbG7UxbofJuAvBFCcZW0pv) — file key `AbG7UxbofJuAvBFCcZW0pv` ✅ Created October 28, 2026
**Figma UI Designs file:** [LearnTrack — UI Designs](https://www.figma.com/design/5dEMGt9iynTXgVGqjxd9IM/LearnTrack-%E2%80%94-UI-Designs) — file key `5dEMGt9iynTXgVGqjxd9IM`

### Design System File Pages (Starter plan — 3 pages)

| Page | Contents |
|---|---|
| Page 1: Cover + Color Tokens | Cover frame, 20 color swatches (light mode, dark mode, semantic status, warning/error) |
| Page 2: Typography + Spacing + Elevation | Full type scale table with samples, spacing tokens with visual bars, shape tokens, elevation levels |
| Page 3: Components | StatusBadge, TopicRow, ProgressHeroCard (default/loading/error), FilterChip, WarningBanner, naming conventions |

---

## 1. Design Tokens — Colors

### 1.1 Material 3 Color Scheme (Light Mode)

| Token name | Role | Hex value | Usage |
|---|---|---|---|
| `md_theme_light_primary` | Primary | `#1565C0` | Buttons, active states, progress fill, links |
| `md_theme_light_onPrimary` | On Primary | `#FFFFFF` | Text/icons on primary color |
| `md_theme_light_primaryContainer` | Primary Container | `#D0E4FF` | Tonal containers, active chip background |
| `md_theme_light_onPrimaryContainer` | On Primary Container | `#001D36` | Text on primary container |
| `md_theme_light_secondary` | Secondary | `#00695C` | Completed status, positive emphasis |
| `md_theme_light_onSecondary` | On Secondary | `#FFFFFF` | Text/icons on secondary |
| `md_theme_light_secondaryContainer` | Secondary Container | `#B2DFDB` | Completed badge background |
| `md_theme_light_onSecondaryContainer` | On Secondary Container | `#00201B` | Text on completed badge |
| `md_theme_light_tertiary` | Tertiary | `#F57F17` | Not-started accent, amber/warning accents |
| `md_theme_light_onTertiary` | On Tertiary | `#FFFFFF` | Text on tertiary |
| `md_theme_light_tertiaryContainer` | Tertiary Container | `#FFF3E0` | Not-started ambient color |
| `md_theme_light_error` | Error | `#B00020` | Error states, critical failures |
| `md_theme_light_onError` | On Error | `#FFFFFF` | Text on error |
| `md_theme_light_errorContainer` | Error Container | `#FDECEA` | Error card background |
| `md_theme_light_background` | Background | `#FAFAFA` | Screen background |
| `md_theme_light_onBackground` | On Background | `#1A1A1A` | Primary text |
| `md_theme_light_surface` | Surface | `#FFFFFF` | Card surfaces |
| `md_theme_light_onSurface` | On Surface | `#1A1A1A` | Text on cards |
| `md_theme_light_surfaceVariant` | Surface Variant | `#E3E8F0` | Progress bar background, dividers |
| `md_theme_light_onSurfaceVariant` | On Surface Variant | `#44566C` | Secondary text, muted labels |
| `md_theme_light_outline` | Outline | `#76869B` | Borders, outlined chips |

### 1.2 Material 3 Color Scheme (Dark Mode)

| Token name | Role | Hex value |
|---|---|---|
| `md_theme_dark_primary` | Primary | `#9ECAFF` |
| `md_theme_dark_onPrimary` | On Primary | `#003258` |
| `md_theme_dark_primaryContainer` | Primary Container | `#004880` |
| `md_theme_dark_onPrimaryContainer` | On Primary Container | `#D0E4FF` |
| `md_theme_dark_secondary` | Secondary | `#5DDFC9` |
| `md_theme_dark_onSecondary` | On Secondary | `#003730` |
| `md_theme_dark_secondaryContainer` | Secondary Container | `#004F46` |
| `md_theme_dark_onSecondaryContainer` | On Secondary Container | `#7CFCDE` |
| `md_theme_dark_tertiary` | Tertiary | `#FFCC80` |
| `md_theme_dark_onTertiary` | On Tertiary | `#3E2000` |
| `md_theme_dark_error` | Error | `#CF6679` |
| `md_theme_dark_onError` | On Error | `#680015` |
| `md_theme_dark_background` | Background | `#121212` |
| `md_theme_dark_onBackground` | On Background | `#E2E2E2` |
| `md_theme_dark_surface` | Surface | `#1E1E1E` |
| `md_theme_dark_onSurface` | On Surface | `#E2E2E2` |
| `md_theme_dark_surfaceVariant` | Surface Variant | `#2D3748` |
| `md_theme_dark_onSurfaceVariant` | On Surface Variant | `#A0AEC0` |
| `md_theme_dark_outline` | Outline | `#718096` |

### 1.3 Semantic Status Colors

| Token | Light hex | Dark hex | Usage |
|---|---|---|---|
| `status_completed` | `#2E7D32` | `#81C784` | Completed status icon, badge, accent |
| `status_completed_container` | `#E8F5E9` | `#1B5E20` | Completed badge background |
| `status_not_started` | `#757575` | `#BDBDBD` | Not-started status icon, badge text |
| `status_not_started_container` | `#F5F5F5` | `#2D2D2D` | Not-started badge background |
| `warning` | `#F57F17` | `#FFCC80` | Warning banner, amber accent |
| `warning_container` | `#FFF8E1` | `#3E2000` | Warning banner background |

### 1.4 Kotlin Color Definitions (Jetpack Compose)

```kotlin
// app/src/main/java/com/learntrack/ui/theme/Color.kt
// Values extracted from Figma file 5dEMGt9iynTXgVGqjxd9IM, node 4:3 — October 28, 2026
package com.learntrack.ui.theme

import androidx.compose.ui.graphics.Color

// Primary (deep blue — progress ring fill, buttons, active states)
val Blue700 = Color(0xFF1565C0)
val Blue900 = Color(0xFF004D99)      // progress ring dark, header accent
val Blue100 = Color(0xFFD6E3FF)      // chip active bg, tonal containers

// Surfaces (light mode — verified from file)
val SurfaceLight     = Color(0xFFFBF9F9)   // card backgrounds
val SurfaceVariant   = Color(0xFFF5F3F3)   // secondary card bg
val SurfaceTint      = Color(0xFFEFEDED)   // dividers, inactive rows
val SurfaceDim       = Color(0xFFE9E8E7)   // skeleton/loading placeholder
val SurfaceNeutral   = Color(0xFFE3E2E2)   // borders, outlines

// On-surface text (verified from file)
val OnSurface        = Color(0xFF1B1C1C)   // primary text
val OnSurfaceVariant = Color(0xFF424752)   // secondary text, labels
val OnSurfaceMuted   = Color(0xFF727783)   // captions, disabled

// Primary text / links
val PrimaryText      = Color(0xFF004D99)   // links, primary-colored text

// Status — Completed (green)
val StatusCompleted           = Color(0xFF217128)   // completed icon
val StatusCompletedText       = Color(0xFF1B6D24)   // completed text on badge
val StatusCompletedContainer  = Color(0xFFA0F399)   // completed badge background

// Status — Not Started (grey — uses OnSurfaceMuted)
val StatusNotStarted          = Color(0xFF727783)
val StatusNotStartedContainer = Color(0xFFE9E8E7)

// Error
val ErrorContainer   = Color(0xFFFFDAD6)   // error state background

// Warning / Invalid data (amber)
val WarningText      = Color(0xFFA24F00)   // warning banner text
val WarningIcon      = Color(0xFF723600)   // warning icon
val WarningContainer = Color(0xFFFFDCC7)   // warning banner background
val WarningDark      = Color(0xFF311300)   // error/warning dark text
```

---

## 2. Design Tokens — Typography

Material 3 type scale mapped to Jetpack Compose TextStyle.

| Token | sp | Weight | Line height | Usage |
|---|---|---|---|---|
| `displayLarge` | 57 | Regular (400) | 64 | Unused in current screens |
| `displayMedium` | 45 | Regular | 52 | Unused |
| `displaySmall` | 36 | Regular | 44 | Unused |
| `headlineLarge` | 32 | Bold (700) | 40 | Progress percentage "53.7%" |
| `headlineMedium` | 28 | SemiBold (600) | 36 | Topic name on detail screen |
| `headlineSmall` | 24 | SemiBold | 32 | Stats numbers (22, 19, 41), section titles |
| `titleLarge` | 22 | Medium (500) | 28 | Screen titles, topic names in list, course name |
| `titleMedium` | 16 | Medium | 24 | App bar titles, card headings |
| `titleSmall` | 14 | Medium | 20 | Sub-headings, filter chip labels |
| `bodyLarge` | 16 | Regular | 24 | Primary body text, detail values |
| `bodyMedium` | 14 | Regular | 20 | Secondary body text, descriptions, bar labels |
| `bodySmall` | 12 | Regular | 16 | Captions, fine print |
| `labelLarge` | 14 | Medium | 20 | Buttons, primary action labels |
| `labelMedium` | 12 | Medium | 16 | Chips, badges, secondary labels |
| `labelSmall` | 11 | Medium | 16 | Stats category labels, footnotes |

Font family: Roboto (Android system default). No custom font unless explicitly specified.

```kotlin
// app/src/main/java/com/learntrack/ui/theme/Type.kt
package com.learntrack.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val LearnTrackTypography = Typography(
    headlineLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 36.sp
    ),
    headlineSmall = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp
    ),
    titleLarge = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
        lineHeight = 28.sp
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    labelLarge = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    labelSmall = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp
    )
    // All other styles use Material 3 defaults
)
```

---

## 3. Design Tokens — Spacing

All spacing values are multiples of 4 dp.

| Token | dp value | Usage |
|---|---|---|
| `space_2` | 2 dp | Divider thickness, fine gaps |
| `space_4` | 4 dp | Minimum internal padding, badge padding |
| `space_8` | 8 dp | Chip padding vertical, small gaps |
| `space_12` | 12 dp | List item vertical padding, section spacing small |
| `space_16` | 16 dp | Content margin (left/right), card padding, standard gap |
| `space_24` | 24 dp | Section spacing, bottom padding for cards |
| `space_32` | 32 dp | Large section spacing |
| `space_48` | 48 dp | Minimum touch target dimension |
| `space_56` | 56 dp | Standard list item height |
| `space_64` | 64 dp | App bar height |
| `space_80` | 80 dp | Bottom navigation bar height |
| `space_120` | 120 dp | Circular progress indicator diameter |

---

## 4. Design Tokens — Borders and Shapes

```kotlin
// app/src/main/java/com/learntrack/ui/theme/Shape.kt
package com.learntrack.ui.theme

import androidx.compose.material3.Shapes
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

val LearnTrackShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),   // Small chips, badges
    small = RoundedCornerShape(8.dp),         // Small chips, filter chips
    medium = RoundedCornerShape(12.dp),       // Cards, bottom sheets
    large = RoundedCornerShape(16.dp),        // Dialogs
    extraLarge = RoundedCornerShape(28.dp)    // Full pill buttons
)

// Additional tokens
val ShapePill = RoundedCornerShape(50)        // Buttons, progress bar ends
val ShapeProgressBar = RoundedCornerShape(4.dp)  // Progress bar track and fill
val ShapeStatusBadge = RoundedCornerShape(6.dp)  // Status badge chips
```

---

## 5. Design Tokens — Elevation and Shadow

Material 3 uses tonal elevation (color overlay) rather than drop shadows for most surfaces.

| Level | Overlay alpha | Usage |
|---|---|---|
| Level 0 | 0% | Background, base surfaces |
| Level 1 | 5% | Cards (default resting state) |
| Level 2 | 8% | Floating elements, bottom sheet drag handle area |
| Level 3 | 11% | Navigation drawer, modal bottom sheet |
| Level 4 | 12% | App bar scrolled state |
| Level 5 | 14% | FAB |

For dark mode: tonal elevation adds a primary-color tint at the specified alpha.
Drop shadows are used for modals and dialogs only.

---

## 6. Component States

Each component must implement the following states where applicable.

| State | Visual indicator | When it applies |
|---|---|---|
| Default | Normal colors, full opacity | Data loaded, no interaction |
| Hovered | Surface variant overlay | Mouse/pointer hover (for large-screen support) |
| Focused | Focus ring (primary color, 2 dp) | Keyboard/accessibility focus |
| Pressed | Ripple effect (material) | Touch/click during press |
| Disabled | 38% opacity, no interaction | Action not available |
| Loading | Shimmer animation on skeleton | Data loading |
| Error | Error color, error icon | Data failed to load |
| Selected | Primary container background | Selected chip, active nav item |

---

## 7. Components

### 7.1 ProgressHeroCard

**Purpose:** Shows course-level progress on the dashboard.
**Variants:** `default` | `loading` | `error`

```
ProgressHeroCard
├── Variant: default
│   ├── courseName: String
│   ├── completedCount: Int (= 22)
│   ├── totalCount: Int (= 41)
│   ├── notStartedCount: Int (= 19)
│   └── completionPercentage: Float (= 53.7f)
├── Variant: loading
│   └── (all data replaced by skeleton placeholders)
└── Variant: error
    ├── errorMessage: String
    └── onRetry: () -> Unit
```

States: default, loading, error

### 7.2 CircularProgressIndicator (custom)

> Note: distinct from the Material 3 built-in indeterminate indicator.

**Purpose:** Shows percentage completion as an animated arc.
**Properties:**
- `percentage: Float` — 0.0 to 100.0 (use 53.7f)
- `diameter: Dp` — default 120.dp
- `strokeWidth: Dp` — default 12.dp
- `primaryColor: Color` — progress arc fill
- `trackColor: Color` — unfilled arc

Variants: `default` | `loading` (no arc, grey ring only)
Accessibility: `semantics { contentDescription = "Completion: $percentage percent" }`

### 7.3 LinearProgressBar (custom)

**Purpose:** Shows percentage completion as a horizontal bar.
**Properties:**
- `percentage: Float` — 0.0 to 100.0 (use 53.7f)
- `height: Dp` — default 8.dp (thin) or 12.dp (standard)
- `animated: Boolean` — default true

Variants: `default` | `loading` | `invalid-data`
Accessibility: `semantics { progressBarRangeInfo = ProgressBarRangeInfo(percentage / 100f, 0f..1f) }`

### 7.4 StatsGrid

**Purpose:** Displays completed, not-started, and total counts in a grid.
**Properties:**
- `completed: Int` (= 22)
- `notStarted: Int` (= 19)
- `total: Int` (= 41)
- `completion: Float` (= 53.7f)

Variants: `default` | `loading` | `empty`

### 7.5 TopicRow

**Purpose:** A single row in the topic list.
**Properties:**
- `topic: Topic` — contains id, name, status
- `onClick: (topicId: String) -> Unit`

Variants:
- `completed` — green filled icon, "Completed" badge visible
- `not_started` — grey outlined icon
- `loading_skeleton` — shimmer placeholder

States: default, pressed (ripple)

### 7.6 StatusBadge

**Purpose:** Chip showing topic completion status.
**Properties:**
- `status: TopicStatus` — `COMPLETED` or `NOT_STARTED`

Variants:
- `completed` — green container, white text "Completed", checkmark leading icon
- `not_started` — grey container, medium-emphasis text "Not Started", clock leading icon

Size: compact (Label Medium, 12 sp, 6 dp corner radius, 8 dp horizontal padding, 4 dp vertical padding)
Accessibility: uses both color and text, not color alone.

### 7.7 FilterChip

**Purpose:** Filter the topic list by status.
**Properties:**
- `label: String` (e.g., "All (41)")
- `count: Int`
- `selected: Boolean`
- `onClick: () -> Unit`

Variants:
- `active` — filled (Primary Container background)
- `inactive` — outlined (Surface with Outline border)

### 7.8 ErrorState

**Purpose:** Full-screen or card-level error presentation.
**Properties:**
- `message: String`
- `onRetry: (() -> Unit)?` — if null, retry button is hidden

Layout: icon (error_outline, 48 dp) + headline + message + optional retry button, vertically centered.

### 7.9 EmptyState

**Purpose:** Full-screen or section-level empty list presentation.
**Properties:**
- `headline: String`
- `body: String`
- `actionLabel: String?`
- `onAction: (() -> Unit)?`

Layout: icon (search_off or similar, 48 dp) + headline + body + optional action button.

### 7.10 LoadingSkeleton

**Purpose:** Placeholder shimmer while data loads.
**Variants:**
- `topicRowSkeleton` — mimics TopicRow layout
- `progressCardSkeleton` — mimics ProgressHeroCard layout
- `statsGridSkeleton` — mimics StatsGrid layout

Shimmer: horizontal gradient animation, light-to-dark-to-light, 1000 ms repeat.

### 7.11 WarningBanner

**Purpose:** Non-blocking warning for data integrity issues.
**Properties:**
- `message: String`
- `onDismiss: () -> Unit`

Layout: amber container, warning icon, message text, dismiss (×) button.
Role: `alert` (always announced by screen reader).

---

## 8. Component Naming and Variant Conventions

| Convention | Example |
|---|---|
| Component name: PascalCase | `ProgressHeroCard`, `TopicRow` |
| Variant separator: `/` | `TopicRow/completed` |
| State added after variant: `/` | `TopicRow/completed/pressed` |
| Figma layer naming: PascalCase | `TopicRow`, `StatusBadge` |
| Kotlin Composable: PascalCase with `@Composable` | `@Composable fun TopicRow(...)` |
| Kotlin file: PascalCase.kt | `TopicRow.kt` |
| Design token prefix: `md_theme_` for Material | `md_theme_light_primary` |
| Custom token prefix: `status_` for status | `status_completed`, `status_not_started` |

---

## 9. Layout Grid and Breakpoints

### Android phone (primary target)

| Dimension | Value |
|---|---|
| Minimum screen width | 360 dp |
| Content margin | 16 dp (left and right) |
| Column gutter | 16 dp |
| Number of columns | 4 (compact layout) |
| Base grid | 4 dp |

### Android medium (large phone / small tablet — secondary target)

| Dimension | Value |
|---|---|
| Screen width range | 600 dp – 839 dp |
| Content margin | 24 dp |
| Column gutter | 16 dp |
| Number of columns | 8 |
| Layout change | Topic list → 2-column grid; stats → side-by-side |

### Breakpoint behavior

| Width | Layout change |
|---|---|
| < 600 dp | Single-column list, portrait stack layout |
| ≥ 600 dp | Two-column topic grid, wider cards |
| ≥ 840 dp | (Out of scope for initial release) |

---

## 10. Accessibility States

All components must support these accessibility states:

| State | Implementation |
|---|---|
| Screen reader label | `contentDescription` on every meaningful Composable |
| Focusable | All interactive elements receive focus via keyboard/dpad |
| Disabled | `enabled = false`, reduced opacity, no interaction |
| Selected | `isSelected = true` in semantics for selected chips |
| Progress | `progressBarRangeInfo` semantics on progress components |
| Error | `error = "Error message"` in semantics where applicable |
| Heading | `heading()` semantics on screen titles |
| Live region | `liveRegion = LiveRegionMode.Polite` on loading states |

---

## Notes

- All hex values in this document are proposed. They may be updated during design review or brand alignment.
- The color palette was derived from a deep-blue seed color (#1565C0) representing education and technology themes. If a brand color specification exists, replace this palette and regenerate the Material 3 scheme using [Material Theme Builder](https://m3.material.io/theme-builder).
- This design system is version 1.0. All changes must be recorded as versioned updates to this document.
