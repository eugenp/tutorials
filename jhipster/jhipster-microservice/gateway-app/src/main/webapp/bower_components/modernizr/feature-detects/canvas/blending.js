/*!
{
  "name": "canvas blending support",
  "property": "canvasblending",
  "tags": ["canvas"],
  "async" : false,
  "notes": [{
      "name": "HTML5 Spec",
      "href": "https://dvcs.w3.org/hg/FXTF/rawfile/tip/compositing/index.html#blending"
    },
    {
      "name": "Article",
      "href": "https://blogs.adobe.com/webplatform/2013/01/28/blending-features-in-canvas"
    }]
}
!*/
/* DOC
Detects if Photoshop style blending modes are available in canvas.
*/
define(['Modernizr', 'createElement', 'test/canvas'], function(Modernizr, createElement) {

  Modernizr.addTest('canvasblending', function() {
    if (Modernizr.canvas === false) {
      return false;
    }
    var ctx = createElement('canvas').getContext('2d');
    // firefox 3 throws an error when setting an invalid `globalCompositeOperation`
    try {
      ctx.globalCompositeOperation = 'screen';
    } catch (e) {}

    return ctx.globalCompositeOperation === 'screen';
  });

});
