package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Genre;
import com.mycompany.myapp.repository.GenreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing Genre.
 */
@RestController
@RequestMapping("/app")
public class GenreResource {

    private final Logger log = LoggerFactory.getLogger(GenreResource.class);

    @Inject
    private GenreRepository genreRepository;

    /**
     * POST  /rest/genres -> Create a new genre.
     */
    @RequestMapping(value = "/rest/genres",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Genre genre) {
        log.debug("REST request to save Genre : {}", genre);
        genreRepository.save(genre);
    }

    /**
     * GET  /rest/genres -> get all the genres.
     */
    @RequestMapping(value = "/rest/genres",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Genre> getAll() {
        log.debug("REST request to get all Genres");
        return genreRepository.findAll();
    }

    /**
     * GET  /rest/genres/:id -> get the "id" genre.
     */
    @RequestMapping(value = "/rest/genres/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Genre> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Genre : {}", id);
        Genre genre = genreRepository.findOne(id);
        if (genre == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(genre, HttpStatus.OK);
    }

    /**
     * DELETE  /rest/genres/:id -> delete the "id" genre.
     */
    @RequestMapping(value = "/rest/genres/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Genre : {}", id);
        genreRepository.delete(id);
    }
}
