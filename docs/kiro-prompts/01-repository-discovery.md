# Prompt 01 — Repository Discovery

## Prerequisites

- Access to the LearnTrack workspace root: `j:\Android\AndroidStudioProjects\Kiro\LearnTrack`
- Read access to all files and directories in the workspace
- No prior knowledge of the repository assumed — treat this as a fresh inspection

## Specific Task

Perform a complete, read-only inspection of the LearnTrack repository. Identify the platform, technology stack, directory structure, and the location and status of `learntrack_topics.json`. Record all findings in the discovery report.

**Do not create, modify, or delete any source file during this prompt. The only permitted write operation is creating or updating the discovery report.**

Steps:

1. List the full directory tree from the workspace root, including hidden directories.
2. Read `.gitignore` and identify the platform (Android, web, other) from its patterns.
3. Search for `learntrack_topics.json` at all likely paths. If found, read and parse it; count total topics, completed topics, and not-started topics; compare to the authoritative distribution (41/22/19).
4. Read `docs/learntrack-ai-sdlc-master-prompt.md` for context on expected platform and data model.
5. Inspect `.kiro/` for existing steering files, specs, and skills.
6. Inspect build files (`build.gradle.kts`, `settings.gradle.kts`, `gradle/libs.versions.toml`) if they exist.
7. Inspect source code structure if it exists (package names, architecture pattern).
8. Inspect CI/CD configuration (`.github/workflows/`, etc.) if it exists.
9. Identify all gaps: anything required by the master prompt that does not yet exist. Classify each as Blocking, Non-blocking, or Low.
10. Write findings to `docs/discovery/learntrack-discovery-report.md`.

## Allowed Scope

- Read any file in the repository.
- Write to `docs/discovery/learntrack-discovery-report.md` only.
- Do not modify source code, JSON data, build files, or Kiro configuration.

## Required Outputs

- `docs/discovery/learntrack-discovery-report.md` containing:
  - Platform determination with supporting evidence
  - Full technology stack table (value, confidence, basis for each dimension)
  - Complete directory tree listing
  - `learntrack_topics.json` status (path, schema, counts, or "not found")
  - Existing Kiro configuration inventory
  - CI/CD status
  - Gaps list with severity classification
  - Recommended conventions based on discovered platform

## Acceptance Criteria

- [ ] Discovery report exists at `docs/discovery/learntrack-discovery-report.md`.
- [ ] Platform is identified with cited evidence (not assumed).
- [ ] Directory tree is complete (not partial).
- [ ] `learntrack_topics.json` status is recorded as found (with path and counts) or not found.
- [ ] Every `.kiro/` file is listed as present or absent.
- [ ] All blocking gaps are identified.
- [ ] No source file was created, modified, or deleted during execution.

## Stop Conditions

- Stop and report if the workspace root path is inaccessible.
- Stop and report if `docs/learntrack-ai-sdlc-master-prompt.md` cannot be read (it is the primary context document).
- Do not proceed to any implementation task — discovery is the sole deliverable of this prompt.
