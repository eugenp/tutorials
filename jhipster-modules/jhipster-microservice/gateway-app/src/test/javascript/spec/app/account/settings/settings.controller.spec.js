'use strict';

describe('Controller Tests', function() {

    beforeEach(mockApiAccountCall);
    beforeEach(mockI18nCalls);

    describe('SettingsController', function() {

        var $scope, $q; // actual implementations
        var MockPrincipal, MockAuth; // mocks
        var createController; // local utility functions

        beforeEach(inject(function($injector) {
            $q = $injector.get('$q');
            $scope = $injector.get("$rootScope").$new();
            MockAuth = jasmine.createSpyObj('MockAuth', ['updateAccount']);
            MockPrincipal = jasmine.createSpyObj('MockPrincipal', ['identity']);
            var locals = {
                '$scope': $scope,
                'Principal': MockPrincipal,
                'Auth': MockAuth
            };
            createController = function() {
                $injector.get('$controller')('SettingsController as vm', locals);
            }
        }));

        it('should send the current identity upon save', function() {
            //GIVEN
            var accountValues = {
                firstName: "John",
                lastName: "Doe",

                activated: true,
                email: "john.doe@mail.com",
                langKey: "en",
                login: "john"
            };
            MockPrincipal.identity.and.returnValue($q.resolve(accountValues));
            MockAuth.updateAccount.and.returnValue($q.resolve());
            $scope.$apply(createController);

            //WHEN
            $scope.vm.save();

            //THEN
            expect(MockPrincipal.identity).toHaveBeenCalled();
            expect(MockAuth.updateAccount).toHaveBeenCalledWith(accountValues);
            expect($scope.vm.settingsAccount).toEqual(accountValues);
        });

        it('should notify of success upon successful save', function() {
            //GIVEN
            var accountValues = {
                firstName: "John",
                lastName: "Doe"
            };
            MockPrincipal.identity.and.returnValue($q.resolve(accountValues));
            MockAuth.updateAccount.and.returnValue($q.resolve());
            createController();

            //WHEN
            $scope.$apply($scope.vm.save);

            //THEN
            expect($scope.vm.error).toBeNull();
            expect($scope.vm.success).toBe('OK');
        });

        it('should notify of error upon failed save', function() {
            //GIVEN
            MockPrincipal.identity.and.returnValue($q.resolve({}));
            MockAuth.updateAccount.and.returnValue($q.reject());
            createController();

            //WHEN
            $scope.$apply($scope.vm.save);

            //THEN
            expect($scope.vm.error).toEqual('ERROR');
            expect($scope.vm.success).toBeNull();
        });
    });
});
