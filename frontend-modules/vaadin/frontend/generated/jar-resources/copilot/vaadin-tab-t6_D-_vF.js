import { x as t, N as e, O as a } from "./copilot-sWvu6xks.js";
const i = {
  tagName: "vaadin-tab",
  displayName: "Tab",
  description: t`You are styling selected tab only, if you wish to style all tabs please pick
    <code>vaadin-tabs</code> instead.`,
  notAccessibleDescription: t`If you wish to style all tabs please pick <code>vaadin-tabs</code> instead.`,
  elements: [
    {
      selector: "vaadin-tab",
      displayName: "Tab item",
      properties: [
        e.textColor,
        e.fontSize,
        e.fontWeight,
        a.backgroundColor,
        a.padding
      ]
    },
    {
      selector: "vaadin-tab[selected]",
      displayName: "Tab item (selected)",
      properties: [
        e.textColor,
        e.fontSize,
        e.fontWeight,
        a.backgroundColor,
        a.padding
      ]
    },
    {
      selector: "vaadin-tab::before",
      displayName: "Selection indicator",
      properties: [a.backgroundColor]
    }
  ]
};
export {
  i as default
};
