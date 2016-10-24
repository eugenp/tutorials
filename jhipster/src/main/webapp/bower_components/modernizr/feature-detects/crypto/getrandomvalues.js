/*!
{
  "name": "getRandomValues",
  "property": "getrandomvalues",
  "caniuse": "window.crypto.getRandomValues",
  "tags": ["crypto"],
  "authors": ["komachi"],
  "notes": [{
    "name": "W3C Editor’s Draft",
    "href": "https://dvcs.w3.org/hg/webcrypto-api/raw-file/tip/spec/Overview.html#RandomSource-method-getRandomValues"
  }],
  "polyfills": [
    "polycrypt"
  ]
}
!*/
/* DOC
Detects support for the window.crypto.getRandomValues for generate cryptographically secure random numbers
*/
define(['Modernizr', 'prefixed', 'is'], function(Modernizr, prefixed, is) {
  // In Safari <=5.0 `window.crypto` exists (for some reason) but is `undefined`, so we have to check
  // it’s truthy before checking for existence of `getRandomValues`
  var crypto = prefixed('crypto', window);
  var supportsGetRandomValues;

  // Safari 6.0 supports crypto.getRandomValues, but does not return the array,
  // which is required by the spec, so we need to actually check.
  if (crypto && 'getRandomValues' in crypto && 'Uint32Array' in window) {
    var array = new Uint32Array(10);
    var values = crypto.getRandomValues(array);
    supportsGetRandomValues = values && is(values[0], 'number');
  }

  Modernizr.addTest('getrandomvalues', !!supportsGetRandomValues);
});
