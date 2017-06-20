'use strict';

angularApp
    .controller('successController', function ($scope, Pets) {

        $scope.successMsg = '';
        $scope.pets = [];

        $scope.initialize = function () {
            $scope.successMsg = "You've logged in!";
            $scope.pets = Pets.get();
        };
    });
