describe('ModernizrProto', function() {
  var ModernizrProto;
  var cleanup;
  var tests;
  var req;

  before(function() {
    define('package', [], function() {return {version: 'v9999'};});

    req = requirejs.config({
      context: Math.random().toString().slice(2),
      baseUrl: '../src',
      paths: {cleanup: '../test/cleanup'}
    });

    req(['cleanup'], function(_cleanup) {
      cleanup = _cleanup;
    });
  });

  beforeEach(function(done) {

    tests = [];
    define('tests', function() {return tests;});

    req(['ModernizrProto', 'tests'], function(_ModernizrProto, _tests) {
      ModernizrProto = _ModernizrProto;
      tests = _tests;
      done();
    });

  });

  afterEach(function() {
    req.undef('tests');
    req.undef('ModernizrProto');
  });

  it('should define a version', function() {
    expect(ModernizrProto._version).to.be.a('string');
  });

  it('should set a default config', function() {
    var config = ModernizrProto._config;

    expect(config.classPrefix).to.be.a('string');
    expect(config.enableClasses).to.be.a('boolean');
    expect(config.enableJSClass).to.be.a('boolean');
    expect(config.usePrefixes).to.be.a('boolean');
  });

  it('should define a working stub for `Modernizr.on`', function(done) {
    ModernizrProto.on('fakeDetect', done);
  });

  it('should define `Modernizr.addTest` and have it pushed to the internal `tests` queue', function() {
    var name = 'fakeDetect';
    var fn = function fakeCallback() {};
    var options = {opt: 'opt'};

    ModernizrProto.addTest(name, fn, options);
    expect(tests).to.have.length(1);
    expect(tests[0].name).to.be(name);
    expect(tests[0].fn).to.be(fn);
    expect(tests[0].options).to.be(options);
  });

  it('should define `Modernizr.addAsyncTest` and have it pushed to the internal `tests` queue', function() {
    var fn = function fakeCallback() {};

    ModernizrProto.addAsyncTest(fn);
    expect(tests).to.have.length(1);
    expect(tests[0].fn).to.be(fn);
  });

  after(function() {
    cleanup();
  });

});
