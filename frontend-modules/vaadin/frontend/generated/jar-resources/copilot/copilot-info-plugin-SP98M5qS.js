import { g as b, V as h, i as C, x as v, M as I, r as w, t as E } from "./copilot-sWvu6xks.js";
import { B as x } from "./base-panel-Zq4jKPA_.js";
var A = function() {
  var e = document.getSelection();
  if (!e.rangeCount)
    return function() {
    };
  for (var t = document.activeElement, a = [], s = 0; s < e.rangeCount; s++)
    a.push(e.getRangeAt(s));
  switch (t.tagName.toUpperCase()) {
    case "INPUT":
    case "TEXTAREA":
      t.blur();
      break;
    default:
      t = null;
      break;
  }
  return e.removeAllRanges(), function() {
    e.type === "Caret" && e.removeAllRanges(), e.rangeCount || a.forEach(function(n) {
      e.addRange(n);
    }), t && t.focus();
  };
}, R = A, m = {
  "text/plain": "Text",
  "text/html": "Url",
  default: "Text"
}, S = "Copy to clipboard: #{key}, Enter";
function D(e) {
  var t = (/mac os x/i.test(navigator.userAgent) ? "⌘" : "Ctrl") + "+C";
  return e.replace(/#{\s*key\s*}/g, t);
}
function T(e, t) {
  var a, s, n, c, l, o, u = !1;
  t || (t = {}), a = t.debug || !1;
  try {
    n = R(), c = document.createRange(), l = document.getSelection(), o = document.createElement("span"), o.textContent = e, o.ariaHidden = "true", o.style.all = "unset", o.style.position = "fixed", o.style.top = 0, o.style.clip = "rect(0, 0, 0, 0)", o.style.whiteSpace = "pre", o.style.webkitUserSelect = "text", o.style.MozUserSelect = "text", o.style.msUserSelect = "text", o.style.userSelect = "text", o.addEventListener("copy", function(i) {
      if (i.stopPropagation(), t.format)
        if (i.preventDefault(), typeof i.clipboardData > "u") {
          a && console.warn("unable to use e.clipboardData"), a && console.warn("trying IE specific stuff"), window.clipboardData.clearData();
          var f = m[t.format] || m.default;
          window.clipboardData.setData(f, e);
        } else
          i.clipboardData.clearData(), i.clipboardData.setData(t.format, e);
      t.onCopy && (i.preventDefault(), t.onCopy(i.clipboardData));
    }), document.body.appendChild(o), c.selectNodeContents(o), l.addRange(c);
    var y = document.execCommand("copy");
    if (!y)
      throw new Error("copy command was unsuccessful");
    u = !0;
  } catch (i) {
    a && console.error("unable to copy using execCommand: ", i), a && console.warn("trying IE specific stuff");
    try {
      window.clipboardData.setData(t.format || "text", e), t.onCopy && t.onCopy(window.clipboardData), u = !0;
    } catch (f) {
      a && console.error("unable to copy using clipboardData: ", f), a && console.error("falling back to prompt"), s = D("message" in t ? t.message : S), window.prompt(s, e);
    }
  } finally {
    l && (typeof l.removeRange == "function" ? l.removeRange(c) : l.removeAllRanges()), o && document.body.removeChild(o), n();
  }
  return u;
}
var $ = T;
const V = /* @__PURE__ */ b($);
var d = /* @__PURE__ */ ((e) => (e.ACTIVE = "active", e.INACTIVE = "inactive", e.UNAVAILABLE = "unavailable", e.ERROR = "error", e))(d || {}), N = Object.defineProperty, O = Object.getOwnPropertyDescriptor, g = (e, t, a, s) => {
  for (var n = s > 1 ? void 0 : s ? O(t, a) : t, c = e.length - 1, l; c >= 0; c--)
    (l = e[c]) && (n = (s ? l(t, a, n) : l(n)) || n);
  return s && n && N(t, a, n), n;
};
const r = window.Vaadin.devTools;
let p = class extends x {
  constructor() {
    super(), this.handleServerInfoEvent = (e) => {
      this.serverInfo = e.data;
      const t = Math.max(this.getIndex("Flow"), this.getIndex("Vaadin"), this.getIndex("Hilla"));
      this.serverInfo.versions.splice(t + 1, 0, { name: "Copilot", version: h }), C().then((a) => {
        a && (this.serverInfo.versions.push({ name: "Vaadin Employee", version: "true" }), this.requestUpdate("serverInfo"));
      });
    }, this.serverInfo = {
      versions: []
    };
  }
  connectedCallback() {
    super.connectedCallback(), this.onCommand("serverInfo", this.handleServerInfoEvent);
  }
  getIndex(e) {
    return this.serverInfo.versions.findIndex((t) => t.name === e);
  }
  render() {
    return v` <div class="info-tray">
      <button class="button copy" @click=${this.copyInfoToClipboard}>Copy</button>
      <dl>
        ${this.serverInfo.versions.map(
      (e) => v`
            <dt>${e.name}</dt>
            <dd>${e.version}</dd>
          `
    )}
        <dt>Browser</dt>
        <dd>${navigator.userAgent}</dd>
        <dt>
          Live reload
          <label class="switch">
            <input
              id="toggle"
              type="checkbox"
              ?disabled=${!r.conf.enable || (r.frontendStatus === d.UNAVAILABLE || r.frontendStatus === d.ERROR) && (r.javaStatus === d.UNAVAILABLE || r.javaStatus === d.ERROR)}
              ?checked="${r.frontendStatus === d.ACTIVE || r.javaStatus === d.ACTIVE}"
              @change=${(e) => r.setActive(e.target.checked)} />
            <span class="slider"></span>
          </label>
        </dt>
        <dd class="live-reload-status" style="--status-color: ${r.getStatusColor(r.javaStatus)}">
          Java ${r.javaStatus} ${r.conf.backend ? `(${r.conf.backend})` : ""}
        </dd>
        <dd class="live-reload-status" style="--status-color: ${r.getStatusColor(r.frontendStatus)}">
          Front end ${r.frontendStatus}
        </dd>
      </dl>
    </div>`;
  }
  copyInfoToClipboard() {
    const e = this.renderRoot.querySelectorAll(".info-tray dt, .info-tray dd"), t = Array.from(e).map((a) => (a.localName === "dd" ? ": " : `
`) + a.textContent.trim()).join("").replace(/^\n/, "");
    V(t), r.showNotification(
      I.INFORMATION,
      "Environment information copied to clipboard",
      void 0,
      void 0,
      "versionInfoCopied"
    );
  }
};
g([
  w()
], p.prototype, "serverInfo", 2);
p = g([
  E("copilot-info-panel")
], p);
const P = {
  header: "Info",
  expanded: !0,
  draggable: !0,
  panelOrder: 0,
  panel: "right",
  floating: !1,
  tag: "copilot-info-panel"
}, k = {
  init(e) {
    e.addPanel(P);
  }
};
window.Vaadin.copilotPlugins.push(k);
export {
  p as CopilotInfoPanel
};
