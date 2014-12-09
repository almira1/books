'use strict';

booksApp.factory('Genre', function ($resource) {
        return $resource('app/rest/genres/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
