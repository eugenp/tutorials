(function() {
    'use strict';

    angular
        .module('gatewayApp')
        .directive('pageRibbon', pageRibbon);

    pageRibbon.$inject = ['ProfileService', '$rootScope'];

    function pageRibbon(ProfileService, $rootScope) {
        var directive = {
            replace : true,
            restrict : 'AE',
            template : '<div class="ribbon hidden"><a href="">{{ribbonEnv}}</a></div>',
            link : linkFunc
        };

        return directive;

        function linkFunc(scope, element, attrs) {
            ProfileService.getProfileInfo().then(function(response) {
                if (response.ribbonEnv) {
                    scope.ribbonEnv = response.ribbonEnv;
                    element.addClass(response.ribbonEnv);
                    element.removeClass('hidden');
                }
            });
        }
    }
})();
