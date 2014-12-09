'use strict';

booksApp.factory('UserPreferences', function ($resource) {
        return $resource('app/rest/userPreferencess/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
