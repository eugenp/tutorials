import { O as a, N as e, Q as t } from "./copilot-sWvu6xks.js";
const i = {
  tagName: "vaadin-tabs",
  displayName: "Tabs",
  elements: [
    {
      selector: "vaadin-tabs",
      displayName: "Tabs",
      properties: [a.padding]
    },
    {
      selector: "vaadin-tabs vaadin-tab",
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
      selector: "vaadin-tabs > vaadin-tab[selected]",
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
      selector: "vaadin-tabs > vaadin-tab::before",
      displayName: "Selection indicator",
      properties: [a.backgroundColor]
    },
    {
      selector: "vaadin-tabs::part(back-button)",
      displayName: "Back button",
      properties: [t.iconColor, t.iconSize]
    },
    {
      selector: "vaadin-tabs::part(forward-button)",
      displayName: "Forward button",
      properties: [t.iconColor, t.iconSize]
    }
  ]
};
export {
  i as default
};
