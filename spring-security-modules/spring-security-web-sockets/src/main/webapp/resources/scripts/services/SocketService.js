'use strict';

var idHelper = function (context) {
  return document.getElementById(context);
};

function SocketService() {

  var that = this;

  that.sessionId = '';

  /**
   * Generic methods.
   */

  that.setConnected = function (opts, connected) {
    idHelper('connectAll').disabled = connected;
    idHelper('connectSpecific').disabled = connected;
    idHelper(opts.disconnect).disabled = !connected;
    idHelper(opts.response).innerHTML = '';
    idHelper('subscribeAll').disabled = !connected;
    idHelper('subscribeSpecific').disabled = !connected;
  };

  that.connect = function (endpoint, opts, isBroadcastAll) {
    var socket = new SockJS(endpoint), stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
      that.setConnected(opts, true);

      if (!isBroadcastAll) {
        var url = stompClient.ws._transport.url;
        console.log(stompClient.ws._transport.url);
        url = url.replace("ws://localhost:8080/spring-security-mvc-socket/secured/room/",  "");
        url = url.replace("/websocket", "");
        url = url.replace(/^[0-9]+\//, "");
        console.log("Your current session is: " + url);
        that.sessionId = url;
      }

    });
    return stompClient;
  };

  that.disconnect = function (opts, stompClient) {
    if (stompClient !== null && stompClient !== undefined) { stompClient.disconnect(); }
    that.setConnected(opts, false);
  };

  that.sendMessage = function (opts, stompClient, endpoint) {
    var to = idHelper(opts.to).value;
    var from = idHelper(opts.from).value;

    var msg = {
      'from': (from === undefined || from === null ) ? to : from,
      'to': (to === undefined || to === null ) ? "ALL" : to,
      'text': idHelper(opts.text).value
    };

    console.log(JSON.stringify(msg));
    stompClient.send(endpoint, {}, JSON.stringify(msg));
  };

  that.messageOut = function (msg, opts) {
    var r = idHelper(opts.response), p = document.createElement('p');
    p.style.wordWrap = 'break-word';
    p.appendChild(document.createTextNode(msg.from + ': ' + msg.text + ' (' + msg.time + ')'));
    r.appendChild(p);
  };

  /**
   * Broadcast to All Users.
   */

  that.subscribeToAll = function(client, url, opts) {
    idHelper('subscribeAll').disabled = true;
    idHelper('subscribeSpecific').disabled = true;
    client.subscribe(url, function (msgOut) {
      that.messageOut(JSON.parse(msgOut.body), opts);
    });
  };

  /**
   * Broadcast to Specific User.
   */

  that.subscribeToSpecific = function(client, url, opts) {
    idHelper('subscribeAll').disabled = true;
    idHelper('subscribeSpecific').disabled = true;
    client.subscribe(url + "-user" + that.sessionId, function (msgOut) {
      that.messageOut(JSON.parse(msgOut.body), opts);
    });
  };


}

angularApp
.service('SocketService', SocketService);