/*!
{
  "name": "WebGL Extensions",
  "property": "webglextensions",
  "tags": ["webgl", "graphics"],
  "builderAliases": ["webgl_extensions"],
  "async" : true,
  "authors": ["Ilmari Heikkinen"],
  "knownBugs": [],
  "notes": [{
    "name": "Kronos extensions registry",
    "href": "http://www.khronos.org/registry/webgl/extensions/"
  }]
}
!*/
/* DOC
Detects support for OpenGL extensions in WebGL. It's `true` if the [WebGL extensions API](https://developer.mozilla.org/en-US/docs/Web/WebGL/Using_Extensions) is supported, then exposes the supported extensions as subproperties, e.g.:

```javascript
if (Modernizr.webglextensions) {
  // WebGL extensions API supported
}
if ('OES_vertex_array_object' in Modernizr.webglextensions) {
  // Vertex Array Objects extension supported
}
```
*/
define(['Modernizr', 'createElement', 'test/webgl'], function(Modernizr, createElement) {
  // based on code from ilmari heikkinen
  // code.google.com/p/graphics-detect/source/browse/js/detect.js

  // Not Async but handles it's own self
  Modernizr.addAsyncTest(function() {
    /* jshint -W053 */

    // Not a good candidate for css classes, so we avoid addTest stuff
    Modernizr.webglextensions = new Boolean(false);

    if (!Modernizr.webgl) {
      return;
    }

    var canvas;
    var ctx;
    var exts;

    try {
      canvas = createElement('canvas');
      ctx = canvas.getContext('webgl') || canvas.getContext('experimental-webgl');
      exts = ctx.getSupportedExtensions();
    }
    catch (e) {
      return;
    }

    if (ctx !== undefined) {
      Modernizr.webglextensions = new Boolean(true);
    }

    for (var i = -1, len = exts.length; ++i < len;) {
      Modernizr.webglextensions[exts[i]] = true;
    }

    canvas = undefined;
  });
});
