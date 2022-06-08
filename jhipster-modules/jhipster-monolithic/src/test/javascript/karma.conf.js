'use strict';

const path = require('path');
const webpack = require('webpack');
const WATCH = process.argv.indexOf('--watch') > -1;
const LoaderOptionsPlugin = require("webpack/lib/LoaderOptionsPlugin");

module.exports = function (config) {
    config.set({

        // base path that will be used to resolve all patterns (eg. files, exclude)
        basePath: './',

        // frameworks to use
        // available frameworks: https://npmjs.org/browse/keyword/karma-adapter
        frameworks: ['jasmine', 'intl-shim'],

        // list of files / patterns to load in the browser
        files: [
            'spec/entry.ts'
        ],


        // list of files to exclude
        exclude: ['e2e/**'],

        // preprocess matching files before serving them to the browser
        // available preprocessors: https://npmjs.org/browse/keyword/karma-preprocessor
        preprocessors: {
            'spec/entry.ts': ['webpack', 'sourcemap']
        },

        webpack: {
            resolve: {
                extensions: ['.ts', '.js']
            },
            module: {
                rules: [
                    {
                        test: /\.ts$/, enforce: 'pre', loader: 'tslint-loader', exclude: /(test|node_modules)/
                    },
                    {
                        test: /\.ts$/,
                        loaders: ['awesome-typescript-loader', 'angular2-template-loader?keepUrl=true'],
                        exclude: /node_modules/
                    },
                    {
                        test: /\.(html|css)$/,
                        loader: 'raw-loader',
                        exclude: /\.async\.(html|css)$/
                    },
                    {
                        test: /\.async\.(html|css)$/,
                        loaders: ['file?name=[name].[hash].[ext]', 'extract']
                    },
                    {
                        test: /\.scss$/,
                        loaders: ['to-string-loader', 'css-loader', 'sass-loader']
                    },
                    {
                        test: /src\/main\/webapp\/.+\.ts$/,
                        enforce: 'post',
                        exclude: /(test|node_modules)/,
                        loader: 'sourcemap-istanbul-instrumenter-loader?force-sourcemap=true'
                    }]
            },
            devtool: 'inline-source-map',
            plugins: [
                new webpack.ContextReplacementPlugin(
                    // The (\\|\/) piece accounts for path separators in *nix and Windows
                    /angular(\\|\/)core(\\|\/)(esm(\\|\/)src|src)(\\|\/)linker/,
                    root('./src') // location of your src
                ),
                new LoaderOptionsPlugin({
                    options: {
                        tslint: {
                            emitErrors: !WATCH,
                            failOnHint: false
                        }
                    }
                })
            ]
        },

        // test results reporter to use
        // possible values: 'dots', 'progress'
        // available reporters: https://npmjs.org/browse/keyword/karma-reporter
        reporters: ['dots', 'junit', 'progress', 'karma-remap-istanbul'],

        junitReporter: {
            outputFile: '../../../../target/test-results/karma/TESTS-results.xml'
        },

        remapIstanbulReporter: {
            reports: { // eslint-disable-line
                'html': 'target/test-results/coverage',
                'text-summary': null
            }
        },

        // web server port
        port: 9876,

        // enable / disable colors in the output (reporters and logs)
        colors: true,

        // level of logging
        // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
        logLevel: config.LOG_INFO,

        // enable / disable watching file and executing tests whenever any file changes
        autoWatch: WATCH,

        // start these browsers
        // available browser launchers: https://npmjs.org/browse/keyword/karma-launcher
        browsers: ['PhantomJS'],

        // Continuous Integration mode
        // if true, Karma captures browsers, runs the tests and exits
        singleRun: !WATCH
    });
};

function root(__path) {
    return path.join(__dirname, __path);
}
