describe('testRunner', function() {
  var Modernizr = {};
  var cleanup;
  var req;

  before(function(done) {

    req = requirejs.config({
      context: Math.random().toString().slice(2),
      baseUrl: '../src',
      paths: {cleanup: '../test/cleanup'}
    });

    define('Modernizr', [], function() {return Modernizr;});
    define('tests', [], function() {
      return [
        {
          'name': 'fakeFn',
          'fn': function() {return true;}
        }, {
          'name': 'fakeBool',
          'fn': false
        }, {
          'name': 'newFakeDetect',
          'fn': function() {return 10;},
          'options': {
            'aliases': ['fakeDetect']
          }
        }, {
          'name': 'fake',
          'fn': true
        }, {
          'name': 'fake.detect',
          'fn': 99
        }, {
          'name': 'fakeBoolDeep',
          /* jshint -W053 */
          'fn': new Boolean(true)
          /* jshint +W053 */
        }, {
          'name': 'fakeBoolDeep.detect',
          'fn': false
        }, {
          'fn': function() {
            return !!'async test';
          }
        }
      ];
    });

    req(['testRunner', 'cleanup'], function(_testRunner, _cleanup) {
      _testRunner();
      cleanup = _cleanup;
      done();
    });

  });

  it('returns true if fn returns true', function() {
    expect('fakefn' in Modernizr).to.be(true);
  });

  it('returns true if fn is a bool', function() {
    expect(Modernizr.fakebool).to.be(false);
  });

  it('assigns aliased values', function() {
    expect(Modernizr.newfakedetect).to.equal(Modernizr.fakedetect);
  });

  it('deep assignments are valid', function() {
    expect(Modernizr.fake.detect).to.equal(99);
  });

  it('deep assignments are true with bool base', function() {
    expect(Modernizr.fakebooldeep instanceof Boolean).to.be(true);
    expect(Modernizr.fakebooldeep.detect).to.equal(false);
  });

  afterEach(function() {
    req.undef('testRunner');
    req.undef('Modernizr');
  });

  after(function() {
    cleanup();
  });
});
