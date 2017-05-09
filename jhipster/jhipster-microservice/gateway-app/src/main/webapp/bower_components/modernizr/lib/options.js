var fs = require('fs');
var _ = require('lodash');
var file = require('file');
var jsdoc = require('doctrine');
var srcRoot = fs.realpathSync(__dirname + '/../src');
var commentRE = /^(\s+)?(\/\*)?\*(\/)?\s?/mg;
var jsdocRE = /[^\S\r\n]*\/(?:\*{2})([\W\w]+?)\*\//mg;

var stripComments = function(str) {
  return str.replace(commentRE, '');
};

function options(cb, allMetadata) {
  var opts;

  file.walkSync(srcRoot, function(start, dirs, files) {
    opts = _.chain(files)
      .map(function(file) {
        var srcFile = fs.readFileSync(start + '/' + file, 'utf8');
        var docs = srcFile.match(jsdocRE);

        if (docs) {
          docs = docs
            .map(stripComments)
            .map(function(str) {
              return jsdoc.parse(str, {
                sloppy: true,
                tags: [
                  'access',
                  'author',
                  'class',
                  'example',
                  'function',
                  'memberOf',
                  'memberof',
                  'name',
                  'optionName',
                  'optionProp',
                  'param',
                  'params',
                  'preserve',
                  'private',
                  'returns',
                  'type'
                ]
              });
            });

          var option = _.chain(docs)
            .flatten()
            .filter(function(doc) {
              if (allMetadata) {
                return true;
              } else {
                return doc && _.some(doc.tags, {title: 'optionName'});
              }
            })
            .map(function(opt) {
              if (allMetadata) {
                return opt;
              } else {
                var tags = opt.tags.filter(function(tag) {
                  return tag.title.indexOf('option') === 0;
                });

                return {
                  name: _.filter(tags, {title: 'optionName'})[0].description,
                  property: _.filter(tags, {title: 'optionProp'})[0].description
                };
              }
            })
            .value();

          return option;
        }
      })
      .filter(function(doc) {
        return doc && doc.length;
      })
      .flatten()
      .value();

  });

  if (cb) {
    cb(opts);
  }

  return opts;

}

module.exports = options;
