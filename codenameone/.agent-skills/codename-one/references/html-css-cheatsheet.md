# HTML/CSS → Codename One Cheat Sheet

Designers and web developers think in HTML/CSS idioms — flexbox rows, hero sections, sticky headers, media queries. None of those translate literally, but every one of them has a CN1 idiom. Use this file as a fast lookup.

## HTML elements → CN1 components

| HTML | CN1 component | Notes |
| --- | --- | --- |
| `<div>` | `Container` | Wraps children; pick a layout. |
| `<span>` | `Label` | Single-line. |
| `<p>` / multi-line text | `SpanLabel` | Wraps automatically. |
| `<h1>`–`<h3>` | `Label` with a UIID like `H1`, `H2`, styled in CSS | |
| `<button>` | `Button` | |
| `<a href="...">` | `Button` styled as a link + `Display.execute(url)` | No native hyperlink component. |
| `<input type="text">` | `TextField` | |
| `<input type="password">` | `TextField` + `setConstraint(TextArea.PASSWORD)` | |
| `<input type="email">` | `TextField` + `setConstraint(TextArea.EMAILADDR)` | |
| `<textarea>` | `TextArea` | |
| `<select>` | `Picker` | Use `Picker` (with `pickerType=Display.PICKER_TYPE_STRINGS`) — opens a native sheet on mobile. Avoid `ComboBox`; its touch UX is poor across platforms. |
| `<input type="checkbox">` | `CheckBox` or `Switch` | |
| `<input type="radio">` | `RadioButton` in a `ButtonGroup` | |
| `<input type="range">` | `Slider` | |
| `<img>` | `Label` with image, or `URLImage` for remote | |
| `<ul>` / `<ol>` | `Container` with `BoxLayout.y()` of children | |
| `<form>` | `Container` + a `Submit` `Button` | No native form element. |
| `<dialog>` | `Dialog` | |
| `<iframe>` | `BrowserComponent` | |
| `<header>` / `<nav>` | `Toolbar` (already on every Form) | |
| `<footer>` | `BorderLayout.SOUTH` container | |

## CSS layout → CN1 layout

### Flexbox row → `BoxLayout.x()` or `FlowLayout`

```css
.row { display: flex; flex-direction: row; gap: 8px; }
```

```java
Container row = BoxLayout.encloseX(a, b, c);
row.getAllStyles().setMargin(0, 0, 2, 2);  // simulate "gap" via per-child margins
```

For `gap`, set padding/margin on the children or wrap each in a `Container` with margins.

### Flexbox column → `BoxLayout.y()`

```css
.col { display: flex; flex-direction: column; }
```

```java
Container col = BoxLayout.encloseY(header, body, footer);
```

### Flex `space-between` (header left + actions right)

If the "bar" you're building is **the form's header**, don't roll a custom row — that's exactly what the Form `Toolbar` is for. Set the title and add a right-bar command:

```java
Form f = new Form("My Profile", new BorderLayout());
f.getToolbar().addMaterialCommandToRightBar("Edit", FontImage.MATERIAL_EDIT, e -> openEdit());
```

That gives you the title-left / action-right layout the design calls for, with platform-correct spacing and back-button handling for free. Reach for a custom `BorderLayout` row only if it's a *sub-header* inside the form body:

```html
<div class="bar"><h3>Recent activity</h3><a href="#">See all</a></div>
```

```java
Container bar = new Container(new BorderLayout());
Label title = new Label("Recent activity");
title.setUIID("H3");
Button seeAll = new Button("See all");
seeAll.setUIID("LinkButton");
seeAll.addActionListener(e -> openActivity());

bar.add(BorderLayout.WEST, title);
bar.add(BorderLayout.EAST, seeAll);
```

`BorderLayout` with WEST/EAST is the CN1 idiom for "stuff on each end".

### CSS Grid → `GridLayout` or `TableLayout`

```css
.grid { display: grid; grid-template-columns: repeat(3, 1fr); gap: 8px; }
```

```java
Container grid = new Container(new GridLayout(rows, 3));
for (Item i : items) grid.add(renderCard(i));
```

If cells need different widths or row spans, use `TableLayout`.

### Hero section / shrinking header

Don't build the hero with a manual `LayeredLayout` of background image plus title — CN1's `Toolbar` is designed for this. Set the Toolbar's background image (in CSS), and let it shrink-on-scroll natively:

```css
Toolbar {
    background-image: url('/hero-bg.jpg');
    background-position: center;
    color: #ffffff;
    padding: 6mm 3mm;
}
Title {
    color: #ffffff;
    font-family: "native:MainBold";
    font-size: 5mm;
}
```

```java
Form f = new Form("Welcome", new BorderLayout());
Toolbar tb = f.getToolbar();
tb.setTitleCentered(true);

// Body scrolls beneath the hero. The Toolbar handles the hero image itself.
Container body = BoxLayout.encloseY(/* cards, list, whatever */);
body.setScrollableY(true);
f.add(BorderLayout.CENTER, body);
```

For the iOS-style "large title that shrinks as you scroll" effect, look at `Toolbar.setScrollOffSize(...)` paired with a scroll listener on the body. Implementing this with bare `LayeredLayout` is substantially more code and lacks the native shrink behavior.

(`LayeredLayout` is still the right tool for stacking decorations inside the body — see *position: absolute* in the "What you cannot do" table below.)

### Card (rounded, shadow-ish)

```css
.card { border-radius: 12px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); padding: 16px; }
```

```css
/* theme.css */
Card {
    background-color: #ffffff;
    border-radius: 3mm;
    padding: 3mm;
    margin: 1mm;
    border: 1px solid #e2e8f0;     /* simulate light shadow with subtle border */
}
```

```java
Container card = new Container(BoxLayout.y());
card.setUIID("Card");
card.add(new Label("Title")).add(new SpanLabel("Body"));
```

CN1 has no real `box-shadow`. Options: a subtle border (above), a `RoundBorder` with `setShadowOpacity`, or a 9-patch image of a card with shadow baked in.

### Sticky header

```css
header { position: sticky; top: 0; }
```

Two CN1 idioms, depending on what you want:

- **Header pinned outside the scrolling area** — put the header in `BorderLayout.NORTH` and the scrolling content in `BorderLayout.CENTER`. The NORTH region stays put when CENTER scrolls. No CSS gymnastics needed.

- **Header pinned to the top of the *same* scrolling area, with shrink/fade effects as you scroll** — use `com.codename1.components.StickyHeaderContainer`. It wraps a scrolling pane and pins one or more header containers with a configurable transition (fade, slide, color shift). See *Sticky headers* in `references/ui-components.md`.

### Centered content

```css
.center { display: flex; justify-content: center; align-items: center; }
```

```java
Container centered = FlowLayout.encloseCenter(new Label("Welcome"));
// or use a Form with FlowLayout configured to center vertically:
form.setLayout(new FlowLayout(Component.CENTER, Component.CENTER));
```

### Media queries / responsive

CN1 CSS supports `@media (prefers-color-scheme: dark)` (see `references/css.md`) but **no** viewport-size queries. Branch in Java for form-factor and orientation:

```java
Display d = Display.getInstance();

if (d.isTablet()) {
    form.setLayout(new BorderLayout());
    form.add(BorderLayout.WEST, sideNav);
    form.add(BorderLayout.CENTER, detail);
} else {
    form.setLayout(BoxLayout.y());
    form.add(BoxLayout.encloseY(quickActions, detail));
}

// Portrait vs. landscape
if (d.isPortrait()) {
    hero.setHidden(false);
} else {
    hero.setHidden(true);     // hide tall hero in landscape
}

// React when the device rotates
form.addOrientationListener(evt -> rebuildLayout(form));
```

`BorderLayout` has a built-in trick that often removes the need to branch on orientation manually: pass `BorderLayout.CENTER_BEHAVIOR_TOTAL_BELOW` (or call `setLandscapeSwap(...)` on the layout) and the WEST/EAST regions swap when the device rotates to landscape — useful for keeping a side panel on the leading edge as the layout flips.

`form.addOrientationListener(evt -> ...)` fires whenever the screen rotates between portrait and landscape; rebuild your layout there for anything more involved than a simple component flip.

See `references/mobile-adaptability.md` for the full responsive pattern matrix.

## Common HTML/CSS recipes

### "Login form"

```html
<form>
    <input type="email" placeholder="Email">
    <input type="password" placeholder="Password">
    <button type="submit">Sign in</button>
    <a href="/forgot">Forgot password?</a>
</form>
```

```java
TextField email = new TextField();
email.setHint("Email");
email.setConstraint(TextArea.EMAILADDR);
email.setSingleLineTextArea(true);

TextField password = new TextField();
password.setHint("Password");
password.setConstraint(TextArea.PASSWORD);
password.setSingleLineTextArea(true);

Button signIn = new Button("Sign in");
signIn.setUIID("PrimaryCta");
signIn.addActionListener(e -> doSignIn(email.getText(), password.getText()));

Button forgot = new Button("Forgot password?");
forgot.setUIID("LinkButton");
forgot.addActionListener(e -> showForgot());

form.add(BorderLayout.CENTER, BoxLayout.encloseY(email, password, signIn, forgot));
```

```css
PrimaryCta {
    background-color: #1d4ed8;
    color: #ffffff;
    border: 1px solid #1d4ed8;
    border-radius: 3mm;
    padding: 2mm 4mm;
}
LinkButton {
    background-color: transparent;
    color: #1d4ed8;
    border: none;
    padding: 1mm;
}
```

### "Card list with chevrons" (settings screen)

```html
<ul class="settings">
    <li><span>Profile</span><span>›</span></li>
    <li><span>Notifications</span><span>›</span></li>
</ul>
```

```java
Container list = new Container(BoxLayout.y());
for (String label : labels) {
    Label chevron = new Label();
    chevron.setMaterialIcon(FontImage.MATERIAL_CHEVRON_RIGHT);

    Container row = new Container(new BorderLayout());
    row.setUIID("SettingsRow");
    row.add(BorderLayout.CENTER, new Label(label));
    row.add(BorderLayout.EAST, chevron);
    row.setLeadComponent(row);
    row.addPointerPressedListener(e -> openSetting(label));
    list.add(row);
}
list.setScrollableY(true);
```

Note the `Label.setMaterialIcon(char)` one-liner — that's the right way to attach a Material glyph, not `new Label(FontImage.createMaterial(FontImage.MATERIAL_CHEVRON_RIGHT, "Label", 4))`. See `references/ui-components.md` for the full Material convenience-API table.

### "Hero image + headline overlay"

```html
<section class="hero">
    <img src="bg.jpg">
    <h1>Welcome</h1>
</section>
```

```java
Container hero = new Container(new LayeredLayout());
hero.add(new Label(Image.createImage("/hero-bg.jpg").scaledWidth(Display.getInstance().getDisplayWidth())));
Label headline = new Label("Welcome");
headline.setUIID("HeroHeadline");
hero.add(headline);

LayeredLayout.LayeredLayoutConstraint c = ((LayeredLayout)hero.getLayout()).createConstraint();
c.setInsets("50% 0 auto auto").setReferenceComponentLeft(headline, 0.5f);
hero.getLayout().addLayoutComponent(c, headline, hero);
```

### "Toast / snackbar"

```js
showToast("Saved");
```

```java
ToastBar.showMessage("Saved", FontImage.MATERIAL_CHECK);
```

### "Confirm before delete"

```js
if (confirm("Delete this?")) delete();
```

```java
if (Dialog.show("Delete?", "Are you sure?", "Delete", "Cancel")) delete();
```

## What you cannot do (and what to do instead)

| Web feature | CN1 alternative |
| --- | --- |
| `position: absolute` over the screen (FAB, toast, tutorial overlay) | `Form.getLayeredPane()` — every Form has a top-level `LayeredPane` you can add components to. For circular FABs use `FloatingActionButton.createFAB(...).bindFabToContainer(form.getContentPane())`. |
| `position: absolute` inside a container | `LayeredLayout` with `LayeredLayoutConstraint` (percent/mm insets, reference-component anchoring). |
| `position: sticky` (pinned but inside the scroller) | `com.codename1.components.StickyHeaderContainer` with optional transition. See *Sticky headers* in `references/ui-components.md`. |
| `position: sticky` (outside the scroller) | `BorderLayout.NORTH` + scrollable `CENTER`. |
| `@media (max-width: 600px) { ... }` | No viewport queries — branch in Java on `Display.isTablet()` / `getDisplayWidth()` / `isPortrait()` / `addOrientationListener(...)`. |
| `@media (prefers-color-scheme: dark) { ... }` | **Supported** — see `references/css.md`. |
| `:hover` | On touch, use `.pressed` UIID state. On the desktop/JavaScript ports, components fire pointer-hover events — see `Component.addPointerHoverListener(...)` / `Component.addPointerHoverReleasedListener(...)` to react when the mouse enters/leaves. |
| `transition: all 0.3s` | `Form.animateLayout(300)` after mutating layout / visibility / UIID. See *Animation* in `references/ui-components.md`. |
| Whole-screen transitions | `Form.setTransitionInAnimator(CommonTransitions.createSlide(...))` etc. |
| Cross-form morph (tap card → expand) | `MorphTransition.create(durationMs).morph(sourceCmp, targetCmp)` then `nextForm.setTransitionInAnimator(...)`. |
| `transform: rotate(45deg)` | Override `paint(Graphics g)` and use `g.rotate(theta, x, y)`. |
| `box-shadow` | `RoundRectBorder.create().shadowOpacity(...)` — supports shadow blur and spread. Don't ship a 9-piece PNG just for shadow. |
| `display: none` (animated removal) | `cmp.setHidden(true); cmp.setVisible(false); parent.animateLayout(200);` — gives a real slide/collapse out. Drop the `animateLayout` only if you want instant removal. |
| CSS variables (`--primary-color`) | Theme constants `#Constants { primaryColor: #1d4ed8; }` read via `UIManager.getInstance().getThemeConstant(...)`. See *Theme constants* in `references/css.md`. |
| `filter: blur()` on an image | `Display.getInstance().gaussianBlurImage(srcImage, radius)`. Returns a new blurred `Image`. Apply once at load time (it's not free per-frame). |
| `backdrop-filter` (live blur behind a popover) | Not supported on cross-platform. Capture the background, blur with `gaussianBlurImage`, paint as the popover's background. |
| `gradient` backgrounds | The `Style` API exposes `BACKGROUND_GRADIENT_LINEAR_VERTICAL`, `BACKGROUND_GRADIENT_LINEAR_HORIZONTAL`, and `BACKGROUND_GRADIENT_RADIAL` — set via `comp.getAllStyles().setBackgroundType(byte)` plus `setBackgroundGradientStartColor` / `setBackgroundGradientEndColor` / `setBackgroundGradientRelativeX/Y/Size`. CSS handles a matching subset (look up `cn1-` gradient extensions for your CN1 version). For arbitrary multi-stop gradients write a custom `Painter`. |

---

For converting an Android (XML + Kotlin/Java) screen to Codename One, see the dedicated guide at `references/android-to-cn1.md`.
