import { S as a, T as e } from "./copilot-sWvu6xks.js";
import { standardButtonProperties as o } from "./vaadin-button-COw2KVfj.js";
const i = {
  tagName: "vaadin-confirm-dialog",
  displayName: "Confirm Dialog",
  elements: [
    {
      selector: "vaadin-confirm-dialog-overlay::part(backdrop)",
      displayName: "Modality curtain (backdrop)",
      properties: a
    },
    {
      selector: "vaadin-confirm-dialog-overlay::part(overlay)",
      displayName: "Dialog surface",
      properties: a
    },
    {
      selector: "vaadin-confirm-dialog-overlay::part(header)",
      displayName: "Header",
      properties: [...a, ...e]
    },
    {
      selector: "vaadin-confirm-dialog-overlay::part(content)",
      displayName: "Content",
      properties: a
    },
    {
      selector: "vaadin-confirm-dialog-overlay::part(message)",
      displayName: "Message",
      properties: e
    },
    {
      selector: "vaadin-confirm-dialog-overlay::part(footer)",
      displayName: "Footer",
      properties: a
    },
    {
      selector: 'vaadin-confirm-dialog-overlay > [slot="confirm-button"]',
      displayName: "Confirm button",
      properties: o
    },
    {
      selector: 'vaadin-confirm-dialog-overlay > [slot="confirm-button"]::part(label)',
      displayName: "Confirm button label",
      properties: e
    },
    {
      selector: 'vaadin-confirm-dialog-overlay > [slot="reject-button"]',
      displayName: "Reject button",
      properties: o
    },
    {
      selector: 'vaadin-confirm-dialog-overlay > [slot="reject-button"]::part(label)',
      displayName: "Reject button label",
      properties: e
    },
    {
      selector: 'vaadin-confirm-dialog-overlay > [slot="cancel-button"]',
      displayName: "Cancel button",
      properties: o
    },
    {
      selector: 'vaadin-confirm-dialog-overlay > [slot="cancel-button"]::part(label)',
      displayName: "Cancel button label",
      properties: e
    }
  ]
};
export {
  i as default
};
