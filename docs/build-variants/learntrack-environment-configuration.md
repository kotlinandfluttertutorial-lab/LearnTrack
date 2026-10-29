# LearnTrack — Environment Configuration

**Phase:** Phase Group 4 — MCP Setup + Build Variants + Quality Gates  
**Platform:** Android (Gradle with Kotlin DSL)  
**Status:** Pre-scaffold — describes the target configuration for when the Android project scaffold is created  
**Last Updated:** Phase Group 4

---

## 1. Overview

LearnTrack is a fully offline Android application. All course data comes from a bundled JSON asset (`learntrack_topics.json`). There is no remote API, database server, or cloud sync in this phase. As a result, environment configuration is minimal — the main axes are:

- **Build type** (`debug` vs. `release`) — controls logging verbosity, minification, and signing
- **Local machine setup** — SDK path and signing credentials in `local.properties`

This document covers local development setup, environment variable names, logging configuration per build type, and deployment restrictions.

---

## 2. Local Development Environment Setup

### Prerequisites

| Tool | Minimum Version | Notes |
|---|---|---|
| Android Studio | Latest stable (Hedgehog or newer) | Installs SDK, emulator, Gradle wrapper |
| JDK | 17 | Bundled with Android Studio; do not use a system JDK unless explicitly configured |
| Android SDK | API 35 (compileSdk) | Install via SDK Manager |
| Android SDK Platform Tools | Latest | Required for `adb` |
| Gradle | Via wrapper (`gradlew`) | Do not install Gradle globally; use the wrapper |
| Git | 2.x | For version control and pre-push hooks |
| PowerShell | 5.1 or 7+ | Required for `scripts/check-before-push.ps1` |
| Node.js | 18+ (optional) | Only needed for MCP server setup |

### Setup Steps

1. **Clone the repository:**
   ```powershell
   git clone https://github.com/kotlinandfluttertutorial-lab/LearnTrack.git
   cd LearnTrack
   ```

2. **Create `local.properties`** in the project root (this file is git-ignored):
   ```properties
   # Android SDK path — update to match your machine
   sdk.dir=C\:\\Users\\YourName\\AppData\\Local\\Android\\Sdk
   ```
   Android Studio creates this file automatically when you open the project for the first time.

3. **Open in Android Studio** and let Gradle sync resolve all dependencies.

4. **Verify the setup:**
   ```powershell
   .\gradlew.bat assembleDebug
   ```
   A successful build confirms the environment is correctly configured.

---

## 3. Environment Variable Names

LearnTrack does not currently use environment variables at runtime — there is no remote API or cloud service. The following environment variables are relevant for CI/CD and local tooling:

| Variable Name | Purpose | Used By | Where to Set |
|---|---|---|---|
| `ANDROID_SDK_ROOT` | Android SDK location | Gradle, CI | System environment or `local.properties` |
| `ANDROID_HOME` | Legacy Android SDK path (fallback) | Older Gradle plugins | System environment (if needed) |
| `GITHUB_TOKEN` | GitHub API access for CI/CD | GitHub Actions, MCP GitHub server | CI secrets; local `~/.bashrc` or PowerShell profile |
| `FIGMA_API_TOKEN` | Figma read access for design work | Figma MCP server | Local environment only; never CI unless needed |
| `KEYSTORE_PATH` | Path to release signing keystore | `app/build.gradle.kts` (release signing) | `local.properties` (local); CI secrets (CI) |
| `KEYSTORE_PASSWORD` | Release keystore password | `app/build.gradle.kts` (release signing) | `local.properties` (local); CI secrets (CI) |
| `KEY_ALIAS` | Release key alias | `app/build.gradle.kts` (release signing) | `local.properties` (local); CI secrets (CI) |
| `KEY_PASSWORD` | Release key password | `app/build.gradle.kts` (release signing) | `local.properties` (local); CI secrets (CI) |

**No actual values for any of these variables should appear in any committed file.**

---

## 4. API Endpoint Selection

**Not applicable for this phase.**

LearnTrack has no remote API. All data is read from the bundled asset `app/src/main/assets/learntrack_topics.json`. There are no endpoints to configure.

If a remote API is introduced in a future phase, the pattern would be:

```kotlin
// hypothetical future use — BuildConfig-based endpoint selection
object ApiConfig {
    val baseUrl: String = BuildConfig.API_BASE_URL
}
```

And in `app/build.gradle.kts`:
```kotlin
buildTypes {
    debug {
        buildConfigField("String", "API_BASE_URL", "\"https://staging.api.learntrack.example.com\"")
    }
    release {
        buildConfigField("String", "API_BASE_URL", "\"https://api.learntrack.example.com\"")
    }
}
```

---

## 5. Logging and Debug Settings Per Environment

LearnTrack uses `android.util.Log` with tag `"LearnTrack"`. Verbose log statements are gated behind `BuildConfig.DEBUG`.

### `debug` Build Type

- `BuildConfig.DEBUG = true`
- Verbose logging enabled — data-load events, JSON parsing steps, progress calculations logged at `Log.d("LearnTrack", ...)`
- Error logging: `Log.e("LearnTrack", message, exception)` — exception message logged, stack trace NOT shown in UI
- Raw JSON content must never be logged even in debug builds (security practice)

### `release` Build Type

- `BuildConfig.DEBUG = false`
- All `if (BuildConfig.DEBUG)` log blocks are excluded by R8/ProGuard
- Error logging: `Log.e` calls for errors remain, but verbose debug blocks are stripped
- No exception stack traces exposed to the user; the UI shows a friendly error message instead

### Log Guard Pattern

```kotlin
// Safe log guard — verbose block excluded from release by R8
if (BuildConfig.DEBUG) {
    Log.d("LearnTrack", "Topics loaded: ${topics.size}")
}

// Error log — present in both debug and release
Log.e("LearnTrack", "Failed to load topics: ${e.message}")
```

---

## 6. Data Source and Persistence Per Environment

| Environment | Data Source | Persistence | Notes |
|---|---|---|---|
| Local debug | `app/src/main/assets/learntrack_topics.json` | Read-only asset | Same file for all environments |
| Local release | `app/src/main/assets/learntrack_topics.json` | Read-only asset | Bundled in APK; cannot change at runtime |
| CI debug | `app/src/main/assets/learntrack_topics.json` | Read-only asset | Same file; no external data source |

**There is no separate staging or production data source.** The single JSON file is bundled into the APK at build time for all variants. Data changes require a new build and deployment.

No database (Room), network, or SharedPreferences are used in this phase. The architecture doc (§ 6) notes that Room would be introduced only if mutable runtime topic status becomes a requirement.

---

## 7. Test Execution Requirements Per Environment

### Local Development (debug)

| Test Type | Command | Requires Device/Emulator | CI Equivalent |
|---|---|---|---|
| Unit tests | `.\gradlew.bat testDebugUnitTest` | No | `./gradlew testDebugUnitTest` |
| Lint | `.\gradlew.bat lintDebug` | No | `./gradlew lintDebug` |
| Build verification | `.\gradlew.bat assembleDebug` | No | `./gradlew assembleDebug` |
| UI tests (instrumented) | `.\gradlew.bat connectedDebugAndroidTest` | Yes | Emulator in CI |

**Pre-push gate:** The `scripts/check-before-push.ps1` script runs JSON validation, unit tests, lint, and the debug build. UI tests are excluded from the pre-push gate because they require a connected device.

### CI (GitHub Actions)

- Unit tests and lint run on every push and PR (no device needed)
- Instrumented UI tests run on an Android emulator (API 34 or 35) via `connectedDebugAndroidTest`
- The release build is verified on the `main` branch only

### Release Build Verification

Before shipping a release:
1. Run `.\gradlew.bat testReleaseUnitTest` — unit tests against the release variant
2. Run `.\gradlew.bat lintRelease` — lint against the release variant
3. Run `.\gradlew.bat assembleRelease` — verify the minified build succeeds
4. Test on a physical device with the signed APK

---

## 8. Signing Configuration

### Debug Signing

Android Studio automatically uses a debug keystore located at:
- Windows: `%USERPROFILE%\.android\debug.keystore`
- macOS/Linux: `~/.android/debug.keystore`

No configuration needed for debug builds.

### Release Signing

Release signing requires a keystore that **must never be committed to the repository**.

Configuration approach in `app/build.gradle.kts` (reads from `local.properties`):

```kotlin
import java.util.Properties

val localProps = Properties().also { props ->
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) props.load(localPropertiesFile.inputStream())
}

android {
    signingConfigs {
        create("release") {
            storeFile = localProps["KEYSTORE_PATH"]?.let { file(it.toString()) }
            storePassword = localProps["KEYSTORE_PASSWORD"]?.toString()
            keyAlias = localProps["KEY_ALIAS"]?.toString()
            keyPassword = localProps["KEY_PASSWORD"]?.toString()
        }
    }
    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
        }
    }
}
```

Add to `local.properties` (never commit):
```properties
KEYSTORE_PATH=/path/to/learntrack-release.keystore
KEYSTORE_PASSWORD=your-keystore-password
KEY_ALIAS=learntrack-release
KEY_PASSWORD=your-key-password
```

In CI (GitHub Actions), set `KEYSTORE_PATH`, `KEYSTORE_PASSWORD`, `KEY_ALIAS`, and `KEY_PASSWORD` as repository secrets.

---

## 9. Deployment Restrictions

| Action | Restriction | Reason |
|---|---|---|
| Pushing to `main` branch | Requires passing CI checks (unit tests + lint + build) | Prevents regressions on the primary branch |
| Deploying APK to Play Store | Requires explicit user approval | Production deployment must not be automated without oversight |
| Modifying `learntrack_topics.json` | Requires JSON validation + count assertion pass | Prevents data integrity regressions |
| Embedding secrets in source | **Prohibited** | Security violation |
| Writing to `assets/` at runtime | **Prohibited** | Assets are read-only by design |
| Skipping pre-push hooks | Only with explicit user permission (`--no-verify`) | Quality gates must not be bypassed silently |

---

## 10. `local.properties` Reference

`local.properties` is git-ignored and machine-specific. It should contain:

```properties
# Required: Android SDK path
sdk.dir=C\:\\Users\\YourName\\AppData\\Local\\Android\\Sdk

# Optional: Release signing (only needed for release builds)
KEYSTORE_PATH=/path/to/learntrack-release.keystore
KEYSTORE_PASSWORD=
KEY_ALIAS=learntrack-release
KEY_PASSWORD=
```

**Never add `local.properties` to version control.** The `.gitignore` already excludes it.

---

## References

- Discovery report: `docs/discovery/learntrack-discovery-report.md`
- Architecture doc: `docs/architecture/learntrack-architecture.md`
- Build variants: `docs/build-variants/learntrack-build-variants.md`
- Quality gates: `docs/quality-gates/learntrack-quality-gates.md`
- MCP setup: `docs/setup/kiro-mcp-setup.md`
- Master prompt § 22: Build Variants and Environments
