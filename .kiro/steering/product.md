# LearnTrack — Product Steering

## Purpose

LearnTrack is an Android application for tracking course progress. Learners use it to see which topics in a structured course are completed and which remain, and to stay motivated through clear progress visualization.

The current phase delivers a fully functional course progress tracking feature for the **AI & Python Technology Course**.

---

## Target Users

- Learners enrolled in the AI & Python Technology Course who want a clear view of their progress.
- Developers and content authors who maintain the course topic data.

---

## Supported Course-Progress Behavior

| Behavior | Description |
|---|---|
| View overall completion | Dashboard shows 53.7% progress (22 of 41 topics complete) |
| View topic list | Scrollable list of all 41 topics with per-topic status badge |
| View topic detail | Tap a topic row to see its name and status |
| Loading state | Spinner shown while topic data loads |
| Empty state | Shown when the topic list returns zero entries |
| Error state | Shown when the JSON is missing, malformed, or fails schema validation |

Progress is always calculated from live data (`completed / total * 100`). The value 53.7% is never hard-coded in application logic.

---

## Core Data — AI & Python Technology Course

| Metric | Authoritative value |
|---|---|
| Total topics | 41 |
| Completed topics | 22 |
| Not-started topics | 19 |
| Completion percentage | 53.7% (53.658…% rounded to one decimal place) |
| Data source | `app/src/main/assets/learntrack_topics.json` |

These values are the authoritative distribution for this phase and may only change with explicit user approval.

---

## In-Scope Features

- Create `learntrack_topics.json` with 41 topics (22 completed, 19 not-started).
- Course dashboard screen: completion percentage, topic counts, progress bar, loading/error/empty states.
- Topic list screen: all 41 topics with status badges, scrollable.
- Topic detail screen: topic name, status, back navigation.
- Progress calculation use case (derived from data, never hard-coded).
- JSON schema validation on app startup.
- Unit tests for progress calculation and JSON parsing.
- UI tests for dashboard and topic list.
- Regression test asserting the 22/19/41 distribution.
- All AI-SDLC documentation, Kiro specs, steering files, skills, and design prompts.
- Pre-push quality gate scripts and GitHub Actions CI.
- Architecture, data-flow, and environment documentation.

---

## Out-of-Scope Features

| Feature | Reason |
|---|---|
| Authentication / registration | Not requested |
| Payment or subscription | Not requested |
| Administrative or instructor dashboards | Not requested |
| Multi-course enrollment or course catalog | Not this phase |
| Backend API or remote sync | Not justified; asset JSON is sufficient |
| Database migration tooling | No existing database to migrate |
| Framework migration (Compose → Views) | Not requested |
| Unrequested production deployment | Requires explicit approval |
| Push notifications or background sync | Not requested |
| Analytics or crash reporting | Not requested this phase |
| Localization or multi-language support | Not requested this phase |
| Mutable topic status via in-app editing | Not requested; JSON is read-only at runtime |
| Destructive changes to existing course records | Prohibited without explicit instruction |

---

## Non-Goals

- The app does not submit or record user answers or quiz results.
- The app does not sync topic status to a backend.
- The app does not allow the user to mark topics complete at runtime.
- The app does not expose an instructor view or administrative controls.
- Completion percentage is not a user-settable value; it is always derived from data.

---

## Design Principles and UX Guidelines

1. **Data first.** Every progress value shown in the UI comes from `learntrack_topics.json`. Hard-coded counts or percentages are never acceptable in application logic.
2. **Graceful degradation.** If data cannot be loaded, the app shows a friendly error — it never crashes.
3. **Accessibility.** All interactive elements carry content descriptions. Progress is communicated as text, not only graphically. Text contrast meets WCAG 2.1 AA (4.5:1 normal, 3:1 large).
4. **Material 3.** All components use the Material 3 design language with system light/dark mode support.
5. **Stateless composables.** UI composables collect state from the ViewModel and do not own mutable data.
6. **Loading, empty, error, success states.** Every data-driven screen must handle all four states explicitly.
7. **No unrelated redesigns.** UI changes are limited to the course progress screens. Unrelated screens are not touched.
8. **Minimal scope creep.** Features not listed in scope require explicit written approval before implementation begins.
