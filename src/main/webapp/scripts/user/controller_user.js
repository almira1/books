'use strict';

booksApp.controller('UserController', function ($scope, resolvedUser, User) {

        $scope.users = resolvedUser;
    
        
        $scope.show = function (login) {
        	
            $scope.user = User.get({login: login});
            $('#showUserModal').modal('show');
        };

    });
