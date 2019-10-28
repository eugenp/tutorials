define(['ModernizrProto', 'testPropsAll', 'cssToDOM', 'atRule'], function(ModernizrProto, testPropsAll, cssToDOM, atRule) {
  /**
   * prefixed returns the prefixed or nonprefixed property name variant of your input
   *
   * @memberof Modernizr
   * @name Modernizr.prefixed
   * @optionName Modernizr.prefixed()
   * @optionProp prefixed
   * @access public
   * @function prefixed
   * @param {string} prop - String name of the property to test for
   * @param {object} [obj] - An object to test for the prefixed properties on
   * @param {HTMLElement} [elem] - An element used to test specific properties against
   * @returns {string|false} The string representing the (possibly prefixed) valid
   * version of the property, or `false` when it is unsupported.
   * @example
   *
   * Modernizr.prefixed takes a string css value in the DOM style camelCase (as
   * opposed to the css style kebab-case) form and returns the (possibly prefixed)
   * version of that property that the browser actually supports.
   *
   * For example, in older Firefox...
   * ```js
   * prefixed('boxSizing')
   * ```
   * returns 'MozBoxSizing'
   *
   * In newer Firefox, as well as any other browser that support the unprefixed
   * version would simply return `boxSizing`. Any browser that does not support
   * the property at all, it will return `false`.
   *
   * By default, prefixed is checked against a DOM element. If you want to check
   * for a property on another object, just pass it as a second argument
   *
   * ```js
   * var rAF = prefixed('requestAnimationFrame', window);
   *
   * raf(function() {
   *  renderFunction();
   * })
   * ```
   *
   * Note that this will return _the actual function_ - not the name of the function.
   * If you need the actual name of the property, pass in `false` as a third argument
   *
   * ```js
   * var rAFProp = prefixed('requestAnimationFrame', window, false);
   *
   * rafProp === 'WebkitRequestAnimationFrame' // in older webkit
   * ```
   *
   * One common use case for prefixed is if you're trying to determine which transition
   * end event to bind to, you might do something like...
   * ```js
   * var transEndEventNames = {
   *     'WebkitTransition' : 'webkitTransitionEnd', * Saf 6, Android Browser
   *     'MozTransition'    : 'transitionend',       * only for FF < 15
   *     'transition'       : 'transitionend'        * IE10, Opera, Chrome, FF 15+, Saf 7+
   * };
   *
   * var transEndEventName = transEndEventNames[ Modernizr.prefixed('transition') ];
   * ```
   *
   * If you want a similar lookup, but in kebab-case, you can use [prefixedCSS](#modernizr-prefixedcss).
   */

  var prefixed = ModernizrProto.prefixed = function(prop, obj, elem) {
    if (prop.indexOf('@') === 0) {
      return atRule(prop);
    }

    if (prop.indexOf('-') != -1) {
      // Convert kebab-case to camelCase
      prop = cssToDOM(prop);
    }
    if (!obj) {
      return testPropsAll(prop, 'pfx');
    } else {
      // Testing DOM property e.g. Modernizr.prefixed('requestAnimationFrame', window) // 'mozRequestAnimationFrame'
      return testPropsAll(prop, obj, elem);
    }
  };

  return prefixed;
});
