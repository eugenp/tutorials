/* This is a copy of the regular `BootstrapHandler.js` in the flow-server
   module, but with the following modifications:
   - The main function is exported as an ES module for lazy initialization.
   - Application configuration is passed as a parameter instead of using
     replacement placeholders as in the regular bootstrapping.
   - It reuses `Vaadin.Flow.clients` if exists.
   - Fixed lint errors.
 */
const init = function (appInitResponse) {
  window.Vaadin = window.Vaadin || {};
  window.Vaadin.Flow = window.Vaadin.Flow || {};

  var apps = {};
  var widgetsets = {};

  var log;
  if (typeof window.console === undefined || !window.location.search.match(/[&?]debug(&|$)/)) {
    /* If no console.log present, just use a no-op */
    log = function () {};
  } else if (typeof window.console.log === 'function') {
    /* If it's a function, use it with apply */
    log = function () {
      window.console.log.apply(window.console, arguments);
    };
  } else {
    /* In IE, its a native function for which apply is not defined, but it works
     without a proper 'this' reference */
    log = window.console.log;
  }

  var isInitializedInDom = function (appId) {
    var appDiv = document.getElementById(appId);
    if (!appDiv) {
      return false;
    }
    for (var i = 0; i < appDiv.childElementCount; i++) {
      var className = appDiv.childNodes[i].className;
      /* If the app div contains a child with the class
      'v-app-loading' we have only received the HTML
      but not yet started the widget set
      (UIConnector removes the v-app-loading div). */
      if (className && className.indexOf('v-app-loading') != -1) {
        return false;
      }
    }
    return true;
  };

  /*
   * Needed for Testbench compatibility, but prevents any Vaadin 7 app from
   * bootstrapping unless the legacy vaadinBootstrap.js file is loaded before
   * this script.
   */
  window.Vaadin = window.Vaadin || {};
  window.Vaadin.Flow = window.Vaadin.Flow || {};

  /*
   * Needed for wrapping custom javascript functionality in the components (i.e. connectors)
   */
  window.Vaadin.Flow.tryCatchWrapper = function (originalFunction, component) {
    return function () {
      try {
        // eslint-disable-next-line
        const result = originalFunction.apply(this, arguments);
        return result;
      } catch (error) {
        console.error(
          `There seems to be an error in ${component}:
${error.message}
Please submit an issue to https://github.com/vaadin/flow-components/issues/new/choose`
        );
      }
    };
  };

  if (!window.Vaadin.Flow.initApplication) {
    window.Vaadin.Flow.clients = window.Vaadin.Flow.clients || {};

    window.Vaadin.Flow.initApplication = function (appId, config) {
      var testbenchId = appId.replace(/-\d+$/, '');

      if (apps[appId]) {
        if (
          window.Vaadin &&
          window.Vaadin.Flow &&
          window.Vaadin.Flow.clients &&
          window.Vaadin.Flow.clients[testbenchId] &&
          window.Vaadin.Flow.clients[testbenchId].initializing
        ) {
          throw new Error('Application ' + appId + ' is already being initialized');
        }
        if (isInitializedInDom(appId)) {
          throw new Error('Application ' + appId + ' already initialized');
        }
      }

      log('init application', appId, config);

      window.Vaadin.Flow.clients[testbenchId] = {
        isActive: function () {
          return true;
        },
        initializing: true,
        productionMode: mode
      };

      var getConfig = function (name) {
        var value = config[name];
        return value;
      };

      /* Export public data */
      var app = {
        getConfig: getConfig
      };
      apps[appId] = app;

      if (!window.name) {
        window.name = appId + '-' + Math.random();
      }

      var widgetset = 'client';
      widgetsets[widgetset] = {
        pendingApps: []
      };
      if (widgetsets[widgetset].callback) {
        log('Starting from bootstrap', appId);
        widgetsets[widgetset].callback(appId);
      } else {
        log('Setting pending startup', appId);
        widgetsets[widgetset].pendingApps.push(appId);
      }

      return app;
    };
    window.Vaadin.Flow.getAppIds = function () {
      var ids = [];
      for (var id in apps) {
        if (Object.prototype.hasOwnProperty.call(apps, id)) {
          ids.push(id);
        }
      }
      return ids;
    };
    window.Vaadin.Flow.getApp = function (appId) {
      return apps[appId];
    };
    window.Vaadin.Flow.registerWidgetset = function (widgetset, callback) {
      log('Widgetset registered', widgetset);
      var ws = widgetsets[widgetset];
      if (ws && ws.pendingApps) {
        ws.callback = callback;
        for (var i = 0; i < ws.pendingApps.length; i++) {
          var appId = ws.pendingApps[i];
          log('Starting from register widgetset', appId);
          callback(appId);
        }
        ws.pendingApps = null;
      }
    };
    window.Vaadin.Flow.getBrowserDetailsParameters = function () {
      var params = {};

      /* Screen height and width */
      params['v-sh'] = window.screen.height;
      params['v-sw'] = window.screen.width;
      /* Browser window dimensions */
      params['v-wh'] = window.innerHeight;
      params['v-ww'] = window.innerWidth;
      /* Body element dimensions */
      params['v-bh'] = document.body.clientHeight;
      params['v-bw'] = document.body.clientWidth;

      /* Current time */
      var date = new Date();
      params['v-curdate'] = date.getTime();

      /* Current timezone offset (including DST shift) */
      var tzo1 = date.getTimezoneOffset();

      /* Compare the current tz offset with the first offset from the end
         of the year that differs --- if less that, we are in DST, otherwise
         we are in normal time */
      var dstDiff = 0;
      var rawTzo = tzo1;
      for (var m = 12; m > 0; m--) {
        date.setUTCMonth(m);
        var tzo2 = date.getTimezoneOffset();
        if (tzo1 != tzo2) {
          dstDiff = tzo1 > tzo2 ? tzo1 - tzo2 : tzo2 - tzo1;
          rawTzo = tzo1 > tzo2 ? tzo1 : tzo2;
          break;
        }
      }

      /* Time zone offset */
      params['v-tzo'] = tzo1;

      /* DST difference */
      params['v-dstd'] = dstDiff;

      /* Time zone offset without DST */
      params['v-rtzo'] = rawTzo;

      /* DST in effect? */
      params['v-dston'] = tzo1 != rawTzo;

      /* Time zone id (if available) */
      try {
        params['v-tzid'] = Intl.DateTimeFormat().resolvedOptions().timeZone;
      } catch (err) {
        params['v-tzid'] = '';
      }

      /* Window name */
      if (window.name) {
        params['v-wn'] = window.name;
      }

      /* Detect touch device support */
      var supportsTouch = false;
      try {
        document.createEvent('TouchEvent');
        supportsTouch = true;
      } catch (e) {
        /* Chrome and IE10 touch detection */
        supportsTouch = 'ontouchstart' in window || typeof navigator.msMaxTouchPoints !== 'undefined';
      }
      params['v-td'] = supportsTouch;

      /* Device Pixel Ratio */
      params['v-pr'] = window.devicePixelRatio;

      if (navigator.platform) {
        params['v-np'] = navigator.platform;
      }

      /* Stringify each value (they are parsed on the server side) */
      Object.keys(params).forEach(function (key) {
        var value = params[key];
        if (typeof value !== 'undefined') {
          params[key] = value.toString();
        }
      });
      return params;
    };
  }

  log('Flow bootstrap loaded');
  if (appInitResponse.appConfig.productionMode && typeof window.__gwtStatsEvent != 'function') {
    window.Vaadin.Flow.gwtStatsEvents = [];
    window.__gwtStatsEvent = function (event) {
      window.Vaadin.Flow.gwtStatsEvents.push(event);
      return true;
    };
  }
  var config = appInitResponse.appConfig;
  var mode = appInitResponse.appConfig.productionMode;
  window.Vaadin.Flow.initApplication(config.appId, config);
};

export { init };
