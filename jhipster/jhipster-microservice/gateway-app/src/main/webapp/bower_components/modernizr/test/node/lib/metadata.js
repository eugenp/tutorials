var root = require('find-parent-dir').sync(__dirname, 'package.json');
var proxyquire = require('proxyquire').noPreserveCache();
var metadata = require(root + 'lib/metadata');
var expect = require('expect.js');
var Joi = require('joi');

describe('cli/metadata', function() {

  it('should ignore .DS_STORE files', function(done) {

    var metadata = proxyquire(root + 'lib/metadata', {
      'file': {
        'walkSync': function(dir, cb) {
          cb('/', [], ['.DS_Store']);
        }
      }
    });

    metadata(function(data) {
      expect(data).to.be.an('array');
      expect(data).to.have.length(0);
      done();
    });
  });

  it('should throw on malformed metadata', function() {

    var metadata = proxyquire(root + 'lib/metadata', {
      'fs': {
        'readFileSync': function() {
          return '/*! { !*/';
        }
      }
    });

    expect(metadata).to.throwException(/Error Parsing Metadata/);
  });

  it('should not throw when metadata is missing', function() {

    var metadata = proxyquire(root + 'lib/metadata', {
      'fs': {
        'readFileSync': function() {
          return 'sup dude';
        }
      }
    });

    expect(metadata).to.not.throwException(/Error Parsing Metadata/);
  });

  it('should throw on malformed deps', function() {

    var metadata = proxyquire(root + 'lib/metadata', {
      'fs': {
        'readFileSync': function() {
          return 'define([[],';
        }
      }
    });

    expect(metadata).to.throwException(/Couldn't parse dependencies/);
  });

  it('should throw if we can\'t find the define', function() {

    var metadata = proxyquire(root + 'lib/metadata', {
      'fs': {
        'readFileSync': function() {
          return 'defin([]),';
        }
      }
    });

    expect(metadata).to.throwException(/Couldn't find the define/);
  });

  it('should use amdPath as a fallback for name', function() {

    var metadata = proxyquire(root + 'lib/metadata', {
      'file': {
        'walkSync': function(dir, cb) {
          cb('/', [], ['fakeDetect.js']);
        }
      },
      'fs': {
        'readFileSync': function() {
          return '/*! { "property": "fake"}!*/ define([],';
        }
      }
    });
    var result = metadata();

    expect(result.name).to.equal(result.amdPath);
  });

  it('should throw if we can\'t find the define', function() {

    var metadata = proxyquire(root + 'lib/metadata', {
      'fs': {
        'readFileSync': function() {
          return '/*! { "polyfills": ["fake"]}!*/ define([],';
        }
      }
    });

    expect(metadata).to.throwException(/Polyfill not found/);
  });

  it('should throw if we can\'t find the define', function() {

    var metadata = proxyquire(root + 'lib/metadata', {
      'fs': {
        'readFileSync': function() {
          return '/*! { "property": "fake", "cssclass": "realFake"}!*/ define([],';
        }
      }
    });

    var firstResult = metadata()[0];

    expect(firstResult.cssclass).to.be(null);
  });

  it('should rename `docs` to `doc` when found', function() {

    var metadata = proxyquire(root + 'lib/metadata', {
      'fs': {
        'readFileSync': function() {
          return '/*! { "docs": "originally docs" }!*/ define([],';
        }
      }
    });

    var firstResult = metadata()[0];

    expect(firstResult.docs).to.be(undefined);
    expect(firstResult.doc).to.match(/originally docs/);
  });

  it('returns a json blob when invoked without callback', function() {
    expect(metadata()).to.be.an('array');
  });

  it('return nothing when given a callback', function() {
    expect(metadata(function noop() {})).to.be(undefined);
  });

  it('pass the json blob when given a callback', function(done) {
    metadata(function(data) {
      expect(data).to.be.an('array');
      done();
    });
  });

  describe('returns an array of valid objects', function() {
    var schema = Joi.object().keys({
                  amdPath: Joi.string().required(),
                  name: Joi.string().required(),
                  path: Joi.string().required(),
                  doc: Joi.alternatives().try(Joi.string(), null),

                  caniuse: Joi.alternatives().try(Joi.string(), null),

                  async: Joi.boolean(),

                  aliases: Joi.array().items(Joi.string()),
                  builderAliases: Joi.array().items(Joi.string()),
                  knownBugs: Joi.array().items(Joi.string()),
                  warnings: Joi.array().items(Joi.string()),
                  authors: Joi.array().items(Joi.string()),
                  tags: Joi.array().items(Joi.string()),
                  deps: Joi.array().items(Joi.string()),

                  notes: Joi.array().items(
                    Joi.object().keys({
                      name: Joi.string().required(),
                      href: Joi.string().required()
                    })
                  ).unique(),

                  cssclass: Joi.alternatives().try(
                              Joi.string().lowercase(),
                              Joi.array().items(Joi.string().lowercase())
                            ).required(),

                  property: Joi.alternatives().try(
                              Joi.string().lowercase(),
                              Joi.array().items(Joi.string().lowercase())
                            ).required(),

                  polyfills: Joi.array().items(
                                Joi.object().keys({
                                  name: Joi.string().required(),
                                  authors: Joi.array().items(Joi.string()),
                                  notes: Joi.array().items(Joi.string()),
                                  href: Joi.string().required(),
                                  licenses: Joi.array().items(Joi.string()).required()
                                })
                              ).unique()
                });

    metadata(function(data) {
      data.forEach(function(obj) {
        var err = schema.validate(obj).error;
        it('for ' + obj.name, function() {
          expect(err).to.be(null);
        });
      });
    });
  });
});
