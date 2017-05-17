/*!
{
  "name": "Input attributes",
  "property": "input",
  "tags": ["forms"],
  "authors": ["Mike Taylor"],
  "notes": [{
    "name": "WHATWG spec",
    "href": "https://html.spec.whatwg.org/multipage/forms.html#input-type-attr-summary"
  }],
  "knownBugs": ["Some blackberry devices report false positive for input.multiple"]
}
!*/
/* DOC
Detects support for HTML5 `<input>` element attributes and exposes Boolean subproperties with the results:

```javascript
Modernizr.input.autocomplete
Modernizr.input.autofocus
Modernizr.input.list
Modernizr.input.max
Modernizr.input.min
Modernizr.input.multiple
Modernizr.input.pattern
Modernizr.input.placeholder
Modernizr.input.required
Modernizr.input.step
```
*/
define(['Modernizr', 'createElement', 'inputElem'], function(Modernizr, createElement, inputElem) {
  // Run through HTML5's new input attributes to see if the UA understands any.
  // Mike Taylr has created a comprehensive resource for testing these attributes
  //   when applied to all input types:
  //   miketaylr.com/code/input-type-attr.html

  // Only input placeholder is tested while textarea's placeholder is not.
  // Currently Safari 4 and Opera 11 have support only for the input placeholder
  // Both tests are available in feature-detects/forms-placeholder.js

  var inputattrs = 'autocomplete autofocus list placeholder max min multiple pattern required step'.split(' ');
  var attrs = {};

  Modernizr.input = (function(props) {
    for (var i = 0, len = props.length; i < len; i++) {
      attrs[ props[i] ] = !!(props[i] in inputElem);
    }
    if (attrs.list) {
      // safari false positive's on datalist: webk.it/74252
      // see also github.com/Modernizr/Modernizr/issues/146
      attrs.list = !!(createElement('datalist') && window.HTMLDataListElement);
    }
    return attrs;
  })(inputattrs);
});
