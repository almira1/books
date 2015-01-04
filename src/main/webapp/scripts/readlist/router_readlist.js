'use strict';

booksApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/readlist', {
                    templateUrl: 'views/readlist.html',
                    controller: 'ReadlistController',
                    resolve:{
                        resolvedReadlist: ['Readlist', function (Readlist) {
                            return Readlist.query().$promise;
                        }],                       
                        resolvedBook: ['Book', function (Book) {
                            return Book.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
