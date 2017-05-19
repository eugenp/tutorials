const webpack = require('webpack');
const CommonsChunkPlugin = require('webpack/lib/optimize/CommonsChunkPlugin');
const CopyWebpackPlugin = require('copy-webpack-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const StringReplacePlugin = require('string-replace-webpack-plugin');
const AddAssetHtmlPlugin = require('add-asset-html-webpack-plugin');
const path = require('path');

module.exports = function (options) {
    const DATAS = {
        VERSION: JSON.stringify(require("../package.json").version),
        DEBUG_INFO_ENABLED: options.env === 'dev'
    };
    return {
        entry: {
            'polyfills': './src/main/webapp/app/polyfills',
            'global': './src/main/webapp/content/scss/global.scss',
            'main': './src/main/webapp/app/app.main'
        },
        resolve: {
            extensions: ['.ts', '.js'],
            modules: ['node_modules']
        },
        module: {
            rules: [
                { test: /bootstrap\/dist\/js\/umd\//, loader: 'imports-loader?jQuery=jquery' },
                {
                    test: /\.ts$/,
                    loaders: [
                        'angular2-template-loader',
                        'awesome-typescript-loader'
                    ],
                    exclude: ['node_modules/generator-jhipster']
                },
                {
                    test: /\.html$/,
                    loader: 'raw-loader',
                    exclude: ['./src/main/webapp/index.html']
                },
                {
                    test: /\.scss$/,
                    loaders: ['to-string-loader', 'css-loader', 'sass-loader'],
                    exclude: /(vendor\.scss|global\.scss)/
                },
                {
                    test: /(vendor\.scss|global\.scss)/,
                    loaders: ['style-loader', 'css-loader', 'postcss-loader', 'sass-loader']
                },
                {
                    test: /\.css$/,
                    loaders: ['to-string-loader', 'css-loader'],
                    exclude: /(vendor\.css|global\.css)/
                },
                {
                    test: /(vendor\.css|global\.css)/,
                    loaders: ['style-loader', 'css-loader']
                },
                {
                    test: /\.(jpe?g|png|gif|svg|woff|woff2|ttf|eot)$/i,
                    loaders: [
                        'file-loader?hash=sha512&digest=hex&name=[hash].[ext]', {
                            loader: 'image-webpack-loader',
                            query: {
                                gifsicle: {
                                    interlaced: false
                                },
                                optipng: {
                                    optimizationLevel: 7
                                }
                            }
                        }
                    ]
                },
                {
                    test: /app.constants.ts$/,
                    loader: StringReplacePlugin.replace({
                        replacements: [{
                            pattern: /\/\* @toreplace (\w*?) \*\//ig,
                            replacement: function (match, p1, offset, string) {
                                return `_${p1} = ${DATAS[p1]};`;
                            }
                        }]
                    })
                }
            ]
        },
        plugins: [
            new CommonsChunkPlugin({
                names: ['manifest', 'polyfills'].reverse()
            }),
            new webpack.DllReferencePlugin({
                context: './',
                manifest: require(path.resolve('./target/www/vendor.json')),
            }),
            new CopyWebpackPlugin([
                { from: './node_modules/swagger-ui/dist', to: 'swagger-ui/dist' },
                { from: './src/main/webapp/swagger-ui/', to: 'swagger-ui' },
                { from: './src/main/webapp/favicon.ico', to: 'favicon.ico' },
                { from: './src/main/webapp/robots.txt', to: 'robots.txt' },
                { from: './src/main/webapp/i18n', to: 'i18n' }
            ]),
            new webpack.ProvidePlugin({
                $: "jquery",
                jQuery: "jquery"
            }),
            new HtmlWebpackPlugin({
                template: './src/main/webapp/index.html',
                chunksSortMode: 'dependency',
                inject: 'body'
            }),
            new AddAssetHtmlPlugin([
                { filepath: path.resolve('./target/www/vendor.dll.js'), includeSourcemap: false }
            ]),
            new StringReplacePlugin()
        ]
    };
};
