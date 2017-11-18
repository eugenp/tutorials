'use strict';

describe('Directive Tests', function () {
    beforeEach(mockApiAccountCall);

    var elm, scope, $httpBackend;

    beforeEach(inject(function($compile, $rootScope, $injector) {
        $httpBackend = $injector.get('$httpBackend');

        var html = '<password-strength-bar password-to-check="password"></password-strength-bar>';
        scope = $rootScope.$new();
        elm = angular.element(html);
        $compile(elm)(scope);
    }));

    describe('Password strength', function () {
        it("Should display the password strength bar", function() {
            expect(elm.find('ul').length).toEqual(1);
            expect(elm.find('li').length).toEqual(5);
        });

        it("Should change the first 2 points of the strength bar", function() {
            scope.$apply(function() {
                scope.password = "morethan5chars"; // that should trigger the 2 first points
            });

            var firstpointStyle = elm.find('ul').children('li')[0].getAttribute('style');
            expect(firstpointStyle).toContain('background-color: rgb(255, 153, 0)');

            var secondpointStyle = elm.find('ul').children('li')[1].getAttribute('style');
            expect(secondpointStyle).toContain('background-color: rgb(255, 153, 0)');

            var thirdpointStyle = elm.find('ul').children('li')[2].getAttribute('style');
            expect(thirdpointStyle).toContain('background-color: rgb(221, 221, 221)');
        });

        it("Should change the first 4 points of the strength bar", function() {
            scope.$apply(function() {
                scope.password = "mo5ch$=!"; // that should trigger the 3 first points
            });

            var firstpointStyle = elm.find('ul').children('li')[0].getAttribute('style');
            dump(firstpointStyle);
            expect(firstpointStyle).toContain('background-color: rgb(153, 255, 0)');

            var secondpointStyle = elm.find('ul').children('li')[1].getAttribute('style');
            expect(secondpointStyle).toContain('background-color: rgb(153, 255, 0)');

            var thirdpointStyle = elm.find('ul').children('li')[2].getAttribute('style');
            expect(thirdpointStyle).toContain('background-color: rgb(153, 255, 0)');

            var fourthpointStyle = elm.find('ul').children('li')[3].getAttribute('style');
            expect(fourthpointStyle).toContain('background-color: rgb(153, 255, 0)');

            var fifthpointStyle = elm.find('ul').children('li')[4].getAttribute('style');
            expect(fifthpointStyle).toContain('background-color: rgb(221, 221, 221)');
        });
    });
});
