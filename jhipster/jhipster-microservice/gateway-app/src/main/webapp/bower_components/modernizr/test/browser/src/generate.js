describe('generate', function() {
  var generate;
  var cleanup;

  before(function(done) {

    define('package', [], function() {return {};});
    define('Modernizr', [], function() {return {};});
    define('testRunner', [], function() {return {};});
    define('lodash', [], function() {return window._;});
    define('ModernizrProto', [], function() {return {};});

    var req = requirejs.config({
      context: Math.random().toString().slice(2),
      baseUrl: '../src',
      paths: {cleanup: '../test/cleanup'}
    });

    req(['generate', 'cleanup'], function(_generate, _cleanup) {
      generate = _generate;
      cleanup = _cleanup;
      done();
    });
  });

  it('is a function', function() {
    expect(generate).to.be.a('function');
  });

  it('does not blow up when no config is given', function() {
    expect(function() {generate();}).to.not.throwError();
  });

  it('defines `feature-detects` if it is not on the config already', function() {
    var config = {};
    generate(config);
    expect(config['feature-detects']).to.not.be(undefined);
  });

  it('does not overwrite `feature-detects` if it is defined already', function() {
    var config = {'feature-detects': ['fake']};
    generate(config);
    expect(config['feature-detects'][0]).to.be('fake');
  });

  describe('outputs feature detects when they are requested', function() {

    it('with amd prefix', function() {
      var config = {'feature-detects': ['test/fake']};
      var output = generate(config);
      expect(output).to.contain('test/fake');
    });

    it('without amd prefix', function() {
      var config = {'feature-detects': ['fake']};
      var output = generate(config);
      expect(output).to.contain('test/fake');
    });
  });

  it('adds `setClasses` and `classes` when defined', function() {
    var config = {'options': ['setClasses']};
    var output = generate(config);
    expect(output).to.contain('setClasses", "classes');
  });

  it('adds options when defined', function() {
    var config = {'options': ['fakeOption']};
    var output = generate(config);
    expect(output).to.contain('fakeOption');
  });

  it('only adds one of the shivs if both are defined', function() {
    var config = {'options': ['html5shiv', 'html5printshiv']};
    var output = generate(config);
    expect(output).to.contain('html5printshiv');
    expect(output).to.not.contain('html5shiv');
  });

  it('outputs a valid function', function() {
    var output = generate({});
    var stashedRequire = window.require;
    window.require = function() {};
    expect(function() {eval(output);}).to.not.throwError();
    window.require = stashedRequire;
  });

  it('outputs a valid function when minified', function() {
    var output = generate({minify: true});
    var stashedRequire = window.require;
    window.require = function() {};
    expect(function() {eval(output);}).to.not.throwError();
    window.require = stashedRequire;
  });

  it('does not modify options', function() {
    var config = {'options': ['setClasses']};
    generate(config);
    expect(config.options).to.eql(['setClasses']);
  });

  after(function() {
    cleanup();
  });
});
