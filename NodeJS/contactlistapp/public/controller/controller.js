var app = angular.module("MyApp",[]);

app.controller("AppCtrl", function($scope,$http){
	<!--whenever we are adding data  we need to refersh the browser

	-->
	console.log("Hello world from controller");
	var refersh =function(){
		$http.get('/contactlist').success(function(response){
			console.log("i got the data from server");
			$scope.contactlist = response;
			$scope.contact = "";
		});
	};
	refersh();

	$scope.addContacts = function(){
		console.log($scope.contact);
		$http.post('/contactlist',$scope.contact).success(function(response){
			console.log(response);
			refersh();
		});
	};
	$scope.remove = function(id){
		console.log(id);
		//send the delete request to server
		//once we delete we need refersh in success function we called refreh(); 
		$http.delete('/contactlist/'+id).success(function(){
			refersh();
		});
	};
	$scope.edit=function(id){
		console.log(id);
		$http.get('/contactlist/'+id).success(function(response){
			$scope.contact = response;
		});
	};
	$scope.update=function(){ 
		console.log($scope.contact._id);
		$http.put('/contactlist/'+$scope.contact._id,$scope.contact).success(function(response){
			refersh();
		});
	
	};
	$scope.deselect=function(){
		$scope.contact="";
	};
});

 