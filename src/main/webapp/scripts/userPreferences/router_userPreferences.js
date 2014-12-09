'use strict';

booksApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/userPreferences', {
                    templateUrl: 'views/userPreferencess.html',
                    controller: 'UserPreferencesController',
                    resolve:{
                        resolvedUserPreferences: ['UserPreferences', function (UserPreferences) {
                            return UserPreferences.query().$promise;
                        }],                       
                        resolvedGenre: ['Genre', function (Genre) {
                            return Genre.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
