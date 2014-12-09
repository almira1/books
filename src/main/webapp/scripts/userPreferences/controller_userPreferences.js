'use strict';

booksApp.controller('UserPreferencesController', function ($scope, resolvedUserPreferences, UserPreferences, resolvedGenre) {

        $scope.userPreferencess = resolvedUserPreferences;
        $scope.genres = resolvedGenre;

        $scope.create = function () {
            UserPreferences.save($scope.userPreferences,
                function () {
                    $scope.userPreferencess = UserPreferences.query();
                    $('#saveUserPreferencesModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.userPreferences = UserPreferences.get({id: id});
            $('#saveUserPreferencesModal').modal('show');
        };

        $scope.delete = function (id) {
            UserPreferences.delete({id: id},
                function () {
                    $scope.userPreferencess = UserPreferences.query();
                });
        };

        $scope.clear = function () {
            $scope.userPreferences = {readBooks: null, id: null};
        };
    });
