# LearnTrack — Google Stitch: Course Progress Screen Prompt

**Tool:** Google Stitch (manual execution required)
**Scope:** This is a focused prompt for generating only the course progress screen and its components. Use this prompt when you need to regenerate or refine the progress visualization independently of the other screens.
**Access status:** Google Stitch MCP access was not available during this phase. Execute this prompt manually in Google Stitch.

---

## When to Use This Prompt

Use this prompt instead of the master prompt when:
- You need to iterate on the progress visualization only (not all screens).
- The master prompt has already been run and you want a focused variation.
- You are testing different progress bar or circular indicator styles.

---

## Exact Data Values (Do Not Change)

| Field | Value |
|---|---|
| Course name | AI & Python Technology Course |
| Completed topics | 22 |
| Not-started topics | 19 |
| Total topics | 41 |
| Completion percentage | 53.7% |
| Calculation | 22 ÷ 41 × 100 = 53.66%, displayed as 53.7% |

These values are authoritative. Do not adjust them to make the design look more or less complete.

---

## Focused Prompt — Copy and Paste into Google Stitch

```
Design the course progress screen and progress card component for LearnTrack, an
Android app (Jetpack Compose, Material 3) for tracking learning progress.

EXACT DATA VALUES — do not change:
- Course: "AI & Python Technology Course"
- Completed: 22 topics
- Not started: 19 topics
- Total: 41 topics
- Completion: 53.7%

COMPONENT 1 — Progress Hero Card

A card component that appears at the top of the course dashboard.

Visual layout (top to bottom):
1. Card header row:
   - Left: course name "AI & Python Technology Course" (Title Medium, 2 lines max)
   - Right: 3-dot overflow menu icon
2. Large circular progress ring (diameter 120 dp):
   - Arc from 0° clockwise to 193.3° (53.7% of 360°)
   - Filled arc: Primary color (#1565C0)
   - Unfilled arc: Surface Variant color
   - Arc stroke width: 12 dp
   - Center text: "53.7%" (Headline Large, bold)
   - Below center: "Complete" (Label Medium, muted)
3. Three-column stats row below the ring:
   - Column 1: "22" (Headline Small, primary color) / "Completed" (Label Small, muted)
   - Column 2: "41" (Headline Small, on-surface) / "Total" (Label Small, muted)
   - Column 3: "19" (Headline Small, tertiary/amber) / "Not Started" (Label Small, muted)
4. Horizontal linear progress bar:
   - Width: full card width minus 32 dp padding
   - Height: 8 dp
   - Corner radius: 4 dp (pill shape)
   - Fill: 53.7% of width, Primary color
   - Background: Surface Variant
   - Animated: fill grows from 0 to 53.7% on first render (300 ms ease-in-out)
5. Card footer row:
   - "View All Topics →" text button (right-aligned, primary color)

Card specs:
- Background: Surface color (not elevated)
- Corner radius: 12 dp
- Elevation: 1 dp tonal (Material 3 tonal surface)
- Padding: 16 dp on all sides
- Width: match parent minus 32 dp total margin (16 dp left + 16 dp right)

COMPONENT 2 — Progress Summary Bottom Sheet

A bottom sheet that expands from the bottom of the screen when the user taps "View
Progress Details".

Visual layout:
- Handle bar at top (32×4 dp pill, centered)
- Title row: "Your Progress" (Title Large) + "×" close button
- Divider
- Linear progress bar (full width, 12 dp tall):
  - Fill: 53.7%, Primary color
  - Background: Surface Variant
  - Label row below bar: "53.7% complete" (left-aligned, Body Medium)
- Stats grid (2 columns × 2 rows):
  - Cell 1: ✓ icon (green) + "22" (number) + "Completed" (label)
  - Cell 2: ○ icon (grey) + "19" (number) + "Not Started" (label)
  - Cell 3: ⊞ icon (blue) + "41" (number) + "Total Topics" (label)
  - Cell 4: 🏆 icon (amber) + "53.7%" (number) + "Completion" (label)
- Motivation text: "You're more than halfway there! Keep going." (Body Medium, centered,
  italic, muted color)
- Primary button: "View All Topics" (filled, full width)
- Bottom padding: 24 dp + system navigation bar inset

STATES TO DESIGN FOR BOTH COMPONENTS:

State A — Default (data loaded correctly):
- Show all values as specified above
- Progress animations in their completed (end) state

State B — Loading:
- Replace circular ring with a skeleton ring (grey, no percentage)
- Replace stat numbers with skeleton rectangles (grey placeholder blocks)
- Replace linear bar with a skeleton bar (grey shimmer)
- Do NOT show spinner over card content

State C — Error:
- Replace circular ring with an error icon (Material "error_outline", 48 dp, error color)
- Error message below icon: "Couldn't load progress"
- Stats row replaced with single line "Data unavailable"
- Retry button (outlined, full width of card): "Retry"

State D — Invalid data (percentage is outside 0–100, or counts don't add up):
- Show amber/yellow warning banner at top of card:
  "⚠ Progress data may be inaccurate"
- Continue showing last known values with reduced opacity (0.6 alpha)
- Dismiss icon on warning banner

INTERACTION NOTES:
- Progress Hero Card: tapping anywhere on the card (except "View All Topics") opens the
  Progress Summary Bottom Sheet
- "View All Topics →" button navigates to the Topic List screen
- Progress Summary Bottom Sheet: dismiss by tapping ×, tapping outside the sheet, or
  swiping down
- Circular ring: no tap action (display only)
- Stats cells: no tap action (display only)

TYPOGRAPHY:
- Circular center "53.7%": Headline Large (32sp), bold, on-surface color
- Stats numbers (22, 19, 41): Headline Small (24sp), respective color
- Stats labels ("Completed", etc.): Label Small (11sp), on-surface-variant
- Card header text: Title Medium (16sp), on-surface
- Linear bar label "53.7% complete": Body Medium (14sp), on-surface-variant
- Motivation text: Body Medium (14sp), italic, on-surface-variant

COLORS:
- Completed stat (22): #2E7D32 (accessible green)
- Not Started stat (19): #757575 (medium grey)
- Total stat (41): #1565C0 (primary blue)
- Completion stat (53.7%): #F57F17 (amber)
- Progress fill: #1565C0 (primary)
- Progress background: #E3E8F0 (surface variant, light mode)
- Card background: #FFFFFF (light mode), #1E1E1E (dark mode)

ACCESSIBILITY:
- Circular progress: contentDescription = "Course completion: 53.7 percent, 22 of 41
  topics completed"
- Linear progress bar: role="progressbar", value 53.7, min 0, max 100,
  contentDescription = "Progress bar showing 53.7 percent completion"
- Stats cells: each cell's icon + number + label are grouped into a single accessibility
  node (e.g., "22 topics completed")
- Warning banner: role="alert", always read aloud by screen reader
- Retry button: contentDescription = "Retry loading course progress"

Show designs in both light mode and dark mode.
```

---

## Iteration Guidance

After generating the first design pass, review against these questions:

1. Does the circular ring show exactly 53.7% fill (approximately 193° arc)?
2. Do the three stat values show exactly 22, 41, and 19?
3. Is the percentage label "53.7%" — not rounded to 54% or 53%?
4. Is the linear bar fill at approximately 54% of the bar width (53.7%)?
5. Are all four states (default, loading, error, invalid-data) present?
6. Do both light and dark mode variants look correct?
7. Is text legible in both modes (contrast sufficient)?

If any of these fail, refine the prompt with specific correction instructions and regenerate.

---

## Notes

- The exact topic names for the "AI & Python Technology Course" are defined in `learntrack_topics.json` (pending creation). The progress screen does not need individual topic names — it only uses aggregate counts.
- These color values (#1565C0, #2E7D32, etc.) are proposed values. They may be updated after the full design system review.
- The "motivation text" is placeholder copy. Replace with final copy before implementation.
