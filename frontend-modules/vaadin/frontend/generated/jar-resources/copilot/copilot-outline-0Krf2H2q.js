import { a as d, b as g, c as v, x as b, d as n, e as w, f as u, h as p, j as C, k as I, l as h, m as x, n as y, o as E, p as D, q as P, s as A, r as F, F as $, t as k } from "./copilot-sWvu6xks.js";
import { B as _ } from "./base-panel-Zq4jKPA_.js";
const O = "copilot-outline-panel{padding:0;position:relative;height:var(--default-content-height, 100%);overflow:auto}copilot-outline-panel vaadin-grid{background-color:transparent;--vaadin-grid-cell-background: transparent;--vaadin-grid-cell-padding: var(--space-100) var(--space-150);font:inherit;color:inherit}copilot-outline-panel vaadin-grid::part(cell){cursor:default;min-height:auto}copilot-outline-panel vaadin-grid::part(row):hover{outline:1px solid var(--selection-color);outline-offset:-1px}copilot-outline-panel vaadin-grid::part(selected-row){background:var(--blue-100);color:var(--color-high-contrast)}copilot-outline-panel vaadin-grid::part(selected-row):hover{outline:0}copilot-outline-panel vaadin-grid::part(cell):focus-visible,copilot-outline-panel vaadin-grid::part(row):focus-visible{outline:2px solid var(--blue-300);outline-offset:-2px}copilot-outline-panel vaadin-grid-tree-toggle::part(toggle){color:var(--border-color-high-contrast);opacity:0}copilot-outline-panel:hover vaadin-grid-tree-toggle::part(toggle){opacity:1}";
var S = Object.defineProperty, T = Object.getOwnPropertyDescriptor, c = (e, t, i, o) => {
  for (var r = o > 1 ? void 0 : o ? T(t, i) : t, l = e.length - 1, a; l >= 0; l--)
    (a = e[l]) && (r = (o ? a(t, i, r) : a(r)) || r);
  return o && r && S(t, i, r), r;
};
function f(e) {
  if (e.currentTarget)
    return e.currentTarget.getEventContext(e).item;
}
let s = class extends _ {
  constructor() {
    super(...arguments), this.expandedItems = [], this.initialExpandDone = !1, this.filter = (e) => d(e) ? !0 : !!g(e), this.getFilteredChildren = (e) => {
      const t = v(e);
      if (t.length === 0)
        return [];
      const i = t.filter(this.filter);
      return i.length === t.length ? t : t.flatMap((o) => i.includes(o) ? o : this.getFilteredChildren(o));
    }, this.dataProvider = (e, t) => {
      if (!this.reactApp)
        t([], 0);
      else if (!e.parentItem)
        t([this.reactApp], 1);
      else {
        const i = this.getFilteredChildren(e.parentItem);
        t(i, i.length);
      }
    };
  }
  connectedCallback() {
    super.connectedCallback(), this.componentTreeUpdated(), this.onEventBus("component-tree-updated", () => this.componentTreeUpdated());
  }
  render() {
    return b`
      <style>
        ${O}
      </style>
      <vaadin-grid
        all-rows-visible
        .dataProvider=${this.dataProvider}
        drop-mode="on-top-or-between"
        .selectedItems=${n.getSelections.map((e) => w(e.element))}
        @keydown=${this.gridKeyDown}
        @grid-drop="${(e) => {
      const { detail: t } = e, { dropTargetItem: i, dropLocation: o } = t, r = e.originalEvent;
      this.handleDropEvent(i, o, r.dataTransfer);
    }}"
        @grid-dragstart="${(e) => {
      this.draggedItem = e.detail.draggedItems[0];
    }}"
        .dropFilter="${(e) => e.item !== this.draggedItem}"
        @mousemove=${this.gridItemMouseMove}
        @click=${this.gridItemClick}
        theme="no-border no-row-borders">
        <vaadin-grid-tree-column
          auto-width
          .__getToggleContent=${this.renderToggleColumn}
          .__isLeafItem=${this.isLeafItem.bind(this)}></vaadin-grid-tree-column>
      </vaadin-grid>
    `;
  }
  handleDropEvent(e, t, i) {
    const o = u.dragSource;
    u.endDrag();
    let r;
    switch (t) {
      case "below":
        r = "after";
        break;
      case "above":
        r = "before";
        break;
      case "on-top":
        r = "append";
        break;
      default:
        throw new Error("Unknown drop location");
    }
    if (o)
      p.emit("drop-outline", {
        draggedElement: o,
        dropLocation: r,
        fiberNode: e
      });
    else {
      const l = i.getData("text/plain"), a = i.getData("application/imports"), m = a ? JSON.parse(a) : {};
      p.emit("drop-outline", {
        templateProperties: {
          template: l,
          imports: m
        },
        dropLocation: r,
        fiberNode: e
      });
    }
  }
  renderToggleColumn(e, t) {
    let i = "";
    return g(t) ? i = "♦ " : C(t) && (i = "</> "), `${i}${I(t)}`;
  }
  isLeafItem(e) {
    return this.getFilteredChildren(e).length === 0;
  }
  gridKeyDown(e) {
    e.altKey || e.metaKey || e.ctrlKey || e.shiftKey || (e.code === "Space" ? (e.preventDefault(), e.stopPropagation()) : (e.key === "Backspace" || e.key === "Delete") && (p.emit("delete-selected", {}), e.preventDefault(), e.stopPropagation()));
  }
  gridItemMouseMove(e) {
    let t;
    const i = f(e);
    i && d(i) && (t = h(i)), t ? n.setHighlighted({ element: t }) : n.setHighlighted(void 0);
  }
  gridItemClick(e) {
    const t = f(e);
    if (!t || !d(t))
      return;
    !e.metaKey && !e.ctrlKey && n.clearSelection();
    const i = h(t);
    i ? n.isSelected(i) ? n.deselect(i) : n.select(i) : x("Unable to find element for selection", t), y("use-outline");
  }
  updated(e) {
    super.updated(e), this.initialExpandDone || this.expandAll();
  }
  expandAll() {
    this.reactApp && this.grid && (this.grid.expandedItems = [this.reactApp, ...E(this.reactApp)], this.initialExpandDone = !0);
  }
  componentTreeUpdated() {
    if (this.reactApp = D(), this.grid) {
      if (this.reactApp) {
        const e = this.grid.expandedItems.map((t) => P(t));
        e.length > 0 && !e.includes(this.reactApp) ? this.expandAll() : this.grid.expandedItems = e;
      }
      this.grid.clearCache();
    }
    this.requestUpdate();
  }
};
c([
  A("vaadin-grid")
], s.prototype, "grid", 2);
c([
  F()
], s.prototype, "draggedItem", 2);
s = c([
  k("copilot-outline-panel")
], s);
const K = {
  header: "Outline",
  expanded: !0,
  draggable: !0,
  panelOrder: 0,
  panel: "left",
  floating: !1,
  tag: "copilot-outline-panel",
  showOn: [$.HillaReact]
}, U = {
  init(e) {
    e.addPanel(K);
  }
};
window.Vaadin.copilotPlugins.push(U);
export {
  s as CopilotOutlinePanel
};
