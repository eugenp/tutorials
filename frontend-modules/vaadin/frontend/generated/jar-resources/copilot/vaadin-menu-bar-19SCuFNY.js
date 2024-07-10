import { O as e, H as i, R as n, W as s, N as a } from "./copilot-sWvu6xks.js";
const l = {
  tagName: "vaadin-menu-bar",
  displayName: "Menu Bar",
  elements: [
    {
      selector: "vaadin-menu-bar vaadin-menu-bar-button",
      displayName: "Buttons",
      properties: [
        e.backgroundColor,
        e.borderColor,
        e.borderWidth,
        e.borderRadius,
        {
          propertyName: "--lumo-button-size",
          displayName: "Size",
          editorType: i.range,
          presets: n.lumoSize,
          icon: "square"
        },
        s.paddingInline
      ]
    },
    {
      selector: "vaadin-menu-bar vaadin-menu-bar-button vaadin-menu-bar-item",
      displayName: "Button labels",
      properties: [a.textColor, a.fontSize, a.fontWeight]
    },
    {
      selector: "vaadin-menu-bar-overlay::part(overlay)",
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
      selector: "vaadin-menu-bar-overlay vaadin-menu-bar-item",
      displayName: "Menu Items",
      properties: [a.textColor, a.fontSize, a.fontWeight]
    }
  ],
  async setupElement(t) {
    t.overlayClass = t.getAttribute("class");
    const r = document.createElement("vaadin-menu-bar-item");
    t.items = [
      {
        component: r,
        children: [
          {
            text: "Sub item"
          }
        ]
      }
    ], await new Promise((o) => {
      setTimeout(o, 10);
    });
  },
  async cleanupElement(t) {
    t._close();
  }
};
export {
  l as default
};
