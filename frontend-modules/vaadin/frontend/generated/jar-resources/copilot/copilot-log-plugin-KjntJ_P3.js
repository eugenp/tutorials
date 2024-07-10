import { M as p, x as r, r as h, t as g } from "./copilot-sWvu6xks.js";
import { B as u } from "./base-panel-Zq4jKPA_.js";
var c = Object.defineProperty, f = Object.getOwnPropertyDescriptor, l = (t, e, s, o) => {
  for (var i = o > 1 ? void 0 : o ? f(e, s) : e, a = t.length - 1, d; a >= 0; a--)
    (d = t[a]) && (i = (o ? d(e, s, i) : d(i)) || i);
  return o && i && c(e, s, i), i;
};
let n = class extends u {
  constructor() {
    super(), this.nextMessageId = 1, this.transitionDuration = 0, this.handleLogEvent = (t) => {
      const e = t.data;
      this.log(e.type, e.message, e.details, e.link);
    }, this.notifications = [], this.unreadErrors = !1, this.messages = [], this.catchErrors();
  }
  connectedCallback() {
    super.connectedCallback(), this.onCommand("log", this.handleLogEvent), this.transitionDuration = parseInt(
      window.getComputedStyle(this).getPropertyValue("--dev-tools-transition-duration"),
      10
    );
  }
  activate() {
    this.unreadErrors = !1, this.updateComplete.then(() => {
      const t = this.renderRoot.querySelector(".message-tray .message:last-child");
      t && t.scrollIntoView();
    });
  }
  format(t) {
    return t.toString();
  }
  catchErrors() {
    const t = window.Vaadin.ConsoleErrors;
    window.Vaadin.ConsoleErrors = {
      push: (e) => {
        this.log(p.ERROR, e.map((s) => this.format(s)).join(" ")), t.push(e);
      }
    };
  }
  render() {
    return r`<div class="message-tray">${this.messages.map((t) => this.renderMessage(t))}</div>`;
  }
  renderMessage(t) {
    return r`
      <div
        class="message ${t.type} ${t.deleted ? "animate-out" : ""} ${t.details || t.link ? "has-details" : ""}">
        <div class="message-content">
          <div class="message-heading">${t.message}</div>
          <div class="message-details" ?hidden="${!t.details && !t.link}">
            ${t.details ? r`<p>${t.details}</p>` : ""}
            ${t.link ? r`<a class="ahreflike" href="${t.link}" target="_blank">Learn more</a>` : ""}
          </div>
          ${t.persistentId ? r`<div
                class="persist ${t.dontShowAgain ? "on" : "off"}"
                @click=${() => this.toggleDontShowAgain(t.id)}>
                Don’t show again
              </div>` : ""}
        </div>
      </div>
    `;
  }
  toggleDontShowAgain(t) {
    const e = this.findNotificationIndex(t);
    if (e !== -1 && !this.notifications[e].deleted) {
      const s = this.notifications[e];
      s.dontShowAgain = !s.dontShowAgain, this.requestUpdate();
    }
  }
  findNotificationIndex(t) {
    let e = -1;
    return this.notifications.some((s, o) => s.id === t ? (e = o, !0) : !1), e;
  }
  log(t, e, s, o) {
    const i = this.nextMessageId;
    for (this.nextMessageId += 1, this.messages.push({
      id: i,
      type: t,
      message: e,
      details: s,
      link: o,
      dontShowAgain: !1,
      deleted: !1
    }); this.messages.length > n.MAX_LOG_ROWS; )
      this.messages.shift();
    this.requestUpdate(), this.updateComplete.then(() => {
      const a = this.renderRoot.querySelector(".message-tray .message:last-child");
      a ? (setTimeout(() => a.scrollIntoView({ behavior: "smooth" }), this.transitionDuration), this.unreadErrors = !1) : t === p.ERROR && (this.unreadErrors = !0);
    });
  }
};
n.MAX_LOG_ROWS = 1e3;
l([
  h()
], n.prototype, "notifications", 2);
l([
  h()
], n.prototype, "unreadErrors", 2);
l([
  h()
], n.prototype, "messages", 2);
n = l([
  g("copilot-log-panel")
], n);
const v = {
  header: "Log",
  expanded: !0,
  draggable: !0,
  panelOrder: 0,
  panel: "bottom",
  floating: !1,
  tag: "copilot-log-panel"
}, m = {
  init(t) {
    t.addPanel(v);
  }
};
window.Vaadin.copilotPlugins.push(m);
export {
  n as CopilotLogPanel
};
