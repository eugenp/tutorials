/*!
{
  "name": "ES5 Immutable Undefined",
  "property": "es5undefined",
  "notes": [{
    "name": "ECMAScript 5.1 Language Specification",
    "href": "http://www.ecma-international.org/ecma-262/5.1/"
  }, {
    "name": "original implementation of detect code",
    "href": "http://kangax.github.io/es5-compat-table/"
  }],
  "authors": ["Ron Waldon (@jokeyrhyme)"],
  "tags": ["es5"]
}
!*/
/* DOC
Check if browser prevents assignment to global `undefined` per ECMAScript 5.
*/
define(['Modernizr'], function(Modernizr) {
  Modernizr.addTest('es5undefined', function() {
    var result, originalUndefined;
    try {
      originalUndefined = window.undefined;
      window.undefined = 12345;
      result = typeof window.undefined === 'undefined';
      window.undefined = originalUndefined;
    } catch (e) {
      return false;
    }
    return result;
  });
});
