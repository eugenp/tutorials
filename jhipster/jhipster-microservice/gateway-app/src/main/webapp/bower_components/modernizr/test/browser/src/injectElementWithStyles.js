describe('injectElementWithStyles', function() {
  var injectElementWithStyles;
  var originalBody;
  var parentNode;
  var cleanup;
  var sinon;

  before(function(done) {

    define('package', [], function() {return {};});

    var req = requirejs.config({
      context: Math.random().toString().slice(2),
      baseUrl: '../src',
      paths: {
        cleanup: '../test/cleanup',
        sinon: '../test/js/lib/sinon'
      }
    });

    req(['injectElementWithStyles', 'sinon', 'cleanup'], function(_injectElementWithStyles, _sinon, _cleanup) {
      injectElementWithStyles = _injectElementWithStyles;
      sinon = _sinon;
      cleanup = _cleanup;
      done();
    });
  });

  it('is a function', function() {
    expect(injectElementWithStyles).to.be.a('function');
  });

  it('styles an injected element', function() {
    var callback = function() {
      var modernizr = document.getElementById('modernizr');
      return modernizr.clientWidth === 10;
    };

    var result = injectElementWithStyles('#modernizr{width: 10px}', callback);
    expect(result).to.be(true);
  });

  it('passes back a rule matching what we gave it', function(done) {
    var style = '#modernizr{width: 10px}';
    var callback = function(elm, rule) {
      expect(rule).to.be(style);
      done();
    };

    injectElementWithStyles(style, callback);
  });

  it('passes the #modernizr element in the callback', function(done) {
    var style = '#modernizr{width: 10px}';
    var callback = function(elm) {
      expect(elm.id).to.be('modernizr');
      done();
    };

    injectElementWithStyles(style, callback);
  });

  it('deletes an element after the test', function() {
    expect(document.getElementById('modernizr')).to.be(null);

    var callback = function() {
      expect(document.getElementById('modernizr')).to.not.be(null);
    };

    injectElementWithStyles('', callback);

    expect(document.getElementById('modernizr')).to.be(null);
  });

  it('creates multiple nodes when requested', function(done) {

    var callback = function(elm) {
      expect(elm.childNodes.length).to.be(9);
      done();
    };

    injectElementWithStyles('', callback, 8);
  });

  it('names multiple nodes based on `testname` when configured', function(done) {

    var callback = function() {
      var test = document.getElementById('test');
      var element = document.getElementById('element');

      expect(test).to.not.be(null);
      expect(element).to.not.be(null);
      done();
    };

    injectElementWithStyles('', callback, 2, ['test', 'element']);
  });


  it('copes with a fake body', function(done) {

    originalBody = document.body;
    parentNode = originalBody.parentNode;

    var callback = function() {
      var body = document.body;

      expect(body.fake).to.be(true);

      // injectElementWithStyles overrides the background value for fake body to
      // an empty string, however old IE changes this to the following string.
      if (body.style.background != 'none transparent scroll repeat 0% 0%') {
        expect(body.style.background.length).to.be(0);
      }

      expect(body.style.overflow).to.be('hidden');
      done();
    };

    expect(document.body.fake).to.not.be(true);
    parentNode.removeChild(originalBody);
    injectElementWithStyles('', callback);

  });

  after(function() {
    if (!$.contains(parentNode, originalBody)) {
      parentNode.appendChild(originalBody);
    }
    expect(document.body.fake).to.not.be(true);
    cleanup();
  });
});
