describe('mq', function() {
  var injectElementWithStyles;
  var mq;
  var cleanup;
  var sinon;
  var req;

  var media = window.matchMedia || (function() {
    // adapted from jQuery Mobile
    // http://git.io/NFWo

    var bool;
    var docElem = document.documentElement;
    var refNode = docElem.firstElementChild || docElem.firstChild;
    // fakeBody required for <FF4 when executed in <head>
    var fakeBody = document.createElement('body');
    var div = document.createElement('div');

    div.id = 'mq-test-1';
    div.style.cssText = 'position:absolute;top:-100em';
    fakeBody.style.background = 'none';
    fakeBody.appendChild(div);

    return function(q) {

      div.innerHTML = '&shy;<style media="' + q + '"> #mq-test-1 { width: 42px; }</style>';

      docElem.insertBefore(fakeBody, refNode);
      bool = div.offsetWidth === 42;
      docElem.removeChild(fakeBody);

      return {
        matches: bool,
        media: q
      };
    };
  })();

  before(function(done) {

    req = requirejs.config({
      context: Math.random().toString().slice(2),
      baseUrl: '../src',
      paths: {
        sinon: '../test/js/lib/sinon',
        cleanup: '../test/cleanup'
      }
    });

    req(['injectElementWithStyles', 'cleanup', 'sinon'], function(_injectElementWithStyles, _cleanup, _sinon) {
      injectElementWithStyles = _injectElementWithStyles;
      cleanup = _cleanup;
      sinon = _sinon;
      done();
    });

  });

  if (window.matchMedia || window.msMatchMedia) {
    describe('matchMedia version', function() {
      before(function(done) {
        req(['mq'], function(_mq) {
          mq = _mq;
          done();
        });
      });

      it('works', function() {
        expect(mq('only screen')).to.equal(media('only screen').matches);
        expect(mq('only fake rule')).to.equal(media('only fake rule').matches);
      });
    });
  } else {
    describe('fallback version', function() {

      before(function(done) {
        injectElementWithStyles = sinon.spy(injectElementWithStyles);
        req.undef('injectElementWithStyles');
        req.undef('mq');

        define('injectElementWithStyles', [], function() {return injectElementWithStyles;});

        req(['mq'], function(_mq) {
          mq = _mq;
          done();
        });
      });

      it('works', function() {

        expect(mq('only screen')).to.equal(media('only screen').matches);
        expect(mq('only fake rule')).to.equal(media('only fake rule').matches);
        expect(injectElementWithStyles.called).to.be(true);
      });

    });

  }

  afterEach(function() {
    req.undef('mq');
  });

  after(function() {
    cleanup();
  });
});
