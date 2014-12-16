'use strict';

booksApp.controller('GenreController', function ($scope, resolvedGenre, Genre, resolvedBook,resolvedUser) {

        $scope.genres = resolvedGenre;
        $scope.books = resolvedBook;

        $scope.searchText = "";
        $scope.search = function(){    	  
     	  var text = $scope.searchText;    	  
     	  var genres = [];
     	  for(var i = resolvedGenre.length - 1; i >= 0; i--) {
     		    if((resolvedGenre[i]).name.search(text) != -1) {
     		    	genres.push(resolvedGenre[i]);
     		    }
     		}
     	  $scope.genres = genres;
       };
       
        $scope.create = function () {
            Genre.save($scope.genre,
                function () {
                    $scope.genres = Genre.query();
                    $('#saveGenreModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.genre = Genre.get({id: id});
            $('#saveGenreModal').modal('show');
        };

        $scope.delete = function (id) {
            Genre.delete({id: id},
                function () {
                    $scope.genres = Genre.query();
                });
        };

        $scope.clear = function () {
            $scope.genre = {name: null, description: null, id: null};
        };
    });
