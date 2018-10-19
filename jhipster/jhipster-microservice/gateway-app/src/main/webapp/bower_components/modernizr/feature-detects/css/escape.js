/*!
{
  "name": "CSS.escape()",
  "property": "cssescape",
  "polyfills": [
    "css-escape"
  ],
  "tags": [
    "css",
    "cssom"
  ]
}
!*/
/* DOC
Tests for `CSS.escape()` support.
*/
define(['Modernizr'], function(Modernizr) {
  var CSS = window.CSS;
  Modernizr.addTest('cssescape', CSS ? typeof CSS.escape == 'function' : false);
});
