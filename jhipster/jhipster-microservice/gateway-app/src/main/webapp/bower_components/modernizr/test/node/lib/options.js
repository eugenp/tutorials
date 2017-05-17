var root = require('find-parent-dir').sync(__dirname, 'package.json');
var options = require(root + 'lib/options');
var expect = require('expect.js');
var Joi = require('joi');

var schema = Joi.array().items(
  Joi.object().keys({
    name: Joi.string(),
    property: Joi.string()
  })
);


describe('cli/options', function() {
  this.timeout(20000);

  it('should return an array of objects in a callback', function(done) {
    options(function(opts) {
      var err = schema.validate(opts).error;
      expect(err).to.be(null);
      done(err);
    });
  });

  it('should return the array of objects immediately after the first run', function() {
    var err = schema.validate(options()).error;
    expect(err).to.be(null);
  });

  it('should return all jsdoc info when the second arg is true', function() {
    expect(options(null, true) !== options(null)).to.be(true);
    expect(options(null, true)[0].description).to.not.be(undefined);
  });
});
