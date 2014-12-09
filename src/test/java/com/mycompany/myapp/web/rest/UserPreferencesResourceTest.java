package com.mycompany.myapp.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.UserPreferences;
import com.mycompany.myapp.repository.UserPreferencesRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserPreferencesResource REST controller.
 *
 * @see UserPreferencesResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class UserPreferencesResourceTest {

    private static final Integer DEFAULT_READ_BOOKS = 0;
    private static final Integer UPDATED_READ_BOOKS = 1;
    

    @Inject
    private UserPreferencesRepository userPreferencesRepository;

    private MockMvc restUserPreferencesMockMvc;

    private UserPreferences userPreferences;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserPreferencesResource userPreferencesResource = new UserPreferencesResource();
        ReflectionTestUtils.setField(userPreferencesResource, "userPreferencesRepository", userPreferencesRepository);
        this.restUserPreferencesMockMvc = MockMvcBuilders.standaloneSetup(userPreferencesResource).build();
    }

    @Before
    public void initTest() {
        userPreferences = new UserPreferences();
        userPreferences.setReadBooks(DEFAULT_READ_BOOKS);
    }

    @Test
    @Transactional
    public void createUserPreferences() throws Exception {
        // Validate the database is empty
        assertThat(userPreferencesRepository.findAll()).hasSize(0);

        // Create the UserPreferences
        restUserPreferencesMockMvc.perform(post("/app/rest/userPreferencess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userPreferences)))
                .andExpect(status().isOk());

        // Validate the UserPreferences in the database
        List<UserPreferences> userPreferencess = userPreferencesRepository.findAll();
        assertThat(userPreferencess).hasSize(1);
        UserPreferences testUserPreferences = userPreferencess.iterator().next();
        assertThat(testUserPreferences.getReadBooks()).isEqualTo(DEFAULT_READ_BOOKS);
    }

    @Test
    @Transactional
    public void getAllUserPreferencess() throws Exception {
        // Initialize the database
        userPreferencesRepository.saveAndFlush(userPreferences);

        // Get all the userPreferencess
        restUserPreferencesMockMvc.perform(get("/app/rest/userPreferencess"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(userPreferences.getId().intValue()))
                .andExpect(jsonPath("$.[0].readBooks").value(DEFAULT_READ_BOOKS));
    }

    @Test
    @Transactional
    public void getUserPreferences() throws Exception {
        // Initialize the database
        userPreferencesRepository.saveAndFlush(userPreferences);

        // Get the userPreferences
        restUserPreferencesMockMvc.perform(get("/app/rest/userPreferencess/{id}", userPreferences.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(userPreferences.getId().intValue()))
            .andExpect(jsonPath("$.readBooks").value(DEFAULT_READ_BOOKS));
    }

    @Test
    @Transactional
    public void getNonExistingUserPreferences() throws Exception {
        // Get the userPreferences
        restUserPreferencesMockMvc.perform(get("/app/rest/userPreferencess/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserPreferences() throws Exception {
        // Initialize the database
        userPreferencesRepository.saveAndFlush(userPreferences);

        // Update the userPreferences
        userPreferences.setReadBooks(UPDATED_READ_BOOKS);
        restUserPreferencesMockMvc.perform(post("/app/rest/userPreferencess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userPreferences)))
                .andExpect(status().isOk());

        // Validate the UserPreferences in the database
        List<UserPreferences> userPreferencess = userPreferencesRepository.findAll();
        assertThat(userPreferencess).hasSize(1);
        UserPreferences testUserPreferences = userPreferencess.iterator().next();
        assertThat(testUserPreferences.getReadBooks()).isEqualTo(UPDATED_READ_BOOKS);;
    }

    @Test
    @Transactional
    public void deleteUserPreferences() throws Exception {
        // Initialize the database
        userPreferencesRepository.saveAndFlush(userPreferences);

        // Get the userPreferences
        restUserPreferencesMockMvc.perform(delete("/app/rest/userPreferencess/{id}", userPreferences.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<UserPreferences> userPreferencess = userPreferencesRepository.findAll();
        assertThat(userPreferencess).hasSize(0);
    }
}
