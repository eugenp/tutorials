/*!
{
  "name": "CSS Opacity",
  "caniuse": "css-opacity",
  "property": "opacity",
  "tags": ["css"]
}
!*/
define(['Modernizr', 'createElement', 'prefixes'], function(Modernizr, createElement, prefixes) {
  // Browsers that actually have CSS Opacity implemented have done so
  // according to spec, which means their return values are within the
  // range of [0.0,1.0] - including the leading zero.

  Modernizr.addTest('opacity', function() {
    var style = createElement('a').style;
    style.cssText = prefixes.join('opacity:.55;');

    // The non-literal . in this regex is intentional:
    // German Chrome returns this value as 0,55
    // github.com/Modernizr/Modernizr/issues/#issue/59/comment/516632
    return (/^0.55$/).test(style.opacity);
  });
});
