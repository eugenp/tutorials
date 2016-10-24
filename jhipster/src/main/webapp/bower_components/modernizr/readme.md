# Modernizr [![Build Status](https://api.travis-ci.org/Modernizr/Modernizr.svg?branch=master)](https://travis-ci.org/Modernizr/Modernizr) [![Inline docs](https://inch-ci.org/github/Modernizr/Modernizr.svg?branch=master)](https://inch-ci.org/github/Modernizr/Modernizr)

##### Modernizr is a JavaScript library that detects HTML5 and CSS3 features in the user’s browser.

- [Website](https://modernizr.com)
- [Documentation](https://modernizr.com/docs/)

Modernizr tests which native CSS3 and HTML5 features are available in the current UA and makes the results available to you in two ways: as properties on a global `Modernizr` object, and as classes on the `<html>` element. This information allows you to progressively enhance your pages with a granular level of control over the experience.

This project adheres to the [Open Code of Conduct][code-of-conduct]. By participating, you are expected to honor this code.
[code-of-conduct]: http://todogroup.org/opencodeofconduct/#Modernizr/conduct@modernizr.com

## New Asynchronous Event Listeners

Often times people want to know when an asynchronous test is done so they can allow their application to react to it.
In the past, you've had to rely on watching properties or `<html>` classes. Only events on **asynchronous** tests are
supported. Synchronous tests should be handled synchronously to improve speed and to maintain consistency.

The new API looks like this:

```js
// Listen to a test, give it a callback
Modernizr.on('testname', function( result ) {
  if (result) {
    console.log('The test passed!');
  }
  else {
    console.log('The test failed!');
  }
});
```

We guarantee that we'll only invoke your function once (per time that you call `on`). We are currently not exposing
a method for exposing the `trigger` functionality. Instead, if you'd like to have control over async tests, use the
`src/addTest` feature, and any test that you set will automatically expose and trigger the `on` functionality.

## Getting Started

- Clone or download the repository
- Install project dependencies with `npm install`

## Test suite

Run the [test suite](https://modernizr.github.io/Modernizr/test/)

## Building Modernizr v3

### To generate everything in 'config-all.json':

```js
npm install
./bin/modernizr -c lib/config-all.json
//outputs to ./modernizr.js
```

### To run tests (in phantom):

```js
grunt test
```

### To run tests (in browser):

```shell
grunt build
serve .
visit <url>/test
```

### To see simple build in browser:

serve the root dir, `<url>/test/modular.html`

### To see the build tool:

* checkout the modernizr.com code
* install all your gems and bundles and jekyll and shit
* `jekyll`
* `serve ./_sites`
* visit <url>/download
* It should be just a big list of things you can build with no frills.

### API Reference

Modernizr can be used programmatically via npm:

```js
var modernizr = require("modernizr");
```

#### Building

A `build` method is exposed for generating custom Modernizr builds. Example:

```javascript
var modernizr = require("modernizr");

modernizr.build({}, function (result) {
  console.log(result); // the build
});
```

The first parameter takes a JSON object of options and feature-detects to include. See [`lib/config-all.json`](lib/config-all.json) for all available options.

The second parameter is a function invoked on task completion.

## License

[MIT License](https://opensource.org/licenses/MIT)
