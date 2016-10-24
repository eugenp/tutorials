/*!
{
  "name": "ES6 Collections",
  "property": "es6collections",
  "notes": [{
    "name": "unofficial ECMAScript 6 draft specification",
    "href": "https://people.mozilla.org/~jorendorff/es6-draft.html"
  }],
  "polyfills": ["es6shim", "weakmap"],
  "authors": ["Ron Waldon (@jokeyrhyme)"],
  "warnings": ["ECMAScript 6 is still a only a draft, so this detect may not match the final specification or implementations."],
  "tags": ["es6"]
}
!*/
/* DOC
Check if browser implements ECMAScript 6 Map, Set, WeakMap and WeakSet
*/
define(['Modernizr'], function(Modernizr) {
  Modernizr.addTest('es6collections', !!(
    window.Map && window.Set && window.WeakMap && window.WeakSet
  ));
});
