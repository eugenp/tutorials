# Building Against a Codename One SNAPSHOT From Git

**This is an edge case.** Most projects should consume Codename One from Maven Central — pinned to a released version, no compilation of the framework itself, no surprises. Reach for the snapshot workflow only when you need to test an in-flight CN1 fix, reproduce a bug against the latest code, or contribute a patch.

## When to use a SNAPSHOT

- You are reproducing a bug against the latest CN1 framework commit to confirm it still happens before reporting it.
- You are patching CN1 itself and want to validate the patch in this project before opening a PR upstream.
- The fix you need landed in the CN1 repo but hasn't shipped to Maven Central yet.

For day-to-day app development against a stable release, **don't do this** — pin to the latest Central release in `pom.xml` and rely on the normal Maven resolution path. SNAPSHOTs change without warning and can break the build.

## The workflow

### 1. Clone the Codename One framework

```bash
cd ~
git clone https://github.com/codenameone/CodenameOne.git
git -C CodenameOne checkout master       # or a specific commit / branch
```

### 2. Bootstrap the workspace and build the SNAPSHOT artifacts into your local Maven cache

```bash
cd ~/CodenameOne
./scripts/setup-workspace.sh -DskipTests       # one-time: downloads JDKs, builds core, installs archetypes
source tools/env.sh

cd maven
mvn install -Plocal-dev-javase -DskipTests
```

This installs `com.codenameone:codenameone-core:8.0-SNAPSHOT`, `codenameone-maven-plugin:8.0-SNAPSHOT`, and friends into `~/.m2/repository`. The exact SNAPSHOT version is in `~/CodenameOne/maven/pom.xml` (the `<version>` near the top).

If you also need the Android port locally:

```bash
./scripts/build-android-port.sh -DskipTests
```

For iOS (macOS only):

```bash
./scripts/build-ios-port.sh -DskipTests
```

### 3. Point your generated project at the SNAPSHOT

Edit your app's root `pom.xml`:

```xml
<properties>
    <cn1.version>8.0-SNAPSHOT</cn1.version>
    <cn1.plugin.version>8.0-SNAPSHOT</cn1.plugin.version>
</properties>
```

(Substitute whatever version is in `~/CodenameOne/maven/pom.xml`.) The properties are referenced from every `<dependency>` and the `codenameone-maven-plugin` block — you should not need to edit individual coordinates.

### 4. Rebuild the app and verify

```bash
mvn -pl common clean compile        # confirms the plugin + framework jars resolve and compile
mvn -pl common cn1:run              # simulator boot against the snapshot
```

If the build fails with "Could not find artifact com.codenameone:codenameone-core:jar:8.0-SNAPSHOT", the framework's `mvn install` didn't complete. Re-run step 2 and watch for build errors.

## Iterating quickly while patching CN1

Once everything resolves, iterating on the CN1 sources is:

```bash
# In ~/CodenameOne/maven, after editing a CN1 source file:
mvn install -Plocal-dev-javase -DskipTests -pl <module>      # e.g. -pl core, or -pl codenameone-maven-plugin

# In your app project:
mvn -pl common cn1:run        # picks up the freshly-installed SNAPSHOT
```

`-pl <module>` targets just the module you changed, instead of rebuilding the whole framework each iteration.

## Reverting to a Maven Central release

When you're done, set the versions back to whatever release you want to pin. Drop the `-SNAPSHOT`:

```xml
<properties>
    <cn1.version>7.0.242</cn1.version>
    <cn1.plugin.version>7.0.242</cn1.plugin.version>
</properties>
```

Then `mvn -pl common clean compile` will resolve those from Central instead of `~/.m2`.

## Pitfalls

- **Cached SNAPSHOT in `~/.m2`** — when the framework version is `8.0-SNAPSHOT` and you re-`mvn install` it, the artifact in `~/.m2/repository/com/codenameone/codenameone-core/8.0-SNAPSHOT/` is overwritten. The previous app build may not pick the new jar up if Maven decided to cache it: pass `-U` to your app build (`mvn -U -pl common cn1:run`) to force re-resolution. With timestamp-bearing SNAPSHOT filenames this matters less, but `-U` is cheap insurance.
- **`codename1.arg.java.version=17` still belongs in `codenameone_settings.properties`** even when consuming a SNAPSHOT. It controls the build-server build path, not the local resolver.
- **iOS / Android local-builds need the matching native ports.** A SNAPSHOT of just `codenameone-core` isn't enough for `cn1:build` to produce a working APK locally — see step 2 for the platform-port build scripts.
- **Don't push a SNAPSHOT pin to a release branch.** SNAPSHOTs evolve daily; downstream CI will break. Use a branch / tag for snapshot-pinned validation work, then revert to a released version before merging.
