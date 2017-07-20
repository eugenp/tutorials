describe('Modernizr Base', function() {
  var modernizrBase;
  var cleanup;

  before(function(done) {

    define('package', [], function() {return {};});

    var req = requirejs.config({
      context: Math.random().toString().slice(2),
      baseUrl: '../src',
      paths: {cleanup: '../test/cleanup'}
    });

    req(['Modernizr', 'cleanup'], function(_ModernizrBase, _cleanup) {
      modernizrBase = _ModernizrBase;
      cleanup = _cleanup;
      done();
    });
  });

  it('should return an object', function() {
    expect(modernizrBase).to.be.an('object');
  });

  after(function() {
    cleanup();
  });
});
