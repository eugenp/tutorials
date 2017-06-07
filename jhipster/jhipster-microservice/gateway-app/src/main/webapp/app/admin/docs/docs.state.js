(function() {
    'use strict';

    angular
        .module('gatewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig ($stateProvider) {
        $stateProvider.state('docs', {
            parent: 'admin',
            url: '/docs',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'API'
            },
            views: {
                'content@': {
                    templateUrl: 'app/admin/docs/docs.html'
                }
            }
        });
    }
})();
