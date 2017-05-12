/*!
{
  "name": "DOM4 MutationObserver",
  "property": "mutationobserver",
  "caniuse": "mutationobserver",
  "tags": ["dom"],
  "authors": ["Karel Sedláček (@ksdlck)"],
  "polyfills": ["mutationobservers"],
  "notes": [{
    "name": "MDN documentation",
    "href": "https://developer.mozilla.org/en-US/docs/Web/API/MutationObserver"
  }]
}
!*/
/* DOC

Determines if DOM4 MutationObserver support is available.

*/
define(['Modernizr'], function(Modernizr) {
  Modernizr.addTest('mutationobserver',
    !!window.MutationObserver || !!window.WebKitMutationObserver);
});
