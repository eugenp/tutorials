/*!
{
  "name": "ES6 Generators",
  "property": "generators",
  "authors": ["Michael Kachanovskyi"],
  "tags": ["es6"]
}
!*/
/* DOC
Check if browser implements ECMAScript 6 Generators per specification.
*/
define(['Modernizr'], function(Modernizr) {
  Modernizr.addTest('generators', function() {
    try {
      /* jshint evil: true */
      new Function('function* test() {}')();
    } catch (e) {
      return false;
    }
    return true;
  });
});
