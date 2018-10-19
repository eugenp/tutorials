# Swagger UI

[![Build Status](https://travis-ci.org/swagger-api/swagger-ui.svg?branch=master)](https://travis-ci.org/swagger-api/swagger-ui)
[![NPM version](https://badge.fury.io/js/swagger-ui.svg)](http://badge.fury.io/js/swagger-ui)

[![Dependency Status](https://david-dm.org/swagger-api/swagger-ui/status.svg)](https://david-dm.org/swagger-api/swagger-ui)
[![devDependency Status](https://david-dm.org/swagger-api/swagger-ui/dev-status.svg)](https://david-dm.org/swagger-api/swagger-ui#info=devDependencies)

Swagger UI is part of the Swagger project.  The Swagger project allows you to produce, visualize and consume your OWN RESTful services.  No proxy or 3rd party services required.  Do it your own way.

Swagger UI is a dependency-free collection of HTML, Javascript, and CSS assets that dynamically
generate beautiful documentation and sandbox from a Swagger-compliant API. Because Swagger UI has no dependencies, you can host it in any server environment, or on your local machine.

## What's Swagger?

The goal of Swagger™ is to define a standard, language-agnostic interface to REST APIs which allows both humans and computers to discover and understand the capabilities of the service without access to source code, documentation, or through network traffic inspection. When properly defined via Swagger, a consumer can understand and interact with the remote service with a minimal amount of implementation logic. Similar to what interfaces have done for lower-level programming, Swagger removes the guesswork in calling the service.


Check out [Swagger-Spec](https://github.com/OAI/OpenAPI-Specification) for additional information about the Swagger project, including additional libraries with support for other languages and more.


## Compatibility
The OpenAPI Specification has undergone 4 revisions since initial creation in 2010.  Compatibility between swagger-ui and the OpenAPI Specification is as follows:

Swagger UI Version | Release Date | OpenAPI Spec compatibility | Notes | Status
------------------ | ------------ | -------------------------- | ----- | ------
2.1.4              | 2016-07-20   | 1.1, 1.2, 2.0              | [tag v.2.1.5](https://github.com/swagger-api/swagger-ui/tree/v2.1.5) |
2.1.4              | 2016-01-06   | 1.1, 1.2, 2.0              | [tag v.2.1.4](https://github.com/swagger-api/swagger-ui/tree/v2.1.4) |
2.0.24             | 2014-09-12   | 1.1, 1.2 | [tag v2.0.24](https://github.com/swagger-api/swagger-ui/tree/v2.0.24) |
1.0.13             | 2013-03-08   | 1.1, 1.2 | [tag v1.0.13](https://github.com/swagger-api/swagger-ui/tree/v1.0.13) |
1.0.1              | 2011-10-11   | 1.0, 1.1 | [tag v1.0.1](https://github.com/swagger-api/swagger-ui/tree/v1.0.1)   |

## How to Use It

### Download
You can use the swagger-ui code AS-IS!  No need to build or recompile--just clone this repo and use the pre-built files in the `dist` folder.  If you like swagger-ui as-is, stop here.

##### Browser support
Swagger UI works in all evergreen desktop browsers (Chrome, Safari, Firefox). Internet Explorer support is version 8 (IE8) and above.

### Build
You can rebuild swagger-ui on your own to tweak it or just so you can say you did.  To do so, follow these steps:

### Windows Users: Please install [Python](https://www.python.org/downloads/windows/) before follow below guidelines for node-gyp rebuild to run.

1. `npm install`
2. `npm run build`
3. You should see the distribution under the dist folder. Open [`./dist/index.html`](./dist/index.html) to launch Swagger UI in a browser

### Development
Use `npm run serve` to make a new build, watch for changes, and serve the result at http://localhost:8080/.

### Build using Docker

To build swagger-ui using a docker container:

```
docker build -t swagger-ui-builder .
docker run -p 80:8080 swagger-ui-builder
```

This will start Swagger UI at `http://localhost`.

### Use
Once you open the Swagger UI, it will load the [Swagger Petstore](http://petstore.swagger.io/v2/swagger.json) service and show its APIs.  You can enter your own server url and click explore to view the API.

### Customize
You may choose to customize Swagger UI for your organization. Here is an overview of what's in its various directories:

-    dist: Contains a distribution which you can deploy on a server or load from your local machine.
-    dist/lang: The swagger localization
-    lib: Contains javascript dependencies which swagger-ui depends on
-    node_modules: Contains node modules which swagger-ui uses for its development.
-    src
-    src/main/templates: [handlebars](http://handlebarsjs.com/) templates used to render swagger-ui
-    src/main/html: the html files, some images and css
-    src/main/javascript: main code

### SwaggerUi
To use swagger-ui you should take a look at the [source of swagger-ui html page](https://github.com/swagger-api/swagger-ui/blob/master/dist/index.html) and customize it. This basically requires you to instantiate a SwaggerUi object and call load() on it as below:

```javascript
var swaggerUi = new SwaggerUi({
  url:"http://petstore.swagger.io/v2/swagger.json",
  dom_id:"swagger-ui-container"
});

swaggerUi.load();
```

##### Parameters

Parameter Name | Description
--- | ---
url | The url pointing to `swagger.json` (Swagger 2.0) or the resource listing (earlier versions) as per [OpenAPI Spec](https://github.com/OAI/OpenAPI-Specification/).
authorizations | An authorization object to be passed to swagger-js.  Setting it here will trigger inclusion of any authorization or custom signing logic when fetching the swagger description file.  Note the object structure should be `{ key: AuthorizationObject }`
spec | A JSON object describing the OpenAPI Specification. When used, the `url` parameter will not be parsed. This is useful for testing manually-generated specifications without hosting them. Works for Swagger 2.0 specs only.
validatorUrl | By default, Swagger-UI attempts to validate specs against swagger.io's online validator. You can use this parameter to set a different validator URL, for example for locally deployed validators ([Validator Badge](https://github.com/swagger-api/validator-badge)). Setting it to `null` will disable validation. This parameter is relevant for Swagger 2.0 specs only.
dom_id | The id of a dom element inside which SwaggerUi will put the user interface for swagger.
booleanValues | SwaggerUI renders boolean data types as a dropdown. By default it provides a 'true' and 'false' string as the possible choices. You can use this parameter to change the values in dropdown to be something else, for example 0 and 1 by setting booleanValues to new Array(0, 1).
docExpansion | Controls how the API listing is displayed. It can be set to 'none' (default), 'list' (shows operations for each resource), or 'full' (fully expanded: shows operations and their details).
apisSorter | Apply a sort to the API/tags list. It can be 'alpha' (sort by name) or a function (see Array.prototype.sort() to know how sort function works). Default is the order returned by the server unchanged.
operationsSorter | Apply a sort to the operation list of each API. It can be 'alpha' (sort by paths alphanumerically), 'method' (sort by HTTP method) or a function (see Array.prototype.sort() to know how sort function works). Default is the order returned by the server unchanged.
defaultModelRendering | Controls how models are shown when the API is first rendered. (The user can always switch the rendering for a given model by clicking the 'Model' and 'Model Schema' links.) It can be set to 'model' or 'schema', and the default is 'schema'.
onComplete | This is a callback function parameter which can be passed to be notified of when SwaggerUI has completed rendering successfully.
onFailure | This is a callback function parameter which can be passed to be notified of when SwaggerUI encountered a failure was unable to render.
highlightSizeThreshold | Any size response below this threshold will be highlighted syntactically, attempting to highlight large responses can lead to browser hangs, not including a threshold will default to highlight all returned responses.
supportedSubmitMethods | An array of of the HTTP operations that will have the 'Try it out!' option. An empty array disables all operations. This does not filter the operations from the display.
oauth2RedirectUrl | OAuth redirect URL
showRequestHeaders | Whether or not to show the headers that were sent when making a request via the 'Try it out!' option. Defaults to `false`.
jsonEditor | Enables a graphical view for editing complex bodies.  Defaults to `false`.

* All other parameters are explained in greater detail below


### HTTP Methods and API Invocation
swagger-ui supports invocation of all HTTP methods APIs including GET, PUT, POST, DELETE, PATCH, OPTIONS.  These are handled in the [swagger-js](https://github.com/swagger-api/swagger-js) project, please see there for specifics on their usage.


### Header Parameters
Header params are supported through a pluggable mechanism in [swagger-js](https://github.com/swagger-api/swagger-js).  You can see the [index.html](https://github.com/swagger-api/swagger-ui/blob/master/dist/index.html) for a sample of how to dynamically set headers:

```js
// add a new SwaggerClient.ApiKeyAuthorization when the api-key changes in the ui.
$('#input_apiKey').change(function() {
  var key = $('#input_apiKey')[0].value;
  if(key && key.trim() != "") {
    swaggerUi.api.clientAuthorizations.add("auth_name", new SwaggerClient.ApiKeyAuthorization("api_key", key, "header"));
  }
})
```

This will add the header `api_key` with value `key` on calls that have the `auth_name` security scheme as part of their swaggerDefinitions.  You can substitute `query` to send the values as a query param.

### Custom Header Parameters - (For Basic auth etc)
If you have some header parameters which you need to send with every request, use the headers as below:

```js
swaggerUi.api.clientAuthorizations.add("key", new SwaggerClient.ApiKeyAuthorization("Authorization", "XXXX", "header"));
```

Note!  You can pass multiple header params on a single request, just use unique names for them (`key` is used in the above example).

### Localization and translation
The localization files are in the [lang](/lang) directory. Note that language files and translator is not included in SwaggerUI by default. You need to add them manually.

To enable translation you should append next two lines in your Swagger's index.html (or another entry point you use)
```html
<script src='lang/translator.js' type='text/javascript'></script>
<script src='lang/en.js' type='text/javascript'></script>
```
The first line script is a translator and the second one is your language lexemes.

If you wish to append support for new language you just need to create lang/your_lang.js and fill it like it's done in existing files.

To append new lexemex for translation you should do two things:
1. Add lexeme into the language file.
    Example of new line: "new sentence":"translation of new sentence".
2. Mark this lexeme in source html with attribute data-sw-translate.
    Example of changed source:
```html
<anyHtmlTag data-sw-translate>new sentence</anyHtmlTag>
or <anyHtmlTag data-sw-translate value='new sentence'/>
```
.

At this moment only inner html, title-attribute and value-attribute are going to be translated.

## CORS Support
### OR: How to deal with "Can't read from server. It may not have the appropriate access-control-origin settings."

CORS is a technique to prevent websites from doing bad things with your personal data.  Most browsers + javascript toolkits not only support CORS but enforce it, which has implications for your API server which supports Swagger.

You can read about CORS here: http://www.w3.org/TR/cors.

There are two cases where no action is needed for CORS support:

1. swagger-ui is hosted on the same server as the application itself (same host *and* port).
2. The application is located behind a proxy that enables the required CORS headers. This may already be covered within your organization.

Otherwise, CORS support needs to be enabled for:

1. Your Swagger docs. For Swagger 2.0 it's the `swagger.json` and any externally `$ref`ed docs, and for prior version it's the `Resource Listing` and `API Declaration` files.
2. For the `Try it now` button to work, CORS needs to be enabled on your API endpoints as well.

### Testing CORS Support

You can verify CORS support with one of three techniques:

- Curl your API and inspect the headers.  For instance:

```bash
$ curl -I "http://petstore.swagger.io/v2/swagger.json"
HTTP/1.1 200 OK
Date: Sat, 31 Jan 2015 23:05:44 GMT
Access-Control-Allow-Origin: *
Access-Control-Allow-Methods: GET, POST, DELETE, PUT, PATCH, OPTIONS
Access-Control-Allow-Headers: Content-Type, api_key, Authorization
Content-Type: application/json
Content-Length: 0
```

This tells us that the petstore resource listing supports OPTIONS, and the following headers:  `Content-Type`, `api_key`, `Authorization`.

- Try swagger-ui from your file system and look at the debug console.  If CORS is not enabled, you'll see something like this:

```
XMLHttpRequest cannot load http://sad.server.com/v2/api-docs. No 'Access-Control-Allow-Origin' header is present on the requested resource. Origin 'null' is therefore not allowed access.
```

Swagger-UI cannot easily show this error state.

- Using the http://www.test-cors.org website. Keep in mind this will show a successful result even if `Access-Control-Allow-Headers` is not available, which is still required for Swagger-UI to function properly.

### Enabling CORS

The method of enabling CORS depends on the server and/or framework you use to host your application. http://enable-cors.org provides information on how to enable CORS in some common web servers.

Other servers/frameworks may provide you information on how to enable it specifically in their use case.

### CORS and Header Parameters

Swagger lets you easily send headers as parameters to requests.  The name of these headers *MUST* be supported in your CORS configuration as well.  From our example above:

```
Access-Control-Allow-Headers: Content-Type, api_key, Authorization
```

Only headers with these names will be allowed to be sent by Swagger-UI.

## How to Improve It

Create your own fork of [swagger-api/swagger-ui](https://github.com/swagger-api/swagger-ui)

To share your changes, [submit a pull request](https://github.com/swagger-api/swagger-ui/pull/new/develop_2.0).

## Change Log
Please see [releases](https://github.com/swagger-api/swagger-ui/releases) for change log.

## License

Copyright 2016 SmartBear Software

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at [apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

---
<img src="http://swagger.io/wp-content/uploads/2016/02/logo.jpg"/>
