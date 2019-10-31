/*!
{
  "name": "CSS user-select",
  "property": "userselect",
  "caniuse": "user-select-none",
  "authors": ["ryan seddon"],
  "tags": ["css"],
  "builderAliases": ["css_userselect"],
  "notes": [{
    "name": "Related Modernizr Issue",
    "href": "https://github.com/Modernizr/Modernizr/issues/250"
  }]
}
!*/
define(['Modernizr', 'testAllProps'], function(Modernizr, testAllProps) {
  //https://github.com/Modernizr/Modernizr/issues/250
  Modernizr.addTest('userselect', testAllProps('userSelect', 'none', true));
});
