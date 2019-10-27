describe('setClasses', function() {
  var setClasses;
  var cleanup;
  var elm;
  var req;

  var setup = function(done, config, defaultClassName) {
    return (function() {
      var modConfig = {_config: config};

      elm = document.createElement('div');
      if (defaultClassName) {
        elm.className = defaultClassName;
      }

      define('Modernizr', [], function() {return modConfig;});
      define('docElement', [], function() {return elm;});

      req(['setClasses'], function(_setClasses) {
        setClasses = _setClasses;
        done();
      });
    })();
  };
  var teardown = function() {
    setClasses = undefined;
    req.undef('setClasses');
    req.undef('docElement');
    req.undef('Modernizr');
  };


  before(function(done) {
    define('package', [], function() {return {};});

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

  describe('cssClasses disabled', function() {
    before(function(done) {
      setup(done, {
        'classPrefix': 'fake',
        'enableClasses': false
      });
    });

    it('should not add anything', function(done) {
      req(['setClasses'], function(setClasses) {
        setClasses(['detect']);
        expect(elm.className).to.not.contain('fakedetect');
        done();
      });
    });

    after(teardown);
  });

  describe('cssClasses enabled, with prefix', function() {
    before(function(done) {
      setup(done, {
        'classPrefix': 'fake',
        'enableClasses': true
      });
    });

    it('adds a class with a prefix', function(done) {
      req(['setClasses'], function(setClasses) {
        setClasses(['detect']);
        expect(elm.className).to.contain('fakedetect');
        done();
      });
    });

    after(teardown);
  });

  describe('cssClasses enabled, without prefix', function() {
    before(function(done) {
      setup(done, {
        'enableClasses': true
      });
    });

    after(teardown);

    it('adds a class without a prefix', function(done) {
      req(['setClasses'], function(setClasses) {
        setClasses(['detect']);
        expect(elm.className).to.contain('detect');
        done();
      });
    });
  });


  describe('enableJSClass enabled, with prefix', function() {
    before(function(done) {
      setup(done, {
        'classPrefix': 'fake',
        'enableClasses': true,
        'enableJSClass': true
      }, ' fakeno-js +fakeno-js fakeno-js- i-has-fakeno-js');
    });

    it('changes the `js` class, and adds a class with a prefix', function(done) {
      req(['setClasses'], function(setClasses) {
        var classNames = elm.className.split(' ');
        expect(classNames).to.contain('fakeno-js');
        setClasses(['detect']);

        classNames = elm.className.split(' ');
        expect(elm.className).to.contain('fakejs');
        expect(elm.className).to.contain('+fakeno-js');
        expect(elm.className).to.contain('fakeno-js-');
        expect(elm.className).to.contain('i-has-fakeno-js');
        expect(elm.className).to.contain('fakedetect');
        done();
      });
    });

    after(teardown);

  });

  describe('enableJSClass enabled, without prefix', function() {
    before(function(done) {
      setup(done, {
        'enableJSClass': true,
        'enableClasses': true
      }, ' no-js +no-js no-js- i-has-no-js');
    });

    after(teardown);

    it('changes the `js` class, and adds a class without a prefix', function(done) {
      req(['setClasses'], function(setClasses) {
        var classNames = elm.className.split(' ');
        expect(classNames).to.contain('no-js');
        setClasses(['detect']);

        classNames = elm.className.split(' ');
        expect(elm.className).to.contain('js');
        expect(elm.className).to.contain('+no-js');
        expect(elm.className).to.contain('no-js-');
        expect(elm.className).to.contain('i-has-no-js');
        expect(elm.className).to.contain('detect');
        done();
      });
    });

  });

  after(function() {
    cleanup();
  });
});
