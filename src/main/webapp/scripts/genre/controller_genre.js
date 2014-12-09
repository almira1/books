'use strict';

booksApp.controller('GenreController', function ($scope, resolvedGenre, Genre, resolvedBook) {

        $scope.genres = resolvedGenre;
        $scope.books = resolvedBook;

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
