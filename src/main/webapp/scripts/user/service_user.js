'use strict';

booksApp.factory('User', function ($resource) {
        return $resource('app/rest/users/:login', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
