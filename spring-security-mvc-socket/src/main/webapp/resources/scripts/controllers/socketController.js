'use strict';

angularApp
.controller('socketController', function ($scope, SocketService) {

  /**
   * URL mapping endpoints.
   */

  var SECURED_CHAT = '/secured/chat';
  var SECURED_CHAT_HISTORY = '/secured/history';
  var SECURED_CHAT_ROOM = '/secured/room';
  var SECURED_CHAT_SPECIFIC_USER = '/secured/user/queue/specific-user';

  var opts = {
    from: 'from',
    to: 'to',
    text: 'text',
    disconnect: 'disconnect',
    conversationDiv: 'conversationDiv',
    response: 'response'
  };

  $scope.sendEndpoint = '';
  $scope.stompClient = null;

  /**
   * Broadcast to All Users.
   */

  $scope.connectAll = function (context) {
    $scope.sendEndpoint = context + SECURED_CHAT;
    $scope.stompClient = SocketService.connect($scope.sendEndpoint , opts, true);
  };

  $scope.subscribeAll = function () { SocketService.subscribeToAll($scope.stompClient, SECURED_CHAT_HISTORY, opts); };

  /**
   * Broadcast to Specific User.
   */

  $scope.connectSpecific = function (context) {
    $scope.sendEndpoint = context + SECURED_CHAT_ROOM;
    $scope.stompClient = SocketService.connect(context + SECURED_CHAT_ROOM, opts, false);
  };

  $scope.subscribeSpecific = function () { SocketService.subscribeToSpecific($scope.stompClient, SECURED_CHAT_SPECIFIC_USER, opts); };

  $scope.disconnect = function () { SocketService.disconnect(opts, $scope.stompClient); };

  $scope.sendMessage = function () { SocketService.sendMessage(opts, $scope.stompClient, $scope.sendEndpoint); };
});