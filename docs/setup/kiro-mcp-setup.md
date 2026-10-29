# LearnTrack — Kiro MCP Setup Guide

**Phase:** Phase Group 4 — MCP Setup + Build Variants + Quality Gates  
**Status:** Pre-scaffold; no MCP servers are currently configured  
**Last Updated:** Phase Group 4

---

## 1. Current MCP Configuration

**`.kiro/settings/mcp.json` does not exist.**

The file was checked at `.kiro/settings/mcp.json` and is absent. No MCP servers are configured for this project. All MCP integration for LearnTrack must be set up from scratch.

---

## 2. What MCP Integrations Does LearnTrack Need?

Based on the actual LearnTrack workflows (repository discovery, design, Jira traceability, implementation, CI/CD), the following MCP servers are relevant:

| Server | Purpose | Required for | Priority |
|---|---|---|---|
| **GitHub** | Create issues, PRs, manage branches, push files | CI/CD, Jira-to-GitHub traceability, PR creation | High (if GitHub CI is used) |
| **Figma** | Read design specs and components for implementation | UI implementation against Figma designs | Medium |
| **Filesystem** | Read/write local project files during agentic tasks | Any file-editing agent task | Low (Kiro has built-in file tools) |
| **Context7** | Fetch current library documentation | Dependency lookups during scaffold/implementation | Optional |
| **Atlassian** | Create/update Jira issues from CSV | Jira work item management | Optional (CSV import is the fallback) |

**Important:** MCP configuration is optional for ordinary implementation and testing. All unit tests, lint checks, and Gradle builds run entirely locally without any MCP server.

---

## 3. Security Requirements

All MCP configuration must follow these rules, regardless of which servers are enabled:

- **Never commit tokens, passwords, API keys, or credentials** to any file in the repository.
- Use environment variable references (`${ENV_VAR_NAME}`) for all secret values.
- Use least-privilege access — request only the scopes actually needed.
- Prefer read-only access when write access is not required.
- `local.properties` is git-ignored — use it for local overrides that must not be committed.
- The `.kiro/settings/mcp.json` file itself should not contain secret values.

---

## 4. MCP Configuration File Location

When ready to configure MCP, create the file at:

```
.kiro/settings/mcp.json
```

The file uses the following structure (no actual tokens should appear here):

```json
{
  "mcpServers": {
    "server-name": {
      "command": "npx",
      "args": ["-y", "@package/server"],
      "env": {
        "API_KEY": "${ENV_VAR_NAME}"
      }
    }
  }
}
```

The `.kiro/settings/` directory is not listed in the `.gitignore` by default. If `mcp.json` will contain any values that reference secrets by name (even variable names), confirm it is either git-ignored or contains no sensitive information before committing.

---

## 5. GitHub MCP Setup

### When to Use

Use GitHub MCP when agentic tasks need to:
- Create or update pull requests programmatically
- Push files to branches
- Create or update GitHub Issues
- Read repository metadata, file contents, or PR status

### Required Scopes (Least Privilege)

For read-only repository access:
- `repo:status`, `read:repo_hook`, `read:org`

For creating PRs and issues (recommended for this project):
- `repo` (full repository access — or use a fine-grained token scoped to the `LearnTrack` repo only)

### Setup Steps

1. Generate a GitHub Personal Access Token (PAT) or fine-grained token at  
   `https://github.com/settings/tokens`

2. Scope the token to the `kotlinandfluttertutorial-lab/LearnTrack` repository only (fine-grained token recommended).

3. Set the token as an environment variable on your local machine:

   **Windows (PowerShell):**
   ```powershell
   $env:GITHUB_TOKEN = "your-token-here"
   # To persist across sessions, use System Environment Variables in Settings
   ```

   **macOS/Linux (Bash):**
   ```bash
   export GITHUB_TOKEN="your-token-here"
   # Add to ~/.bashrc or ~/.zshrc to persist
   ```

4. Add the server entry to `.kiro/settings/mcp.json`:

   ```json
   {
     "mcpServers": {
       "github": {
         "command": "npx",
         "args": ["-y", "@modelcontextprotocol/server-github"],
         "env": {
           "GITHUB_PERSONAL_ACCESS_TOKEN": "${GITHUB_TOKEN}"
         }
       }
     }
   }
   ```

5. Verify Node.js (≥ 18) and `npx` are installed:
   ```powershell
   node --version
   npx --version
   ```

### Notes

- The GitHub remote for this project is `https://github.com/kotlinandfluttertutorial-lab/LearnTrack.git`
- CI/CD will be implemented via GitHub Actions (`.github/workflows/`), which does not require MCP — it runs on push/PR triggers automatically.
- MCP is only needed for agentic operations (Kiro agents creating PRs, etc.), not for the build pipeline itself.

---

## 6. Figma MCP Setup

### When to Use

Use Figma MCP when:
- Implementing UI screens from Figma design specifications
- Checking design tokens, component variants, or spacing values
- Confirming a composable matches the approved design before merging

### Kiro-Native Figma MCP (Recommended — Used in LearnTrack)

Kiro has a built-in Figma integration that connects to `https://mcp.figma.com/mcp` automatically via OAuth. **No npm package or personal access token is required.**

To use it:
1. Ensure you are signed in to Figma in your browser.
2. In Kiro's MCP settings, add:
   ```json
   {
     "mcpServers": {
       "figma": {
         "url": "https://mcp.figma.com/mcp",
         "disabled": false
       }
     }
   }
   ```
3. Kiro will open an OAuth flow to authenticate with your Figma account.
4. Verify access with `figma::whoami` — confirm seat is **Full** (not View-only). View-only seats cannot use MCP write tools.

**LearnTrack Figma files (verified working):**

| File | Key | URL |
|---|---|---|
| LearnTrack — UI Designs | `5dEMGt9iynTXgVGqjxd9IM` | https://www.figma.com/design/5dEMGt9iynTXgVGqjxd9IM |
| LearnTrack — Design System | `AbG7UxbofJuAvBFCcZW0pv` | https://www.figma.com/design/AbG7UxbofJuAvBFCcZW0pv |

**Key node IDs for `figma::get_design_context`:**

| Node | ID | Contents |
|---|---|---|
| Stitch Import page | `3:4` | Full dark-mode dashboard design |
| Course Progress (Dark Mode) frame | `4:3` | 390×1789dp — main design reference |
| UI Screens page | `1:2` | All 5 screen sections |

### Legacy npm-based Setup (Alternative — not used in LearnTrack)

If Kiro's built-in Figma integration is unavailable:

1. Generate a Figma Personal Access Token at `https://www.figma.com/settings` → Personal access tokens.
2. Set as env var: `FIGMA_API_TOKEN`
3. Add to `.kiro/settings/mcp.json`:
   ```json
   {
     "mcpServers": {
       "figma": {
         "command": "npx",
         "args": ["-y", "@figma/mcp-server"],
         "env": { "FIGMA_API_KEY": "${FIGMA_API_TOKEN}" }
       }
     }
   }
   ```

When a Figma file is created, record its file key here.

---

## 7. Google Stitch MCP Setup

**Note:** Google Stitch MCP availability has NOT been verified for this Kiro installation. Stitch is a Google internal tool for AI-assisted UI generation and may not have a publicly available MCP server.

### Manual Alternative

Use the prompts in `docs/design/google-stitch/` to generate designs manually:
- `docs/design/google-stitch/learntrack-stitch-master-prompt.md` — Master prompt for the LearnTrack design context
- `docs/design/google-stitch/course-progress-prompt.md` — Specific prompt for the course progress screen
- `docs/design/google-stitch/design-review-checklist.md` — Checklist for reviewing generated designs

Copy the relevant prompt into the Stitch interface (if you have access) and follow the review checklist to validate the output.

---

## 8. Filesystem MCP Setup

The filesystem MCP server allows agents to read and write files outside the immediate workspace context. For LearnTrack, Kiro's built-in file tools (read, write, edit) cover all required local file operations.

**Recommendation:** Do not configure a separate filesystem MCP server unless a specific agentic workflow requires access to files outside the workspace root (`j:\Android\AndroidStudioProjects\Kiro\LearnTrack`).

If needed, the server can be configured as:

```json
{
  "mcpServers": {
    "filesystem": {
      "command": "npx",
      "args": [
        "-y",
        "@modelcontextprotocol/server-filesystem",
        "j:/Android/AndroidStudioProjects/Kiro/LearnTrack"
      ]
    }
  }
}
```

Note that this server has no authentication — it exposes file system access. Scope it to the workspace root only.

---

## 9. Complete Example mcp.json

The following is a complete example with all servers configured using environment variable references. Do not populate actual token values — use environment variables only.

```json
{
  "mcpServers": {
    "github": {
      "command": "npx",
      "args": ["-y", "@modelcontextprotocol/server-github"],
      "env": {
        "GITHUB_PERSONAL_ACCESS_TOKEN": "${GITHUB_TOKEN}"
      }
    },
    "figma": {
      "command": "npx",
      "args": ["-y", "@figma/mcp-server"],
      "env": {
        "FIGMA_API_KEY": "${FIGMA_API_TOKEN}"
      }
    }
  }
}
```

---

## 10. Checklist Before Committing mcp.json

Before committing `.kiro/settings/mcp.json`:

- [ ] No actual token values appear in the file
- [ ] All secrets use `${ENV_VAR_NAME}` references
- [ ] The file does not contain passwords, API keys, or signing credentials
- [ ] Confirmed that `.kiro/settings/mcp.json` is not listed in `.gitignore` (it is safe to commit if it has no secrets)
- [ ] Each server's access scope has been reviewed for least-privilege compliance

---

## References

- Discovery report: `docs/discovery/learntrack-discovery-report.md`
- Master prompt § 21: MCP Configuration
- Architecture doc: `docs/architecture/learntrack-architecture.md`
- GitHub remote: `https://github.com/kotlinandfluttertutorial-lab/LearnTrack`
- Figma design specs: `docs/design/figma/`
- Stitch prompts: `docs/design/google-stitch/`
