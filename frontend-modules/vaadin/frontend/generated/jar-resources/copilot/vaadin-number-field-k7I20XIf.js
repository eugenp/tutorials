import { inputFieldProperties as t, labelProperties as a, helperTextProperties as i, errorMessageProperties as o } from "./vaadin-text-field-ku8iHk7Q.js";
import { Q as e } from "./copilot-sWvu6xks.js";
import { standardButtonProperties as p } from "./vaadin-button-COw2KVfj.js";
const d = {
  tagName: "vaadin-number-field",
  displayName: "Number Field",
  elements: [
    {
      selector: "vaadin-number-field::part(input-field)",
      displayName: "Input field",
      properties: t
    },
    {
      selector: "vaadin-number-field::part(label)",
      displayName: "Label",
      properties: a
    },
    {
      selector: "vaadin-number-field::part(helper-text)",
      displayName: "Helper text",
      properties: i
    },
    {
      selector: "vaadin-number-field::part(error-message)",
      displayName: "Error message",
      properties: o
    },
    {
      selector: "vaadin-number-field::part(clear-button)",
      displayName: "Clear button",
      properties: p
    },
    {
      selector: "vaadin-number-field::part(increase-button)",
      displayName: "Increase button",
      properties: [e.iconColor, e.iconSize]
    },
    {
      selector: "vaadin-number-field::part(decrease-button)",
      displayName: "Decrease button",
      properties: [e.iconColor, e.iconSize]
    }
  ],
  setupElement(r) {
    r.stepButtonsVisible = !0;
  }
};
export {
  d as default
};
