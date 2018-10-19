/*!
{
  "name": "cssall",
  "property": "cssall",
  "notes": [{
    "name": "Spec",
    "href": "https://drafts.csswg.org/css-cascade/#all-shorthand"
  }]
}
!*/
/* DOC
Detects support for the `all` css property, which is a shorthand to reset all css properties (except direction and unicode-bidi) to their original value
*/

define(['Modernizr', 'docElement'], function(Modernizr, docElement) {
  Modernizr.addTest('cssall', 'all' in docElement.style);
});
