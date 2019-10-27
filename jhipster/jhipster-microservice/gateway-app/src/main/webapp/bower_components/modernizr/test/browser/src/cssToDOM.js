describe('cssToDOM', function() {
  var cssToDOM;
  var cleanup;

  before(function(done) {

    var req = requirejs.config({
      context: Math.random().toString().slice(2),
      baseUrl: '../src',
      paths: {cleanup: '../test/cleanup'}
    });

    req(['cssToDOM', 'cleanup'], function(_cssToDOM, _cleanup) {
      cssToDOM = _cssToDOM;
      cleanup = _cleanup;
      done();
    });
  });

  it('converts kebab to camel', function() {
    expect(cssToDOM('fake-detect')).to.equal('fakeDetect');
  });

  after(function() {
    cleanup();
  });
});
