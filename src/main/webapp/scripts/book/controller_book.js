'use strict';

booksApp.controller('BookController', function ($scope, resolvedBook, Book, BookData, resolvedAuthor, resolvedComment, resolvedGenre,resolvedUser,fileReader) {

        $scope.books = resolvedBook;
        $scope.authors = resolvedAuthor;
        $scope.comments = resolvedComment;
        $scope.genres = resolvedGenre;
        $scope.users = resolvedUser;           
                 
        $scope.readFile = function (file) {    
            
            fileReader.readAsDataUrl(file, $scope)
                     .then(function (result) {                         
                         $scope.book.picture = result;
                     });
        };
        
        
       $scope.searchText = "";
       $scope.search = function(){    	  
    	  var text = $scope.searchText;    	  
    	  var books = [];
    	  switch($scope.searchCriteria)
    	  {
    	  case 'Title' : 
    		  for(var i = resolvedBook.length - 1; i >= 0; i--) {
      		    if((resolvedBook[i]).title.search(text) != -1) {
      		    	books.push(resolvedBook[i]);
      		    }
      		}
    		break;
    	  case 'Author' :
    		  for(var i = resolvedBook.length - 1; i >= 0; i--) {
    			  for(var j = (resolvedBook[i]).authors.length -1; j>= 0; j--)    				  
        		    if((resolvedBook[i]).authors[j].name.search(text) != -1) {
        		    	books.push(resolvedBook[i]);
        		    	break;
        		    }
        		}
      		break;    	
    	  case 'Genre' :    		 
    		  for(var i = resolvedBook.length - 1; i >= 0; i--) {
    			  for(var j = (resolvedBook[i]).genres.length -1; j>= 0; j--)    				  
        		    if((resolvedBook[i]).genres[j].name.search(text) != -1) {
        		    	books.push(resolvedBook[i]);
        		    	break;
        		    }
        		}
      		break;    	  
    	  }
    	  
    	  $scope.books = books;
      };
          
        $scope.create = function () {        	
        		Book.save($scope.book,
                function () {
                    $scope.books = Book.query();
                    $('#saveBookModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
        	
            $scope.book = Book.get({id: id});    
            $scope.imageSrc = $scope.book.picture;
            $('#saveBookModal').modal('show');
        };
        
        $scope.show = function (id) {
                   $scope.book = Book.get({id: id});
                   $('#showBookModal').modal('show');
               };

        $scope.delete = function (id) {
            Book.delete({id: id},
                function () {
                    $scope.books = Book.query();
                });
        };

        $scope.clear = function () {
            $scope.book = {title: null, summary: null, filePath: null, rate: null, picture: null, id: null};
        };
        
        $scope.searchCriteria = 'Title';       
        
    });



booksApp.controller('BookDetails', function ($scope,$routeParams,Book,Comment,Account,User,BookData) {
  function getBook (){
	  BookData.getBook($routeParams.id, function(results){
		  $scope.book = results;
		  setRate();
	  }); 
  };
  getBook();
  
  $scope.settingsAccount = Account.get();
  
  
  $scope.fullStars = [];
  $scope.emptyStars = [];



  function setRate()  {
	  var average = 0;	  
	  if($scope.book.rate != 0)
		  average = Math.round($scope.book.rate / $scope.book.ratePeople);
	  
	  $scope.fullStars = [];
	  $scope.emptyStars = [];
    
  	for(var i = 1; i <= average; i++)
  		$scope.fullStars.push(i);

  	for(var i = 5; i > average; i--)
  		$scope.emptyStars.push(i);	
  };


  $scope.UserRating = 1;

  $scope.rateFunction = function() {
      $scope.book.rate = $scope.book.rate + $scope.UserRating;
      $scope.book.ratePeople = $scope.book.ratePeople + 1; 
      Book.save($scope.book, function(){});
      setRate();
    };

  $scope.UserComment = '';
  $scope.commentFunction = function(){
	  var newComment = 
	  {
		text:$scope.UserComment,
		book : {id : $scope.book.id },
		user : {login: $scope.settingsAccount.login },
		likes : 0,
		  
	  };
	  Comment.save(newComment,
              function () {         
		  BookData.getComments($scope.book.id, function (results) {
			  $scope.UserComment = '';
			  $scope.book.comments = results;
			  $scope.$apply();
		  });
              });
	  
  };

  $scope.commentLikeFunction = function(id){
	  var comment = null;
	  for(var i = 0; i< $scope.book.comments.length; i ++)
		  if($scope.book.comments[i].id == id)
		  {
			  $scope.book.comments[i].likes = $scope.book.comments[i].likes + 1;
			  comment = $scope.book.comments[i];
			  break;
		  }
	  Comment.save(comment, function () {});

  };
   
});


