'use strict';

booksApp.controller('AuthorController', function ($scope, resolvedAuthor, Author, resolvedBook) {

        $scope.authors = resolvedAuthor;
        $scope.books = resolvedBook;


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

        $scope.clear = function () {
            $scope.author = {name: null, summary: null, picture: null, id: null};
        };
    });
