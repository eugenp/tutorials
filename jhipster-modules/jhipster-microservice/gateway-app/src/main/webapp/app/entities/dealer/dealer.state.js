(function() {
    'use strict';

    angular
        .module('gatewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('dealer', {
            parent: 'entity',
            url: '/dealer',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Dealers'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/dealer/dealers.html',
                    controller: 'DealerController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('dealer-detail', {
            parent: 'dealer',
            url: '/dealer/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Dealer'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/dealer/dealer-detail.html',
                    controller: 'DealerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Dealer', function($stateParams, Dealer) {
                    return Dealer.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'dealer',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('dealer-detail.edit', {
            parent: 'dealer-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dealer/dealer-dialog.html',
                    controller: 'DealerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Dealer', function(Dealer) {
                            return Dealer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('dealer.new', {
            parent: 'dealer',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dealer/dealer-dialog.html',
                    controller: 'DealerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                address: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('dealer', null, { reload: 'dealer' });
                }, function() {
                    $state.go('dealer');
                });
            }]
        })
        .state('dealer.edit', {
            parent: 'dealer',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dealer/dealer-dialog.html',
                    controller: 'DealerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Dealer', function(Dealer) {
                            return Dealer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('dealer', null, { reload: 'dealer' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('dealer.delete', {
            parent: 'dealer',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dealer/dealer-delete-dialog.html',
                    controller: 'DealerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Dealer', function(Dealer) {
                            return Dealer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('dealer', null, { reload: 'dealer' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
