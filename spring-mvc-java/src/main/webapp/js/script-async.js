window.onload = function () {
    var node1 = document.createTextNode("message = " + message);
    var node2 = document.createTextNode("number = " + number);
    document.getElementById('message-async').append(node1);
    document.getElementById('number-async').append(node2);
};
