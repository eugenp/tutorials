(function() {
    'use strict';

    angular
        .module('gatewayApp')
        .controller('GatewayController', GatewayController);

    GatewayController.$inject = ['$filter', '$interval', 'GatewayRoutes'];

    function GatewayController ($filter, $interval, GatewayRoutes) {
        var vm = this;

        vm.gatewayRoutes = null;
        vm.refresh = refresh;
        vm.updatingRoutes = null;

        vm.refresh();

        function refresh () {
            vm.updatingRoutes = true;
            GatewayRoutes.query(function(result) {
                vm.gatewayRoutes = result;
                vm.updatingRoutes = false;
            });
        }
    }

})();
