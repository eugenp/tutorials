describe('hasOwnProp', function() {
  var hasOwnProp;
  var cleanup;

  before(function(done) {

    var req = requirejs.config({
      context: Math.random().toString().slice(2),
      baseUrl: '../src',
      paths: {cleanup: '../test/cleanup'}
    });

    req(['hasOwnProp', 'cleanup'], function(_hasOwnProp, _cleanup) {
      hasOwnProp = _hasOwnProp;
      cleanup = _cleanup;
      done();
    });
  });

  it('is a function', function() {
    expect(hasOwnProp).to.be.an('function');
  });

  it('works', function() {
    var obj = {};
    obj.prop = true;
    expect(hasOwnProp(obj, 'prop')).to.be(true);
  });

  after(function() {
    cleanup();
  });
});
