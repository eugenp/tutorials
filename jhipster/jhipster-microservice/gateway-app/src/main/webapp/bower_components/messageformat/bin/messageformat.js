#!/usr/bin/env node

/**
 * Copyright 2012-2014 Alex Sexton, Eemeli Aro, and Contributors
 *
 * Licensed under the MIT License
 */

var
  nopt          = require('nopt'),
  fs            = require('fs'),
  Path          = require('path'),
  glob          = require('glob'),
  async         = require('async'),
  MessageFormat = require('../'),
  knownOpts = {
    "locale"    : String,
    "inputdir"  : Path,
    "output"    : Path,
    "watch"     : Boolean,
    "namespace" : String,
    "include"   : String,
    "stdout"    : Boolean,
    "module"    : Boolean,
    "verbose"   : Boolean,
    "help"      : Boolean
  },
  description = {
    "locale"    : "locale(s) to use [mandatory]",
    "inputdir"  : "directory containing messageformat files to compile",
    "output"    : "output where messageformat will be compiled",
    "namespace" : "global object in the output containing the templates",
    "include"   : "glob patterns for files to include from `inputdir`",
    "stdout"    : "print the result in stdout instead of writing in a file",
    "watch"     : "watch `inputdir` for changes",
    "module"    : "create a commonJS module, instead of a global window variable",
    "verbose"   : "print logs for debug"
  },
  defaults = {
    "inputdir"  : process.cwd(),
    "output"    : process.cwd(),
    "watch"     : false,
    "namespace" : 'i18n',
    "include"   : '**/*.json',
    "stdout"    : false,
    "verbose"   : false,
    "module"    : false,
    "help"      : false
  },
  shortHands = {
    "l"  : "--locale",
    "i"  : "--inputdir",
    "o"  : "--output",
    "ns" : "--namespace",
    "I"  : "--include",
    "m"  : "--module",
    "s"  : "--stdout",
    "w"  : "--watch",
    "v"  : "--verbose",
    "?"  : "--help"
  },
  options = (function() {
    var o = nopt(knownOpts, shortHands, process.argv, 2);
    for (var key in defaults) {
      o[key] = o[key] || defaults[key];
    }
    if (o.argv.remain) {
      if (o.argv.remain.length >= 1) o.inputdir = o.argv.remain[0];
      if (o.argv.remain.length >= 2) o.output = o.argv.remain[1];
    }
    if (!o.locale || o.help) {
      var usage = ['Usage: messageformat -l [locale] [OPTIONS] [INPUT_DIR] [OUTPUT_DIR]'];
      if (!o.help) {
        usage.push("Try 'messageformat --help' for more information.");
        console.error(usage.join('\n'));
        process.exit(-1);
      }
      usage.push('\nAvailable options:');
      for (var key in shortHands) {
        var desc = description[shortHands[key].toString().substr(2)];
        if (desc) usage.push('   -' + key + ',\t' + shortHands[key] + (shortHands[key].length < 8 ? '  ' : '') + '\t' + desc);
      }
      console.log(usage.join('\n'));
      process.exit(0);
    }
    if (fs.existsSync(o.output) && fs.statSync(o.output).isDirectory()) {
      o.output = Path.join(o.output, 'i18n.js');
    }
    o.namespace = o.module ? 'module.exports' : o.namespace.replace(/^window\./, '')
    return o;
  })(),
  _log = (options.verbose ? function(s) { console.log(s); } : function(){});

function write(options, data) {
  if (options.stdout) { _log(''); return console.log(data); }
  fs.writeFile( options.output, data, 'utf8', function(err) {
    if (err) return console.error('--->\t' + err.message);
    _log(options.output + " written.");
  });
}

function parseFileSync(dir, file) {
  var path = Path.join(dir, file),
      file_parts = file.split(/[.\/]+/),
      r = { namespace: null, locale: null, data: null };
  if (!fs.statSync(path).isFile()) {
    _log('Skipping ' + file);
    return null;
  }
  r.namespace = file.replace(/\.[^.]*$/, '').replace(/\\/g, '/');
  for (var i = file_parts.length - 1; i >= 0; --i) {
    if (file_parts[i] in MessageFormat.plurals) { r.locale = file_parts[i]; break; }
  }
  try {
    _log('Building ' + JSON.stringify(r.namespace) + ' from `' + file + '` with ' + (r.locale ? 'locale ' + JSON.stringify(r.locale) : 'default locale'));
    r.data = JSON.parse(fs.readFileSync(path, 'utf8'));
  } catch (ex) {
    console.error('--->\tRead error in ' + path + ': ' + ex.message);
  }
  return r;
}

function build(options, callback) {
  var lc = options.locale.trim().split(/[ ,]+/),
      mf = new MessageFormat(lc[0]),
      messages = {},
      compileOpt = { global: options.namespace, locale: {} };
  lc.slice(1).forEach(function(l){
    var pf = mf.runtime.pluralFuncs[l] = MessageFormat.plurals[l];
    if (!pf) throw 'Plural function for locale `' + l + '` not found';
  });
  _log('Input dir: ' + options.inputdir);
  _log('Included locales: ' + lc.join(', '));
  glob(options.include, {cwd: options.inputdir}, function(err, files) {
    if (!err) async.each(files,
      function(file, cb) {
        var r = parseFileSync(options.inputdir, file);
        if (r && r.data) {
          messages[r.namespace] = r.data;
          if (r.locale) compileOpt.locale[r.namespace] = r.locale;
        }
        cb();
      },
      function() {
        var fn_str = mf.compile(messages, compileOpt).toString();
        fn_str = fn_str.replace(/^\s*function\b[^{]*{\s*/, '').replace(/\s*}\s*$/, '');
        var data = options.module ? fn_str : '(function(G) {\n' + fn_str + '\n})(this);';
        return callback(options, data.trim() + '\n');
      }
    );
  });
}

build(options, write);

if (options.watch) {
  _log('watching for changes in ' + options.inputdir + '...\n');
  require('watchr').watch({
    path: options.inputdir,
    ignorePaths: [ options.output ],
    listener: function(changeType, filePath) { if (/\.json$/.test(filePath)) build(options, write); }
  });
}
