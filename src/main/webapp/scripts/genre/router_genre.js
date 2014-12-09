'use strict';

booksApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/genre', {
                    templateUrl: 'views/genres.html',
                    controller: 'GenreController',
                    resolve:{
                        resolvedGenre: ['Genre', function (Genre) {
                            return Genre.query().$promise;
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
