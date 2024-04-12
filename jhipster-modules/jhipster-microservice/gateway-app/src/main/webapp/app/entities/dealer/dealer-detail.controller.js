(function() {
    'use strict';

    angular
        .module('gatewayApp')
        .controller('DealerDetailController', DealerDetailController);

    DealerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Dealer'];

    function DealerDetailController($scope, $rootScope, $stateParams, previousState, entity, Dealer) {
        var vm = this;

        vm.dealer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('gatewayApp:dealerUpdate', function(event, result) {
            vm.dealer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
