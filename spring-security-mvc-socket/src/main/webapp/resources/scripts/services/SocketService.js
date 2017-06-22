'use strict';

function SocketService() {
    var that = this;

    that.setConnected = function (connected) {
        document.getElementById('connect').disabled = connected;
        document.getElementById('disconnect').disabled = !connected;
        document.getElementById('conversationDiv').style.visibility = connected ? 'visible' : 'hidden';
        document.getElementById('response').innerHTML = '';
    };

    that.authenticate = function (context) {
        var socket = new SockJS(context + '/secured/chat'),
            stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            that.setConnected(true);
            console.log('Connected: ' + frame);
            stompClient.subscribe('/secured/topic/messages', function (messageOutput) {
                that.showMessageOutput(JSON.parse(messageOutput.body));
            });
        });
        return stompClient;
    };

    that.connect = function (context) {
        var socket = new SockJS(context + '/secured/chat'),
            stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            that.setConnected(true);
            console.log('Connected: ' + frame);
            stompClient.subscribe('/secured/topic/messages', function (messageOutput) {
                that.showMessageOutput(JSON.parse(messageOutput.body));
            });
        });
        return stompClient;
    };

    that.disconnect = function (stompClient) {
        if (stompClient != null && stompClient != undefined) {
            stompClient.disconnect();
        }
        that.setConnected(false);
        console.log("Disconnected");
    };

    that.sendMessage = function (stompClient, context) {
        var from = document.getElementById('from').value,
            text = document.getElementById('text').value;
        stompClient.send('/secured/chat', {},
            JSON.stringify({'from': from, 'text': text}));
    };

    that.showMessageOutput = function (messageOutput) {
        var response = document.getElementById('response'),
            p = document.createElement('p');
        p.style.wordWrap = 'break-word';
        p.appendChild(document.createTextNode(messageOutput.from + ': ' + messageOutput.text + ' (" + messageOutput.time + ")'));
        response.appendChild(p);
    }
}

angularApp
    .service('SocketService', SocketService);