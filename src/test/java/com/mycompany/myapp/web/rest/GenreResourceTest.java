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
import com.mycompany.myapp.domain.Genre;
import com.mycompany.myapp.repository.GenreRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GenreResource REST controller.
 *
 * @see GenreResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class GenreResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";
    

    @Inject
    private GenreRepository genreRepository;

    private MockMvc restGenreMockMvc;

    private Genre genre;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GenreResource genreResource = new GenreResource();
        ReflectionTestUtils.setField(genreResource, "genreRepository", genreRepository);
        this.restGenreMockMvc = MockMvcBuilders.standaloneSetup(genreResource).build();
    }

    @Before
    public void initTest() {
        genre = new Genre();
        genre.setName(DEFAULT_NAME);
        genre.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createGenre() throws Exception {
        // Validate the database is empty
        assertThat(genreRepository.findAll()).hasSize(0);

        // Create the Genre
        restGenreMockMvc.perform(post("/app/rest/genres")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(genre)))
                .andExpect(status().isOk());

        // Validate the Genre in the database
        List<Genre> genres = genreRepository.findAll();
        assertThat(genres).hasSize(1);
        Genre testGenre = genres.iterator().next();
        assertThat(testGenre.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGenre.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllGenres() throws Exception {
        // Initialize the database
        genreRepository.saveAndFlush(genre);

        // Get all the genres
        restGenreMockMvc.perform(get("/app/rest/genres"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(genre.getId().intValue()))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.[0].description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getGenre() throws Exception {
        // Initialize the database
        genreRepository.saveAndFlush(genre);

        // Get the genre
        restGenreMockMvc.perform(get("/app/rest/genres/{id}", genre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(genre.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGenre() throws Exception {
        // Get the genre
        restGenreMockMvc.perform(get("/app/rest/genres/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGenre() throws Exception {
        // Initialize the database
        genreRepository.saveAndFlush(genre);

        // Update the genre
        genre.setName(UPDATED_NAME);
        genre.setDescription(UPDATED_DESCRIPTION);
        restGenreMockMvc.perform(post("/app/rest/genres")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(genre)))
                .andExpect(status().isOk());

        // Validate the Genre in the database
        List<Genre> genres = genreRepository.findAll();
        assertThat(genres).hasSize(1);
        Genre testGenre = genres.iterator().next();
        assertThat(testGenre.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGenre.getDescription()).isEqualTo(UPDATED_DESCRIPTION);;
    }

    @Test
    @Transactional
    public void deleteGenre() throws Exception {
        // Initialize the database
        genreRepository.saveAndFlush(genre);

        // Get the genre
        restGenreMockMvc.perform(delete("/app/rest/genres/{id}", genre.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Genre> genres = genreRepository.findAll();
        assertThat(genres).hasSize(0);
    }
}
