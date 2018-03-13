var app = angular.module('app', ['ngRoute']);

var restClient = new $.RestClient('', {
    stringifyData: true
});
restClient.add("billings");

app.config(specifyRoutes);

function specifyRoutes($routeProvider) {
    $routeProvider.when('/', {
        templateUrl: 'pages/billings.html',
        controller: 'BillingCtrl'
    }).otherwise({
        templateUrl: 'pages/404.html'
    });
}

$(function () {
    var requests = [];
    for (var i = 0; i < 2; i++) {
        var request = $.ajax({
            url: "billings"
        });
        requests.push(request);
    }
    for (var i = 0; i < 2; i++) {
        var request = $.ajax({
            url: "billings"
        });
        requests.push(request);
    }
    $.when.apply($, requests).done(function (data) {
        console.log("all requests succeed");
    }).fail(function () {
        console.log("some requests failed");
    });

});

