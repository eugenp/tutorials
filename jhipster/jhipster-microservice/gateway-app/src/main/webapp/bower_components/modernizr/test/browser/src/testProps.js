describe('testProps', function() {
  var ModernizrProto = {foo: 7};
  var Modernizr = {_q: []};
  var testProps;
  var cleanup;
  var sinon;
  var req;

  before(function(done) {

    req = requirejs.config({
      context: Math.random().toString().slice(2),
      baseUrl: '../src',
      paths: {
        sinon: '../test/js/lib/sinon',
        cleanup: '../test/cleanup'
      }
    });

    define('ModernizrProto', [], function() {return ModernizrProto;});
    define('Modernizr', function() {return Modernizr;});
    define('package', [], function() {return {};});

    req(['cleanup', 'sinon'], function(_cleanup, _sinon) {
      cleanup = _cleanup;
      sinon = _sinon;
      done();
    });
  });

  describe('native detect', function() {
    var nativeTestProps = function(props, value) {
      return !!value ? true : undefined;
    };
    var contains;

    beforeEach(function(done) {
      contains = sinon.spy();

      nativeTestProps = sinon.spy(nativeTestProps);

      define('contains', function() {return contains;});
      define('nativeTestProps', sinon.spy(function() {return nativeTestProps;}));

      req(['testProps'], function(_testProps) {
        testProps = _testProps;
        done();
      });
    });

    it('returns the value from nativeTestProp if not undefined', function() {
      expect(testProps(['fake'], undefined, true));
      expect(nativeTestProps.callCount).to.be(1);
      expect(contains.callCount).to.be(0);
    });

    it('does not return the value from nativeTestProp when undefined', function() {
      expect(testProps(['fake'], undefined, false));
      expect(nativeTestProps.callCount).to.be(1);
      expect(contains.callCount).to.not.be(0);
    });

    afterEach(function() {
      req.undef('mStyle');
      req.undef('cssToDOM');
      req.undef('contains');
      req.undef('testProps');
      req.undef('nativeTestProps');
    });
  });

  describe('nonnative detect', function() {
    var contains = function(a, b) {return a.indexOf(b) > -1;};
    var nativeTestProps = function() { return; };
    var cssToDOM;
    var mStyle;

    beforeEach(function(done) {
      nativeTestProps = sinon.spy(nativeTestProps);
      contains = sinon.spy(contains);
      cssToDOM = sinon.spy();
      mStyle = {};

      define('nativeTestProps', sinon.spy(function() {return nativeTestProps;}));
      define('cssToDOM', function() {return cssToDOM;});
      define('contains', function() {return contains;});
      define('mStyle', function() {return mStyle;});

      req(['testProps'], function(_testProps) {
        testProps = _testProps;
        done();
      });
    });

    it('cleans up mStyle changes', function() {
      expect(testProps(['fake'], undefined, true));
      expect(contains.callCount).to.be(1);
      expect(mStyle.style).to.equal(undefined);
      expect(mStyle.modElem).to.equal(undefined);
    });

    it('calls cssToDOM when props have a `-`', function() {
      expect(testProps(['fake-detect'], undefined, true));
      expect(cssToDOM.called).to.be(true);
    });

    it('returns true for valid prop, and skipValueTest', function() {
      expect(testProps(['display'], undefined, true, true)).to.be(true);
    });

    it('returns true for valid prop, and good value', function() {
      expect(testProps(['display'], undefined, 'block')).to.be(true);
    });

    it('returns false for valid prop and bad value', function() {
      expect(testProps(['display'], undefined, 'penguin')).to.be(false);
    });

    it('returns the prop if a prefixed lookup', function() {
      expect(testProps(['display'], 'pfx', 'block')).to.be('display');
    });

    it('returns the prop if a prefixed lookup with skipValueTest', function() {
      expect(testProps(['display'], 'pfx', 'block', true)).to.be('display');
    });

    it('works properly', function() {
      // Everyone supports margin
      expect(testProps(['margin'])).to.equal(true);
      // Nobody supports the happiness style. :(
      expect(testProps(['happiness'])).to.equal(false);
      // Everyone supports fontSize
      expect(testProps(['fontSize'])).to.equal(true);
      // kebab-case should work too
    });


    afterEach(function() {
      req.undef('mStyle');
      req.undef('cssToDOM');
      req.undef('contains');
      req.undef('testProps');
      req.undef('ModernizrProto');
      req.undef('nativeTestProps');
    });
  });

  after(function() {
    cleanup();
  });
});
