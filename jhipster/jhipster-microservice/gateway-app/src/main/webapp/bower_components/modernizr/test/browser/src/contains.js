describe('contains', function() {
  var contains;
  var cleanup;

  before(function(done) {

    var req = requirejs.config({
      context: Math.random().toString().slice(2),
      baseUrl: '../src',
      paths: {cleanup: '../test/cleanup'}
    });

    req(['contains', 'cleanup'], function(_contains, _cleanup) {
      contains = _contains;
      cleanup = _cleanup;
      done();
    });
  });

  it('finds substrings', function() {
    expect(contains('fakeDetect', 'akeDet')).to.be(true);
  });

  after(function() {
    cleanup();
  });
});
