/*global module */

var browsers = require('./test/browser/sauce-browsers.json');
var serveStatic = require('serve-static');

module.exports = function(grunt) {
  'use strict';

  // Load grunt dependencies
  require('load-grunt-tasks')(grunt);

  var browserTests = grunt.file.expand([
    'test/universal/**/*.js',
    'test/browser/**/*.js',
    '!test/browser/setup.js',
    '!test/browser/integration/*.js'
  ]);

  grunt.initConfig({
    env: {
      nodeTests: [
        'test/universal/**/*.js',
        'test/node/**/*.js'
      ],
      browserTests: browserTests,
      coverage: {
        APP_DIR_FOR_CODE_COVERAGE: 'test/coverage/instrument',
        urls: [
          'http://localhost:9999/test/unit.html',
          'http://localhost:9999/test/index.html'
        ]
      }
    },
    generate: {
      dest: './dist/modernizr-build.js'
    },
    copy: {
      'gh-pages': {
        files: [
          {
            expand: true,
            src: [
              './**/*',
              '!./test/coverage/**',
              '!./node_modules/*grunt-*/**',
              '!./node_modules/**/node_modules/**'
            ],
            dest: 'gh-pages'
          }
        ]
      }
    },
    jscs: {
      src: [
        'Gruntfile.js',
        'src/*.js',
        'lib/*.js',
        'test/**/*.js',
        'feature-detects/**/*.js',
        '!src/html5printshiv.js',
        '!test/coverage/**/*.js',
        '!test/js/lib/**/*.js',
        '!src/html5shiv.js'
      ]
    },
    jshint: {
      options: {
        jshintrc: true,
        ignores: [
          'src/html5printshiv.js',
          'src/html5shiv.js'
        ]
      },
      files: [
        'Gruntfile.js',
        'src/*.js',
        'lib/*.js',
        'feature-detects/**/*.js'
      ],
      tests: {
        options: {
          jshintrc: true
        },
        files: {
          src: [
            '<%= env.nodeTests%>',
            '<%= env.browserTests %>',
            'test/browser/setup.js',
            'test/browser/integration/*.js'
          ]
        }
      }
    },
    clean: {
      dist: [
        'dist',
        'test/coverage',
        'test/*.html',
        'gh-pages'
      ]
    },
    jade: {
      compile: {
        options: {
          data: {
            unitTests: browserTests,
            integrationTests: grunt.file.expand(['test/browser/integration/*.js'])
          }
        },
        files: {
          'test/unit.html': 'test/browser/unit.jade',
          'test/iframe.html': 'test/browser/iframe.jade',
          'test/index.html': 'test/browser/integration.jade'
        }
      }
    },
    connect: {
      server: {
        options: {
          middleware: function() {
            return [
              function(req, res, next) {
                // catchall middleware used in testing
                var ua = req.headers['user-agent'];

                // record code coverage results from browsers
                if (req.url == '/coverage/client' && req.method == 'POST') {
                  var name = encodeURI(ua.replace(/\//g, '-'));
                  var body = '';

                  req.on('data', function(data) {
                    body = body + data;
                  });

                  req.on('end', function() {
                    grunt.file.write('test/coverage/reports/' + name + '.json', body);
                    res.end();
                  });

                  return;
                }

                // redirect requests form the `require`d components to their instrumented versions
                if (req.url.match(/^\/(src|lib)\//)) {
                  req.url = '/test/coverage/instrument' + req.url;
                }

                next();
              },
              serveStatic(__dirname)
            ];
          },
          port: 9999
        }
      }
    },
    'saucelabs-custom': {
      all: {
        options: {
          urls:  '<%= env.coverage.urls %>',
          testname: process.env.CI_BUILD_NUMBER || 'Modernizr Test',
          browsers: browsers,
          maxRetries: 3
        }
      }
    },
    mocha: {
      test: {
        options: {
          urls: '<%= env.coverage.urls %>',
          log: true
        },
      },
    },
    // `mocha` runs browser tests, `mochaTest` runs node tests
    mochaTest: {
      test: {
        options: {
          reporter: 'dot'
        },
        src: ['<%= env.nodeTests%>']
      }
    },
    instrument: {
      files: [
        'src/**/*.js',
        'lib/**/*.js'
      ],
      options: {
        basePath: 'test/coverage/instrument/'
      }
    },
    storeCoverage: {
      options: {
        dir: 'test/coverage/reports'
      }
    },
    makeReport: {
      src: 'test/coverage/reports/**/*.json',
      options: {
        type: 'lcov',
        dir: 'test/coverage/reports',
        print: 'detail'
      }
    },
    coveralls: {
      all: {
        src: 'test/coverage/reports/lcov.info',
        options: {
          force: true
        }
      }
    }
  });

  grunt.registerMultiTask('generate', 'Create a version of Modernizr from Grunt', function() {
    var done = this.async();
    var config = require('./lib/config-all');
    var modernizr = require('./lib/cli');
    var dest = this.data;

    modernizr.build(config, function(output) {
      grunt.file.write(dest, output);
      done();
    });
  });

  grunt.registerTask('nodeTests', ['mochaTest']);

  grunt.registerTask('browserTests', ['connect', 'mocha']);

  grunt.registerTask('build', ['clean', 'generate']);

  grunt.registerTask('lint', ['jshint', 'jscs']);

  grunt.registerTask('default', ['lint', 'build']);

  var tests = ['clean', 'lint', 'jade', 'instrument', 'env:coverage', 'nodeTests'];

  if (process.env.APPVEYOR) {
    grunt.registerTask('test', tests);
  } else if (process.env.BROWSER_COVERAGE !== 'true') {
    grunt.registerTask('test', tests.concat(['generate', 'browserTests']));
  } else {
    grunt.registerTask('test', tests.concat(['generate', 'storeCoverage', 'browserTests', 'saucelabs-custom', 'makeReport', 'coveralls']));
  }
};
