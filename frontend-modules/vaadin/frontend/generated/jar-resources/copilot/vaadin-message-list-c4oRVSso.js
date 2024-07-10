import { O as e, N as a } from "./copilot-sWvu6xks.js";
const t = {
  tagName: "vaadin-message-list",
  displayName: "Message List",
  elements: [
    {
      selector: "vaadin-message-list",
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
      selector: "vaadin-message-list::part(list)",
      displayName: "Internal list layout",
      properties: [
        e.backgroundColor,
        e.borderColor,
        e.borderWidth,
        e.borderRadius,
        e.padding
      ]
    },
    {
      selector: "vaadin-message-list vaadin-message",
      displayName: "Message item",
      properties: [
        e.backgroundColor,
        e.borderColor,
        e.borderWidth,
        e.borderRadius,
        e.padding
      ]
    },
    {
      selector: "vaadin-message-list vaadin-message::part(name)",
      displayName: "Name",
      properties: [
        a.textColor,
        a.fontSize,
        a.fontWeight,
        a.fontStyle
      ]
    },
    {
      selector: "vaadin-message-list vaadin-message::part(time)",
      displayName: "Time",
      properties: [
        a.textColor,
        a.fontSize,
        a.fontWeight,
        a.fontStyle
      ]
    },
    {
      selector: "vaadin-message-list vaadin-message::part(message)",
      displayName: "Text",
      properties: [
        a.textColor,
        a.fontSize,
        a.fontWeight,
        a.fontStyle
      ]
    },
    {
      selector: "vaadin-message-list vaadin-message > vaadin-avatar",
      displayName: "Avatar",
      properties: [
        e.backgroundColor,
        e.borderColor,
        e.borderWidth,
        e.borderRadius,
        e.padding
      ]
    },
    {
      selector: "vaadin-message-list vaadin-message > vaadin-avatar::part(abbr)",
      displayName: "Avatar abbreviation",
      properties: [
        a.textColor,
        a.fontSize,
        a.fontWeight,
        a.fontStyle
      ]
    }
  ]
};
export {
  t as default
};
