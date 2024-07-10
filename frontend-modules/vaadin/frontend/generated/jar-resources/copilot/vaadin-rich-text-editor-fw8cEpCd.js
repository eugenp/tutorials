import { O as r, N as o } from "./copilot-sWvu6xks.js";
const t = {
  tagName: "vaadin-rich-text-editor",
  displayName: "Rich Text Editor",
  elements: [
    {
      selector: "vaadin-rich-text-editor",
      displayName: "Editor",
      properties: [
        r.backgroundColor,
        r.borderRadius,
        r.borderWidth,
        r.borderColor,
        r.padding
      ]
    },
    {
      selector: "vaadin-rich-text-editor::part(toolbar)",
      displayName: "Toolbar",
      properties: [
        r.backgroundColor,
        r.borderRadius,
        r.borderWidth,
        r.borderColor,
        r.padding
      ]
    },
    {
      selector: "vaadin-rich-text-editor::part(toolbar-button)",
      displayName: "Toolbar Buttons",
      properties: [
        { ...o.textColor, displayName: "Color" },
        { ...o.textColor, displayName: "Highlight Color", propertyName: "--lumo-primary-color" }
      ]
    },
    {
      selector: "vaadin-rich-text-editor::part(content)",
      displayName: "Content",
      properties: [
        r.backgroundColor,
        r.borderRadius,
        r.borderWidth,
        r.borderColor,
        r.padding
      ]
    }
  ]
};
export {
  t as default
};
