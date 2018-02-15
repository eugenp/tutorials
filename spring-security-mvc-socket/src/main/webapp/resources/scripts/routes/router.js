'use strict';

angularApp
	.config(function ($routeProvider) {
		$routeProvider
			.when('/index', {
				controller: 'indexController'
			})
			.when('/login', {
				controller: 'loginController'
			})
			.when('/sockets', {
				controller: 'socketController'
			})
			.when('/success', {
				controller: 'successController'
			})
			.otherwise('/index');
	});