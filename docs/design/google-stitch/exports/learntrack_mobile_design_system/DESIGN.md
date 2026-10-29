---
name: LearnTrack Mobile Design System
colors:
  surface: '#fbf9f9'
  surface-dim: '#dbdad9'
  surface-bright: '#fbf9f9'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#f5f3f3'
  surface-container: '#efeded'
  surface-container-high: '#e9e8e7'
  surface-container-highest: '#e3e2e2'
  on-surface: '#1b1c1c'
  on-surface-variant: '#424752'
  inverse-surface: '#303031'
  inverse-on-surface: '#f2f0f0'
  outline: '#727783'
  outline-variant: '#c2c6d4'
  surface-tint: '#005db7'
  primary: '#004d99'
  on-primary: '#ffffff'
  primary-container: '#1565c0'
  on-primary-container: '#dae5ff'
  inverse-primary: '#a9c7ff'
  secondary: '#1b6d24'
  on-secondary: '#ffffff'
  secondary-container: '#a0f399'
  on-secondary-container: '#217128'
  tertiary: '#7d3c00'
  on-tertiary: '#ffffff'
  tertiary-container: '#a24f00'
  on-tertiary-container: '#ffdecc'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
  primary-fixed: '#d6e3ff'
  primary-fixed-dim: '#a9c7ff'
  on-primary-fixed: '#001b3d'
  on-primary-fixed-variant: '#00468c'
  secondary-fixed: '#a3f69c'
  secondary-fixed-dim: '#88d982'
  on-secondary-fixed: '#002204'
  on-secondary-fixed-variant: '#005312'
  tertiary-fixed: '#ffdcc7'
  tertiary-fixed-dim: '#ffb787'
  on-tertiary-fixed: '#311300'
  on-tertiary-fixed-variant: '#723600'
  background: '#fbf9f9'
  on-background: '#1b1c1c'
  surface-variant: '#e3e2e2'
typography:
  headline-lg:
    fontFamily: Inter
    fontSize: 32px
    fontWeight: '700'
    lineHeight: 40px
    letterSpacing: 0px
  headline-sm:
    fontFamily: Inter
    fontSize: 24px
    fontWeight: '600'
    lineHeight: 32px
    letterSpacing: 0px
  title-lg:
    fontFamily: Inter
    fontSize: 22px
    fontWeight: '600'
    lineHeight: 28px
    letterSpacing: 0px
  title-md:
    fontFamily: Inter
    fontSize: 16px
    fontWeight: '500'
    lineHeight: 24px
    letterSpacing: 0.15px
  title-sm:
    fontFamily: Inter
    fontSize: 14px
    fontWeight: '500'
    lineHeight: 20px
    letterSpacing: 0.1px
  body-lg:
    fontFamily: Inter
    fontSize: 16px
    fontWeight: '400'
    lineHeight: 24px
    letterSpacing: 0.5px
  body-md:
    fontFamily: Inter
    fontSize: 14px
    fontWeight: '400'
    lineHeight: 20px
    letterSpacing: 0.25px
  body-sm:
    fontFamily: Inter
    fontSize: 12px
    fontWeight: '400'
    lineHeight: 16px
    letterSpacing: 0.4px
  label-lg:
    fontFamily: Inter
    fontSize: 14px
    fontWeight: '500'
    lineHeight: 20px
    letterSpacing: 0.1px
  label-md:
    fontFamily: Inter
    fontSize: 12px
    fontWeight: '500'
    lineHeight: 16px
    letterSpacing: 0.5px
  label-sm:
    fontFamily: Inter
    fontSize: 11px
    fontWeight: '500'
    lineHeight: 16px
    letterSpacing: 0.5px
rounded:
  sm: 0.25rem
  DEFAULT: 0.5rem
  md: 0.75rem
  lg: 1rem
  xl: 1.5rem
  full: 9999px
spacing:
  gutter: 1rem
  margin: 1rem
  space-xs: 0.25rem
  space-sm: 0.5rem
  space-md: 1rem
  space-lg: 1.5rem
  space-xl: 2rem
---

## Brand & Style

The design system is engineered for structured mobile education workflows, personal syllabus management, and academic momentum. It balances analytical precision with motivating positive reinforcement. The aesthetic adheres directly to the Google Material 3 (Material You) Android design paradigm, optimized specifically for high-frequency daily interaction and glanceable visual progress feedback.

### Visual Character & Tone
- **Methodical & Trustworthy:** Dense academic and progress information is organized into clear visual chunks, preventing cognitive fatigue.
- **Motivating & Rewarding:** Progress states use immediate, clear chromatic cues—accentuating completed milestones without visual clutter.
- **Android-Native Polish:** Expressive pill-shaped interactives paired with structured rounded cards, tactile ripple feedback, tonal elevation layers, and Material Symbols Rounded iconography.

## Colors

The color system structures academic hierarchy and task execution through systematic chromatic roles, mapped cleanly across both Light and Dark themes.

### Status & Academic Semantics
- **Primary (`#1565C0`):** Represents ongoing tracking, active filters, core interactions, interactive accents, and total workload counts.
- **Success / Completed (`#2E7D32`):** Communicates finished modules, completed daily streaks, full mastery ratings, and verified achievements.
- **Tertiary / Completion Highlight (`#F57F17`):** Marks urgent deadlines, impending test dates, streak flames, and highlighted milestones.
- **Neutral Medium Grey (`#757575`):** Denotes untouched or "Not Started" units, inactive checkpoints, and secondary metadata labels.

### Theme Surface Mapping
- **Light Theme:**
  - Background: `#F8FAFC`
  - Surface: `#FFFFFF`
  - Surface Variant: `#E2E8F0`
  - Outline: `#CBD5E1`
  - On Background / On Surface: `#0F172A`
- **Dark Theme:**
  - Background: `#121212`
  - Surface: `#1E1E1E`
  - Surface Variant: `#2D3748`
  - Outline: `#4A5568`
  - On Background / On Surface: `#F1F5F9`

## Typography

The type scale implements a strict Material 3 hierarchy using `Inter` for maximal legibility across high-density mobile screens. Numerical values, course progress ratios, and deadlines are structured systematically:

- **Headline Large (32px Bold):** High-level view headers, overarching milestone summaries, and weekly completion statistics.
- **Headline Small (24px SemiBold):** Main screen headers and top app bar page titles.
- **Title Large (22px SemiBold):** Card container headers, active track cards, and bottom sheet sheet headers.
- **Title Medium (16px Medium):** Module names, syllabus track items, and list item headlines.
- **Body Medium (14px Regular):** Standard supporting body copy, study notes, and task explanations.
- **Label Small (11px Medium):** Status indicator tags, timestamp badges, auxiliary progress percentages, and bottom navigation labels.

## Layout & Spacing

Layouts follow an 8dp (0.5rem) spatial system aligned with Android touch targets and standard viewport specifications.

### Handheld Architecture
- **Mobile Grid (Compact < 600dp):** Single-column 4-column layout with 16dp (`margin: 1rem`) screen edge margins and 16dp (`gutter: 1rem`) gutters between stacked cards.
- **Touch Targets:** Interactive targets maintain a minimum dimension of 48dp × 48dp regardless of visual bounding pill size.
- **Vertical Rhythm:**
  - Related inner content groups: 8dp (`space-sm`).
  - Standard card interior padding: 16dp (`space-md`).
  - Section separation: 24dp (`space-lg`) to 32dp (`space-xl`).
- **Edge-to-Edge:** Scaffold views extend edge-to-edge behind system navigation and status bars with dynamic padding applied to Android WindowInsets.

## Elevation & Depth

Visual hierarchy adopts Material 3 tonal elevation accompanied by subtle ambient shadows, minimizing harsh drop shadows in favor of surface tonal luminance shifts.

### Depth Hierarchy
- **Level 0 (Background):** Base canvas layer (`#F8FAFC` light / `#121212` dark).
- **Level 1 (Card / Resting Surfaces):** Base container fill (`#FFFFFF` light / `#1E1E1E` dark) paired with a 1px boundary stroke using Surface Variant (`#E2E8F0` / `#2D3748`). Shadow: `0px 1px 3px rgba(0, 0, 0, 0.06), 0px 1px 2px rgba(0, 0, 0, 0.04)`.
- **Level 2 (Scrolled App Bar / Hover / Drag Cards):** Surface elevated with a 5% Primary color tint wash. Shadow: `0px 3px 6px rgba(0, 0, 0, 0.08), 0px 2px 4px rgba(0, 0, 0, 0.06)`.
- **Level 3 (Bottom Navigation / Dialogs / Bottom Sheets):** Floating overlay surfaces with tonal tinting and `0px 8px 16px rgba(0, 0, 0, 0.12)`.

## Shapes

The design system enforces a strict dual-geometry shape standard:

- **Cards & Sheet Containers (12px / Medium):** All progress cards, track overviews, summary containers, and dialog frames use a continuous 12px border radius.
- **Pill Shape (9999px / Full):** Buttons, interactive filter chips, status badges, progress bar tracks, indicator tags, and input search bars strictly use full capsule roundedness (9999px).

## Components

### Iconography
- **Set:** Material Symbols Rounded exclusively, standard weight (400), optical size 24px (compact indicators at 20px).

### Buttons
- **Primary Filled Button:** Height 44dp, pill-shaped (9999px), background `#1565C0`, text `#FFFFFF` (Label Large 14px), 24dp horizontal padding.
- **Tonal Button:** Height 44dp, pill-shaped, background Surface Variant with Primary color text.
- **Outlined Button:** 1px stroke `#CBD5E1` (light) / `#4A5568` (dark), transparent interior, text `#1565C0`.

### Chips & Progress Filters
- **Filter Chips:** Height 32dp, pill-shaped (9999px), 12dp horizontal padding, Label Medium typography.
  - Active: Background `#1565C0`, text `#FFFFFF`.
  - Inactive: Background transparent, border 1px `#CBD5E1`, text `#757575`.
- **Status Indicator Badges:** Compact pill badge (height 24dp, 8dp padding).
  - *Completed:* `#2E7D32` container tint with `#2E7D32` bold label and check icon.
  - *In Progress:* `#1565C0` container tint with `#1565C0` label.
  - *Not Started:* `#757575` surface tint with `#757575` label.
  - *Milestone Alert:* `#F57F17` surface tint with `#F57F17` label.

### Cards
- Standard course track and module cards feature a 12px corner radius, `#FFFFFF` (light) or `#1E1E1E` (dark) background, 1px Surface Variant stroke, and 16dp internal padding.
- Include a linear progress bar pinned either along the lower interior or below the title group.

### Progress Bars & Rings
- **Linear Progress Indicator:** Height 8dp, rounded ends (pill 9999px). Background is Surface Variant; fill is `#1565C0` (overall/active) or `#2E7D32` (100% completion).
- **Circular Progress Ring:** 48dp diameter, 4dp stroke thickness, rounded stroke caps.

### Lists
- Two-line and three-line list items within cards or bottom sheets. Left-aligned status symbol (24dp rounded glyph or status ring), Title Medium module name, and Body Medium metadata. Dividers are 1px Surface Variant inset by 56dp.

### Checkboxes & Radios
- Checkboxes feature a 4px corner radius; radios are circular. Checked state fills with `#2E7D32` (completed study milestone) or `#1565C0` (selected unit), displaying crisp white glyphs. Unchecked state is bordered with `#757575`.

### Input Fields
- Outlined text fields with pill-shaped boundaries (28dp radius) for quick searches, or 12px radius for multi-line study logs. Label Medium floating label, 16dp horizontal text padding. Focused state shifts border to 2px `#1565C0`.