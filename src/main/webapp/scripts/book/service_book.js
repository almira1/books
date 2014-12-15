'use strict';

booksApp.factory('Book', function ($resource) {
        return $resource('app/rest/books/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });

booksApp.factory('BookData', function ($http) {
    return {
        getComments: function (id, callback) {

            $http.get("app/rest/books/comments/" + id).success(callback);
        }
    }
})