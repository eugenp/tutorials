(function() {
    'use strict';

    angular
        .module('gatewayApp')
        .config(compileServiceConfig);

    compileServiceConfig.$inject = ['$compileProvider','DEBUG_INFO_ENABLED'];

    function compileServiceConfig($compileProvider,DEBUG_INFO_ENABLED) {
        // disable debug data on prod profile to improve performance
        $compileProvider.debugInfoEnabled(DEBUG_INFO_ENABLED);

        /*
        If you wish to debug an application with this information
        then you should open up a debug console in the browser
        then call this method directly in this console:

		angular.reloadWithDebugInfo();
		*/
    }
})();
