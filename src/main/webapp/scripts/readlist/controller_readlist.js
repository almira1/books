'use strict';

booksApp.controller('ReadlistController', function ($scope, resolvedReadlist, Readlist, resolvedBook) {

	$scope.readlist = resolvedReadlist;
    $scope.books = resolvedBook;
    
    $scope.create = function () {
        Readlist.save($scope.readlist,
            function () {
                $scope.readlist = Readlist.query();
                $('#saveReadlistModal').modal('hide');
                $scope.clear();
            });
    };
       
    
    });
