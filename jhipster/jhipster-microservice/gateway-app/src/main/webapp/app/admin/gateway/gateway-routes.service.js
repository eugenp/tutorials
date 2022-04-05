(function() {
    'use strict';

    angular
        .module('gatewayApp')
        .factory('GatewayRoutes', GatewayRoutes);

    GatewayRoutes.$inject = ['$resource'];

    function GatewayRoutes ($resource) {
        var service = $resource('api/gateway/routes/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });

        return service;
    }

})();
