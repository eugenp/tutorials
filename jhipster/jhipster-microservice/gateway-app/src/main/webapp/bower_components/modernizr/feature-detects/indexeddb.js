/*!
{
  "name": "IndexedDB",
  "property": "indexeddb",
  "caniuse": "indexeddb",
  "tags": ["storage"],
  "polyfills": ["indexeddb"]
}
!*/
/* DOC
Detects support for the IndexedDB client-side storage API (final spec).
*/
define(['Modernizr', 'prefixed'], function(Modernizr, prefixed) {
  // Vendors had inconsistent prefixing with the experimental Indexed DB:
  // - Webkit's implementation is accessible through webkitIndexedDB
  // - Firefox shipped moz_indexedDB before FF4b9, but since then has been mozIndexedDB
  // For speed, we don't test the legacy (and beta-only) indexedDB

  var indexeddb;
  try {
    indexeddb = prefixed('indexedDB', window);
  } catch (e) {
  }

  Modernizr.addTest('indexeddb', !!indexeddb);

  if (!!indexeddb) {
    Modernizr.addTest('indexeddb.deletedatabase', 'deleteDatabase' in indexeddb);
  }
});
