(function() {
    'use strict';

    angular
        .module('gatewayApp')
        .factory('notificationInterceptor', notificationInterceptor);

    notificationInterceptor.$inject = ['$q', 'AlertService'];

    function notificationInterceptor ($q, AlertService) {
        var service = {
            response: response
        };

        return service;

        function response (response) {
            var headers = Object.keys(response.headers()).filter(function (header) {
                return header.indexOf('app-alert', header.length - 'app-alert'.length) !== -1 || header.indexOf('app-params', header.length - 'app-params'.length) !== -1;
            }).sort();
            var alertKey = response.headers(headers[0]);
            if (angular.isString(alertKey)) {
                AlertService.success(alertKey, { param : response.headers(headers[1])});
            }
            return response;
        }
    }
})();
