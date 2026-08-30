# Build and Run Reference

## Local builds vs cloud builds

A Codename One project can produce four kinds of artifacts. Some build entirely on your machine; others rely on the Codename One **build server** (cloud) because cross-compiling iOS/Android natively on every developer's box would be unrealistic.

| Output | Where it builds | Maven goal |
| --- | --- | --- |
| Desktop simulator | Local (your machine, JVM only) | `mvn -pl common cn1:run` / `cn1:debug` |
| Unit tests | Local (the CN1 test runner inside a JVM) | `mvn -pl common cn1:test` |
| Standalone desktop app (`.jar` + bundled JRE for Mac/Win/Linux) | Cloud (build server packages a JRE for each OS) | `mvn -pl javase package -Dcodename1.platform=javase -Dcodename1.buildTarget=mac-os-x-desktop` (or `windows-desktop`, `linux-desktop`) |
| Android APK / AAB | Cloud by default; **also** locally if you run `cn1:install-android-sdk` and use the local Android build path | `mvn -pl android package -Dcodename1.platform=android -Dcodename1.buildTarget=android-device` |
| iOS app | Cloud, **or** locally as an Xcode project via `ios-source` | `mvn -pl ios package -Dcodename1.platform=ios -Dcodename1.buildTarget=ios-device` (cloud) or `…-Dcodename1.buildTarget=ios-source` (local Xcode project) |
| JavaScript / web bundle | Cloud | `mvn -pl javascript package -Dcodename1.platform=javascript -Dcodename1.buildTarget=javascript` |

The two big "local-only" outputs are the **simulator** and **tests** — those are everything you need for ordinary development and CI feedback loops. You only invoke the cloud builds when you want a deployable native artifact.

## Required JDKs

| Task | JDK |
| --- | --- |
| Compiling the `common` module | JDK 17+ as `JAVA_HOME` |
| Running `cn1:run` / `cn1:debug` (the simulator) | **JDK 17–25** (CN1 plugin aborts on older JDKs with a friendly error) |
| Cross-compiling for Android **locally with Gradle 8** | The same JDK 17+ already used as `JAVA_HOME` is reused automatically; you only need to set `JAVA17_HOME` if Maven is running on an older JVM |
| Cross-compiling for iOS locally (`ios-source`) | macOS only — see Xcode prerequisites below |
| Cloud builds (any target) | None — the cloud server holds the toolchain. Local `JAVA_HOME` only runs Maven. |

`codename1.arg.java.version=17` in `codenameone_settings.properties` selects the **build server's** JDK 17 toolchain. Keep it set even when you upgrade your local JDK (21, 25, …) — the local JDK only runs Maven and the simulator; the property routes the cloud-build target.

## Maven goal cheat sheet

All goals come from the `codenameone-maven-plugin` and live under the `cn1:` prefix. Run them from the project root unless noted.

```bash
# --- Local-only ---

# Desktop simulator (live, with hot CSS reload). No cloud, no credentials.
mvn -pl common cn1:run

# Debug from your IDE (attaches to JDWP).
mvn -pl common cn1:debug

# Run the CN1 test runner (NOT surefire). Runs inside the simulator JVM.
mvn -pl common cn1:test

# Compile CSS into theme.res without running the app. Process-resources phase.
mvn -pl common compile

# --- Cloud builds (need a Codename One account; some need Enterprise tier) ---

# Native iOS app (.ipa). Cloud-built.
mvn -pl ios package -Dcodename1.platform=ios -Dcodename1.buildTarget=ios-device

# Local Xcode project, no cloud. See Xcode prerequisites below.
mvn -pl ios package -Dcodename1.platform=ios -Dcodename1.buildTarget=ios-source

# Native Android APK/AAB. Cloud-built by default.
mvn -pl android package -Dcodename1.platform=android -Dcodename1.buildTarget=android-device

# JavaScript / web bundle. Cloud-built.
mvn -pl javascript package -Dcodename1.platform=javascript -Dcodename1.buildTarget=javascript

# Standalone Mac / Windows / Linux desktop app. Cloud-built.
mvn -pl javase package -Dcodename1.platform=javase -Dcodename1.buildTarget=mac-os-x-desktop
mvn -pl javase package -Dcodename1.platform=javase -Dcodename1.buildTarget=windows-desktop
mvn -pl javase package -Dcodename1.platform=javase -Dcodename1.buildTarget=linux-desktop
```

## Tests in CI/CD

For **logic, UI, and screenshot tests**, run `mvn -pl common cn1:test` (the CN1 test runner). It executes inside a local JVM via the simulator, so CI/CD does not need a Codename One account, a build server, or platform tooling — any GitHub Actions runner with a JDK 17+ can run it. This is the right loop for fast feedback.

### Framebuffer requirement on headless Linux runners

The CN1 test runner boots the simulator's AWT/Swing-based JavaSE port, which needs a graphics environment. macOS and Windows runners ship one out of the box. **Linux runners (the common GitHub Actions `ubuntu-latest`) do not** — you must provide a virtual framebuffer via `xvfb-run`:

```yaml
# .github/workflows/test.yml
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with: { distribution: 'temurin', java-version: '17' }
      - name: Run CN1 tests under a virtual framebuffer
        run: xvfb-run -a ./mvnw -pl common test-compile cn1:test
```

`xvfb-run -a` automatically picks a free display number (`-a` = autodisplay). Without it the test JVM aborts with `java.awt.HeadlessException` or "Can't connect to X11 display".

On `macos-latest` / `windows-latest` runners just call `./mvnw -pl common test-compile cn1:test` directly — no wrapper needed.

You only need the cloud build path when you want to **produce a native artifact** (.ipa / .apk / desktop installer / web bundle) — see the next section.

## Automated cloud builds for CI / LLM workflows

`-Dautomated=true` on a cloud `package` goal switches the build into non-interactive mode: no browser prompt, no credential dialog, build failure becomes a non-zero exit code, and `result.zip` is downloaded directly into `target/`.

```bash
mvn -pl ios     package -Dcodename1.platform=ios     -Dcodename1.buildTarget=ios-device     -Dautomated=true
mvn -pl android package -Dcodename1.platform=android -Dcodename1.buildTarget=android-device -Dautomated=true
```

This is the right tool **specifically for producing native artifacts in unattended pipelines** (release builds, nightly TestFlight uploads, LLM-driven builds that need to verify a target compiles). It is **not** needed for running tests — tests are local.

Credentials must already be set:

- Provide `-Duser=<email> -Dtoken=<api-token>` on the command line.
- Or run `mvn cn1:set-user-token -Duser=<email> -Dtoken=<token>` once on the build machine; credentials are then stored in Java preferences for the user.

> **Tier requirement**: `-Dautomated=true` and the API-token-based, non-interactive build flow require a **Codename One Enterprise** subscription. Free/community accounts can use the regular cloud build (which opens a browser for login) but not the unattended automated mode.

## Local iOS builds (`ios-source`) prerequisites

`-Dcodename1.buildTarget=ios-source` skips the build server and emits an Xcode project under `ios/target/codenameone/ios/dist/<App>-src/`. You can open it in Xcode and produce an `.ipa` locally. Requirements:

| Requirement | Notes |
| --- | --- |
| macOS | Apple silicon or Intel; no other OS can build iOS apps. |
| Xcode | Latest stable from the App Store. If you intend to submit to the App Store, use the version Apple currently mandates. |
| Xcode command-line tools | `xcode-select --install`. Required for `xcodebuild` and the iOS Simulator. |
| CocoaPods | `sudo gem install cocoapods` if your project (or any cn1lib) declares `codename1.arg.ios.pods`. |
| Apple developer account + signing | Set `codename1.arg.ios.teamId=ABCDEF1234` in `codenameone_settings.properties` so the generated Xcode project signs with your team automatically. |
| Deployment target | Set `codename1.arg.ios.deployment_target=14.0` (or current minimum) to match Apple's accepted submission range. |

After the project is generated, you can also run `xcodebuild -workspace <App>.xcworkspace -scheme <App> -configuration Release archive` from CI to produce an archive without opening Xcode interactively.

## `codenameone_settings.properties` — the configuration source of truth

This file lives at `common/codenameone_settings.properties`. The most useful keys:

```properties
codename1.packageName=com.example.myapp
codename1.mainName=MyAppName
codename1.displayName=My App Name
codename1.arg.java.version=17                 # Required: routes the build to the Java 17 build server
codename1.arg.android.googlePlayVersion=true
codename1.arg.ios.includePush=false
codename1.kotlin=false
codename1.arg.android.xPermissions=...
codename1.arg.ios.deployment_target=14.0
codename1.arg.ios.teamId=ABCDEF1234
codename1.arg.build.compile=true              # ahead-of-time compile (recommended for iOS)
```

Anything prefixed `codename1.arg.<platform>.<key>` is forwarded to the build server. See [`build-hints.md`](build-hints.md) for the curated index of build hints. The complete reference is in the Codename One Developer Guide at <https://www.codenameone.com/developer-guide/>.

## Layout invariants

- `common/src/main/css/theme.css` — **the** CSS file. Compiled to `common/target/classes/theme.res`.
- `common/src/main/l10n/messages*.properties` — i18n bundles. **MUST** live here (not in `src/main/resources`), or the CSS compiler will not bake them in.
- `common/src/main/java/<pkg>/<MainClass>.java` — entry point. Class name must match `codename1.mainName`.
- `common/src/main/resources/` — arbitrary runtime resources read at runtime via `Display.getInstance().getResourceAsStream("/file.json")`. **Important constraint**: the Codename One classloader keeps resources in a **flat namespace** — nested directories under `src/main/resources` are not supported by default. Drop files at the top level (e.g. `src/main/resources/data.json` → loaded as `/data.json`). If you must ship a deeper structure, package it inside a single zip resource and read entries with `ZipInputStream` on top of `getResourceAsStream`. See `references/java-api-subset.md` for IO patterns.
- `common/src/main/guibuilder/` — optional GUI builder XML (auto-generates Java).

## Common build errors and fixes

- **`Resources.getGlobalResources().getL10N(...)` returns null at runtime** → your bundles are under `common/src/main/resources/` instead of `common/src/main/l10n/`. Move them.
- **`Cannot find symbol class XxxView`** after using the GUI builder → run `mvn -pl common generate-sources` to regenerate.
- **`OutOfMemoryError` running `cn1:test`** → bump `<argLine>-Xmx2g</argLine>` in the surefire/cn1 plugin config in `common/pom.xml`.
- **Build server returns "build args invalid"** → check `codename1.arg.*` keys against [`build-hints.md`](build-hints.md) or the Developer Guide.
- **`When using gradle 8, you must set the JAVA17_HOME environment variable`** for a local Android build → only happens when Maven itself is running on a JDK older than 17. Re-launch Maven with a JDK 17+ `JAVA_HOME` and the builder reuses it automatically.
- **`-Dautomated=true` exits 0 even though the build failed** → make sure you're on a recent `cn1.plugin.version`; older versions of the plugin swallowed errors.

## The build client jar

The build server protocol is driven by `CodeNameOneBuildClient.jar`. The Codename One Maven plugin places it under `~/.codenameone/CodeNameOneBuildClient.jar` automatically the first time you run a `cn1:build` / `package` goal. If it ever goes missing, download the canonical copy:

<https://github.com/codenameone/CodenameOne/raw/refs/heads/master/maven/CodeNameOneBuildClient.jar>

Put it at `~/.codenameone/CodeNameOneBuildClient.jar`.

## Reference links

- Codename One Developer Guide: <https://www.codenameone.com/developer-guide/>
- JavaDoc: <https://www.codenameone.com/javadoc/>
- Maven plugin manual: <https://shannah.github.io/codenameone-maven-manual/>
- Build hints curated index: [`build-hints.md`](build-hints.md)
- Java API subset, IO, networking: [`java-api-subset.md`](java-api-subset.md)
