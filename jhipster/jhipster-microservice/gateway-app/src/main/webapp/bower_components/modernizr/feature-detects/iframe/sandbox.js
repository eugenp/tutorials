/*!
{
  "name": "iframe[sandbox] Attribute",
  "property": "sandbox",
  "tags": ["iframe"],
  "builderAliases": ["iframe_sandbox"],
  "notes": [
  {
    "name": "WhatWG Spec",
    "href": "https://html.spec.whatwg.org/multipage/embedded-content.html#attr-iframe-sandbox"
  }],
  "knownBugs": [ "False-positive on Firefox < 29" ]
}
!*/
/* DOC
Test for `sandbox` attribute in iframes.
*/
define(['Modernizr', 'createElement'], function(Modernizr, createElement) {
  Modernizr.addTest('sandbox', 'sandbox' in createElement('iframe'));
});
