/*!
{
  "name": "Dart",
  "property": "dart",
  "authors": ["Theodoor van Donge"],
  "notes": [{
    "name": "Language website",
    "href": "https://www.dartlang.org/"
  }]
}
!*/
/* DOC
Detects native support for the Dart programming language.
*/
define(['Modernizr', 'prefixed'], function(Modernizr, prefixed) {
  Modernizr.addTest('dart', !!prefixed('startDart', navigator));
});
