/*!
{
  "name": "CSS Reflections",
  "caniuse": "css-reflections",
  "property": "cssreflections",
  "tags": ["css"]
}
!*/
define(['Modernizr', 'testAllProps'], function(Modernizr, testAllProps) {
  Modernizr.addTest('cssreflections', testAllProps('boxReflect', 'above', true));
});
