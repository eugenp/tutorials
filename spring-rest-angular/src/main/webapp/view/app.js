var app = angular.module('app', ['ui.grid','ui.grid.pagination']);

app.controller('StudentCtrl', ['$scope','StudentService', function ($scope,StudentService) {
   var paginationOptions = {
     pageNumber: 1,
	 pageSize: 5,
	 sort: null
   };	
	
   StudentService.getStudents(paginationOptions.pageNumber,
		   paginationOptions.pageSize).success(function(data){
	  $scope.gridOptions.data = data.content;
 	  $scope.gridOptions.totalItems = data.totalElements;
   });
   
   $scope.gridOptions = {
    paginationPageSizes: [5, 10, 20],
    paginationPageSize: paginationOptions.pageSize,
    enableColumnMenus:false,
	useExternalPagination: true,
    columnDefs: [
      { name: 'id' },
      { name: 'name' },
      { name: 'gender' },
      { name: 'age' }
    ],
    onRegisterApi: function(gridApi) {
        $scope.gridApi = gridApi;
        gridApi.pagination.on.paginationChanged($scope, function (newPage, pageSize) {
          paginationOptions.pageNumber = newPage;
          paginationOptions.pageSize = pageSize;
          StudentService.getStudents(newPage,pageSize).success(function(data){
        	  $scope.gridOptions.data = data.content;
         	  $scope.gridOptions.totalItems = data.totalElements;
          });
        });
     }
  };
  
}]);

app.service('StudentService',['$http', function ($http) {
	
	function getStudents(pageNumber,size) {
		pageNumber = pageNumber > 0?pageNumber - 1:0;
        return  $http({
          method: 'GET',
          url: 'student/get?page='+pageNumber+'&size='+size
        });
    }
	
    return {
    	getStudents:getStudents
    };
	
}]);

app.controller('EmployeeCRUDCtrl', ['$scope','EmployeeCRUDService', function ($scope,EmployeeCRUDService) {
	  
    $scope.updateEmployee = function () {
        EmployeeCRUDService.updateEmployee($scope.employee.id,$scope.employee.name,$scope.employee.age)
          .then(function success(response){
              $scope.message = 'Employee data updated!';
              $scope.errorMessage = '';
          },
          function error(response){
              $scope.errorMessage = 'Error updating Employee!';
              $scope.message = '';
          });
    }
    
    $scope.getEmployee = function () {
        var id = $scope.employee.id;
        EmployeeCRUDService.getEmployee($scope.employee.id)
          .then(function success(response){
              $scope.employee = response.data;
              $scope.employee.id = id;
              $scope.message='';
              $scope.errorMessage = '';
          },
          function error (response ){
              $scope.message = '';
              if (response.status === 404){
                  $scope.errorMessage = 'Employee not found!';
              }
              else {
                  $scope.errorMessage = "Error getting Employee!";
              }
          });
    }
    
    $scope.addEmployee = function () {
        if ($scope.employee != null && $scope.employee.id) {
            EmployeeCRUDService.addEmployee($scope.employee.id, $scope.employee.name, $scope.employee.age)
              .then (function success(response){
                  $scope.message = 'Employee added!';
                  $scope.errorMessage = '';
              },
              function error(response){
                  $scope.errorMessage = 'Error adding Employee!';
                  $scope.message = '';
            });
        }
        else {
            $scope.errorMessage = 'Please enter an id!';
            $scope.message = '';
        }
    }
    
    $scope.deleteEmployee = function () {
        EmployeeCRUDService.deleteEmployee($scope.employee.id)
          .then (function success(response){
              $scope.message = 'Employee deleted!';
              $scope.employee = null;
              $scope.errorMessage='';
          },
          function error(response){
              $scope.errorMessage = 'Error deleting Employee!';
              $scope.message='';
          })
    }
    
    $scope.getAllEmployees = function () {
        EmployeeCRUDService.getAllEmployees()
          .then(function success(response){
              $scope.employees = response.data._embedded.employee;
              $scope.message='';
              $scope.errorMessage = '';
          },
          function error (response ){
              $scope.message='';
              $scope.errorMessage = 'Error getting Employees!';
          });
    }
    
    $scope.getEmployeesByName = function () {
        EmployeeCRUDService.getEmployeesByName($scope.name)
          .then(function success(response){
              $scope.employees = response.data._embedded.employee;
              $scope.message='';
              $scope.errorMessage = '';
          },
          function error (response ){
              $scope.message='';
              $scope.errorMessage = 'Error getting Employees!';
          });
    }
	  
}]);

app.service('EmployeeCRUDService',['$http', function ($http) {
	
    this.getEmployee = function getEmployee(employeeId){
        return $http({
          method: 'GET',
          url:'employees/'+employeeId
        });
	}
	
    this.addEmployee = function addEmployee(id, name, age, gender){
        return $http({
          method: 'POST',
          url:'employees',
          data: {id:id, name:name, age:age}
        });
    }
	
    this.deleteEmployee = function deleteEmployee(id){
        return $http({
          method: 'DELETE',
          url: 'employees/'+id
        })
    }
	
    this.updateEmployee = function updateEmployee(id,name,age){
        return $http({
          method: 'PATCH',
          url: 'employees/'+id,
          data: {name:name, age:age}
        })
    }
	
    this.getAllEmployees = function getAllEmployees(){
        return $http({
          method: 'GET',
          url:'employees'
        });
    }
	
    this.getEmployeesByName = function getEmployeesByName(name){
        return $http({
          method: 'GET',
          url:'employees/search/findByName',
          params:{name:name}
        });
    }
	
}]);