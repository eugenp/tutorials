# Codename One CSS Reference

`common/src/main/css/theme.css` is the entry point. The CN1 plugin's CSS compiler (`compile-css` goal, runs in `process-resources`) parses it and bakes the result into `common/target/classes/theme.res`. The runtime then loads styles by UIID from that binary resource — no CSS exists at runtime.

This is **not** web CSS. It is a deliberate subset designed for native rendering on mobile. Treat any unfamiliar property as "probably unsupported" until you check.

## Selector model

The only selector form is:

```
<UIID>[.<state>] [, <UIID>[.<state>] ...] { ... }
```

- `UIID` matches `component.getUIID()` — e.g. `Button`, `Form`, `Label`, `Title`, `Toolbar`, `MyCustomCard`.
- `.<state>` matches one of the built-in style variants: `.pressed`, `.disabled`, `.selected`.
- Multiple UIIDs may be grouped with commas.
- **No descendant combinators**, **no attribute selectors**, **no `*`**, **no `:hover`** on touch components (desktop / JavaScript ports do expose pointer-hover listeners in Java — see `references/ui-components.md`).
- **`@media` queries**: only `@media (prefers-color-scheme: dark)` is honored — see *Dark mode* below. No viewport-size media queries.

```css
/* Valid */
Button { ... }
Button, Label { ... }
Button.pressed { ... }
MyCard.selected { ... }
@media (prefers-color-scheme: dark) {     /* honored — rewrites selectors as Dark variants */
    Form { background-color: #0f172a; }
}

/* INVALID — these silently fail or break the compile */
Form Button { ... }              /* no descendant combinator */
Button:hover { ... }              /* no :hover */
@media (max-width: 600px) { ... } /* no size media queries */
.btn { ... }                      /* class selectors don't exist; use UIIDs */
```

## Special case: `Container` UIID

`Container` is a structural component, **not** a styled one. Layout containers wrap other components; they should not paint backgrounds, borders, or contribute spacing of their own. Treat the default `Container` UIID as load-bearing:

- **Never restyle `Container`.** Don't add background-color, border, padding, or margin to a rule targeting `Container { ... }`. Many built-in screens nest `Container`s several deep; styling the base UIID at theme level produces gnarly visual surprises (double padding, accent color bleeding into the wrong region, layouts that shift on certain platforms).
- The default style for `Container` should remain: **transparent background, no border, 0 padding, 0 margin**. If the initializr or a cn1lib has accidentally restyled it, restore those defaults before doing anything else.
- If you need a styled "box" (a card, a banner, a section), give the wrapper its **own** UIID (e.g. `Card`, `HeroSection`) and style that UIID instead. The Java side mirrors this: `Container c = new Container(BoxLayout.y()); c.setUIID("Card");`.

The same caveat applies to `ContentPane` and `CenterAlignedContentPane` (the Form's inner container) to a lesser extent — change them only when you specifically want to recolor the form's background and you understand the cascading impact.

## Modern theme accent colors

Initializr projects generated since the introduction of the modern theme inherit a base palette plus an **accent color**. The fastest way to recolor a new app is to override the accent. The relevant UIIDs are bound to it:

```css
/* In theme.css — set this near the top, before other overrides. */
Button {
    background-color: #1d4ed8;     /* your accent */
    color: #ffffff;
    border: 1px solid #1d4ed8;
    border-radius: 3mm;
}
Button.pressed {
    background-color: #1e3a8a;     /* ~22% darker */
    border: 1px solid #1e3a8a;
}
Title, TitleCommand { color: #1d4ed8; }
```

This is the right entry point for "make the app look like our brand". Sweeping changes can be applied this way with no need to subclass components.

## Supported properties

| Category | Properties |
| --- | --- |
| Background | `background-color`, `background-image`, `background-repeat`, `background-position`, gradient backgrounds (specific subset — see *Gradients* below) |
| Border | `border`, `border-color`, `border-style`, `border-width`, `border-radius`, `border-image` (9-piece) — see *Borders* below |
| Spacing | `margin`, `margin-top/right/bottom/left`, `padding`, `padding-top/right/bottom/left` |
| Text | `color`, `font-family`, `font-size`, `font-weight`, `font-style`, `text-decoration`, `text-align` (with `align` fallback) |
| Layout (per-component) | `cn1-text-align`, `cn1-include-native-bool` |
| Theme constants | Any key inside `#Constants { ... }` (see *Theme constants* below) |

Anything not on this list — `display`, `position`, `float`, `flex`, `grid`, `transform`, `box-shadow`, `opacity`, `overflow`, `z-index` — is **not honored**. Use Java layouts for arrangement.

## No negative values

`margin: -2mm` is **not supported**. The CN1 layout system does not allow negative margins, padding, or border insets. If a CSS rule resolves to a negative value the compiler either ignores it or clamps to zero, depending on the property.

If you find yourself wanting `margin-top: -2mm` (e.g. to overlap a card on top of a hero image), do it the CN1 way: use `LayeredLayout` with percent/mm insets — it's purpose-built for overlap.

## The CN1 box model — why borders need matching padding

In standard CSS the **border draws between padding and margin**, and the border has zero effect on the inner content area beyond its stroke width.

In Codename One the **border is painted on top of the component's edge**, inside the padding region. If the underlying component has no padding, a thick or rounded border will visually clip the content. As a rule of thumb:

> Whenever you set a non-trivial `border` (especially `RoundBorder`, `RoundRectBorder`, or a 9-piece image border), set padding to **at least the visual thickness of that border**. Otherwise the label/text inside the bordered component gets eaten by the border curve.

Example — a rounded primary button:

```css
PrimaryCta {
    background-color: #1d4ed8;
    color: #ffffff;
    border: 1px solid #1d4ed8;
    border-radius: 3mm;
    padding: 2mm 4mm;     /* MUST be >= the corner radius so text isn't clipped */
}
```

Symptoms when padding is too small:
- Text appears off-center or clipped at the curved corners.
- Tap target is smaller than it looks.
- A focused/pressed state visibly resizes the component because pressed-state border is thicker.

This is also why setting `border-radius: 10mm` on a tiny `Label` with `padding: 0` produces a label that looks broken — the rounded border is drawn but eats the text.

## Borders — recommended types, and what to avoid

CN1 supports four practical border styles. Two of them are the right tools 99% of the time; two exist for compatibility and should be considered fallbacks.

| Style | When to use | Notes |
| --- | --- | --- |
| **`RoundBorder`** (a circle / pill shape) | Circular FAB, avatar, pill button | Use `border-radius: <bigger than half the height>;` or `Border.createRoundBorder(...)` in Java. Native-rendered, scales perfectly. |
| **`RoundRectBorder`** (rounded rectangle with optional shadow) | Cards, normal rounded buttons, dialogs | Auto-selected by the CSS compiler for moderate `border-radius` values. Supports shadow via `RoundRectBorder.create().shadowOpacity(...)`. **Always pair with padding ≥ corner radius.** |
| Solid `border: <w> solid <color>` | Plain rectangles | Vector, scales well. Fine. |
| `border-image: url('...9.png') ...;` (**9-piece image border**) | Last resort | See warning below. |

**Why the 9-piece image border is a fallback**, not a default:

- It is rasterized: a fixed-resolution PNG stretched at runtime, so it does not scale crisply across device densities. On a high-DPI screen it gets blurry; on a low-DPI screen it can alias badly.
- It costs memory: the image is decoded and held in the resource map.
- It is brittle: the 1-pixel control border that defines the stretchable region must be edited carefully (Android Studio's draw-9-patch tool is the canonical authoring path). If the wrong pixels mark "stretch" you get visible seams.
- **The screenshot tests in `references/testing-and-screenshots.md` are particularly sensitive to 9-piece borders** — even minor cross-device anti-aliasing differences accumulate around the stretched seams and push the comparison over the tolerance threshold.

Prefer `RoundBorder` / `RoundRectBorder` for any rounded UI. Reach for `border-image` only when you need a shape neither vector border can produce (asymmetric bubble tails, decorative frames, etc.). When you must, drop the image at `common/src/main/css/images/<name>.9.png` and reference it as `border-image: url('/<name>.9.png') <top> <right> <bottom> <left>;`.

## Units

`mm`, `px`, `%`, named integers. **Prefer `mm`** because pixel density varies wildly across devices:

```css
Button {
    padding: 2mm 4mm;         /* GOOD — same physical size on any screen */
    border-radius: 3mm;
    margin: 1mm;
}
Button {
    padding: 12px;            /* BAD — tiny on 3x density, huge on 1x */
}
```

CN1 internally converts `mm` to pixels via `Display.convertToPixels(float)`.

## Colors

```css
Button {
    background-color: #1d4ed8;     /* hex, the most reliable form */
    color: rgb(255, 255, 255);     /* rgb() works */
    border: 1px solid #475569;
}
```

**Named colors**: only a few are recognized by the initializr's pre-processor (`pink`, `orange`, `purple`, `yellow`, `gray`, `grey`). Beyond that, **use hex**. CSS like `color: red;` may not compile.

Alpha: use `rgba(r, g, b, a)` where `a` is 0–255 in some compiler versions and 0.0–1.0 in others — test it. The safe alternative is to leave the color opaque and animate transparency programmatically via `setAlpha(int 0..255)` on the component style.

## Gradients

CSS gradients compile to a native `theme.res` descriptor. Supported functions:

- `linear-gradient(<angle>, <stops...>)` — angle in `deg` / `rad` / `turn`, or `to <side>` / `to <side1> <side2>`.
- `radial-gradient([circle|ellipse] [<extent>] [at <position>], <stops...>)` — extent: `closest-side` / `closest-corner` / `farthest-side` / `farthest-corner` (default), or explicit radii in percent.
- `conic-gradient([from <angle>] [at <position>], <stops...>)` — 0° points up, sweep clockwise.
- `repeating-linear-gradient(...)` / `repeating-radial-gradient(...)` — stop pattern tiles outward.

```css
HeroCard      { background: linear-gradient(135deg, #ff0080 0%, #ff8c00 50%, #40e0d0 100%); }
Spotlight     { background: radial-gradient(circle farthest-corner at 30% 30%, #fff, #001 70%); }
ColorWheel    { background: conic-gradient(from 0deg at 50% 50%, red, yellow, green, blue, red); }
DiagonalStripes { background: repeating-linear-gradient(45deg, #eee 0%, #ccc 10%); }
```

Programmatically, use the `Gradient` hierarchy (`LinearGradient`, `RadialGradient`, `ConicGradient` — `Paint` subclasses analogous to `Shape`):

```java
LinearGradient g = new LinearGradient(135f,
        new int[]   { 0xffff0080, 0xffff8c00, 0xff40e0d0 },
        new float[] { 0f,         0.5f,       1f });
card.getAllStyles().setBackgroundType(Style.BACKGROUND_GRADIENT_LINEAR);
card.getAllStyles().setGradient(g);

// Or fill a rect directly via Graphics:
graphics.fillGradient(g, 0, 0, w, h);
```

CSS background types map 1:1: `BACKGROUND_GRADIENT_LINEAR` / `_REPEATING_LINEAR` ↔ `LinearGradient`, `BACKGROUND_GRADIENT_RADIAL_FULL` / `_REPEATING_RADIAL` ↔ `RadialGradient`, `BACKGROUND_GRADIENT_CONIC` ↔ `ConicGradient`.

## Filter and backdrop-filter

`filter` and `backdrop-filter` accept a chain of functions:

```css
Overlay   { background: rgba(0, 0, 0, 0.4); backdrop-filter: blur(12px); }
BlurImg   { filter: blur(4px); }
Faded     { filter: brightness(0.6) contrast(1.2); }
Grayscale { filter: grayscale(1); }
Sepia     { filter: sepia(0.8) saturate(1.1); }
```

Supported functions: `blur(<length>)`, `brightness(<number>)`, `contrast(<number>)`, `grayscale(<number|%>)`, `hue-rotate(<angle>)`, `invert(<number|%>)`, `opacity(<number|%>)`, `saturate(<number>)`, `sepia(<number|%>)`.

`filter:` applies to the component's own painted content; `backdrop-filter:` applies to whatever is painted behind. Radii / matrices are exposed on `Style` (`getFilterBlurRadius()`, `getFilterColorMatrix()`, etc.) and round-trip through `theme.res`.

## Dark mode

Dark mode is **inherited automatically from the OS** on iOS and Android — you don't opt in. When the system reports dark mode, `Display.getInstance().isDarkMode()` returns `true` and CN1 applies any `$Dark<UIID>` variants in your theme. If a UIID has no `$Dark` variant the regular variant is used.

To customize the dark appearance, write a `@media (prefers-color-scheme: dark)` block in `theme.css`. The CSS compiler walks rules inside that block and rewrites every selector into a `$Dark<UIID>` variant baked into `theme.res`. To force-override the platform's choice at runtime call `Display.getInstance().setDarkMode(Boolean)` (pass `null` to revert to the platform default).

```css
Form { background-color: #ffffff; color: #0f172a; }
Toolbar { background-color: #ffffff; }
Button { background-color: #f1f5f9; color: #0f172a; }

@media (prefers-color-scheme: dark) {
    Form { background-color: #0f172a; color: #e2e8f0; }
    Toolbar { background-color: #0f172a; }
    Button { background-color: #1e293b; color: #e2e8f0; }
}
```

Both the light and dark blocks must define the same UIIDs you want to recolor — the dark block does *not* inherit declarations from the light block; the compiler creates an entirely separate Dark variant per UIID.

Don't try to nest other `@media` queries inside — viewport-size queries, prefers-reduced-motion, etc. are not recognized.

## Fonts — `native:` prefix and the platform mapping

CN1 ships ten `native:` font aliases. They resolve at runtime to the platform's default UI typeface (Roboto on Android, San Francisco on iOS, system font on desktop), at the requested weight.

**Main family** (upright):

| `font-family` | Android (Roboto) | iOS (San Francisco) |
| --- | --- | --- |
| `native:MainThin` | Roboto Thin | SF Thin |
| `native:MainLight` | Roboto Light | SF Light |
| `native:MainRegular` | Roboto Regular | SF Regular |
| `native:MainBold` | Roboto Bold | SF Bold |
| `native:MainBlack` | Roboto Black | SF Black |

**Italic family** (slanted):

| `font-family` | Android (Roboto) | iOS (San Francisco) |
| --- | --- | --- |
| `native:ItalicThin` | Roboto Thin Italic | SF Thin Italic |
| `native:ItalicLight` | Roboto Light Italic | SF Light Italic |
| `native:ItalicRegular` | Roboto Italic | SF Regular Italic |
| `native:ItalicBold` | Roboto Bold Italic | SF Bold Italic |
| `native:ItalicBlack` | Roboto Black Italic | SF Black Italic |

(The simulator typically resolves these to bundled Roboto faces — what you see in the simulator should match Android.)

Aliases shorthand `native:Main`, `native:MainBold`, `native:Italic` map to `native:MainRegular`, `native:MainBold`, `native:ItalicRegular` respectively.

```css
Title { font-family: "native:MainBold"; font-size: 4mm; }
Body  { font-family: "native:MainRegular"; font-size: 3mm; }
Caption { font-family: "native:ItalicLight"; font-size: 2.5mm; }
```

### Multi-images — one image, multiple densities

A **multi-image** is a single named resource that holds several per-density variants of the same image, packaged inside `theme.res`. At runtime CN1 picks the variant whose density matches `Display.getDeviceDensity()`.

CN1 uses **density buckets**, closer to Android's `ldpi/mdpi/hdpi/...` than iOS's `1x/2x/3x`: `DENSITY_VERY_LOW` (~88 ppi), `DENSITY_LOW` (~120 ppi), `DENSITY_MEDIUM` (~160 ppi, iPhone 3GS / original iPad / Android mdpi), `DENSITY_HIGH` (~240 ppi, Android hdpi), `DENSITY_VERY_HIGH` (~320 ppi, iPhone 4 era / Android xhdpi), `DENSITY_HD` (~540 ppi, iPhone 6+ / Android xxhdpi), `DENSITY_560` (~750 ppi, Android xxxhdpi), `DENSITY_2HD` (~1000 ppi), `DENSITY_4K` (~1250 ppi).

Multi-images are **authored in the Codename One Designer** (the resource editor) — not auto-generated from CSS. Open the theme `.res` in the designer, add the source image at its native density, tick the lower-density buckets you want, and the designer creates the variants and writes them as one named entry.

At runtime, fetch the matching variant by name from the cached global resources:

```java
Image logo = Resources.getGlobalResources().getImage("logo");
```

Use multi-images for everything that ships with the app (icons, decorative graphics, splash artwork). For network-loaded images, `URLImage` handles its own density-aware caching.

See `references/java-api-subset.md` *Multi-images* for the full density table.

### SVG icons — build-time transcoder

Drop an `.svg` file into `common/src/main/css/` and reference it from CSS the same way you would a PNG:

```css
HomeIcon {
    background: url(home.svg);
    cn1-svg-width: 6mm;        /* recommended: pin physical size */
    cn1-svg-height: 6mm;
    bg-type: image_scaled_fit;
}
```

The build-time SVG transcoder parses each referenced `.svg` and emits a Java `com.codename1.ui.GeneratedSVGImage` subclass that renders the vector through the standard `Graphics` shape API. A generated `SVGRegistry` is installed automatically into every `Resources` opened in the VM — no glue code in app land. Fetching is identical to multi-images:

```java
Image home = Resources.getGlobalResources().getImage("home.svg");
```

**Sizing keys** (mirror the multi-image conventions):

- `cn1-svg-width` / `cn1-svg-height` in **mm** — recommended; the value is routed through `Display.convertToPixels()` so the icon comes out at the same physical size on every device. Use this for any SVG with non-standard intrinsic dimensions (most third-party SVGs).
- `cn1-source-dpi: <bucket>` — declares which density bucket the SVG was authored for; runtime scales by `deviceDensity / sourceDensity`. Use the same keywords multi-images do (`medium`, `high`, `very-high`, `hd`, `2hd`, `4k`, etc.).
- No hint — the SVG's declared pixel dimensions are treated as `DENSITY_MEDIUM` design pixels and scaled to the device.

**SVG coverage**: rect / circle / ellipse / line / polyline / polygon / path (full mini-language including arcs), affine transforms (translate / rotate / scale / skew / matrix), linear gradients (shape-clipped), opacity / fill-opacity / stroke-opacity (animatable), `<text>` with anchor / font-size / font-weight / font-style, SMIL animations (`<animate>` of numeric attrs, `<animateTransform>` translate / scale / rotate, `<set>`). Not (yet) supported: `<filter>` primitives, alpha `<mask>`. Radial gradients fall back to first-stop color.

For the full feature matrix and troubleshooting, point users to `docs/developer-guide/SVG-Transcoder.asciidoc`.

### Custom TTF fonts

Drop a `.ttf` (or `.otf`) under `common/src/main/css/fonts/`, then reference its **font name (not file name)** in `font-family`:

```css
@font-face {
    font-family: "Inter";
    src: url("fonts/Inter-Regular.ttf");
}
@font-face {
    font-family: "Inter Bold";
    src: url("fonts/Inter-Bold.ttf");
}

Title { font-family: "Inter Bold"; font-size: 4mm; }
Body  { font-family: "Inter"; font-size: 3mm; }
```

Custom TTF/OTF files are **packaged with the app binary** (placed under the build output so the runtime can `Font.createTrueTypeFont(name, file)` them at startup) — they are **not** embedded inside `theme.res`. That means each font you add increases the deployed app size; choose lean subsets where possible.

To load a TTF programmatically:

```java
Font inter = Font.createTrueTypeFont("Inter", "Inter-Regular.ttf")
                 .derive(3f, Font.STYLE_PLAIN);
label.getAllStyles().setFont(inter);
```

The first argument is the family name (used to look the font up); the second is the TTF file name as packaged with the app.

## Theme constants

`#Constants { ... }` in `theme.css` exposes framework-wide booleans, numbers, strings, and image references that change CN1 component behavior. The suffix on the key name is significant: `xxxBool` → boolean, `xxxInt` → integer, `xxxImage` → image lookup, otherwise → string.

```css
#Constants {
    useLargerTextScaleBool: true;          /* honor system "Larger Text" accessibility */
    rtlBool: false;                        /* default right-to-left for the whole app */
    drawMapPointerBool: true;              /* show a center marker on MapComponent */
    centeredPopupBool: true;
    statusBarHidden: false;
}
```

Read a constant at runtime with `UIManager.getInstance().getThemeConstant(name, defaultValue)`:

```java
boolean larger = UIManager.getInstance().isThemeConstant("useLargerTextScaleBool", true);
String mapTileText = UIManager.getInstance().getThemeConstant("mapTileLoadingText", "Loading...");
```

### Commonly-needed constants

| Constant | Purpose |
| --- | --- |
| `useLargerTextScaleBool` | Honor system "Larger Text" accessibility setting via `Display.getLargerTextScale()`. |
| `rtlBool` | Set the whole app to right-to-left layout. |
| `globalToobarBool` | Whether `Toolbar` is enabled by default on every new `Form`. |
| `hideBackCommandBool` | Hide the back command from the side menu when possible. |
| `hideLeftSideMenuBool` / `hideRightSideMenuBool` | Suppress the side-menu hamburger icon on one side. |
| `iosScrollMotionBool` | Use iOS-style rubber-band scroll physics (default `true` on iOS). |
| `iosStyleBackArrowBool` | Use the iOS chevron back arrow icon. |
| `paintsTitleBarBool` | Whether the title bar contributes a background fill. |
| `pureTouchBool` | Disable focus visuals (mouse/keyboard cues) — use on pure-touch builds. |
| `dlgCommandGridBool` | Lay dialog buttons out in a grid for uniform sizing. |
| `dlgInvisibleButtons` | Hex color for the separator between dialog buttons. |
| `dialogTransitionInImage` / `dialogTransitionOutImage` | Custom `Timeline` transition images for dialogs. |
| `formTransitionIn` / `formTransitionOut` | Default form-transition names. |
| `formTransitionInImage` / `formTransitionOutImage` | Custom `Timeline` transition images for forms. |
| `fadeScrollBarBool` / `fadeScrollEdgeBool` / `fadeScrollEdgeInt` | Scrollbar/edge fade behavior. |
| `centeredPopupBool` | Center popups instead of anchoring near the source component. |
| `menuImage` / `menuImageSize` | Hamburger menu icon override and size. |
| `infiniteImage` / `infiniteMaterialDesignSize` / `infiniteMaterialImageSize` / `infiniteDefaultColor` | `InfiniteProgress` appearance. |
| `mapTileLoadingImage` / `mapTileLoadingText` | `MapComponent` tile-loading placeholders. |
| `mapZoomButtonsBool` / `drawMapPointerBool` | `MapComponent` toggles. |
| `imageviewerNavigationArrowsBool` / `imageviewerThumbnailsBool` / `imageviewerThumbnailBarHeightMM` | `ImageViewer` defaults. |
| `mediaPlayImage` / `mediaPauseImage` / `mediaFwdImage` / `mediaBackImage` | Media-player icon overrides. |
| `labelGap` / `listItemGapInt` | Default gaps inside compound components. |
| `dlgButtonCommandUIID` / `dlgCommandButtonSizeInt` | Dialog button styling. |
| `comboImage` | Dropdown arrow image used by Picker/legacy ComboBox. |
| `checkBoxCheckedImage` / `checkBoxUncheckedImage` / `checkBoxCheckDisImage` / `checkBoxUncheckDisImage` / `checkBoxOppositeSideBool` | Custom checkbox iconography. |
| `defaultCommandImage` / `defaultEmblemImage` | Fallback icons for command/list rendering. |
| `iconUiid` / `textUiid` | UIID overrides for icon/text inside `SpanLabel` / `SpanButton` / `MultiButton` / `SpanMultiButton`. |
| `interactionDialogSpeedInt` | `InteractionDialog` slide duration in ms (defaults to 400). |
| `DecayMotionScaleFactorInt` | Velocity-to-distance multiplier for exponential-decay scroll motion (default 950). |
| `disabledColor` | Default color used when disabling components. |

(The full list is in the Codename One Developer Guide under *Advanced Theming → Theme constants*: <https://www.codenameone.com/developer-guide/>.)

## Java side of styling: `setUIID`, the four state styles, and `getAllStyles`

```java
Component cmp = ...;

cmp.setUIID("PrimaryCta");                                  // (1) pick a CSS rule
cmp.getAllStyles().setBgColor(0x1d4ed8);                    // (2) write-only fan-out override
int unselectedBg = cmp.getUnselectedStyle().getBgColor();   // (3) read a specific state's value
```

- **`setUIID(String)`** is the **only** way to apply a theme rule. Whenever you create a component and want it styled, give it a UIID, then write the rule in `theme.css`. Don't reach for the style API for things CSS can express — themed UIIDs are reusable and theme-overridable; programmatic styling is one-off.

- **The four state styles map directly to the CSS selectors CN1 supports**:

  | Java getter | CSS selector | When in effect |
  | --- | --- | --- |
  | `getUnselectedStyle()` | `UIID { ... }` | Default rendering. |
  | `getSelectedStyle()` | `UIID.selected { ... }` | Focused. In **touch mode** focus is not rendered, so this matters on desktop/JavaScript and for arrow-key navigation; on phones the user never sees it. |
  | `getPressedStyle()` | `UIID.pressed { ... }` | While a finger / mouse is held down on the component. |
  | `getDisabledStyle()` | `UIID.disabled { ... }` | `cmp.setEnabled(false)`. |

  For **reading** style values explicitly (in tests, in painters, in conditional logic), always call the **specific state getter** — `getUnselectedStyle()`, `getSelectedStyle()`, `getPressedStyle()`, or `getDisabledStyle()`. That's the unambiguous form and it lines up with the CSS rule that produced the value.

- **`getStyle()` returns the style for the *currently active* state.** That means it's the right tool inside a custom `paint(Graphics g)` implementation — `paint()` is called for whatever state the component is in right now (unselected / focused / pressed / disabled), and `getStyle()` hands back exactly the colors, font, and border that should be used to render that state. **Outside of `paint()`, don't use `getStyle()`** — be explicit about which state you mean. Tests that read `getStyle()` will pass or fail depending on the focus/press state of the component at the moment of assertion, which is a flake source.

- **`getAllStyles()`** returns a **fan-out** style object that writes the same value to **all four** state styles at once. It is the right hammer for "set this color on the component" overrides. Treat it as **write-only** — never read values from it; the value you read may not match any specific state, since the aggregation is for setters only.

```java
// Test pattern — verify CSS applied (use the explicit state getter):
Button save = new Button("Save");
save.setUIID("PrimaryCta");
form.add(save);
form.show();
assertEqual(0x1d4ed8, save.getUnselectedStyle().getBgColor(),
        "PrimaryCta should map to accent in the theme");
assertEqual(0x1e3a8a, save.getPressedStyle().getBgColor(),
        "PrimaryCta.pressed should darken the accent");

// Inside a custom paint(): getStyle() returns the style for the state
// the component is currently in, so the rendering reflects that state.
public void paint(Graphics g) {
    Style s = getStyle();                  // unselected / selected / pressed / disabled, per current state
    g.setColor(s.getFgColor());
    g.drawLine(...);
}

// One-off programmatic override — animation, dynamic theming:
errorBanner.getAllStyles().setBgColor(0xb91c1c);
```

## Validating styles from a test

You generally don't validate CSS at the CSS level. Instead build the component, attach it to a Form, then read each state's `Style` explicitly:

```java
public class PrimaryCtaStyleTest extends AbstractTest {
    @Override public boolean shouldExecuteOnEDT() { return true; }
    @Override public boolean runTest() {
        Button btn = new Button("Submit");
        btn.setUIID("PrimaryCta");
        Form f = new Form("Test", BoxLayout.y());
        f.add(btn);
        f.show();

        Style unsel = btn.getUnselectedStyle();
        assertEqual(0x1d4ed8, unsel.getBgColor(), "Accent applied to background");
        assertEqual(0xffffff, unsel.getFgColor(), "Foreground forced to white");
        assertNotNull(unsel.getBorder(),          "Border attached");

        Style pressed = btn.getPressedStyle();
        assertEqual(0x1e3a8a, pressed.getBgColor(), "Pressed state darkens the accent");
        return true;
    }
}
```

This is more useful than checking raw CSS strings: it confirms the rule made it into `theme.res`, was matched at runtime, and resolved to the values you expect. Plus it catches regressions like UIID typos, accidental cascading, and resource cache staleness — symptoms that pure CSS validation would miss.

Always use the **explicit state getter** (`getUnselectedStyle()` / `getSelectedStyle()` / `getPressedStyle()` / `getDisabledStyle()`) rather than `getStyle()` — the latter returns whichever state is active at the moment of assertion and can change between test runs if focus or hover state shifts.

If the test fails and you want to inspect every UIID/key the theme actually contains:

```java
java.util.Hashtable<String, Object> map = UIManager.getInstance().getThemeProps();
for (Object k : map.keySet()) System.out.println(k);
```

That's the canonical "what's actually in the theme?" introspection.

## Animations — use transitions and `animate()`, not CSS

CSS does not animate anything in CN1. The two right tools, in priority order:

1. **`Form.animateLayout(durationMs)`** — for layout-driven animations. Mutate `setHidden`, change a child's UIID/visibility, swap LayoutConstraints, then call `animateLayout(250)`. CN1 tweens children from their old positions/sizes to the new ones. Use this for hide/show, panel push-in, expanding cards.

2. **CN1 transitions (`CommonTransitions`, `MorphTransition`, `Form#setTransitionInAnimator`/`setTransitionOutAnimator`)** — for whole-screen transitions (slide, fade) and component-to-component morphs across Forms. Set the desired transition on a Form before calling `show()`.

   ```java
   nextForm.setTransitionInAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, true, 250));
   nextForm.show();
   ```

3. **Per-component animation via `Component.animate()` + `setAnimation(true)`** — for "I want a value to drift over N ms". Override `animate()` on a Component, return `true` to keep the animation alive, and CN1 calls it once per frame. Register the animation with `getComponentForm().registerAnimated(this)`.

   ```java
   class Pulse extends Container {
       private Motion m = Motion.createEaseInOutMotion(0, 255, 600);
       @Override public boolean animate() {
           int alpha = m.getValue();
           getAllStyles().setBgTransparency(alpha);
           if (m.isFinished()) m.start();   // loop
           return true;                     // keep ticking
       }
       @Override protected void initComponent() {
           super.initComponent();
           m.start();
           getComponentForm().registerAnimated(this);
       }
   }
   ```

Painters are for **drawing** (custom backgrounds, decorations), not for animating. A `Painter` that mutates state to look animated won't actually be re-painted unless something else triggers a repaint — and that something else is the `animate()` mechanism above. Use `animate()` to drive state changes; use `Painter` only for one-off drawing.

## Common pitfalls

| Symptom | Cause / fix |
| --- | --- |
| Style doesn't apply | Write a `Style` test (see *Validating styles from a test* above). Read the explicit state — `getUnselectedStyle().getBgColor()`, etc. — never `getStyle()`. Iterate `UIManager.getInstance().getThemeProps().keySet()` to confirm the theme actually contains the entry. |
| Container has unexpected background / padding | The base `Container` UIID was restyled. Restore it to transparent / 0 padding / 0 margin and apply styling on a child UIID instead. |
| Text clipped by rounded corners | Padding too small relative to `border-radius`. Increase padding so it's ≥ the radius. |
| Padding ignored on the Form | You're setting padding on the Form itself; set it on its ContentPane or wrap content in your own UIID. |
| Border radius animates jaggy | Border radius is rasterized at compile when using image fallbacks; switch to `RoundRectBorder` and animate via `Form.animateLayout(...)`. |
| `text-align` does nothing | Add the `align` fallback (the initializr appends one automatically for `text-align`). |
| New CSS only takes effect after restart | The build cache may be stale — `mvn -pl common clean compile`. |
| 9-piece border looks blurry on iPhone Pro | Expected — 9-piece images are rasterized at the bundled resolution. Use a vector border (`RoundBorder`/`RoundRectBorder`) instead. |
| Custom TTF doesn't render on device but works in simulator | The `@font-face` `src:` filename and the JS-side `Font.createTrueTypeFont(name, file)` filename must match exactly, and the file must end up packaged with the app. Re-check spelling and confirm the file is under `common/src/main/css/fonts/`. |

## Reaching beyond the compiler

If you need a CSS feature that doesn't exist, you have two escapes:

1. **Programmatic styling** via `comp.getAllStyles().setXxx(...)`. Verbose, write-only, but supports anything. See *Margin and padding from Java* in `references/ui-components.md` for the unit pitfall.
2. **Custom painters** — implement `Painter` and `comp.getAllStyles().setBgPainter(painter)`. Lets you draw arbitrary shapes/gradients. Pair with `animate()` if the painted state needs to change over time.

Programmatic styling is the right hammer for animations, dynamic theming, and anything per-instance.
