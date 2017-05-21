/*!
{
  "name": "CSS Transitions",
  "property": "csstransitions",
  "caniuse": "css-transitions",
  "tags": ["css"]
}
!*/
define(['Modernizr', 'testAllProps'], function(Modernizr, testAllProps) {
  Modernizr.addTest('csstransitions', testAllProps('transition', 'all', true));
});
