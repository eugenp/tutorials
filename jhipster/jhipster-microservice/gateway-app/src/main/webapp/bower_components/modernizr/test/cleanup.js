/*jshint: globals __coverage__ */
if (typeof define !== 'function') {
  var requirejs = require('requirejs');
}

define([], function() {
  return function() {
    var contexts = requirejs.s.contexts;

    for (var context in contexts) {
      if (contexts.hasOwnProperty(context)) {
        var defined = contexts[context].defined;
        for (var module in defined) {
          if (defined.hasOwnProperty(module) && !module.match(/cleanup|sinon/)) {
            requirejs.undef(module);
          }
        }
      }
    }
  };
});
