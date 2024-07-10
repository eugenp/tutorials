import { x as t, Q as i, O as e, N as o } from "./copilot-sWvu6xks.js";
const r = {
  tagName: "vaadin-side-nav-item",
  displayName: "Side Navigation Item",
  description: t`You are styling selected item only, if you wish to style all items of given SideNav component please
    pick <code>vaadin-side-nav</code> instead.`,
  notAccessibleDescription: t`If you wish to style all items of current SideNav component please pick
    <code>vaadin-side-nav</code> instead.`,
  elements: [
    {
      selector: 'vaadin-side-nav-item > [slot="prefix"]',
      displayName: "Nav item icon",
      properties: [i.iconSize, i.iconColor]
    },
    {
      selector: 'vaadin-side-nav-item[active] > [slot="prefix"]',
      displayName: "Nav item icon (active)",
      properties: [i.iconSize, i.iconColor]
    },
    {
      selector: "vaadin-side-nav-item::part(item)",
      displayName: "Nav item",
      properties: [
        e.backgroundColor,
        e.borderColor,
        e.borderWidth,
        e.borderRadius,
        e.padding,
        o.textColor,
        o.fontSize,
        o.fontWeight,
        o.fontStyle
      ]
    },
    {
      selector: "vaadin-side-nav-item[active]::part(item)",
      displayName: "Nav item (active)",
      properties: [
        o.textColor,
        o.fontSize,
        o.fontWeight,
        o.fontStyle,
        e.backgroundColor,
        e.borderColor,
        e.borderWidth,
        e.borderRadius,
        e.padding
      ]
    },
    {
      selector: "vaadin-side-nav-item::part(toggle-button)::before",
      displayName: "Expand/collapse icon",
      properties: [i.iconSize, i.iconColor]
    },
    {
      selector: "vaadin-side-nav-item[active]::part(toggle-button)::before",
      displayName: "Expand/collapse icon (active)",
      properties: [i.iconSize, i.iconColor]
    }
  ]
};
export {
  r as default
};
