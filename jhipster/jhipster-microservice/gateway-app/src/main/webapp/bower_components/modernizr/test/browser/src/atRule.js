describe('atRule', function() {
  var atRule;
  var cleanup;

  before(function(done) {

    if (window.CSSRule) {
      window.CSSRule.MODERNIZR_FAKE_RULE = 999;
    }

    var prefixes = ['Modernizr'];

    define('cssomPrefixes', [], function() {return prefixes;});
    define('package', [], function() {return {};});

    var req = requirejs.config({
      context: Math.random().toString().slice(2),
      baseUrl: '../src',
      paths: {cleanup: '../test/cleanup'}
    });

    req(['atRule', 'cleanup'], function(_atRule, _cleanup) {
      atRule = _atRule;
      cleanup = _cleanup;
      done();
    });
  });

    it('returns undefined when the browser does not support CSSRule', function() {
      var ref = window.CSSRule;
      window.CSSRule = undefined;

      expect(atRule('charset')).to.be(undefined);

      window.CSSRule = ref;
    });

    if (window.CSSRule) {
      it('detects `@rule`s', function() {
        expect(atRule('charset')).to.be('@charset');
      });

      it('returns false when a property is not given', function() {
        expect(atRule()).to.be(false);
      });

      it('returns false when a property is not found', function() {
        expect(atRule('fart')).to.be(false);
      });

      it('detects prefixed properties', function() {
        expect(atRule('fake')).to.be('@-modernizr-fake');
      });
    }

  after(function() {
    if (window.CSSRule) {
      delete window.CSSRule.MODERNIZR_FAKE_RULE;
    }
    cleanup();
  });
});
