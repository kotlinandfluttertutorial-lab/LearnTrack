---
name: learntrack-repository-analysis
description: Analyze the LearnTrack repository structure, identify the authoritative data source and architecture conventions, and produce a discovery report without modifying any files prematurely.
version: 1.0.0
tags: [learntrack, discovery, analysis, android]
---

## Purpose

Perform a read-only inspection of the LearnTrack repository. Identify the platform, technology stack, data source location, existing Kiro configuration, and code conventions. Record all findings in the discovery report. Do not create, modify, or delete any source files during this skill.

---

## When to Use

- At the start of any new implementation phase to confirm the current state of the repository.
- Before creating or modifying any file that depends on the platform, package structure, or data-file location.
- When a downstream agent or phase needs to know what already exists vs. what must be created.
- When a gap or inconsistency is suspected between the documented architecture and actual files.

---

## Required Inputs

| Input | Source | Required? |
|---|---|---|
| Workspace root path | Workflow context | Yes |
| Existing discovery report (if any) | `docs/discovery/learntrack-discovery-report.md` | No — read if present, create if absent |
| Master prompt | `docs/learntrack-ai-sdlc-master-prompt.md` | Yes |

---

## Step-by-Step Procedure

1. **Read the master prompt** (`docs/learntrack-ai-sdlc-master-prompt.md`) to understand the expected platform and data model.

2. **List the full directory tree** from the workspace root. Record every file and directory present, including hidden directories (`.kiro/`, `.git/`, `.github/`). Do not modify anything.

3. **Determine the platform** by inspecting `.gitignore`, directory names, and any build files present. Confirm or refute the Android hypothesis.

4. **Locate `learntrack_topics.json`**: Search for this file at all likely paths:
   - `app/src/main/assets/learntrack_topics.json`
   - Root of the repository
   - `assets/` at the root
   - Any other location found during the directory listing
   - Record the exact path if found, or "not found" if absent.

5. **Read `learntrack_topics.json`** if it exists:
   - Parse the JSON and record the schema (top-level keys, topic object fields).
   - Count total topics, completed topics, and not-started topics.
   - Compare against the authoritative distribution (41 total, 22 completed, 19 not-started).
   - Record any discrepancy.

6. **Inspect existing Kiro configuration** under `.kiro/`:
   - List all files in `.kiro/steering/`, `.kiro/specs/`, `.kiro/skills/`, `.kiro/settings/`.
   - Record what exists and what is absent.

7. **Inspect build files** if present:
   - `build.gradle.kts`, `settings.gradle.kts`, `gradle/libs.versions.toml`
   - Record pinned dependency versions and the confirmed technology stack.

8. **Inspect source code** if present:
   - Identify the root package name from `AndroidManifest.xml` or existing Kotlin files.
   - Note the architecture pattern in use (MVVM, MVI, etc.).
   - Note any deviations from the org-standard conventions.

9. **Inspect CI/CD** if present:
   - Check `.github/workflows/`, `Jenkinsfile`, `bitrise.yml`, etc.
   - Record what exists and what is absent.

10. **Record all gaps** — anything the master prompt requires that does not yet exist:
    - Missing scaffold, missing JSON, missing Kiro config, missing CI.
    - Classify each gap as Blocking, Non-blocking, or Low-severity.

11. **Write the discovery report** to `docs/discovery/learntrack-discovery-report.md`:
    - Platform determination and evidence.
    - Full technology stack table.
    - Full directory tree.
    - Kiro configuration status.
    - `learntrack_topics.json` status.
    - CI/CD status.
    - Gaps and decisions needed.
    - Recommended conventions.

---

## Constraints

- **Read-only.** Do not create, edit, or delete any file except the discovery report output.
- **No assumptions presented as facts.** Every claim must cite the file or pattern that supports it. Use "inferred" or "not confirmed" when source code evidence is absent.
- **Do not fabricate file contents.** If a file does not exist, record "not found" — never invent a schema or topic list.
- **Do not hard-code the stack.** If the `.gitignore` or source files suggest a different platform than Android, record that finding accurately.
- **Scope is read-only analysis.** Do not start implementation steps during this skill.

---

## Expected Outputs

| Output | Location | Description |
|---|---|---|
| Discovery report | `docs/discovery/learntrack-discovery-report.md` | Full findings as described in step 11 |
| Platform determination | Section 1 of report | Android / Web / Other, with evidence |
| Technology stack table | Section 2 of report | All stack dimensions, confidence, and basis |
| Directory tree | Section 3 of report | Complete listing |
| Kiro config status | Section 4 of report | What exists vs. what must be created |
| JSON status | Section 5 of report | Location, schema, counts, discrepancies |
| Gaps list | Section 8 of report | Blocking and non-blocking gaps |

---

## Verification Criteria

- [ ] Discovery report exists at `docs/discovery/learntrack-discovery-report.md`.
- [ ] Report states the platform with supporting evidence.
- [ ] Report includes a full directory tree (not a partial listing).
- [ ] Report records the exact path and status of `learntrack_topics.json` (found or not found).
- [ ] If JSON exists, report includes topic counts compared against 41/22/19.
- [ ] Report lists every `.kiro/` file that exists and every required file that is absent.
- [ ] Report identifies all blocking gaps.
- [ ] No source file was created, modified, or deleted during this skill execution.
