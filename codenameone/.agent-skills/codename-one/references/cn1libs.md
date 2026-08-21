# cn1libs — Creating, Packaging, and Consuming Codename One Libraries

A **cn1lib** is a reusable Codename One library: Java/Kotlin source, optional CSS, optional resources, and platform-native code (Objective-C for iOS, Java/Kotlin for Android, JavaScript for the web port, plain Java for the desktop simulator) packaged into a single artifact. CN1 has two cn1lib distribution formats:

- **Maven (modern, preferred for new libraries)** — published to Maven Central or a private Maven repo, consumed via a regular `<dependency>` in `common/pom.xml`.
- **`.cn1lib` binary (legacy)** — a self-contained zip of jars and native source trees, dropped into the consuming project's `cn1libs/` directory. Still in active use; many older CN1 libraries (ZipSupport, CodeRAD, push integrations) ship this way.

This guide covers both directions: **creating** a new cn1lib and **consuming** existing ones.

## Consuming an existing cn1lib

### Maven cn1lib

Add the dependency to `common/pom.xml`:

```xml
<dependency>
    <groupId>com.example.libs</groupId>
    <artifactId>my-cn1lib</artifactId>
    <version>1.0.0</version>
    <type>pom</type>
</dependency>
```

The `<type>pom</type>` matters — Maven cn1libs aggregate multiple per-platform classifier jars under a parent POM, so the consumer pulls in the right classifier (`common`, `ios`, `android`, `javascript`, `javase`) automatically.

Rebuild: `mvn -pl common compile`. The cn1lib's CSS, resources, and per-platform native sources are wired in by the CN1 maven plugin.

### Legacy `.cn1lib` binary

Drop the `.cn1lib` file into `<project>/cn1libs/`:

```
my-project/
├── cn1libs/
│   ├── ZipSupport.cn1lib
│   └── PushIntegration.cn1lib
├── common/
└── ...
```

Run `mvn -pl common compile` and the CN1 plugin scans `cn1libs/`, unpacks each file into the local Maven cache, and adds them to the classpath. The simulator's **File → Install Cn1Lib** menu drops the file into `cn1libs/` for you if you prefer a UI.

To verify a cn1lib is wired in correctly, run the bytecode compliance check — it scans all dependencies and will fail noisily if a class is missing:

```bash
mvn -pl common compile cn1:compliance-check
```

## Creating a new cn1lib (Maven)

The fastest path is the Maven archetype:

```bash
mvn archetype:generate \
    -DarchetypeGroupId=com.codenameone \
    -DarchetypeArtifactId=cn1lib-archetype \
    -DarchetypeVersion=<cn1.plugin.version> \
    -DgroupId=com.example.libs \
    -DartifactId=my-cn1lib \
    -Dversion=1.0-SNAPSHOT
```

Or, from inside an existing CN1 project root:

```bash
mvn cn1:generate-cn1lib-project \
    -DgroupId=com.example.libs \
    -DartifactId=my-cn1lib
```

Both produce a multi-module Maven project with the cn1lib layout:

```
my-cn1lib/
├── pom.xml                    # Aggregator + version
├── common/                    # The Java/Kotlin/CSS source you ship
│   ├── pom.xml
│   └── src/main/
│       ├── java/<pkg>/...     # Public API
│       ├── css/               # CSS bundled into consumers' theme.res
│       └── resources/         # Flat-namespace resources
├── ios/src/main/objectivec/   # iOS native sources
├── android/src/main/java/     # Android native sources
├── javascript/src/main/javascript/
└── javase/src/main/java/      # Desktop simulator stub
```

Author your library API as plain Java/Kotlin in `common/src/main/java/`. If you need platform-native behavior, follow the `references/native-interfaces.md` workflow — declare an interface in `common/`, run `mvn cn1:generate-native-interfaces`, fill in the stubs that the generator drops into each platform module.

### Bundled CSS

CSS placed under `common/src/main/css/` is automatically merged into the consumer's `theme.res` when they depend on your library. **Namespace your UIIDs** (`MyLibPrimaryButton`, not `Button`) — overwriting common UIIDs in a library is a surefire way to break consumer apps.

### Bundled resources

Resources at the top of `common/src/main/resources/` are visible to consumers via `Display.getInstance().getResourceAsStream("/<name>")`. Same flat-namespace constraint as application resources (see `references/java-api-subset.md`).

### Build and publish

```bash
mvn install                 # local install to ~/.m2 — for use in your own apps
mvn deploy                  # publish to whatever <distributionManagement> repo is configured
```

For Maven Central, follow the standard OSSRH process (sonatype JIRA → GPG signing → staging → release). Codename One's own libraries follow this path.

## Creating a `.cn1lib` binary (legacy)

The legacy format is what tools like the simulator's *Install Cn1Lib* menu produce. The Maven archetype above gives you the modern multi-module layout, but you can still emit a `.cn1lib` binary from the same project for compatibility:

```bash
mvn package cn1:cn1lib
```

The `cn1:cn1lib` goal (defined by `Cn1libMojo` in the plugin) bundles `common/target/` and the per-platform native sources into `target/<artifactId>-<version>.cn1lib`. Distribute that file directly — no Maven repo needed. Consumers drop it into their `cn1libs/` directory.

For internal libraries shared across a few apps, this can be simpler than running a Maven repository, but the modern Maven path is preferred for any library intended to be reused by other teams.

## Versioning and SNAPSHOTs

The same SNAPSHOT pattern that applies to the CN1 framework itself (see `references/snapshot-builds.md`) applies to your cn1libs. While iterating on a library:

```bash
# In the cn1lib project root, after editing:
mvn install                       # 1.0-SNAPSHOT goes to ~/.m2

# In the consuming app:
mvn -U -pl common compile         # -U forces SNAPSHOT re-resolution
```

For releases, drop the `-SNAPSHOT`, `mvn deploy`, and tag the git repo.

## Common pitfalls

- **CSS UIID collisions** — if your library styles `Button { ... }`, you've just changed how every Button in the consumer app renders. Always prefix (e.g. `MyLibCallButton`). The consumer can still override your UIIDs by name if they want.
- **Resources nested in subdirectories** — same flat-namespace rule as apps. Put files at the resource root or package them inside a zip + `BrowserComponent.setURLHierarchy` / `ZipSupport`.
- **Native source compiled into the wrong module** — when authoring iOS code in your library, it must live in `ios/src/main/objectivec/`, not `common/...`. The CN1 maven plugin copies these files into the consumer's iOS build at link time.
- **Cyclic dependencies between cn1libs** — Maven resolves them, but the runtime classloader has surprises. Keep each cn1lib's public API small.
- **Forgetting `<type>pom</type>` on a Maven cn1lib consumer** — without it Maven only pulls the parent POM (no jar) and you get `NoClassDefFoundError` at runtime.
- **Distributing a `.cn1lib` from a SNAPSHOT** — the file embeds the version at packaging time, so distributing across teams while iterating is risky. Pin to a release version first.

## Reference

- The CN1 archetype source: `https://github.com/codenameone/cn1-maven-archetypes`
- `Cn1libMojo` source (legacy `.cn1lib` packaging): in the CN1 plugin under `com.codename1.maven.Cn1libMojo`
- `GenerateCn1libProjectMojo` source (`mvn cn1:generate-cn1lib-project`): in the same plugin under `com.codename1.maven.GenerateCn1libProjectMojo`
