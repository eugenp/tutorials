describe('svg context unit tests', function() {
  var svgContext;
  var cleanup;
  var object;
  var req;

  if ('createElementNS' in document) {

    var setup = function(settings) {
      var stringified = settings.stringified;
      var instrumented = !!stringified.match(/__cov_/);

      if (instrumented) {
        settings.coverageObjName = stringified.match(/(?:^[^{]*{)([^.]*)/)[1];
        svgContext[settings.coverageObjName] = window[settings.coverageObjName];
      }

      _.extend(svgContext, settings.setup);

      svgContext.eval(stringified);

      settings.cleanup = function() {
        if (instrumented) {
          window[settings.coverageObjName] = svgContext[settings.coverageObjName] ;
        }
      };

      return settings;
    };

    before(function(done) {

      req = requirejs.config({
        context: Math.random().toString().slice(2),
        baseUrl: '../src',
        paths: {cleanup: '../test/cleanup'}
      });

      req(['cleanup'], function(_cleanup) {
        cleanup = _cleanup;
        done();
      });
    });

    beforeEach(function(done) {
      object = document.createElement('object');

      object.data = '../test/img/unit.svg';
      object.type = 'image/svg+xml';
      object.id = 'svgContext';

      object.onerror = function() {
        var arg = Array.prototype.slice.call(arguments).join(' ');
        try {
          expect(arg).to.be(undefined);
        } catch (e) {
          done(e);
        }
      };

      object.runUnitTests = function(thisRef) {
        svgContext = thisRef;

        req = requirejs.config({
          context: Math.random().toString().slice(2),
          baseUrl: '../src'
        });

        done();
      };

      document.body.appendChild(object);
    });

    it('is still able to set classNames correctly', function(done) {
      req(['setClasses'], function(setClasses) {
        try {
          var testInstance = setup({
            stringified: setClasses.toString(),
            setup: {
              Modernizr: {_config: {enableClasses: true}},
              docElement: svgContext.document.documentElement,
              isSVG: true
            }
          });

          svgContext.test(function() {
            setClasses(['svgdetect']);
          });

          expect(svgContext.document.documentElement.className.baseVal).to.contain('svgdetect');

          testInstance.cleanup();

          done();
        }
        catch (e) { done(e); }
      });
    });

    it('uses the correct namespace when creating elements', function(done) {
      req(['createElement'], function(createElement) {
        try {
          var testInstance = setup({
            stringified: createElement.toString(),
            setup: {
              isSVG: true
            }
          });

          svgContext.test(function() {
            window._testElem = createElement('a');
          });

          expect(svgContext._testElem.namespaceURI).to.equal('http://www.w3.org/2000/svg');

          testInstance.cleanup();

          done();
        }
        catch (e) { done(e); }
      });
    });

    it('uses a SVG element for when making a fake body', function(done) {
      req(['getBody'], function(getBody) {
        try {
          var testInstance = setup({
            stringified: getBody.toString(),
            setup: {
              isSVG: true,
              createElement: function() {
                return svgContext.document.createElement.apply(svgContext.document, arguments);
              }
            }
          });

          svgContext.test(function() {
            window._body = getBody();
          });

          expect(svgContext._body.nodeName.toLowerCase()).to.equal('svg');

          testInstance.cleanup();

          done();
        }
        catch (e) { done(e); }
      });
    });


    afterEach(function() {
      object.parentNode.removeChild(object);
      cleanup();
    });
  }
});
