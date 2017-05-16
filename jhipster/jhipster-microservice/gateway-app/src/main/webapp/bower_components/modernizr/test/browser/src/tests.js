describe('tests', function() {
  var tests;
  var cleanup;

  before(function(done) {

    var req = requirejs.config({
      context: Math.random().toString().slice(2),
      baseUrl: '../src',
      paths: {cleanup: '../test/cleanup'}
    });

    req(['tests', 'cleanup'], function(_tests, _cleanup) {
      tests = _tests;
      cleanup = _cleanup;
      done();
    });
  });


  it('is an array', function() {
    expect(tests).to.be.an('array');
  });

  after(function() {
    cleanup();
  });
});
