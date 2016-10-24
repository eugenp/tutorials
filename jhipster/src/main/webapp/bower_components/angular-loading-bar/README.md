angular-loading-bar
===================

The idea is simple: Add a loading bar / progress bar whenever an XHR request goes out in angular.  Multiple requests within the same time period get bundled together such that each response increments the progress bar by the appropriate amount.

This is mostly cool because you simply include it in your app, and it works.  There's no complicated setup, and no need to maintain the state of the loading bar; it's all handled automatically by the interceptor.

**Requirements:** AngularJS 1.2+

**File Size:** 2.4Kb minified, 0.5Kb gzipped


## Usage:

1. include the loading bar as a dependency for your app.  If you want animations, include `ngAnimate` as well. *note: ngAnimate is optional*

    ```js
    angular.module('myApp', ['angular-loading-bar', 'ngAnimate'])
    ```
    
2. include the supplied JS and CSS file (or create your own CSS to override defaults).

    ```html
    <link rel='stylesheet' href='build/loading-bar.min.css' type='text/css' media='all' />
    <script type='text/javascript' src='build/loading-bar.min.js'></script>
    ```

3. That's it -- you're done!

#### via bower:
```
$ bower install angular-loading-bar
```
#### via npm:
```
$ npm install angular-loading-bar
```

#### via CDN:
```html
 <link rel='stylesheet' href='//cdnjs.cloudflare.com/ajax/libs/angular-loading-bar/0.7.1/loading-bar.min.css' type='text/css' media='all' />
 <script type='text/javascript' src='//cdnjs.cloudflare.com/ajax/libs/angular-loading-bar/0.7.1/loading-bar.min.js'></script>
```

## Why I created this
There are a couple projects similar to this out there, but none were ideal for me.  All implementations I've seen require that you maintain state on behalf of the loading bar.  In other words, you're setting the value of the loading/progress bar manually from potentially many different locations.  This becomes complicated when you have a very large application with several services all making independent XHR requests. It becomes even more complicated if you want these services to be loosly coupled.

Additionally, Angular was created as a highly testable framework, so it pains me to see Angular modules without tests.  That is not the case here as this loading bar ships with 100% code coverage.


**Goals for this project:**

1. Make it automatic
2. Unit tests, 100% coverage
3. Must work well with ngAnimate
4. Must be styled via external CSS (not inline)
5. No jQuery dependencies


## Configuration

#### Turn the spinner on or off:
The insertion of the spinner can be controlled through configuration.  It's on by default, but if you'd like to turn it off, simply configure the service:

```js
angular.module('myApp', ['angular-loading-bar'])
  .config(['cfpLoadingBarProvider', function(cfpLoadingBarProvider) {
    cfpLoadingBarProvider.includeSpinner = false;
  }])
```

#### Turn the loading bar on or off:
Like the spinner configuration above, the loading bar can also be turned off for cases where you only want the spinner:

```js
angular.module('myApp', ['angular-loading-bar'])
  .config(['cfpLoadingBarProvider', function(cfpLoadingBarProvider) {
    cfpLoadingBarProvider.includeBar = false;
  }])
```

#### Customize the template:
If you'd like to replace the default HTML template you can configure it by providing inline HTML as a string:

```js
angular.module('myApp', ['angular-loading-bar'])
  .config(['cfpLoadingBarProvider', function(cfpLoadingBarProvider) {
    cfpLoadingBarProvider.spinnerTemplate = '<div><span class="fa fa-spinner">Loading...</div>';
  }])
```

#### Latency Threshold
By default, the loading bar will only display after it has been waiting for a response for over 100ms.  This helps keep things feeling snappy, and avoids the annoyingness of showing a loading bar every few seconds on really chatty applications.  This threshold is totally configurable:

```js
angular.module('myApp', ['angular-loading-bar'])
  .config(['cfpLoadingBarProvider', function(cfpLoadingBarProvider) {
    cfpLoadingBarProvider.latencyThreshold = 500;
  }])
```

#### Ignoring particular XHR requests:
The loading bar can also be forced to ignore certain requests, for example, when long-polling or periodically sending debugging information back to the server.

```js
// ignore a particular $http GET:
$http.get('/status', {
  ignoreLoadingBar: true
});

// ignore a particular $http POST.  Note: POST and GET have different
// method signatures:
$http.post('/save', data, {
  ignoreLoadingBar: true
});

```


```js
// ignore particular $resource requests:
.factory('Restaurant', function($resource) {
  return $resource('/api/restaurant/:id', {id: '@id'}, {
    query: {
      method: 'GET',
      isArray: true,
      ignoreLoadingBar: true
    }
  });
});

```




## How it works:
This library is split into two modules, an $http `interceptor`, and a `service`:

**Interceptor**  
The interceptor simply listens for all outgoing XHR requests, and then instructs the loadingBar service to start, stop, and increment accordingly.  There is no public API for the interceptor.  It can be used stand-alone by including `cfp.loadingBarInterceptor` as a dependency for your module.

**Service**  
The service is responsible for the presentation of the loading bar.  It injects the loading bar into the DOM, adjusts the width whenever `set()` is called, and `complete()`s the whole show by removing the loading bar from the DOM.

## Service API (advanced usage)
Under normal circumstances you won't need to use this.  However, if you wish to use the loading bar without the interceptor, you can do that as well.  Simply include the loading bar service as a dependency instead of the main `angular-loading-bar` module:

```js
angular.module('myApp', ['cfp.loadingBar'])
```


```js

cfpLoadingBar.start();
// will insert the loading bar into the DOM, and display its progress at 1%.
// It will automatically call `inc()` repeatedly to give the illusion that the page load is progressing.

cfpLoadingBar.inc();
// increments the loading bar by a random amount.
// It is important to note that the auto incrementing will begin to slow down as
// the progress increases.  This is to prevent the loading bar from appearing
// completed (or almost complete) before the XHR request has responded. 

cfpLoadingBar.set(0.3) // Set the loading bar to 30%
cfpLoadingBar.status() // Returns the loading bar's progress.
// -> 0.3

cfpLoadingBar.complete()
// Set the loading bar's progress to 100%, and then remove it from the DOM.

```

## Events
The loading bar broadcasts the following events over $rootScope allowing further customization:

**`cfpLoadingBar:loading`** triggered upon each XHR request that is not already cached

**`cfpLoadingBar:loaded`** triggered each time an XHR request recieves a response (either successful or error)

**`cfpLoadingBar:started`** triggered once upon the first XHR request.  Will trigger again if another request goes out after `cfpLoadingBar:completed` has triggered.

**`cfpLoadingBar:completed`** triggered once when the all XHR requests have returned (either successfully or not)

## Credits: 
Credit goes to [rstacruz](https://github.com/rstacruz) for his excellent [nProgress](https://github.com/rstacruz/nprogress).

## License:
Licensed under the MIT license
