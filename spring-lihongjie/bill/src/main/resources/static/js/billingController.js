app.controller('BillingCtrl', function ($scope, $log, $location) {

    var loadBillings = function (page) {
        restClient.billings.read({page: page}).done(function (data) {
            $scope.billings = data.content;
            $scope.page = data.page;
            $scope.CurrentDate = new Date();
            $scope.$apply();
        });
    };

    loadBillings(0);

    $scope.previousPage = function () {
        loadBillings($scope.page.number - 1);
    };

    $scope.nextPage = function () {
        loadBillings($scope.page.number + 1);
    };

    $scope.addBilling = function () {
        $scope.add = {};
        $("#add-billing").modal("show");
    };

    $scope.confirmAdd = function () {
        var request = restClient.billings.create($scope.add);
        request.complete(function (data) {
            if (data.status == 200) {
                toastr.success('Add billing succeed');
                loadBillings($scope.page.number);
                $("#add-billing").modal("hide");
            } else {
                toastr.error('Add billing failed');
            }
        });
    }

    $scope.editBilling = function (billing) {

        $scope.edit = angular.copy(billing);
        $("#edit-billing").modal("show");

    };

    $scope.confirmEdit = function () {

        var request = restClient.billings.patch($scope.edit.id, $scope.edit);
        request.complete(function (data) {
            if (data.status == 200) {
                toastr.success('Update billing succeed');
                loadBillings($scope.page.number);
                $("#edit-billing").modal("hide");
            } else {
                toastr.error('Update billing failed');
            }
        });
    }

    $scope.deleteBilling = function (billing) {
        bootbox.confirm("Are you sure want to delete billing: " + billing.name, function (result) {
            if (result == true) {
                var request = restClient.billings.delete(billing.id);
                request.complete(function (data) {
                    if (data.status == 200) {
                        toastr.success('Delete succeed');
                        loadBillings($scope.page.number);
                    } else {
                        toastr.error('Delete failed');
                    }
                });
            }
        })
    };
});

