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
import com.mycompany.myapp.domain.Book;
import com.mycompany.myapp.repository.BookRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BookResource REST controller.
 *
 * @see BookResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class BookResourceTest {

    private static final String DEFAULT_TITLE = "SAMPLE_TEXT";
    private static final String UPDATED_TITLE = "UPDATED_TEXT";
    
    private static final String DEFAULT_SUMMARY = "SAMPLE_TEXT";
    private static final String UPDATED_SUMMARY = "UPDATED_TEXT";
    
    private static final String DEFAULT_FILE_PATH = "SAMPLE_TEXT";
    private static final String UPDATED_FILE_PATH = "UPDATED_TEXT";
    
    private static final Integer DEFAULT_RATE = 0;
    private static final Integer UPDATED_RATE = 1;
    
    private static final String DEFAULT_PICTURE = "SAMPLE_TEXT";
    private static final String UPDATED_PICTURE = "UPDATED_TEXT";
    

    @Inject
    private BookRepository bookRepository;

    private MockMvc restBookMockMvc;

    private Book book;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BookResource bookResource = new BookResource();
        ReflectionTestUtils.setField(bookResource, "bookRepository", bookRepository);
        this.restBookMockMvc = MockMvcBuilders.standaloneSetup(bookResource).build();
    }

    @Before
    public void initTest() {
        book = new Book();
        book.setTitle(DEFAULT_TITLE);
        book.setSummary(DEFAULT_SUMMARY);
        book.setFilePath(DEFAULT_FILE_PATH);
        book.setRate(DEFAULT_RATE);
        book.setPicture(DEFAULT_PICTURE);
    }

    @Test
    @Transactional
    public void createBook() throws Exception {
        // Validate the database is empty
        assertThat(bookRepository.findAll()).hasSize(0);

        // Create the Book
        restBookMockMvc.perform(post("/app/rest/books")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(book)))
                .andExpect(status().isOk());

        // Validate the Book in the database
        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(1);
        Book testBook = books.iterator().next();
        assertThat(testBook.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testBook.getSummary()).isEqualTo(DEFAULT_SUMMARY);
        assertThat(testBook.getFilePath()).isEqualTo(DEFAULT_FILE_PATH);
        assertThat(testBook.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testBook.getPicture()).isEqualTo(DEFAULT_PICTURE);
    }

    @Test
    @Transactional
    public void getAllBooks() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the books
        restBookMockMvc.perform(get("/app/rest/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(book.getId().intValue()))
                .andExpect(jsonPath("$.[0].title").value(DEFAULT_TITLE.toString()))
                .andExpect(jsonPath("$.[0].summary").value(DEFAULT_SUMMARY.toString()))
                .andExpect(jsonPath("$.[0].filePath").value(DEFAULT_FILE_PATH.toString()))
                .andExpect(jsonPath("$.[0].rate").value(DEFAULT_RATE))
                .andExpect(jsonPath("$.[0].picture").value(DEFAULT_PICTURE.toString()));
    }

    @Test
    @Transactional
    public void getBook() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get the book
        restBookMockMvc.perform(get("/app/rest/books/{id}", book.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(book.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.summary").value(DEFAULT_SUMMARY.toString()))
            .andExpect(jsonPath("$.filePath").value(DEFAULT_FILE_PATH.toString()))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE))
            .andExpect(jsonPath("$.picture").value(DEFAULT_PICTURE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBook() throws Exception {
        // Get the book
        restBookMockMvc.perform(get("/app/rest/books/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBook() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Update the book
        book.setTitle(UPDATED_TITLE);
        book.setSummary(UPDATED_SUMMARY);
        book.setFilePath(UPDATED_FILE_PATH);
        book.setRate(UPDATED_RATE);
        book.setPicture(UPDATED_PICTURE);
        restBookMockMvc.perform(post("/app/rest/books")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(book)))
                .andExpect(status().isOk());

        // Validate the Book in the database
        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(1);
        Book testBook = books.iterator().next();
        assertThat(testBook.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBook.getSummary()).isEqualTo(UPDATED_SUMMARY);
        assertThat(testBook.getFilePath()).isEqualTo(UPDATED_FILE_PATH);
        assertThat(testBook.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testBook.getPicture()).isEqualTo(UPDATED_PICTURE);;
    }

    @Test
    @Transactional
    public void deleteBook() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get the book
        restBookMockMvc.perform(delete("/app/rest/books/{id}", book.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(0);
    }
}
