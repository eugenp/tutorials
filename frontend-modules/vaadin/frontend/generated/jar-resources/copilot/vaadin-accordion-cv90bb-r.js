import { N as o, O as a, Q as e } from "./copilot-sWvu6xks.js";
const r = {
  tagName: "vaadin-accordion",
  displayName: "Accordion",
  elements: [
    {
      selector: "vaadin-accordion > vaadin-accordion-panel > vaadin-accordion-heading",
      displayName: "Heading",
      properties: [o.textColor, o.fontSize, a.padding]
    },
    {
      selector: "vaadin-accordion > vaadin-accordion-panel > vaadin-accordion-heading::part(toggle)",
      displayName: "Toggle",
      properties: [e.iconColor, e.iconSize]
    },
    {
      selector: "vaadin-accordion > vaadin-accordion-panel",
      displayName: "Panel",
      properties: [
        a.backgroundColor,
        a.borderColor,
        a.borderWidth,
        a.borderRadius
      ]
    }
  ]
};
export {
  r as default
};
