(function() {
    'use strict';

    angular
        .module('gatewayApp')
        .directive('jhSort', jhSort);

    function jhSort () {
        var directive = {
            restrict: 'A',
            scope: {
                predicate: '=jhSort',
                ascending: '=',
                callback: '&'
            },
            controller: SortController,
            controllerAs: 'vm',
            bindToController: true
        };

        return directive;
    }

    SortController.$inject = ['$scope', '$element'];

    function SortController ($scope, $element) {
        var vm = this;

        vm.applyClass = applyClass;
        vm.resetClasses = resetClasses;
        vm.sort = sort;
        vm.triggerApply = triggerApply;

        $scope.$watchGroup(['vm.predicate', 'vm.ascending'], vm.triggerApply);
        vm.triggerApply();

        function applyClass (element) {
            var thisIcon = element.find('span.glyphicon'),
                sortIcon = 'glyphicon-sort',
                sortAsc = 'glyphicon-sort-by-attributes',
                sortDesc = 'glyphicon-sort-by-attributes-alt',
                remove = sortIcon + ' ' + sortDesc,
                add = sortAsc;
            if (!vm.ascending) {
                remove = sortIcon + ' ' + sortAsc;
                add = sortDesc;
            }
            vm.resetClasses();
            thisIcon.removeClass(remove);
            thisIcon.addClass(add);
        }

        function resetClasses () {
            var allThIcons = $element.find('span.glyphicon'),
                sortIcon = 'glyphicon-sort',
                sortAsc = 'glyphicon-sort-by-attributes',
                sortDesc = 'glyphicon-sort-by-attributes-alt';
            allThIcons.removeClass(sortAsc + ' ' + sortDesc);
            allThIcons.addClass(sortIcon);
        }

        function sort (field) {
            if (field !== vm.predicate) {
                vm.ascending = true;
            } else {
                vm.ascending = !vm.ascending;
            }
            vm.predicate = field;
            $scope.$apply();
            vm.callback();
        }

        function triggerApply (values)  {
            vm.resetClasses();
            if (values && values[0] !== '_score') {
                vm.applyClass($element.find('th[jh-sort-by=\'' + values[0] + '\']'));
            }
        }
    }
})();
