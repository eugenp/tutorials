# Porting Android (XML + Kotlin/Java) to Codename One

Codename One's component model is similar enough to Android's that you can usually translate screens one-to-one. As with the HTML conversion guide (`references/html-css-cheatsheet.md`), the layout system is the part that differs most — Android XML doesn't translate; build the same screen in Java + CN1 CSS.

## Android view → CN1 component

| Android `View` | CN1 component | Notes |
| --- | --- | --- |
| `LinearLayout` (vertical) | `BoxLayout.y()` | |
| `LinearLayout` (horizontal) | `BoxLayout.x()` | |
| `RelativeLayout` / `ConstraintLayout` | `LayeredLayout` with `LayeredLayoutConstraint` | Percent or mm insets and `setReferenceComponent*` for "below this view". |
| `FrameLayout` | `LayeredLayout` | Same stacking semantics. |
| `GridLayout` (Android's, not CSS) | `GridLayout` | |
| `RecyclerView` | `InfiniteContainer` | The pagination/recycling story maps directly. |
| `ScrollView` (vertical) | `Container.setScrollableY(true)` | Wrap your content. |
| `HorizontalScrollView` | `Container.setScrollableX(true)` | |
| `NestedScrollView` | **Don't nest scrollables in CN1** — see *Scrolling* in `references/ui-components.md`. |
| `Toolbar` / `ActionBar` | `Form.getToolbar()` | Already on every Form. Use `addMaterialCommandToRightBar/SideMenu` for actions. |
| `BottomNavigationView` | `Tabs` (placed in `BorderLayout.SOUTH`) or `Toolbar` bottom commands. | |
| `NavigationView` (drawer) | `Toolbar.addCommandToSideMenu(...)` / `addMaterialCommandToSideMenu(...)`. | |
| `TextView` | `Label` (single line) / `SpanLabel` (wrapped) | |
| `EditText` | `TextField` (single line) / `TextArea` (multi-line) | Set `setConstraint(...)` for the keyboard type. |
| `Button` / `MaterialButton` | `Button` | Use `Button(String, char)` to set a Material icon directly. |
| `ImageView` | `Label` with an `Image`, or `URLImage` for remote, or `Label.setMaterialIcon(char)` for an icon glyph. | |
| `Switch` / `CheckBox` / `RadioButton` | `Switch` / `CheckBox` / `RadioButton` | `RadioButton` needs a `ButtonGroup`. |
| `Spinner` | `Picker` (string list) | Do **not** use `ComboBox`. |
| `ProgressBar` (determinate) | `Slider` (set max, `setEditable(false)`) | |
| `ProgressBar` (indeterminate) | `InfiniteProgress` | |
| `FloatingActionButton` | `com.codename1.components.FloatingActionButton` | `createFAB(char icon).bindFabToContainer(...)`. |
| `Dialog` / `AlertDialog` | `Dialog.show(...)` | |
| `Toast` | `ToastBar.showMessage(...)` | |
| `Fragment` | A factory method returning a configured `Container`, attached to a Form. CN1 has no Fragment lifecycle — keep state in regular Java objects. |
| `Activity` | A separate `Form` class (or factory). Navigation = `nextForm.show()`. |
| `Intent` (in-app) | Direct method call to the next Form's factory. |
| `Intent` (external — `tel:` / `mailto:` / URL) | `Display.getInstance().execute("tel:...")`. |
| `RecyclerView.Adapter` | Implement `InfiniteContainer.fetchComponents(int, int)` or pass items to `MultiList`. |
| `WebView` | `BrowserComponent`. |
| `BottomSheetDialogFragment` | `InteractionDialog` (slides up from the bottom of the layered pane). |

## Android XML → CN1 Java

CN1 has no XML layout for screens (the GUI builder uses its own format). Translate Android XML directly to Java:

```xml
<!-- Android: res/layout/profile.xml -->
<LinearLayout android:orientation="vertical" android:padding="16dp">
    <TextView android:text="@string/name" style="@style/Headline" />
    <EditText android:hint="@string/name_hint" />
    <Button android:text="@string/save" style="@style/PrimaryButton" />
</LinearLayout>
```

```java
// CN1
Container col = new Container(BoxLayout.y());
col.setUIID("ProfileCard");            // padding/margin lives in CSS

Label headline = new Label(L10n.get("name"));
headline.setUIID("Headline");

TextField nameField = new TextField();
nameField.setHint(L10n.get("name_hint"));

Button save = new Button(L10n.get("save"));
save.setUIID("PrimaryCta");

col.add(headline).add(nameField).add(save);
```

```css
/* Android themes ~= CN1 CSS rules. */
ProfileCard { padding: 2mm; }
Headline { font-family: "native:MainBold"; font-size: 4mm; color: #0f172a; }
PrimaryCta { background-color: #1d4ed8; color: #ffffff; border-radius: 3mm; padding: 2mm 4mm; }
```

## Threading and main thread — the most important conversion

In Android the **UI thread** is the only safe place to touch views, and you marshal work back from a background thread with `Handler.post(...)`, `runOnUiThread(...)`, or `View.post(...)`.

In Codename One the **EDT** (Event Dispatch Thread) plays the same role, and the equivalent marshaling primitive is **`Display.getInstance().callSerially(...)`**.

```java
// Android:
new Thread(() -> {
    final List<Item> items = api.fetch();
    runOnUiThread(() -> render(items));
}).start();

// Codename One:
Display.getInstance().startThread(() -> {
    final List<Item> items = api.fetch();
    Display.getInstance().callSerially(() -> render(items));
}, "items-fetcher").start();
```

Three things to note:

1. **`Display.startThread(Runnable, String)`** is preferred over `new Thread(...)`. It carries CN1's per-platform thread setup (the iOS autorelease pool, EDT-aware cleanup). Plain `new Thread(...).start()` works in the simulator but can leak resources on iOS.
2. **`callSerially(Runnable)`** is the Android `runOnUiThread` analogue. It queues the runnable on the EDT and returns immediately. Use this whenever a background thread needs to update UI.
3. **`callSeriallyAndWait(Runnable)`** blocks the caller until the runnable has run on the EDT. Equivalent to Android's `Handler#runWithScissors`. Use rarely — it can deadlock if called from the EDT itself.

The EDT/UI-thread rule is identical in spirit to Android: never touch a component from anywhere but the EDT. Listeners (`Button.addActionListener`, `Form.show()`, `Display.callSerially`) all run on the EDT, so most code is already safe — only spawned threads and network callbacks need explicit marshaling.

## Android idioms that DO NOT translate

| Android | Why | What to do in CN1 |
| --- | --- | --- |
| `Context` everywhere | CN1 has no `Context`. | Use `Display.getInstance()` or static singletons. |
| `findViewById(R.id.x)` | No XML view inflation. | Hold component references as fields after constructing. |
| `Handler.post(...)` / `runOnUiThread(...)` | No `Handler`. | `Display.getInstance().callSerially(...)`. |
| `LiveData`, `ViewModel`, `Flow` (Jetpack) | No Jetpack. | `com.codename1.properties.*` for observable property models; chain regular callbacks otherwise. |
| `Room` | No Room. | `com.codename1.db.Database` (SQLite) + a thin manual model layer. |
| `SharedPreferences` | Different API, same intent. | `com.codename1.io.Preferences` (drop-in semantically). |
| `Picasso` / `Glide` for image loading | Not available. | `URLImage.createToStorage(...)`. |
| `Retrofit` / `OkHttp` | Not in the JDK subset. | `Rest.get/post(...).fetchAsJsonMap(...)` — see `references/java-api-subset.md`. |
| `Coroutines` / `RxJava` | No coroutines runtime; no RxJava in the subset. | `Display.startThread(...)` + `Display.callSerially(...)`; chain callbacks. |
| `R.string.xxx`, `R.drawable.xxx` | No resources system. | `UIManager.getInstance().localize("name", "default")` for strings (reads `messages.properties` bundles); load images by file name `Image.createImage("/foo.png")`. |
| `Permission` manifest entries | Different mechanism. | `Display.requestPermission(...)` at runtime + `codename1.arg.android.xPermissions` build hint (see `references/build-hints.md`). |
| `Activity onCreate/onResume/onPause` | No Activity lifecycle. | Override `Lifecycle.init/start/stop` (app-level) and react to `Form.show()` (per-screen). |
| `AsyncTask` | Deprecated upstream too. | `Display.startThread(...)` + `callSerially(...)`. |
| `BroadcastReceiver` | No analog at the CN1 level. | For lifecycle-related events (`Lifecycle.start()` etc.) use the CN1 lifecycle. For external system events use a native interface. |
| `Service` / `WorkManager` | No background-service runtime in CN1. | Background fetch with `BackgroundFetch` cn1lib for periodic wake-ups; for long-running tasks, run them in a foreground push handler. |

## Android resources → CN1

| Android | CN1 |
| --- | --- |
| `res/values/strings.xml` | `common/src/main/l10n/messages.properties` (and per-locale `messages_de.properties`, etc.) — see `references/build-and-run.md`. |
| `res/drawable/foo.png` | `common/src/main/resources/foo.png` (flat namespace — `references/java-api-subset.md`). |
| `res/values/colors.xml` | Theme constants in `theme.css` under `#Constants { ... }`. |
| `res/font/x.ttf` | `common/src/main/css/fonts/x.ttf`, declared via `@font-face` in `theme.css`. |
| `res/raw/seed.json` | `common/src/main/resources/seed.json` — read with `Display.getInstance().getResourceAsStream("/seed.json")`. |
| `res/layout/*.xml` | No equivalent — build the layout in Java (`Container` + `Layout` + components). |

## Kotlin

Kotlin source compiles to JVM bytecode that CN1 cross-builds the same way as Java. The constraint is the **API subset**, not the language — see `references/java-api-subset.md`. Kotlin standard library calls that go through `java.util.*` / `java.lang.*` work; calls into Kotlin-only Android extensions (`Activity.findViewById`, the `databinding` plugin, etc.) don't.

If the project's `codenameone_settings.properties` already has `codename1.kotlin=true` and there's a `common/src/main/kotlin/` directory, the build chain is in place — you can write screens in Kotlin and they compile through the same `cn1:run` / `cn1:build` goals.
