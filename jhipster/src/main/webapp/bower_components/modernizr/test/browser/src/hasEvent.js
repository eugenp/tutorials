describe('hasEvent', function() {
  var hasEvent;
  var cleanup;
  var req;

  before(function(done) {

    define('package', [], function() {return {};});

    req = requirejs.config({
      context: Math.random().toString().slice(2),
      baseUrl: '../src',
      paths: {cleanup: '../test/cleanup'}
    });

    req(['hasEvent', 'cleanup'], function(_hasEvent, _cleanup) {
      hasEvent = _hasEvent;
      cleanup = _cleanup;
      done();
    });
  });

  it('allows you to pass an element to test against', function() {
    expect(hasEvent('click'), document.createElement('a')).to.be(true);
  });

  it('allows you to pass an string name for an element to test against', function() {
    expect(hasEvent('click', 'a')).to.be(true);
  });

  it('allows you to pass something other then a DOM element or string', function() {
    expect(hasEvent('click', document)).to.be(true);
  });


  it('returns false when no event name is provided', function() {
    expect(hasEvent()).to.be(false);
  });


  it('returns true when the event exists', function() {
    expect(hasEvent('click')).to.be(true);
  });

  it('returns false when the event does not exists', function() {
    expect(hasEvent('fart')).to.be(false);
  });

  describe('fallback', function() {
    var onblur = document.documentElement.onblur;
    var called;

    before(function(done) {
      try {
        // IE 7 fails if we try to delete properties that don't exist
        delete document.documentElement.onblur;
      } catch (e) {}

      req.undef('hasEvent');
      req.undef('createElement');

      define('createElement', [], function() {
        return function() {

          var elm = typeof document.createElement !== 'function' ?
            document.createElement(arguments[0]) :
            document.createElement.apply(document, arguments);

          // logic added to get simulate old firefox's behavior
          if (!called) {
            try {
              delete elm.onclick;
            } catch (e) {}

            elm.setAttribute = undefined;
            called = true;
          }
          return elm;
        };
      });

      req(['hasEvent'], function(_hasEvent) {
        hasEvent = _hasEvent;
        done();
      });

    });

    it('fallsback properly with no element', function() {
      expect(hasEvent('click')).to.be(true);
    });

    it('fallsback properly when testing with a global element', function() {
      expect(hasEvent('click', document)).to.be(true);
    });

    after(function() {
      document.documentElement.onblur = onblur;
    });

  });

  after(function() {
    cleanup();
  });
});
