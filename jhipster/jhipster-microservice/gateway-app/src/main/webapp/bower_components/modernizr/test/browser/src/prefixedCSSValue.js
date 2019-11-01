describe('prefixedCSSValue', function() {
  var prefixedCSSValue;
  var cleanup;

  before(function(done) {

    var req = requirejs.config({
      context: Math.random().toString().slice(2),
      baseUrl: '../src',
      paths: {cleanup: '../test/cleanup'}
    });

    req(['cleanup', 'prefixedCSSValue'], function(_cleanup, _prefixedCSSValue) {
      prefixedCSSValue = _prefixedCSSValue;
      cleanup = _cleanup;
      done();
    });
  });

  it('returns the value when it is valid', function() {
    expect(prefixedCSSValue('display', 'block')).to.equal('block');
  });

  it('returns false when the prop is not supported', function() {
    expect(prefixedCSSValue('fart', 'block')).to.equal(false);
  });

  it('returns false when value is not supported', function() {
    expect(prefixedCSSValue('display', 'fart')).to.equal(false);
  });

  after(function() {
    cleanup();
  });
});
