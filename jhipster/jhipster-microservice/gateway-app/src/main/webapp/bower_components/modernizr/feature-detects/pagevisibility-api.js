/*!
{
  "name": "Page Visibility API",
  "property": "pagevisibility",
  "caniuse": "pagevisibility",
  "tags": ["performance"],
  "notes": [{
    "name": "MDN documentation",
    "href": "https://developer.mozilla.org/en-US/docs/DOM/Using_the_Page_Visibility_API"
  },{
    "name": "W3C spec",
    "href": "https://www.w3.org/TR/2011/WD-page-visibility-20110602/"
  },{
    "name": "HTML5 Rocks tutorial",
    "href": "http://www.html5rocks.com/en/tutorials/pagevisibility/intro/"
  }],
  "polyfills": ["visibilityjs", "visiblyjs", "jquery-visibility"]
}
!*/
/* DOC
Detects support for the Page Visibility API, which can be used to disable unnecessary actions and otherwise improve user experience.
*/
define(['Modernizr', 'prefixed'], function(Modernizr, prefixed) {
  Modernizr.addTest('pagevisibility', !!prefixed('hidden', document, false));
});
