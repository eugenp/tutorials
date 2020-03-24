'use strict';

angularApp
.controller('successController', function ($scope) {
  $scope.successMsg = '';

  $scope.initialize = function () {
    $scope.successMsg = "You've logged in!";
  };

  $scope.initialize();
});
