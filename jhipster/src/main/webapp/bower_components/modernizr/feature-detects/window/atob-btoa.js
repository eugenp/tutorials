/*!
{
  "name": "Base 64 encoding/decoding",
  "property": ["atobbtoa"],
  "builderAliases": ["atob-btoa"],
  "caniuse" : "atob-btoa",
  "tags": ["atob", "base64", "WindowBase64", "btoa"],
  "authors": ["Christian Ulbrich"],
  "notes": [{
    "name": "WindowBase64",
    "href": "https://www.w3.org/TR/html5/webappapis.html#windowbase64"
  }, {
    "name": "MDN documentation",
    "href": "https://developer.mozilla.org/en-US/docs/Web/API/WindowBase64/atob"
  }],
  "polyfills": ["base64js"]
}
!*/
/* DOC

Detects support for WindowBase64 API (window.atob && window.btoa).

*/
define(['Modernizr'], function(Modernizr) {
  Modernizr.addTest('atobbtoa', 'atob' in window && 'btoa' in window, {aliases: ['atob-btoa']});
});
