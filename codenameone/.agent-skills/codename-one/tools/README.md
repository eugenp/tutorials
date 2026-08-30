# Agent Tools

These are small Java 17+ utilities meant to be invoked by an AI agent (or a developer) to answer a single yes/no question about a Codename One project. They use Java's single-file source-mode (Java 11+), so they need no compile step:

```
java tools/<ToolName>.java <args>
```

Each tool prints a one-line result on stdout and exits with status `0` for success, `1` for negative answer, `2` for a usage / discovery error. Diagnostic messages go to stderr.

The tools require **a Java 17+ JDK on `PATH`**. They auto-discover the Codename One jars under `~/.m2/repository/` — run `mvn -pl common compile` once in the consuming app to make sure that cache is populated.

## Available tools

### `IsApiSupported.java`

Checks whether a fully-qualified class name (or class#method) is part of the Codename One supported Java API subset.

```bash
java tools/IsApiSupported.java java.util.HashMap
# YES

java tools/IsApiSupported.java java.nio.file.Files
# NO

java tools/IsApiSupported.java java.util.HashMap#put
# YES (class present at java-runtime-7.0.242.jar!java/util/HashMap.class — for method-level confirmation run `javap -p -classpath …` and grep for `put`)
```

Useful when porting code from desktop Java and you want a quick "is this safe to use" check before discovering it at `mvn cn1:compliance-check` time.

For the full picture of what's supported and what isn't, see `references/java-api-subset.md`.

### `IsCssValid.java`

Validates a `theme.css` file by running it through the Codename One CSS compiler. Reports `VALID` if the compiler produced a theme, `INVALID` plus the compiler's error message otherwise.

```bash
java tools/IsCssValid.java common/src/main/css/theme.css
# VALID
```

The tool loads the latest `codenameone-core` jar from `~/.m2` and invokes the compiler via reflection — no manual `-cp` setup needed.

This is **not** a substitute for running the simulator and looking at the result; it catches CSS *syntax* errors and unsupported properties, but a syntactically-valid file that styles the wrong UIID will still pass. Use it as a fast pre-flight before `mvn -pl common cn1:run`.

## Adding more tools

The pattern is intentionally minimal — drop a new `.java` file into this directory and document it in this README. Constraints:

- Single Java source file. No external dependencies beyond what's already on the JDK plus the CN1 jars in `~/.m2`.
- One question per tool. Print one line of result on stdout. Send diagnostics to stderr.
- Exit codes: `0` success, `1` negative answer / validation failed, `2` discovery or usage error.
- Auto-discover the CN1 jars from `~/.m2/repository/com/codenameone/`. Tell the user when discovery fails and what to fix.
