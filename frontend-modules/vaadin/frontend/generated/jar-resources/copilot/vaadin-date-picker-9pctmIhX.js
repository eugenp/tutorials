import { inputFieldProperties as t, labelProperties as o, helperTextProperties as i, errorMessageProperties as p } from "./vaadin-text-field-ku8iHk7Q.js";
import { Q as a, O as r, N as e } from "./copilot-sWvu6xks.js";
const n = {
  tagName: "vaadin-date-picker",
  displayName: "Date Picker",
  elements: [
    {
      selector: "vaadin-date-picker::part(input-field)",
      displayName: "Input field",
      properties: t
    },
    {
      selector: "vaadin-date-picker::part(toggle-button)",
      displayName: "Toggle button",
      properties: [a.iconColor, a.iconSize]
    },
    {
      selector: "vaadin-date-picker::part(label)",
      displayName: "Label",
      properties: o
    },
    {
      selector: "vaadin-date-picker::part(helper-text)",
      displayName: "Helper text",
      properties: i
    },
    {
      selector: "vaadin-date-picker::part(error-message)",
      displayName: "Error message",
      properties: p
    },
    {
      selector: "vaadin-date-picker-overlay::part(overlay)",
      displayName: "Overlay content",
      properties: [
        r.backgroundColor,
        r.borderColor,
        r.borderWidth,
        r.borderRadius,
        r.padding
      ]
    },
    {
      selector: "vaadin-date-picker-overlay vaadin-month-calendar::part(month-header)",
      displayName: "Month header",
      properties: [
        e.textColor,
        e.fontSize,
        e.fontWeight,
        e.fontStyle
      ]
    },
    {
      selector: "vaadin-date-picker-overlay vaadin-month-calendar::part(weekday)",
      displayName: "Weekday",
      properties: [
        e.textColor,
        e.fontSize,
        e.fontWeight,
        e.fontStyle
      ]
    },
    {
      selector: "vaadin-date-picker-overlay vaadin-month-calendar::part(date)",
      displayName: "Day",
      properties: [
        e.textColor,
        e.fontSize,
        e.fontWeight,
        e.fontStyle,
        r.backgroundColor,
        r.borderColor,
        r.borderWidth,
        r.borderRadius
      ]
    },
    {
      selector: "vaadin-date-picker-overlay vaadin-date-picker-year::part(year-number)",
      displayName: "Year number",
      properties: [
        e.textColor,
        e.fontSize,
        e.fontWeight,
        e.fontStyle
      ]
    }
  ]
};
export {
  n as default
};
