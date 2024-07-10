import { O as o, N as e, Q as a } from "./copilot-sWvu6xks.js";
import { checkboxElement as i, checkedCheckboxElement as t } from "./vaadin-checkbox-05L5ft0u.js";
const r = [
  e.textColor,
  e.fontSize,
  e.fontWeight,
  e.fontStyle,
  o.backgroundColor
], c = {
  tagName: "vaadin-grid",
  displayName: "Grid",
  elements: [
    {
      selector: "vaadin-grid",
      displayName: "Root element",
      properties: [o.borderColor, o.borderWidth]
    },
    {
      selector: "vaadin-grid::part(header-cell)",
      displayName: "Header row cell",
      properties: [
        e.textColor,
        // Hack to overcome slotted vaadin-grid-cell-content
        { ...e.fontSize, propertyName: "--lumo-font-size-s" },
        // TextProperties.fontWeight, -- cannot be styled in single block
        e.fontStyle,
        o.backgroundColor
      ]
    },
    {
      selector: "vaadin-grid::part(body-cell)",
      displayName: "Body cell",
      properties: r
    },
    {
      selector: "vaadin-grid::part(even-row-cell)",
      displayName: "Cell in even row",
      properties: r
    },
    {
      selector: "vaadin-grid::part(odd-row-cell)",
      displayName: "Cell in odd row",
      properties: r
    },
    {
      selector: "vaadin-grid::part(selected-row-cell)",
      displayName: "Cell in selected row",
      properties: r
    },
    {
      selector: "vaadin-grid vaadin-grid-cell-content > vaadin-checkbox::part(checkbox)",
      displayName: "Row selection checkbox",
      properties: i.properties
    },
    {
      selector: "vaadin-grid vaadin-grid-cell-content > vaadin-checkbox[checked]::part(checkbox)",
      displayName: "Row selection checkbox (when checked)",
      properties: t.properties
    },
    {
      selector: "vaadin-grid vaadin-grid-cell-content > vaadin-checkbox::part(checkbox)::after",
      displayName: "Row selection checkbox checkmark",
      properties: [a.iconColor]
    },
    {
      selector: "vaadin-grid vaadin-grid-tree-toggle::part(toggle)",
      displayName: "Expand icon (for tree grid)",
      properties: [a.iconColor]
    }
  ]
};
export {
  r as cellProperties,
  c as default
};
