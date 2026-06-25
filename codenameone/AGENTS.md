# AGENTS.md

This project is a Codename One cross-platform mobile app (Java 17 / Maven /
ParparVM-iOS / Android / JavaScript / desktop). A vendor-neutral authoring skill
is bundled in this repository for any AI agent:

- **Start here:** `.agent-skills/codename-one/SKILL.md`
- **Topical references:** `.agent-skills/codename-one/references/`
- **Runnable utilities (Java 17 single-file source mode):** `.agent-skills/codename-one/tools/`

Tool integrations (Claude Code, Cursor, etc.) may also pick this skill up via
their own conventions; the canonical source of truth is `.agent-skills/`.

## Quick orientation for an agent

- App source lives in `common/src/main/java/`.
- Theme/styling lives in `common/src/main/css/theme.css` (Codename One CSS — a
  deliberate subset, see `.agent-skills/codename-one/references/css.md`).
- Run the simulator with `mvn -pl common cn1:run`.
- Run tests with `mvn -pl common cn1:test` (on Linux CI use `xvfb-run -a`).
- Native cloud builds use `mvn -pl <ios|android|javascript|javase> package -Dcodename1.platform=... -Dcodename1.buildTarget=...`.

When in doubt, open `.agent-skills/codename-one/SKILL.md` and follow the
reference table at the bottom.
