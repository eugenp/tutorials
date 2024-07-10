import { O as o, S as a, T as r, U as i } from "./copilot-sWvu6xks.js";
import { inputFieldProperties as p, labelProperties as l, helperTextProperties as t, errorMessageProperties as n } from "./vaadin-text-field-ku8iHk7Q.js";
import { standardButtonProperties as e } from "./vaadin-button-COw2KVfj.js";
const m = {
  tagName: "vaadin-login-overlay",
  displayName: "Login Overlay",
  elements: [
    {
      selector: "vaadin-login-overlay-wrapper::part(backdrop)",
      displayName: "Overlay backdrop / modality curtain",
      properties: [o.backgroundColor]
    },
    {
      selector: "vaadin-login-overlay-wrapper::part(card)",
      displayName: "Overlay card",
      properties: a
    },
    {
      selector: "vaadin-login-overlay-wrapper::part(brand)",
      displayName: "Card header",
      properties: a
    },
    {
      selector: "vaadin-login-overlay-wrapper::part(title)",
      displayName: "Title",
      properties: r
    },
    {
      selector: "vaadin-login-overlay-wrapper::part(description)",
      displayName: "Description",
      properties: r
    },
    {
      selector: "vaadin-login-overlay-wrapper vaadin-login-form vaadin-login-form-wrapper",
      displayName: "Login form",
      properties: a
    },
    {
      selector: "vaadin-login-overlay-wrapper vaadin-login-form vaadin-login-form-wrapper::part(form-title)",
      displayName: "Form title",
      properties: r
    },
    {
      selector: "vaadin-login-overlay-wrapper vaadin-login-form vaadin-login-form-wrapper::part(error-message)",
      displayName: "Error message section",
      properties: a
    },
    {
      selector: "vaadin-login-overlay-wrapper vaadin-login-form vaadin-login-form-wrapper::part(error-message-title)",
      displayName: "Error message heading",
      properties: r
    },
    {
      selector: "vaadin-login-overlay-wrapper vaadin-login-form vaadin-login-form-wrapper::part(error-message-description)",
      displayName: "Error message description",
      properties: r
    },
    {
      selector: "vaadin-login-overlay-wrapper vaadin-login-form vaadin-login-form-wrapper [required]::part(input-field)",
      displayName: "Input field",
      properties: p
    },
    {
      selector: "vaadin-login-overlay-wrapper vaadin-login-form vaadin-login-form-wrapper [required]::part(label)",
      displayName: "Input field label",
      properties: l
    },
    {
      selector: "vaadin-login-overlay-wrapper vaadin-login-form vaadin-login-form-wrapper [required]::part(helper-text)",
      displayName: "Input field helper text",
      properties: t
    },
    {
      selector: "vaadin-login-overlay-wrapper vaadin-login-form vaadin-login-form-wrapper [required]::part(error-message)",
      displayName: "Input field error message",
      properties: n
    },
    {
      selector: "vaadin-login-overlay-wrapper vaadin-login-form vaadin-password-field::part(reveal-button)",
      displayName: "Password field reveal button",
      properties: i
    },
    {
      selector: 'vaadin-login-overlay-wrapper vaadin-login-form vaadin-button[theme~="submit"]',
      displayName: "Log In Button",
      properties: e
    },
    {
      selector: 'vaadin-login-overlay-wrapper vaadin-login-form vaadin-button[theme~="submit"]::part(label)',
      displayName: "Log In Button Label",
      properties: r
    },
    {
      selector: 'vaadin-login-overlay-wrapper vaadin-login-form [slot="forgot-password"]',
      displayName: "Forgot password button",
      properties: e
    },
    {
      selector: 'vaadin-login-overlay-wrapper vaadin-login-form [slot="forgot-password"]::part(label)',
      displayName: "Forgot password button label",
      properties: r
    }
  ]
};
export {
  m as default
};
