import { S as e, T as r, U as o } from "./copilot-sWvu6xks.js";
import { inputFieldProperties as i, labelProperties as t, helperTextProperties as p, errorMessageProperties as s } from "./vaadin-text-field-ku8iHk7Q.js";
import { standardButtonProperties as a } from "./vaadin-button-COw2KVfj.js";
const m = {
  tagName: "vaadin-login-form",
  displayName: "Login",
  elements: [
    {
      selector: "vaadin-login-form",
      displayName: "Login form root component",
      properties: e
    },
    {
      selector: "vaadin-login-form vaadin-login-form-wrapper",
      displayName: "Login form",
      properties: e
    },
    {
      selector: "vaadin-login-form vaadin-login-form-wrapper::part(form-title)",
      displayName: "Form title",
      properties: r
    },
    {
      selector: "vaadin-login-form vaadin-login-form-wrapper::part(error-message)",
      displayName: "Error message section",
      properties: e
    },
    {
      selector: "vaadin-login-form vaadin-login-form-wrapper::part(error-message-title)",
      displayName: "Error message heading",
      properties: r
    },
    {
      selector: "vaadin-login-form vaadin-login-form-wrapper::part(error-message-description)",
      displayName: "Error message description",
      properties: r
    },
    {
      selector: "vaadin-login-form vaadin-login-form-wrapper [required]::part(input-field)",
      displayName: "Input field",
      properties: i
    },
    {
      selector: "vaadin-login-form vaadin-login-form-wrapper [required]::part(label)",
      displayName: "Label",
      properties: t
    },
    {
      selector: "vaadin-login-form vaadin-login-form-wrapper [required]::part(helper-text)",
      displayName: "Helper text",
      properties: p
    },
    {
      selector: "vaadin-login-form vaadin-login-form-wrapper [required]::part(error-message)",
      displayName: "Error message",
      properties: s
    },
    {
      selector: "vaadin-login-form vaadin-password-field::part(reveal-button)",
      displayName: "Reveal button",
      properties: o
    },
    {
      selector: 'vaadin-login-form vaadin-button[theme~="submit"]',
      displayName: "Log In Button",
      properties: a
    },
    {
      selector: 'vaadin-login-form vaadin-button[theme~="submit"]::part(label)',
      displayName: "Log In Button Label",
      properties: r
    },
    {
      selector: 'vaadin-login-form [slot="forgot-password"]',
      displayName: "Forgot password button",
      properties: a
    },
    {
      selector: 'vaadin-login-form [slot="forgot-password"]::part(label)',
      displayName: "Forgot password button label",
      properties: r
    }
  ]
};
export {
  m as default
};
