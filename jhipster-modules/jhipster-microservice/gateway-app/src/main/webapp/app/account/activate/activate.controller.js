(function() {
    'use strict';

    angular
        .module('gatewayApp')
        .controller('ActivationController', ActivationController);

    ActivationController.$inject = ['$stateParams', 'Auth', 'LoginService'];

    function ActivationController ($stateParams, Auth, LoginService) {
        var vm = this;

        Auth.activateAccount({key: $stateParams.key}).then(function () {
            vm.error = null;
            vm.success = 'OK';
        }).catch(function () {
            vm.success = null;
            vm.error = 'ERROR';
        });

        vm.login = LoginService.open;
    }
})();
