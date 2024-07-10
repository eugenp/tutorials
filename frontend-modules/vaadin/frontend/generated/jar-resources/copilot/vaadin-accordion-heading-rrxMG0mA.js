import { x as e, N as o, O as i, Q as a } from "./copilot-sWvu6xks.js";
const d = {
  tagName: "vaadin-accordion-heading",
  displayName: "Accordion Heading",
  description: e`You are trying to style selected heading only, if you wish to style all panel headings of given
    accordion please pick <code>vaadin-accordion</code> instead.`,
  notAccessibleDescription: e`If you wish to style all panel headings of current accordion please pick
    <code>vaadin-accordion</code> instead.`,
  elements: [
    {
      selector: "vaadin-accordion-heading",
      displayName: "Heading",
      properties: [o.textColor, o.fontSize, i.padding]
    },
    {
      selector: "vaadin-accordion-heading::part(toggle)",
      displayName: "Toggle",
      properties: [a.iconColor, a.iconSize]
    }
  ]
};
export {
  d as default
};
