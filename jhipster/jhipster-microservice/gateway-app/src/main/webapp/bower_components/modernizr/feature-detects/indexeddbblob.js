/*!
{
  "name": "IndexedDB Blob",
  "property": "indexeddbblob"
}
!*/
/* DOC
Detects if the browser can save File/Blob objects to IndexedDB
*/
define(['Modernizr', 'addTest', 'prefixed', 'test/indexeddb'], function(Modernizr, addTest, prefixed) {
  // Vendors had inconsistent prefixing with the experimental Indexed DB:
  // - Webkit's implementation is accessible through webkitIndexedDB
  // - Firefox shipped moz_indexedDB before FF4b9, but since then has been mozIndexedDB
  // For speed, we don't test the legacy (and beta-only) indexedDB

  Modernizr.addAsyncTest(function() {
    /* jshint -W053 */
    var indexeddb;
    var dbname = 'detect-blob-support';
    var supportsBlob = false;
    var request;
    var db;

    try {
      indexeddb = prefixed('indexedDB', window);
    } catch (e) {
    }

    if (!(Modernizr.indexeddb && Modernizr.indexeddb.deletedatabase)) {
      return false;
    }

    // Calling `deleteDatabase` in a try…catch because some contexts (e.g. data URIs)
    // will throw a `SecurityError`
    try {
      indexeddb.deleteDatabase(dbname).onsuccess = function() {
        request = indexeddb.open(dbname, 1);
        request.onupgradeneeded = function() {
          request.result.createObjectStore('store');
        };
        request.onsuccess = function() {
          db = request.result;
          try {
            db.transaction('store', 'readwrite').objectStore('store').put(new Blob(), 'key');
            supportsBlob = true;
          }
          catch (e) {
            supportsBlob = false;
          }
          finally {
            addTest('indexeddbblob', supportsBlob);
            db.close();
            indexeddb.deleteDatabase(dbname);
          }
        };
      };
    }
    catch (e) {
      addTest('indexeddbblob', false);
    }
  });
});
