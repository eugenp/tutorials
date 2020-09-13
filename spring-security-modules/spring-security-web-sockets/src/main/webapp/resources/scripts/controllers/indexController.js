'use strict';

angularApp
.controller('indexController', function ($scope) {

  $scope.greeting = '';

  $scope.initialize = function () {
    $scope.greeting = "Howdy!"
  };

  $scope.initialize();
});