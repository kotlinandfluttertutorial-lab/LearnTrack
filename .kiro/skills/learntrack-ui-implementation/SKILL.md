---
name: learntrack-ui-implementation
description: Implement LearnTrack UI screens following the approved Stitch and Figma designs, reusing existing Material 3 components and design tokens, implementing all required states (loading, empty, error, success), and avoiding unrelated UI redesigns.
version: 1.0.0
tags: [learntrack, ui, compose, android, material3, accessibility]
---

## Purpose

Guide the implementation of LearnTrack's course progress UI screens on Android using Jetpack Compose and Material 3. Ensure all screens follow the approved designs, handle every required UI state, meet accessibility requirements, and stay within the defined scope boundary.

---

## When to Use

- When implementing `CourseProgressScreen`, `TopicListScreen`, or `TopicDetailScreen`.
- When updating UI composables to reflect a change in `CourseUiState` or `CourseProgress`.
- When adding loading, empty, error, or success state handling to any data-driven screen.
- When reviewing a UI pull request for scope compliance and accessibility.
- Before creating any new composable to confirm the design reference and component inventory.

---

## Required Inputs

| Input | Source | Required? |
|---|---|---|
| Figma screen inventory | `docs/design/figma/learntrack-screen-inventory.md` | Yes |
| Figma design system | `docs/design/figma/learntrack-design-system.md` | Yes |
| Stitch course progress prompt | `docs/design/google-stitch/course-progress-prompt.md` | Yes |
| Architecture doc (UI state) | `docs/architecture/learntrack-architecture.md` § 7 | Yes |
| Kiro spec requirements | `.kiro/specs/course-progress-update/requirements.md` | Yes |
| Kiro spec design | `.kiro/specs/course-progress-update/design.md` | Yes |

---

## Step-by-Step Procedure

### Step 1 — Read design references before writing code

Before implementing any screen, read:

1. `docs/design/figma/learntrack-screen-inventory.md` — identify the screen name, layout zones, and components.
2. `docs/design/figma/learntrack-design-system.md` — identify applicable design tokens (colors, typography, spacing, shape).
3. `docs/design/google-stitch/course-progress-prompt.md` — confirm loading, empty, and error state specifications.

Do not begin coding until you can answer: What components are on this screen? What design tokens do they use? What states must it handle?

### Step 2 — Confirm the UiState sealed class

Read `CourseUiState.kt` and confirm all four states exist:

```kotlin
sealed class CourseUiState {
    object Loading : CourseUiState()
    data class Success(val progress: CourseProgress, val topics: List<Topic>) : CourseUiState()
    data class Error(val message: String) : CourseUiState()
    object Empty : CourseUiState()
}
```

If the sealed class is missing a state required by the design, add it before implementing the screen.

### Step 3 — Implement CourseProgressScreen (Dashboard)

Required elements per FR-005, FR-006, FR-010, FR-011, FR-013:

- **Loading state:** `CircularProgressIndicator` centered on screen. Content description: `"Loading course data"`.
- **Error state:** Error message text from `uiState.message`. Icon or illustration optional. No technical exception details shown.
- **Empty state:** Friendly message indicating no topics are available. No fabricated progress data.
- **Success state:**
  - Completion percentage text: `"%.1f%%".format(progress.completionPercentage)` — e.g., `"53.7%"`.
  - "X of Y topics" text using `progress.completed` and `progress.total`.
  - `LinearProgressIndicator` with `progress = progress.completionPercentage / 100f`. Content description: `"Course completion: 53.7 percent"` (dynamic).
  - Navigation entry point to `TopicListScreen`.

Composable signature:

```kotlin
@Composable
fun CourseProgressScreen(
    uiState: CourseUiState,
    onNavigateToTopics: () -> Unit,
    modifier: Modifier = Modifier
)
```

The screen-level composable that collects ViewModel state must use `collectAsStateWithLifecycle()`.

### Step 4 — Implement TopicListScreen

Required elements per FR-007, FR-012:

- **Loading state:** `CircularProgressIndicator`.
- **Empty state:** Empty state composable — no fabricated topic rows.
- **Error state:** Error message.
- **Success state:**
  - `LazyColumn` (scrollable) with one row per topic.
  - Each row: topic name text + status badge.
  - Status badge for `TopicStatus.COMPLETED`: green-tinted chip or badge with label "Completed". Content description: `"<topic name>, completed"`.
  - Status badge for `TopicStatus.NOT_STARTED`: neutral chip or badge with label "Not Started". Content description: `"<topic name>, not started"`.
  - Tap on any row calls `onTopicClick(topic)`.

Composable signature:

```kotlin
@Composable
fun TopicListScreen(
    uiState: CourseUiState,
    onTopicClick: (Topic) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
)
```

### Step 5 — Implement TopicDetailScreen

Required elements per FR-008, FR-014:

- Topic name as heading text.
- Topic status displayed as a badge or text label.
- Back navigation: system back gesture and/or back button returns to `TopicListScreen` without reloading data.
- No data loss on back navigation (ViewModel state is shared; list is not re-fetched).

Composable signature:

```kotlin
@Composable
fun TopicDetailScreen(
    topic: Topic,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
)
```

### Step 6 — Apply design tokens

Use only values from `learntrack-design-system.md` and the Material 3 theme:

- Colors: `MaterialTheme.colorScheme.*` — never hard-code hex values.
- Typography: `MaterialTheme.typography.*` — never hard-code font sizes.
- Spacing: multiples of 4 dp — use `Modifier.padding(N.dp)` aligned to the 4 dp grid.
- Shape: `MaterialTheme.shapes.*` for cards and chips.

### Step 7 — Accessibility checklist

For every composable, verify:

- [ ] Progress bar has a dynamic `contentDescription` matching the displayed percentage.
- [ ] Status badges have `contentDescription` that includes both the topic name and status.
- [ ] Loading indicators have `contentDescription = "Loading"` or equivalent.
- [ ] All tappable elements have a minimum touch target of 48 × 48 dp.
- [ ] Text contrast meets WCAG 2.1 AA (4.5:1 for body text, 3:1 for large text / UI components).
- [ ] Completion percentage is rendered as readable text, not only as a graphical fill.
- [ ] `Modifier.semantics { }` is used where a visual-only element needs a semantic label.

### Step 8 — Scope boundary check

Before committing:

- [ ] Only `CourseProgressScreen.kt`, `TopicListScreen.kt`, `TopicDetailScreen.kt`, `CourseProgressViewModel.kt`, and `CourseUiState.kt` were modified.
- [ ] No screens or composables outside the course-progress feature were changed.
- [ ] No new navigation destinations were added beyond the three screens defined in the spec.
- [ ] No new dependencies were introduced without approval.

---

## Constraints

- **Stateless composables only.** No `remember { mutableStateOf(...) }` for data that belongs in the ViewModel.
- **No hard-coded progress values.** The strings `"53.7%"`, `"22"`, `"41"` must not appear as literals in composables. They are always formatted from `CourseProgress` fields.
- **No unrelated redesigns.** Do not change app-level theming, navigation structure, or other screens.
- **Material 3 components only.** Do not use deprecated Material 2 components (`Button` from `androidx.compose.material`, not `material3`).
- **Follow the Figma spec.** If the Figma spec and the Stitch prompt conflict, the Figma spec wins. Raise a conflict to the user before deviating.
- **Platform: Android / Jetpack Compose.** Do not apply web, Flutter, or cross-platform patterns.

---

## Expected Outputs

| Output | Location |
|---|---|
| `CourseProgressScreen.kt` | `app/src/main/java/com/learntrack/ui/progress/` |
| `TopicListScreen.kt` | `app/src/main/java/com/learntrack/ui/topics/` |
| `TopicDetailScreen.kt` | `app/src/main/java/com/learntrack/ui/topics/` |
| `CourseUiState.kt` | `app/src/main/java/com/learntrack/ui/progress/` |
| Updated `CourseProgressViewModel.kt` | `app/src/main/java/com/learntrack/ui/progress/` |

---

## Verification Criteria

- [ ] All three screens compile without errors.
- [ ] Each screen handles Loading, Success, Error, and Empty states.
- [ ] Progress percentage displays as `"53.7%"` when data is 22/41.
- [ ] `LinearProgressIndicator` progress value is `0.537...` (not `53.7`).
- [ ] All interactive elements have `contentDescription`.
- [ ] No hard-coded hex colors or font sizes.
- [ ] No composable imports directly from `data.*` packages.
- [ ] Back navigation from detail screen works without reloading.
- [ ] `./gradlew lintDebug` reports no new errors related to these files.
