/*!
{
  "name": "Application Cache",
  "property": "applicationcache",
  "caniuse": "offline-apps",
  "tags": ["storage", "offline"],
  "notes": [{
    "name": "MDN documentation",
    "href": "https://developer.mozilla.org/en/docs/HTML/Using_the_application_cache"
  }],
  "polyfills": ["html5gears"]
}
!*/
/* DOC
Detects support for the Application Cache, for storing data to enable web-based applications run offline.

The API has been [heavily criticized](http://alistapart.com/article/application-cache-is-a-douchebag) and discussions are underway to address this.
*/
define(['Modernizr'], function(Modernizr) {
  Modernizr.addTest('applicationcache', 'applicationCache' in window);
});
