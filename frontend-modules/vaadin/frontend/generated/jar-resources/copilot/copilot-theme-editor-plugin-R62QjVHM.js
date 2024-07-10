import { B as je } from "./base-panel-Zq4jKPA_.js";
import { u as T, v, m as H, r as b, w as h, t as f, y as S, x as n, z as qe, s as Re, A as Ve, B as He, C as De, D as Ue, E as Be, G as L, H as Q, I as Fe, g as We, J as Ge, K as ee, L as Ke, P as Ze } from "./copilot-sWvu6xks.js";
var D = /* @__PURE__ */ ((t) => (t.disabled = "disabled", t.enabled = "enabled", t.missing_theme = "missing_theme", t))(D || {}), u = /* @__PURE__ */ ((t) => (t.local = "local", t.global = "global", t))(u || {});
function te(t, e) {
  return `${t}|${e}`;
}
class x {
  constructor(e) {
    this._properties = {}, this._metadata = e;
  }
  get metadata() {
    return this._metadata;
  }
  get properties() {
    return Object.values(this._properties);
  }
  getPropertyValue(e, s) {
    return this._properties[te(e, s)] || null;
  }
  updatePropertyValue(e, s, o, i) {
    if (!o) {
      delete this._properties[te(e, s)];
      return;
    }
    let r = this.getPropertyValue(e, s);
    r ? (r.value = o, r.modified = i || !1) : (r = {
      elementSelector: e,
      propertyName: s,
      value: o,
      modified: i || !1
    }, this._properties[te(e, s)] = r);
  }
  addPropertyValues(e) {
    e.forEach((s) => {
      this.updatePropertyValue(s.elementSelector, s.propertyName, s.value, s.modified);
    });
  }
  getPropertyValuesForElement(e) {
    return this.properties.filter((s) => s.elementSelector === e);
  }
  static combine(...e) {
    if (e.length < 2)
      throw new Error("Must provide at least two themes");
    const s = new x(e[0].metadata);
    return e.forEach((o) => s.addPropertyValues(o.properties)), s;
  }
  static fromServerRules(e, s, o) {
    const i = new x(e);
    return e.elements.forEach((r) => {
      const a = R(r, s), l = o.find((c) => c.selector === a.replace(/ > /g, ">"));
      l && r.properties.forEach((c) => {
        const d = l.properties[c.propertyName];
        d && i.updatePropertyValue(r.selector, c.propertyName, d, !0);
      });
    }), i;
  }
}
function R(t, e) {
  const s = t.selector;
  if (e.themeScope === "global")
    return s;
  if (!e.localClassName)
    throw new Error("Can not build local scoped selector without instance class name");
  const o = s.match(/^[\w\d-_]+/), i = o && o[0];
  if (!i)
    throw new Error(`Selector does not start with a tag name: ${s}`);
  return `${i}.${e.localClassName}${s.substring(i.length, s.length)}`;
}
function Je(t, e, s, o) {
  const i = R(t, e), r = { [s]: o };
  return s === "border-width" && (parseInt(o) > 0 ? r["border-style"] = "solid" : r["border-style"] = ""), {
    selector: i,
    properties: r
  };
}
function Xe(t) {
  const e = Object.entries(t.properties).map(([s, o]) => `${s}: ${o};`).join(" ");
  return `${t.selector} { ${e} }`;
}
const ve = {
  crosshair: T`<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round">
   <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
   <path d="M4 8v-2a2 2 0 0 1 2 -2h2"></path>
   <path d="M4 16v2a2 2 0 0 0 2 2h2"></path>
   <path d="M16 4h2a2 2 0 0 1 2 2v2"></path>
   <path d="M16 20h2a2 2 0 0 0 2 -2v-2"></path>
   <path d="M9 12l6 0"></path>
   <path d="M12 9l0 6"></path>
</svg>`,
  square: T`<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" fill="currentColor" stroke-linecap="round" stroke-linejoin="round">
   <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
   <path d="M3 3m0 2a2 2 0 0 1 2 -2h14a2 2 0 0 1 2 2v14a2 2 0 0 1 -2 2h-14a2 2 0 0 1 -2 -2z"></path>
</svg>`,
  font: T`<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round">
   <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
   <path d="M4 20l3 0"></path>
   <path d="M14 20l7 0"></path>
   <path d="M6.9 15l6.9 0"></path>
   <path d="M10.2 6.3l5.8 13.7"></path>
   <path d="M5 20l6 -16l2 0l7 16"></path>
</svg>`,
  undo: T`<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round">
   <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
   <path d="M9 13l-4 -4l4 -4m-4 4h11a4 4 0 0 1 0 8h-1"></path>
</svg>`,
  redo: T`<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round">
   <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
   <path d="M15 13l4 -4l-4 -4m4 4h-11a4 4 0 0 0 0 8h1"></path>
</svg>`,
  cross: T`<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" stroke-width="3" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round">
   <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
   <path d="M18 6l-12 12"></path>
   <path d="M6 6l12 12"></path>
</svg>`
};
let j, ge = "";
function Ae(t) {
  j || (j = new CSSStyleSheet(), document.adoptedStyleSheets = [...document.adoptedStyleSheets, j]), ge += t.cssText, j.replaceSync(ge);
}
const Me = v`
  .editor-row {
    display: flex;
    align-items: baseline;
    padding: var(--theme-editor-section-horizontal-padding);
    gap: 10px;
  }

  .editor-row > .label {
    flex: 0 0 auto;
    width: 120px;
  }

  .editor-row > .editor {
    flex: 1 1 0;
  }
`, fe = "__vaadin-theme-editor-measure-element", ye = /((::before)|(::after))$/, be = /::part\(([\w\d_-]+)\)$/;
Ae(v`
  .__vaadin-theme-editor-measure-element {
    position: absolute;
    top: 0;
    left: 0;
    visibility: hidden;
  }
`);
async function Ye(t) {
  const e = new x(t), s = document.createElement(t.tagName);
  s.classList.add(fe), document.body.append(s), t.setupElement && await t.setupElement(s);
  const o = {
    themeScope: u.local,
    localClassName: fe
  };
  try {
    t.elements.forEach((i) => {
      we(s, i, o, !0);
      let r = R(i, o);
      const a = r.match(ye);
      r = r.replace(ye, "");
      const l = r.match(be), c = r.replace(be, "");
      let d = document.querySelector(c);
      if (d && l) {
        const k = `[part~="${l[1]}"]`;
        d = d.shadowRoot.querySelector(k);
      }
      if (!d)
        return;
      d.style.transition = "none";
      const w = a ? a[1] : null, $ = getComputedStyle(d, w);
      i.properties.forEach((E) => {
        const k = $.getPropertyValue(E.propertyName) || E.defaultValue || "";
        e.updatePropertyValue(i.selector, E.propertyName, k);
      }), we(s, i, o, !1);
    });
  } finally {
    try {
      t.cleanupElement && await t.cleanupElement(s);
    } finally {
      s.remove();
    }
  }
  return e;
}
function we(t, e, s, o) {
  if (e.stateAttribute) {
    if (e.stateElementSelector) {
      const i = R(
        {
          ...e,
          selector: e.stateElementSelector
        },
        s
      );
      t = document.querySelector(i);
    }
    t && (o ? t.setAttribute(e.stateAttribute, "") : t.removeAttribute(e.stateAttribute));
  }
}
function xe(t) {
  return t.trim();
}
function Qe(t) {
  const e = t.element;
  if (!e)
    return null;
  const s = e.querySelector("label");
  if (s && s.textContent)
    return xe(s.textContent);
  const o = e.textContent;
  return o ? xe(o) : null;
}
class et {
  constructor() {
    this._localClassNameMap = /* @__PURE__ */ new Map();
  }
  get stylesheet() {
    return this.ensureStylesheet(), this._stylesheet;
  }
  add(e) {
    this.ensureStylesheet(), this._stylesheet.replaceSync(e);
  }
  clear() {
    this.ensureStylesheet(), this._stylesheet.replaceSync("");
  }
  previewLocalClassName(e, s) {
    if (!e)
      return;
    const o = this._localClassNameMap.get(e);
    o && (e.classList.remove(o), e.overlayClass = null), s ? (e.classList.add(s), e.overlayClass = s, this._localClassNameMap.set(e, s)) : this._localClassNameMap.delete(e);
  }
  ensureStylesheet() {
    this._stylesheet || (this._stylesheet = new CSSStyleSheet(), this._stylesheet.replaceSync(""), document.adoptedStyleSheets = [...document.adoptedStyleSheets, this._stylesheet]);
  }
}
const A = new et(), p = {
  index: -1,
  entries: []
};
class tt {
  constructor(e) {
    this.api = e;
  }
  get allowUndo() {
    return p.index >= 0;
  }
  get allowRedo() {
    return p.index < p.entries.length - 1;
  }
  get allowedActions() {
    return {
      allowUndo: this.allowUndo,
      allowRedo: this.allowRedo
    };
  }
  push(e, s, o) {
    const i = {
      requestId: e,
      execute: s,
      rollback: o
    };
    if (p.index++, p.entries = p.entries.slice(0, p.index), p.entries.push(i), s)
      try {
        s();
      } catch (r) {
        H("Execute history entry failed", r);
      }
    return this.allowedActions;
  }
  async undo() {
    if (!this.allowUndo)
      return this.allowedActions;
    const e = p.entries[p.index];
    p.index--;
    try {
      await this.api.undo(e.requestId), e.rollback && e.rollback();
    } catch (s) {
      H("Undo failed", s);
    }
    return this.allowedActions;
  }
  async redo() {
    if (!this.allowRedo)
      return this.allowedActions;
    p.index++;
    const e = p.entries[p.index];
    try {
      await this.api.redo(e.requestId), e.execute && e.execute();
    } catch (s) {
      H("Redo failed", s);
    }
    return this.allowedActions;
  }
  // Only intended to be used for testing
  static clear() {
    p.entries = [], p.index = -1;
  }
}
var st = Object.defineProperty, ot = Object.getOwnPropertyDescriptor, _ = (t, e, s, o) => {
  for (var i = o > 1 ? void 0 : o ? ot(e, s) : e, r = t.length - 1, a; r >= 0; r--)
    (a = t[r]) && (i = (o ? a(e, s, i) : a(i)) || i);
  return o && i && st(e, s, i), i;
};
class it extends CustomEvent {
  constructor(e, s, o) {
    super("theme-property-value-change", {
      bubbles: !0,
      composed: !0,
      detail: { element: e, property: s, value: o }
    });
  }
}
class g extends S {
  constructor() {
    super(), this.value = "", this.originalValue = "";
  }
  static get styles() {
    return [
      Me,
      v`
        :host {
          display: block;
        }

        .editor-row .label .modified {
          display: inline-block;
          width: 6px;
          height: 6px;
          background: orange;
          border-radius: 3px;
          margin-left: 3px;
        }
      `
    ];
  }
  firstUpdated(e) {
    super.firstUpdated(e), e.has("value") && (this.originalValue = this.value);
  }
  update(e) {
    super.update(e), (e.has("propertyMetadata") || e.has("theme")) && this.updateValueFromTheme();
  }
  render() {
    var e;
    return n`
      <div class="editor-row">
        <div class="label">
          ${this.propertyMetadata.displayName}
          ${(e = this.propertyValue) != null && e.modified ? n`<span class="modified"></span>` : null}
        </div>
        <div class="editor">${this.renderEditor()}</div>
      </div>
    `;
  }
  updateValueFromTheme() {
    var e;
    this.propertyValue = this.theme.getPropertyValue(this.elementMetadata.selector, this.propertyMetadata.propertyName), this.value = ((e = this.propertyValue) == null ? void 0 : e.value) || "";
  }
  dispatchChange(e) {
    this.dispatchEvent(new it(this.elementMetadata, this.propertyMetadata, e));
  }
}
_([
  h({})
], g.prototype, "elementMetadata", 2);
_([
  h({})
], g.prototype, "propertyMetadata", 2);
_([
  h({})
], g.prototype, "theme", 2);
_([
  b()
], g.prototype, "propertyValue", 2);
_([
  b()
], g.prototype, "value", 2);
class U {
  constructor(e) {
    if (this._values = [], this._rawValues = {}, e) {
      const s = e.propertyName, o = e.presets ?? [];
      this._values = (o || []).map((r) => r.startsWith("--") ? `var(${r})` : r);
      const i = document.createElement("div");
      i.style.borderStyle = "solid", i.style.visibility = "hidden", document.body.append(i);
      try {
        this._values.forEach((r) => {
          i.style.setProperty(s, r);
          const a = getComputedStyle(i);
          this._rawValues[r] = a.getPropertyValue(s).trim();
        });
      } finally {
        i.remove();
      }
    }
  }
  get values() {
    return this._values;
  }
  get rawValues() {
    return this._rawValues;
  }
  tryMapToRawValue(e) {
    return this._rawValues[e] ?? e;
  }
  tryMapToPreset(e) {
    return this.findPreset(e) ?? e;
  }
  findPreset(e) {
    const s = e && e.trim();
    return this.values.find((o) => this._rawValues[o] === s);
  }
}
class Ce extends CustomEvent {
  constructor(e) {
    super("change", { detail: { value: e } });
  }
}
let B = class extends S {
  constructor() {
    super(), this.value = "", this.showClearButton = !1;
  }
  static get styles() {
    return v`
      :host {
        display: inline-block;
        width: 100%;
        position: relative;
      }

      input {
        width: 100%;
        box-sizing: border-box;
        padding: 0.25rem 0.375rem;
        color: inherit;
        background: rgba(0, 0, 0, 0.2);
        border-radius: 0.25rem;
        border: none;
      }

      button {
        display: none;
        position: absolute;
        right: 4px;
        top: 4px;
        padding: 0;
        line-height: 0;
        border: none;
        background: none;
        color: var(--dev-tools-text-color);
      }

      button svg {
        width: 16px;
        height: 16px;
      }

      button:not(:disabled):hover {
        color: var(--dev-tools-text-color-emphasis);
      }

      :host(.show-clear-button) input {
        padding-right: 20px;
      }

      :host(.show-clear-button) button {
        display: block;
      }
    `;
  }
  update(t) {
    super.update(t), t.has("showClearButton") && (this.showClearButton ? this.classList.add("show-clear-button") : this.classList.remove("show-clear-button"));
  }
  render() {
    return n`
      <input class="input" .value=${this.value} @change=${this.handleInputChange} />
      <button @click=${this.handleClearClick}>${qe.cross}</button>
    `;
  }
  handleInputChange(t) {
    const e = t.target;
    this.dispatchEvent(new Ce(e.value));
  }
  handleClearClick() {
    this.dispatchEvent(new Ce(""));
  }
};
_([
  h({})
], B.prototype, "value", 2);
_([
  h({})
], B.prototype, "showClearButton", 2);
B = _([
  f("copilot-theme-text-input")
], B);
var rt = Object.defineProperty, at = Object.getOwnPropertyDescriptor, X = (t, e, s, o) => {
  for (var i = o > 1 ? void 0 : o ? at(e, s) : e, r = t.length - 1, a; r >= 0; r--)
    (a = t[r]) && (i = (o ? a(e, s, i) : a(i)) || i);
  return o && i && rt(e, s, i), i;
};
class nt extends CustomEvent {
  constructor(e) {
    super("class-name-change", { detail: { value: e } });
  }
}
let M = class extends S {
  constructor() {
    super(), this.editedClassName = "", this.invalid = !1;
  }
  static get styles() {
    return [
      Me,
      v`
        .editor-row {
          padding-top: 0;
        }

        .editor-row .editor .error {
          display: inline-block;
          color: var(--dev-tools-red-color);
          margin-top: 4px;
        }
      `
    ];
  }
  update(t) {
    super.update(t), t.has("className") && (this.editedClassName = this.className, this.invalid = !1);
  }
  render() {
    return n` <div class="editor-row local-class-name">
      <div class="label">CSS class name</div>
      <div class="editor">
        <copilot-theme-text-input
          type="text"
          .value=${this.editedClassName}
          @change=${this.handleInputChange}></copilot-theme-text-input>
        ${this.invalid ? n`<br /><span class="error">Please enter a valid CSS class name</span>` : null}
      </div>
    </div>`;
  }
  handleInputChange(t) {
    this.editedClassName = t.detail.value;
    const e = /^-?[_a-zA-Z]+[_a-zA-Z0-9-]*$/;
    this.invalid = !this.editedClassName.match(e), !this.invalid && this.editedClassName !== this.className && this.dispatchEvent(new nt(this.editedClassName));
  }
};
X([
  h({})
], M.prototype, "className", 2);
X([
  b()
], M.prototype, "editedClassName", 2);
X([
  b()
], M.prototype, "invalid", 2);
M = X([
  f("copilot-theme-class-name-editor")
], M);
var lt = Object.defineProperty, ct = Object.getOwnPropertyDescriptor, Y = (t, e, s, o) => {
  for (var i = o > 1 ? void 0 : o ? ct(e, s) : e, r = t.length - 1, a; r >= 0; r--)
    (a = t[r]) && (i = (o ? a(e, s, i) : a(i)) || i);
  return o && i && lt(e, s, i), i;
};
class dt extends CustomEvent {
  constructor(e) {
    super("scope-change", { detail: { value: e } });
  }
}
Ae(v`
  vaadin-select-overlay[theme~='copilot-theme-scope-selector'] {
    --lumo-primary-color-50pct: rgba(255, 255, 255, 0.5);
    z-index: 100000 !important;
  }

  vaadin-select-overlay[theme~='copilot-theme-scope-selector']::part(overlay) {
    background: #333;
  }

  vaadin-select-overlay[theme~='copilot-theme-scope-selector'] vaadin-item {
    color: rgba(255, 255, 255, 0.8);
  }

  vaadin-select-overlay[theme~='copilot-theme-scope-selector'] vaadin-item::part(content) {
    font-size: 13px;
  }

  vaadin-select-overlay[theme~='copilot-theme-scope-selector'] vaadin-item .title {
    color: rgba(255, 255, 255, 0.95);
    font-weight: bold;
  }

  vaadin-select-overlay[theme~='copilot-theme-scope-selector'] vaadin-item::part(checkmark) {
    margin: 6px;
  }

  vaadin-select-overlay[theme~='copilot-theme-scope-selector'] vaadin-item::part(checkmark)::before {
    color: rgba(255, 255, 255, 0.95);
  }

  vaadin-select-overlay[theme~='copilot-theme-scope-selector'] vaadin-item:hover {
    background: rgba(255, 255, 255, 0.1);
  }
`);
let z = class extends S {
  constructor() {
    super(), this.value = u.local;
  }
  static get styles() {
    return v`
      vaadin-select {
        --lumo-primary-color-50pct: rgba(255, 255, 255, 0.5);
        width: 100px;
      }

      vaadin-select::part(input-field) {
        background: rgba(0, 0, 0, 0.2);
      }

      vaadin-select vaadin-select-value-button,
      vaadin-select::part(toggle-button) {
        color: var(--dev-tools-text-color);
      }

      vaadin-select:hover vaadin-select-value-button,
      vaadin-select:hover::part(toggle-button) {
        color: var(--dev-tools-text-color-emphasis);
      }

      vaadin-select vaadin-select-item {
        font-size: 13px;
      }
    `;
  }
  update(t) {
    var e;
    super.update(t), t.has("metadata") && ((e = this.select) == null || e.requestContentUpdate());
  }
  render() {
    return n` <vaadin-select
      theme="small copilot-theme-scope-selector"
      .value=${this.value}
      .renderer=${this.selectRenderer.bind(this)}
      @value-changed=${this.handleValueChange}></vaadin-select>`;
  }
  selectRenderer(t) {
    var o;
    const e = ((o = this.metadata) == null ? void 0 : o.displayName) || "Component", s = `${e}s`;
    Ve(
      n`
        <vaadin-list-box>
          <vaadin-item value=${u.local} label="Local">
            <span class="title">Local</span>
            <br />
            <span>Edit styles for this ${e}</span>
          </vaadin-item>
          <vaadin-item value=${u.global} label="Global">
            <span class="title">Global</span>
            <br />
            <span>Edit styles for all ${s}</span>
          </vaadin-item>
        </vaadin-list-box>
      `,
      t
    );
  }
  handleValueChange(t) {
    const e = t.detail.value;
    e !== this.value && this.dispatchEvent(new dt(e));
  }
};
Y([
  h({})
], z.prototype, "value", 2);
Y([
  h({})
], z.prototype, "metadata", 2);
Y([
  Re("vaadin-select")
], z.prototype, "select", 2);
z = Y([
  f("copilot-theme-scope-selector")
], z);
var ht = Object.defineProperty, pt = Object.getOwnPropertyDescriptor, ut = (t, e, s, o) => {
  for (var i = o > 1 ? void 0 : o ? pt(e, s) : e, r = t.length - 1, a; r >= 0; r--)
    (a = t[r]) && (i = (o ? a(e, s, i) : a(i)) || i);
  return o && i && ht(e, s, i), i;
};
let _e = class extends g {
  static get styles() {
    return [
      g.styles,
      v`
        .editor-row {
          align-items: center;
        }
      `
    ];
  }
  handleInputChange(t) {
    const s = t.target.checked ? this.propertyMetadata.checkedValue : "";
    this.dispatchChange(s || "");
  }
  renderEditor() {
    const t = this.value === this.propertyMetadata.checkedValue;
    return n` <input type="checkbox" .checked=${t} @change=${this.handleInputChange} /> `;
  }
};
_e = ut([
  f("copilot-theme-checkbox-property-editor")
], _e);
var mt = Object.defineProperty, vt = Object.getOwnPropertyDescriptor, gt = (t, e, s, o) => {
  for (var i = o > 1 ? void 0 : o ? vt(e, s) : e, r = t.length - 1, a; r >= 0; r--)
    (a = t[r]) && (i = (o ? a(e, s, i) : a(i)) || i);
  return o && i && mt(e, s, i), i;
};
let $e = class extends g {
  handleInputChange(t) {
    this.dispatchChange(t.detail.value);
  }
  renderEditor() {
    var t;
    return n`
      <copilot-theme-text-input
        .value=${this.value}
        .showClearButton=${((t = this.propertyValue) == null ? void 0 : t.modified) || !1}
        @change=${this.handleInputChange}></copilot-theme-text-input>
    `;
  }
};
$e = gt([
  f("copilot-theme-text-property-editor")
], $e);
/**
 * @license
 * Copyright 2018 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */
const ft = He(class extends De {
  constructor(t) {
    var e;
    if (super(t), t.type !== Ue.ATTRIBUTE || t.name !== "class" || ((e = t.strings) == null ? void 0 : e.length) > 2)
      throw Error("`classMap()` can only be used in the `class` attribute and must be the only part in the attribute.");
  }
  render(t) {
    return " " + Object.keys(t).filter((e) => t[e]).join(" ") + " ";
  }
  update(t, [e]) {
    var o, i;
    if (this.it === void 0) {
      this.it = /* @__PURE__ */ new Set(), t.strings !== void 0 && (this.st = new Set(t.strings.join(" ").split(/\s/).filter((r) => r !== "")));
      for (const r in e)
        e[r] && !((o = this.st) != null && o.has(r)) && this.it.add(r);
      return this.render(e);
    }
    const s = t.element.classList;
    for (const r of this.it)
      r in e || (s.remove(r), this.it.delete(r));
    for (const r in e) {
      const a = !!e[r];
      a === this.it.has(r) || (i = this.st) != null && i.has(r) || (a ? (s.add(r), this.it.add(r)) : (s.remove(r), this.it.delete(r)));
    }
    return Be;
  }
});
var yt = Object.defineProperty, bt = Object.getOwnPropertyDescriptor, de = (t, e, s, o) => {
  for (var i = o > 1 ? void 0 : o ? bt(e, s) : e, r = t.length - 1, a; r >= 0; r--)
    (a = t[r]) && (i = (o ? a(e, s, i) : a(i)) || i);
  return o && i && yt(e, s, i), i;
};
let F = class extends g {
  constructor() {
    super(), this.selectedPresetIndex = -1, this.presets = new U();
  }
  static get styles() {
    return [
      g.styles,
      v`
        :host {
          --preset-count: 3;
          --slider-bg: #fff;
          --slider-border: #333;
        }

        .editor-row {
          align-items: center;
        }

        .editor-row > .editor {
          display: flex;
          align-items: center;
          gap: 1rem;
        }

        .editor-row .input {
          flex: 0 0 auto;
          width: 80px;
        }

        .slider-wrapper {
          flex: 1 1 0;
          display: flex;
          align-items: center;
          gap: 0.5rem;
        }

        .icon {
          width: 20px;
          height: 20px;
          color: #aaa;
        }

        .icon.prefix > svg {
          transform: scale(0.75);
        }

        .slider {
          flex: 1 1 0;
          -webkit-appearance: none;
          background: linear-gradient(to right, #666, #666 2px, transparent 2px);
          background-size: calc((100% - 13px) / (var(--preset-count) - 1)) 8px;
          background-position: 5px 50%;
          background-repeat: repeat-x;
        }

        .slider::-webkit-slider-runnable-track {
          width: 100%;
          box-sizing: border-box;
          height: 16px;
          background-image: linear-gradient(#666, #666);
          background-size: calc(100% - 12px) 2px;
          background-repeat: no-repeat;
          background-position: 6px 50%;
        }

        .slider::-moz-range-track {
          width: 100%;
          box-sizing: border-box;
          height: 16px;
          background-image: linear-gradient(#666, #666);
          background-size: calc(100% - 12px) 2px;
          background-repeat: no-repeat;
          background-position: 6px 50%;
        }

        .slider::-webkit-slider-thumb {
          -webkit-appearance: none;
          height: 16px;
          width: 16px;
          border: 2px solid var(--slider-border);
          border-radius: 50%;
          background: var(--slider-bg);
          cursor: pointer;
        }

        .slider::-moz-range-thumb {
          height: 16px;
          width: 16px;
          border: 2px solid var(--slider-border);
          border-radius: 50%;
          background: var(--slider-bg);
          cursor: pointer;
        }

        .custom-value {
          opacity: 0.5;
        }

        .custom-value:hover,
        .custom-value:focus-within {
          opacity: 1;
        }

        .custom-value:not(:hover, :focus-within) {
          --slider-bg: #333;
          --slider-border: #666;
        }
      `
    ];
  }
  update(t) {
    t.has("propertyMetadata") && (this.presets = new U(this.propertyMetadata)), super.update(t);
  }
  renderEditor() {
    var s;
    const t = {
      "slider-wrapper": !0,
      "custom-value": this.selectedPresetIndex < 0
    }, e = this.presets.values.length;
    return n`
      <div class=${ft(t)}>
        ${null}
        <input
          type="range"
          class="slider"
          style="--preset-count: ${e}"
          step="1"
          min="0"
          .max=${(e - 1).toString()}
          .value=${this.selectedPresetIndex}
          @input=${this.handleSliderInput}
          @change=${this.handleSliderChange} />
        ${null}
      </div>
      <copilot-theme-text-input
        class="input"
        .value=${this.value}
        .showClearButton=${((s = this.propertyValue) == null ? void 0 : s.modified) || !1}
        @change=${this.handleValueChange}></copilot-theme-text-input>
    `;
  }
  handleSliderInput(t) {
    const e = t.target, s = parseInt(e.value), o = this.presets.values[s];
    this.selectedPresetIndex = s, this.value = this.presets.rawValues[o];
  }
  handleSliderChange() {
    this.dispatchChange(this.value);
  }
  handleValueChange(t) {
    this.value = t.detail.value, this.updateSliderValue(), this.dispatchChange(this.value);
  }
  dispatchChange(t) {
    const e = this.presets.tryMapToPreset(t);
    super.dispatchChange(e);
  }
  updateValueFromTheme() {
    var t;
    super.updateValueFromTheme(), this.value = this.presets.tryMapToRawValue(((t = this.propertyValue) == null ? void 0 : t.value) || ""), this.updateSliderValue();
  }
  updateSliderValue() {
    const t = this.presets.findPreset(this.value);
    this.selectedPresetIndex = t ? this.presets.values.indexOf(t) : -1;
  }
};
de([
  b()
], F.prototype, "selectedPresetIndex", 2);
de([
  b()
], F.prototype, "presets", 2);
F = de([
  f("copilot-theme-range-property-editor")
], F);
const V = (t, e = 0, s = 1) => t > s ? s : t < e ? e : t, m = (t, e = 0, s = Math.pow(10, e)) => Math.round(s * t) / s, ze = ({ h: t, s: e, v: s, a: o }) => {
  const i = (200 - e) * s / 100;
  return {
    h: m(t),
    s: m(i > 0 && i < 200 ? e * s / 100 / (i <= 100 ? i : 200 - i) * 100 : 0),
    l: m(i / 2),
    a: m(o, 2)
  };
}, ne = (t) => {
  const { h: e, s, l: o } = ze(t);
  return `hsl(${e}, ${s}%, ${o}%)`;
}, se = (t) => {
  const { h: e, s, l: o, a: i } = ze(t);
  return `hsla(${e}, ${s}%, ${o}%, ${i})`;
}, wt = ({ h: t, s: e, v: s, a: o }) => {
  t = t / 360 * 6, e = e / 100, s = s / 100;
  const i = Math.floor(t), r = s * (1 - e), a = s * (1 - (t - i) * e), l = s * (1 - (1 - t + i) * e), c = i % 6;
  return {
    r: m([s, a, r, r, l, s][c] * 255),
    g: m([l, s, s, a, r, r][c] * 255),
    b: m([r, r, l, s, s, a][c] * 255),
    a: m(o, 2)
  };
}, xt = (t) => {
  const { r: e, g: s, b: o, a: i } = wt(t);
  return `rgba(${e}, ${s}, ${o}, ${i})`;
}, Ct = (t) => {
  const s = /rgba?\(?\s*(-?\d*\.?\d+)(%)?[,\s]+(-?\d*\.?\d+)(%)?[,\s]+(-?\d*\.?\d+)(%)?,?\s*[/\s]*(-?\d*\.?\d+)?(%)?\s*\)?/i.exec(t);
  return s ? _t({
    r: Number(s[1]) / (s[2] ? 100 / 255 : 1),
    g: Number(s[3]) / (s[4] ? 100 / 255 : 1),
    b: Number(s[5]) / (s[6] ? 100 / 255 : 1),
    a: s[7] === void 0 ? 1 : Number(s[7]) / (s[8] ? 100 : 1)
  }) : { h: 0, s: 0, v: 0, a: 1 };
}, _t = ({ r: t, g: e, b: s, a: o }) => {
  const i = Math.max(t, e, s), r = i - Math.min(t, e, s), a = r ? i === t ? (e - s) / r : i === e ? 2 + (s - t) / r : 4 + (t - e) / r : 0;
  return {
    h: m(60 * (a < 0 ? a + 6 : a)),
    s: m(i ? r / i * 100 : 0),
    v: m(i / 255 * 100),
    a: o
  };
}, $t = (t, e) => {
  if (t === e)
    return !0;
  for (const s in t)
    if (t[s] !== e[s])
      return !1;
  return !0;
}, Et = (t, e) => t.replace(/\s/g, "") === e.replace(/\s/g, ""), Ee = {}, Ie = (t) => {
  let e = Ee[t];
  return e || (e = document.createElement("template"), e.innerHTML = t, Ee[t] = e), e;
}, he = (t, e, s) => {
  t.dispatchEvent(new CustomEvent(e, {
    bubbles: !0,
    detail: s
  }));
};
let O = !1;
const le = (t) => "touches" in t, kt = (t) => O && !le(t) ? !1 : (O || (O = le(t)), !0), ke = (t, e) => {
  const s = le(e) ? e.touches[0] : e, o = t.el.getBoundingClientRect();
  he(t.el, "move", t.getMove({
    x: V((s.pageX - (o.left + window.pageXOffset)) / o.width),
    y: V((s.pageY - (o.top + window.pageYOffset)) / o.height)
  }));
}, St = (t, e) => {
  const s = e.keyCode;
  s > 40 || t.xy && s < 37 || s < 33 || (e.preventDefault(), he(t.el, "move", t.getMove({
    x: s === 39 ? 0.01 : s === 37 ? -0.01 : s === 34 ? 0.05 : s === 33 ? -0.05 : s === 35 ? 1 : s === 36 ? -1 : 0,
    y: s === 40 ? 0.01 : s === 38 ? -0.01 : 0
  }, !0)));
};
class pe {
  constructor(e, s, o, i) {
    const r = Ie(`<div role="slider" tabindex="0" part="${s}" ${o}><div part="${s}-pointer"></div></div>`);
    e.appendChild(r.content.cloneNode(!0));
    const a = e.querySelector(`[part=${s}]`);
    a.addEventListener("mousedown", this), a.addEventListener("touchstart", this), a.addEventListener("keydown", this), this.el = a, this.xy = i, this.nodes = [a.firstChild, a];
  }
  set dragging(e) {
    const s = e ? document.addEventListener : document.removeEventListener;
    s(O ? "touchmove" : "mousemove", this), s(O ? "touchend" : "mouseup", this);
  }
  handleEvent(e) {
    switch (e.type) {
      case "mousedown":
      case "touchstart":
        if (e.preventDefault(), !kt(e) || !O && e.button != 0)
          return;
        this.el.focus(), ke(this, e), this.dragging = !0;
        break;
      case "mousemove":
      case "touchmove":
        e.preventDefault(), ke(this, e);
        break;
      case "mouseup":
      case "touchend":
        this.dragging = !1;
        break;
      case "keydown":
        St(this, e);
        break;
    }
  }
  style(e) {
    e.forEach((s, o) => {
      for (const i in s)
        this.nodes[o].style.setProperty(i, s[i]);
    });
  }
}
class Pt extends pe {
  constructor(e) {
    super(e, "hue", 'aria-label="Hue" aria-valuemin="0" aria-valuemax="360"', !1);
  }
  update({ h: e }) {
    this.h = e, this.style([
      {
        left: `${e / 360 * 100}%`,
        color: ne({ h: e, s: 100, v: 100, a: 1 })
      }
    ]), this.el.setAttribute("aria-valuenow", `${m(e)}`);
  }
  getMove(e, s) {
    return { h: s ? V(this.h + e.x * 360, 0, 360) : 360 * e.x };
  }
}
class Nt extends pe {
  constructor(e) {
    super(e, "saturation", 'aria-label="Color"', !0);
  }
  update(e) {
    this.hsva = e, this.style([
      {
        top: `${100 - e.v}%`,
        left: `${e.s}%`,
        color: ne(e)
      },
      {
        "background-color": ne({ h: e.h, s: 100, v: 100, a: 1 })
      }
    ]), this.el.setAttribute("aria-valuetext", `Saturation ${m(e.s)}%, Brightness ${m(e.v)}%`);
  }
  getMove(e, s) {
    return {
      s: s ? V(this.hsva.s + e.x * 100, 0, 100) : e.x * 100,
      v: s ? V(this.hsva.v - e.y * 100, 0, 100) : Math.round(100 - e.y * 100)
    };
  }
}
const Tt = ':host{display:flex;flex-direction:column;position:relative;width:200px;height:200px;user-select:none;-webkit-user-select:none;cursor:default}:host([hidden]){display:none!important}[role=slider]{position:relative;touch-action:none;user-select:none;-webkit-user-select:none;outline:0}[role=slider]:last-child{border-radius:0 0 8px 8px}[part$=pointer]{position:absolute;z-index:1;box-sizing:border-box;width:28px;height:28px;display:flex;place-content:center center;transform:translate(-50%,-50%);background-color:#fff;border:2px solid #fff;border-radius:50%;box-shadow:0 2px 4px rgba(0,0,0,.2)}[part$=pointer]::after{content:"";width:100%;height:100%;border-radius:inherit;background-color:currentColor}[role=slider]:focus [part$=pointer]{transform:translate(-50%,-50%) scale(1.1)}', Ot = "[part=hue]{flex:0 0 24px;background:linear-gradient(to right,red 0,#ff0 17%,#0f0 33%,#0ff 50%,#00f 67%,#f0f 83%,red 100%)}[part=hue-pointer]{top:50%;z-index:2}", Rt = "[part=saturation]{flex-grow:1;border-color:transparent;border-bottom:12px solid #000;border-radius:8px 8px 0 0;background-image:linear-gradient(to top,#000,transparent),linear-gradient(to right,#fff,rgba(255,255,255,0));box-shadow:inset 0 0 0 1px rgba(0,0,0,.05)}[part=saturation-pointer]{z-index:3}", q = Symbol("same"), oe = Symbol("color"), Se = Symbol("hsva"), ie = Symbol("update"), Pe = Symbol("parts"), W = Symbol("css"), G = Symbol("sliders");
let Vt = class extends HTMLElement {
  static get observedAttributes() {
    return ["color"];
  }
  get [W]() {
    return [Tt, Ot, Rt];
  }
  get [G]() {
    return [Nt, Pt];
  }
  get color() {
    return this[oe];
  }
  set color(e) {
    if (!this[q](e)) {
      const s = this.colorModel.toHsva(e);
      this[ie](s), this[oe] = e;
    }
  }
  constructor() {
    super();
    const e = Ie(`<style>${this[W].join("")}</style>`), s = this.attachShadow({ mode: "open" });
    s.appendChild(e.content.cloneNode(!0)), s.addEventListener("move", this), this[Pe] = this[G].map((o) => new o(s));
  }
  connectedCallback() {
    if (this.hasOwnProperty("color")) {
      const e = this.color;
      delete this.color, this.color = e;
    } else
      this.color || (this.color = this.colorModel.defaultColor);
  }
  attributeChangedCallback(e, s, o) {
    const i = this.colorModel.fromAttr(o);
    this[q](i) || (this.color = i);
  }
  handleEvent(e) {
    const s = this[Se], o = { ...s, ...e.detail };
    this[ie](o);
    let i;
    !$t(o, s) && !this[q](i = this.colorModel.fromHsva(o)) && (this[oe] = i, he(this, "color-changed", { value: i }));
  }
  [q](e) {
    return this.color && this.colorModel.equal(e, this.color);
  }
  [ie](e) {
    this[Se] = e, this[Pe].forEach((s) => s.update(e));
  }
};
class At extends pe {
  constructor(e) {
    super(e, "alpha", 'aria-label="Alpha" aria-valuemin="0" aria-valuemax="1"', !1);
  }
  update(e) {
    this.hsva = e;
    const s = se({ ...e, a: 0 }), o = se({ ...e, a: 1 }), i = e.a * 100;
    this.style([
      {
        left: `${i}%`,
        color: se(e)
      },
      {
        "--gradient": `linear-gradient(90deg, ${s}, ${o}`
      }
    ]);
    const r = m(i);
    this.el.setAttribute("aria-valuenow", `${r}`), this.el.setAttribute("aria-valuetext", `${r}%`);
  }
  getMove(e, s) {
    return { a: s ? V(this.hsva.a + e.x) : e.x };
  }
}
const Mt = `[part=alpha]{flex:0 0 24px}[part=alpha]::after{display:block;content:"";position:absolute;top:0;left:0;right:0;bottom:0;border-radius:inherit;background-image:var(--gradient);box-shadow:inset 0 0 0 1px rgba(0,0,0,.05)}[part^=alpha]{background-color:#fff;background-image:url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill-opacity=".05"><rect x="8" width="8" height="8"/><rect y="8" width="8" height="8"/></svg>')}[part=alpha-pointer]{top:50%}`;
class zt extends Vt {
  get [W]() {
    return [...super[W], Mt];
  }
  get [G]() {
    return [...super[G], At];
  }
}
const It = {
  defaultColor: "rgba(0, 0, 0, 1)",
  toHsva: Ct,
  fromHsva: xt,
  equal: Et,
  fromAttr: (t) => t
};
class Lt extends zt {
  get colorModel() {
    return It;
  }
}
/**
 * @license
 * Copyright (c) 2017 - 2023 Vaadin Ltd.
 * This program is available under Apache License Version 2.0, available at https://vaadin.com/license/
 */
function jt(t) {
  const e = [];
  for (; t; ) {
    if (t.nodeType === Node.DOCUMENT_NODE) {
      e.push(t);
      break;
    }
    if (t.nodeType === Node.DOCUMENT_FRAGMENT_NODE) {
      e.push(t), t = t.host;
      continue;
    }
    if (t.assignedSlot) {
      t = t.assignedSlot;
      continue;
    }
    t = t.parentNode;
  }
  return e;
}
const re = {
  start: "top",
  end: "bottom"
}, ae = {
  start: "left",
  end: "right"
}, Ne = new ResizeObserver((t) => {
  setTimeout(() => {
    t.forEach((e) => {
      e.target.__overlay && e.target.__overlay._updatePosition();
    });
  });
}), qt = (t) => class extends t {
  static get properties() {
    return {
      /**
       * The element next to which this overlay should be aligned.
       * The position of the overlay relative to the positionTarget can be adjusted
       * with properties `horizontalAlign`, `verticalAlign`, `noHorizontalOverlap`
       * and `noVerticalOverlap`.
       */
      positionTarget: {
        type: Object,
        value: null
      },
      /**
       * When `positionTarget` is set, this property defines whether to align the overlay's
       * left or right side to the target element by default.
       * Possible values are `start` and `end`.
       * RTL is taken into account when interpreting the value.
       * The overlay is automatically flipped to the opposite side when it doesn't fit into
       * the default side defined by this property.
       *
       * @attr {start|end} horizontal-align
       */
      horizontalAlign: {
        type: String,
        value: "start"
      },
      /**
       * When `positionTarget` is set, this property defines whether to align the overlay's
       * top or bottom side to the target element by default.
       * Possible values are `top` and `bottom`.
       * The overlay is automatically flipped to the opposite side when it doesn't fit into
       * the default side defined by this property.
       *
       * @attr {top|bottom} vertical-align
       */
      verticalAlign: {
        type: String,
        value: "top"
      },
      /**
       * When `positionTarget` is set, this property defines whether the overlay should overlap
       * the target element in the x-axis, or be positioned right next to it.
       *
       * @attr {boolean} no-horizontal-overlap
       */
      noHorizontalOverlap: {
        type: Boolean,
        value: !1
      },
      /**
       * When `positionTarget` is set, this property defines whether the overlay should overlap
       * the target element in the y-axis, or be positioned right above/below it.
       *
       * @attr {boolean} no-vertical-overlap
       */
      noVerticalOverlap: {
        type: Boolean,
        value: !1
      },
      /**
       * If the overlay content has no intrinsic height, this property can be used to set
       * the minimum vertical space (in pixels) required by the overlay. Setting a value to
       * the property effectively disables the content measurement in favor of using this
       * fixed value for determining the open direction.
       *
       * @attr {number} required-vertical-space
       */
      requiredVerticalSpace: {
        type: Number,
        value: 0
      }
    };
  }
  static get observers() {
    return [
      "__positionSettingsChanged(horizontalAlign, verticalAlign, noHorizontalOverlap, noVerticalOverlap, requiredVerticalSpace)",
      "__overlayOpenedChanged(opened, positionTarget)"
    ];
  }
  constructor() {
    super(), this.__onScroll = this.__onScroll.bind(this), this._updatePosition = this._updatePosition.bind(this);
  }
  /** @protected */
  connectedCallback() {
    super.connectedCallback(), this.opened && this.__addUpdatePositionEventListeners();
  }
  /** @protected */
  disconnectedCallback() {
    super.disconnectedCallback(), this.__removeUpdatePositionEventListeners();
  }
  /** @private */
  __addUpdatePositionEventListeners() {
    window.addEventListener("resize", this._updatePosition), this.__positionTargetAncestorRootNodes = jt(this.positionTarget), this.__positionTargetAncestorRootNodes.forEach((s) => {
      s.addEventListener("scroll", this.__onScroll, !0);
    });
  }
  /** @private */
  __removeUpdatePositionEventListeners() {
    window.removeEventListener("resize", this._updatePosition), this.__positionTargetAncestorRootNodes && (this.__positionTargetAncestorRootNodes.forEach((s) => {
      s.removeEventListener("scroll", this.__onScroll, !0);
    }), this.__positionTargetAncestorRootNodes = null);
  }
  /** @private */
  __overlayOpenedChanged(s, o) {
    if (this.__removeUpdatePositionEventListeners(), o && (o.__overlay = null, Ne.unobserve(o), s && (this.__addUpdatePositionEventListeners(), o.__overlay = this, Ne.observe(o))), s) {
      const i = getComputedStyle(this);
      this.__margins || (this.__margins = {}, ["top", "bottom", "left", "right"].forEach((r) => {
        this.__margins[r] = parseInt(i[r], 10);
      })), this.setAttribute("dir", i.direction), this._updatePosition(), requestAnimationFrame(() => this._updatePosition());
    }
  }
  __positionSettingsChanged() {
    this._updatePosition();
  }
  /** @private */
  __onScroll(s) {
    this.contains(s.target) || this._updatePosition();
  }
  _updatePosition() {
    if (!this.positionTarget || !this.opened)
      return;
    const s = this.positionTarget.getBoundingClientRect(), o = this.__shouldAlignStartVertically(s);
    this.style.justifyContent = o ? "flex-start" : "flex-end";
    const i = this.__isRTL, r = this.__shouldAlignStartHorizontally(s, i), a = !i && r || i && !r;
    this.style.alignItems = a ? "flex-start" : "flex-end";
    const l = this.getBoundingClientRect(), c = this.__calculatePositionInOneDimension(
      s,
      l,
      this.noVerticalOverlap,
      re,
      this,
      o
    ), d = this.__calculatePositionInOneDimension(
      s,
      l,
      this.noHorizontalOverlap,
      ae,
      this,
      r
    );
    Object.assign(this.style, c, d), this.toggleAttribute("bottom-aligned", !o), this.toggleAttribute("top-aligned", o), this.toggleAttribute("end-aligned", !a), this.toggleAttribute("start-aligned", a);
  }
  __shouldAlignStartHorizontally(s, o) {
    const i = Math.max(this.__oldContentWidth || 0, this.$.overlay.offsetWidth);
    this.__oldContentWidth = this.$.overlay.offsetWidth;
    const r = Math.min(window.innerWidth, document.documentElement.clientWidth), a = !o && this.horizontalAlign === "start" || o && this.horizontalAlign === "end";
    return this.__shouldAlignStart(
      s,
      i,
      r,
      this.__margins,
      a,
      this.noHorizontalOverlap,
      ae
    );
  }
  __shouldAlignStartVertically(s) {
    const o = this.requiredVerticalSpace || Math.max(this.__oldContentHeight || 0, this.$.overlay.offsetHeight);
    this.__oldContentHeight = this.$.overlay.offsetHeight;
    const i = Math.min(window.innerHeight, document.documentElement.clientHeight), r = this.verticalAlign === "top";
    return this.__shouldAlignStart(
      s,
      o,
      i,
      this.__margins,
      r,
      this.noVerticalOverlap,
      re
    );
  }
  // eslint-disable-next-line max-params
  __shouldAlignStart(s, o, i, r, a, l, c) {
    const d = i - s[l ? c.end : c.start] - r[c.end], w = s[l ? c.start : c.end] - r[c.start], $ = a ? d : w, k = $ > (a ? w : d) || $ > o;
    return a === k;
  }
  /**
   * Returns an adjusted value after resizing the browser window,
   * to avoid wrong calculations when e.g. previously set `bottom`
   * CSS property value is larger than the updated viewport height.
   * See https://github.com/vaadin/web-components/issues/4604
   */
  __adjustBottomProperty(s, o, i) {
    let r;
    if (s === o.end) {
      if (o.end === re.end) {
        const a = Math.min(window.innerHeight, document.documentElement.clientHeight);
        if (i > a && this.__oldViewportHeight) {
          const l = this.__oldViewportHeight - a;
          r = i - l;
        }
        this.__oldViewportHeight = a;
      }
      if (o.end === ae.end) {
        const a = Math.min(window.innerWidth, document.documentElement.clientWidth);
        if (i > a && this.__oldViewportWidth) {
          const l = this.__oldViewportWidth - a;
          r = i - l;
        }
        this.__oldViewportWidth = a;
      }
    }
    return r;
  }
  /**
   * Returns an object with CSS position properties to set,
   * e.g. { top: "100px" }
   */
  // eslint-disable-next-line max-params
  __calculatePositionInOneDimension(s, o, i, r, a, l) {
    const c = l ? r.start : r.end, d = l ? r.end : r.start, w = parseFloat(a.style[c] || getComputedStyle(a)[c]), $ = this.__adjustBottomProperty(c, r, w), E = o[l ? r.start : r.end] - s[i === l ? r.end : r.start], k = $ ? `${$}px` : `${w + E * (l ? -1 : 1)}px`;
    return {
      [c]: k,
      [d]: ""
    };
  }
};
var Ht = Object.defineProperty, Dt = Object.getOwnPropertyDescriptor, P = (t, e, s, o) => {
  for (var i = o > 1 ? void 0 : o ? Dt(e, s) : e, r = t.length - 1, a; r >= 0; r--)
    (a = t[r]) && (i = (o ? a(e, s, i) : a(i)) || i);
  return o && i && Ht(e, s, i), i;
};
class Ut extends CustomEvent {
  constructor(e) {
    super("color-picker-change", { detail: { value: e } });
  }
}
const Le = v`
  :host {
    --preview-size: 24px;
    --preview-color: rgba(0, 0, 0, 0);
  }

  .preview {
    --preview-bg-size: calc(var(--preview-size) / 2);
    --preview-bg-pos: calc(var(--preview-size) / 4);

    width: var(--preview-size);
    height: var(--preview-size);
    padding: 0;
    position: relative;
    overflow: hidden;
    background: none;
    border: solid 2px #888;
    border-radius: 4px;
    box-sizing: content-box;
  }

  .preview::before,
  .preview::after {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
  }

  .preview::before {
    content: '';
    background: white;
    background-image: linear-gradient(45deg, #666 25%, transparent 25%),
      linear-gradient(45deg, transparent 75%, #666 75%), linear-gradient(45deg, transparent 75%, #666 75%),
      linear-gradient(45deg, #666 25%, transparent 25%);
    background-size: var(--preview-bg-size) var(--preview-bg-size);
    background-position: 0 0, 0 0, calc(var(--preview-bg-pos) * -1) calc(var(--preview-bg-pos) * -1),
      var(--preview-bg-pos) var(--preview-bg-pos);
  }

  .preview::after {
    content: '';
    background-color: var(--preview-color);
  }
`;
let I = class extends S {
  constructor() {
    super(...arguments), this.commitValue = !1;
  }
  static get styles() {
    return [
      Le,
      v`
        #toggle {
          display: block;
        }
      `
    ];
  }
  update(t) {
    super.update(t), t.has("value") && this.overlay && this.overlay.requestContentUpdate();
  }
  firstUpdated() {
    this.overlay = document.createElement("copilot-color-picker-overlay"), this.overlay.renderer = this.renderOverlayContent.bind(this), this.overlay.owner = this, this.overlay.positionTarget = this.toggle, this.overlay.noVerticalOverlap = !0, this.overlay.addEventListener("vaadin-overlay-escape-press", this.handleOverlayEscape.bind(this)), this.overlay.addEventListener("vaadin-overlay-close", this.handleOverlayClose.bind(this)), this.append(this.overlay);
  }
  render() {
    const t = this.value || "rgba(0, 0, 0, 0)";
    return n` <button
      id="toggle"
      class="preview"
      style="--preview-color: ${t}"
      @click=${this.open}></button>`;
  }
  open() {
    this.commitValue = !1, this.overlay.opened = !0, this.overlay.style.zIndex = "1000000";
    const t = this.overlay.shadowRoot.querySelector('[part="overlay"]');
    t.style.background = "#333";
  }
  renderOverlayContent(t) {
    const s = getComputedStyle(this.toggle, "::after").getPropertyValue("background-color");
    Ve(
      n` <div>
        <copilot-color-picker-overlay-content
          .value=${s}
          .presets=${this.presets}
          @color-changed=${this.handleColorChange.bind(this)}></copilot-color-picker-overlay-content>
      </div>`,
      t
    );
  }
  handleColorChange(t) {
    this.commitValue = !0, this.dispatchEvent(new Ut(t.detail.value)), t.detail.close && (this.overlay.opened = !1, this.handleOverlayClose());
  }
  handleOverlayEscape() {
    this.commitValue = !1;
  }
  handleOverlayClose() {
    const t = this.commitValue ? "color-picker-commit" : "color-picker-cancel";
    this.dispatchEvent(new CustomEvent(t));
  }
};
P([
  h({})
], I.prototype, "value", 2);
P([
  h({})
], I.prototype, "presets", 2);
P([
  Re("#toggle")
], I.prototype, "toggle", 2);
I = P([
  f("copilot-color-picker")
], I);
let K = class extends S {
  static get styles() {
    return [
      Le,
      v`
        :host {
          display: block;
          padding: 12px;
        }

        .picker::part(saturation),
        .picker::part(hue) {
          margin-bottom: 10px;
        }

        .picker::part(hue),
        .picker::part(alpha) {
          flex: 0 0 20px;
        }

        .picker::part(saturation),
        .picker::part(hue),
        .picker::part(alpha) {
          border-radius: 3px;
        }

        .picker::part(saturation-pointer),
        .picker::part(hue-pointer),
        .picker::part(alpha-pointer) {
          width: 20px;
          height: 20px;
        }

        .swatches {
          display: grid;
          grid-template-columns: repeat(6, var(--preview-size));
          grid-column-gap: 10px;
          grid-row-gap: 6px;
          margin-top: 16px;
        }
      `
    ];
  }
  render() {
    return n` <div>
      <copilot-rgba-string-color-picker
        class="picker"
        .color=${this.value}
        @color-changed=${this.handlePickerChange}></copilot-rgba-string-color-picker>
      ${this.renderSwatches()}
    </div>`;
  }
  renderSwatches() {
    if (!this.presets || this.presets.length === 0)
      return;
    const t = this.presets.map((e) => n` <button
        class="preview"
        style="--preview-color: ${e}"
        @click=${() => this.selectPreset(e)}></button>`);
    return n` <div class="swatches">${t}</div>`;
  }
  handlePickerChange(t) {
    this.dispatchEvent(new CustomEvent("color-changed", { detail: { value: t.detail.value } }));
  }
  selectPreset(t) {
    this.dispatchEvent(new CustomEvent("color-changed", { detail: { value: t, close: !0 } }));
  }
};
P([
  h({})
], K.prototype, "value", 2);
P([
  h({})
], K.prototype, "presets", 2);
K = P([
  f("copilot-color-picker-overlay-content")
], K);
customElements.whenDefined("vaadin-overlay").then(() => {
  const t = customElements.get("vaadin-overlay");
  class e extends qt(t) {
  }
  customElements.define("copilot-color-picker-overlay", e);
});
customElements.define("copilot-rgba-string-color-picker", Lt);
var Bt = Object.defineProperty, Ft = Object.getOwnPropertyDescriptor, Wt = (t, e, s, o) => {
  for (var i = o > 1 ? void 0 : o ? Ft(e, s) : e, r = t.length - 1, a; r >= 0; r--)
    (a = t[r]) && (i = (o ? a(e, s, i) : a(i)) || i);
  return o && i && Bt(e, s, i), i;
};
let Te = class extends g {
  constructor() {
    super(...arguments), this.presets = new U();
  }
  static get styles() {
    return [
      g.styles,
      v`
        .editor-row {
          align-items: center;
        }

        .editor-row > .editor {
          display: flex;
          align-items: center;
          gap: 0.5rem;
        }
      `
    ];
  }
  update(t) {
    t.has("propertyMetadata") && (this.presets = new U(this.propertyMetadata)), super.update(t);
  }
  renderEditor() {
    var t;
    return n`
      <copilot-color-picker
        .value=${this.value}
        .presets=${this.presets.values}
        @color-picker-change=${this.handleColorPickerChange}
        @color-picker-commit=${this.handleColorPickerCommit}
        @color-picker-cancel=${this.handleColorPickerCancel}></copilot-color-picker>
      <copilot-theme-text-input
        .value=${this.value}
        .showClearButton=${((t = this.propertyValue) == null ? void 0 : t.modified) || !1}
        @change=${this.handleInputChange}></copilot-theme-text-input>
    `;
  }
  handleInputChange(t) {
    this.value = t.detail.value, this.dispatchChange(this.value);
  }
  handleColorPickerChange(t) {
    this.value = t.detail.value, this.dispatchChange(this.value);
  }
  handleColorPickerCommit() {
    this.originalValue = this.value, this.dispatchChange(this.value);
  }
  handleColorPickerCancel() {
    this.value = this.originalValue, this.dispatchChange(this.value);
  }
  dispatchChange(t) {
    const e = this.presets.tryMapToPreset(t);
    super.dispatchChange(e);
  }
  updateValueFromTheme() {
    var t;
    super.updateValueFromTheme(), this.value = this.presets.tryMapToRawValue(((t = this.propertyValue) == null ? void 0 : t.value) || "");
  }
};
Te = Wt([
  f("copilot-theme-color-property-editor")
], Te);
var Gt = Object.defineProperty, Kt = Object.getOwnPropertyDescriptor, ue = (t, e, s, o) => {
  for (var i = o > 1 ? void 0 : o ? Kt(e, s) : e, r = t.length - 1, a; r >= 0; r--)
    (a = t[r]) && (i = (o ? a(e, s, i) : a(i)) || i);
  return o && i && Gt(e, s, i), i;
};
class Zt extends CustomEvent {
  constructor(e) {
    super("open-css", { detail: { element: e } });
  }
}
let Z = class extends S {
  static get styles() {
    return v`
      .section .header {
        display: flex;
        align-items: baseline;
        justify-content: space-between;
        padding: 0.4rem var(--theme-editor-section-horizontal-padding);
        color: var(--dev-tools-text-color-emphasis);
        background-color: rgba(0, 0, 0, 0.2);
      }

      .section .property-list .property-editor:not(:last-child) {
        border-bottom: solid 1px rgba(0, 0, 0, 0.2);
      }

      .section .header .open-css {
        all: initial;
        font-family: inherit;
        font-size: var(--dev-tools-font-size-small);
        line-height: 1;
        white-space: nowrap;
        background-color: rgba(255, 255, 255, 0.12);
        color: var(--dev-tools-text-color);
        font-weight: 600;
        padding: 0.25rem 0.375rem;
        border-radius: 0.25rem;
        visibility: inherit;
      }

      .section .header .open-css:hover {
        color: var(--dev-tools-text-color-emphasis);
      }
    `;
  }
  render() {
    const t = this.metadata.elements.map((e) => this.renderSection(e));
    return n` <div>${t}</div> `;
  }
  renderSection(t) {
    const e = t.properties.map((s) => this.renderPropertyEditor(t, s));
    return n`
      <div class="section" data-testid=${t == null ? void 0 : t.displayName}>
        <div class="header">
          <span> ${t.displayName} </span>
          <button class="open-css" @click=${() => this.handleOpenCss(t)}>Edit CSS</button>
        </div>
        <div class="property-list">${e}</div>
      </div>
    `;
  }
  handleOpenCss(t) {
    this.dispatchEvent(new Zt(t));
  }
  renderPropertyEditor(t, e) {
    let s;
    switch (e.editorType) {
      case Q.checkbox:
        s = L`copilot-theme-checkbox-property-editor`;
        break;
      case Q.range:
        s = L`copilot-theme-range-property-editor`;
        break;
      case Q.color:
        s = L`copilot-theme-color-property-editor`;
        break;
      default:
        s = L`copilot-theme-text-property-editor`;
    }
    return Fe` <${s}
          class="property-editor"
          .elementMetadata=${t}
          .propertyMetadata=${e}
          .theme=${this.theme}
          data-testid=${e.propertyName}
        >
        </${s}>`;
  }
};
ue([
  h({})
], Z.prototype, "metadata", 2);
ue([
  h({})
], Z.prototype, "theme", 2);
Z = ue([
  f("copilot-theme-property-list")
], Z);
const y = () => window.Vaadin.copilotPlugins._internals;
/*!
 * dashify <https://github.com/jonschlinkert/dashify>
 *
 * Copyright (c) 2015-2017, Jon Schlinkert.
 * Released under the MIT License.
 */
var Jt = (t, e) => {
  if (typeof t != "string")
    throw new TypeError("expected a string");
  return t.trim().replace(/([a-z])([A-Z])/g, "$1-$2").replace(/\W/g, (s) => /[À-ž]/.test(s) ? s : "-").replace(/^-+|-+$/g, "").replace(/-{2,}/g, (s) => e && e.condense ? "-" : s).toLowerCase();
};
const Xt = /* @__PURE__ */ We(Jt), Yt = (t) => {
  var e, s;
  return (s = (e = t.split("\\").pop()) == null ? void 0 : e.split("/").pop()) == null ? void 0 : s.split(".")[0];
}, Qt = (t) => {
  var e;
  return (e = Yt(t)) == null ? void 0 : e.replace(/\.[^/.]+$/, "");
};
var es = Object.defineProperty, ts = Object.getOwnPropertyDescriptor, N = (t, e, s, o) => {
  for (var i = o > 1 ? void 0 : o ? ts(e, s) : e, r = t.length - 1, a; r >= 0; r--)
    (a = t[r]) && (i = (o ? a(e, s, i) : a(i)) || i);
  return o && i && es(e, s, i), i;
};
let C = class extends Ge {
  constructor() {
    super(), this.baseTheme = null, this.editedTheme = null, this.expanded = !1, this.themeEditorState = D.enabled, this.effectiveTheme = null;
  }
  static get styles() {
    return v`
      :host {
        animation: fade-in var(--dev-tools-transition-duration) ease-in;
        --theme-editor-section-horizontal-padding: 0.75rem;
        display: flex;
        flex-direction: column;
        height: var(--default-content-height, 100%);
      }

      .notice {
        padding: var(--theme-editor-section-horizontal-padding);
      }

      .notice a {
        color: var(--dev-tools-text-color-emphasis);
      }

      .hint vaadin-icon {
        color: var(--dev-tools-green-color);
        font-size: var(--lumo-icon-size-m);
      }

      .hint {
        display: flex;
        align-items: center;
        gap: var(--theme-editor-section-horizontal-padding);
      }

      .header {
        flex: 0 0 auto;
        border-bottom: solid 1px rgba(0, 0, 0, 0.2);
      }

      .header .picker-row {
        padding: var(--theme-editor-section-horizontal-padding);
        display: flex;
        gap: 20px;
        align-items: center;
        justify-content: space-between;
      }

      .picker {
        flex: 1 1 0;
        min-width: 0;
        display: flex;
        align-items: center;
      }

      .picker button {
        min-width: 0;
        display: inline-flex;
        align-items: center;
        padding: 0;
        line-height: 20px;
        border: none;
        background: none;
        color: var(--dev-tools-text-color);
      }

      .picker button:not(:disabled):hover {
        color: var(--dev-tools-text-color-emphasis);
      }

      .picker svg,
      .picker .component-type {
        flex: 0 0 auto;
        margin-right: 4px;
      }

      .picker .instance-name {
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        color: #e5a2fce5;
      }

      .picker .instance-name-quote {
        color: #e5a2fce5;
      }

      .picker .no-selection {
        font-style: italic;
      }

      .actions {
        display: flex;
        align-items: center;
        gap: 8px;
      }

      .property-list {
        flex: 1 1 auto;
        overflow-y: auto;
      }

      .link-button {
        all: initial;
        font-family: inherit;
        font-size: var(--dev-tools-font-size-small);
        line-height: 1;
        white-space: nowrap;
        color: inherit;
        font-weight: 600;
        text-decoration: underline;
      }

      .link-button:focus,
      .link-button:hover {
        color: var(--dev-tools-text-color-emphasis);
      }

      .icon-button {
        padding: 0;
        line-height: 0;
        border: none;
        background: none;
        color: var(--dev-tools-text-color);
      }

      .icon-button:disabled {
        opacity: 0.5;
      }

      .icon-button:not(:disabled):hover {
        color: var(--dev-tools-text-color-emphasis);
      }
    `;
  }
  connectedCallback() {
    super.connectedCallback(), this.reaction(
      () => y().copilotUiState.getSelections,
      () => {
        this.refreshPicked(), this.requestUpdate();
      }
    ), this.refreshPicked();
  }
  async refreshPicked() {
    var s;
    if (y().copilotUiState.getSelections.length !== 1) {
      this.context = null;
      return;
    }
    const t = y().copilotUiState.getSelections[0];
    if (!t.metadata) {
      this.context = { scope: ((s = this.context) == null ? void 0 : s.scope) ?? u.local, selectedElement: t }, this.baseTheme = null, this.editedTheme = null, this.effectiveTheme = null;
      return;
    }
    await this.refreshComponentAndTheme(t);
  }
  firstUpdated() {
    this.history = new tt(this.api), this.historyActions = this.history.allowedActions, this.undoRedoListener = (t) => {
      var s, o;
      const e = t.key === "Z" || t.key === "z";
      e && (t.ctrlKey || t.metaKey) && t.shiftKey ? (s = this.historyActions) != null && s.allowRedo && this.handleRedo() : e && (t.ctrlKey || t.metaKey) && (o = this.historyActions) != null && o.allowUndo && this.handleUndo();
    }, document.addEventListener("vaadin-theme-updated", () => {
      A.clear(), this.refreshTheme();
    }), document.addEventListener("keydown", this.undoRedoListener), this.dispatchEvent(new CustomEvent("before-open"));
  }
  update(t) {
    super.update(t);
  }
  disconnectedCallback() {
    super.disconnectedCallback(), document.removeEventListener("keydown", this.undoRedoListener), this.dispatchEvent(new CustomEvent("after-close"));
  }
  render() {
    var t, e, s, o;
    return this.themeEditorState === D.missing_theme ? this.renderMissingThemeNotice() : n`
      <div class="header">
        <div class="picker-row">
          ${this.renderPicker()}
          <div class="actions">
            ${(e = (t = this.context) == null ? void 0 : t.selectedElement) != null && e.metadata ? n` <copilot-theme-scope-selector
                  .value=${this.context.scope}
                  .metadata=${this.context.selectedElement.metadata}
                  @scope-change=${this.handleScopeChange}></copilot-theme-scope-selector>` : null}
            <button
              class="icon-button"
              data-testid="undo"
              ?disabled=${!((s = this.historyActions) != null && s.allowUndo)}
              @click=${this.handleUndo}>
              ${ve.undo}
            </button>
            <button
              class="icon-button"
              data-testid="redo"
              ?disabled=${!((o = this.historyActions) != null && o.allowRedo)}
              @click=${this.handleRedo}>
              ${ve.redo}
            </button>
          </div>
        </div>
        ${this.renderLocalClassNameEditor()}
      </div>
      ${this.renderPropertyList()}
    `;
  }
  renderMissingThemeNotice() {
    return n`
      <div class="notice">
        It looks like you have not set up an application theme yet. Theme editor requires an existing theme to work
        with. Please check our
        <a href="https://vaadin.com/docs/latest/styling/application-theme" target="_blank">documentation</a>
        on how to set up an application theme.
      </div>
    `;
  }
  renderPropertyList() {
    var s;
    if (!this.context)
      return null;
    if (!((s = this.context.selectedElement) != null && s.metadata)) {
      const o = this.context.selectedElement.element.localName;
      return n`
        <div class="notice">Styling <code>&lt;${o}&gt;</code> components is not supported at the moment.</div>
      `;
    }
    const t = this.context.selectedElement.metadata;
    if (this.context.scope === u.local && !this.context.accessible) {
      const o = t.displayName;
      return n`
        ${t.notAccessibleDescription && this.context.scope === u.local ? n`<div class="notice hint" style="padding-bottom: 0;">
              <vaadin-icon icon="vaadin:lightbulb"></vaadin-icon>
              <div>${t.notAccessibleDescription}</div>
            </div>` : ""}
        <div class="notice">
          The selected ${o} cannot be styled locally. Currently, Theme Editor only supports styling
          instances that are assigned to a local variable, like so:
          <pre><code>Button saveButton = new Button("Save");</code></pre>
          If you want to modify the code so that it satisfies this requirement,
          <button class="link-button" @click=${this.handleShowComponent}>click here</button>
          to open it in your IDE. Alternatively you can choose to style all ${o}s by selecting "Global" from
          the scope dropdown above.
        </div>
      `;
    }
    return n` ${t.description && this.context.scope === u.local ? n`<div class="notice hint">
            <vaadin-icon icon="vaadin:lightbulb"></vaadin-icon>
            <div>${t.description}</div>
          </div>` : ""}
      <copilot-theme-property-list
        class="property-list"
        .metadata=${t}
        .theme=${this.effectiveTheme}
        @theme-property-value-change=${this.handlePropertyChange}
        @open-css=${this.handleOpenCss}></copilot-theme-property-list>`;
  }
  handleShowComponent() {
    if (!this.context)
      return;
    if (ee()) {
      const e = y().getFlowComponent(this.context.selectedElement.element), s = {
        nodeId: e.nodeId,
        uiId: e.uiId,
        element: e.element
      };
      y().copilotEventBus.send("showComponentCreateLocation", s);
      return;
    }
    const t = Ke(this.context.selectedElement.element);
    y().copilotEventBus.send(`${Ze}show-component`, {
      ...t
    });
  }
  async handleOpenCss(t) {
    if (!this.context)
      return;
    await this.ensureLocalClassName();
    const e = {
      themeScope: this.context.scope,
      localClassName: this.context.localClassName
    }, s = R(t.detail.element, e);
    await this.api.openCss(s);
  }
  renderPicker() {
    var e, s;
    let t;
    if ((s = (e = this.context) == null ? void 0 : e.selectedElement) != null && s.metadata) {
      const o = this.context.selectedElement.metadata, i = this.context.scope === u.local ? o.displayName : `All ${o.displayName}s`, r = n`<span class="component-type">${i}</span>`, a = this.context.scope === u.local ? Qe(this.context.selectedElement) : null, l = a ? n` <span class="instance-name-quote">"</span><span class="instance-name">${a}</span
            ><span class="instance-name-quote">"</span>` : null;
      t = n`${r} ${l}`;
    } else
      t = n`<span class="no-selection">Pick a single element to get started</span>`;
    return n` <div class="picker">${t}</div> `;
  }
  renderLocalClassNameEditor() {
    var s;
    const t = ((s = this.context) == null ? void 0 : s.scope) === u.local && this.context.accessible;
    if (!this.context || !t)
      return null;
    const e = this.context.localClassName || this.context.suggestedClassName;
    return n` <copilot-theme-class-name-editor
      .className=${e}
      @class-name-change=${this.handleClassNameChange}>
    </copilot-theme-class-name-editor>`;
  }
  async handleClassNameChange(t) {
    if (!this.context)
      return;
    const e = this.context.localClassName, s = t.detail.value;
    if (e) {
      const o = this.context.selectedElement.element;
      this.context.localClassName = s, await this.updateClassName(o, s, e);
    } else
      this.context = {
        ...this.context,
        suggestedClassName: s
      };
  }
  handleScopeChange(t) {
    this.context && this.refreshTheme({
      ...this.context,
      scope: t.detail.value
    });
  }
  async handlePropertyChange(t) {
    if (!this.context || !this.baseTheme || !this.editedTheme)
      return;
    const { element: e, property: s, value: o } = t.detail;
    this.editedTheme.updatePropertyValue(e.selector, s.propertyName, o, !0), this.effectiveTheme = x.combine(this.baseTheme, this.editedTheme), await this.ensureLocalClassName();
    const i = {
      themeScope: this.context.scope,
      localClassName: this.context.localClassName
    }, r = Je(e, i, s.propertyName, o);
    try {
      const a = await this.api.setCssRules([r]);
      this.historyActions = this.history.push(a.requestId);
      const l = Xe(r);
      A.add(l);
    } catch (a) {
      H("Failed to update property value", a);
    }
  }
  async handleUndo() {
    this.historyActions = await this.history.undo(), await this.refreshComponentAndTheme();
  }
  async handleRedo() {
    this.historyActions = await this.history.redo(), await this.refreshComponentAndTheme();
  }
  async ensureLocalClassName() {
    if (!this.context || this.context.scope === u.global || this.context.localClassName)
      return;
    if (!this.context.localClassName && !this.context.suggestedClassName)
      throw new Error(
        "Cannot assign local class name for the component because it does not have a suggested class name"
      );
    const t = this.context.selectedElement.element, e = this.context.suggestedClassName;
    this.context.localClassName = e, await this.updateClassName(t, e);
  }
  async refreshComponentAndTheme(t) {
    var s;
    if (!t)
      return;
    let e;
    if (ee()) {
      const o = y().getFlowComponent(t.element);
      if (!o)
        return;
      e = await this.api.loadComponentMetadata(o), A.previewLocalClassName(t.element, e.className);
    } else
      e = {
        accessible: !0,
        className: t.element.className,
        suggestedClassName: this.suggestClassName(t)
      };
    await this.refreshTheme({
      scope: ((s = this.context) == null ? void 0 : s.scope) || u.local,
      localClassName: e.className,
      suggestedClassName: e.suggestedClassName,
      accessible: e.accessible,
      selectedElement: t
    });
  }
  async refreshTheme(t) {
    var a;
    const e = t || this.context;
    if (!((a = e == null ? void 0 : e.selectedElement) != null && a.metadata))
      return;
    if (e.scope === u.local && !e.accessible) {
      this.context = e, this.baseTheme = null, this.editedTheme = null, this.effectiveTheme = null;
      return;
    }
    let o = new x(e.selectedElement.metadata);
    if (!(e.scope === u.local && !e.localClassName)) {
      const l = {
        themeScope: e.scope,
        localClassName: e.localClassName
      }, c = e.selectedElement.metadata.elements.map(
        (w) => R(w, l)
      ), d = await this.api.loadRules(c);
      o = x.fromServerRules(
        e.selectedElement.metadata,
        l,
        d.rules
      );
    }
    const r = await Ye(e.selectedElement.metadata);
    this.context = e, this.baseTheme = r, this.editedTheme = o, this.effectiveTheme = x.combine(r, this.editedTheme);
  }
  suggestClassName(t) {
    var s;
    const e = (s = y().getElementSourceInfo(t.element)) == null ? void 0 : s.fileName;
    return Xt((e ? Qt(e) : "") + t.displayName, { condense: !0 });
  }
  async updateClassName(t, e, s) {
    if (ee()) {
      const o = y().getFlowComponent(t), i = await this.api.setLocalClassName(o, e);
      this.historyActions = this.history.push(
        i.requestId,
        () => A.previewLocalClassName(t, e),
        () => A.previewLocalClassName(t, s)
      );
      return;
    }
    s ? e = t.className.replace(s, e) : t.className && (e = `${t.className} ${e}`), await y().setElementAttributeValue(t, "className", e), await this.api.setLocalClassNameReact(t.localName, e, s), t.className = e;
  }
};
N([
  h({})
], C.prototype, "expanded", 2);
N([
  h({})
], C.prototype, "themeEditorState", 2);
N([
  h()
], C.prototype, "api", 2);
N([
  b()
], C.prototype, "historyActions", 2);
N([
  b()
], C.prototype, "context", 2);
N([
  b()
], C.prototype, "effectiveTheme", 2);
C = N([
  f("copilot-theme-editor")
], C);
var ce = /* @__PURE__ */ ((t) => (t.state = "copilot-theme-editor-state", t.response = "copilot-theme-editor-response", t.loadComponentMetadata = "copilot-theme-editor-metadata", t.setLocalClassName = "copilot-theme-editor-local-class-name", t.setCssRules = "copilot-theme-editor-rules", t.loadRules = "copilot-theme-editor-load-rules", t.history = "copilot-theme-editor-history", t.openCss = "copilot-theme-editor-open-css", t))(ce || {});
class ss {
  constructor() {
    this.pendingRequests = {}, this.requestCounter = 0;
  }
  sendRequest(e, s) {
    const o = (this.requestCounter++).toString(), i = s.uiId ?? this.getGlobalUiId();
    return new Promise((r, a) => {
      y().copilotEventBus.send(e, {
        ...s,
        requestId: o,
        uiId: i
      }), this.pendingRequests[o] = {
        resolve: r,
        reject: a
      };
    });
  }
  handleResponse(e) {
    const s = this.pendingRequests[e.requestId];
    if (!s) {
      console.warn("Received response for unknown request");
      return;
    }
    delete this.pendingRequests[e.requestId], e.code === "ok" ? s.resolve(e) : s.reject(e);
  }
  loadComponentMetadata(e) {
    return this.sendRequest("copilot-theme-editor-metadata", { nodeId: e.nodeId });
  }
  setLocalClassName(e, s) {
    return this.sendRequest("copilot-theme-editor-local-class-name", { nodeId: e.nodeId, className: s });
  }
  setLocalClassNameReact(e, s, o) {
    return this.sendRequest("copilot-theme-editor-local-class-name", { tagName: e, oldClassName: o, className: s });
  }
  setCssRules(e) {
    return this.sendRequest("copilot-theme-editor-rules", { rules: e });
  }
  loadRules(e) {
    return this.sendRequest("copilot-theme-editor-load-rules", { selectors: e });
  }
  undo(e) {
    return this.sendRequest("copilot-theme-editor-history", { undo: e });
  }
  redo(e) {
    return this.sendRequest("copilot-theme-editor-history", { redo: e });
  }
  openCss(e) {
    return this.sendRequest("copilot-theme-editor-open-css", { selector: e });
  }
  getGlobalUiId() {
    if (this.globalUiId === void 0) {
      const e = window.Vaadin;
      if (e && e.Flow) {
        const { clients: s } = e.Flow, o = Object.keys(s);
        for (const i of o) {
          const r = s[i];
          if (r.getNodeId) {
            this.globalUiId = r.getUIId();
            break;
          }
        }
      }
    }
    return this.globalUiId ?? -1;
  }
}
var os = Object.defineProperty, is = Object.getOwnPropertyDescriptor, me = (t, e, s, o) => {
  for (var i = o > 1 ? void 0 : o ? is(e, s) : e, r = t.length - 1, a; r >= 0; r--)
    (a = t[r]) && (i = (o ? a(e, s, i) : a(i)) || i);
  return o && i && os(e, s, i), i;
};
const Oe = window.Vaadin.devTools;
let J = class extends je {
  constructor() {
    super(), this.api = new ss(), this.handleStateEvent = (t) => {
      this.themeEditorState = t.data.state;
    }, this.handleServerEvent = (t) => {
      this.api.handleResponse(t.data);
    }, this.expanded = !0, this.themeEditorState = D.disabled;
  }
  connectedCallback() {
    super.connectedCallback(), this.onCommand(ce.state, this.handleStateEvent), this.onCommand(ce.response, this.handleServerEvent);
  }
  render() {
    return n` <copilot-theme-editor
      .expanded=${this.expanded}
      .themeEditorState=${this.themeEditorState}
      .api=${this.api}
      @before-open=${this.disableJavaLiveReload}
      @after-close=${this.enableJavaLiveReload}></copilot-theme-editor>`;
  }
  disableJavaLiveReload() {
    var t;
    (t = Oe.javaConnection) == null || t.setActive(!1);
  }
  enableJavaLiveReload() {
    var t;
    (t = Oe.javaConnection) == null || t.setActive(!0);
  }
};
me([
  b()
], J.prototype, "expanded", 2);
me([
  b()
], J.prototype, "themeEditorState", 2);
J = me([
  f("copilot-theme-editor-panel")
], J);
const rs = {
  header: "Theme Editor",
  expanded: !0,
  draggable: !0,
  panelOrder: 0,
  panel: "right",
  floating: !1,
  tag: "copilot-theme-editor-panel"
}, as = {
  init(t) {
    t.addPanel(rs);
  }
};
window.Vaadin.copilotPlugins.push(as);
export {
  J as CopilotThemeEditorPanel
};
