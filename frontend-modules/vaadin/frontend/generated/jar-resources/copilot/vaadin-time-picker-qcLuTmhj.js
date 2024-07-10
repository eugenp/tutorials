import { inputFieldProperties as o, labelProperties as p, helperTextProperties as s, errorMessageProperties as l } from "./vaadin-text-field-ku8iHk7Q.js";
import { Q as r, O as e, N as a } from "./copilot-sWvu6xks.js";
const c = {
  tagName: "vaadin-time-picker",
  displayName: "Time Picker",
  elements: [
    {
      selector: "vaadin-time-picker::part(input-field)",
      displayName: "Input field",
      properties: o
    },
    {
      selector: "vaadin-time-picker::part(toggle-button)",
      displayName: "Toggle button",
      properties: [r.iconColor, r.iconSize]
    },
    {
      selector: "vaadin-time-picker::part(label)",
      displayName: "Label",
      properties: p
    },
    {
      selector: "vaadin-time-picker::part(helper-text)",
      displayName: "Helper text",
      properties: s
    },
    {
      selector: "vaadin-time-picker::part(error-message)",
      displayName: "Error message",
      properties: l
    },
    {
      selector: "vaadin-time-picker-overlay::part(overlay)",
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
      selector: "vaadin-time-picker-overlay vaadin-time-picker-item",
      displayName: "Overlay items",
      properties: [a.textColor, a.fontSize, a.fontWeight]
    },
    {
      selector: "vaadin-time-picker-overlay vaadin-time-picker-item::part(checkmark)::before",
      displayName: "Overlay item checkmark",
      properties: [r.iconColor, r.iconSize]
    }
  ],
  async setupElement(i) {
    i.overlayClass = i.getAttribute("class"), i.value = "00:00", await new Promise((t) => {
      setTimeout(t, 10);
    });
  }
};
export {
  c as default
};
