# JUnit 5 Testing Reference

Codename One ships two compatible ways to write tests:

| Framework | Runs through | When to reach for it |
| --- | --- | --- |
| `com.codename1.testing.AbstractTest` + `cn1:test` | The Codename One Maven plugin's own runner | Tests that must also execute **on a real device** (`mvn cn1:test -Dtarget=ios`), and anything that must compile under the CN1 device subset (no reflection, no JavaSE APIs). |
| `@CodenameOneTest` (JUnit 5) + Surefire | Standard `mvn test` via the JUnit Jupiter engine | Simulator-only tests. You get a real JVM, so you can use **reflection**, **Mockito**, **AssertJ**, parameterized tests, and any other library that would fail under ParparVM. Faster startup than `cn1:test`, integrates with your IDE's standard JUnit runner, plays nicely with `mvn -pl common test -Dtest=MyTest#oneMethod` filtering. |

Pick per test class. Both run in the same project from the same `common/src/test/java` directory; the runners don't interfere with each other (`cn1:test` discovers classes that implement `com.codename1.testing.UnitTest`, Surefire discovers `@Test`-annotated methods).

See `references/testing-and-screenshots.md` for the AbstractTest path, including the `screenshotTest` baseline algorithm.

## Project setup

The cn1app archetype generates a `common/pom.xml` and `javase/pom.xml` that already pull in `junit-jupiter` and `codenameone-javase` at test scope. If your project predates that wiring, add these two blocks yourself:

```xml
<!-- common/pom.xml -->
<dependency>
    <groupId>com.codenameone</groupId>
    <artifactId>codenameone-javase</artifactId>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.9.3</version>
    <scope>test</scope>
</dependency>
```

```xml
<!-- javase/pom.xml -->
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.9.3</version>
    <scope>test</scope>
</dependency>
```

The `common` module has Surefire skipped (`<skipTests>true</skipTests>`) — JUnit tests actually execute from the `javase` module, which mounts `common/src/test/java` via `<testSourceDirectory>`. That avoids running each test twice.

## A minimal JUnit test

```java
package com.example.myapp;

import com.codename1.testing.junit.CodenameOneTest;
import com.codename1.testing.junit.RunOnEdt;
import com.codename1.ui.CN;
import com.codename1.ui.Display;
import com.codename1.ui.Form;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@CodenameOneTest
class GreetingFormTest {

    @Test
    @RunOnEdt
    void formShowsExpectedTitle() {
        Form f = new Form("Hello");
        f.show();
        assertEquals("Hello", Display.getInstance().getCurrent().getTitle());
        assertTrue(CN.isEdt(), "EDT-bound assertions run on the EDT");
    }
}
```

Run it with `mvn -pl javase test` (or `mvn test` from the project root). Filter to one method with `mvn -pl javase test -Dtest=GreetingFormTest#formShowsExpectedTitle`.

## Annotation reference

All annotations live under `com.codename1.testing.junit.*` (in the `codenameone-javase` artifact).

### `@CodenameOneTest`

Class-level meta-annotation that registers `CodenameOneExtension` on the class. Equivalent to `@ExtendWith(CodenameOneExtension.class)`. The extension:

1. Aborts the class with `TestAbortedException` if `GraphicsEnvironment.isHeadless()` (so a Linux CI runner without Xvfb reports **skipped** instead of failing — see "Why the simulator needs a display" below).
2. Boots `Display` once per JVM via `Display.init(null)` (idempotent — multiple test classes share one Display).
3. Applies any `@SimulatorProperty`, `@Theme`, `@DarkMode`, `@LargerText`, `@Orientation`, `@RTL` annotations on the EDT in one batch before each test.
4. Dispatches `@RunOnEdt`-annotated methods through `CN.callSerially` and rethrows their exceptions on the JUnit thread.

### `@RunOnEdt`

Place on a single test method, or on the test class to apply to every `@Test` (and `@BeforeEach` / `@AfterEach`). The body runs on the Codename One EDT via `CN.callSerially`. Failures rethrow on the JUnit worker thread so the stack trace IDE-jumps cleanly.

```java
@Test
@RunOnEdt
void canMutateUiSafely() {
    Form f = new Form("X");
    f.add(new com.codename1.ui.Button("Tap"));
    f.show();           // safe -- we are on the EDT
}

@Test
@RunOnEdt(timeoutMillis = 60000)   // override the default 30s timeout
void slowFormBuild() { /* ... */ }
```

Tests that exercise pure model / utility code can skip `@RunOnEdt` and run on the JUnit worker — they're faster.

### `@SimulatorProperty` / `@SimulatorProperties`

Set a property visible to the simulator before the test runs.

```java
@Test
@SimulatorProperty(name = "feature.flag", value = "on")
void appReadsFlagFromDisplayProperty() {
    assertEquals("on", Display.getInstance().getProperty("feature.flag", null));
}

@Test
@SimulatorProperties({
    @SimulatorProperty(name = "user.tier", value = "pro"),
    @SimulatorProperty(name = "user.region", value = "eu")
})
void multipleAppProperties() { /* ... */ }
```

The `scope` field controls where it lands:

| Scope | Where it goes | When applied |
| --- | --- | --- |
| `DISPLAY` (default) | `Display.getInstance().setProperty(name, value)` | After Display init. Use for properties your app reads via `Display.getProperty(...)`. |
| `SYSTEM` | `System.setProperty(name, value)` | Before Display init (class-level only). Use for things the simulator reads at startup — e.g. `java.awt.headless`. Method-level SYSTEM properties are ignored because Display is already up. |

`@SimulatorProperties` is the container annotation. The JavaSE port compiles at source 1.7 (predates `@Repeatable`), so put multiple `@SimulatorProperty` entries inside `@SimulatorProperties({ ... })` rather than repeating the annotation directly.

### `@Theme`

Loads a base theme and installs it via `UIManager.setThemeProps`, then triggers a refresh. Two mutually exclusive inputs:

**By native theme** — the recommended form for cross-platform look-and-feel coverage. The `NativeTheme` enum mirrors the simulator's *Simulate → Native Theme* menu:

```java
@Test @Theme(nativeTheme = NativeTheme.IOS_MODERN)         void looksRightOnIosModern()    { /* ... */ }
@Test @Theme(nativeTheme = NativeTheme.IOS_FLAT)           void looksRightOnIosFlat()      { /* ... */ }
@Test @Theme(nativeTheme = NativeTheme.IPHONE_PRE_FLAT)    void looksRightOnClassicIos()   { /* ... */ }
@Test @Theme(nativeTheme = NativeTheme.ANDROID_MATERIAL)   void looksRightOnAndroid()      { /* ... */ }
@Test @Theme(nativeTheme = NativeTheme.ANDROID_HOLO_LIGHT) void looksRightOnHoloLight()    { /* ... */ }
@Test @Theme(nativeTheme = NativeTheme.ANDROID_LEGACY)     void looksRightOnAndroidLegacy(){ /* ... */ }
```

Each enum constant carries the resource path (`NativeTheme.IOS_MODERN.resourcePath()` returns `/iOSModernTheme.res`) and the simulator-menu label (`displayName()` returns `"iOS Modern (Liquid Glass)"`).

**By resource path** — for app themes shipped under `src/main/resources` or `src/test/resources`:

```java
@Test @Theme("/MyAppTheme.res")  void looksRightWithAppTheme()  { /* ... */ }
```

If both are set, `nativeTheme` wins. If neither is set, the annotation is a no-op.

### `@DarkMode`

Toggles dark/light mode via `Display.setDarkMode(Boolean)` and refreshes the active form.

```java
@Test @DarkMode                     void mainFormIsLegibleInDark()  { /* ... */ }
@Test @DarkMode(enabled = false)    void mainFormIsLegibleInLight() { /* ... */ }
```

### `@LargerText`

Sets the accessibility text-scale multiplier — the same knob as the simulator's "Larger Text" submenu. Useful for catching layout regressions at accessibility font sizes.

```java
@CodenameOneTest
@LargerText                 // class-level: every test runs at 1.3× (AX2)
class AccessibilityTest {

    @Test void buttonsStillFit() { /* ... */ }

    @Test
    @LargerText(scale = 2.0f)    // method-level override: AX5
    void buttonsAtExtremeScale() { /* ... */ }
}
```

`scale = 1.0f` restores the default size.

### `@Orientation`

Forces the simulator into portrait or landscape. Uses `JavaSEPort.setSimulatorPortrait(boolean)` internally, which sets an explicit-portrait flag honored by `Display.isPortrait()`.

```java
@Test
@Orientation(Orientation.Value.LANDSCAPE)
void formStillFitsInLandscape() {
    assertFalse(Display.getInstance().isPortrait());
    // ... layout assertions ...
}
```

### `@RTL`

Flips the look-and-feel into right-to-left mode (Arabic, Hebrew). Calls `UIManager.getInstance().getLookAndFeel().setRTL(...)` and revalidates the active form.

```java
@Test @RTL                          void mirrorsCorrectly()      { /* ... */ }
@Test @RTL(enabled = false)         void restoresLtrLayout()     { /* ... */ }
```

## Resolution rules

When the same annotation appears at both the class and the method level, **method wins**. Example:

```java
@CodenameOneTest
@LargerText(scale = 1.3f)        // class default
@DarkMode                         // class default
class LayoutTest {

    @Test
    @LargerText(scale = 2.0f)     // overrides class -> 2.0f for this test
    void extremeScale() { /* runs at 2.0× scale + dark mode */ }

    @Test
    void defaultScale() { /* runs at 1.3× scale + dark mode */ }
}
```

Annotations the method doesn't override inherit from the class. Annotations that appear on neither leave Display state alone — the extension never "resets" a knob the caller didn't ask for, so write `@AfterEach` cleanup yourself if a test must leave the simulator pristine for the next class.

## EDT semantics, in one paragraph

`@RunOnEdt` dispatches the test body through `CN.callSerially(...)` and uses a latch + `wait/notify` to block the JUnit thread until the EDT-side runnable finishes or `timeoutMillis` expires. Throwables from inside the EDT runnable are captured and rethrown on the JUnit thread so the assertion stack trace lands in the IDE as if the test had been called directly. Without `@RunOnEdt`, the test body runs on the Surefire worker thread — fine for pure model assertions, broken for any UI mutation that has to happen on the EDT.

## Why the simulator needs a display

`Display.init(null)` eventually calls `JavaSEPort.init(null)`, which constructs a `javax.swing.JFrame` to host the canvas. JFrame construction in a headless JVM throws `HeadlessException` immediately. So:

- **Local dev (macOS / Windows / Linux desktop)**: works out of the box.
- **CI with an X server (Xvfb, headful container)**: works — wrap your test command with `xvfb-run mvn test` if needed.
- **CI with `-Djava.awt.headless=true` or no DISPLAY at all**: the `@CodenameOneTest` extension calls `GraphicsEnvironment.isHeadless()` in `@BeforeAll` and aborts the class via `TestAbortedException`. JUnit reports the class as **skipped** rather than failed, and crucially the Display singleton is never partially initialized — so the next test class in the same JVM isn't poisoned.

That's also why our own `CodenameOneExtensionTest` carries `@DisabledIfSystemProperty(named = "java.awt.headless", matches = "true")` as a belt-and-suspenders. The extension catches auto-detected headless, the annotation catches explicit `-Djava.awt.headless=true`. Pure-logic tests (no UI, no `@CodenameOneTest`) can run on a headless runner without either.

## Coexisting with `cn1:test`

Both runners discover their own classes in the same `common/src/test/java` tree:

- `cn1:test` looks for classes that `implements com.codename1.testing.UnitTest` (the AbstractTest interface).
- Surefire (JUnit Jupiter) looks for `@Test`-annotated methods.

They don't trip over each other. `mvn install` runs both — Surefire during `test`, then `cn1:test` from the javase module's test profile. If you want only one or the other:

```bash
mvn -pl javase test                            # only Surefire / JUnit
mvn -pl javase test -DskipTests                # neither
mvn -pl javase verify -Dtest=NoMatchingTest    # only cn1:test (skip Surefire by filtering it to nothing)
```

## Side-by-side example

The same "click a button, assert title changed" check, written both ways:

**AbstractTest / `cn1:test`:**

```java
public class LoginNavTest extends com.codename1.testing.AbstractTest {
    @Override public boolean shouldExecuteOnEDT() { return true; }

    @Override public boolean runTest() throws Exception {
        new MyApp().runApp();
        TestUtils.waitForFormTitle("Login");
        TestUtils.setText("usernameField", "alice");
        TestUtils.clickButtonByLabel("Sign In");
        TestUtils.waitForFormTitle("Home");
        return TestUtils.screenshotTest("home-screen");
    }
}
```

**JUnit 5 / Surefire:**

```java
@CodenameOneTest
class LoginNavTest {

    @Test
    @RunOnEdt
    void signingInLandsOnHome() throws Exception {
        new MyApp().runApp();
        TestUtils.waitForFormTitle("Login");
        TestUtils.setText("usernameField", "alice");
        TestUtils.clickButtonByLabel("Sign In");
        TestUtils.waitForFormTitle("Home");
        assertTrue(TestUtils.screenshotTest("home-screen"), "home screenshot regressed");
    }
}
```

The UI driving (`TestUtils.*`) is identical — `TestUtils` is independent of the runner. What changes is the lifecycle plumbing: `shouldExecuteOnEDT() + runTest() returns boolean` becomes `@RunOnEdt + @Test void + assertion library`.

## When NOT to use JUnit

- Tests that must execute on a real iOS/Android device via `mvn cn1:test -Dtarget=ios`. JUnit Jupiter doesn't exist on ParparVM. Stick with `AbstractTest` for device-runnable tests.
- Codebases where every test already extends `AbstractTest` and you have no migration appetite. Both runners coexist — there is no need to convert.
- Library (cn1lib) test apps inside `tests/` of a cn1lib project. Those drive the simulator differently; treat them like the device case.
