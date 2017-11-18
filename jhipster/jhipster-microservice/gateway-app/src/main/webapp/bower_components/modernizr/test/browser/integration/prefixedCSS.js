describe('prefixedCSS', function() {
  function gimmePrefix(prop, obj) {
    var prefixes = ['Moz', 'Khtml', 'Webkit', 'O', 'ms'],
      domPrefixes = ['moz', 'khtml', 'webkit', 'o', 'ms'],
      elem     = document.createElement('div'),
      upper    = prop.charAt(0).toUpperCase() + prop.slice(1),
      len;

    if (!obj) {
      if (prop in elem.style) {
        return prop;
      }

      for (len = prefixes.length; len--;) {
        if ((prefixes[len] + upper)  in elem.style) {
          return (prefixes[len] + upper);
        }
      }
    } else {
      if (prop in obj) {
        return prop;
      }

      for (len = domPrefixes.length; len--;) {
        if ((domPrefixes[len] + upper)  in obj) {
          return (domPrefixes[len] + upper);
        }
      }
    }

    return false;
  }

  function domToCSS(name) {
    return name.replace(/([A-Z])/g, function(str, m1) {
      return '-' + m1.toLowerCase();
    }).replace(/^ms-/, '-ms-');
  }

  function cssToDOM(name) {
    return name.replace(/([a-z])-([a-z])/g, function(str, m1, m2) {
      return m1 + m2.toUpperCase();
    }).replace(/^-/, '');
  }

  function testProp(prop) {
    var prefixed = gimmePrefix(cssToDOM(prop));
    if (prefixed) {
      it('results for ' + prop + ' match the homebaked prefix finder', function() {
        expect(Modernizr.prefixedCSS(prop)).to.be(domToCSS(prefixed));
      });
    } else {
      it('results for ' + prop + ' match the homebaked prefix finder', function() {
        expect(Modernizr.prefixedCSS(prop)).to.be(false);
      });
    }
  }

  testProp('animationProperty');
  testProp('fontFeatureSettings');
  testProp('flexWrap');
  testProp('boxSizing');
  testProp('textShadow');
  testProp('resize');
  testProp('animation-property');
  testProp('font-feature-settings');
  testProp('flex-wrap');
  testProp('box-sizing');
  testProp('text-shadow');
});
