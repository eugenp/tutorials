describe('classes', function() {
  var classes;
  var cleanup;

  before(function(done) {

    var req = requirejs.config({
      context: Math.random().toString().slice(2),
      baseUrl: '../src',
      paths: {cleanup: '../test/cleanup'}
    });

    req(['classes', 'cleanup'], function(_classes, _cleanup) {
      classes = _classes;
      cleanup = _cleanup;
      done();
    });
  });


  it('is an array', function() {
    expect(classes).to.be.an('array');
  });

  after(function() {
    cleanup();
  });
});
