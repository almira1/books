package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Comment;
import com.mycompany.myapp.domain.Readlist;
import com.mycompany.myapp.repository.ReadlistRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * REST controller for managing Readlist.
 */
@RestController
@RequestMapping("/app")
public class ReadlistResource {
	private final Logger log = LoggerFactory.getLogger(ReadlistResource.class);

    @Inject
    private ReadlistRepository readlistRepository;

    /**
     * POST  /rest/readlists -> Create a new readlist.
     */
    @RequestMapping(value = "/rest/readlists",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Readlist readlist) {
        log.debug("REST request to save Readlist for book: {}", readlist.getBook().getId());
        readlistRepository.save(readlist);
    }

    /**
     * GET  /rest/readlists -> get all the readlists.
     */
    @RequestMapping(value = "/rest/readlists",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Readlist> getAll() {
        log.debug("REST request to get all Readlists");
        return readlistRepository.findAll();
    }

    /**
     * GET  /rest/readlists/:id -> get the "id" readlist.
     */
    @RequestMapping(value = "/rest/readlists/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Readlist> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Readlist : {}", id);
        Readlist readlist = readlistRepository.findOne(id);
        if (readlist == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(readlist, HttpStatus.OK);
    }
    
    
    /**
     * GET  /rest/readlistsforbook/:id -> get the "id" readlist.
     */
    @RequestMapping(value = "/rest/readlistsforbook/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Readlist> readlistsforbook(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get readlistsforbook : {}", id);
        return new ArrayList<Readlist> (readlistRepository.getReadlistsForBook(id));      
        
    }
    

    /**
     * DELETE  /rest/readlists/:id -> delete the "id" readlist.
     */
    @RequestMapping(value = "/rest/readlists/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Readlist : {}", id);
        readlistRepository.delete(id);
    }

}
