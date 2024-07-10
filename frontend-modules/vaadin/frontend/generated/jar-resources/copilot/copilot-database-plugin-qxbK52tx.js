import { x as p, r as c, t as d } from "./copilot-sWvu6xks.js";
import { B as h } from "./base-panel-Zq4jKPA_.js";
async function u(e) {
  const t = () => !!e.document.querySelector("[name='url']");
  let n;
  const o = new Promise((l) => {
    n = l;
  }), a = setInterval(() => {
    t() && (clearInterval(a), n());
  }, 10);
  return o;
}
function b(e, t) {
  const n = e.document;
  n.querySelector("[name='url']").value = t, n.querySelector("[type='submit']").click();
}
var m = Object.defineProperty, f = Object.getOwnPropertyDescriptor, i = (e, t, n, o) => {
  for (var a = o > 1 ? void 0 : o ? f(t, n) : t, l = e.length - 1, s; l >= 0; l--)
    (s = e[l]) && (a = (o ? s(t, n, a) : s(a)) || a);
  return o && a && m(t, n, a), a;
};
let r = class extends h {
  constructor() {
    super(...arguments), this.handleDbInitEvent = (e) => {
      const t = e.data.h2;
      t && (this.h2path = t.path, this.h2jdbcUrl = t.jdbcUrl);
    };
  }
  connectedCallback() {
    super.connectedCallback(), this.onCommand("devtools-database-init", this.handleDbInitEvent);
  }
  render() {
    return p`<div style="padding: 1em;display:inline-flex;flex-direction:column;">
      <button ?disabled=${!this.h2path} @click=${this.openH2Console}>Open H2 console</button>${this.h2path ? "" : "H2 is not in use"}
    </div>`;
  }
  async openH2Console() {
    const e = window.open(this.h2path);
    await u(e), b(e, this.h2jdbcUrl);
  }
};
i([
  c()
], r.prototype, "h2path", 2);
i([
  c()
], r.prototype, "h2jdbcUrl", 2);
r = i([
  d("copilot-database-panel")
], r);
const v = {
  header: "Database",
  expanded: !0,
  draggable: !0,
  panelOrder: 0,
  panel: "right",
  floating: !1,
  tag: "copilot-database-panel"
}, g = {
  init(e) {
    e.addPanel(v);
  }
};
window.Vaadin.copilotPlugins.push(g);
export {
  r as CopilotDatabasePanel
};
