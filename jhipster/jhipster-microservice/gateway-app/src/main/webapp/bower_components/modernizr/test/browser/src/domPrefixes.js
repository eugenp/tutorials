describe('domPrefixes', function() {
  var req;

  var setup = function(done, bool) {
    return (function() {
      define('ModernizrProto', [], function() {return {_config: {usePrefixes: bool}};});

      req(['domPrefixes'], function(_domPrefixes) {
        domPrefixes = _domPrefixes;
        done();
      });
    })();
  };

  var teardown = function() {
    domPrefixes = undefined;
    req.undef('domPrefixes');
    req.undef('ModernizrProto');
  };
  var domPrefixes;
  var cleanup;

  before(function(done) {
    define('package', [], function() {return {version: 'v9999'};});

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
      req(['domPrefixes'], function(domPrefixes) {
        expect(domPrefixes).to.not.have.length(0);
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
      req(['domPrefixes'], function(domPrefixes) {
        expect(domPrefixes).to.have.length(0);
        done();
      });
    });
  });

  after(function() {
    cleanup();
  });
});
