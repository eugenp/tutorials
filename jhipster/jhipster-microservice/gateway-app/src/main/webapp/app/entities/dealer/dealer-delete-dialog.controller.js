(function() {
    'use strict';

    angular
        .module('gatewayApp')
        .controller('DealerDeleteController',DealerDeleteController);

    DealerDeleteController.$inject = ['$uibModalInstance', 'entity', 'Dealer'];

    function DealerDeleteController($uibModalInstance, entity, Dealer) {
        var vm = this;

        vm.dealer = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Dealer.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
