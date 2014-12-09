package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.UserPreferences;
import com.mycompany.myapp.repository.UserPreferencesRepository;
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
 * REST controller for managing UserPreferences.
 */
@RestController
@RequestMapping("/app")
public class UserPreferencesResource {

    private final Logger log = LoggerFactory.getLogger(UserPreferencesResource.class);

    @Inject
    private UserPreferencesRepository userPreferencesRepository;

    /**
     * POST  /rest/userPreferencess -> Create a new userPreferences.
     */
    @RequestMapping(value = "/rest/userPreferencess",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody UserPreferences userPreferences) {
        log.debug("REST request to save UserPreferences : {}", userPreferences);
        userPreferencesRepository.save(userPreferences);
    }

    /**
     * GET  /rest/userPreferencess -> get all the userPreferencess.
     */
    @RequestMapping(value = "/rest/userPreferencess",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<UserPreferences> getAll() {
        log.debug("REST request to get all UserPreferencess");
        return userPreferencesRepository.findAll();
    }

    /**
     * GET  /rest/userPreferencess/:id -> get the "id" userPreferences.
     */
    @RequestMapping(value = "/rest/userPreferencess/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserPreferences> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get UserPreferences : {}", id);
        UserPreferences userPreferences = userPreferencesRepository.findOne(id);
        if (userPreferences == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userPreferences, HttpStatus.OK);
    }

    /**
     * DELETE  /rest/userPreferencess/:id -> delete the "id" userPreferences.
     */
    @RequestMapping(value = "/rest/userPreferencess/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete UserPreferences : {}", id);
        userPreferencesRepository.delete(id);
    }
}
