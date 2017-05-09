describe('slice', function() {
  var slice;
  var cleanup;

  before(function(done) {

    var req = requirejs.config({
      context: Math.random().toString().slice(2),
      baseUrl: '../src',
      paths: {cleanup: '../test/cleanup'}
    });

    req(['slice', 'cleanup'], function(_slice, _cleanup) {
      slice = _slice;
      cleanup = _cleanup;
      done();
    });
  });

  it('returns an instance of `slice`', function() {
    expect(slice).to.equal([].slice);
  });

  after(function() {
    cleanup();
  });
});
