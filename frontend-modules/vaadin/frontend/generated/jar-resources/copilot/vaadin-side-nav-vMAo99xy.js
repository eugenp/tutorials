import { O as e, N as i, Q as a } from "./copilot-sWvu6xks.js";
const t = {
  tagName: "vaadin-side-nav",
  displayName: "Side Navigation",
  elements: [
    {
      selector: "vaadin-side-nav",
      displayName: "Root element",
      properties: [
        e.backgroundColor,
        e.borderColor,
        e.borderWidth,
        e.borderRadius,
        e.padding
      ]
    },
    {
      selector: 'vaadin-side-nav > [slot="label"]',
      displayName: "Label",
      properties: [
        i.textColor,
        i.fontSize,
        i.fontWeight,
        i.fontStyle
      ]
    },
    {
      selector: 'vaadin-side-nav vaadin-side-nav-item > [slot="prefix"]',
      displayName: "Nav item icon",
      properties: [a.iconSize, a.iconColor]
    },
    {
      selector: 'vaadin-side-nav vaadin-side-nav-item[active] > [slot="prefix"]',
      displayName: "Nav item icon (active)",
      properties: [a.iconSize, a.iconColor]
    },
    {
      selector: "vaadin-side-nav vaadin-side-nav-item::part(item)",
      displayName: "Nav item",
      properties: [
        e.backgroundColor,
        e.borderColor,
        e.borderWidth,
        e.borderRadius,
        e.padding,
        i.textColor,
        i.fontSize,
        i.fontWeight,
        i.fontStyle
      ]
    },
    {
      selector: "vaadin-side-nav vaadin-side-nav-item[active]::part(item)",
      displayName: "Nav item (active)",
      properties: [
        i.textColor,
        i.fontSize,
        i.fontWeight,
        i.fontStyle,
        e.backgroundColor,
        e.borderColor,
        e.borderWidth,
        e.borderRadius,
        e.padding
      ]
    },
    {
      selector: "vaadin-side-nav vaadin-side-nav-item::part(toggle-button)::before",
      displayName: "Expand/collapse icon",
      properties: [a.iconSize, a.iconColor]
    },
    {
      selector: "vaadin-side-nav vaadin-side-nav-item[active]::part(toggle-button)::before",
      displayName: "Expand/collapse icon (active)",
      properties: [a.iconSize, a.iconColor]
    }
  ]
};
export {
  t as default
};
