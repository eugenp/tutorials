define(['ModernizrProto'], function(ModernizrProto) {
  /**
   * Previously, Modernizr.load was an alias for yepnope. Since yepnope was
   * deprecated, we removed it as well. It is not available on the website builder,
   * this is only included as an improved warning to those who build a custom
   * version locally.
   *
   * @memberof Modernizr
   * @name Modernizr.load
   * @access private
   * @function load
   *
   */

  var err = function() {};
  var warn = function() {};

  if (window.console) {
    err = function() {
      var method = console.error ? 'error' : 'log';
      window.console[method].apply(window.console, Array.prototype.slice.call(arguments));
    };

    warn = function() {
      var method = console.warn ? 'warn' : 'log';
      window.console[method].apply(window.console, Array.prototype.slice.call(arguments));
    };
  }

  ModernizrProto.load = function() {
    if ('yepnope' in window) {
      warn('yepnope.js (aka Modernizr.load) is no longer included as part of Modernizr. yepnope appears to be available on the page, so we’ll use it to handle this call to Modernizr.load, but please update your code to use yepnope directly.\n See http://github.com/Modernizr/Modernizr/issues/1182 for more information.');
      window.yepnope.apply(window, [].slice.call(arguments, 0));
    } else {
      err('yepnope.js (aka Modernizr.load) is no longer included as part of Modernizr. Get it from http://yepnopejs.com. See http://github.com/Modernizr/Modernizr/issues/1182 for more information.');
    }
  };

});
