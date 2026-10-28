# LearnTrack — Screen Inventory

**Platform:** Android (Jetpack Compose, Material 3)
**Figma file:** LearnTrack — UI Designs _(URL to be filled after manual Figma setup — do not fabricate)_
**Version:** 1.0
**Status:** Pending Figma file creation

---

## Overview

This document catalogs every screen in scope for the LearnTrack course progress feature. Each screen entry includes its ID, name, purpose, entry points, components used, data requirements, states, and acceptance criteria. Every screen is mapped to functional requirements and Jira work items.

Total screens in scope: **4**
Total screen variants (states): **20+**

---

## Screen Inventory

### Screen 1 — Course Dashboard

| Field | Value |
|---|---|
| **Screen ID** | SCR-001 |
| **Name** | Course Dashboard |
| **Figma page** | Page 3 — Course Dashboard |
| **Figma frame prefix** | `dashboard/` |
| **Route / destination** | Root screen, launched on app start |
| **Bottom nav tab** | Home (index 0) |

**Purpose:**
The primary entry point for the learner. Shows an at-a-glance view of overall course progress, motivates continued learning, and provides navigation to the full topic list.

**Entry Points:**
- App launch (cold and warm start)
- Bottom navigation bar → Home tab
- Back navigation from any depth (returns to this screen)

**Components Used:**
| Component | Variant | Source |
|---|---|---|
| `ProgressHeroCard` | default, loading, error | `learntrack-design-system.md §7.1` |
| `CircularProgressIndicator` | default, loading | `learntrack-design-system.md §7.2` |
| `StatsGrid` (3-column) | default, loading | `learntrack-design-system.md §7.4` |
| Continue Learning card | — | Inline composition |
| Material 3 TopAppBar | — | Material 3 |
| Material 3 NavigationBar | home selected | Material 3 |
| Material 3 FilledButton | "View All Topics" | Material 3 |

**Data Requirements:**
| Field | Source | Value |
|---|---|---|
| `completedCount` | `learntrack_topics.json` | 22 |
| `notStartedCount` | `learntrack_topics.json` | 19 |
| `totalCount` | `learntrack_topics.json` | 41 |
| `completionPercentage` | Calculated: (22/41)×100 | 53.7% |
| `firstNotStartedTopic` | First not-started topic in JSON | varies |

**States:**
| State ID | State name | Trigger |
|---|---|---|
| SCR-001-S1 | Default | Data loaded successfully |
| SCR-001-S2 | Loading | Data fetch in progress |
| SCR-001-S3 | Error | Data fetch failed |

**Figma frames to create:**
- `dashboard/light/default`
- `dashboard/light/loading`
- `dashboard/light/error`
- `dashboard/dark/default`
- `dashboard/dark/loading`
- `dashboard/dark/error`

**Acceptance Criteria:**
- Displays "AI & Python Technology Course" as the course name
- Shows circular progress indicator at 53.7% fill
- Shows "22 of 41 topics completed" label
- Shows stats: Completed=22, Not Started=19, Total=41
- "View All Topics" button navigates to SCR-003 (Topic List)
- Loading state shows skeleton shimmer (not blank screen, not spinner only)
- Error state shows icon + message + Retry button
- Dark mode variant is correct and legible

**Functional requirement mapping:**
| Requirement | Description |
|---|---|
| FR-003 | Display overall course completion percentage |
| FR-004 | Display completed and not-started topic counts |
| FR-009 | Show loading, error, and empty states |

**Jira issue mapping:**
| Jira issue | Purpose |
|---|---|
| LT-006 | Progress calculation and data integrity |
| LT-007 | Course dashboard and topic-list UI |

---

### Screen 2 — Progress Summary

| Field | Value |
|---|---|
| **Screen ID** | SCR-002 |
| **Name** | Progress Summary |
| **Figma page** | Page 6 — Progress Summary |
| **Figma frame prefix** | `progress-summary/` |
| **Route / destination** | Modal bottom sheet over SCR-001 |
| **Bottom nav tab** | N/A (modal overlay) |

**Purpose:**
A detailed breakdown of course progress, shown as a bottom sheet when the learner taps the hero card or stats section on the dashboard. Provides more visual granularity than the dashboard hero card alone.

**Entry Points:**
- Tap on ProgressHeroCard (SCR-001)
- Tap on StatsGrid row (SCR-001)
- Bottom navigation bar → Progress tab (SCR-004 equivalent, or same sheet)

**Components Used:**
| Component | Variant | Source |
|---|---|---|
| `LinearProgressBar` | default, loading, invalid-data | `learntrack-design-system.md §7.3` |
| `StatsGrid` (2×2) | default, loading, empty | `learntrack-design-system.md §7.4` |
| `WarningBanner` | — | `learntrack-design-system.md §7.11` |
| Material 3 ModalBottomSheet | — | Material 3 |
| Material 3 FilledButton | "View All Topics" | Material 3 |

**Data Requirements:**
| Field | Source | Value |
|---|---|---|
| `completedCount` | `learntrack_topics.json` | 22 |
| `notStartedCount` | `learntrack_topics.json` | 19 |
| `totalCount` | `learntrack_topics.json` | 41 |
| `completionPercentage` | Calculated | 53.7% |

**States:**
| State ID | State name | Trigger |
|---|---|---|
| SCR-002-S1 | Default | Data loaded successfully |
| SCR-002-S2 | Loading | Data fetch in progress |
| SCR-002-S3 | Empty | No topics returned from JSON |
| SCR-002-S4 | Invalid Data | Percentage outside 0–100 or counts inconsistent |

**Figma frames to create:**
- `progress-summary/light/default`
- `progress-summary/light/empty`
- `progress-summary/light/invalid-data`
- `progress-summary/dark/default`

**Acceptance Criteria:**
- Linear progress bar shows 53.7% fill
- Stats grid shows all four values (22, 19, 41, 53.7%)
- "View All Topics" button navigates to SCR-003
- Dismiss action (× button or swipe down) closes the sheet
- Warning banner is amber, dismissible, and shown only for invalid data
- Empty state shows a message and reload action

**Functional requirement mapping:**
| Requirement | Description |
|---|---|
| FR-003 | Display completion percentage with visual bar |
| FR-004 | Show all four stats (completed, not-started, total, percentage) |
| FR-009 | Show empty and invalid-data states |
| FR-010 | Warn when data integrity is questionable |

**Jira issue mapping:**
| Jira issue | Purpose |
|---|---|
| LT-006 | Progress calculation and data integrity |
| LT-007 | Course dashboard and topic-list UI |

---

### Screen 3 — Topic List

| Field | Value |
|---|---|
| **Screen ID** | SCR-003 |
| **Name** | Topic List |
| **Figma page** | Page 4 — Topic List |
| **Figma frame prefix** | `topic-list/` |
| **Route / destination** | `learntrack://topics` |
| **Bottom nav tab** | Topics (index 1) |

**Purpose:**
Shows all 41 topics in the AI & Python Technology Course with status indicators. Allows the learner to browse topics, filter by status, and navigate to individual topic detail screens.

**Entry Points:**
- Dashboard "View All Topics" button (SCR-001)
- Bottom navigation bar → Topics tab
- Progress Summary "View All Topics" button (SCR-002)

**Components Used:**
| Component | Variant | Source |
|---|---|---|
| `TopicRow` | completed, not_started, loading_skeleton | `learntrack-design-system.md §7.5` |
| `StatusBadge` | completed, not_started | `learntrack-design-system.md §7.6` |
| `FilterChip` | active, inactive | `learntrack-design-system.md §7.7` |
| `EmptyState` | — | `learntrack-design-system.md §7.9` |
| `ErrorState` | — | `learntrack-design-system.md §7.8` |
| `LoadingSkeleton` | topicRowSkeleton | `learntrack-design-system.md §7.10` |
| Material 3 TopAppBar | — | Material 3 |
| Material 3 NavigationBar | topics selected | Material 3 |
| Material 3 SearchBar (optional) | — | Material 3 |

**Data Requirements:**
| Field | Source | Value |
|---|---|---|
| `topics` | `learntrack_topics.json` | 41 Topic objects |
| Each `topic.id` | JSON | unique string |
| Each `topic.name` | JSON | non-empty string |
| Each `topic.status` | JSON | `completed` or `not_started` |
| `activeFilter` | UI state | ALL, COMPLETED, or NOT_STARTED |

**Filter Chip Counts (authoritative):**
| Filter | Count |
|---|---|
| All | 41 |
| Completed | 22 |
| Not Started | 19 |

**States:**
| State ID | State name | Trigger |
|---|---|---|
| SCR-003-S1 | Default (All filter) | Data loaded, no filter |
| SCR-003-S2 | Completed filter | "Completed (22)" chip selected |
| SCR-003-S3 | Not Started filter | "Not Started (19)" chip selected |
| SCR-003-S4 | Loading | Data fetch in progress |
| SCR-003-S5 | Error | Data fetch failed |
| SCR-003-S6 | Empty (search) | Search term matches no topics |
| SCR-003-S7 | Empty (filter) | Filter returns no results (edge case) |

**Figma frames to create:**
- `topic-list/light/default` (all 41 topics, All filter)
- `topic-list/light/completed-filter` (22 topics, Completed filter)
- `topic-list/light/not-started-filter` (19 topics, Not Started filter)
- `topic-list/light/loading` (5 skeleton rows)
- `topic-list/light/error`
- `topic-list/light/empty-search`
- `topic-list/dark/default`
- `topic-list/dark/loading`

**Acceptance Criteria:**
- Default state shows all 41 topic rows
- "Completed (22)" filter shows exactly 22 rows
- "Not Started (19)" filter shows exactly 19 rows
- Each row has the correct status icon (filled green vs outlined grey)
- Tapping a row navigates to SCR-004 (Topic Detail)
- Loading state shows skeleton rows, not a blank screen
- Error state has a Retry button
- Empty search state has a "Clear search" action

**Functional requirement mapping:**
| Requirement | Description |
|---|---|
| FR-001 | Load all 41 topics from learntrack_topics.json |
| FR-002 | Display per-topic status with visual indicator |
| FR-007 | Support filtering by status |
| FR-009 | Show loading, error, and empty states |

**Jira issue mapping:**
| Jira issue | Purpose |
|---|---|
| LT-005 | Topic JSON update and validation |
| LT-007 | Course dashboard and topic-list UI |

---

### Screen 4 — Topic Detail

| Field | Value |
|---|---|
| **Screen ID** | SCR-004 |
| **Name** | Topic Detail |
| **Figma page** | Page 5 — Topic Detail |
| **Figma frame prefix** | `topic-detail/` |
| **Route / destination** | `learntrack://topics/{topicId}` |
| **Bottom nav tab** | N/A (drilled from SCR-003) |

**Purpose:**
Shows the full details of a single topic: its name, status, ID, and any available metadata. Provides back navigation to the topic list and sequential navigation to adjacent topics.

**Entry Points:**
- Tap any TopicRow on SCR-003 (Topic List)
- "Continue Learning" card on SCR-001 (Dashboard) — navigates to first not-started topic

**Components Used:**
| Component | Variant | Source |
|---|---|---|
| `StatusBadge` | completed, not_started | `learntrack-design-system.md §7.6` |
| `ErrorState` | — | `learntrack-design-system.md §7.8` |
| `LoadingSkeleton` | — | `learntrack-design-system.md §7.10` |
| Material 3 TopAppBar | with back arrow | Material 3 |
| Material 3 Card | tonal | Material 3 |
| Material 3 OutlinedButton | disabled "Mark as Started" | Material 3 |

**Data Requirements:**
| Field | Source |
|---|---|
| `topic.id` | Route param + JSON |
| `topic.name` | `learntrack_topics.json` |
| `topic.status` | `learntrack_topics.json` (`completed` or `not_started`) |
| `previousTopicId` | Derived from JSON topic order |
| `nextTopicId` | Derived from JSON topic order |

**States:**
| State ID | State name | Trigger |
|---|---|---|
| SCR-004-S1 | Default (Completed) | Topic loaded, status = completed |
| SCR-004-S2 | Default (Not Started) | Topic loaded, status = not_started |
| SCR-004-S3 | Loading | Topic fetch in progress |
| SCR-004-S4 | Error | Topic fetch failed |
| SCR-004-S5 | Not Found | Topic ID not present in JSON |

**Figma frames to create:**
- `topic-detail/light/completed`
- `topic-detail/light/not-started`
- `topic-detail/light/loading`
- `topic-detail/light/error`
- `topic-detail/light/not-found`
- `topic-detail/dark/completed`
- `topic-detail/dark/not-started`

**Acceptance Criteria:**
- Topic name is displayed correctly
- StatusBadge shows the correct status (Completed = green, Not Started = grey)
- Back arrow returns to SCR-003 (Topic List)
- "Previous Topic" / "Next Topic" navigation works correctly at list boundaries
- "Mark as Started" button is visible but disabled (future feature)
- Loading state shows skeleton placeholder
- Error and not-found states show back navigation

**Functional requirement mapping:**
| Requirement | Description |
|---|---|
| FR-001 | Load individual topic data |
| FR-002 | Display topic status |
| FR-008 | Navigate between topics |
| FR-009 | Show loading, error, and not-found states |

**Jira issue mapping:**
| Jira issue | Purpose |
|---|---|
| LT-005 | Topic JSON data |
| LT-007 | Topic detail UI |

---

## Functional Requirement to Screen Mapping

| Req ID | Description | Screens |
|---|---|---|
| FR-001 | Load all topics from learntrack_topics.json | SCR-003, SCR-004 |
| FR-002 | Display per-topic status with visual indicator | SCR-003, SCR-004 |
| FR-003 | Display overall completion percentage (53.7%) | SCR-001, SCR-002 |
| FR-004 | Display completed (22) and not-started (19) counts | SCR-001, SCR-002 |
| FR-005 | Calculate progress from live data, not hard-coded | SCR-001, SCR-002 |
| FR-006 | Validate JSON schema on load | N/A (data layer) |
| FR-007 | Filter topic list by status | SCR-003 |
| FR-008 | Navigate between topics sequentially | SCR-004 |
| FR-009 | Show loading, error, empty states on all screens | SCR-001, SCR-002, SCR-003, SCR-004 |
| FR-010 | Warn on data integrity issues | SCR-002 |
| FR-011 | Support dark mode on all screens | SCR-001, SCR-002, SCR-003, SCR-004 |

---

## Jira Issue to Screen Mapping

| Jira issue | Description | Primary screens |
|---|---|---|
| LT-001 | Repository discovery | N/A |
| LT-002 | Requirements and acceptance criteria | N/A |
| LT-003 | Google Stitch design preparation | Informs all screens |
| LT-004 | Figma design system and handoff | Informs all screens |
| LT-005 | Topic JSON update and validation | SCR-003, SCR-004 (data source) |
| LT-006 | Progress calculation and data integrity | SCR-001, SCR-002 |
| LT-007 | Course dashboard and topic-list UI | SCR-001, SCR-002, SCR-003, SCR-004 |
| LT-008 | Automated tests and regression coverage | All screens (test coverage) |
| LT-009 | Kiro skills and implementation documentation | N/A |
| LT-010 | Build variants and quality gates | N/A |
| LT-011 | Release-readiness verification | All screens |

---

## Notes

- **SCR-002 (Progress Summary)** is implemented as a modal bottom sheet, not a standalone screen. It shares the same route/destination context as SCR-001. This is a design decision, not an architectural constraint — if a full screen is preferred, update this document and the handoff.
- No Figma file IDs, export URLs, or auto-generated asset references are included in this document. All such references must be added manually after the Figma file is created. Do not fabricate file IDs.
- Topic names for all 41 topics are pending definition. The screens are designed to accommodate topic names up to approximately 60 characters (two lines in list view, three lines in detail view).
- The "Continue Learning" section on SCR-001 shows the first not-started topic. The ordering logic (which topic is "first" in the not-started set) must be defined during implementation.
