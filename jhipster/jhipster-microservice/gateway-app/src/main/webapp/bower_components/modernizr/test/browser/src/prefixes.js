describe('prefixes', function() {
  var setup = function(done, bool) {
    return (function() {
      define('ModernizrProto', [], function() {return {_config: {usePrefixes: bool}};});

      req(['prefixes'], function(_prefixes) {
        prefixes = _prefixes;
        done();
      });
    })();
  };
  var teardown = function() {
    prefixes = undefined;
    req.undef('prefixes');
    req.undef('ModernizrProto');
  };
  var prefixes;
  var cleanup;
  var req;


  before(function(done) {
    define('package', [], function() {return {};});

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

  describe('prefixes enabled', function() {
    before(function(done) {
      setup(done, true);
    });

    after(teardown);

    it('returns prefixes', function(done) {
      req(['prefixes'], function(prefixes) {
        expect(prefixes).to.be.an('array');
        expect(prefixes).to.not.have.length(0);
        done();
      });
    });
  });

  describe('prefixes disabled', function() {
    before(function(done) {
      setup(done, false);
    });

    after(teardown);

    it('returns no prefixes', function(done) {
      req(['prefixes'], function(prefixes) {
        expect(prefixes).to.be.an('array');
        expect(prefixes).to.have.length(0);
        done();
      });
    });
  });

  after(function() {
    cleanup();
  });
});
