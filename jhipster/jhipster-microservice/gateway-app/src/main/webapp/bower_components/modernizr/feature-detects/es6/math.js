/*!
{
  "name": "ES6 Math",
  "property": "es6math",
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
Check if browser implements ECMAScript 6 Math per specification.
*/
define(['Modernizr'], function(Modernizr) {
  Modernizr.addTest('es6math', !!(Math &&
    Math.clz32 &&
    Math.cbrt &&
    Math.imul &&
    Math.sign &&
    Math.log10 &&
    Math.log2 &&
    Math.log1p &&
    Math.expm1 &&
    Math.cosh &&
    Math.sinh &&
    Math.tanh &&
    Math.acosh &&
    Math.asinh &&
    Math.atanh &&
    Math.hypot &&
    Math.trunc &&
    Math.fround));
});
