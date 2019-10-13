define(['lodash'], function(_) {
  /**
   * generate creates the base version of a Modernizr build, before the r.js
   * optimizer actually generates the final code
   *
   * @access private
   * @function generate
   * @param {object} [config] - A configuration object
   * @param {array} [config.options[]] - An array of options to include in the build
   * @param {array} [config.feature-detects[]] - An array of the feature detects to include
   * @returns {string} A string of the require.js build
   */

  return function generate(config) {
    // Set some defaults
    if (!config) {
      config = {};
    }

    config.options = config.options || [];
    config['feature-detects'] = config['feature-detects'] || [];

    // Some special cases
    var setClasses = _.includes(config.options, 'setClasses');

    var output = 'require(["ModernizrProto", "Modernizr", "testRunner"';

    // Needed named module requires
    if (setClasses) {
      output += ', "setClasses", "classes"';
    }

    // Only allow one shiv at a time
    if (_.includes(config.options, 'html5printshiv')) {
      config.options = _.without(config.options, 'html5shiv');
    }

    // Load in the rest of the options, excluding special cases
    // (they dont return values, so they aren't declared)
    _.forEach(_.without(config.options, 'setClasses'), function(option) {
      output += ', "' + option + '"';
    });

    // Load in all the detects
    _.forEach(config['feature-detects'], function(detect) {
      detect = detect.indexOf('test/') === 0 ? detect : 'test/' + detect;
      output += ', "' + detect + '"';
    });

    output += '], function( ModernizrProto, Modernizr, testRunner';

    // Needed named module declarations
    if (setClasses) {
      output += ', setClasses, classes';
    }

    output += ') {\n' +
    '  // Run each test\n' +
    '  testRunner();\n' +
    '\n';

    // Actually run the setClasses function
    if (setClasses) {
      output += '  // Remove the "no-js" class if it exists\n' +
      '  setClasses(classes);\n' +
      '\n';
    }

    output += '  delete ModernizrProto.addTest;\n';
    output += '  delete ModernizrProto.addAsyncTest;\n' +
    '\n';

    // TODO:: if there's a way to figure out if there will be no
    // items in this queue, then we could avoid the code.
    output += '  // Run the things that are supposed to run after the tests\n' +
    '  for (var i = 0; i < Modernizr._q.length; i++) {\n' +
    '    Modernizr._q[i]();\n' +
    '  }\n\n';

    output += '  // Leak Modernizr namespace\n' +
    '  window.Modernizr = Modernizr;\n' +
    '\n' +
    '});';

    return output;
  };
});
