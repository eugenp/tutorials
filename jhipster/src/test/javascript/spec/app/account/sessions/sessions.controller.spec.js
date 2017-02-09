'use strict';

describe('Controller Tests', function() {

    beforeEach(mockApiAccountCall);
    beforeEach(mockI18nCalls);

    describe('SessionsController', function() {

        var $scope, $q; // actual implementations
        var MockSessions, MockPrincipal; // mocks
        var createController; // local utility function

        var sessions = [{
            tokenDate: '2015-10-15',
            ipAddress: '0:0:0:0:0:0:0:1',
            series: 'xxxxxx==',
            userAgent: 'Mozilla/5.0'
        }];

        beforeEach(inject(function($injector) {
            $q = $injector.get('$q');
            $scope = $injector.get('$rootScope').$new();
            MockSessions = jasmine.createSpyObj('MockSessions', ['getAll', 'delete']);
            MockPrincipal = jasmine.createSpyObj('MockPrincipal', ['identity']);

            var locals = {
                '$scope': $scope,
                'Sessions': MockSessions,
                'Principal': MockPrincipal
            };
            createController = function() {
                return $injector.get('$controller')('SessionsController as vm', locals);
            };
        }));

        it('should define its initial state', function() {
            MockPrincipal.identity.and.returnValue($q.resolve({
                id: 'fuzzer'
            }));
            MockSessions.getAll.and.returnValue(sessions);
            // given
            $scope.$apply(createController);
            // then
            expect(MockPrincipal.identity).toHaveBeenCalled();
            expect(MockSessions.getAll).toHaveBeenCalled();
            expect($scope.vm.success).toBeNull();
            expect($scope.vm.error).toBeNull();
            expect($scope.vm.account).toEqual({
                id: 'fuzzer'
            });
            expect($scope.vm.sessions).toEqual(sessions);
        });

        it('should call delete on Sessions to invalidate a session', function() {
            MockPrincipal.identity.and.returnValue($q.resolve({
                id: 'fuzzer'
            }));
            MockSessions.getAll.and.returnValue(sessions);
            // given
            createController();
            $scope.vm.invalidate('xyz');
            $scope.$apply();
            // then
            expect(MockSessions.delete).toHaveBeenCalledWith({
                series: 'xyz'
            }, jasmine.any(Function), jasmine.any(Function));
        });

        it('should call delete on Sessions and notify of error', function() {
            MockPrincipal.identity.and.returnValue($q.resolve({
                id: 'fuzzer'
            }));
            MockSessions.getAll.and.returnValue(sessions);
            MockSessions.delete.and.callFake(function resourceDelete(arg, success, failure) {
                failure();
            });
            // given
            createController();
            $scope.vm.invalidate('xyz');
            $scope.$apply();
            // then
            expect($scope.vm.success).toBeNull();
            expect($scope.vm.error).toBe('ERROR');
        });

        it('should call notify of success upon session invalidation', function() {
            MockPrincipal.identity.and.returnValue($q.resolve({
                id: 'fuzzer'
            }));
            MockSessions.getAll.and.returnValue(sessions);
            MockSessions.delete.and.callFake(function resourceDelete(arg, success, failure) {
                success();
            });
            // given
            createController();
            $scope.vm.invalidate('xyz');
            $scope.$apply();
            // then
            expect($scope.vm.error).toBeNull();
            expect($scope.vm.success).toBe('OK');
        });
    });
});
