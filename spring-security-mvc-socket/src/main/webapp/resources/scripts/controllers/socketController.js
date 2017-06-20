'use strict';

angularApp
    .controller('socketController', function ($scope, SocketService) {

        $scope.stompClient = null;

        $scope.connect = function (context) {
            $scope.stompClient = SocketService.connect(context);
        };

        $scope.disconnect = function () {
            SocketService.disconnect($scope.stompClient);
        };

        $scope.sendMessage = function (context) {
            SocketService.sendMessage($scope.stompClient, context);
        };

        $scope.authenticate = function (context) {
            $scope.stompClient = SocketService.authenticate(context);
        };
    });