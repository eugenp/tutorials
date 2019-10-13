/*! {
  "name": "hairline",
  "property": "csshairline",
  "tags": ["css"],
  "authors": ["strarsis"],
  "notes": [{
    "name": "Blog post about CSS retina hairlines",
    "href": "http://dieulot.net/css-retina-hairline"
  },{
    "name": "Derived from",
    "href": "https://gist.github.com/dieulot/520a49463f6058fbc8d1"
  }]
}
!*/
/* DOC
Detects support for hidpi/retina hairlines, which are CSS borders with less than 1px in width, for being physically 1px on hidpi screens.
*/

define(['Modernizr', 'testStyles'], function(Modernizr, testStyles) {
  Modernizr.addTest('hairline', function() {
    return testStyles('#modernizr {border:.5px solid transparent}', function(elem) {
      return elem.offsetHeight === 1;
    });
  });
});
