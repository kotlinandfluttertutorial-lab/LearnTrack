# LearnTrack — Extended AI-SDLC Master Prompt
## Google Stitch + Figma + Jira + Kiro + Skills + Quality Gates
## v2 — Full-Stack: FastAPI · Streamlit · Android · Python Analytics

---

## Version History

| Version | Date | Change |
|---|---|---|
| v1.0 | October 2026 | Android-only: Kotlin, Jetpack Compose, JSON asset, Hilt, Coroutines |
| v2.0 | October 2026 | Full-stack: added FastAPI, Pydantic, SQLAlchemy, Alembic, SQLite→PostgreSQL, Streamlit, NumPy, Pandas, Matplotlib, Pytest, Docker, Retrofit, Room, DataStore, WorkManager |

---

## Architecture Overview (v2)

```
┌─────────────────────┐     ┌─────────────────────┐
│   Streamlit Web App  │     │  Android App         │
│   (Python)          │     │  Kotlin + Compose    │
└────────┬────────────┘     └──────────┬───────────┘
         │  HTTP                       │  Retrofit/OkHttp
         ▼                             ▼
┌─────────────────────────────────────────────────┐
│              FastAPI REST API                    │
│   Pydantic validation · Business logic           │
│   Course / topic / progress / analytics endpoints│
└──────────────────────┬──────────────────────────┘
                       │
         ┌─────────────┼──────────────┐
         ▼             ▼              ▼
  SQLAlchemy      Alembic        Analytics
  ORM layer       Migrations     NumPy · Pandas
         │                       Matplotlib
         ▼
  SQLite (dev) → PostgreSQL (prod)
  Shared persistent learning data
```

**Implementation phases (in order):**
1. Foundation — Python project, SQLite, SQLAlchemy models, seed 41 topics
2. Backend — FastAPI endpoints, Pydantic schemas, progress rules, Pytest
3. Web — Streamlit dashboard, topic management, charts, reports
4. Android — Kotlin+Compose connected to FastAPI via Retrofit; Room cache
5. Learning features — Study sessions, exercises, assessments, revision, exports
6. Production — Auth, PostgreSQL, Docker, logging, optional offline sync

### 11. End-to-End AI-SDLC Workflow

Extend the existing LearnTrack implementation with a complete AI-SDLC workflow.

**v1 mandatory workflow (Android-only — completed October 2026):**
Requirements → Repository Discovery → Google Stitch → Figma → Architecture & Data Design → Jira → Kiro Specs → Kiro Skills → Implementation → Testing → Quality Gates → Release Readiness.

**v2 mandatory workflow (full-stack extension):**
The same 12-phase workflow applies to each new implementation phase below. Run the full workflow loop once per phase — do not batch phases into a single loop.

| Phase | Stack added | AI-SDLC loop |
|---|---|---|
| Phase 1 — Foundation | Python project, SQLite, SQLAlchemy models, seed data | Full loop |
| Phase 2 — Backend | FastAPI, Pydantic, Pytest, progress rules | Full loop |
| Phase 3 — Web | Streamlit, Pandas, Matplotlib, NumPy analytics | Full loop |
| Phase 4 — Android v2 | Retrofit, Room, DataStore, WorkManager | Full loop |
| Phase 5 — Learning features | Study sessions, assessments, revision, exports | Full loop |
| Phase 6 — Production | Auth, PostgreSQL, Docker, logging | Full loop |

For every phase:

- Define inputs, outputs, and acceptance criteria.
- Preserve the existing LearnTrack architecture and functionality.
- Generate reusable artifacts instead of relying on chat-only instructions.
- Maintain traceability between requirements, designs, Jira issues, implementation, and tests.
- Do not mark a phase complete until its deliverables have been verified.
- Do not claim an external tool was used unless the action was actually performed.
- If an external integration is unavailable, generate copy-ready prompts and document the required manual steps.

### 12. Scope Management

Create a scope document before implementation.

**v1 scope (Android-only — completed):**

- ✅ `learntrack_topics.json` with 41 topics (22 completed, 19 not-started)
- ✅ Android app: dashboard, topic list, topic detail, progress calculation
- ✅ JSON schema validation, unit tests, regression test, CI/CD

**v2 in scope (full-stack extension):**

| Phase | In scope |
|---|---|
| Phase 1 — Foundation | Python project structure (`src/`, `tests/`, `alembic/`). SQLite database. SQLAlchemy models: `Course`, `Topic`, `TopicStatus`. Alembic initial migration. Seed script importing 41 topics with IDs and titles preserved. |
| Phase 2 — Backend | FastAPI application. Pydantic schemas for all entities. Endpoints: courses, topics, topic-status updates, progress calculation, learning notes, exercises, study sessions, assessments, revision tasks, analytics. Pytest unit + integration tests. Progress calculated from topic records — never stored as a manual field. `needs_revision` status does not count as completed until re-completed. |
| Phase 3 — Web | Streamlit multi-page web app. Pages: dashboard, course management, topic management, learning content, search + filter, analytics charts (NumPy, Pandas, Matplotlib), report export. |
| Phase 4 — Android v2 | Retrofit + OkHttp HTTP client connecting to FastAPI. Room database for local caching and offline access. DataStore for preferences (theme, display, learning settings). WorkManager for eligible background tasks (revision reminders, optional sync). |
| Phase 5 — Learning features | Study session tracking. Exercise management. Interview question bank. Assessment results. Revision scheduling. Report exports (CSV, PDF). |
| Phase 6 — Production | Authentication and authorization. PostgreSQL (replace SQLite). Docker: FastAPI container + Streamlit container + docker-compose. Logging and monitoring. Backup strategy. Optional offline synchronisation for Android. |

**Out of scope unless explicitly approved:**

- Unrelated course platforms or third-party LMS integrations
- Real-time collaboration or multi-user editing
- Video or media hosting
- Payment, subscription, or billing
- Native iOS application
- Kubernetes or cloud-native infrastructure (Phase 6 uses Docker only)
- Destructive changes to existing topic IDs or names

Document any additional scope discovered during repository analysis. Obtain approval before introducing significant new functionality.

### 12a. Pre-Phase Access Verification (NEW — added after LearnTrack retrospective)

Before starting the Figma phase, the Stitch phase, or the Jira phase, perform these checks:

**Figma access:**
1. Call `figma::whoami` to confirm the authenticated account and seat type.
2. Confirm the seat is **Full** (not View-only). View-only seats cannot use MCP write tools.
3. If no Figma file exists yet, call `figma::create_new_file` to create it — do not fabricate a file ID.
4. Record the real file key in all Figma docs immediately.

**Jira access:**
1. Call `atlassian::getAccessibleAtlassianResources` to get the real `cloudId`.
2. Search for the project (`project = <KEY> ORDER BY created`) to confirm the project key exists.
3. Use the actual project key (e.g., `LEAR`) in all CSVs and issue creation — do not use placeholder keys like `LT-001` as if they were real Jira keys.
4. Create issues via MCP when access is available. Clearly state "CSV only" when issues are not created live.

**Google Stitch access:**
1. Stitch does not have a verified MCP integration. Always treat Stitch as requiring manual execution.
2. Write copy-ready prompts. Do not claim a Stitch project was run unless you have the project URL and export files on disk.

**Topic names:**
Before scaffolding the Android project, confirm the exact names for all topics with the user. Topic names are a **required blocker** for creating `learntrack_topics.json`. Document confirmed names in the scope doc and tasks spec before T-005 begins.

---

### 13. Google Stitch — UI Generation

Create Google Stitch prompts based on the verified LearnTrack requirements and existing application platform.

First inspect whether LearnTrack is an Android app, web application, or another supported interface. Generate designs appropriate to that platform instead of assuming a mobile or web implementation.

Create:

`docs/design/google-stitch/learntrack-stitch-master-prompt.md`

`docs/design/google-stitch/course-progress-prompt.md`

`docs/design/google-stitch/design-review-checklist.md`

The prompts must specify:

- Product name: LearnTrack.
- Course name: AI & Python Technology Course.
- Completed topics: 22.
- Not-started topics: 19.
- Total topics: 41.
- Completion: 53.7%.
- Clear progress visualization.
- Topic-list status indicators.
- Consistent typography, spacing, colors, and component behavior.
- Loading, empty, error, and invalid-data states where applicable.
- Accessibility, readable contrast, and responsive behavior.
- Search, filtering, and topic-detail behavior only where consistent with existing functionality.

Design the course dashboard, progress summary, topic list, and relevant topic-detail states.

Do not fabricate lesson content or change the status rules to make a design look better.

**Deliverable:** Stitch prompts, generated-design references or exports when available, and a review checklist.

If Stitch access is unavailable, prepare the prompts for manual execution and document the handoff.

**LearnTrack Stitch project:** ID `683813527608164394` — recorded in `docs/design/google-stitch/learntrack-stitch-master-prompt.md`. HTML code exports are present in `docs/design/google-stitch/exports/`. PNG image files require re-export from the live Stitch project (PNG fetch failed during automated export).
- Course Dashboard (light + dark mode)
- Progress Summary (separate from dashboard — this is often missed)
- Topic List (light + dark mode)
- Topic Detail (completed variant + not-started variant)
- All edge states: loading skeleton, error, empty, invalid data

**Lesson learned (LearnTrack):** Progress Summary was not exported as a separate screen — only embedded as part of the dark-mode dashboard scroll. Always require it as an independent export.

### 14. Figma — Design System and Handoff

Use the approved Stitch concepts to prepare a Figma handoff.

Create:

`docs/design/figma/learntrack-figma-handoff.md`

`docs/design/figma/learntrack-design-system.md`

`docs/design/figma/learntrack-screen-inventory.md`

Define:

- Design tokens for colors, typography, spacing, borders, and component states.
- Reusable progress cards, progress bars, topic rows, status badges, and relevant navigation components.
- Completed, not-started, loading, empty, error, and accessibility states.
- Responsive layouts appropriate to the existing application platform.
- Component naming and variant conventions.
- Screen specifications and interaction notes.
- Design-to-implementation mapping.
- Design acceptance criteria.

Use Figma MCP only when the required server is available, correctly configured, and authorized.

If Figma MCP is unavailable, provide the exact Figma setup instructions and copy-ready design specifications. Do not invent Figma file IDs, links, exported assets, or successful MCP operations.

**Acceptance criteria**

- Every approved screen maps to one or more functional requirements.
- UI components use a consistent design system.
- The 53.7% progress state matches the authoritative course data.
- The implementation does not introduce visual or behavioral regressions.
- Dark mode variants exist for **all** primary screens — not only the dashboard.
- The Figma Design System file is created as a **separate file** from the UI Designs file. Color tokens, typography, and component specs live there.
- All Figma node IDs used in MCP calls are recorded in `learntrack-figma-handoff.md` so implementation agents can call `figma::get_design_context` without re-discovering them.

**Lesson learned (LearnTrack):** Dark mode variants were only created for the dashboard. Require dark mode for all screens up front. Also, record all node IDs in the handoff doc immediately after MCP inspection — saves re-discovery time in the implementation phase.

### 15. Architecture and Data Design

Create or update:

`docs/architecture/learntrack-architecture.md`

`docs/architecture/learntrack-data-flow.md`

`docs/architecture/learntrack-environments.md`

**v2 additions (create these new docs):**

`docs/architecture/learntrack-backend-architecture.md`

`docs/architecture/learntrack-database-schema.md`

`docs/architecture/learntrack-api-design.md`

Inspect the existing architecture before making recommendations.

#### v1 architecture (preserve — do not regress)
- Android app reads `learntrack_topics.json` from assets
- MVVM + Repository + Hilt + Coroutines + Flow
- No network calls; no backend

#### v2 architecture additions

**Backend layer (Phase 2):**
- FastAPI application structured as: `api/` (routes), `core/` (config, security), `models/` (SQLAlchemy), `schemas/` (Pydantic), `services/` (business logic), `tests/`
- All topic-status transitions go through the service layer — the API never writes raw status strings directly
- Progress is always calculated: `completed_count / total_count * 100` — never stored as a column
- `needs_revision` topics are excluded from the completed count until their status returns to `completed`
- Pydantic validates all inbound data before it reaches the service layer

**Database layer (Phase 1 → Phase 6):**
- Phase 1–5: SQLite (`learntrack.db`), managed via SQLAlchemy + Alembic
- Phase 6: PostgreSQL, same SQLAlchemy models, zero application-code changes
- Alembic migration per schema change — never `drop_all` / `create_all` in production
- Topic `id` and `name` are immutable after seeding — same data-preservation rule as v1

**Key SQLAlchemy models (document in `learntrack-database-schema.md`):**
- `Course` — id, title, description, created_at
- `Topic` — id, course_id, title, order, status (enum), created_at, updated_at
- `TopicStatus` enum — `not_started`, `in_progress`, `completed`, `needs_revision`
- `StudySession` — id, topic_id, started_at, ended_at, duration_minutes (Phase 5)
- `Assessment` — id, topic_id, score, max_score, taken_at (Phase 5)
- `RevisionTask` — id, topic_id, due_date, completed_at (Phase 5)

**API design (document in `learntrack-api-design.md`):**
- Base URL: `/api/v1/`
- Key endpoints:
  - `GET /courses` — list all courses with progress summary
  - `GET /courses/{id}/topics` — list all topics with status
  - `PATCH /topics/{id}/status` — update topic status (validates enum)
  - `GET /courses/{id}/progress` — calculated completion percentage
  - `GET /analytics/summary` — NumPy/Pandas-computed stats
  - `GET /analytics/charts/{type}` — Matplotlib chart data or base64 image
- All responses use Pydantic output schemas
- Errors follow RFC 7807 (application/problem+json)

**Analytics layer (Phase 3):**
- NumPy: completion rates, average assessment scores, study-time statistics
- Pandas: topic-status grouping, module-level progress, report DataFrames
- Matplotlib: bar charts (topics by status), line charts (study time trends), pie charts (completion breakdown)
- Analytics run server-side in FastAPI endpoints; charts rendered in Streamlit

**Android v2 additions (Phase 4):**
- Retrofit + OkHttp replaces direct JSON asset reading for live data
- Room provides local cache — `TopicEntity`, `CourseEntity` with offline support
- DataStore (Proto or Preferences) stores: theme, display density, notification preferences
- WorkManager schedules revision reminders when `RevisionTask.due_date` approaches
- Sync strategy: Retrofit fetches → Room caches → UI reads from Room (offline-first)

**Environments:**
- Local dev: SQLite + `uvicorn --reload` + `streamlit run` + Android emulator pointing to `http://10.0.2.2:8000`
- Docker dev: `docker-compose up` runs FastAPI + Streamlit containers; Android points to `http://localhost:8000`
- Production: PostgreSQL + Docker + reverse proxy (nginx); Android points to production URL

Use the current framework, language, state-management approach, and persistence technology unless an approved requirement justifies a change.

**Do not introduce PostgreSQL, Docker, or authentication before Phase 6.** SQLite is the correct database for Phases 1–5.

### 16. Jira — Epics, Stories, Subtasks, and CSV

Create Jira-ready work items covering the complete feature lifecycle.

**v1 epic (completed):** `LEAR-1` — Course Progress Update (Android-only)
All 11 stories LEAR-2 through LEAR-12 are Done.

**v2 epics (one per phase):**

| Epic | Phase | Summary |
|---|---|---|
| `LEAR-E2` | Phase 1 | Foundation — Python project, SQLite, SQLAlchemy, seed data |
| `LEAR-E3` | Phase 2 | Backend — FastAPI, Pydantic, Pytest, progress API |
| `LEAR-E4` | Phase 3 | Web App — Streamlit, analytics, charts, reports |
| `LEAR-E5` | Phase 4 | Android v2 — Retrofit, Room, DataStore, WorkManager |
| `LEAR-E6` | Phase 5 | Learning features — sessions, assessments, revision |
| `LEAR-E7` | Phase 6 | Production — auth, PostgreSQL, Docker, logging |

**v2 suggested stories per phase:**

Phase 1 — Foundation:
- Python project scaffold (pyproject.toml / requirements.txt, folder structure)
- SQLAlchemy models (Course, Topic, TopicStatus enum)
- Alembic setup and initial migration
- Database seed script (import 41 topics preserving IDs and titles)
- Data integrity tests (Pytest: topic count, status enum, no duplicate IDs)

Phase 2 — Backend:
- FastAPI application shell (main.py, router structure, CORS, error handling)
- Pydantic schemas (request/response models for all entities)
- Course and topic endpoints (CRUD + status update)
- Progress calculation endpoint (calculated, never stored)
- Pytest: unit tests for services, integration tests for endpoints
- OpenAPI spec generated and committed (`docs/api/openapi.json`)

Phase 3 — Web:
- Streamlit app shell (multi-page setup, navigation, theme)
- Dashboard page (progress summary, completion chart, recent activity)
- Course and topic management pages (list, create, update status)
- Analytics page (NumPy stats, Pandas grouping, Matplotlib charts)
- Search and filter (by status, course, keyword)
- Report export (CSV download via Pandas)

Phase 4 — Android v2:
- Retrofit + OkHttp client (API service interface, base URL config)
- Room database (TopicEntity, CourseEntity, DAOs, migrations)
- DataStore preferences (theme, display, notifications)
- ViewModel updates (switch from JSON asset to API + Room)
- WorkManager (revision reminder task)
- Offline-first sync strategy

Phase 5 — Learning features:
- Study session tracking (start/stop, duration recording)
- Exercise management (CRUD, linked to topic)
- Assessment recording (score, max_score, date)
- Revision scheduling (due dates, WorkManager integration)
- Report export endpoints (FastAPI CSV/PDF generation)

Phase 6 — Production:
- Authentication (JWT or OAuth2 via FastAPI security)
- Authorization (role-based: learner vs. admin)
- PostgreSQL migration (swap SQLite URL, run Alembic)
- Docker (Dockerfile for FastAPI, Dockerfile for Streamlit, docker-compose.yml)
- Logging (structured JSON logging, request IDs)
- Monitoring and backup strategy

These are proposed story descriptions. Use actual Jira keys once the project is discovered via MCP.

**Important (lesson learned from LearnTrack v1):** Always call `atlassian::getAccessibleAtlassianResources` first to get the real `cloudId` and confirm the project key. Create issues via MCP when available. CSV-only is a fallback, not the default.

Create per-phase CSV files:

`docs/jira/learntrack-v2-phase1-foundation.csv`
`docs/jira/learntrack-v2-phase2-backend.csv`
`docs/jira/learntrack-v2-phase3-web.csv`
`docs/jira/learntrack-v2-phase4-android.csv`
`docs/jira/learntrack-v2-phase5-features.csv`
`docs/jira/learntrack-v2-phase6-production.csv`

Map every requirement to its corresponding Jira work item, Kiro specification, implementation area, and verification test.

Do not invent remote Jira issue keys or claim issues have been created when only CSV files have been generated.

### 17. Kiro Specifications

Create or update:

`.kiro/specs/course-progress-update/requirements.md`

`.kiro/specs/course-progress-update/design.md`

`.kiro/specs/course-progress-update/tasks.md`

Requirements must contain functional and non-functional requirements, acceptance criteria, edge cases, and data-preservation constraints.

Design must document the actual architecture, data flow, UI changes, error handling, and testing strategy.

Tasks must be broken into small, independently verifiable implementation steps.

Include references to:

- Google Stitch prompts.
- Figma handoff and design system.
- Jira issues.
- Source files.
- Automated tests.
- Quality-gate documentation.

Use existing project conventions and update existing specifications when appropriate rather than duplicating them.

### 18. Kiro Steering Files

Inspect the existing `.kiro/steering/` directory before adding files.

Create or update the following where appropriate:

- `.kiro/steering/product.md`
- `.kiro/steering/tech.md`
- `.kiro/steering/structure.md`

Product steering must define LearnTrack's purpose, supported course-progress behavior, scope, and non-goals.

**v2 tech.md additions** — add these dimensions to the technology steering:

| Dimension | Value | Phase introduced |
|---|---|---|
| Backend framework | FastAPI (Python 3.11+) | Phase 2 |
| API validation | Pydantic v2 | Phase 2 |
| ORM | SQLAlchemy 2.x (async-capable) | Phase 1 |
| Migrations | Alembic | Phase 1 |
| Database (dev) | SQLite | Phase 1 |
| Database (prod) | PostgreSQL 15+ | Phase 6 |
| Analytics | NumPy, Pandas, Matplotlib | Phase 3 |
| Web frontend | Streamlit | Phase 3 |
| Testing (Python) | Pytest + httpx (async test client) | Phase 2 |
| Containerisation | Docker + docker-compose | Phase 6 |
| Android HTTP | Retrofit 2 + OkHttp | Phase 4 |
| Android local DB | Room | Phase 4 |
| Android preferences | DataStore (Preferences) | Phase 4 |
| Android background | WorkManager | Phase 4 |

**v2 coding standards additions:**

Python:
- Follow PEP 8. Use `ruff` for linting and formatting.
- Type-annotate all function signatures. Use `mypy` for static checking.
- All FastAPI route handlers are `async def`.
- Service layer is pure Python — no FastAPI imports in service classes.
- Pydantic models are in `schemas/` — never reuse SQLAlchemy models as API schemas.
- Alembic auto-generate migrations, then review before committing.
- Never use `Base.metadata.drop_all()` or `create_all()` in production code paths.

Progress calculation rule:
- `completion_rate = completed_count / total_count * 100`
- `needs_revision` status is NOT counted as completed
- Progress is always calculated at query time — never stored in a column
- This rule applies in Python (FastAPI), Kotlin (Android), and Streamlit equally

**v2 structure.md additions** — add these directories:

```
learntrack-backend/          ← new Python project root
  src/
    api/                     ← FastAPI routers
    core/                    ← config, security, database session
    models/                  ← SQLAlchemy ORM models
    schemas/                 ← Pydantic input/output schemas
    services/                ← business logic (no FastAPI deps)
    analytics/               ← NumPy, Pandas, Matplotlib functions
  tests/
    unit/                    ← service-level Pytest tests
    integration/             ← httpx async client tests
  alembic/                   ← migration scripts
  alembic.ini
  pyproject.toml             ← or requirements.txt
  Dockerfile

learntrack-web/              ← new Streamlit project root
  pages/
    01_Dashboard.py
    02_Topics.py
    03_Analytics.py
    04_Reports.py
  components/                ← reusable Streamlit components
  utils/                     ← API client, chart helpers
  app.py
  Dockerfile

docker-compose.yml           ← at repo root (Phase 6)
```

Structure steering must document actual repository organization, naming conventions, module boundaries, and artifact locations.

Keep project-wide steering concise and reusable. Feature-specific rules belong in the feature specification or skills.

### 19. Reusable Kiro Skills

Create reusable skills following the skill format supported by the installed Kiro version.

Inspect the repository and Kiro documentation before choosing exact frontmatter or configuration fields.

**v1 skills (exist — do not recreate):**
- `learntrack-repository-analysis/SKILL.md`
- `learntrack-json-integrity/SKILL.md`
- `learntrack-progress-validation/SKILL.md`
- `learntrack-ui-implementation/SKILL.md`
- `learntrack-testing-quality-gates/SKILL.md`
- `learntrack-jira-traceability/SKILL.md`

**v2 new skills to create:**

`.kiro/skills/learntrack-fastapi-backend/SKILL.md`

Responsibilities:
- Scaffold FastAPI application structure.
- Implement routers, Pydantic schemas, and service layer.
- Write async Pytest integration tests using `httpx.AsyncClient`.
- Validate that progress is calculated, never stored.
- Ensure Pydantic rejects invalid status values at the API boundary.

`.kiro/skills/learntrack-database-migrations/SKILL.md`

Responsibilities:
- Create and apply Alembic migrations safely.
- Never use `drop_all` / `create_all` in production paths.
- Preserve existing topic IDs and names across schema changes.
- Verify migration with `alembic upgrade head` and a post-migration data check.
- Generate a rollback migration for every forward migration.

`.kiro/skills/learntrack-streamlit-web/SKILL.md`

Responsibilities:
- Build Streamlit pages following the approved Figma/Stitch designs.
- Call the FastAPI backend via the shared API client utility.
- Render Matplotlib charts inline (`st.pyplot`).
- Handle API errors gracefully (show error state, never crash).
- Keep page logic thin — business logic belongs in FastAPI services.

`.kiro/skills/learntrack-analytics/SKILL.md`

Responsibilities:
- Use NumPy for numerical calculations (completion rates, score averages).
- Use Pandas for topic-status grouping, module-level aggregation, and report DataFrames.
- Use Matplotlib for chart generation; return base64 or figure objects.
- Never hard-code 22, 19, 41, or 53.7% — all values come from database queries.
- Validate output ranges (completion rate must be 0–100).

`.kiro/skills/learntrack-android-v2/SKILL.md`

Responsibilities:
- Implement Retrofit service interfaces matching FastAPI OpenAPI spec.
- Implement Room DAOs and sync logic (API fetch → Room upsert → UI observes Room).
- Configure DataStore for preferences without blocking the main thread.
- Schedule WorkManager tasks only when the feature is implemented — no speculative scheduling.
- Test ViewModels with mock Retrofit responses and in-memory Room database.

`.kiro/skills/learntrack-docker-deployment/SKILL.md`

Responsibilities:
- Write Dockerfiles for FastAPI and Streamlit with minimal base images.
- Write `docker-compose.yml` for local development (SQLite volume) and production (PostgreSQL service).
- Never embed secrets in Dockerfile or docker-compose — use environment variables.
- Verify `docker-compose up` starts all services and health checks pass.
- Document the migration step required before starting the API container.

Every skill must include its purpose, when to use it, required inputs, step-by-step procedure, constraints, expected outputs, and verification criteria.

Skills must be actionable, reusable, and specific to LearnTrack.

### 20. Kiro Implementation Prompt Chain

Create copy-ready prompts in:

`docs/kiro-prompts/`

**v1 prompts (exist — do not recreate):**
01–12 cover repository discovery through Android release readiness.

**v2 new prompts to create (one set per phase):**

Phase 1 — Foundation:
- `13-python-project-scaffold.md` — project structure, pyproject.toml, folder layout
- `14-sqlalchemy-models.md` — Course, Topic, TopicStatus models, relationships
- `15-alembic-migrations.md` — initial migration, seed script, data integrity test
- `16-foundation-quality-gates.md` — Pytest data tests, linting with ruff, mypy check

Phase 2 — Backend:
- `17-fastapi-scaffold.md` — app factory, routers, CORS, error handling
- `18-pydantic-schemas.md` — input/output schemas for all entities
- `19-api-endpoints.md` — course/topic/progress/analytics endpoints
- `20-backend-testing.md` — Pytest unit + httpx integration tests
- `21-openapi-export.md` — generate and commit openapi.json

Phase 3 — Web:
- `22-streamlit-scaffold.md` — multi-page app, API client utility
- `23-streamlit-dashboard.md` — progress summary, charts, recent activity
- `24-streamlit-topics.md` — topic management, status updates, filters
- `25-streamlit-analytics.md` — NumPy stats, Pandas grouping, Matplotlib charts
- `26-streamlit-reports.md` — CSV export, report download

Phase 4 — Android v2:
- `27-retrofit-client.md` — service interface, base URL, interceptors
- `28-room-database.md` — entities, DAOs, migrations, offline-first sync
- `29-datastore-preferences.md` — theme, display, notification settings
- `30-workmanager-tasks.md` — revision reminders, sync task

Phase 5 — Learning features:
- `31-study-sessions.md` — start/stop, duration, API + Room
- `32-assessments.md` — score recording, history, API
- `33-revision-scheduling.md` — due dates, WorkManager trigger
- `34-report-exports.md` — FastAPI CSV/PDF endpoints

Phase 6 — Production:
- `35-authentication.md` — JWT/OAuth2, protected endpoints
- `36-postgresql-migration.md` — connection string swap, Alembic verify
- `37-docker-setup.md` — Dockerfiles, docker-compose, health checks
- `38-logging-monitoring.md` — structured logging, request IDs, error alerting
- `39-production-readiness.md` — release checklist, backup, secrets management

Each prompt must include its prerequisites, specific task, allowed scope, required outputs, acceptance criteria, and stop conditions.

Later prompts must reference artifacts produced by earlier prompts.

### 21. MCP Configuration

Inspect the existing `.kiro/settings/mcp.json`.

Determine whether the project requires GitHub, Figma, documentation, Git/filesystem, or other MCP integrations based on actual workflows.

Create or update:

`docs/setup/kiro-mcp-setup.md`

If necessary, prepare a project-specific MCP configuration using only supported server configurations and verified documentation.

Requirements:

- Use least-privilege access.
- Never include access tokens, passwords, or credentials in committed configuration.
- Prefer environment-variable references and approved authentication flows.
- Keep write access disabled or restricted when read-only access is sufficient.
- Document the purpose of each MCP server.
- Validate JSON syntax and server configuration.
- Separate development-only database access from production.
- Do not add a database MCP unless the existing architecture and task justify it.
- Do not assume Stitch or Figma MCP access exists without verification.

MCP configuration must be optional for ordinary implementation and testing when the repository can be handled locally.

### 22. Build Variants and Environments

Create or update:

`docs/build-variants/learntrack-build-variants.md`

`docs/build-variants/learntrack-environment-configuration.md`

**v2 additions:**

`docs/build-variants/learntrack-python-environments.md`

`docs/build-variants/learntrack-docker-environments.md`

Inspect the existing build system first.

#### Android (existing — v1)
- Debug: `.\gradlew.bat assembleDebug` — no applicationIdSuffix; uses `com.learntrack`
- Release: `.\gradlew.bat assembleRelease` — requires keystore in `local.properties`
- v2 addition: `BASE_URL` BuildConfig field pointing to FastAPI (`http://10.0.2.2:8000` for emulator, production URL for release)

#### Python backend (new — v2)
| Environment | Command | Database | Notes |
|---|---|---|---|
| Local dev | `uvicorn src.main:app --reload` | SQLite `learntrack.db` | No Docker needed |
| Local test | `pytest tests/` | In-memory SQLite (test fixture) | Isolated per test |
| Docker dev | `docker-compose up` | SQLite volume | Matches production structure |
| Production | `docker-compose -f docker-compose.prod.yml up` | PostgreSQL | Requires `DATABASE_URL` env var |

Environment variables (never committed — use `.env` or system env):
- `DATABASE_URL` — SQLite path or PostgreSQL connection string
- `SECRET_KEY` — JWT signing key (Phase 6)
- `ALLOWED_ORIGINS` — CORS origins for FastAPI
- `DEBUG` — `true` for dev, `false` for prod

#### Streamlit web app (new — v2)
| Environment | Command | API base URL |
|---|---|---|
| Local dev | `streamlit run app.py` | `http://localhost:8000` |
| Docker dev | `docker-compose up` | `http://api:8000` (Docker network) |
| Production | Container via docker-compose | Production FastAPI URL |

Do not create unnecessary flavors, cloud services, or deployment environments just to match another project's architecture.

Keep development and production configuration separate. Never embed secrets in source code.

### 23. Pre-Push Quality Gates

Create or update:

- `scripts/check-before-push.ps1`
- `scripts/check-before-push.sh`
- `docs/quality-gates/learntrack-quality-gates.md`

Inspect the existing CI pipeline and available tooling before implementing the scripts.

**v1 checks (exist — preserve):**
1. JSON syntax validation (`Test-Json` / `jq`)
2. Topic distribution assertion (41/22/19 via `.topics`)
3. Android unit tests (`gradlew.bat testDebugUnitTest`)
4. Android lint (`gradlew.bat lintDebug`)
5. Android build (`gradlew.bat assembleDebug`)
6. Secret scan on staged files

**v2 additional checks (add per phase):**

Phase 2 — Backend:
7. Python lint: `ruff check src/ tests/`
8. Python type check: `mypy src/`
9. Pytest unit tests: `pytest tests/unit/ -v`
10. Pytest integration tests: `pytest tests/integration/ -v`
11. OpenAPI spec freshness: confirm `docs/api/openapi.json` matches running app

Phase 3 — Web:
12. Streamlit import check: `python -c "import app"` (no syntax errors)
13. Pandas/Matplotlib smoke test: run analytics module with seed data

Phase 6 — Production:
14. Docker build: `docker-compose build --no-cache`
15. Docker health check: `docker-compose up -d && sleep 5 && curl http://localhost:8000/health`
16. Alembic migration check: `alembic check` (no pending migrations)

Requirements:

- Use commands supported by the actual repository.
- Fail when a required check fails.
- Return a non-zero exit code on failure.
- Report which check failed.
- Stop subsequent checks when continuing would be unsafe.
- Skip checks whose toolchain is not yet installed (Phase 1 cannot run Phase 6 Docker checks).
- Support Windows PowerShell and Bash where feasible.
- Do not automatically push, deploy, or delete files.

If an existing check-before-push script exists, extend it rather than replacing it without review.

### 24. CI/CD and Release Readiness

Inspect the existing CI/CD configuration.

Update the pipeline only where required to enforce the agreed quality gates.

The release-readiness process must:

- Validate the JSON and topic distribution.
- Run automated tests.
- Build the application.
- Check relevant dependency and configuration issues.
- Verify that the approved design is reflected in the UI.
- Confirm that all acceptance criteria are addressed.
- Produce a report of passed, failed, skipped, and blocked checks.
- Keep deployment separate from validation.
- Require explicit approval for production deployment or destructive data operations.

Do not introduce a new cloud platform or CI provider without a demonstrated requirement.

### 25. End-to-End Traceability

Create:

`docs/traceability/learntrack-requirement-traceability.md`

Maintain the following chain:

Requirement ID
→ Stitch screen or design requirement
→ Figma component or screen
→ Jira issue
→ Kiro specification and task
→ Implementation file
→ Automated test
→ Quality-gate result.

Not every requirement needs a UI design. Data integrity and backend requirements may link directly to architecture, code, and tests.

Flag missing links rather than inventing them.

### 26. Final Deliverables Checklist

Before reporting completion, verify the applicable deliverables.

**v1 deliverables (completed October 2026):**
- [x] `learntrack_topics.json` (41/22/19) ✅
- [x] Repository discovery report ✅
- [x] Requirements, design, and task specifications ✅
- [x] Google Stitch prompts and design review checklist ✅
- [x] Figma UI Designs + Design System (both files live) ✅
- [x] Jira: 48 issues LEAR-1 through LEAR-49 (all 11 stories Done) ✅
- [x] 6 reusable Kiro skills ✅
- [x] 12 Kiro implementation prompts ✅
- [x] Steering files (product, tech, structure) ✅
- [x] Architecture and data-flow documentation ✅
- [x] Build variants and environment documentation ✅
- [x] PowerShell pre-push quality gate ✅
- [x] Bash pre-push quality gate ✅
- [x] GitHub Actions CI/CD ✅
- [x] 9 unit tests passing ✅
- [x] Requirement-to-design-to-code-to-test traceability ✅
- [x] Release-readiness report ✅
- [ ] UI tests on connected device 🔲 (pending)
- [ ] Stitch PNG exports 🔲 (pending — re-download from project 683813527608164394)

**v2 deliverables (add per phase — check off when complete):**

Phase 1 — Foundation:
- [ ] Python project structure (`pyproject.toml`, `src/`, `tests/`, `alembic/`)
- [ ] SQLAlchemy models (Course, Topic, TopicStatus)
- [ ] Alembic initial migration committed
- [ ] Seed script — 41 topics imported with IDs and titles preserved
- [ ] Pytest data integrity tests (count, enum, no duplicate IDs)

Phase 2 — Backend:
- [ ] FastAPI app (routers, CORS, error handling)
- [ ] Pydantic schemas for all entities
- [ ] All API endpoints implemented and documented
- [ ] `docs/api/openapi.json` generated and committed
- [ ] Pytest unit + integration tests (all passing)
- [ ] ruff + mypy pass with zero errors

Phase 3 — Web:
- [ ] Streamlit app with all pages (dashboard, topics, analytics, reports)
- [ ] Matplotlib charts rendered inline
- [ ] CSV report download working
- [ ] Analytics endpoint verified (NumPy/Pandas output correct)

Phase 4 — Android v2:
- [ ] Retrofit client connected to FastAPI
- [ ] Room database with offline-first sync
- [ ] DataStore preferences implemented
- [ ] WorkManager task registered (if feature implemented)
- [ ] ViewModel tests updated for API + Room

Phase 5 — Learning features:
- [ ] Study session tracking (API + Android)
- [ ] Assessment recording (API + Android)
- [ ] Revision scheduling (API + WorkManager)
- [ ] Report export endpoints

Phase 6 — Production:
- [ ] Authentication (JWT/OAuth2)
- [ ] PostgreSQL migration verified
- [ ] Dockerfile (FastAPI) builds cleanly
- [ ] Dockerfile (Streamlit) builds cleanly
- [ ] `docker-compose up` starts all services and health checks pass
- [ ] Structured logging in FastAPI
- [ ] No secrets committed to any file
- [ ] Release-readiness report for v2

Mark a deliverable complete only when its actual file or external artifact has been verified.

### 27. Final Execution Instructions

Start with repository discovery. Then proceed incrementally through requirements, design, planning, implementation, testing, and release readiness — **one phase at a time**.

At each phase:

1. Read existing project guidance and related files.
2. Reuse existing artifacts where possible.
3. Produce the phase deliverables.
4. Validate the deliverables (run tests, lint, build).
5. Record unresolved decisions and dependencies.
6. Proceed when required prerequisites are satisfied.

**Phase sequencing rule:** Do not start Phase N+1 until Phase N has a passing test suite and committed deliverables. In particular:
- Do not introduce FastAPI (Phase 2) before SQLAlchemy models and seed data exist (Phase 1).
- Do not introduce Streamlit (Phase 3) before the FastAPI endpoints it calls are working and tested.
- Do not introduce Retrofit in Android (Phase 4) before the FastAPI API is stable.
- Do not introduce PostgreSQL or Docker (Phase 6) before all Phase 1–5 features are tested on SQLite.

For external tools that require user access or manual action, prepare the handoff and wait for the necessary access rather than claiming completion.

Do not stop at documentation when the actual implementation can safely proceed. Do not modify unrelated features, overwrite existing user data, fabricate external resources, or deploy without authorization.

The final report must summarize actual changes, verified results, missing integrations, outstanding approvals, and the remaining work required for release.

---

### 28. Lessons Learned — Append After Each Project (NEW)

After completing a project with this prompt, append findings here so the next project benefits.

**LearnTrack v1 — October 2026 (Android-only):**

1. **Figma MCP seat requirement:** MCP write tools require a Full seat. Always check `figma::whoami` seat field before the Figma phase. View-only accounts silently fail.

2. **Topic names are a blocker:** The topic list for the JSON file was not confirmed before documentation. This created a dependency gap noted in the discovery report. Always get content confirmed before T-005.

3. **Progress Summary needs its own Stitch export:** Stitch exported it only as part of the long dashboard scroll. Explicitly request it as a standalone screen.

4. **Jira project key must be discovered first:** Using placeholder keys like `LT-001` in docs before knowing the real Jira project key (e.g., `LEAR`) creates confusion. Discover the key in the pre-phase check and use it everywhere from the start.

5. **Android scaffold is a story, not just subtasks:** T-001 (Gradle scaffold), T-002 (theme/nav), T-003 (Hilt) are substantial enough for their own Jira stories. Subtasks alone make them invisible on the board.

6. **JSON schema wrapper object:** The pre-push scripts initially assumed a flat JSON array. The actual schema `{"topics": [...]}` requires `.topics` access. Always specify the exact schema shape in the quality gate scripts and test them against a real file before committing.

7. **Record Figma node IDs immediately:** After MCP inspection, record all node IDs (`page:section`, `frame`) in the handoff doc. Implementation agents need these to call `figma::get_design_context` without re-discovery.

8. **Dark mode for all screens:** Only the dashboard got a dark variant in Figma. Require dark mode frames for all screens in the Stitch prompt and Figma handoff from the start.

---

**LearnTrack v2 — lessons to record after each phase completes:**

*(Append here after Phase 1, Phase 2, etc.)*

**General full-stack lessons (pre-recorded from architecture planning):**

9. **Progress must never be a stored column:** A column like `completion_percentage FLOAT` in the database is always wrong. It drifts from reality the moment a topic status changes. Always calculate at query time.

10. **`needs_revision` ≠ `completed`:** Define the TopicStatus enum and its business rules before writing the first endpoint. Encode the rule in a service-layer function that is tested independently from the API layer.

11. **Alembic before data:** Create the Alembic migration before writing any seed data. Running `alembic upgrade head` is the only way to create the schema — `Base.metadata.create_all()` is for tests only.

12. **Android emulator reaches localhost via 10.0.2.2:** The Android emulator's loopback address is `10.0.2.2`, not `localhost` or `127.0.0.1`. Configure this as the default `BASE_URL` in debug `BuildConfig` from the start to avoid connection failures during development.

13. **Streamlit API client is not a backend:** Streamlit should call FastAPI endpoints for all data operations. It must not import SQLAlchemy models directly — this would bypass validation, business logic, and Pydantic schemas.

14. **Docker health checks before declaring service ready:** `docker-compose up` returning exit 0 does not mean the API is accepting requests. Always add a `healthcheck` to the FastAPI service and wait for it before running integration tests in CI.

15. **SQLite to PostgreSQL is a URL swap, not a rewrite:** If SQLAlchemy is used correctly from Phase 1 (no raw SQL, no SQLite-specific types), the Phase 6 migration is just changing `DATABASE_URL`. Test this assumption in Phase 1 by verifying no SQLite-specific dialect features are used.
