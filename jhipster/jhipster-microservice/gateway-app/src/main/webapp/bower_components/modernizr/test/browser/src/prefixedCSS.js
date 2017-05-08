describe('prefixedCSS', function() {
  var ModernizrProto = {_config: {usePrefixes: true}, _q: []};
  var prefixedCSS;
  var cleanup;

  before(function(done) {
    define('ModernizrProto', [], function() {return ModernizrProto;});
    define('package', [], function() {return {version: 'v9999'};});


    var req = requirejs.config({
      context: Math.random().toString().slice(2),
      baseUrl: '../src',
      paths: {cleanup: '../test/cleanup'}
    });

    req(['cleanup', 'prefixedCSS'], function(_cleanup, _prefixedCSS) {
      prefixedCSS = _prefixedCSS;
      cleanup = _cleanup;
      done();
    });
  });

  it('creates a reference on `ModernizrProto`', function() {
    expect(prefixedCSS).to.equal(ModernizrProto.prefixedCSS);
  });

  it('returns false on unknown properties', function() {
    expect(prefixedCSS('fart')).to.equal(false);
  });

  it('returns known values without prefix', function() {
    expect(prefixedCSS('display')).to.equal('display');
  });

  after(function() {
    cleanup();
  });
});
