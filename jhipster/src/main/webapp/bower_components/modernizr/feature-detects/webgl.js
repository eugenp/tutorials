/*!
{
  "name": "WebGL",
  "property": "webgl",
  "caniuse": "webgl",
  "tags": ["webgl", "graphics"],
  "polyfills": ["jebgl", "cwebgl", "iewebgl"]
}
!*/
define(['Modernizr', 'createElement'], function(Modernizr, createElement) {
  Modernizr.addTest('webgl', function() {
    var canvas = createElement('canvas');
    var supports = 'probablySupportsContext' in canvas ? 'probablySupportsContext' :  'supportsContext';
    if (supports in canvas) {
      return canvas[supports]('webgl') || canvas[supports]('experimental-webgl');
    }
    return 'WebGLRenderingContext' in window;
  });
});
