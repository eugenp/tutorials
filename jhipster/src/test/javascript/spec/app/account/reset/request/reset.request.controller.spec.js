'use strict';

describe('Controller Tests', function() {

    beforeEach(mockApiAccountCall);
    beforeEach(mockI18nCalls);

    describe('RequestResetController', function() {

        var $rootScope, $scope, $q; // actual implementations
        var MockState, MockTimeout, MockAuth; // mocks
        var createController; // local utility function

        beforeEach(inject(function($injector) {
            $q = $injector.get('$q');
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockState = jasmine.createSpy('MockState');
            MockTimeout = jasmine.createSpy('MockTimeout');
            MockAuth = jasmine.createSpyObj('MockAuth', ['resetPasswordInit']);

            var locals = {
                '$rootScope': $rootScope,
                '$scope': $scope,
                '$state': MockState,
                '$timeout': MockTimeout,
                'Auth': MockAuth
            };
            createController = function() {
                return $injector.get('$controller')('RequestResetController as vm', locals);
            };
        }));

        it('should define its initial state', function() {
            // given
            createController();

            // then
            expect($scope.vm.success).toBeNull();
            expect($scope.vm.error).toBeNull();
            expect($scope.vm.errorEmailNotExists).toBeNull();
            expect($scope.vm.resetAccount).toEqual({});
        });

        it('registers a timeout handler set set focus', function() {
            // given
            var MockAngular = jasmine.createSpyObj('MockAngular', ['element']);
            var MockElement = jasmine.createSpyObj('MockElement', ['focus']);
            MockAngular.element.and.returnValue(MockElement);
            MockTimeout.and.callFake(function(callback) {
                withMockedAngular(MockAngular, callback)();
            });
            createController();

            // then
            expect(MockTimeout).toHaveBeenCalledWith(jasmine.any(Function));
            expect(MockAngular.element).toHaveBeenCalledWith('#email');
            expect(MockElement.focus).toHaveBeenCalled();
        });

        it('notifies of success upon successful requestReset', function() {
            // given
            MockAuth.resetPasswordInit.and.returnValue($q.resolve());
            createController();
            $scope.vm.resetAccount.email = 'user@domain.com';
            // when
            $scope.$apply($scope.vm.requestReset);
            // then
            expect(MockAuth.resetPasswordInit).toHaveBeenCalledWith('user@domain.com');
            expect($scope.vm.success).toEqual('OK');
            expect($scope.vm.error).toBeNull();
            expect($scope.vm.errorEmailNotExists).toBeNull();
        });
        it('notifies of unknown email upon e-mail address not registered/400', function() {
            // given
            MockAuth.resetPasswordInit.and.returnValue($q.reject({
                status: 400,
                data: 'e-mail address not registered'
            }));
            createController();
            $scope.vm.resetAccount.email = 'user@domain.com';
            // when
            $scope.$apply($scope.vm.requestReset);
            // then
            expect(MockAuth.resetPasswordInit).toHaveBeenCalledWith('user@domain.com');
            expect($scope.vm.success).toBeNull();
            expect($scope.vm.error).toBeNull();
            expect($scope.vm.errorEmailNotExists).toEqual('ERROR');
        });

        it('notifies of error upon error response', function() {
            // given
            MockAuth.resetPasswordInit.and.returnValue($q.reject({
                status: 503,
                data: 'something else'
            }));
            createController();
            $scope.vm.resetAccount.email = 'user@domain.com';
            // when
            $scope.$apply($scope.vm.requestReset);
            // then
            expect(MockAuth.resetPasswordInit).toHaveBeenCalledWith('user@domain.com');
            expect($scope.vm.success).toBeNull();
            expect($scope.vm.errorEmailNotExists).toBeNull();
            expect($scope.vm.error).toEqual('ERROR');
        });

    });
});
