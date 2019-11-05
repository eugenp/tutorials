var app = angular.module('app', ['ngMessages']);

app.controller('UserCtrl', ['$scope','UserService', function ($scope,UserService) {
	
	$scope.submitted = false;
	
	$scope.getUsers = function() {
		   UserService.getUsers().then( function(data){
		       $scope.users = data;
	       });
	   }
    
    $scope.saveUser = function () {
    	$scope.submitted = true;
    	  if ($scope.userForm.$valid){
            UserService.saveUser($scope.user)
              .then (function success(response){
                  $scope.message = 'User added!';
                  $scope.errorMessage = '';
                  $scope.getUsers();
                  $scope.user = null;
                  $scope.submitted = false;
              },
              function error(response){
            	  if (response.status == 409){
                  $scope.errorMessage = response.data[0];
            	  }
            	  else {
            		  $scope.errorMessage = 'Error adding user!';
            	  }
                  $scope.message = '';
            });
    	  }
    }
   
   $scope.getUsers();
   
   $scope.resetForm = function () {
	   $scope.userForm.$setPristine();
	   $scope.user=null;
	   $scope.message='';
	   $scope.errorMessage='';
	   $scope.submitted = false;
   }
    
}]);

app.service('UserService',['$http', function ($http) {
	
    this.saveUser = function saveUser(user){
        return $http({
          method: 'POST',
          url: 'user',
          params: {email:user.email, password:user.password, name:user.name, age:user.age},
          headers: 'Accept:application/json'
        });
    }
	
	
    this.getUsers = function getUsers(){
        return $http({
          method: 'GET',
          url: 'users',
          headers:'Accept:application/json'
        }).then( function(response){
        	return response.data;
        } );
    }

}]);