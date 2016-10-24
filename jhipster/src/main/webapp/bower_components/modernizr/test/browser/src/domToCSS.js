describe('domToCSS', function() {
  var domToCSS;
  var cleanup;

  before(function(done) {

    var req = requirejs.config({
      context: Math.random().toString().slice(2),
      baseUrl: '../src',
      paths: {cleanup: '../test/cleanup'}
    });

    req(['domToCSS', 'cleanup'], function(_domToCSS, _cleanup) {
      domToCSS = _domToCSS;
      cleanup = _cleanup;
      done();
    });
  });

  it('converts kebab to camel', function() {
    expect(domToCSS('fakeDetect')).to.equal('fake-detect');
  });

  after(function() {
    cleanup();
  });
});
