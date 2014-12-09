'use strict';

booksApp.controller('CommentController', function ($scope, resolvedComment, Comment, resolvedBook) {

        $scope.comments = resolvedComment;        
        $scope.books = resolvedBook;

        $scope.create = function () {
            Comment.save($scope.comment,
                function () {
                    $scope.comments = Comment.query();
                    $('#saveCommentModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.comment = Comment.get({id: id});
            $('#saveCommentModal').modal('show');
        };

        $scope.delete = function (id) {
            Comment.delete({id: id},
                function () {
                    $scope.comments = Comment.query();
                });
        };

        $scope.clear = function () {
            $scope.comment = {text: null, id: null};
        };
    });
