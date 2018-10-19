describe('load', function() {
  var ModernizrProto;
  var cleanup;
  var sinon;
  var load;


  before(function(done) {

    define('ModernizrProto', [], function() {return {};});

    var req = requirejs.config({
      context: Math.random().toString().slice(2),
      baseUrl: '../src',
      paths: {
        sinon: '../test/js/lib/sinon',
        cleanup: '../test/cleanup'
      }
    });

    req(['ModernizrProto', 'sinon', 'load', 'cleanup'], function(_ModernizrProto, _sinon, _load, _cleanup) {
      ModernizrProto = _ModernizrProto;
      cleanup = _cleanup;
      sinon = _sinon;
      load = _load;
      done();
    });
  });

  it('creates a reference on `ModernizrProto`', function() {
    expect(ModernizrProto.load).to.be.a('function');
  });


  if (window.console && console.error) {
    describe('errors', function() {

      it('reports that `load` has been removed', function() {
        var err = console.error;
        console.error = sinon.spy();
        ModernizrProto.load();
        expect(console.error.calledOnce).to.be(true);
        console.error = err;
      });

      it('fallsback to log when `console.error` is not defined', function() {
        var err;
        var log;
        err = console.error;
        log = console.log;
        console.error = undefined;
        console.log = sinon.spy();

        ModernizrProto.load();

        expect(console.log.calledOnce).to.be(true);

        console.log = log;
        console.error = err;
      });

    });
  }

  if (window.console && console.log) {

    describe('warnings', function() {
      var yepnope;

      beforeEach(function() {
        yepnope = sinon.spy();
        window.yepnope = yepnope;
      });

      it('reports that `load` has been removed, but still calls if yepnope is on the page', function() {
        var warn = console.warn;
        console.warn = sinon.spy();
        ModernizrProto.load();
        expect(console.warn.calledOnce).to.be(true);
        expect(yepnope.calledOnce).to.be(true);
        console.warn = warn;
      });

      it('fallsback to log when `console.warn` is not defined', function() {
        var warn;
        var log;
        warn = console.warn;
        log = console.log;
        console.warn = undefined;
        console.log = sinon.spy();

        ModernizrProto.load();

        expect(console.log.calledOnce).to.be(true);
        expect(yepnope.calledOnce).to.be(true);

        console.log = log;
        console.warn = warn;
      });

      afterEach(function() {
        yepnope = undefined;
        // ie <= 8 doesn't support deleting window properties,
        //  so we fallback to setting it to undefiend
        try {
          delete window.yepnope;
        } catch (e) {
          window.yepnope = undefined;
        }
      });

    });

  }

  if (!window.console) {
    describe('browsers without a console', function() {

      it('doesn\'t blow up when calling console.error', function() {
        expect(ModernizrProto.load).to.not.throwError();
      });

      it('doesn\'t blow up when calling console.warn', function() {
        var yepnope = sinon.spy();
        window.yepnope = yepnope;
        expect(ModernizrProto.load).to.not.throwError();
        expect(yepnope.calledOnce).to.be(true);
      });

    });
  }

  after(function() {
    cleanup();
  });
});
