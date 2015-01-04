'use strict';

booksApp.factory('Readlist', function ($resource) {
        return $resource('app/rest/readlists/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
