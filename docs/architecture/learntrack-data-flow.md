# LearnTrack — Data Flow

**Phase:** Architecture and Data Design (Phase Group 2)
**Status:** Baseline — pre-scaffold; describes target state derived from platform determination.
**Last Updated:** Phase Group 2

---

## 1. End-to-End Data Flow Overview

```
learntrack_topics.json (asset)
        │
        │  AssetManager.open()
        ▼
CourseRepositoryImpl
        │  parse + validate
        ▼
List<Topic>  ─────────────────────────────────────────────────┐
        │                                                      │
        │  GetCourseProgressUseCase                            │
        ▼                                                      ▼
CalculateProgressUseCase                               Full topic list
        │                                                      │
        ▼                                                      │
CourseProgress(total, completed, notStarted, %)               │
        │                                                      │
        └─────────────────────┬────────────────────────────────┘
                              │  CourseProgressViewModel
                              ▼
                    CourseUiState (sealed)
                              │
                ┌─────────────┼─────────────┐
                ▼             ▼             ▼
           Loading         Success        Error / Empty
                              │
              ┌───────────────┼───────────────┐
              ▼               ▼               ▼
  CourseProgressScreen  TopicListScreen  TopicDetailScreen
```

---

## 2. Sequence Diagram — App Startup to Dashboard Render

```mermaid
sequenceDiagram
    participant App as Application
    participant VM as CourseProgressViewModel
    participant UC as GetCourseProgressUseCase
    participant Repo as CourseRepositoryImpl
    participant Asset as learntrack_topics.json

    App->>VM: onCreate / hiltViewModel()
    VM->>VM: emit Loading state
    VM->>UC: invoke()
    UC->>Repo: getTopics()
    Repo->>Asset: AssetManager.open("learntrack_topics.json")

    alt File found
        Asset-->>Repo: InputStream
        Repo->>Repo: parse JSON → List<TopicDto>
        Repo->>Repo: validate schema and counts
        alt Validation passes
            Repo-->>UC: Result.Success(List<Topic>)
            UC->>UC: CalculateProgressUseCase(topics)
            UC-->>VM: CourseProgress + List<Topic>
            VM->>VM: emit Success(CourseUiState)
            VM-->>App: StateFlow update
            App->>App: render CourseProgressScreen (Success)
        else Validation fails
            Repo-->>UC: Result.Failure(ValidationException)
            UC-->>VM: propagate error
            VM->>VM: emit Error(message)
            VM-->>App: StateFlow update
            App->>App: render ErrorState
        end
    else File not found
        Asset-->>Repo: IOException
        Repo-->>UC: Result.Failure(IOException)
        UC-->>VM: propagate error
        VM->>VM: emit Error("Course data could not be loaded.")
        VM-->>App: StateFlow update
        App->>App: render ErrorState
    end
```

---

## 3. Flowchart — Full Data Pipeline

```mermaid
flowchart TD
    A([App Start]) --> B[CourseProgressViewModel init]
    B --> C[Emit Loading state]
    C --> D[GetCourseProgressUseCase.invoke]
    D --> E[CourseRepositoryImpl.getTopics]
    E --> F{learntrack_topics.json\nexists in assets?}

    F -- No --> G[Catch IOException]
    G --> H[emit Error: file not found]
    H --> I([Show Error Screen])

    F -- Yes --> J[Parse JSON]
    J --> K{JSON syntax\nvalid?}

    K -- No --> L[Catch ParseException]
    L --> M[emit Error: malformed JSON]
    M --> I

    K -- Yes --> N[Validate schema\neach topic has id, name, status]
    N --> O{Schema\nvalid?}

    O -- No --> P[emit Error: invalid schema]
    P --> I

    O -- Yes --> Q[Validate counts\n41 total, 22 completed, 19 not_started]
    Q --> R{Counts\nmatch?}

    R -- No --> S[emit Warning / Error: count mismatch]
    S --> I

    R -- Yes --> T[Return List<Topic>]
    T --> U[CalculateProgressUseCase]
    U --> V[completed / total × 100]
    V --> W[CourseProgress: 22/41 = 53.7%]
    W --> X[emit Success CourseUiState]
    X --> Y([Render Dashboard\n53.7% — 22/41 topics])
    Y --> Z{User taps\nTopic List?}
    Z -- Yes --> AA([Render TopicListScreen\n41 topics with status badges])
    AA --> AB{User taps\na topic row?}
    AB -- Yes --> AC([Render TopicDetailScreen\nname + status])
    AC --> AD{Back pressed?}
    AD -- Yes --> AA
```

---

## 4. State Transitions for Topic Status

Topic status is static in the current phase (read-only JSON). The state machine below captures the intended lifecycle if mutability is added in a future phase.

```mermaid
stateDiagram-v2
    [*] --> not_started : Initial value in JSON
    not_started --> completed : User marks topic done (future)
    completed --> not_started : User resets topic (future)
    not_started --> [*] : App reads status
    completed --> [*] : App reads status

    note right of not_started
        Current phase: read-only.
        Status is set in learntrack_topics.json
        at development time only.
    end note
```

**Current phase constraint:** Status transitions are not possible at runtime. The only valid state transitions are:
- Development-time edit of `learntrack_topics.json`
- Followed by validation, commit, and new build

---

## 5. Progress Calculation Algorithm

```
Input:  topics: List<Topic>
Output: CourseProgress

Algorithm:
  1. total     ← topics.size
  2. completed ← count where topic.status == COMPLETED
  3. notStarted← count where topic.status == NOT_STARTED
  4. IF total > 0:
       percentage ← (completed / total) × 100.0
     ELSE:
       percentage ← 0.0
  5. Return CourseProgress(total, completed, notStarted, percentage)

Display: "%.1f%%".format(percentage)
  → 53.658...% → "53.7%"
```

**Authoritative test vectors:**

| Input | completed | total | Expected output |
|---|---|---|---|
| Authoritative JSON | 22 | 41 | 53.7% |
| All completed | 41 | 41 | 100.0% |
| None completed | 0 | 41 | 0.0% |
| Empty list | 0 | 0 | 0.0% |
| One of one | 1 | 1 | 100.0% |
| One of two | 1 | 2 | 50.0% |

---

## 6. Error Propagation Paths

```mermaid
flowchart LR
    subgraph DataLayer["Data Layer"]
        R[CourseRepositoryImpl]
    end

    subgraph DomainLayer["Domain Layer"]
        UC[GetCourseProgressUseCase]
    end

    subgraph VMLayer["ViewModel Layer"]
        VM[CourseProgressViewModel]
    end

    subgraph UILayer["UI Layer"]
        UI[CourseProgressScreen]
    end

    R -- "Result.Failure(IOException)" --> UC
    R -- "Result.Failure(ParseException)" --> UC
    R -- "Result.Failure(ValidationException)" --> UC
    UC -- "propagate as sealed Error" --> VM
    VM -- "emit Error(message)" --> UI
    UI -- "render ErrorMessage composable" --> UI
```

All exceptions are caught at the repository boundary. The domain and ViewModel layers propagate errors using `Result<T>` or a sealed error type — never by re-throwing.

The UI renders a dedicated error state composable with a user-friendly message. Technical exception details are logged at `Log.e` level only, never shown in the UI.

---

## 7. Data Validation Checkpoints

| Checkpoint | Location | What is checked | Failure action |
|---|---|---|---|
| CP-1: File exists | `CourseRepositoryImpl` | `assets/learntrack_topics.json` is accessible | Emit `Error("file not found")` |
| CP-2: JSON syntax | `CourseRepositoryImpl` | File content parses without exception | Emit `Error("malformed JSON")` |
| CP-3: Schema — id | `TopicSchemaValidator` | Each topic has a non-empty `id` | Emit `Error("invalid schema")` |
| CP-4: Schema — name | `TopicSchemaValidator` | Each topic has a non-empty `name` | Emit `Error("invalid schema")` |
| CP-5: Schema — status | `TopicSchemaValidator` | Each status is `completed` or `not_started` | Emit `Error("invalid schema")` |
| CP-6: Unique ids | `TopicSchemaValidator` | No duplicate `id` values | Emit `Error("duplicate topic ids")` |
| CP-7: Count — total | `TopicSchemaValidator` | List contains exactly 41 topics | Emit `Error("unexpected topic count")` |
| CP-8: Count — completed | `TopicSchemaValidator` | Exactly 22 topics are `completed` | Emit `Error("unexpected status distribution")` |
| CP-9: Count — not_started | `TopicSchemaValidator` | Exactly 19 topics are `not_started` | Emit `Error("unexpected status distribution")` |
| CP-10: Progress calc | Unit test only | `22/41 = 53.66...%` rounds to 53.7% | Test fails; build blocked |

Checkpoints CP-7 through CP-9 can be relaxed to warnings in a future phase if the topic list becomes user-managed.

---

## 8. JSON Schema Reference

```json
{
  "topics": [
    {
      "id": "topic-001",
      "name": "Introduction to AI",
      "status": "completed"
    },
    {
      "id": "topic-002",
      "name": "Python Basics",
      "status": "not_started"
    }
  ]
}
```

| Field | Type | Required | Constraints |
|---|---|---|---|
| `topics` | Array | Yes | Length == 41 |
| `topics[].id` | String | Yes | Non-empty, unique across array |
| `topics[].name` | String | Yes | Non-empty |
| `topics[].status` | String | Yes | `"completed"` or `"not_started"` |

---

## References

- Architecture doc: `docs/architecture/learntrack-architecture.md`
- Environments doc: `docs/architecture/learntrack-environments.md`
- Scope doc: `docs/scope/learntrack-scope.md`
- Discovery report: `docs/discovery/learntrack-discovery-report.md`
- Kiro spec (design): `.kiro/specs/course-progress-update/design.md`
- Kiro spec (requirements): `.kiro/specs/course-progress-update/requirements.md`
