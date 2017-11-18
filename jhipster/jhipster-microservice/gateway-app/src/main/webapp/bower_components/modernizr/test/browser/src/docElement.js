describe('docElement', function() {
  var docElement;
  var cleanup;

  before(function(done) {

    var req = requirejs.config({
      context: Math.random().toString().slice(2),
      baseUrl: '../src',
      paths: {cleanup: '../test/cleanup'}
    });

    req(['docElement', 'cleanup'], function(_docElement, _cleanup) {
      docElement = _docElement;
      cleanup = _cleanup;
      done();
    });
  });

  it('is an alias to document.documentElement', function() {
    expect(docElement).to.equal(document.documentElement);
  });

  it('is valid and correct', function() {
    expect(docElement).to.equal(document.getElementsByTagName('html')[0]);
  });

  after(function() {
    cleanup();
  });
});
