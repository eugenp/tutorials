import { inputFieldProperties as i, labelProperties as l, helperTextProperties as s, errorMessageProperties as p } from "./vaadin-text-field-ku8iHk7Q.js";
import { Q as t, O as e, N as r } from "./copilot-sWvu6xks.js";
const d = {
  tagName: "vaadin-multi-select-combo-box",
  displayName: "Multi-Select Combo Box",
  elements: [
    {
      selector: "vaadin-multi-select-combo-box::part(input-field)",
      displayName: "Input field",
      properties: i
    },
    {
      selector: "vaadin-multi-select-combo-box::part(toggle-button)",
      displayName: "Toggle button",
      properties: [t.iconColor, t.iconSize]
    },
    {
      selector: "vaadin-multi-select-combo-box::part(label)",
      displayName: "Label",
      properties: l
    },
    {
      selector: "vaadin-multi-select-combo-box::part(helper-text)",
      displayName: "Helper text",
      properties: s
    },
    {
      selector: "vaadin-multi-select-combo-box::part(error-message)",
      displayName: "Error message",
      properties: p
    },
    {
      selector: "vaadin-multi-select-combo-box-overlay::part(overlay)",
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
      selector: "vaadin-multi-select-combo-box-overlay vaadin-multi-select-combo-box-item",
      displayName: "Overlay items",
      properties: [r.textColor, r.fontSize, r.fontWeight]
    },
    {
      selector: "vaadin-multi-select-combo-box-overlay vaadin-multi-select-combo-box-item::part(checkmark)::before",
      displayName: "Overlay item checkmark",
      properties: [t.iconColor, t.iconSize]
    },
    {
      selector: "vaadin-multi-select-combo-box vaadin-multi-select-combo-box-chip",
      displayName: "Chip",
      properties: [
        e.backgroundColor,
        e.borderColor,
        e.borderWidth,
        e.borderRadius,
        e.padding
      ]
    },
    {
      selector: "vaadin-multi-select-combo-box vaadin-multi-select-combo-box-chip",
      displayName: "Chip label",
      properties: [r.textColor, r.fontSize, r.fontWeight]
    },
    {
      selector: "vaadin-multi-select-combo-box vaadin-multi-select-combo-box-chip::part(remove-button)",
      displayName: "Chip remove button",
      properties: [
        e.backgroundColor,
        e.borderColor,
        e.borderWidth,
        e.borderRadius,
        e.padding
      ]
    },
    {
      selector: "vaadin-multi-select-combo-box vaadin-multi-select-combo-box-chip::part(remove-button)::before",
      displayName: "Chip remove button icon",
      properties: [t.iconColor, t.iconSize]
    }
  ],
  async setupElement(o) {
    o.overlayClass = o.getAttribute("class"), o.items = [{ label: "Item", value: "value" }], o.value = "value", o.opened = !0, await new Promise((a) => {
      setTimeout(a, 10);
    });
  },
  async cleanupElement(o) {
    o.opened = !1;
  }
};
export {
  d as default
};
