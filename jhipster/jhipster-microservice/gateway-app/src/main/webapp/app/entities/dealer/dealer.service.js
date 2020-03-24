(function() {
    'use strict';
    angular
        .module('gatewayApp')
        .factory('Dealer', Dealer);

    Dealer.$inject = ['$resource'];

    function Dealer ($resource) {
        var resourceUrl =  'dealerapp/' + 'api/dealers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
