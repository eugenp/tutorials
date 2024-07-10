import { O as a } from "./copilot-sWvu6xks.js";
const e = {
  tagName: "vaadin-split-layout",
  displayName: "Split Layout",
  elements: [
    {
      selector: "vaadin-split-layout",
      displayName: "Layout",
      properties: [
        a.backgroundColor,
        a.borderColor,
        a.borderWidth,
        a.borderRadius
      ]
    },
    {
      selector: "vaadin-split-layout::part(splitter)",
      displayName: "Splitter bar",
      properties: [a.backgroundColor]
    },
    {
      selector: "vaadin-split-layout::part(handle)::after",
      displayName: "Splitter handle",
      properties: [a.backgroundColor]
    }
  ]
};
export {
  e as default
};
