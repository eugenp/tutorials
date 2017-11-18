var fs = require('fs');
var file = require('file');
var marked = require('marked');
var polyfills = require('./polyfills.json');
var viewRoot = fs.realpathSync(__dirname + '/../feature-detects');

function metadata(cb) {
  var tests = [];
  file.walkSync(viewRoot, function(start, dirs, files) {
    files.forEach(function(file) {
      if (file === '.DS_Store') {
        return;
      }
      var test = fs.readFileSync(start + '/' + file, 'utf8');
      // TODO :: make this regex not suck
      var metaRE = /\/\*\!([\s\S]*)\!\*\//m;
      var matches = test.match(metaRE);
      var docRE = /\/\*\sDOC([\s\S]*?)\*\//m;
      var docmatches = test.match(docRE);
      var depRE = /define\((\[[^\]]*\]),/;
      var depMatches = test.match(depRE);

      var metadata;

      if (matches && matches[1]) {
        try {
          metadata = JSON.parse(matches[1]);
        } catch (e) {
          throw new Error('Error Parsing Metadata: ' + file + '\nInput: `' + matches[1] + '`');
        }
      }
      else {
        metadata = {};
      }

      var docs = null;

      if (docmatches && docmatches[1]) {
        docs = marked(docmatches[1].trim());
      }

      metadata.doc = docs;

      var deps = [];
      var matchedDeps;

      if (depMatches && depMatches[1]) {
        try {
          matchedDeps = JSON.parse(depMatches[1].replace(/'/g, '"'));
        } catch (e) {
          throw new Error('Couldn\'t parse dependencies for `' + file + '`:\n`' + depMatches[1] + '\n`');
        }
        matchedDeps.forEach(function(dep) {
          if (dep === 'Modernizr') {
            return;
          }
          deps.push(dep);
        });
      } else {
        throw new Error('Couldn\'t find the define for `' + file + '`');
      }
      metadata.deps = deps;

      var baseDir = __dirname.replace(/lib$/, '');
      metadata.path = './' + (start + '/' + file).replace(baseDir, '').replace(/\\/g, '/');
      metadata.amdPath = metadata.path.replace(/^\.\/feature\-detects/, 'test').replace(/\.js$/i, '');

      if (!metadata.name) {
        metadata.name = metadata.amdPath;
      }

      var pfs = [];
      if (metadata.polyfills && metadata.polyfills.length) {
        metadata.polyfills.forEach(function(polyname) {
          if (polyfills[polyname]) {
            pfs.push(polyfills[polyname]);
          }
          else {
            throw new Error(metadata.name + ': Polyfill not found in `' + file + '`: ' + polyname);
          }
        });
      }
      metadata.polyfills = pfs;

      if (!metadata.async) {
        metadata.async = false;
      }

      if (!metadata.notes) {
        metadata.notes = [];
      }

      if (!metadata.warnings) {
        metadata.warnings = [];
      }

      if (!metadata.caniuse) {
        metadata.caniuse = null;
      }

      if (!metadata.cssclass && metadata.property) {
        metadata.cssclass = metadata.property;
      } else {
        metadata.cssclass = null;
      }

      // Maybe catch a bug
      if (!metadata.doc && metadata.docs) {
        metadata.doc = metadata.docs;
        delete metadata.docs;
      }

      // If you want markdown parsed code minus the docs and metadata, this'll do it.
      // Off by default for now.
      // metadata.code =  marked('```javascript\n' + test.replace(metaRE, '').replace(docRE, '') + '\n```');

      if (!metadata.tags) {
        metadata.tags = [];
      }

      if (!metadata.authors) {
        metadata.authors = [];
      }

      if (!metadata.knownBugs) {
        metadata.knownBugs = [];
      }

      tests.push(metadata);
    });
  });

  if (cb && typeof cb == 'function') {
    return cb(tests);
  }
  return tests;
}


module.exports = metadata;
