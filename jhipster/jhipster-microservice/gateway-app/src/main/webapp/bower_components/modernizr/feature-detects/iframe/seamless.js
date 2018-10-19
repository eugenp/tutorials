/*!
{
  "name": "iframe[seamless] Attribute",
  "property": "seamless",
  "tags": ["iframe"],
  "builderAliases": ["iframe_seamless"],
  "notes": [{
    "name": "WhatWG Spec",
    "href": "https://html.spec.whatwg.org/multipage/embedded-content.html#attr-iframe-seamless"
  }]
}
!*/
/* DOC
Test for `seamless` attribute in iframes.
*/
define(['Modernizr', 'createElement'], function(Modernizr, createElement) {
  Modernizr.addTest('seamless', 'seamless' in createElement('iframe'));
});
