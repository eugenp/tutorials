describe('testDOMProps', function() {
  var elm = document.createElement('div');
  var testDOMProps;
  var cleanup;
  var req;

  before(function(done) {

    req = requirejs.config({
      context: Math.random().toString().slice(2),
      baseUrl: '../src',
      paths: {cleanup: '../test/cleanup'}
    });

    req(['cleanup'], function(_cleanup) {
      cleanup = _cleanup;
      done();
    });

  });

  beforeEach(function(done) {
    req(['testDOMProps'], function(_testDOMProps) {
      testDOMProps = _testDOMProps;
      done();
    });
  });

  it('returns known values', function() {
    expect(testDOMProps(['clientHeight'], elm)).to.equal(elm.clientHeight);
  });

  it('returns false for unknown values', function() {
    expect(testDOMProps(['fart'], elm)).to.equal(false);
  });

  it('bind a value to to the object', function() {
    elm.answer = function() {return 42;};
    expect(testDOMProps(['answer'], elm)()).to.equal(elm.answer());
  });

  it('bind a value to the element, if it is provided', function() {
    elm.answer = function() {return 42;};
    expect(testDOMProps(['answer'], {}, elm)).to.equal(false);
  });

  it('return the property name as a string if elem is false', function() {
    elm.answer = function() {return 42;};
    expect(testDOMProps(['answer'], {'answer': 42}, false)).to.equal('answer');
  });

  after(function() {
    cleanup();
  });
});
