(function() {
    'use strict';

    angular
        .module('baeldungApp')
        .controller('SessionsController', SessionsController);

    SessionsController.$inject = ['Sessions', 'Principal'];

    function SessionsController (Sessions, Principal) {
        var vm = this;

        vm.account = null;
        vm.error = null;
        vm.invalidate = invalidate;
        vm.sessions = Sessions.getAll();
        vm.success = null;


        Principal.identity().then(function(account) {
            vm.account = account;
        });

        function invalidate (series) {
            Sessions.delete({series: encodeURIComponent(series)},
                function () {
                    vm.error = null;
                    vm.success = 'OK';
                    vm.sessions = Sessions.getAll();
                },
                function () {
                    vm.success = null;
                    vm.error = 'ERROR';
                });
        }
    }
})();
