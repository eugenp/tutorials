$(function () {
    var node1 = document.createTextNode("message = " + message);
    var node2 = document.createTextNode("number = " + number);
    document.getElementById('message-async-jquery').append(node1);
    document.getElementById('number-async-jquery').append(node2);
});
