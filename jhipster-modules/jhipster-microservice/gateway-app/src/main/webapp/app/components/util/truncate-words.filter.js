(function() {
    'use strict';

    angular
        .module('gatewayApp')
        .filter('words', words);

    function words() {
        return wordsFilter;

        function wordsFilter(input, words) {
            if (isNaN(words)) {
                return input;
            }
            if (words <= 0) {
                return '';
            }
            if (input) {
                var inputWords = input.split(/\s+/);
                if (inputWords.length > words) {
                    input = inputWords.slice(0, words).join(' ') + '...';
                }
            }
            return input;
        }
    }
})();
