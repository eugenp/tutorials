var request = new XMLHttpRequest()

function sendName() {  
    request.open('GET', 'http://localhost:8080/rest/'+$("#name").val(), true)
    request.onload = function () {
    	var data = JSON.parse(this.response)
    	showGreeting(data.greeting)
    }
    request.send()
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#send" ).click(function() { sendName(); });
});