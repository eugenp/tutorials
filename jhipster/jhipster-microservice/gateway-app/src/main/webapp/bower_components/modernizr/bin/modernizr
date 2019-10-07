#!/usr/bin/env node
'use strict';

var fs = require('fs');
var _ = require('lodash');
var path = require('path');
var mkdirp = require('mkdirp');
var Modernizr = require(path.resolve(__dirname + '/../lib/cli.js'));
var yargs = require('yargs')
  .options('h', {
    alias: 'help',
    describe: 'Print Help'
  })
  .options('V', {
    alias: ['v', 'version'],
    describe: 'Print the version and exit'
  })
  .options('f', {
    alias: 'features',
    describe: 'comma separated list of feature detects'
  })
  .options('o', {
    alias: 'options',
    describe: 'comma separated list of extensibility options'
  })
  .options('c', {
    alias: 'config',
    describe: 'Path to a JSON file containing Modernizr configuration. See lib/config-all.json for an example'
  })
  .options('d', {
    alias: 'dest',
    describe: 'Path to write the Modernizr build file to. Defaults to ./modernizr.js'
  })
  .options('m', {
    alias: 'metadata',
    describe: 'Path to where the Modernizr feature-detect metadata should be saved. Defaults to ./metadata.json'
  })
  .options('u', {
    alias: 'uglify',
    describe: 'uglify/minify the output'
  })
  .options('q', {
    alias: 'quiet',
    describe: 'Silence all output'
  });
var argv = yargs.argv;
var cwd = process.cwd();
var dest = cwd + '/modernizr.js';
var inlineConfig;
var configPath;
var config;

function log() {
  if (!argv.q) {
    console.log.apply(console, arguments);
  }
}
function stringify(obj, minified) {
  var replacer = function(key, value) {
    return value;
  };
  var args = minified ? [replacer,2] : [];
  args.unshift(obj);
  return JSON.stringify.apply(JSON, args);
}

if (argv.h) {
  yargs.showHelp();
  process.exit();
}

if (argv.V) {
  var pkg = require('../package.json');
  console.log('Modernizr v' + pkg.version);
  process.exit();
}

if (argv.d) {
  dest = path.normalize(argv.d);
  var exists = fs.existsSync(dest);
  var isDir = exists && fs.statSync(dest).isDirectory();
  var fileRequested = _.endsWith(dest, '.js');

  if ((exists && isDir) || (!exists && !fileRequested)) {
    dest = path.join(dest, 'modernizr.js');
  }

  mkdirp.sync(path.dirname(dest));
}

if (argv.m) {
  // path.normalize is used instead of normalize in order to support ~
  // we get an absolute path on the fallback from cwd, and any user supplied
  // argument will be relative to their current directory.
  var metaDest = path.normalize(argv.m === true ? cwd + '/metadata.json' : argv.m);
  Modernizr.metadata(function(metadata) {
    mkdirp.sync(path.dirname(metaDest));
    fs.writeFileSync(metaDest, stringify(metadata, !argv.u));
    log('metadata saved to ' + metaDest);
  });
  if (!argv.d) {
    // return early unless we explictly request Modernizr to be built
    return;
  }
}


if (argv.o || argv.f) {
  var metadata = Modernizr.metadata();
  var options = Modernizr.options();


  var find = function(config, source) {
    if (!config) {
      return;
    }

    return config
      .replace(/-/g, ',')
      .split(',')
      .map(function(prop) {
        var obj = _.find(source, {property: prop});

        if (_.isUndefined(obj)) {
          throw new Error('invalid key value name - ' + prop);
        } else {
          return obj.amdPath || obj.property;
        }
      });
  };

  config = {
    'feature-detects': find(argv.f, metadata),
    'options': find(argv.o, options)
  };

  inlineConfig = true;
}

if (argv.c) {
  try {
    configPath = fs.realpathSync(argv.c);
  } catch (e) {
    console.error(argv.c + ' does not exist.');
    process.exit(1);
  }
  if (!configPath) {
    configPath = path.resolve(__dirname, '../lib/config-all.json');
  }
}

try {
  if (!config && !configPath) {
    console.error('config file, inline features, or options required.');
    yargs.showHelp();
    process.exit(1)
  } else {
    config = config || {};
    if (configPath) {
      config = _.extend(config, require(configPath));
    }
  }
} catch (e) {
  console.error(configPath + ' is not valid JSON.');
  console.error(e);
  process.exit(1);
}

if (argv.u) {
  config.minify = true;
}

Modernizr.build(config, function(output) {
  fs.writeFileSync(dest, output);
  log('Modernizr build saved to ' + dest);
});
