/*!
{
  "name": "SVG SMIL animation",
  "property": "smil",
  "caniuse": "svg-smil",
  "tags": ["svg"],
  "notes": [{
  "name": "W3C Synchronised Multimedia spec",
  "href": "https://www.w3.org/AudioVideo/"
  }]
}
!*/
define(['Modernizr', 'toStringFn'], function(Modernizr, toStringFn) {
  // SVG SMIL animation
  Modernizr.addTest('smil', function() {
    return !!document.createElementNS &&
      /SVGAnimate/.test(toStringFn.call(document.createElementNS('http://www.w3.org/2000/svg', 'animate')));
  });
});
