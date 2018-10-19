/* global uaparse */
window.caniusecb = function(caniuse) {
  // So Phantom doesn't kill the caniuse.com matching exit out as it's useless anyway within PhantomJS
  if (window._phantom) {
    return;
  }
  describe('caniuse', function() {

    var ua = uaparse(navigator.userAgent);
    var unusedModernizr = [];
    var unusedCaniuse = _.keys(caniuse.data);
    var map = {
      adownload: 'download',
      ambientlight: 'ambient-light',
      apng: 'apng',
      appearance: 'css-appearance',
      applicationcache: 'offline-apps',
      audio: 'audio',
      backgroundblendmode: 'css-backgroundblendmode',
      blobconstructor: 'blobbuilder',
      bloburls: 'bloburls',
      borderimage: 'border-image',
      borderradius: 'border-radius',
      boxshadow: 'css-boxshadow',
      boxsizing: 'css3-boxsizing',
      canvas: 'canvas',
      canvasblending: 'canvas-blending',
      canvastext: 'canvas-text',
      classlist: 'classlist',
      contenteditable: 'contenteditable',
      contextmenu: 'menu',
      cors: 'cors',
      cssanimations: 'css-animation',
      csscalc: 'calc',
      csscolumns: 'multicolumn',
      cssfilters: 'css-filters',
      cssgradients: 'css-gradients',
      csspointerevents: 'pointer-events',
      csspositionsticky: 'css-sticky',
      cssreflections: 'css-reflections',
      cssremunit: 'rem',
      cssresize: 'css-resize',
      csstransforms3d: 'transforms3d',
      csstransforms: 'transforms2d',
      csstransitions: 'css-transitions',
      cssvhunit: 'viewport-units',
      cssvmaxunit: 'viewport-units',
      cssvminunit: 'viewport-units',
      cssvwunit: 'viewport-units',
      datalistelem: 'datalist',
      datauri: 'datauri',
      details: 'details',
      deviceorientation: 'deviceorientation',
      displaytable: 'css-table',
      eventsource: 'eventsource',
      filereader: 'fileapi',
      filesystem: 'filesystem',
      flexbox: 'flexbox',
      flexboxlegacy: 'flexbox',
      flexboxtweener: 'flexbox',
      fontface: 'fontface',
      formvalidationapi: 'form-validation',
      fullscreen: 'fullscreen',
      gamepads: 'gamepad',
      geolocation: 'geolocation',
      getrandomvalues: 'getrandomvalues',
      getusermedia: 'stream',
      hashchange: 'hashchange',
      hidden: 'hidden',
      history: 'history',
      hsla: 'css3-colors',
      htmlimports: 'imports',
      indexeddb: 'indexeddb',
      inlinesvg: 'svg-html5',
      inputtypes: 'forms',
      jpegxr: 'jpegxr',
      jpeg2000: 'jpeg2000',
      json: 'json',
      localstorage: 'namevalue-storage',
      mathml: 'mathml',
      mediaqueries: 'css-mediaqueries',
      meter: 'progress',
      multiplebgs: 'multibackgrounds',
      mutationobserver: 'mutationobserver',
      notification: 'notifications',
      objectfit: 'object-fit',
      opacity: 'css-opacity',
      pagevisibility: 'pagevisibility',
      performance: 'nav-timing',
      picture: 'picture',
      postmessage: 'x-doc-messaging',
      progressbar: 'progress',
      promises: 'promises',
      queryselector: 'queryselector',
      regions: 'css-regions',
      requestanimationframe: 'requestanimationframe',
      rgba: 'css3-colors',
      ruby: 'ruby',
      sandbox: 'iframe-sandbox',
      scriptasync: 'script-async',
      scriptdefer: 'script-defer',
      seamless: 'iframe-seamless',
      shapes: 'css-shapes',
      sharedworkers: 'sharedworkers',
      smil: 'svg-smil',
      strictmode: 'use-strict',
      stylescoped: 'style-scoped',
      supports: 'css-featurequeries',
      svg: 'svg',
      svgasimg: 'svg-img',
      svgfilters: 'svg-filters',
      template: 'template',
      textalignlast: 'css-text-align-last',
      textshadow: 'css-textshadow',
      typedarrays: 'typedarrays',
      unicoderange: 'font-unicode-range',
      userselect: 'user-select-none',
      vibrate: 'vibration',
      video: 'video',
      webanimations: 'web-animation',
      webaudio: 'audio-api',
      webgl: 'webgl',
      webp: 'webp',
      websockets: 'websockets',
      websqldatabase: 'sql-storage',
      webworkers: 'webworkers',
      willchange: 'will-change',
      xhr2: 'xhr2'
    };

    // translate 'y' 'n' or 'a' into a boolean that Modernizr uses
    function bool(result) {
      // To handle correctly things like 'y #1' or 'a #2'
      if (result.indexOf('y') === 0 || result.indexOf('a') === 0) {
        return true;
      }
      // 'p' is for polyfill
      if (result.indexOf('n') === 0 || result.indexOf('p') === 0) {
        return false;
      }
      throw 'unknown return value from can i use - ' + result;
    }

    function testify(o) {

      var ciubool = bool(o.caniuseResult);

      /**************************************************************
      * `o.caniuseResult` maps to the caniuse `stat` property
      *
      * y - (Y)es, supported by default
      * a - (A)lmost supported (aka Partial support)
      * n - (N)o support, or disabled by default
      * p - No support, but has (P)olyfill
      * u - Support (u)nknown
      * x - Requires prefi(x) to work
      * d - (D)isabled by default (need to enable flag or something)
      **************************************************************/

      // caniuse says audio/video are yes/no, Modernizr has more detail which we'll dumb down.
      if (_.includes(['video', 'audio', 'webglextensions'], o.feature)) {
        o.result = o.result.valueOf();
      }

      // webgl `partial` support means that not all users with these browsers have
      // WebGL access, so we just ignore this test, and only check if the browser
      // either fully supports or does not support
      if (o.feature === 'webgl' && o.caniuseResult.indexOf('a') === 0) {
        return;
      }

      // change the *documented* false positives
      if (!ciubool && (o.feature == 'textshadow' && o.browser == 'Firefox' && o.version == 3)) {
        ciubool = o.fp = true;
      }

      // firefox does not support unicode-range without a flag
      if (o.feature === 'unicoderange' && o.caniuseResult.indexOf('y') === 0 && o.browser == 'Firefox' && o.version <= 40) {
        return;
      }

      // firefox only supports web animation when a flag is enabled, which we
      // don't do on sauce
      if (o.feature === 'webanimations' && o.caniuseResult.indexOf('a d') === 0) {
        return;
      }

      // firefox only supports the `url` version of css-filters, which we don't
      // consider support
      if (o.feature === 'cssfilters' && o.browser == 'Firefox' && o.caniuseResult.indexOf('a') === 0) {
        return;
      }

      // before 4.0, firefox only supports MathML on XHTML documents. Since we
      // don't run inside of one, we will have a technically false negative
      if (o.feature === 'mathml' && o.browser == 'Firefox' && o.version < 4) {
        return;
      }

      // caniuse bundles viewport units, all of which work in IE 9+, save for vmax
      // we skip this comparison with a version gate, hoping its fixed in later versions.
      if (o.feature === 'cssvmaxunit' && o.caniuseResult.indexOf('a') === 0) {
        return;
      }

      // safari 5 does not support the `FileReader` API, which we test as a requirement
      if (o.feature === 'filereader' && o.caniuseResult.indexOf('a') === 0) {
        return;
      }

      // safari 5 and 6 only support the old version of WebSockets, which breaks most servers.
      // As a result, we mark it as not supported, and ignore the caniuse match
      if (o.feature === 'websockets' && o.caniuseResult.indexOf('a') === 0) {
        return;
      }

      // safari 7 recognizes the `seamless` attribute but does not actually support it
      if (o.feature === 'seamless' && o.browser === 'Safari' && o.version === 7) {
        return;
      }


      // caniuse counts a partial support for CORS via the XDomainRequest,
      // but thats not really cors - so skip the comparison.
      if (o.feature === 'cors' && o.browser == 'IE' && o.version < 10) {
        return;
      }

      // Opera 12 has a false positive for `defer`
      if (o.feature === 'scriptdefer' && o.browser == 'Opera' && parseInt(o.version, 10) === 12) {
        return;
      }

      // caniuse bundles forms into one big wad of detects. we check to see if their result matches
      // atleast some of our inputtypes.
      if (o.ciufeature === 'forms') {
        return it('Caniuse result for forms matches Modernizr\'s result for inputtypes', function() {
          return expect(ciubool).to.be(_.some(Modernizr.inputtypes, function(modernizrResult) {
            return modernizrResult;
          }));
        });
      }


      // we breakout flexbox sniffing into three seperate detects, which borks the caniuse mappings,
      // since no browser supports all three
      if (o.ciufeature === 'flexbox') {
        return it('Caniuse result for flexbox matches Modernizr\'s result for flexbox', function() {
          return expect([
            Modernizr.flexbox,
            Modernizr.flexboxlegacy,
            Modernizr.flexboxtweener
          ]).to.contain(ciubool);
        });
      }

      // caniuse bundles progress and meter elements, so we do too.
      if (_.includes(['meter', 'progressbar'], o.feature)) {
        return it('Caniuse result for ' + o.ciufeature + ' matches Modernizr\'s result for ' + o.feature, function() {
          return expect([
            Modernizr.meter,
            Modernizr.progressbar
          ]).to.contain(ciubool);
        });
      }

      // caniuse counts `filter` opacity as partial support - we don't.
      if (o.feature === 'opacity' && o.browser === 'IE' && o.version < 9) {
        return;
      }

      // if caniuse gave us a 'partial', lets let it pass with a note.
      if (o.caniuseResult.indexOf('a') === 0) {
        return it(o.browser + o.version + ': Caniuse reported partial support for ' + o.ciufeature, function() {
          var modernizrResult = o.result instanceof Boolean ? o.result.valueOf() : !!o.result;
          expect(ciubool).to.equal(modernizrResult);
        });
      }

      // where we actually do most our assertions
      it(o.browser + o.version + ': Caniuse result for ' + o.ciufeature + ' matches Modernizr\'s ' + (o.fp ? '*false positive*' : 'result') + ' for ' + o.feature, function() {
        var modernizrResult = o.result instanceof Boolean ? o.result.valueOf() : !!o.result;
        expect(ciubool).to.equal(modernizrResult);
      });
    }

    _.forEach(Modernizr, function(result, feature) {

      var caniuseFeatureName = map[feature];

      if (_.isUndefined(caniuseFeatureName)) {
        return unusedModernizr.push(feature);
      }

      var caniuseFeatureData = caniuse.data[caniuseFeatureName];

      if (caniuseFeatureData === undefined) {
        throw 'unknown key of caniusedata - ' + caniuseFeatureName;
      }

      unusedCaniuse = _.without(unusedCaniuse, caniuseFeatureName);

      // get results for this feature for all versions of this browser
      var browserResults = caniuseFeatureData.stats[ua.family.toLowerCase()];

      // let's get our versions in order..
      var minorver   = ua.minor &&                                  // caniuse doesn't use two digit minors
        ua.minor.toString().replace(/(\d)\d$/, '$1'); // but opera does.


      var majorminor = (ua.major + '.' + minorver)
        // opera gets grouped in some cases by caniuse
        .replace(/(9\.(6|5))/ , ua.family == 'opera' ? '9.5-9.6'   : '$1')
        .replace(/(10\.(0|1))/, ua.family == 'opera' ? '10.0-10.1' : '$1');

      var mmResult   = browserResults[majorminor];
      var mResult    = browserResults[ua.major];


      // check it against the major.minor: eg. FF 3.6
      if (mmResult && mmResult != 'u') { // 'y' 'n' or 'a'

        // data ends w/ ` x` if its still prefixed in the imp
        mmResult = mmResult.replace(' x', '');

        // match it against our data.
        testify({
          feature: feature,
          ciufeature: caniuseFeatureName,
          result: Modernizr[feature],
          caniuseResult: mmResult,
          browser: ua.family,
          version: majorminor
        });

        return; // don't check the major version
      }

      // check it against just the major version: eg. FF 3
      if (mResult) {

        // unknown support from caniuse... He would probably like to know our data, though!
        if (mResult == 'u') {
          return;
        }

        // data ends w/ ` x` if its still prefixed in the imp
        mResult = mResult.replace(' x', '');

        testify({
          feature: feature,
          ciufeature: caniuseFeatureName,
          result: Modernizr[feature],
          caniuseResult: mResult,
          browser: ua.family,
          version: ua.major
        });
      }
    });

  });
};
