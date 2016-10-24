/*!
{
  "name": "MathML",
  "property": "mathml",
  "caniuse": "mathml",
  "authors": ["Addy Osmani", "Davide P. Cervone", "David Carlisle"],
  "knownBugs": ["Firefox < 4 will likely return a false, however it does support MathML inside XHTML documents"],
  "notes": [{
    "name": "W3C spec",
    "href": "https://www.w3.org/Math/"
  }],
  "polyfills": ["mathjax"]
}
!*/
/* DOC
Detects support for MathML, for mathematic equations in web pages.
*/
define(['Modernizr', 'testStyles'], function(Modernizr, testStyles) {
  // Based on work by Davide (@dpvc) and David (@davidcarlisle)
  // in https://github.com/mathjax/MathJax/issues/182

  Modernizr.addTest('mathml', function() {
    var ret;

    testStyles('#modernizr{position:absolute;display:inline-block}', function(node) {
      node.innerHTML += '<math><mfrac><mi>xx</mi><mi>yy</mi></mfrac></math>';

      ret = node.offsetHeight > node.offsetWidth;
    });

    return ret;
  });
});
