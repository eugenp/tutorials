/*!
{
  "name": "Workers from Blob URIs",
  "property": "blobworkers",
  "tags": ["performance", "workers"],
  "builderAliases": ["workers_blobworkers"],
  "notes": [{
    "name": "W3C Reference",
    "href": "https://www.w3.org/TR/workers/"
  }],
  "knownBugs": ["This test may output garbage to console."],
  "authors": ["Jussi Kalliokoski"],
  "async": true
}
!*/
/* DOC
Detects support for creating Web Workers from Blob URIs.
*/
define(['Modernizr', 'addTest'], function(Modernizr, addTest) {
  Modernizr.addAsyncTest(function() {
    try {
      // we're avoiding using Modernizr._domPrefixes as the prefix capitalization on
      // these guys are notoriously peculiar.
      var BlobBuilder = window.BlobBuilder;
      var URL         = window.URL;
      if (Modernizr._config.usePrefix) {
        BlobBuilder = BlobBuilder || window.MozBlobBuilder || window.WebKitBlobBuilder || window.MSBlobBuilder || window.OBlobBuilder;
        URL         = URL || window.MozURL || window.webkitURL || window.MSURL || window.OURL;
      }
      var data    = 'Modernizr',
          blob,
          bb,
          worker,
          url,
          timeout,
          scriptText = 'this.onmessage=function(e){postMessage(e.data)}';

      try {
        blob = new Blob([scriptText], {type: 'text/javascript'});
      } catch (e) {
        // we'll fall back to the deprecated BlobBuilder
      }
      if (!blob) {
        bb = new BlobBuilder();
        bb.append(scriptText);
        blob = bb.getBlob();
      }

      url = URL.createObjectURL(blob);
      worker = new Worker(url);

      worker.onmessage = function(e) {
        addTest('blobworkers', data === e.data);
        cleanup();
      };

      // Just in case...
      worker.onerror = fail;
      timeout = setTimeout(fail, 200);

      worker.postMessage(data);
    } catch (e) {
      fail();
    }

    function fail() {
      addTest('blobworkers', false);
      cleanup();
    }

    function cleanup() {
      if (url) {
        URL.revokeObjectURL(url);
      }
      if (worker) {
        worker.terminate();
      }
      if (timeout) {
        clearTimeout(timeout);
      }
    }
  });
});
