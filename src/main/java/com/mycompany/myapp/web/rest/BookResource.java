package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Book;
import com.mycompany.myapp.domain.Comment;
import com.mycompany.myapp.repository.AuthorRepository;
import com.mycompany.myapp.repository.BookRepository;
import com.mycompany.myapp.repository.CommentRepository;
import com.mycompany.myapp.repository.GenreRepository;
import com.mycompany.myapp.repository.ReadlistRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * REST controller for managing Book.
 */
@RestController
@RequestMapping("/app")
public class BookResource {

    private final Logger log = LoggerFactory.getLogger(BookResource.class);

    @Inject
    private BookRepository bookRepository;
    @Inject
    private CommentRepository commentRepository; 
    @Inject
    private ReadlistRepository readlistRepository;  

    /**
     * POST  /rest/books -> Create a new book.
     */
    @RequestMapping(value = "/rest/books",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Book book) {
        log.debug("REST request to save Book : {}", book);
        bookRepository.save(book);
    }

    /**
     * GET  /rest/books -> get all the books.
     */
    @RequestMapping(value = "/rest/books",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Book> getAll() {
        log.debug("REST request to get all Books");
        List<Book> books =  bookRepository.findEagerRelationships();
        
        for(Book b : books){
        	b.setComments(commentRepository.getCommentsForBook(b.getId()));
        	b.setReadlists(readlistRepository.getReadlistsForBook(b.getId()));
        }
        return books;        
    }

    /**
     * GET  /rest/books/:id -> get the "id" book.
     */
    @RequestMapping(value = "/rest/books/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Book> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Book : {}", id);
        Book book = bookRepository.findOneWithEagerRelationships(id);        
        if (book == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        book.setComments(commentRepository.getCommentsForBook(book.getId()));
        book.setReadlists(readlistRepository.getReadlistsForBook(book.getId()));
        return new ResponseEntity<>(book, HttpStatus.OK);
    }    
    
    /**
     * GET  /rest/rateForBook/:id -> get the "id" book.
     */
    @RequestMapping(value = "/rest/rateForBook/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public int getRateForBook(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Book : {}", id);
        Book book = bookRepository.findOne(id); 
        if (book == null) {
            return 0;
        }
        else return book.getRate();
    }    
    
    /**
     * DELETE  /rest/books/:id -> delete the "id" book.
     */
    @RequestMapping(value = "/rest/books/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Book : {}", id);
        bookRepository.delete(id);
    }
}
