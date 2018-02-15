'use strict';

function SocketService() {
    var that = this,
        idHelper = function(context) {
            return document.getElementById(context);
        };

    that.setConnected = function (elems, connected) {
        idHelper(elems.connect).disabled = connected;
        idHelper(elems.disconnect).disabled = !connected;
        idHelper(elems.conversationDiv).style.visibility = connected ? 'visible' : 'hidden';
        idHelper(elems.response).innerHTML = '';
    };

    that.connect = function (endpoint, elems) {
        var socket = new SockJS(endpoint), stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            that.setConnected(elems, true);
        });
        return stompClient;
    };

    that.disconnect = function (elems, stompClient) {
        if (stompClient !== null && stompClient !== undefined) stompClient.disconnect();
        that.setConnected(elems, false);
    };

    that.sendMessage = function (elems, stompClient, endpoint) {
        stompClient.send(endpoint, {},
            JSON.stringify({
                'from': idHelper(elems.from).value,
                'text': idHelper(elems.text).value
            }));
    };

    that.messageOut = function (msg, elems) {
        var r = idHelper(elems.response), p = document.createElement('p');
        p.style.wordWrap = 'break-word';
        p.appendChild(document.createTextNode(msg.from + ': ' + msg.text + ' (' + msg.time + ')'));
        r.appendChild(p);
    };
}

angularApp
    .service('SocketService', SocketService);