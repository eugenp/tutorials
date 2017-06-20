'use strict';

angularApp
    .factory('Form', function ($http, $q) {
        return {
            post: function (context, username, password) {
                var deferred = $q.defer();

                $http({
                    method: 'POST',
                    url: context + '/authenticate',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                    data: $.param({username: username, password: password})
                }).then(function (response) {
                    deferred.resolve(response);
                });

                return deferred.promise;
            }
        }
    });