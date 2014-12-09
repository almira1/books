'use strict';

booksApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/comment', {
                    templateUrl: 'views/comments.html',
                    controller: 'CommentController',
                    resolve:{
                        resolvedComment: ['Comment', function (Comment) {
                            return Comment.query().$promise;
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
