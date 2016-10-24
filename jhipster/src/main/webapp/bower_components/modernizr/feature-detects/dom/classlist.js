/*!
{
  "name": "classList",
  "caniuse": "classlist",
  "property": "classlist",
  "tags": ["dom"],
  "builderAliases": ["dataview_api"],
  "notes": [{
    "name": "MDN Docs",
    "href": "https://developer.mozilla.org/en/DOM/element.classList"
  }]
}
!*/
define(['Modernizr', 'docElement'], function(Modernizr, docElement) {
  Modernizr.addTest('classlist', 'classList' in docElement);
});
