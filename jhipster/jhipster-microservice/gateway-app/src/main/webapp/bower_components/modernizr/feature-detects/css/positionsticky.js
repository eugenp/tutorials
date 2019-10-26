/*!
{
  "name": "CSS position: sticky",
  "property": "csspositionsticky",
  "tags": ["css"],
  "builderAliases": ["css_positionsticky"],
  "notes": [{
    "name": "Chrome bug report",
    "href":"https://code.google.com/p/chromium/issues/detail?id=322972"
  }],
  "warnings": [ "using position:sticky on anything but top aligned elements is buggy in Chrome < 37 and iOS <=7+" ]
}
!*/
define(['Modernizr', 'createElement', 'prefixes'], function(Modernizr, createElement, prefixes) {
  // Sticky positioning - constrains an element to be positioned inside the
  // intersection of its container box, and the viewport.
  Modernizr.addTest('csspositionsticky', function() {
    var prop = 'position:';
    var value = 'sticky';
    var el = createElement('a');
    var mStyle = el.style;

    mStyle.cssText = prop + prefixes.join(value + ';' + prop).slice(0, -prop.length);

    return mStyle.position.indexOf(value) !== -1;
  });
});
