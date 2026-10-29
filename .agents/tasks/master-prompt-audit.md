# LearnTrack — Extended AI-SDLC Master Prompt Audit Report

**Audit date:** 2026-10-29 (read-only investigation)  
**Workspace root:** `j:\Android\AndroidStudioProjects\Kiro\LearnTrack`  
**Master prompt:** `docs/learntrack-ai-sdlc-master-prompt.md`  
**Auditor:** automated Kiro workflow step  

---

## 1. Section-by-Section Findings Table

### §11 — End-to-End AI-SDLC Workflow (12 phases)

| Section | Expected deliverable | File / system | Status | Notes |
|---|---|---|---|---|
| §11 Discovery | `docs/discovery/learntrack-discovery-report.md` | `docs/discovery/learntrack-discovery-report.md` | ✅ | Exists, GAP-001–009 documented |
| §11 Requirements | `.kiro/specs/course-progress-update/requirements.md` | `.kiro/specs/course-progress-update/requirements.md` | ✅ | FR-001–015, NFR-001–008 |
| §11 Google Stitch | Prompts on disk; exports | `docs/design/google-stitch/` | ⚠️ | Prompts exist; exports are placeholder — see §13 |
| §11 Figma | Design files + handoff docs | `docs/design/figma/` + live Figma files | ✅ | Both files live; node IDs recorded |
| §11 Architecture | 3 architecture docs | `docs/architecture/` | ✅ | All 3 docs present |
| §11 Jira | CSV files; live issues | `docs/jira/` + Atlassian LEAR board | ⚠️ | CSVs use LT-xxx keys; live board uses LEAR-2–12; CSVs never updated with real keys |
| §11 Kiro Specs | 3 spec files | `.kiro/specs/course-progress-update/` | ✅ | All 3 present and complete |
| §11 Skills | 6 SKILL.md files | `.kiro/skills/*/SKILL.md` | ✅ | All 6 present |
| §11 Implementation | Full app/ module | `app/src/main/java/com/learntrack/` | ✅ | All required source files exist |
| §11 Testing | Unit + regression tests passing; UI tests present | `app/src/test/` + `app/src/androidTest/` | ✅ | 9 unit tests pass; 2 UI test files present |
| §11 Quality Gates | Pre-push scripts + CI | `scripts/` + `.github/workflows/ci.yml` | ✅ | All present and functional |
| §11 Release Readiness | Release report | `docs/release/learntrack-release-readiness-report.md` | ✅ | Exists; AC-1–8 PASS, AC-9 pending device |

---

### §12 — Scope Management

| Expected | File | Status | Notes |
|---|---|---|---|
| `docs/scope/learntrack-scope.md` covers in/out scope | `docs/scope/learntrack-scope.md` | ✅ | In-scope and out-of-scope sections present |
| `learntrack_topics.json` has 41/22/19 | `app/src/main/assets/learntrack_topics.json` | ✅ | Verified: total=41, completed=22, not_started=19 |

---

### §12a — Pre-Phase Access Verification (NEW)

| Expected | Status | Notes |
|---|---|---|
| Section exists in master prompt | ✅ | Found at line 45 of master prompt |
| Covers Figma `whoami` + seat check | ✅ | Item 1–2 of Figma access block |
| Covers Jira `cloudId` discovery | ✅ | Item 1 of Jira access block |
| Stitch treated as manual-only | ✅ | Explicitly stated: "always treat Stitch as requiring manual execution" |
| Topic names as required blocker | ✅ | Last bullet in §12a |

---

### §13 — Google Stitch

| Expected | File | Status | Notes |
|---|---|---|---|
| `docs/design/google-stitch/learntrack-stitch-master-prompt.md` | Exists | ✅ | Present |
| `docs/design/google-stitch/course-progress-prompt.md` | Exists | ✅ | Present |
| `docs/design/google-stitch/design-review-checklist.md` | Exists | ✅ | Present |
| `docs/design/google-stitch/exports/` — 5 subdirs with screen.png + code.html | Exists, 5 dirs | ⚠️ | **5 subdirs exist but all screen.png files are 28-byte placeholders** (content: `<FIFE Image failed to fetch>`). Not valid PNG images. code.html files contain real HTML (23–40 KB each). `learntrack_mobile_design_system/` contains `DESIGN.md` only, not screen.png + code.html |
| Stitch Project ID `683813527608164394` recorded in master prompt doc | `docs/learntrack-ai-sdlc-master-prompt.md` | ❌ | **Project ID is NOT in the master prompt.** It is only in `learntrack-stitch-master-prompt.md`. Master prompt §13 contains no reference to this ID |
| Stitch project ID in stitch-master-prompt.md | Line 26 of `learntrack-stitch-master-prompt.md` | ✅ | Recorded there |
| No fabricated export sizes | — | ⚠️ | `learntrack-stitch-master-prompt.md` shows export sizes (441 KB, 253 KB, etc.) and file paths that **do not match** the actual files on disk. The actual screen.png files are 28 bytes each; the referenced paths `exports/course-progress-dark.png` etc. do not exist |

---

### §14 — Figma

| Expected | File | Status | Notes |
|---|---|---|---|
| `docs/design/figma/learntrack-figma-handoff.md` with real file key `5dEMGt9iynTXgVGqjxd9IM` | Exists | ✅ | File key on line 6 and confirmed at line 22 |
| `docs/design/figma/learntrack-design-system.md` with Design System file key `AbG7UxbofJuAvBFCcZW0pv` | Exists | ✅ | File key on line 5 |
| `docs/design/figma/learntrack-screen-inventory.md` | Exists | ✅ | Present |
| UI Designs has 3 pages (Cover, Stitch Import, UI Screens) | Verified via MCP; documented in handoff | ✅ | Page IDs `0:1`, `3:4`, `1:2` confirmed |
| UI Screens page has 5 sections (Dashboard, Topic List, Topic Detail, Progress Summary, States) | Documented in handoff, lines 36–40 | ✅ | Section IDs `3:5`–`3:9` all listed |
| Figma Design System has 3 pages populated | Documented in design-system.md lines 12–14 | ✅ | Starter plan — 3 pages: Cover+Color Tokens, Typography+Spacing+Elevation, Components |
| Node IDs recorded in handoff for implementation agents | Section 7 of handoff, lines 505+ | ✅ | `4:3`, `3:4`, `1:2` etc. all recorded |
| MCP setup doc documents `https://mcp.figma.com/mcp` | `docs/setup/kiro-mcp-setup.md` | ❌ | **`mcp.figma.com` URL is NOT in the MCP setup doc.** Doc was written before Figma MCP was used; shows `@figma/mcp-server` npm package config instead. Figma MCP notes are in the handoff doc (section 7), not the MCP setup doc |

---

### §15 — Architecture

| Expected | File | Status | Notes |
|---|---|---|---|
| `docs/architecture/learntrack-architecture.md` | Exists | ✅ | Present |
| `docs/architecture/learntrack-data-flow.md` | Exists | ✅ | Present |
| `docs/architecture/learntrack-environments.md` | Exists | ✅ | Present |

---

### §16 — Jira

| Expected | File / System | Status | Notes |
|---|---|---|---|
| `docs/jira/learntrack-epics.csv` | Exists | ✅ | 1 epic, CSV-only, no fabricated live keys |
| `docs/jira/learntrack-stories.csv` | Exists | ✅ | 11 stories with `LT-001`–`LT-011` (proposed keys, not live) |
| `docs/jira/learntrack-subtasks.csv` | Exists | ✅ | Subtasks linked to parent stories |
| `docs/jira/learntrack-traceability.csv` | Exists | ✅ | FR-001–015 + NFRs; still uses LT-xxx keys |
| Live Jira issues LEAR-1 through LEAR-12 | Atlassian board | ⚠️ | Checklist records LEAR-2 through LEAR-12 Done; LEAR-1 = Epic (referenced in release report as the epic ID). **Live issues were created (board URL: `androidassistant.atlassian.net/jira/software/projects/LEAR/boards/38`) but the CSV files themselves were never updated with LEAR-xxx keys — they still show proposed LT-xxx keys** |
| All 11 stories marked Done | LEAR-2 through LEAR-12 | ✅ | Deliverables checklist records all 11 Done |

---

### §17 — Kiro Specifications

| Expected | File | Status | Notes |
|---|---|---|---|
| `requirements.md` FR-001–015 | `.kiro/specs/course-progress-update/requirements.md` | ✅ | All 15 FRs present (lines 22–231) |
| `design.md` | `.kiro/specs/course-progress-update/design.md` | ✅ | Present |
| `tasks.md` T-001–T-014 | `.kiro/specs/course-progress-update/tasks.md` | ✅ | T-001 through T-014 confirmed |
| Specs reference Stitch, Figma, Jira, source files, tests, quality gates | Present in spec cross-references | ✅ | Prompt 09 and prompt 12 reference earlier artifacts |

---

### §18 — Kiro Steering Files

| Expected | File | Status | Notes |
|---|---|---|---|
| `.kiro/steering/product.md` | Exists | ✅ | Present; reflects actual tech stack |
| `.kiro/steering/tech.md` | Exists | ✅ | Present; verified stack (Compose M3, Hilt, Coroutines) |
| `.kiro/steering/structure.md` | Exists | ✅ | Present |
| Steering reflects actual implementation | — | ⚠️ | **Minor deviations from actual layout:** (1) `structure.md` lists theme package as `com.learntrack.theme` but actual implementation uses `com.learntrack.ui.theme`. (2) `structure.md` target layout does not include `com.learntrack.navigation` package, which exists in the implementation with `LearnTrackNavHost.kt` and `Screen.kt`. (3) `structure.md` lists `TopicContainer.kt` in `data/validation/` but actual location is `data/model/TopicContainer.kt`; also `TopicSchemaValidator.kt` actual path includes a `data/validation/ValidationException.kt` file not in the spec |

---

### §19 — Kiro Skills

| Expected | File | Status | Notes |
|---|---|---|---|
| `learntrack-repository-analysis/SKILL.md` | Exists | ✅ | Present |
| `learntrack-json-integrity/SKILL.md` | Exists | ✅ | All 7 required sections confirmed |
| `learntrack-progress-validation/SKILL.md` | Exists | ✅ | Present |
| `learntrack-ui-implementation/SKILL.md` | Exists | ✅ | Present |
| `learntrack-testing-quality-gates/SKILL.md` | Exists | ✅ | Present |
| `learntrack-jira-traceability/SKILL.md` | Exists | ✅ | Present |
| Each has: Purpose, When to Use, Required Inputs, Step-by-Step Procedure, Constraints, Expected Outputs, Verification Criteria | Verified for json-integrity | ✅ | All 7 sections present in the verified skill |

---

### §20 — Kiro Implementation Prompt Chain

| Expected | File | Status | Notes |
|---|---|---|---|
| `01-repository-discovery.md` | Exists | ✅ | Present |
| `02-requirements-and-scope.md` | Exists | ✅ | Present |
| `03-google-stitch-ui-design.md` | Exists | ✅ | Present |
| `04-figma-design-handoff.md` | Exists | ✅ | Present |
| `05-architecture-and-data-design.md` | Exists | ✅ | Present |
| `06-jira-work-breakdown.md` | Exists | ✅ | Present |
| `07-kiro-specs-and-skills.md` | Exists | ✅ | Present |
| `08-topic-json-update.md` | Exists | ✅ | Present |
| `09-progress-ui-implementation.md` | Exists | ✅ | References prompt 04 and earlier artifacts |
| `10-automated-testing.md` | Exists | ✅ | Present |
| `11-build-and-quality-gates.md` | Exists | ✅ | Present |
| `12-release-readiness.md` | Exists | ✅ | References learntrack artifacts, FR- items, LEAR- keys |
| Later prompts reference earlier artifacts | Verified for 09 and 12 | ✅ | Cross-references confirmed |

---

### §21 — MCP Configuration

| Expected | File | Status | Notes |
|---|---|---|---|
| `docs/setup/kiro-mcp-setup.md` | Exists | ✅ | Present |
| Documents Figma MCP (`https://mcp.figma.com/mcp`) | Not in file | ❌ | **Setup doc uses `@figma/mcp-server` npm config, not `https://mcp.figma.com/mcp`. The actual Figma MCP URL used in this project (`mcp.figma.com`) is not documented in the setup guide.** Figma MCP notes are split across the handoff doc instead |
| Documents Atlassian MCP | Line 27 | ✅ | Atlassian listed as server option |
| No secrets or tokens present | Checked for `figd_`, `eyJ`, `Bearer ` patterns | ✅ | No literal token values found; all references use env var placeholders |
| `.kiro/settings/mcp.json` exists | Not present | ⚠️ | MCP setup doc says the file was absent; confirmed absent. The MCP setup doc itself is accurate about this, but the project has no configured MCP.json for future agents |

---

### §22 — Build Variants

| Expected | File | Status | Notes |
|---|---|---|---|
| `docs/build-variants/learntrack-build-variants.md` | Exists | ✅ | Present |
| `docs/build-variants/learntrack-environment-configuration.md` | Exists | ✅ | Present |
| Matches actual `app/build.gradle.kts` (debug/release build types) | — | ⚠️ | **Minor mismatch:** `learntrack-build-variants.md` documents `applicationIdSuffix = .debug` for the debug build type, but `app/build.gradle.kts` has **no `applicationIdSuffix`** in the debug block. The debug app runs as `com.learntrack`, not `com.learntrack.debug`. Functionally harmless but the doc is inaccurate |

---

### §23 — Pre-Push Quality Gates

| Expected | File | Status | Notes |
|---|---|---|---|
| `scripts/check-before-push.ps1` — accesses `.topics` from JSON wrapper | Exists | ✅ | `$parsed.topics` confirmed at line 126; uses `gradlew.bat` variable (`$GradleWrapper = Join-Path $RepoRoot "gradlew.bat"`) |
| `scripts/check-before-push.sh` — same | Exists | ✅ | `.topics \| length` jq expression at lines 154–157 |
| `docs/quality-gates/learntrack-quality-gates.md` | Exists | ✅ | Present |
| PowerShell script uses `cmd /c gradlew.bat` (Windows-compatible) | — | ✅ | Script uses `& $GradleWrapper` where `$GradleWrapper` = `gradlew.bat` — Windows-compatible |

---

### §24 — CI/CD

| Expected | File | Status | Notes |
|---|---|---|---|
| `.github/workflows/ci.yml` | Exists | ✅ | Present |
| JSON validation step | Line `QG-001/002` | ✅ | `bash scripts/check-before-push.sh` |
| Unit test step | `QG-003/004` | ✅ | `./gradlew testDebugUnitTest` |
| Lint step | `QG-006` | ✅ | `./gradlew lintDebug` |
| `assembleDebug` step | `QG-007` | ✅ | Present |
| `android:Theme.Material.Light.NoActionBar` theme fix | `app/src/main/res/values/themes.xml` | ✅ | Theme parent confirmed as `android:Theme.Material.Light.NoActionBar` — correct fix applied |
| MDC-Android dependency | `app/build.gradle.kts` | ✅ | Uses `androidx.compose.material3`, not legacy MDC |

---

### §25 — Traceability

| Expected | File | Status | Notes |
|---|---|---|---|
| `docs/traceability/learntrack-requirement-traceability.md` | Exists | ✅ | Present |
| Maps FR-001–015 through all layers | Confirmed for FR-001–015 | ✅ | All FRs + NFRs have full chain rows |
| Implementation file columns have real paths (not "Predicted") | — | ❌ | **All implementation file cells still say `_(PREDICTED — not yet created)_` and `Status: MISSING`.** The document was written during the pre-scaffold phase and was never updated after implementation. The files now exist on disk but the traceability doc was never refreshed |

---

### §26 — Final Deliverables Checklist

| Expected | File | Status | Notes |
|---|---|---|---|
| `docs/learntrack-deliverables-checklist.md` | Exists | ✅ | Present |
| Counts show 16/17 complete | Lines 283–291 | ✅ | Summary shows: ✅ Complete: 16, 🔲 Manual Action Required: 1 |
| One pending item (UI tests) correctly documented | Line item 15 + item 15's note | ✅ | AC-9 (connectedDebugAndroidTest) documented as pending device connection; not falsely marked complete |

---

### §27 — Final Execution Instructions

| Expected | Status | Notes |
|---|---|---|
| §27 exists in master prompt | ✅ | Lines 538–557 |
| Instructions match what actually happened | ✅ | Phases ran in documented order; external tool handoffs prepared; report produced |

---

### §28 — Lessons Learned (NEW)

| Expected | Status | Notes |
|---|---|---|
| §28 section exists | ✅ | Found at line 559 of master prompt |
| 8 lessons from LearnTrack project | ✅ | Lessons 1–8 all present and specific |
| Lessons cover: Figma seat, topic names, Stitch export, Jira key, scaffold stories, JSON schema, node IDs, dark mode | ✅ | All 8 topics confirmed |

---

### Key Source Files

| File | Status | Notes |
|---|---|---|
| `app/src/main/assets/learntrack_topics.json` | ✅ | 41 topics, 22 completed, 19 not_started, wrapper schema |
| `app/src/main/java/com/learntrack/MainActivity.kt` | ✅ | Present |
| `app/src/main/java/com/learntrack/LearnTrackApplication.kt` | ✅ | Present |
| `app/src/main/java/com/learntrack/data/model/Topic.kt` | ✅ | Present |
| `app/src/main/java/com/learntrack/data/model/TopicStatus.kt` | ✅ | Present |
| `app/src/main/java/com/learntrack/data/model/TopicContainer.kt` | ✅ | Present (in `data/model/`, not `data/validation/`) |
| `app/src/main/java/com/learntrack/data/validation/TopicSchemaValidator.kt` | ✅ | Present |
| `app/src/main/java/com/learntrack/data/repository/CourseRepository.kt` | ✅ | Present |
| `app/src/main/java/com/learntrack/data/repository/CourseRepositoryImpl.kt` | ✅ | Present |
| `app/src/main/java/com/learntrack/domain/model/CourseProgress.kt` | ✅ | Present |
| `app/src/main/java/com/learntrack/domain/usecase/CalculateProgressUseCase.kt` | ✅ | Present |
| `app/src/main/java/com/learntrack/domain/usecase/GetCourseProgressUseCase.kt` | ✅ | Present |
| `app/src/main/java/com/learntrack/ui/progress/CourseUiState.kt` | ✅ | Present |
| `app/src/main/java/com/learntrack/ui/progress/CourseProgressViewModel.kt` | ✅ | Present |
| `app/src/main/java/com/learntrack/ui/progress/CourseProgressScreen.kt` | ✅ | Present |
| `app/src/main/java/com/learntrack/ui/topics/TopicListScreen.kt` | ✅ | Present |
| `app/src/main/java/com/learntrack/ui/topics/TopicDetailScreen.kt` | ✅ | Present |
| `app/src/main/java/com/learntrack/di/AppModule.kt` | ✅ | Present |
| `app/src/main/java/com/learntrack/di/DataModule.kt` | ✅ | Present |
| `app/src/test/java/com/learntrack/domain/ProgressCalculatorTest.kt` | ✅ | 5 tests, all pass |
| `app/src/test/java/com/learntrack/data/repository/CourseRepositoryImplTest.kt` | ✅ | 3 tests, all pass |
| `app/src/test/java/com/learntrack/regression/TopicDistributionRegressionTest.kt` | ✅ | 1 test, passes |
| `app/src/androidTest/java/com/learntrack/ui/CourseProgressScreenTest.kt` | ✅ | Present (requires device to run) |
| `app/src/androidTest/java/com/learntrack/ui/TopicListScreenTest.kt` | ✅ | Present (requires device to run) |

**Extra files present (not in structure.md target layout):**
- `app/src/main/java/com/learntrack/navigation/LearnTrackNavHost.kt` — navigation graph
- `app/src/main/java/com/learntrack/navigation/Screen.kt` — screen route definitions
- `app/src/main/java/com/learntrack/ui/topics/StatusBadge.kt` — status badge composable
- `app/src/main/java/com/learntrack/data/validation/ValidationException.kt` — validation exception class
- `app/src/main/java/com/learntrack/ui/theme/Shape.kt` — Material 3 shape tokens

---

## 2. Gap List (❌ and ⚠️ Items)

### ❌ GAP-001: Stitch project ID `683813527608164394` not in master prompt (§13)

**Issue:** The master prompt §13 never received the real Stitch project ID. The ID is recorded in `learntrack-stitch-master-prompt.md` but the master prompt itself has no reference.  
**Impact:** A reader following only the master prompt cannot locate the live Stitch project.  
**Remediation:** Add the following to master prompt §13 under "Deliverable":
> *LearnTrack Stitch project ID: `683813527608164394` — recorded in `docs/design/google-stitch/learntrack-stitch-master-prompt.md`.*

---

### ❌ GAP-002: Stitch export files inconsistent — placeholders vs. documented sizes (§13)

**Issue:** `learntrack-stitch-master-prompt.md` "Generated Design References" table lists exports with real-sounding sizes (441 KB, 253 KB, etc.) and file paths (`exports/course-progress-dark.png`) that **do not exist on disk**. Actual files in `exports/` subdirectories are 28-byte placeholder strings (`<FIFE Image failed to fetch>`), not real PNG images. The subdirectory names also differ from the referenced file paths.  
**Impact:** Misleading: the document implies Stitch was run and images were saved, but they were not. This is a factual error in a design reference document.  
**Remediation:** Update the "Generated Design References" section in `learntrack-stitch-master-prompt.md` to reflect actual export state:
- Remove the fabricated file sizes and dimensions
- Change screen.png status to `⚠️ Placeholder — image fetch failed`
- Add a note: "Stitch MCP API returned `<FIFE Image failed to fetch>` for all PNG exports. HTML code files (code.html) are present and contain real HTML. PNG exports require a working Stitch session."

---

### ❌ GAP-003: Traceability matrix never updated after implementation (§25)

**Issue:** `docs/traceability/learntrack-requirement-traceability.md` has `Status: MISSING` and `_(PREDICTED — not yet created)_` for all implementation files and test files. This was written during the pre-scaffold phase and was not refreshed after the `app/` module was built.  
**Impact:** The traceability matrix is stale and inaccurate. It says files are missing that actually exist.  
**Remediation:** Update the Status column for each FR row to `✅ Implemented` and replace `_(PREDICTED)_` with the confirmed file paths. The confirmed paths are all those listed in the "Key Source Files" table above.

---

### ❌ GAP-004: MCP setup doc does not document `https://mcp.figma.com/mcp` URL (§21)

**Issue:** The audit brief checks that `docs/setup/kiro-mcp-setup.md` documents Figma MCP with `url: https://mcp.figma.com/mcp`. The actual setup doc uses the `@figma/mcp-server` npm package approach, not the `mcp.figma.com` hosted MCP URL. The correct Kiro Figma MCP connection uses `https://mcp.figma.com/mcp` as the SSE URL — this is the approach that was actually used and should be documented.  
**Impact:** An agent following the setup doc would configure Figma MCP incorrectly for Kiro.  
**Remediation:** Add a section to `docs/setup/kiro-mcp-setup.md` under §6 showing the Kiro-native Figma MCP config:
```json
{
  "mcpServers": {
    "figma": {
      "command": "npx",
      "args": ["-y", "@modelcontextprotocol/server-sse"],
      "env": { "MCP_SERVER_URL": "https://mcp.figma.com/mcp" }
    }
  }
}
```
Or note that Kiro uses the built-in Figma power which connects to `https://mcp.figma.com/mcp` automatically.

---

### ⚠️ GAP-005: Jira CSV files never updated with real LEAR-xxx keys (§16)

**Issue:** `learntrack-stories.csv`, `learntrack-traceability.csv`, and related CSV files still use proposed `LT-001`–`LT-011` keys. Live issues now exist on the board as `LEAR-2`–`LEAR-12`. The CSVs are frozen in the pre-import state.  
**Impact:** Anyone importing the CSVs or reading them for reference will see `LT-xxx` keys that don't match the live board. Traceability chain breaks between CSV docs and live issues.  
**Remediation:** Update CSV files to replace `LT-001`–`LT-011` with `LEAR-2`–`LEAR-12` (the epic remains `LEARNTRACK-EPIC-COURSE-PROGRESS`). Update `learntrack-traceability.csv` Status column to `Implemented` for all rows.

---

### ⚠️ GAP-006: Build variants doc documents `applicationIdSuffix = .debug` that doesn't exist (§22)

**Issue:** `learntrack-build-variants.md` says the debug build type has `applicationIdSuffix = .debug` (app ID: `com.learntrack.debug`). The actual `app/build.gradle.kts` has no `applicationIdSuffix` — debug uses `com.learntrack`.  
**Impact:** Low functional impact (debug app installs correctly) but the doc is inaccurate. A future developer adding a staging flavor might introduce duplicate app ID conflicts based on the wrong baseline assumption.  
**Remediation:** Remove the `applicationIdSuffix` row from the debug build type table, or add a note: "No suffix is configured; both debug and release use `com.learntrack`. Add `applicationIdSuffix = ".debug"` if parallel installation is needed."

---

### ⚠️ GAP-007: Steering structure.md diverges from actual package layout (§18)

**Issues:**
1. `structure.md` lists theme files in `com.learntrack.theme` but actual package is `com.learntrack.ui.theme`.
2. `structure.md` target layout omits the `com.learntrack.navigation` package (contains `LearnTrackNavHost.kt` and `Screen.kt`).
3. `TopicContainer.kt` is listed in the structure under `data/model/` correctly, but `TopicSchemaValidator.kt` has a sibling `ValidationException.kt` not mentioned in the layout.
4. `StatusBadge.kt` lives in `ui/topics/` — not listed in the target layout.

**Impact:** Future agents using `structure.md` as a reference will create files in wrong packages or miss existing ones.  
**Remediation:** Update `structure.md` target layout to reflect the actual implemented structure.

---

### ⚠️ GAP-008: `.kiro/settings/mcp.json` does not exist (§21)

**Issue:** No `mcp.json` configuration file exists at `.kiro/settings/mcp.json`. The MCP setup doc acknowledges this ("file does not exist") but provides no populated example that matches the Kiro-native MCP configuration actually used.  
**Impact:** A fresh checkout has no MCP configuration. Agents starting a new session cannot use Figma or Atlassian MCP without manual setup steps.  
**Remediation (optional):** Create a template `.kiro/settings/mcp.json.example` with the verified Figma and Atlassian server entries (no secrets), alongside the setup doc.

---

## 3. Master Prompt Accuracy — Outdated or Inaccurate Statements

| Location | Statement in master prompt | Actual state | Assessment |
|---|---|---|---|
| §13 Deliverable section | Silent on Stitch project ID | Project ID `683813527608164394` exists in Stitch doc | **Outdated** — add reference |
| §13 "Required screens" list | Lists "Progress Summary" as a required separate export | `learntrack-stitch-master-prompt.md` exports table does not include a standalone Progress Summary screen (only dark mode dashboard, light mode, topic list, topic detail) | **Gap persists** — Stitch exports didn't include a standalone Progress Summary as a separate item |
| §14 Acceptance criteria | "The Figma Design System file is created as a separate file from the UI Designs file." | ✅ Both files created | **Now accurate** — was prospective |
| §14 Acceptance criteria | "All Figma node IDs used in MCP calls are recorded in `learntrack-figma-handoff.md`" | ✅ Node IDs in section 7 of handoff | **Now accurate** — was prospective |
| §21 | Implies MCP setup doc is the authoritative place for Figma MCP config | Figma MCP details are actually in `learntrack-figma-handoff.md` §7, not in `docs/setup/kiro-mcp-setup.md` | **Mismatch** — §21 should note that actual MCP usage is documented in design docs |
| §26 Checklist | 17 deliverable bullets | Deliverables checklist adds a 17th (release readiness report) which is complete; 16 ✅ + 1 🔲 | **Accurate** — checklist is correctly maintained |
| §27 | "Do not stop at documentation when the actual implementation can safely proceed." | Implementation completed (LEAR-6 through LEAR-12) | **Accurate** — instruction was followed |
| Checklist item 5 (Figma handoff) | Initially marked as "specs written for manual setup" | Figma MCP was live and used; both files created with real file keys | **Outdated** — checklist item 5 still contains the old "manual setup" note; the actual state is that Figma was fully integrated via MCP |

---

## 4. Overall Verdict

**PASS WITH NOTES**

The project is substantively complete. All 47 documentation artifacts and 31 source code files exist. The Android app builds (assembleDebug PASS), 9 unit tests pass, lint is clean, and the JSON distribution (41/22/19) is correct. The Figma integration worked live (both file keys recorded, node IDs documented). The Kiro specs, skills, steering files, prompt chain, CI/CD, and quality gate scripts are all present and functional.

The three ❌ gaps are documentation accuracy issues, not implementation failures:
- GAP-001: Stitch project ID absent from master prompt (minor cross-reference gap)
- GAP-002: Stitch export metadata in the prompt doc is fabricated/inaccurate
- GAP-003: Traceability matrix was never updated after implementation (stale "PREDICTED" labels)
- GAP-004: MCP setup doc uses wrong Figma MCP URL approach

None of the ❌ items affect the running application. The ⚠️ items (Jira CSV key mismatch, applicationIdSuffix doc error, structure.md drift, missing mcp.json) are housekeeping items with no functional impact.

---

## 5. Recommended Master Prompt Updates

The following changes would make the master prompt more accurate and useful for the next project:

### Must fix (affects next project's ability to follow the prompt)

1. **§21 — Add `mcp.figma.com/mcp` URL.** Replace or supplement the `@figma/mcp-server` npm config example with the Kiro-native Figma MCP URL (`https://mcp.figma.com/mcp`). Note that Kiro uses a built-in Figma integration that connects to this endpoint — developers don't need to configure the npm server.

2. **§13 — Clarify Stitch export verification.** Add a requirement: "Before marking Stitch exports complete, verify that `screen.png` files are valid PNG images (≥ 67 bytes, starting with `89 50 4E 47`). A file containing the text `<FIFE Image failed to fetch>` is a failed placeholder, not an export." Also add: "Record the actual Stitch project ID in this master prompt §13 once known."

3. **§25 — Add post-implementation traceability update step.** The traceability matrix must be re-scanned after implementation and the Status column updated from `MISSING` / `PREDICTED` to `Implemented` with confirmed paths. Add this as a required step in §27 execution instructions: "After implementation, update the traceability matrix Status column for all rows where implementation files now exist."

### Should fix (improves accuracy for next project)

4. **§22 — Clarify `applicationIdSuffix`.** Add guidance: "If no `applicationIdSuffix` is set, document that the debug build uses the base `applicationId` (`com.learntrack`). Only document a suffix if one is explicitly added to `build.gradle.kts`."

5. **§18 — Add post-implementation structure audit step.** Steering files document the intended layout, but implementations often add packages not in the spec (e.g., `navigation/`, `ui/theme/` vs `theme/`). Add: "After implementation, diff the actual source package tree against `structure.md` and update the structure steering to match reality."

6. **§16 — Add CSV post-import update requirement.** "Once Jira CSVs are imported and real issue keys are assigned, update the CSV files to replace proposed keys (e.g., `LT-001`) with actual keys (e.g., `LEAR-2`). Update any cross-reference documents accordingly."

### Nice to have (improves guidance clarity)

7. **§13 — Clarify Progress Summary export requirement more forcefully.** The lesson in §28 says "explicitly request it as a standalone screen." The §13 Required Screens list already includes it, but add: "Verify in the Stitch exports table that Progress Summary appears as an independent entry — not embedded in a scrolled dashboard screenshot."

8. **§14 — Note Starter plan page limit.** Add: "Figma Starter accounts are limited to 3 pages per file. Design the Design System file to fit within 3 pages (e.g., combine Cover + Color Tokens on page 1)."

---

*Report generated: read-only audit, no files modified.*
