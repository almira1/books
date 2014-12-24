'use strict';

booksApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/book', {
                    templateUrl: 'views/books.html',
                    controller: 'BookController',
                    resolve:{
                        resolvedBook: ['Book', function (Book) {
                            return Book.query().$promise;
                        }],
                        resolvedAuthor: ['Author', function (Author) {
                            return Author.query().$promise;
                        }],
                        resolvedComment: ['Comment', function (Comment) {
                            return Comment.query().$promise;
                        }],
                        resolvedGenre: ['Genre', function (Genre) {
                            return Genre.query().$promise;
                        }],
                        resolvedUser: ['User', function (User) {
                            return User.query().$promise;
                        }] 
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
                .when('/bookdetails/:id', {
                    templateUrl: 'views/bookDetails.html',
                    controller: 'BookDetails',
                    resolve:{
                        resolvedBook: ['Book', function (Book) {
                            return Book.query().$promise;
                        }],
                        resolvedComment: ['Comment', function (Comment) {
                            return Comment.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
