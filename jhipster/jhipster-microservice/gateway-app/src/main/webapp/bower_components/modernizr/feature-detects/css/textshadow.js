/*!
{
  "name": "CSS textshadow",
  "property": "textshadow",
  "caniuse": "css-textshadow",
  "tags": ["css"],
  "knownBugs": ["FF3.0 will false positive on this test"]
}
!*/
define(['Modernizr', 'testProp'], function(Modernizr, testProp) {
  Modernizr.addTest('textshadow', testProp('textShadow', '1px 1px'));
});
