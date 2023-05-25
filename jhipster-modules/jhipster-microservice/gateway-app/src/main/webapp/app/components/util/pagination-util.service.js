(function () {
    'use strict';

    angular
        .module('gatewayApp')
        .factory('PaginationUtil', PaginationUtil);

    function PaginationUtil () {

        var service = {
            parseAscending : parseAscending,
            parsePage : parsePage,
            parsePredicate : parsePredicate
        };

        return service;

        function parseAscending (sort) {
            var sortArray = sort.split(',');
            if (sortArray.length > 1){
                return sort.split(',').slice(-1)[0] === 'asc';
            } else {
                // default to true if no sort defined
                return true;
            }
        }

        // query params are strings, and need to be parsed
        function parsePage (page) {
            return parseInt(page);
        }

        // sort can be in the format `id,asc` or `id`
        function parsePredicate (sort) {
            var sortArray = sort.split(',');
            if (sortArray.length > 1){
                sortArray.pop();
            }
            return sortArray.join(',');
        }
    }
})();
