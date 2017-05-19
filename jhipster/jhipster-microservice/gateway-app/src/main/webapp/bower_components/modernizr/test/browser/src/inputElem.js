describe('inputElem', function() {
  var inputElem;
  var cleanup;

  before(function(done) {

    var req = requirejs.config({
      context: Math.random().toString().slice(2),
      baseUrl: '../src',
      paths: {cleanup: '../test/cleanup'}
    });

    req(['inputElem', 'cleanup'], function(_inputElem, _cleanup) {
      inputElem = _inputElem;
      cleanup = _cleanup;
      done();
    });
  });

  it('returns an input element', function() {
    expect(inputElem.nodeName).to.equal('INPUT');
  });

  after(function() {
    cleanup();
  });
});
