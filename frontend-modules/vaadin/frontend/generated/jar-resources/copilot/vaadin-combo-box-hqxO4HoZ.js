import { inputFieldProperties as t, labelProperties as i, helperTextProperties as s, errorMessageProperties as p } from "./vaadin-text-field-ku8iHk7Q.js";
import { Q as o, O as e, N as r } from "./copilot-sWvu6xks.js";
const b = {
  tagName: "vaadin-combo-box",
  displayName: "Combo Box",
  elements: [
    {
      selector: "vaadin-combo-box::part(input-field)",
      displayName: "Input field",
      properties: t
    },
    {
      selector: "vaadin-combo-box::part(toggle-button)",
      displayName: "Toggle button",
      properties: [o.iconColor, o.iconSize]
    },
    {
      selector: "vaadin-combo-box::part(label)",
      displayName: "Label",
      properties: i
    },
    {
      selector: "vaadin-combo-box::part(helper-text)",
      displayName: "Helper text",
      properties: s
    },
    {
      selector: "vaadin-combo-box::part(error-message)",
      displayName: "Error message",
      properties: p
    },
    {
      selector: "vaadin-combo-box-overlay::part(overlay)",
      displayName: "Overlay",
      properties: [
        e.backgroundColor,
        e.borderColor,
        e.borderWidth,
        e.borderRadius,
        e.padding
      ]
    },
    {
      selector: "vaadin-combo-box-overlay vaadin-combo-box-item",
      displayName: "Overlay items",
      properties: [r.textColor, r.fontSize, r.fontWeight]
    },
    {
      selector: "vaadin-combo-box-overlay vaadin-combo-box-item::part(checkmark)::before",
      displayName: "Overlay item checkmark",
      properties: [o.iconColor, o.iconSize]
    }
  ],
  async setupElement(a) {
    a.overlayClass = a.getAttribute("class");
  }
};
export {
  b as default
};
