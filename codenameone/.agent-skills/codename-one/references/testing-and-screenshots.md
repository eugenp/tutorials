# Testing and Screenshots Reference

This document covers Codename One's `AbstractTest` framework, which runs through `cn1:test`. Standard JUnit 5 tests against the simulator are covered in `references/junit-testing.md` — both frameworks coexist in the same project and you pick per test class.

When to use `AbstractTest` (this doc):

- The test must also run on a device via `mvn cn1:test -Dtarget=ios`. JUnit Jupiter is not available on ParparVM, so on-device tests must use `AbstractTest`.
- The test compiles under the strict device subset (no reflection, no `java.nio.file.*`, no `java.net.http.*`).
- You already have a body of `AbstractTest` tests and want to keep adding peers in the same style.

When to use JUnit instead (`references/junit-testing.md`):

- Simulator-only tests that want reflection, Mockito, AssertJ, `assertThrows`, parameterized tests, `-Dtest=Foo#bar` filtering, IDE-native test discovery.

Either way, the `TestUtils` helpers below (`waitForFormTitle`, `clickButtonByLabel`, `screenshotTest`, etc.) are framework-independent — they work the same from both.

`AbstractTest` tests mutate the UI on the EDT, drive components programmatically, and capture screenshots for regression. This document covers the API and the screenshot comparison algorithm, plus how to use screenshots to evaluate UI you just generated.

## Where tests live

```
common/src/test/java/<pkg>/MyTest.java
```

Tests in this folder are picked up by the CN1 test runner. The runner can execute on the desktop (simulator) and also on real devices via `cn1:test`. Surefire (`mvn test`) is mapped to `cn1:test` in the standard initializr POM so the usual `mvn test` works.

## The `AbstractTest` base class

```java
import com.codename1.testing.AbstractTest;
import com.codename1.testing.TestUtils;
import com.codename1.ui.Display;

public class HelloFormTest extends AbstractTest {
    @Override
    public boolean shouldExecuteOnEDT() {
        return true;     // Set true when you touch UI; false for pure logic tests.
    }

    @Override
    public boolean runTest() throws Exception {
        // Build the screen you want to verify.
        new MyAppName().runApp();
        TestUtils.waitForFormTitle("Hello");

        TestUtils.assertLabel("Hello World");
        TestUtils.clickButtonByLabel("Tap me");
        TestUtils.waitForFormTitle("Tapped");

        return screenshotTest("tapped-screen");
    }
}
```

Returning `true` means pass. Returning `false` or throwing means fail.

`AbstractTest` exposes a fluent set of assertions:

```java
assertTrue(condition, "message");
assertFalse(condition, "message");
assertEqual(expected, actual, "message");
assertNotEqual(notExpected, actual, "message");
assertNotNull(value, "message");
assertNull(value, "message");
```

## `TestUtils` — interacting with the UI

The most useful helpers (all under `com.codename1.testing.TestUtils`):

| Method | Purpose |
| --- | --- |
| `waitForFormTitle(String, long timeout)` | Block until a form with the given title is current. |
| `waitForUnnamedForm(long timeout)` | Wait for any unnamed form (e.g. modal closed). |
| `findByName(String)` / `findByPath(String)` | Locate a component by `setName` value or path. |
| `clickButtonByLabel(String label)` | Click any button with the given displayed text. |
| `clickButtonByName(String name)` | Click by `setName(...)` value. |
| `setText(String name, String text)` | Type into a `TextField` / `TextArea`. |
| `assertLabel(String text)` | Assert a label with this text exists on the current form. |
| `assertTitle(String title)` | Assert the current form's title. |
| `keyPress(int code)` / `keyRelease(int code)` | Simulate hardware keys. |
| `screenshotTest(String name)` | See below. |

Always `waitForFormTitle(...)` after triggering an async screen transition — `show()` is async, so the next assertion may run before the new form appears otherwise.

## `screenshotTest(name)` — visual regression

```java
return screenshotTest("settings-screen");
```

Semantics:

1. Capture the current form to an in-memory image.
2. Look up `settings-screen.png` in CN1 `Storage` (a per-app key-value store rooted at `~/.codenameone/<your.app>/` on the simulator).
3. **If no baseline exists**: save the captured image as the baseline, log a warning, return `true`. **First run always passes.**
4. **If a baseline exists**: compare pixel-by-pixel with tolerance. Return `true` if within tolerance, `false` otherwise. On failure also save the captured image as `settings-screen.png.fail` so you can diff visually.

The tolerance algorithm (`TestUtils.imagesWithinTolerance`) is intentionally lenient so it doesn't flake on minor anti-aliasing/native-rendering noise:

- Per-channel delta of ≤3 is "the same pixel"
- Up to **1% of pixels** may exceed that delta
- Average absolute channel delta across the whole image must be ≤ 2.5
- RMS channel delta must be ≤ 5.0

Pictures that differ structurally (a button moved 10px, a color changed by 20 units, text is missing) will reliably fail. Tiny rendering noise won't.

## Where baselines live and how to refresh them

Baselines are stored under CN1 `Storage`. On the simulator:

```
~/.codenameone/<package.MyAppName>/   (Mac/Linux)
%APPDATA%\.codenameone\<package.MyAppName>\   (Windows)
```

You'll find `<name>.png` and `<name>.png.fail` after a failed run. To refresh a baseline because the screen *should* have changed:

```bash
# Delete the stale baseline; the next test run will record a new one and pass:
rm ~/.codenameone/com.example.myapp.MyAppName/settings-screen.png

# Run twice — first records baseline, second confirms it's stable:
mvn -pl common cn1:test
mvn -pl common cn1:test
```

The two-run pattern is important: a single passing run only proves "we wrote a file", not "we compare equal across runs". The second run is the one that actually validates determinism.

## Using screenshots to evaluate UI you just generated

**Caveat from prior incidents**: a screenshot test only proves "this run matches the saved baseline". If you generated the baseline from the same code you just wrote, the test passes trivially — that proves consistency, not correctness.

The right loop:

1. Write/modify the UI.
2. Run the simulator (`mvn -pl common cn1:run`) and **visually confirm** the screen looks right at least once.
3. *Then* write a `screenshotTest("name")` and run it twice to lock in the baseline.
4. On the next CI run, the screen rendering must continue to match — that's where the test earns its keep.

A screenshot test where the baseline was never visually inspected is theatrical. Don't ship it as "validated".

## Tests that don't need UI

For pure-logic tests (parsers, models, business rules), set `shouldExecuteOnEDT()` to `false` and skip the UI helpers:

```java
public class CurrencyFormatterTest extends AbstractTest {
    @Override public boolean shouldExecuteOnEDT() { return false; }
    @Override public boolean runTest() {
        assertEqual("$12.50", new CurrencyFormatter().format(12.50));
        return true;
    }
}
```

These run faster because they don't pump the EDT.

## Capturing a screenshot programmatically (no test)

```java
import com.codename1.ui.Display;
import com.codename1.ui.Image;
import com.codename1.ui.util.ImageIO;
import com.codename1.io.Storage;

Image img = Display.getInstance().captureScreen();
ImageIO io = ImageIO.getImageIO();
io.save(img, Storage.getInstance().createOutputStream("debug.png"),
        ImageIO.FORMAT_PNG, 1);
```

Useful inside a `Display.callSerially` block while debugging. Files saved to `Storage` end up in the per-app storage folder above.

## Running tests against multiple form factors

`AbstractTest.shouldExecuteOnFormFactor(...)` doesn't exist out of the box, but you can branch in `runTest()`:

```java
if (Display.getInstance().isTablet()) {
    return screenshotTest("home-tablet");
} else {
    return screenshotTest("home-phone");
}
```

The simulator's skin chooser controls which factor is reported. Run the test suite in two skins to validate both.

## Tying it together: a UI evaluation workflow

When the user asks "evaluate this screen looks right":

1. Open simulator: `mvn -pl common cn1:run`
2. Drive it to the relevant screen, screenshot manually (Simulator menu → Save Screenshot) into a known folder.
3. Show the image / inspect it visually before claiming the change is correct.
4. Optionally write `screenshotTest("...")` and run it twice to lock in a baseline for regression.

If you cannot run the simulator (headless container, no display), **say so explicitly** — do not claim UI correctness based purely on a passing screenshot test you just generated, because you'd be comparing the new code to itself.

## Common pitfalls

| Symptom | Cause |
| --- | --- |
| Test "passes" on first run, "fails" on second | The first run created a baseline; an unrelated UI change shifted pixels. Inspect `<name>.png.fail`. |
| `screenshotTest` returns `true` even when screen is obviously wrong | The baseline was captured from the broken state. Delete the baseline, fix the screen, re-record. |
| Tests pass locally, fail in CI | Different display density / font hinting. Use larger features (avoid 1-pixel borders) or relax tolerance by capturing at multiple densities. |
| EDT hangs | A test marked `shouldExecuteOnEDT()` true did `Thread.sleep` inside the EDT. Use `TestUtils.waitForFormTitle` or `callSeriallyAndWait` for synchronization. |
| `NullPointerException` on `Display.getInstance().captureScreen()` | The test ran before a Form was shown. Make sure `runApp()` or `show()` was called first. |
