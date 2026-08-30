# Mobile Adaptability Reference

A Codename One app must look right on a 4-inch iPhone SE, a 6.7-inch Pro Max, a 10-inch tablet, in portrait and landscape, with the system "Larger Text" accessibility setting on or off, and in light/dark mode. CSS has no viewport-size `@media` queries (only `prefers-color-scheme: dark` is recognized — see `references/css.md`), so per-form-factor layouts are branched in Java rather than in CSS.

## Density-independent units

| Unit | Meaning | When to use |
| --- | --- | --- |
| `mm` | Real-world millimeters | **The default.** Use for all padding, margins, font sizes, border radii. |
| `%` | Percent of parent container | For percentage-based widths/heights in `LayeredLayoutConstraint`. |
| `px` | Device pixels | Only when you need pixel-perfect alignment of a 1px hairline. |
| `pt` | Font points | Rarely used; CN1 fonts are typically sized in `mm`. |

```css
Button {
    padding: 2mm 4mm;
    font-size: 4mm;
    border-radius: 3mm;
    margin: 1mm;
}
```

The CN1 runtime converts `mm` to pixels via `Display.getInstance().convertToPixels(float mm)`, which factors in the device's reported DPI. The result is that a 4mm font appears as 4mm regardless of pixel density.

Programmatic equivalent:

```java
int px = Display.getInstance().convertToPixels(2.5f);   // 2.5mm → device pixels
```

## Form factor detection

```java
Display d = Display.getInstance();

boolean tablet      = d.isTablet();
boolean portrait    = d.isPortrait();
int widthPx         = d.getDisplayWidth();
int heightPx        = d.getDisplayHeight();
float widthMm       = widthPx / (float) d.convertToPixels(1f);

// React to orientation changes
form.addOrientationListener(evt -> rebuildLayout(form));
```

The most common branch is `if (Display.getInstance().isTablet())` — switch to a master/detail split layout on tablets.

## Phone-vs-tablet master/detail

```java
private void buildLayout(Form f, List<Item> items) {
    f.removeAll();
    if (Display.getInstance().isTablet()) {
        // Two-pane: list on the left (35%), detail on the right (65%)
        Container split = new Container(new BorderLayout());
        split.add(BorderLayout.WEST, buildList(items, this::showDetail));
        split.add(BorderLayout.CENTER, detailPane);
        f.add(BorderLayout.CENTER, split);
    } else {
        // Single-pane: list only; tapping pushes a new Form for detail
        f.add(BorderLayout.CENTER, buildList(items, item -> openDetailForm(item)));
    }
    f.revalidate();
}
```

Wire `Form.addOrientationListener` to call `buildLayout` again when rotation changes the form factor.

## `LayeredLayout` — the percent-based responsive layout

`LayeredLayout` stacks children on top of each other. Each child has insets you can specify as percent or `mm`, anchored to any of the four edges or to other components.

```java
Container c = new Container(new LayeredLayout());
LayeredLayout ll = (LayeredLayout) c.getLayout();

Component bg = new Label(); bg.setUIID("HeroBg");
Component title = new Label("Welcome"); title.setUIID("HeroTitle");

c.add(bg).add(title);

ll.setInsets(title, "20% 10% auto 10%");        // top 20%, right 10%, auto bottom, left 10%
ll.setInsets(bg,    "0 0 0 0");                  // full bleed
```

Inset syntax mirrors CSS shorthand: `top right bottom left`. Use `auto` to let the size be computed.

`LayeredLayoutConstraint` also supports referencing other components: "place title 2mm under bg" without absolute coordinates. This makes responsive layouts trivial:

```java
LayeredLayoutConstraint cc = ll.createConstraint();
cc.setInsets("0 0 auto 0").setReferenceComponentTop(bg, 1f);  // top edge = 100% of bg
ll.addLayoutComponent(cc, label, c);
```

## Scaling fonts with accessibility settings

Add this to `theme.css`:

```css
#Constants {
    useLargerTextScaleBool: true;
}
```

This makes CN1 honor the system "Larger Text" / "Dynamic Type" accessibility setting. When the user bumps text size in iOS Settings / Android Display, your app's fonts scale automatically.

The initializr's barebones template already adds this constant by default.

## Safe-area insets (notches, home indicators)

`Toolbar` and `Form` handle the system safe area automatically on iOS and Android. You don't usually need to think about it. If you draw outside the toolbar (e.g. a full-bleed hero image), make sure the toolbar UIID has `padding-top` that accounts for the notch:

```css
Form {
    cn1-include-native-bool: true;     /* include native status bar area in form bounds */
}
Toolbar {
    /* iOS notch / Android status bar already handled by the framework */
}
```

Programmatic access:

```java
int safeTop    = Form.getCurrentForm().getTitleStyle().getPaddingTop();
int safeBottom = Display.getInstance().getKeyboardHeight();  // when keyboard is up
```

## Keyboard handling

When a `TextField` is focused, the soft keyboard appears and overlaps the form. CN1 emits a "size changed" event when this happens:

```java
form.addOnSizeChangedListener(evt -> {
    // Form may have shrunk vertically because of the keyboard.
    if (Display.getInstance().isVirtualKeyboardShowing()) {
        scrollFocusedFieldIntoView(form);
    }
});
```

Most of the time you don't need this — wrapping the form's content in a `Container.setScrollableY(true)` makes the focused field auto-scroll into view.

## RTL (right-to-left) languages

```java
form.setRTL(true);                                                 // per-form
UIManager.getInstance().getLookAndFeel().setRTL(true);             // global
```

```css
#Constants {
    rtlBool: true;
}
```

When RTL is enabled, `BorderLayout.WEST` becomes the right side, text flows right-to-left, and `mm` insets are mirrored. Arabic and Hebrew locales should set this — the initializr's bundled `messages_ar.properties` / `messages_he.properties` ship for that purpose.

## Animation that respects layout

```java
// Mutate the layout (e.g. move a panel, hide a component)
sidePanel.setHidden(!sidePanel.isHidden());

// Animate the change
form.animateLayout(250);
```

`animateLayout(durationMs)` re-runs the layout pass and tweens children from their previous positions to their new ones. Works on every layout (`BorderLayout`, `BoxLayout`, `LayeredLayout`, etc.).

## Common adaptability patterns

### Card grid that responds to width

```java
int cols = Display.getInstance().isTablet() ? 3 : 1;
Container grid = new Container(new GridLayout(rowsFor(items, cols), cols));
for (Item it : items) grid.add(renderCard(it));
```

### Auto-scaling button row

```java
// On tablet show three buttons side by side; on phone stack vertically.
Container row;
if (Display.getInstance().isTablet()) {
    row = BoxLayout.encloseX(buyBtn, shareBtn, saveBtn);
} else {
    row = BoxLayout.encloseY(buyBtn, shareBtn, saveBtn);
}
form.add(BorderLayout.SOUTH, row);
```

### Image that fills width without distorting

```java
Image src = Image.createImage("/banner.jpg");
Image scaled = src.scaledWidth(Display.getInstance().getDisplayWidth());
form.add(new Label(scaled));
```

For lazy network images use `URLImage.createToStorage(..., URLImage.RESIZE_SCALE_TO_FILL)`.

### Orientation-aware media display

```java
form.addOrientationListener(evt -> {
    if (Display.getInstance().isPortrait()) {
        videoComponent.setHeight(Display.getInstance().convertToPixels(60f));
    } else {
        videoComponent.setHeight(Display.getInstance().getDisplayHeight());   // landscape: full screen
    }
    form.revalidate();
});
```

## Testing on multiple skins

The CN1 simulator has a "Skin" menu — pick "iPhone 15 Pro", "Pixel 8", "iPad", "Galaxy Tab S9". Restart the simulator to switch skin. If your screen looks broken on one but fine on another, that's an adaptability bug. Fix it before declaring "done".

## What CN1 explicitly does NOT do

- Dark mode is opt-in: write a `@media (prefers-color-scheme: dark) { ... }` block in `theme.css` to recolor UIIDs (see `references/css.md`). For runtime overrides — toggling dark mode in-app regardless of system preference — call `Display.getInstance().setDarkMode(Boolean)`; read the current state with `Display.getInstance().isDarkMode()`.
- Orientation lock at runtime — call `Display.getInstance().lockOrientation(boolean portrait)` to pin the orientation while the app is running; `unlockOrientation()` releases it. Check `canForceOrientation()` first (some browsers / JavaScript runtimes don't allow it outside full-screen mode). The old `codename1.arg.ios.orientation` / `codename1.arg.android.screenOrientation` build hints are **discouraged** — `lockOrientation` works portably and dynamically across all platforms.
- No automatic "iPad split view" support (the macOS-style split view) — design master/detail manually with `BorderLayout`.
- No `vh` / `vw` units in CSS. Use percent insets in `LayeredLayoutConstraint`.
