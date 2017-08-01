describe('prefixed', function() {
  var ModernizrProto;
  var testPropsAll;
  var prefixed;
  var cssToDOM;
  var cleanup;
  var atRule;
  var sinon;
  var req;

  before(function(done) {

    req = requirejs.config({
      context: Math.random().toString().slice(2),
      baseUrl: '../src',
      paths: {
        sinon: '../test/js/lib/sinon',
        cleanup: '../test/cleanup'
      }
    });

    req(['sinon', 'cleanup'], function(_sinon, _cleanup) {
      sinon = _sinon;
      cleanup = _cleanup;
      done();
    });
  });

  beforeEach(function(done) {

    testPropsAll = sinon.spy(function() {return 'fakeRule';});
    cssToDOM = sinon.spy(function() {return 'fakeRule';});
    atRule = sinon.spy(function() {return '@fakeRule';});
    ModernizrProto = {};

    define('ModernizrProto', [], function() {return ModernizrProto;});
    define('testPropsAll', [], function() {return testPropsAll;});
    define('cssToDOM', [], function() {return cssToDOM;});
    define('atRule', [], function() {return atRule;});


    req(['prefixed'], function(_prefixed) {
      prefixed = _prefixed;

      done();
    });
  });

  it('is a function', function() {
    expect(prefixed).to.be.a('function');
  });

  it('creates a reference on `ModernizrProto`', function() {
    expect(prefixed).to.equal(ModernizrProto.prefixed);
  });

  it('uses atRule to lookup rules starting with "@"', function() {
    expect(prefixed('@fakeRule')).to.equal('@fakeRule');
    expect(atRule.calledOnce).to.be(true);
  });

  it('uses cssToDOM to lookup rules with "-"', function() {
    expect(prefixed('fake-rule')).to.equal('fakeRule');
    expect(cssToDOM.calledOnce).to.be(true);
    expect(testPropsAll.calledOnce).to.be(true);
  });

  it('looks up properties on an element, when one is provided', function() {
    var elm = document.createElement('div');
    expect(prefixed('children', elm)).to.equal('fakeRule');
    expect(testPropsAll.calledOnce).to.be(true);
  });

  afterEach(function() {
    req.undef('ModernizrProto');
    req.undef('testPropsAll');
    req.undef('cssToDOM');
    req.undef('prefixed');
    req.undef('atRule');
  });

  after(function() {
    cleanup();
  });
});
