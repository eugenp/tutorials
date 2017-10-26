/*global define*/
define(['lodash', 'metadata'], function(_, metadata) {

  function getDetectObjByAmdPath(amdPath) {
    return _.find(metadata, function(detect) {
      return detect.amdPath == amdPath || detect.amdPath == 'test/' + amdPath;
    });
  }

  return function generateBuildQuery(config) {
    // Format:
    // ?-<prop1>-<prop2>-…-<propN>-<option1>-<option2>-…<optionN>[-dontmin][-cssclassprefix:<prefix>]
    // where prop1…N and option1…N are sorted alphabetically (for consistency)
    var dontmin = !config.minify;

    // Config uses amdPaths, but build query uses property names
    var props = _.chain(config['feature-detects'])
      .map(function(amdPath) {
        var detect = getDetectObjByAmdPath(amdPath);
        var property = detect && detect.property;
        if (property) {
          property = _.isArray(property) ?
            property.join('_').replace('-', '_') :
            property.replace('-', '_');
          return property;
        }
    })
    .filter()
    .value();

    // Config uses amdPaths, but the option's just use their names.
    // A few of the values have to be massaged in order to match
    // the `value`
    var opts = _.map(config.options, function(opt) {
      if (opt.indexOf('html5') === 0) {
        opt = opt.replace('html5', '');
      }
      return opt.toLowerCase();
    });

    var sortedProps = props.sort();
    var sortedOpts = opts.sort();

    // Options are AMD paths in the config, but need to be converted to
    var buildQuery = '?-' + sortedProps.concat(sortedOpts).join('-') +
        (dontmin ? '-dontmin' : '') +
        ((config.classPrefix) ?
          '-cssclassprefix:' + config.classPrefix : '');

    return buildQuery;
  };
});
