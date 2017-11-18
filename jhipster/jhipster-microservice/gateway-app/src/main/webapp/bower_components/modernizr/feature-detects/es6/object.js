/*!
{
  "name": "ES6 Object",
  "property": "es6object",
  "notes": [{
    "name": "unofficial ECMAScript 6 draft specification",
    "href": "https://people.mozilla.org/~jorendorff/es6-draft.html"
  }],
  "polyfills": ["es6shim"],
  "authors": ["Ron Waldon (@jokeyrhyme)"],
  "warnings": ["ECMAScript 6 is still a only a draft, so this detect may not match the final specification or implementations."],
  "tags": ["es6"]
}
!*/
/* DOC
Check if browser implements ECMAScript 6 Object per specification.
*/
define(['Modernizr'], function(Modernizr) {
  Modernizr.addTest('es6object', !!(Object.assign &&
    Object.is &&
    Object.setPrototypeOf));
});
