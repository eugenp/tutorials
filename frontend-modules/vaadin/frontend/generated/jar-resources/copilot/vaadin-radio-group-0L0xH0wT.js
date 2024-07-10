import { O as e, H as i, R as d, Q as p, N as r } from "./copilot-sWvu6xks.js";
import { labelProperties as s, helperTextProperties as l, errorMessageProperties as n } from "./vaadin-text-field-ku8iHk7Q.js";
const c = {
  tagName: "vaadin-radio-group",
  displayName: "Radio Button Group",
  elements: [
    {
      selector: "vaadin-radio-group",
      displayName: "Group",
      properties: [
        e.backgroundColor,
        e.borderColor,
        e.borderWidth,
        e.borderRadius,
        e.padding
      ]
    },
    {
      selector: "vaadin-radio-group::part(label)",
      displayName: "Label",
      properties: s
    },
    {
      selector: "vaadin-radio-group::part(helper-text)",
      displayName: "Helper text",
      properties: l
    },
    {
      selector: "vaadin-radio-group::part(error-message)",
      displayName: "Error message",
      properties: n
    },
    {
      selector: "vaadin-radio-group vaadin-radio-button",
      displayName: "Radio buttons",
      properties: [
        {
          propertyName: "--vaadin-radio-button-size",
          displayName: "Radio button size",
          defaultValue: "var(--lumo-font-size-l)",
          editorType: i.range,
          presets: d.lumoFontSize,
          icon: "square"
        }
      ]
    },
    {
      selector: "vaadin-radio-group vaadin-radio-button::part(radio)",
      displayName: "Radio part",
      properties: [e.backgroundColor, e.borderColor, e.borderWidth]
    },
    {
      selector: "vaadin-radio-group vaadin-radio-button[checked]::part(radio)",
      stateAttribute: "checked",
      // Checked state attribute needs to be applied on radio button rather than group
      stateElementSelector: "vaadin-radio-group vaadin-radio-button",
      displayName: "Radio part (when checked)",
      properties: [e.backgroundColor, e.borderColor, e.borderWidth]
    },
    {
      selector: "vaadin-radio-group vaadin-radio-button::part(radio)::after",
      displayName: "Selection indicator",
      properties: [
        {
          ...p.iconColor,
          // Radio button dot uses border-color instead of background-color
          propertyName: "border-color"
        }
      ]
    },
    {
      selector: "vaadin-radio-group vaadin-radio-button label",
      displayName: "Label",
      properties: [
        r.textColor,
        r.fontSize,
        r.fontWeight,
        r.fontStyle
      ]
    }
  ],
  setupElement(t) {
    const o = document.createElement("vaadin-radio-button"), a = document.createElement("label");
    a.textContent = "Some label", a.setAttribute("slot", "label"), o.append(a), t.append(o);
  }
};
export {
  c as default
};
