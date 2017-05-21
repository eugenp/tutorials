(function() {
    'use strict';

    angular
        .module('gatewayApp')
        .controller('JhiConfigurationController', JhiConfigurationController);

    JhiConfigurationController.$inject = ['$filter','JhiConfigurationService'];

    function JhiConfigurationController (filter,JhiConfigurationService) {
        var vm = this;

        vm.allConfiguration = null;
        vm.configuration = null;

        JhiConfigurationService.get().then(function(configuration) {
            vm.configuration = configuration;
        });
        JhiConfigurationService.getEnv().then(function (configuration) {
            vm.allConfiguration = configuration;
        });
    }
})();
