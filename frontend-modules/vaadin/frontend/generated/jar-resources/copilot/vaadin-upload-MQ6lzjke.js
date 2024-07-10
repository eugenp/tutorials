import { standardButtonProperties as o } from "./vaadin-button-COw2KVfj.js";
import { N as e, O as a } from "./copilot-sWvu6xks.js";
const d = {
  tagName: "vaadin-upload",
  displayName: "Upload",
  elements: [
    {
      selector: "vaadin-upload > vaadin-button",
      displayName: "Upload button",
      properties: o
    },
    {
      selector: "vaadin-upload > vaadin-button::part(label)",
      displayName: "Upload button text",
      properties: [e.textColor, e.fontSize, e.fontWeight]
    },
    {
      selector: "vaadin-upload::part(drop-label)",
      displayName: "Drop label",
      properties: [e.textColor, e.fontSize, e.fontWeight]
    },
    {
      selector: "vaadin-upload vaadin-upload-file-list::part(list)",
      displayName: "File list",
      properties: [
        a.backgroundColor,
        a.borderColor,
        a.borderWidth,
        a.borderRadius,
        a.padding
      ]
    },
    {
      selector: "vaadin-upload vaadin-upload-file",
      displayName: "File element",
      properties: [
        e.textColor,
        e.fontSize,
        e.fontWeight,
        a.backgroundColor,
        a.borderColor,
        a.borderWidth,
        a.borderRadius,
        a.padding
      ]
    },
    {
      selector: "vaadin-upload vaadin-upload-file > vaadin-progress-bar::part(value)",
      displayName: "Progress bar",
      properties: [a.backgroundColor]
    }
  ]
};
export {
  d as default
};
