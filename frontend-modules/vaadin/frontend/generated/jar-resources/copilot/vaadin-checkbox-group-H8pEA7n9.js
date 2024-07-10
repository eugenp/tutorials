import { O as e } from "./copilot-sWvu6xks.js";
import { labelProperties as d, helperTextProperties as i, errorMessageProperties as m } from "./vaadin-text-field-ku8iHk7Q.js";
import { hostElement as a, checkboxElement as t, checkedCheckboxElement as c, checkmarkElement as l, labelElement as s } from "./vaadin-checkbox-05L5ft0u.js";
const k = {
  tagName: "vaadin-checkbox-group",
  displayName: "Checkbox Group",
  elements: [
    {
      selector: "vaadin-checkbox-group",
      displayName: "Root element",
      properties: [
        e.backgroundColor,
        e.borderColor,
        e.borderWidth,
        e.borderRadius,
        e.padding
      ]
    },
    {
      selector: "vaadin-checkbox-group::part(label)",
      displayName: "Label",
      properties: d
    },
    {
      selector: "vaadin-checkbox-group::part(helper-text)",
      displayName: "Helper text",
      properties: i
    },
    {
      selector: "vaadin-checkbox-group::part(error-message)",
      displayName: "Error message",
      properties: m
    },
    {
      ...a,
      selector: `vaadin-checkbox-group ${a.selector}`,
      displayName: "Checkboxes"
    },
    {
      ...t,
      selector: `vaadin-checkbox-group ${t.selector}`,
      displayName: "Checkmark boxes"
    },
    {
      ...c,
      selector: `vaadin-checkbox-group ${c.selector}`,
      displayName: "Checkmark boxes (when checked)",
      // Checked state attribute needs to be applied on checkbox rather than group
      stateElementSelector: "vaadin-checkbox-group vaadin-checkbox"
    },
    {
      ...l,
      selector: `vaadin-checkbox-group ${l.selector}`,
      displayName: "Checkmarks"
    },
    {
      ...s,
      selector: `vaadin-checkbox-group ${s.selector}`,
      displayName: "Checkbox labels"
    }
  ],
  setupElement(p) {
    const r = document.createElement("vaadin-checkbox"), o = document.createElement("label");
    o.textContent = "Some label", o.setAttribute("slot", "label"), r.append(o), p.append(r);
  }
};
export {
  k as default
};
