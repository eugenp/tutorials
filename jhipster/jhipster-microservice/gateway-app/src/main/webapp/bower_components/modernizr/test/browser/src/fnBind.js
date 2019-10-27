describe('fnBind', function() {
  var fnBind;
  var cleanup;

  before(function(done) {

    var req = requirejs.config({
      context: Math.random().toString().slice(2),
      baseUrl: '../src',
      paths: {cleanup: '../test/cleanup'}
    });

    req(['fnBind', 'cleanup'], function(_fnBind, _cleanup) {
      fnBind = _fnBind;
      cleanup = _cleanup;
      done();
    });
  });

  it('binds to `this`', function() {
    var foo = {x: 1};
    var bar = function() {
      return this.x;
    };

    expect(fnBind(bar, foo)()).to.equal(1);
  });

  after(function() {
    cleanup();
  });
});
