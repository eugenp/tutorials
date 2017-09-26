(function() {
    'use strict';

    angular
        .module('gatewayApp')
        .controller('DealerDialogController', DealerDialogController);

    DealerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Dealer'];

    function DealerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Dealer) {
        var vm = this;

        vm.dealer = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.dealer.id !== null) {
                Dealer.update(vm.dealer, onSaveSuccess, onSaveError);
            } else {
                Dealer.save(vm.dealer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('gatewayApp:dealerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
