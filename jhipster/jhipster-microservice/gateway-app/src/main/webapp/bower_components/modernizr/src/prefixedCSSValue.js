define(['ModernizrProto', 'domPrefixes', 'createElement'], function(ModernizrProto, domPrefixes, createElement) {
  /**
   * prefixedCSSValue is a way test for prefixed css properties (e.g. display: -webkit-flex)
   *
   * @memberof Modernizr
   * @name Modernizr.prefixedCSSValue
   * @optionName Modernizr.prefixedCSSValue()
   * @optionProp prefixedCSSValue
   * @access public
   * @function prefixedCSSValue
   * @param {string} prop - String name of the property to test for
   * @param {string} value - String value of the non prefixed version of the value you want to test for
   * @returns {string|false} The string representing the (possibly prefixed)
   * valid version of the property, or `false` when it is unsupported.
   * @example
   *
   * `Modernizr.prefixedCSSValue` is a way test for prefixed css properties (e.g. display: -webkit-flex)
   *
   * ```js
   * Modernizr.prefixedCSSValue('background', 'linear-gradient(left, red, red)')
   * ```
   *
   */

  var prefixedCSSValue = function(prop, value) {
    var result = false;
    var elem = createElement('div');
    var style = elem.style;

    if (prop in style) {
      var i = domPrefixes.length;

      style[prop] = value;
      result = style[prop];

      while (i-- && !result) {
        style[prop] = '-' + domPrefixes[i] + '-' + value;
        result = style[prop];
      }
    }

    if (result === '') {
      result = false;
    }

    return result;
  };

  ModernizrProto.prefixedCSSValue = prefixedCSSValue;
  return prefixedCSSValue;
});
