# Debugging — JDB Attach to the Simulator

Codename One's simulator is a regular JVM, so the standard Java Debugger (`jdb`) attaches to it cleanly. For an LLM-driven agent this is the most powerful tool in the kit — you can run a faulty screen to the breakpoint, dump locals, evaluate expressions, and step through code without an IDE. The same workflow works in CI / batch contexts where a graphical debugger is unavailable.

## One-time check

Before relying on this workflow, confirm `jdb` is installed:

```bash
jdb -version
```

`jdb` is bundled with every Oracle / OpenJDK / Temurin JDK at `$JAVA_HOME/bin/jdb`. If `jdb -version` fails, your `PATH` is pointing at a JRE rather than a JDK. Switch to a JDK 17+ install (Temurin from <https://adoptium.net> is the canonical choice).

## Start the simulator under JDWP

The CN1 maven plugin exposes a `cn1:debug` goal that activates a `debug-simulator` profile in `javase/pom.xml`. That profile adds these JVM args to the forked simulator:

```
-Xdebug
-Xrunjdwp:transport=dt_socket,server=y,address=${jpda.address},suspend=y
```

`server=y` makes the simulator listen for a debugger; `suspend=y` means it freezes immediately on startup and won't run until a debugger attaches. That suspend is the key feature for agent-driven debugging — you get to set breakpoints before any code runs.

From the project root:

```bash
mvn -pl common cn1:debug -Djpda.address=8000
```

Output looks like:

```
Listening for transport dt_socket at address: 8000
```

The simulator is now paused, holding port 8000, waiting for `jdb`. Leave this terminal running.

## Attach `jdb`

In a second terminal:

```bash
jdb -attach 8000
```

Or, if the simulator is on a different machine:

```bash
jdb -attach localhost:8000
```

You get the `jdb` prompt. The VM is suspended; you can set breakpoints before resuming.

## Essential `jdb` commands

The full command list is `jdb`'s `help`. The minimum useful set:

| Command | Purpose |
| --- | --- |
| `stop in com.example.myapp.MyAppName.runApp` | Breakpoint on a method entry. |
| `stop at com.example.myapp.LoginForm:42` | Breakpoint at source line 42. |
| `clear` | List active breakpoints. `clear <bpRef>` removes one. |
| `run` | Start (or resume) the VM. (For an already-listening server you usually use `cont` instead — `run` is for `-launch` mode.) |
| `cont` | Continue execution until the next breakpoint or VM exit. |
| `step` | Step into the next line. |
| `next` | Step over the next line (don't enter called methods). |
| `step up` | Run to end of current method. |
| `where` | Stack trace of the current thread. |
| `where all` | Stack traces of all threads (find the EDT among them). |
| `threads` | List threads. The CN1 EDT shows as `CodenameOneThread[EDT-…]`. |
| `thread <id>` | Switch the current thread context. |
| `locals` | All local variables in the current frame. |
| `print <expr>` | Evaluate an expression. Supports method calls: `print form.getTitle()`, `print Display.getInstance().isEdt()`. |
| `dump <var>` | Pretty-print an object's fields. |
| `set <var> = <expr>` | Mutate a local. Useful for forcing a code path during debugging. |
| `monitor` | Run a command every time you hit a breakpoint. Pair with `print` to log without stopping. |
| `quit` | Detach and exit jdb. The simulator continues running (or terminates if you also kill the Maven process). |

## Agent-driven debugging recipe

The cleanest workflow for an agent that needs to debug a CN1 bug:

1. **Confirm the bug**. Reproduce it from the simulator manually first (`mvn -pl common cn1:run`) and identify the symptom: a wrong value, an exception, a UI element in the wrong state.
2. **Pick a breakpoint location.** Either the line that throws (the stack trace tells you), the method that returns the wrong value, or the listener that fires the bad behavior.
3. **Start the simulator under JDWP**: `mvn -pl common cn1:debug -Djpda.address=8000` in one terminal.
4. **Attach `jdb`**: `jdb -attach 8000` in another terminal.
5. **Set the breakpoint, then continue**: at the `jdb >` prompt:

   ```
   stop at com.example.myapp.LoginForm:42
   cont
   ```

   The simulator UI now appears and runs. When execution reaches line 42, `jdb` interrupts and prompts.
6. **Inspect state**:

   ```
   where
   locals
   print credential
   print Display.getInstance().getCurrent().getTitle()
   ```
7. **Step** (`step` / `next`) until you have the diagnosis.
8. **Optionally mutate** (`set field = 10`) to confirm the fix would work, before editing the source.
9. **`quit`** the `jdb` session, fix the code, run `mvn -pl common cn1:test` (or rerun the simulator) to confirm.

## Driving `jdb` non-interactively

For headless agent runs, pipe commands into `jdb` via a script. `jdb` supports `--init <file>` and `-source <list>` flags but most of the time the simplest pattern is a here-doc:

```bash
mvn -pl common cn1:debug -Djpda.address=8000 &
SIMULATOR_PID=$!

# Give the simulator a moment to start the JDWP listener.
sleep 2

jdb -attach 8000 <<'EOF' >jdb-output.log 2>&1
stop at com.example.myapp.LoginForm:42
cont
where
locals
print credential
quit
EOF

# Inspect jdb-output.log for the captured state.
kill "$SIMULATOR_PID" 2>/dev/null || true
```

The output of `where`, `locals`, and `print` ends up in `jdb-output.log` — exactly what an agent can parse to determine what went wrong. Keep `quit` at the end of the script or the simulator hangs forever.

## Catching exceptions

```
catch java.lang.NullPointerException
catch caught java.lang.IllegalStateException
catch uncaught java.lang.Throwable
```

`catch` makes `jdb` break the moment that exception type is thrown. `caught` vs `uncaught` lets you filter only ones that escape vs all (including ones the app handles). For "find me the source of any NPE" this is faster than guessing at line breakpoints.

## Common pitfalls

- **`Unable to attach to target VM`** — the simulator isn't listening yet. Check the first terminal — wait for `Listening for transport dt_socket at address: 8000` before running `jdb -attach`.
- **`Address already in use`** — port 8000 is held by a previous `cn1:debug` run that didn't clean up. `lsof -i :8000` to find the process, kill it, or just pick a different port (`-Djpda.address=8001`).
- **Breakpoint says "Set deferred breakpoint" and never fires** — the class hasn't been loaded yet. That's normal; CN1 classes load lazily as Forms open. As long as you `cont` and the simulator eventually loads the class, the breakpoint resolves and fires.
- **Source lines don't match the running code** — your simulator was started before a code change. Stop the Maven `cn1:debug` process, recompile (`mvn -pl common compile`), and restart `cn1:debug`.
- **`print` returns "No instance specified"** — you're in the wrong frame or thread. Use `where`/`threads` to find the EDT, `thread <id>` to switch, then retry.
- **EDT thread is named `CodenameOneThread[EDT…]`** — `threads` lists everything; grep for `EDT` to identify the right one. CN1 also spawns timer threads, the GC, the network thread; usually you want the EDT for any UI debugging.

## When `jdb` isn't enough

For UI-rendering bugs (a label shows the wrong text, a colour is off, a layout has shifted), `jdb` reads state but doesn't see pixels. Combine it with the screenshot tests in `references/testing-and-screenshots.md` — `Display.captureScreen()` from inside a debugger session writes a PNG to `Storage` that you can inspect after the fact.

For build / compile errors there's no JVM running yet. Use `mvn -pl common compile -e -X` for verbose Maven diagnostics; consult `references/build-and-run.md` for the common build error patterns.

For native-side bugs on iOS / Android the simulator's JVM doesn't apply at all. Use Xcode's debugger (for `ios-source` builds) or Android Studio attached to the running APK.
