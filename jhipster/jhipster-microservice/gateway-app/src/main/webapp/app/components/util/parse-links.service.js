(function(){
    'use strict';

    angular
        .module('gatewayApp')
        .factory('ParseLinks', ParseLinks);

    function ParseLinks () {

        var service = {
            parse : parse
        };

        return service;

        function parse(header) {
            if (header.length === 0) {
                throw new Error('input must not be of zero length');
            }

            // Split parts by comma
            var parts = header.split(',');
            var links = {};
            // Parse each part into a named link
            angular.forEach(parts, function(p) {
                var section = p.split('>;');
                if (section.length !== 2) {
                    throw new Error('section could not be split on ">;"');
                }
                var url = section[0].replace(/<(.*)/, '$1').trim();
                var queryString = {};
                url.replace(
                    new RegExp('([^?=&]+)(=([^&]*))?', 'g'),
                    function($0, $1, $2, $3) { queryString[$1] = $3; }
                );
                var page = queryString.page;
                if (angular.isString(page)) {
                    page = parseInt(page);
                }
                var name = section[1].replace(/rel="(.*)"/, '$1').trim();
                links[name] = page;
            });
            return links;
        }
    }
})();
