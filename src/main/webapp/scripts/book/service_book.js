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

            $http.get("app/rest/commentsforbook/" + id).success(callback);
        },
        getReadlists: function (id, callback) {

            $http.get("app/rest/readlistsforbook/" + id).success(callback);
        },
        getBook : function (id, callback){
        	$http.get("app/rest/books/" + id).success(callback);
        }
    }
})

booksApp.directive('fileInput', function ($parse) {
     return {
         restrict: "EA",
         template: "<input type='file' />",
         replace: true,
         link: function (scope, element, attrs) {

             var modelGet = $parse(attrs.fileInput);
             var modelSet = modelGet.assign;
             var onChange = $parse(attrs.onChange);

             var updateModel = function () {
                 scope.$apply(function () {
                     modelSet(scope, element[0].files[0]);
                     onChange(scope);
                 });
             };

             element.bind('change', updateModel);
         }
     }
});

booksApp.service('fileReader', function ($q, $log) {

    var onLoad = function (reader, deferred, scope) {
        return function () {
            scope.$apply(function () {
                deferred.resolve(reader.result);
            });
        };
    };

    var onError = function (reader, deferred, scope) {
        return function () {
            scope.$apply(function () {
                deferred.reject(reader.result);
            });
        };
    };

    var onProgress = function (reader, scope) {
        return function (event) {
            scope.$broadcast("fileProgress",
                {
                    total: event.total,
                    loaded: event.loaded
                });
        };
    };

    var getReader = function (deferred, scope) {
        var reader = new FileReader();
        reader.onload = onLoad(reader, deferred, scope);
        reader.onerror = onError(reader, deferred, scope);
        reader.onprogress = onProgress(reader, scope);
        return reader;
    };

    var readAsDataURL = function (file, scope) {
        var deferred = $q.defer();

        var reader = getReader(deferred, scope);
        reader.readAsDataURL(file);

        return deferred.promise;
    };

    var readAsBinaryString = function (file, scope) {
        var deferred = $q.defer();

        var reader = getReader(deferred, scope);
        reader.readAsBinaryString(file);

        return deferred.promise;
    };

    return {
        readAsDataUrl: readAsDataURL,
        readAsBinaryString: readAsBinaryString
    };
});