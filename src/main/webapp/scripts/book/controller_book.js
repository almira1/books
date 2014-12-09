'use strict';

booksApp.controller('BookController', function ($scope, resolvedBook, Book, resolvedAuthor, resolvedComment, resolvedGenre) {

        $scope.books = resolvedBook;
        $scope.authors = resolvedAuthor;
        $scope.comments = resolvedComment;
        $scope.genres = resolvedGenre;

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
