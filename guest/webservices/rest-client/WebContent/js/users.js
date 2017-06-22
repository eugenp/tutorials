var app = angular.module('App', [ 'ngRoute', 'ngResource' ]);

app.controller("UsersCtrl", [ '$scope', '$resource',
		function($scope, $resource) {

			var url = "http://localhost:8080/rest-server";

			var User = $resource(url + '/users');

			$scope.users = User.query();

			$scope.addUser = function() {

				var json = {
					'email' : $scope.email,
					'name' : $scope.name
				};
				User.save(json, function() {
					$scope.email = '';
					$scope.name = '';
					$scope.users = User.query();
				});
			};
		} ]);
