'use strict';

booksApp.controller('AuthorController', function ($scope, resolvedAuthor, Author, resolvedBook,fileReader) {

        $scope.authors = resolvedAuthor;
        $scope.books = resolvedBook;
		
        $scope.readFile = function (file) { 
		    fileReader.readAsDataUrl(file, $scope)
		             .then(function (result) {                         
		                 $scope.author.picture = result;		                
		             });
        };


        $scope.searchText = "";
        $scope.search = function(){    	  
     	  var text = $scope.searchText;    	  
     	  var authors = [];
     	  for(var i = resolvedAuthor.length - 1; i >= 0; i--) {
     		    if((resolvedAuthor[i]).name.search(text) != -1) {
     		    	authors.push(resolvedAuthor[i]);
     		    }
     		}
     	  $scope.authors = authors;
       };
       
        $scope.create = function () {
            Author.save($scope.author,
                function () {
                    $scope.authors = Author.query();
                    $('#saveAuthorModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.author = Author.get({id: id});
            $('#saveAuthorModal').modal('show');
        };

        $scope.delete = function (id) {
            Author.delete({id: id},
                function () {
                    $scope.authors = Author.query();
                });
        };
        
        $scope.show = function (id) {
            $scope.author = Author.get({id: id});
            $('#showAuthorModal').modal('show');
        };        
        

        $scope.clear = function () {
            $scope.author = {name: null, summary: null, picture: null, id: null};
        };
    });

booksApp.controller('AuthorDetails',function ($scope,Author,Book,$routeParams,$location,fileReader)
{	
	$scope.readFile = function (file) { 
	    fileReader.readAsDataUrl(file, $scope)
	             .then(function (result) {                         
	            	 $scope.author.picture = result;	                 
	             });
    };
    
	$scope.author = Author.get({id: $routeParams.id});	
	
	$scope.create = function () {		
        Author.save($scope.author,
            function () {              
                $('#saveAuthorModal').modal('hide');                
            });
    };

    $scope.update = function (id) {
        $scope.author = Author.get({id: id});        
        $('#saveAuthorModal').modal('show');
    };

    $scope.delete = function (id) {
        Author.delete({id: id},
            function () {
        	$location.path('/author');
            });
    };
	
});
