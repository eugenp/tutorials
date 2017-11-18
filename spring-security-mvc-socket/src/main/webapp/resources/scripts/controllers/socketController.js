'use strict';

angularApp
    .controller('socketController', function ($scope, SocketService) {

        $scope.stompClient = null;
        $scope.sendEndpoint = '/secured/chat';
        $scope.subscribeEndpoint = '/secured/history';
        $scope.elems = {
            connect: 'connect',
            from: 'from',
            text: 'text',
            disconnect: 'disconnect',
            conversationDiv: 'conversationDiv',
            response: 'response'
        };

        $scope.connect = function (context) {
            $scope.sendEndpoint = '/secured/chat';
            $scope.sendEndpoint = context + $scope.sendEndpoint ;
            $scope.stompClient = SocketService.connect($scope.sendEndpoint, $scope.elems);
        };

        $scope.subscribe = function () {
            $scope.stompClient.subscribe($scope.subscribeEndpoint, function (msgOut) {
                SocketService.messageOut(JSON.parse(msgOut.body), $scope.elems);
            });
        };

        $scope.disconnect = function () {
            SocketService.disconnect($scope.elems, $scope.stompClient);
        };

        $scope.sendMessage = function () {
            SocketService.sendMessage( $scope.elems, $scope.stompClient, $scope.sendEndpoint);
        };
    });