angularApp
    .controller('requestBody', function ($scope, Form) {
        $scope.postForm = function (context) {
            var userField = document.getElementById('userField');
            var passwordField = document.getElementById('passwordField');
            if (userField.value != '' && passwordField.value != '') {
                Form.post(context, userField.value,
                    passwordField.value).then(function (v) {

                });
            }
        };
    })

    .controller('responseBody', function ($scope, Form) {
        $scope.response = "RESPONSEBODY WILL SHOW HERE";
        $scope.postForm = function (context) {
            var userField = document.getElementById('userField');
            var passwordField = document.getElementById('passwordField');
            if (userField.value != '' && passwordField.value != '') {
                Form.post(context, userField.value,
                    passwordField.value).then(function (v) {
                    $scope.response = v;
                });
            }
        };
    });