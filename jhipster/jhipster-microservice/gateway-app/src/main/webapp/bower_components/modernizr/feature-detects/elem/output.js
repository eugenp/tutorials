/*!
{
  "name": "output Element",
  "property": "outputelem",
  "tags": ["elem"],
  "builderAliases": ["elem_output"],
  "notes": [{
    "name": "WhatWG Spec",
    "href": "https://html.spec.whatwg.org/multipage/forms.html#the-output-element"
  }]
}
!*/
define(['Modernizr', 'createElement'], function(Modernizr, createElement) {
  Modernizr.addTest('outputelem', 'value' in createElement('output'));
});
