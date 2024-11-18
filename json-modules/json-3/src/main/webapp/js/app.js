'use strict';

var app = angular.module('jsonforms-intro', ['jsonforms']);
app.controller('MyController', ['$scope', 'Schema', 'UISchema', function($scope, Schema, UISchema) {

    $scope.schema = Schema;

    $scope.uiSchema = UISchema;

    $scope.data = {
        "id": 1,
        "name": "Lampshade",
        "price": 1.85
    };
}]);
