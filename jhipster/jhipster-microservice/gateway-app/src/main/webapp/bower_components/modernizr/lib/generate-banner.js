/*global location, define*/
define(['lodash', 'package', 'lib/build-query'], function(_, pkg, buildQuery) {
  var domain = 'modernizr.com';
  if (typeof location !== 'undefined' && 'host' in location) {
    domain = location.host;
  }

  // Return a Modernizr file banner.
  // Usage:
  //   require('banners')(type);
  // Arguments:
  //   type (String, required): either 'compact' or 'full'.

  return function banners(type, config) {
    config = config || {};
    var query = buildQuery(config);
    if (!type || type === 'compact') {
      return '/*! ' + pkg.name + ' ' + pkg.version + ' (Custom Build) | ' + pkg.license  + ' *\n' +
        ' * http://' + domain + '/download/' + query +
        ' !*/\n';
    }
    else if (type === 'full') {
      return '/*!\n' +
        ' * ' + pkg.name + ' v' + pkg.version + '\n' +
        ' * Build http://' + domain + '/download' + query + '\n' +
        ' *\n' +
        ' * Copyright (c)\n *  ' + _.map(pkg.contributors, 'name').join('\n *  ') + '\n\n' +
        ' * ' + pkg.license + ' License\n */\n' +
      '\n' +
        '/*\n' +
        ' * Modernizr tests which native CSS3 and HTML5 features are available in the\n' +
        ' * current UA and makes the results available to you in two ways: as properties on\n' +
        ' * a global `Modernizr` object, and as classes on the `<html>` element. This\n' +
        ' * information allows you to progressively enhance your pages with a granular level\n' +
        ' * of control over the experience.\n*/\n';
    } else {
      throw 'banners() must be passed "compact" or "full" as an argument.';
    }
  };
});
