describe('roundedEquals', function() {
  var roundedEquals;
  var cleanup;

  before(function(done) {

    var req = requirejs.config({
      context: Math.random().toString().slice(2),
      baseUrl: '../src',
      paths: {cleanup: '../test/cleanup'}
    });

    req(['roundedEquals', 'cleanup'], function(_roundedEquals, _cleanup) {
      roundedEquals = _roundedEquals;
      cleanup = _cleanup;
      done();
    });
  });

  it('works', function() {
    expect(roundedEquals(1, 2)).to.be(true);
    expect(roundedEquals(2, 2)).to.be(true);
    expect(roundedEquals(3, 2)).to.be(true);
  });

  after(function() {
    cleanup();
  });
});
