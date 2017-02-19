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

app.controller('StudentCRUDCtrl', ['$scope','StudentCRUDService', function ($scope,StudentCRUDService) {
  
    $scope.updateStudent = function () {
        StudentCRUDService.updateStudent($scope.student.id,$scope.student.name,$scope.student.age,$scope.student.gender)
          .then(function success(response){
              $scope.message = 'Student data updated!';
              $scope.errorMessage = '';
          },
          function error(response){
              $scope.errorMessage = 'Error updating student!';
              $scope.message = '';
          });
    }
    
    $scope.getStudent = function () {
        var id = $scope.student.id;
        StudentCRUDService.getStudent($scope.student.id)
          .then(function success(response){
              $scope.student = response.data;
              $scope.student.id = id;
              $scope.message='';
              $scope.errorMessage = '';
          },
          function error (response ){
              $scope.message = '';
              if (response.status === 404){
                  $scope.errorMessage = 'Student not found!';
              }
              else {
                  $scope.errorMessage = "Error getting student!";
              }
          });
    }
    
    $scope.addStudent = function () {
        StudentCRUDService.addStudent($scope.student.id, $scope.student.name, $scope.student.age, $scope.student.gender)
          .then (function success(response){
              $scope.message = 'Student added!';
              $scope.errorMessage = '';
          },
        function error(response){
            $scope.errorMessage = 'Error adding student!';
            $scope.message = '';
        })
    }
    
    $scope.deleteStudent = function () {
        StudentCRUDService.deleteStudent($scope.student.id)
          .then (function success(response){
              $scope.message = 'Student deleted!';
              $scope.student = null;
              $scope.errorMessage='';
          },
          function error(response){
              $scope.errorMessage = 'Error deleting student!';
              $scope.message='';
          })
    }
    
    $scope.getAllStudents = function () {
        StudentCRUDService.getAllStudents()
          .then(function success(response){
              $scope.students = response.data;
              $scope.message='';
              $scope.errorMessage = '';
          },
          function error (response ){
              $scope.message='';
              $scope.errorMessage = 'Error getting students!';
          });
    }
    
    $scope.getStudentsByName = function () {
        StudentCRUDService.getStudentsByName($scope.name)
          .then(function success(response){
              $scope.students = response.data;
              $scope.message='';
              $scope.errorMessage = '';
          },
          function error (response ){
              $scope.message='';
              $scope.errorMessage = 'Error getting students!';
          });
    }
	  
}]);

app.service('StudentCRUDService',['$http', function ($http) {
	
    this.getStudent = function getStudent(studentId){
        return $http({
          method: 'GET',
          url:'student/'+studentId
        });
	}
	
    this.addStudent = function addStudent(id, name, age, gender){
        return $http({
          method: 'POST',
          url:'student',
          data: {id:id, name:name, age:age, gender:gender}
        });
    }
	
    this.deleteStudent = function deleteStudent(id){
        return $http({
          method: 'DELETE',
          url: 'student/'+id
        })
    }
	
    this.updateStudent = function updateStudent(id,name,age,gender){
        return $http({
          method: 'PATCH',
          url: 'student/'+id,
          data: {name:name, age:age, gender:gender}
        })
    }
	
    this.getAllStudents = function getAllStudents(){
        return $http({
          method: 'GET',
          url:'student'
        });
    }
	
    this.getStudentsByName = function getStudentsByName(name){
        return $http({
          method: 'GET',
          url:'student/search/findByName',
          params:{name:name}
        });
    }
	
}]);