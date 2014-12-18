'use strict';

booksApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/user', {
                    templateUrl: 'views/users.html',
                    controller: 'UserController',
                    resolve:{
                        resolvedUser: ['User', function (User) {
                            return User.query().$promise;
                        }]
                        
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
                 
        });
