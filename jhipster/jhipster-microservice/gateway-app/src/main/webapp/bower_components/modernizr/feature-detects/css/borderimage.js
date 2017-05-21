/*!
{
  "name": "Border Image",
  "property": "borderimage",
  "caniuse": "border-image",
  "polyfills": ["css3pie"],
   "knownBugs": ["Android < 2.0 is true, but has a broken implementation"],
  "tags": ["css"]
}
!*/
define(['Modernizr', 'testAllProps'], function(Modernizr, testAllProps) {
  Modernizr.addTest('borderimage', testAllProps('borderImage', 'url() 1', true));
});
