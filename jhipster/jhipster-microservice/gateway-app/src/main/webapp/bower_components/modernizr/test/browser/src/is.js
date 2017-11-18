describe('is', function() {
  var cleanup;
  var is;

  before(function(done) {

    var req = requirejs.config({
      context: Math.random().toString().slice(2),
      baseUrl: '../src',
      paths: {cleanup: '../test/cleanup'}
    });

    req(['is', 'cleanup'], function(_is, _cleanup) {
      is = _is;
      cleanup = _cleanup;
      done();
    });
  });


  it('is a function', function() {
    expect(is).to.be.a('function');
  });

  it('recognizes all types', function() {
    var _undefined = is(undefined, 'undefined');
    var _func = is(function() {}, 'function');
    var _bool = is(true, 'boolean');
    var _null = is(null, 'object');
    var _str = is('1', 'string');

    expect(_undefined).to.be(true);
    expect(_func).to.be(true);
    expect(_bool).to.be(true);
    expect(_null).to.be(true);
    expect(_str).to.be(true);
  });

  after(function() {
    cleanup();
  });
});
