(function() {
    'use strict';

    angular
        .module('baeldungApp')
        .factory('authExpiredInterceptor', authExpiredInterceptor);

    
    authExpiredInterceptor.$inject = ['$rootScope', '$q', '$injector', '$document'];

    function authExpiredInterceptor($rootScope, $q, $injector, $document) {
        var service = {
            responseError: responseError
        };

        return service;

        function responseError(response) {
            // If we have an unauthorized request we redirect to the login page
            // Don't do this check on the account API to avoid infinite loop
            if (response.status === 401 && angular.isDefined(response.data.path) && response.data.path.indexOf('/api/account') === -1) {
                var Auth = $injector.get('Auth');
                var to = $rootScope.toState;
                var params = $rootScope.toStateParams;
                Auth.logout();
                if (to.name !== 'accessdenied') {
                    Auth.storePreviousState(to.name, params);
                }
                var LoginService = $injector.get('LoginService');
                LoginService.open();
            } else if (response.status === 403 && response.config.method !== 'GET' && getCSRF() === '') {
                // If the CSRF token expired, then try to get a new CSRF token and retry the old request
                var $http = $injector.get('$http');
                return $http.get('/').finally(function() { return afterCSRFRenewed(response); });
            }
            return $q.reject(response);
        }

        function getCSRF() {
            var doc = $document[0];
            if (doc) {
                var name = 'CSRF-TOKEN=';
                var ca = doc.cookie.split(';');
                for (var i = 0; i < ca.length; i++) {
                    var c = ca[i];
                    while (c.charAt(0) === ' ') {c = c.substring(1);}

                    if (c.indexOf(name) !== -1) {
                        return c.substring(name.length, c.length);
                    }
                }
            }
            return '';
        }

        function afterCSRFRenewed(oldResponse) {
            if (getCSRF() !== '') {
                // retry the old request after the new CSRF-TOKEN is obtained
                var $http = $injector.get('$http');
                return $http(oldResponse.config);
            } else {
                // unlikely get here but reject with the old response any way and avoid infinite loop
                return $q.reject(oldResponse);
            }
        }
    }
})();
