describe('omPrefixes', function() {
  var omPrefixes;
  var cleanup;

  before(function(done) {

    var req = requirejs.config({
      context: Math.random().toString().slice(2),
      baseUrl: '../src',
      paths: {cleanup: '../test/cleanup'}
    });

    req(['omPrefixes', 'cleanup'], function(_omPrefixes, _cleanup) {
      omPrefixes = _omPrefixes;
      cleanup = _cleanup;
      done();
    });
  });

  it('returns a string', function() {
    expect(omPrefixes).to.be.a('string');
    expect(omPrefixes.length).to.not.be(0);
  });

  after(function() {
    cleanup();
  });
});
