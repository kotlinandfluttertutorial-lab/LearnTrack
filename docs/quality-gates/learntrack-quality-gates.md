# LearnTrack — Quality Gates

**Phase:** Phase Group 4 — MCP Setup + Build Variants + Quality Gates  
**Platform:** Android (Gradle with Kotlin DSL)  
**Status:** Defined — scripts exist; Gradle-dependent gates are skipped until Android scaffold is created  
**Last Updated:** Phase Group 4

---

## 1. Purpose and Overview

Quality gates are automated checks that must pass before code is merged into the `main` branch or shipped as a release build. They protect the following invariants:

1. **Data integrity** — `learntrack_topics.json` always contains exactly 41 topics (22 completed, 19 not-started) in valid JSON format.
2. **Code correctness** — all unit tests pass; no regressions in progress calculation or JSON parsing.
3. **Code quality** — zero Android Lint errors; no obvious secrets committed.
4. **Build health** — the debug APK compiles cleanly after every change.

Quality gates run in two contexts:
- **Locally** — via `scripts/check-before-push.ps1` (Windows) or `scripts/check-before-push.sh` (macOS/Linux), before `git push`
- **In CI** — via GitHub Actions on every push and pull request to `main`

---

## 2. Gate Inventory

### QG-001 — JSON Syntax Validation

| Property | Value |
|---|---|
| **Gate ID** | QG-001 |
| **Name** | JSON Syntax Validation |
| **Check type** | Static validation |
| **Local command (PowerShell)** | `Get-Content -Raw <path> \| Test-Json` |
| **Local command (Bash / jq)** | `jq empty app/src/main/assets/learntrack_topics.json` |
| **Local command (Bash / python fallback)** | `python3 -c "import json,sys; json.load(open('<path>'))"` |
| **Pass criteria** | File parses as valid JSON with no syntax errors |
| **Fail criteria** | Any JSON syntax error, trailing comma, or non-UTF-8 content |
| **Blocking** | Yes — must pass before count validation runs |
| **File checked** | `app/src/main/assets/learntrack_topics.json` |
| **Requires scaffold** | No |

---

### QG-002 — Topic Distribution Regression

| Property | Value |
|---|---|
| **Gate ID** | QG-002 |
| **Name** | Topic Distribution Regression |
| **Check type** | Data integrity assertion |
| **Local command (PowerShell)** | Run `check-before-push.ps1` (includes this check) |
| **Local command (Bash)** | Run `check-before-push.sh` (includes this check) |
| **CI command** | `./gradlew testDebugUnitTest` (runs `TopicDistributionRegressionTest`) |
| **Pass criteria** | Exactly 41 topics; 22 with `status = "completed"`; 19 with `status = "not_started"`; zero unrecognized status values |
| **Fail criteria** | Any count deviation; any unrecognized status value |
| **Blocking** | Yes |
| **Authoritative values** | total=41, completed=22, not_started=19, percentage≈53.7% |
| **Requires scaffold** | Partially — script-level check works without scaffold; JUnit regression test requires scaffold |

The authoritative values are never hard-coded in application logic. They appear only in test assertions, comments, and this document.

---

### QG-003 — Unit Tests

| Property | Value |
|---|---|
| **Gate ID** | QG-003 |
| **Name** | Unit Tests |
| **Check type** | Automated test execution |
| **Local command** | `.\gradlew.bat testDebugUnitTest` (Windows) / `./gradlew testDebugUnitTest` (macOS/Linux) |
| **CI command** | `./gradlew testDebugUnitTest` |
| **Pass criteria** | All unit tests exit with code 0; zero test failures |
| **Fail criteria** | Any test failure or error |
| **Blocking** | Yes |
| **Test locations** | `app/src/test/java/com/learntrack/` |
| **Key test classes** | `ProgressCalculatorTest`, `CourseRepositoryImplTest`, `TopicDistributionRegressionTest` |
| **Requires scaffold** | Yes — skipped automatically in scripts if `gradlew`/`gradlew.bat` not found |

---

### QG-004 — Integration Test: JSON Schema

| Property | Value |
|---|---|
| **Gate ID** | QG-004 |
| **Name** | JSON Schema Integration Test |
| **Check type** | Integration test (reads actual JSON asset) |
| **Local command** | `.\gradlew.bat testDebugUnitTest` (covers integration tests in `app/src/test/`) |
| **CI command** | `./gradlew testDebugUnitTest` |
| **Pass criteria** | Schema validation test passes: 41 topics, valid IDs, valid names, valid status values |
| **Fail criteria** | Missing required fields, duplicate IDs, invalid status values |
| **Blocking** | Yes |
| **Test location** | `app/src/test/java/com/learntrack/data/repository/CourseRepositoryImplTest.kt` |
| **Requires scaffold** | Yes |

---

### QG-005 — UI Tests (Instrumented)

| Property | Value |
|---|---|
| **Gate ID** | QG-005 |
| **Name** | UI Tests (Compose Instrumented) |
| **Check type** | Compose UI instrumented test |
| **Local command** | `.\gradlew.bat connectedDebugAndroidTest` (requires emulator or device) |
| **CI command** | `./gradlew connectedDebugAndroidTest` (runs on emulator in CI) |
| **Pass criteria** | All Compose UI tests pass |
| **Fail criteria** | Any UI test failure |
| **Blocking** | Yes (in CI); non-blocking for pre-push gate (requires device) |
| **Test locations** | `app/src/androidTest/java/com/learntrack/ui/` |
| **Key test classes** | `CourseProgressScreenTest`, `TopicListScreenTest` |
| **Requires scaffold** | Yes; also requires connected device or emulator |

**Note:** UI tests are excluded from the pre-push scripts because they require an emulator. They run in CI on every PR via GitHub Actions with an Android emulator action.

---

### QG-006 — Lint

| Property | Value |
|---|---|
| **Gate ID** | QG-006 |
| **Name** | Android Lint |
| **Check type** | Static analysis |
| **Local command** | `.\gradlew.bat lintDebug` (Windows) / `./gradlew lintDebug` (macOS/Linux) |
| **CI command** | `./gradlew lintDebug` |
| **Pass criteria** | Zero lint errors (warnings are allowed but should be reviewed) |
| **Fail criteria** | Any lint error (`severity="error"`) |
| **Blocking** | Yes |
| **Report location** | `app/build/reports/lint-results-debug.html` |
| **Suppression policy** | Suppress only with `@SuppressLint("RuleName")` and a documented justification in a comment |
| **Requires scaffold** | Yes |

---

### QG-007 — Debug Build

| Property | Value |
|---|---|
| **Gate ID** | QG-007 |
| **Name** | Debug Build |
| **Check type** | Compilation / assembly |
| **Local command** | `.\gradlew.bat assembleDebug` (Windows) / `./gradlew assembleDebug` (macOS/Linux) |
| **CI command** | `./gradlew assembleDebug` |
| **Pass criteria** | Build completes with exit code 0; APK produced at `app/build/outputs/apk/debug/app-debug.apk` |
| **Fail criteria** | Any compilation error or resource error |
| **Blocking** | Yes |
| **Requires scaffold** | Yes |

---

### QG-008 — Secret Detection

| Property | Value |
|---|---|
| **Gate ID** | QG-008 |
| **Name** | Secret Detection |
| **Check type** | Pattern-based static scan |
| **Local command** | Included in `check-before-push.ps1` and `check-before-push.sh` |
| **CI command** | Included in pre-push scripts; optionally replace with `gitleaks` or GitHub Secret Scanning |
| **Pass criteria** | No obvious secret patterns found in staged files |
| **Fail criteria** | Patterns matching API keys, private keys, tokens, passwords found in staged source files |
| **Blocking** | Yes |
| **Scanned files** | All staged non-binary files |
| **Excluded files** | `.png`, `.jpg`, `.jpeg`, `.gif`, `.webp`, `.apk`, `.aab`, `.keystore`, `.jks` |
| **Requires scaffold** | No |

**Limitations:** The built-in pattern scan is a lightweight heuristic. For production-grade secret detection, use [gitleaks](https://github.com/gitleaks/gitleaks) or enable GitHub Secret Scanning on the repository.

---

## 3. Gate Execution Order

Gates run in dependency order. The pre-push scripts stop and accumulate failures but continue checking independent gates:

```
QG-001 JSON Syntax Validation
    │
    └─► QG-002 Topic Distribution Regression
    │
QG-008 Secret Detection (independent)
    │
QG-004 Git Working Tree Status (informational)
    │
QG-003 Unit Tests (requires scaffold)
    │
    └─► QG-004 JSON Schema Integration Test (covered by unit tests)
    │
QG-006 Lint (requires scaffold)
    │
QG-007 Debug Build (requires scaffold)
    │
QG-005 UI Tests (CI only — requires emulator)
```

---

## 4. Running Locally

### Windows (PowerShell)

```powershell
# From the repository root
.\scripts\check-before-push.ps1
```

### macOS/Linux (Bash)

```bash
# From the repository root
bash scripts/check-before-push.sh

# Or, after chmod +x:
./scripts/check-before-push.sh
```

### Running Individual Gradle Checks

```powershell
# Unit tests only
.\gradlew.bat testDebugUnitTest

# Lint only
.\gradlew.bat lintDebug

# Build only
.\gradlew.bat assembleDebug

# UI tests (requires emulator)
.\gradlew.bat connectedDebugAndroidTest
```

### Pre-Scaffold Checks

Before the Android scaffold exists, only the following gates run:
- **QG-001** JSON Syntax Validation
- **QG-002** Topic Distribution (script-level count check)
- **QG-008** Secret Detection
- Working tree status (informational)

Gradle-dependent gates (QG-003, QG-005, QG-006, QG-007) are automatically skipped with a `[SKIP]` message.

---

## 5. CI/CD Integration (GitHub Actions)

Create `.github/workflows/ci.yml` with the following structure (to be implemented in the CI/CD phase):

```yaml
name: LearnTrack CI

on:
  push:
    branches: [main]
  pull_request:
    branches: [main]

jobs:
  quality-gate:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4

      - name: Set up JDK 17
        uses: actions/setup-java@v4
        with:
          java-version: '17'
          distribution: 'temurin'

      - name: Grant execute permission for gradlew
        run: chmod +x gradlew

      - name: QG-001/002 JSON validation and topic count
        run: bash scripts/check-before-push.sh

      - name: QG-003/004 Unit tests
        run: ./gradlew testDebugUnitTest

      - name: QG-006 Lint
        run: ./gradlew lintDebug

      - name: QG-007 Debug build
        run: ./gradlew assembleDebug

  ui-tests:
    runs-on: ubuntu-latest
    needs: quality-gate
    steps:
      - uses: actions/checkout@v4

      - name: Set up JDK 17
        uses: actions/setup-java@v4
        with:
          java-version: '17'
          distribution: 'temurin'

      - name: Grant execute permission for gradlew
        run: chmod +x gradlew

      - name: QG-005 UI tests on emulator
        uses: reactivecircus/android-emulator-runner@v2
        with:
          api-level: 34
          script: ./gradlew connectedDebugAndroidTest
```

---

## 6. Bypassing a Gate

Gates should never be bypassed silently. The only acceptable bypass is `--no-verify` on `git push` with explicit user permission and a documented reason.

| Gate | Bypass Acceptable? | Condition |
|---|---|---|
| QG-001 JSON Syntax | No | Never bypass — malformed JSON breaks the app at runtime |
| QG-002 Distribution | No | Never bypass — distribution regression is a product-level defect |
| QG-003 Unit Tests | Only with explicit approval | Temporary CI infra issue; must be resolved before next PR |
| QG-005 UI Tests | Only with explicit approval | Emulator infra issue; must be resolved before release |
| QG-006 Lint | Only with explicit approval | Lint config issue; must not bypass for code quality issues |
| QG-007 Build | No | A failing build cannot be pushed |
| QG-008 Secret Scan | No | A false positive must be reviewed and documented, not bypassed |

To bypass pre-push scripts (local only):
```bash
git push --no-verify
```
This skips local hooks but does not bypass CI. CI gates still run on push.

---

## 7. Test Reports and Artifacts

| Gate | Report Location | Format |
|---|---|---|
| QG-003 Unit tests | `app/build/reports/tests/testDebugUnitTest/` | HTML |
| QG-005 UI tests | `app/build/reports/androidTests/connected/` | HTML |
| QG-006 Lint | `app/build/reports/lint-results-debug.html` | HTML |
| QG-007 Build | `app/build/outputs/apk/debug/app-debug.apk` | APK |

---

## References

- Pre-push PowerShell script: `scripts/check-before-push.ps1`
- Pre-push Bash script: `scripts/check-before-push.sh`
- Build variants: `docs/build-variants/learntrack-build-variants.md`
- Environment configuration: `docs/build-variants/learntrack-environment-configuration.md`
- Architecture (testing boundaries): `docs/architecture/learntrack-architecture.md` § 9
- Master prompt § 23: Pre-Push Quality Gates
- JSON asset: `app/src/main/assets/learntrack_topics.json`
