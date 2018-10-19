/*!
{
  "name": "ES5 String.prototype.contains",
  "property": "contains",
  "authors": ["Robert Kowalski"],
  "tags": ["es6"]
}
!*/
/* DOC
Check if browser implements ECMAScript 6 `String.prototype.contains` per specification.
*/
define(['Modernizr', 'is'], function(Modernizr, is) {
  Modernizr.addTest('contains', is(String.prototype.contains, 'function'));
});
