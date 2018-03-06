var webpack = require('webpack');
var path = require('path');

module.exports = {
	entry: './src/main/ts/app.tsx',
	output: {
		path: __dirname + '/build/resources/main/static/public/script/',
		filename: 'bundle.js'
	},
	devtool: "source-map",
	resolve: {
		// Add '.ts' and '.tsx' as resolvable extensions.
		extensions: ["", ".webpack.js", ".web.js", ".ts", ".tsx", ".js"]
	},
	module: {
		loaders: [
			{ test: /\.tsx?$/, loader: 'ts-loader' }
		],
		preLoaders: [
			// All output '.js' files will have any sourcemaps re-processed by 'source-map-loader'.
			{ test: /\.js$/, loader: "source-map-loader" }
		]
	},
	externals: {
		"react": "React",
		"react-dom": "ReactDOM",
		"jquery": "$",
		"classnames": "classNames",
		"monent": "monent"
	}
};