describe('createElement', function() {
  var createElement;
  var cleanup;

  before(function(done) {

    var req = requirejs.config({
      context: Math.random().toString().slice(2),
      baseUrl: '../src',
      paths: {cleanup: '../test/cleanup'}
    });

    req(['createElement', 'cleanup'], function(_createElement, _cleanup) {
      createElement = _createElement;
      cleanup = _cleanup;
      done();
    });
  });


  it('is a function', function() {
    expect(createElement).to.be.a('function');
  });

  it('creates an element', function() {
    var element = createElement('modernizr');
    expect(element.nodeName.toUpperCase()).to.be('MODERNIZR');
  });

  after(function() {
    cleanup();
  });
});
