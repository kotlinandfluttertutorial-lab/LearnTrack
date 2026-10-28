# Course Progress Update — Design

**Spec:** course-progress-update
**Phase:** Phase Group 2 — Architecture + Kiro Specs
**Status:** Draft — pre-scaffold; describes target implementation design.
**Jira Epic:** LEARNTRACK-EPIC-COURSE-PROGRESS (CSV only; not a live Jira key)

---

## 1. Architecture Overview

LearnTrack is an Android application using Kotlin, Jetpack Compose, and MVVM + Repository pattern. The architecture is derived from the org's established pattern (sibling MediHistory-AI project) adapted to LearnTrack's simpler, read-only data model.

### Layer Summary

| Layer | Technology | Key Classes |
|---|---|---|
| UI | Jetpack Compose + Material 3 | `CourseProgressScreen`, `TopicListScreen`, `TopicDetailScreen` |
| ViewModel | Kotlin Coroutines + StateFlow | `CourseProgressViewModel` |
| Use Case | Pure Kotlin | `GetCourseProgressUseCase`, `CalculateProgressUseCase` |
| Repository | Kotlin Coroutines + Flow | `CourseRepository` (interface), `CourseRepositoryImpl` |
| Data Source | Android AssetManager | `learntrack_topics.json` |
| DI | Hilt | `AppModule`, `DataModule` |
| Navigation | Jetpack Navigation Compose | `NavHost`, `NavController` |

Full layer diagram: `docs/architecture/learntrack-architecture.md`

---

## 2. Data Flow for the Progress Update

The complete data flow from JSON asset to rendered UI:

```
learntrack_topics.json
      │  AssetManager.open()
      ▼
CourseRepositoryImpl.getTopics()
      │  parse + validate
      ▼
List<Topic>
      │  GetCourseProgressUseCase.invoke()
      ├──────────────────────────────────────────► full topic list (for TopicListScreen)
      ▼
CalculateProgressUseCase.calculate()
      │  completed / total × 100
      ▼
CourseProgress(total=41, completed=22, notStarted=19, completionPercentage=53.658...)
      │
      ▼
CourseProgressViewModel emits CourseUiState.Success
      │  StateFlow<CourseUiState>
      ▼
CourseProgressScreen collects state
      │
      ├──► Progress bar at 53.7%
      ├──► Text "22 of 41 topics"
      └──► Text "53.7%"
```

Full sequence diagram: `docs/architecture/learntrack-data-flow.md`

---

## 3. learntrack_topics.json — Schema and Update Plan

### Schema

```json
{
  "topics": [
    {
      "id": "topic-001",
      "name": "Introduction to AI",
      "status": "completed"
    }
  ]
}
```

| Field | Type | Constraints |
|---|---|---|
| `topics` | Array | Required; length == 41 |
| `topics[].id` | String | Required; non-empty; unique |
| `topics[].name` | String | Required; non-empty |
| `topics[].status` | String | Required; `"completed"` or `"not_started"` only |

### File Location

`app/src/main/assets/learntrack_topics.json`

This is the standard Android location for bundled static assets. The file is accessible via `context.assets.open("learntrack_topics.json")`.

### Update Plan

The file does not exist yet. It must be created during implementation task T-005. Steps:
1. Define 41 topic names for the AI & Python Technology Course (requires user confirmation of topic list).
2. Assign statuses: first 22 topics as `"completed"`, remaining 19 as `"not_started"` (or per an approved ordering).
3. Generate the JSON with sequential IDs (`topic-001` through `topic-041`).
4. Run validation: parse, schema check, count check.
5. Commit the file.

After creation, topic ids and names must not be changed without an explicit decision (FR-015).

---

## 4. Domain Model

```kotlin
// data/model/Topic.kt
@Serializable
data class Topic(
    val id: String,
    val name: String,
    @SerialName("status") val status: TopicStatus
)

// data/model/TopicStatus.kt
@Serializable
enum class TopicStatus {
    @SerialName("completed") COMPLETED,
    @SerialName("not_started") NOT_STARTED
}

// domain/model/CourseProgress.kt
data class CourseProgress(
    val total: Int,
    val completed: Int,
    val notStarted: Int,
    val completionPercentage: Float
)
```

---

## 5. Repository Layer Design

```kotlin
// data/repository/CourseRepository.kt
interface CourseRepository {
    fun getTopics(): Flow<Result<List<Topic>>>
}

// data/repository/CourseRepositoryImpl.kt
class CourseRepositoryImpl @Inject constructor(
    private val context: Context
) : CourseRepository {

    override fun getTopics(): Flow<Result<List<Topic>>> = flow {
        try {
            val inputStream = context.assets.open("learntrack_topics.json")
            val json = inputStream.bufferedReader().readText()
            val topicContainer = Json.decodeFromString<TopicContainer>(json)
            val validated = TopicSchemaValidator.validate(topicContainer.topics)
            emit(Result.success(validated))
        } catch (e: IOException) {
            Log.e("LearnTrack", "Failed to open asset", e)
            emit(Result.failure(e))
        } catch (e: SerializationException) {
            Log.e("LearnTrack", "Failed to parse JSON", e)
            emit(Result.failure(e))
        } catch (e: ValidationException) {
            Log.e("LearnTrack", "JSON schema validation failed", e)
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)
}
```

---

## 6. ViewModel Design

```kotlin
// ui/progress/CourseProgressViewModel.kt
@HiltViewModel
class CourseProgressViewModel @Inject constructor(
    private val getCourseProgressUseCase: GetCourseProgressUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<CourseUiState>(CourseUiState.Loading)
    val uiState: StateFlow<CourseUiState> = _uiState.asStateFlow()

    init {
        loadCourseProgress()
    }

    private fun loadCourseProgress() {
        viewModelScope.launch {
            getCourseProgressUseCase().collect { result ->
                _uiState.value = result.fold(
                    onSuccess = { (progress, topics) ->
                        if (topics.isEmpty()) CourseUiState.Empty
                        else CourseUiState.Success(progress, topics)
                    },
                    onFailure = { e ->
                        CourseUiState.Error(e.toUserMessage())
                    }
                )
            }
        }
    }
}

// ui/progress/CourseUiState.kt
sealed class CourseUiState {
    object Loading : CourseUiState()
    object Empty : CourseUiState()
    data class Success(
        val progress: CourseProgress,
        val topics: List<Topic>
    ) : CourseUiState()
    data class Error(val message: String) : CourseUiState()
}
```

---

## 7. UI Changes Required

### CourseProgressScreen (Dashboard)

New screen — does not exist yet.

Required elements:
- `TopAppBar` with "LearnTrack" or course name
- `LinearProgressIndicator` at `progress = completionPercentage / 100f`
- Text: completion percentage e.g. "53.7%"
- Text: "22 of 41 topics completed"
- Button / navigation link to topic list
- `Loading` state: `CircularProgressIndicator` centered
- `Error` state: error message text + retry button (optional)
- `Empty` state: informational message

### TopicListScreen

New screen — does not exist yet.

Required elements:
- `TopAppBar` with course name and back navigation
- `LazyColumn` of 41 topic rows
- Each row: topic name + `StatusBadge` composable
- `StatusBadge`: "Completed" (green/tonal) or "Not Started" (outline/neutral) — Material 3 `AssistChip` or `SuggestionChip`
- `Empty` state: centered message

### TopicDetailScreen

New screen — does not exist yet.

Required elements:
- `TopAppBar` with back navigation
- Topic name as headline text
- Topic status as a `StatusBadge`

### Navigation

Jetpack Navigation Compose with a `NavHost`:

```
CourseProgress (dashboard)
    └── TopicList
            └── TopicDetail/{topicId}
```

---

## 8. UI State Management

- Each screen has a single `StateFlow<UiState>` from its ViewModel.
- Composables are stateless: they receive state and emit events.
- `collectAsStateWithLifecycle()` used for lifecycle-aware collection.
- State is hoisted to ViewModel; no local `remember { mutableStateOf }` for server data.
- All screens share a single `CourseProgressViewModel` (or scoped per navigation graph).

---

## 9. Error Handling Strategy

| Error source | Exception | Repository action | ViewModel action | UI action |
|---|---|---|---|---|
| Missing asset file | `IOException` | `Result.failure(e)` | Emit `Error` | Show error message |
| Invalid JSON syntax | `SerializationException` | `Result.failure(e)` | Emit `Error` | Show error message |
| Schema violation | `ValidationException` | `Result.failure(e)` | Emit `Error` | Show error message |
| Empty topic list | — | `Result.success(emptyList())` | Emit `Empty` | Show empty state |
| Unknown status value | Caught during validation | `Result.failure(e)` | Emit `Error` | Show error message |

User-facing messages are generic and safe. Exception details are logged via `Log.e("LearnTrack", ...)` in debug builds only.

---

## 10. Testing Strategy

### Unit Tests (app/src/test/)

| Test class | What is tested |
|---|---|
| `ProgressCalculatorTest` | `CalculateProgressUseCase` — all edge cases |
| `CourseRepositoryImplTest` | Valid JSON, missing file, malformed JSON |
| `CourseProgressViewModelTest` | Loading → Success / Loading → Error state transitions |
| `TopicSchemaValidatorTest` | Missing fields, invalid status, duplicate ids |
| `TopicDistributionRegressionTest` | Asserts 22/19/41 from actual asset file |

### Instrumented / UI Tests (app/src/androidTest/)

| Test class | What is tested |
|---|---|
| `CourseProgressScreenTest` | Progress %, counts, progress bar, loading state, error state |
| `TopicListScreenTest` | 41 rows, status badge text, scrollability |
| `TopicDetailScreenTest` | Topic name, status, back navigation |

### Test Conventions

- Use MockK for mocking in unit tests.
- Use Hilt testing support for ViewModel and repository tests.
- Use `composeTestRule.setContent { }` for Compose UI tests.
- Use `AndroidJUnit4` runner for instrumented tests.
- Test JSON assets stored in `app/src/test/resources/` or `app/src/androidTest/assets/`.

---

## 11. References

- Google Stitch master prompt: `docs/design/google-stitch/learntrack-stitch-master-prompt.md`
- Google Stitch screen prompt: `docs/design/google-stitch/course-progress-prompt.md`
- Figma handoff: `docs/design/figma/learntrack-figma-handoff.md`
- Figma design system: `docs/design/figma/learntrack-design-system.md`
- Architecture doc: `docs/architecture/learntrack-architecture.md`
- Data flow doc: `docs/architecture/learntrack-data-flow.md`
- Environments doc: `docs/architecture/learntrack-environments.md`
- Jira stories CSV: `docs/jira/learntrack-stories.csv`
- Kiro requirements spec: `.kiro/specs/course-progress-update/requirements.md`
- Kiro tasks spec: `.kiro/specs/course-progress-update/tasks.md`
