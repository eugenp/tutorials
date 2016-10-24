describe('addTest', function() {
  var ModernizrProto;
  var setClasses;
  var Modernizr;
  var addTest;
  var cleanup;
  var sinon;
  var req;

  before(function(done) {

    req = requirejs.config({
      context: Math.random().toString().slice(2),
      baseUrl: '../src',
      paths: {
        cleanup: '../test/cleanup',
        sinon: '../test/js/lib/sinon'
      }
    });

    req(['cleanup', 'sinon'], function(_cleanup, _sinon) {
      cleanup = _cleanup;
      sinon = _sinon;
      done();
    });

  });

  beforeEach(function(done) {

    ModernizrProto = {};
    Modernizr = {_q: [], _config:  {}};
    setClasses = sinon.spy();

    define('ModernizrProto', [], function() {return ModernizrProto;});
    define('Modernizr', [], function() {return Modernizr;});
    define('setClasses', [], function() {return setClasses;});
    define('package', [], function() {return {};});

    req(['addTest'], function(_addTest) {
      addTest = _addTest;
      done();
    });
  });

  afterEach(function() {
    req.undef('ModernizrProto');
    req.undef('setClasses');
    req.undef('Modernizr');
    req.undef('package');
    req.undef('addTest');
  });

  describe('setup', function() {

    it('adds an object for test listeners', function() {
      expect(ModernizrProto._l).to.be.an('object');
    });

    it('should define the `ModernizrProto._trigger` function', function() {
      expect(ModernizrProto._trigger).to.be.an('function');
    });

    it('should push the Modernizr.addTest definition to the `_q`', function() {
      expect(Modernizr._q).to.have.length(1);
      expect(Modernizr._q[0]).to.be.a('function');
    });

    it('should define Modernizr.addTest at the end of the _q', function() {
      Modernizr._q[0]();
      expect(ModernizrProto.addTest).to.be(addTest);
    });
  });

  describe('Modernizr.on', function() {
    var fakeDetect = function() {};

    it('keeps track of requests', function() {
        ModernizrProto.on('fakeDetect', fakeDetect);
        expect(ModernizrProto._l.fakeDetect).to.be.an('array');
        expect(ModernizrProto._l.fakeDetect[0]).to.be(fakeDetect);
    });

    it('does not recreate the queue with duplicate requests', function() {
        ModernizrProto.on('fakeDetect', fakeDetect);
        ModernizrProto.on('fakeDetect', fakeDetect);
        expect(ModernizrProto._l.fakeDetect.length).to.be(2);
    });


    it('triggers results if the detect already ran', function(done) {
        Modernizr.fakeDetect = 'fake';
        Modernizr._trigger = sinon.spy();
        ModernizrProto.on('fakeDetect', fakeDetect);

        setTimeout(function() {
          expect(Modernizr._trigger.calledOnce).to.be(true);
          expect(Modernizr._trigger.calledWith('fakeDetect', Modernizr.fakeDetect)).to.be(true);
          done();
        }, 0);
    });

  });

  describe('Modernizr._trigger', function() {

    it('skips the callback if it does not exist', function() {
      expect(function() {ModernizrProto._trigger('fakeDetect');}).to.not.throwError();
    });

    it('runs the listener calledback if it does exist', function(done) {
      var spy = sinon.spy();

      ModernizrProto.on('fakeDetect', spy);
      ModernizrProto._trigger('fakeDetect', 'fakeRes');

      setTimeout(function() {
        expect(spy.calledOnce).to.be(true);
        done();
      });
    });

    it('deletes the listener after it runs', function(done) {

      ModernizrProto.on('fakeDetect', function() {});

      expect(ModernizrProto._l.fakeDetect).to.be.an('array');

      ModernizrProto._trigger('fakeDetect', 'fakeRes');

      setTimeout(function() {
        expect(ModernizrProto._l.fakeDetect).to.be(undefined);
        done();
      });
    });
  });

  describe('Modernizr.addTest', function() {

    beforeEach(function() {
      Modernizr._trigger = sinon.spy();
      Modernizr._trigger = sinon.spy();
      expect(Modernizr.fakedetect).to.be(undefined);
      expect(Modernizr.fake).to.be(undefined);
      expect(Modernizr.detect).to.be(undefined);
    });

    it('sets the proper bool on the Modernizr object with a function', function() {
      addTest('fakedetect', function() {return true;});
      expect(Modernizr.fakedetect).to.be(true);
    });

    it('sets the proper bool on the Modernizr object with a bool', function() {
      addTest('fakedetect', false);
      expect(Modernizr.fakedetect).to.be(false);
    });

    it('does not cast to a bool on the Modernizr object with a truthy value', function() {
      addTest('fakedetect', function() {return 100;});
      expect(Modernizr.fakedetect).to.be(100);
    });

    it('sets a true class for a true value', function() {
      addTest('fakedetect', function() {return 100;});
      expect(setClasses.callCount).to.be(1);
      expect(setClasses.calledWith(['fakedetect'])).to.be(true);
    });

    it('sets a truthy class for a truthy value', function() {
      addTest('fakedetect', function() {return 100;});
      expect(setClasses.callCount).to.be(1);
      expect(setClasses.calledWith(['fakedetect'])).to.be(true);
    });

    it('sets a negative class for a false value', function() {
      addTest('fakedetect', function() {return false;});
      expect(setClasses.callCount).to.be(1);
      expect(setClasses.calledWith(['no-fakedetect'])).to.be(true);
    });

    it('sets a negative class for a falsey value', function() {
      addTest('fakedetect', function() {return undefined;});
      expect(setClasses.callCount).to.be(1);
      expect(setClasses.calledWith(['no-fakedetect'])).to.be(true);
    });

    it('does not cast to a bool on the Modernizr object with a falsy value', function() {
      addTest('fakedetect', function() {return undefined;});
      expect('fakedetect' in Modernizr).to.be(true);
      expect(Modernizr.fakedetect).to.be(undefined);
    });

    it('forces detect names are lowercase', function() {
      addTest('FaKeDeTeCt', true);
      expect(Modernizr.fakedetect).to.be(true);
    });

    it('supports nested properties with a bool base', function() {
      /* jshint -W053 */
      addTest('fake', new Boolean(true));
      /* jshint +W053 */
      addTest('fake.detect', true);
      expect(Modernizr.fake).to.be.an('object');
      expect(Modernizr.fake.detect).to.be(true);
    });

    it('supports nested properties', function() {
      addTest('fake', true);
      addTest('fake.detect', true);
      expect(Modernizr.fake).to.be.an('object');
      expect(Modernizr.fake.detect).to.be(true);
    });

    it('does not overwrite values once they are set', function() {
      addTest('fakeDetect', false);
      expect(Modernizr.fakedetect).to.be(false);
      expect(Modernizr._trigger.calledOnce).to.be(true);

      addTest('fakeDetect', true);
      expect(Modernizr.fakedetect).to.be(false);
      expect(Modernizr._trigger.calledOnce).to.be(true);
    });

    it('allows feature to be an object of features', function() {
      addTest({fake: true, detect: false});
      expect(Modernizr.fake).to.be(true);
      expect(Modernizr.detect).to.be(false);
      expect(setClasses.callCount).to.be(2);
      expect(setClasses.calledWith(['fake'])).to.be(true);
      expect(setClasses.calledWith(['no-detect'])).to.be(true);
    });

    it('properly filters out monkey patched object properties', function() {
      var noop = function() {};
      Object.prototype.MOD_FAKE_VALUE = noop;
      var config = {detect: false};

      expect(config.MOD_FAKE_VALUE).to.be(noop);
      addTest(config);

      delete Object.prototype.MOD_FAKE_VALUE;

      expect(Modernizr.MOD_FAKE_VALUE).to.be(undefined);
      expect(Modernizr.mod_fake_value).to.be(undefined);
      expect(Modernizr.detect).to.be(false);
      expect(setClasses.callCount).to.be(1);
    });

    it('returns an instance of Modernizr for chaining', function() {
      expect(addTest('fakeDetect', true)).to.be(Modernizr);
    });

  });

  after(function() {
    cleanup();
  });
});
