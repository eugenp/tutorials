import { x as d, M as p, r as u, t as g } from "./copilot-sWvu6xks.js";
import { B as f } from "./base-panel-Zq4jKPA_.js";
var h = Object.defineProperty, v = Object.getOwnPropertyDescriptor, c = (e, a, t, s) => {
  for (var r = s > 1 ? void 0 : s ? v(a, t) : a, n = e.length - 1, o; n >= 0; n--)
    (o = e[n]) && (r = (s ? o(a, t, r) : o(r)) || r);
  return s && r && h(a, t, r), r;
};
const l = window.Vaadin.devTools;
let i = class extends f {
  constructor() {
    super(), this.handleFeatureFlags = (e) => {
      this.features = e.data.features;
    }, this.features = [];
  }
  connectedCallback() {
    super.connectedCallback(), this.onCommand("featureFlags", this.handleFeatureFlags);
  }
  render() {
    return d` <div class="features-tray">
      ${this.features.map(
      (e) => d` <div class="feature">
            <label class="switch">
              <input
                class="feature-toggle"
                id="feature-toggle-${e.id}"
                type="checkbox"
                ?checked=${e.enabled}
                @change=${(a) => this.toggleFeatureFlag(a, e)} />
              <span class="slider"></span>
              ${e.title}
            </label>
            <a class="ahreflike" href="${e.moreInfoLink}" target="_blank">Learn more</a>
          </div>`
    )}
    </div>`;
  }
  toggleFeatureFlag(e, a) {
    const t = e.target.checked;
    l.frontendConnection ? (l.frontendConnection.send("setFeature", { featureId: a.id, enabled: t }), l.showNotification(
      p.INFORMATION,
      `“${a.title}” ${t ? "enabled" : "disabled"}`,
      a.requiresServerRestart ? "This feature requires a server restart" : void 0,
      void 0,
      `feature${a.id}${t ? "Enabled" : "Disabled"}`
    )) : l.log("error", `Unable to toggle feature ${a.title}: No server connection available`);
  }
};
c([
  u()
], i.prototype, "features", 2);
i = c([
  g("copilot-features-panel")
], i);
const b = {
  header: "Features",
  expanded: !0,
  draggable: !0,
  panelOrder: 0,
  panel: "right",
  floating: !1,
  tag: "copilot-features-panel"
}, F = {
  init(e) {
    e.addPanel(b);
  }
};
window.Vaadin.copilotPlugins.push(F);
export {
  i as CopilotFeaturesPanel
};
