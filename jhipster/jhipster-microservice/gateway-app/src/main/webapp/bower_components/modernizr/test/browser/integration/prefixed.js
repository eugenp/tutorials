describe('prefixed()', function() {

  // Generic control function used for prefixed() and prefixedCSS() tests
  // https://gist.github.com/523692
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

  var propArr = ['transition', 'backgroundSize', 'boxSizing', 'borderImage',
    'borderRadius', 'boxShadow', 'columnCount'];

  var domPropArr = [
    {'prop': 'requestAnimationFrame',  'obj': window},
    {'prop': 'querySelectorAll',       'obj': document},
    {'prop': 'matchesSelector',        'obj': document.createElement('div')}];

  _.forEach(propArr, function(prop) {
    it('results for ' + prop + ' match the homebaked prefix finder', function() {
      expect(Modernizr.prefixed(prop)).to.equal(gimmePrefix(prop));
    });
  });

  _.forEach(propArr, function(prop) {
    it('results for ' + prop + ' match the homebaked prefix finder', function() {
      expect(Modernizr.prefixed(domToCSS(prop))).to.equal(gimmePrefix(prop));
    });
  });

  _.forEach(domPropArr, function(prop) {
    it('results for ' + prop.prop + ' match the homebaked prefix finder', function() {
      var modernizrVersion = Modernizr.prefixed(prop.prop, prop.obj, false);
      var localVersion = gimmePrefix(prop.prop, prop.obj);
      expect(modernizrVersion).to[(_.isString(localVersion) ? 'contain' : 'equal')](localVersion);
    });
  });
});
