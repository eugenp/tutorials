var root = require('find-parent-dir').sync(__dirname, 'package.json');
var expect = require('expect.js');
var cp = require('child_process');
var Modernizr = require(root + 'lib/cli');


describe('cli', function() {

  it('exposes a build function', function() {
    expect(Modernizr.build).to.be.a('function');
  });

  it('exposes a metadata function', function() {
    expect(Modernizr.metadata).to.be.a('function');
  });

  it('does not throw when being executed', function(done) {
    cp.exec('node ' + root + '/bin/modernizr -f adownload', done);
  });

});
