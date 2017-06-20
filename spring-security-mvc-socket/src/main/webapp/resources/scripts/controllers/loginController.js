'use strict';

angularApp
    .controller('loginController', function ($scope, Form) {

        $scope.postForm = function (context) {
            var userField = document.getElementById('userField'),
                passwordField = document.getElementById('passwordField'),
                data = {};

            if (userField != '' && passwordField != '') {
                data.username = userField.value;
                data.password = passwordField.value;
            }
            
           Form.post(context, data.username, data.password).then(function (authenticated) {
                console.log("You haz attempted to be authenticated: " + authenticated);
            });
        };

    });
