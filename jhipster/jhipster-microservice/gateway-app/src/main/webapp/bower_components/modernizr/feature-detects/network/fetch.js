/*!
{
  "name": "Fetch API",
  "property": "fetch",
  "tags": ["network"],
  "caniuse": "fetch",
  "notes": [{
    "name": "Fetch Living Standard",
    "href": "https://fetch.spec.whatwg.org/"
  }],
  "polyfills": ["fetch"]
}
!*/
/* DOC
Detects support for the fetch API, a modern replacement for XMLHttpRequest.
*/
define(['Modernizr'], function(Modernizr) {
  Modernizr.addTest('fetch', 'fetch' in window);
});
