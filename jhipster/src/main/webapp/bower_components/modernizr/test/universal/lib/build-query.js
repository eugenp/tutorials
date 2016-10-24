if (typeof define !== 'function') {
  var projectRoot = require('find-parent-dir').sync(__dirname, 'package.json');
  var filesRoot = projectRoot;
  if (process.env.APP_DIR_FOR_CODE_COVERAGE) {
    filesRoot = filesRoot + process.env.APP_DIR_FOR_CODE_COVERAGE;
  }
  var requirejs = require('requirejs');
  var expect = require('expect.js');
} else {
  var projectRoot = '..';
  var filesRoot = '..';
}
var cleanup;
var req;


describe('build-query', function() {
  var buildQuery;

  before(function(done) {

    req = requirejs.config({
      context: Math.random().toString().slice(2),
      paths: {
        lib: filesRoot + '/lib',
        lodash: projectRoot + '/node_modules/lodash/lodash',
        metadata: projectRoot + '/test/mocks/lib/metadata',
        cleanup: projectRoot + '/test/cleanup'
      }
    });

    req(['lib/build-query', 'cleanup'], function(_buildQuery, _cleanup) {
      buildQuery = _buildQuery;
      cleanup = _cleanup;
      done();
    });
  });

  it('builds a query from a feature-detect', function() {
    var query = buildQuery({
      'feature-detects': ['css/boxsizing']
    });
    expect(query).to.be('?-boxsizing-dontmin');
  });

  it('properly formats detects with multiple properties', function() {
    var query = buildQuery({
      'feature-detects': ['dom/createElement-attrs']
    });
    expect(query).to.be('?-createelementattrs_createelement_attrs-dontmin');
  });

  it('adds options to the query', function() {
    var query = buildQuery({
      options: ['mq']
    });
    expect(query).to.be('?-mq-dontmin');
  });

  it('adds classPrefix when setClasses is true as well', function() {
    var query = buildQuery({
      classPrefix: 'TEST_PREFIX',
      options: ['setClasses']
    });
    expect(query).to.be('?-setclasses-dontmin-cssclassprefix:TEST_PREFIX');
  });

  it('strips `html5` from the shiv options', function() {
    var query = buildQuery({
      options: ['html5shiv']
    });
    expect(query).to.be('?-shiv-dontmin');
  });

  it('removes the dontmin option when minify is true', function() {
    var query = buildQuery({
      minify: true
    });
    expect(query).to.be('?-');
  });

  it('removes custom tests from the build query', function() {
      var query = buildQuery({
            'feature-detects': ['css/boxsizing', 'custom/test/path']
          });
      expect(query).to.be('?-boxsizing-dontmin');
    });


  after(function() {
    cleanup();
  });

});
