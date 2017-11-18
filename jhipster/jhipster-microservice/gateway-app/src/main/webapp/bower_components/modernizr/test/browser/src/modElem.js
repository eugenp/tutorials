describe('modElem', function() {
  var Modernizr;
  var modElem;
  var cleanup;
  var req;

  beforeEach(function(done) {
    Modernizr = {_q: []};
    define('Modernizr', [], function() {return Modernizr;});

    req = requirejs.config({
      context: Math.random().toString().slice(2),
      baseUrl: '../src',
      paths: {cleanup: '../test/cleanup'}
    });

    req(['modElem', 'cleanup'], function(_modElem, _cleanup) {
      modElem = _modElem;
      cleanup = _cleanup;
      done();
    });
  });

  it('returns an object with an `elem` prop', function() {
    expect(modElem).to.be.an('object');
    expect(modElem.elem).to.not.be(undefined);
    expect(modElem.elem.nodeName.toUpperCase()).to.be('MODERNIZR');
  });

  it('pushes a function onto the Modernizr._q', function() {
    expect(Modernizr._q[0]).to.be.a('function');
  });

  it('deletes modElem.style after the `_q` runs', function() {
    expect(modElem.elem).to.not.be(undefined);
    Modernizr._q[0]();
    expect(modElem.elem).to.be(undefined);
  });

  afterEach(function() {
    req.undef('Modernizr');
    req.undef('modElem');
  });

  after(function() {
    cleanup();
  });
});
