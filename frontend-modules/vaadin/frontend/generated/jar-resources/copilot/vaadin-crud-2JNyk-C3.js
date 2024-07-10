import { O as r, Q as o } from "./copilot-sWvu6xks.js";
const d = {
  tagName: "vaadin-crud",
  displayName: "CRUD",
  elements: [
    {
      selector: "vaadin-crud",
      displayName: "Root element",
      properties: [r.borderColor, r.borderWidth]
    },
    {
      selector: "vaadin-crud::part(toolbar)",
      displayName: "Toolbar below grid",
      properties: [
        r.backgroundColor,
        r.borderColor,
        r.borderWidth,
        r.padding
      ]
    },
    {
      selector: "vaadin-crud::part(editor)",
      displayName: "Editor panel",
      properties: [r.backgroundColor]
    },
    {
      selector: "vaadin-crud vaadin-crud-edit",
      displayName: "Edit button in grid",
      properties: [r.backgroundColor, r.borderColor, r.borderWidth]
    },
    {
      selector: "vaadin-crud vaadin-crud-edit::part(icon)::before",
      displayName: "Edit button in grid icon",
      properties: [o.iconColor, o.iconSize]
    }
  ]
};
export {
  d as default
};
