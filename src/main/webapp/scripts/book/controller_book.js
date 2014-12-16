'use strict';

booksApp.controller('BookController', function ($scope, resolvedBook, Book, BookData, resolvedAuthor, resolvedComment, resolvedGenre,resolvedUser) {

        $scope.books = resolvedBook;
        $scope.authors = resolvedAuthor;
        $scope.comments = resolvedComment;
        $scope.genres = resolvedGenre;
        $scope.users = resolvedUser;   
        
        
        
        
       $scope.searchText = "";
       $scope.search = function(){    	  
    	  var text = $scope.searchText;    	  
    	  var books = [];
    	  for(var i = resolvedBook.length - 1; i >= 0; i--) {
    		    if((resolvedBook[i]).title.search(text) != -1) {
    		    	books.push(resolvedBook[i]);
    		    }
    		}
    	  $scope.books = books;
      };
          
        $scope.create = function () {
            Book.save($scope.book,
                function () {
                    $scope.books = Book.query();
                    $('#saveBookModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.book = Book.get({id: id});
            $('#saveBookModal').modal('show');
        };

        $scope.delete = function (id) {
            Book.delete({id: id},
                function () {
                    $scope.books = Book.query();
                });
        };

        $scope.clear = function () {
            $scope.book = {title: null, summary: null, filePath: null, rate: null, picture: null, id: null};
        };
    });
