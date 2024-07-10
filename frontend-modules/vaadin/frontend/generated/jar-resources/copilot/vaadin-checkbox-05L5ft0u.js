import { H as a, R as r, O as e, Q as t, N as o } from "./copilot-sWvu6xks.js";
const c = {
  selector: "vaadin-checkbox",
  displayName: "Checkbox",
  properties: [
    {
      propertyName: "--vaadin-checkbox-size",
      displayName: "Checkbox size",
      defaultValue: "var(--lumo-font-size-l)",
      editorType: a.range,
      presets: r.lumoFontSize,
      icon: "square"
    }
  ]
}, s = {
  selector: "vaadin-checkbox::part(checkbox)",
  displayName: "Checkbox box",
  properties: [
    e.backgroundColor,
    e.borderColor,
    e.borderWidth,
    e.borderRadius
  ]
}, i = {
  selector: "vaadin-checkbox[checked]::part(checkbox)",
  stateAttribute: "checked",
  displayName: "Checkbox box (when checked)",
  properties: [
    e.backgroundColor,
    e.borderColor,
    e.borderWidth,
    e.borderRadius
  ]
}, l = {
  selector: "vaadin-checkbox::part(checkbox)::after",
  displayName: "Checkmark",
  properties: [t.iconColor]
}, d = {
  selector: "vaadin-checkbox label",
  displayName: "Label",
  properties: [o.textColor, o.fontSize, o.fontWeight, o.fontStyle]
}, n = {
  tagName: "vaadin-checkbox",
  displayName: "Checkbox",
  elements: [c, s, i, l, d]
};
export {
  s as checkboxElement,
  i as checkedCheckboxElement,
  l as checkmarkElement,
  n as default,
  c as hostElement,
  d as labelElement
};
