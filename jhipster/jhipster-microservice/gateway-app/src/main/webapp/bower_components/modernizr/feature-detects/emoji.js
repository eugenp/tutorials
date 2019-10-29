/*!
{
  "name": "Emoji",
  "property": "emoji"
}
!*/
/* DOC
Detects support for emoji character sets.
*/
define(['Modernizr', 'createElement', 'test/canvastext'], function(Modernizr, createElement) {
  Modernizr.addTest('emoji', function() {
    if (!Modernizr.canvastext) {
      return false;
    }
    var pixelRatio = window.devicePixelRatio || 1;
    var offset = 12 * pixelRatio;
    var node = createElement('canvas');
    var ctx = node.getContext('2d');
    ctx.fillStyle = '#f00';
    ctx.textBaseline = 'top';
    ctx.font = '32px Arial';
    ctx.fillText('\ud83d\udc28', 0, 0); // U+1F428 KOALA
    return ctx.getImageData(offset, offset, 1, 1).data[0] !== 0;
  });
});
