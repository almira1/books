package com.mycompany.myapp.repository;

import java.util.List;
import java.util.Set;

import com.mycompany.myapp.domain.Author;
import com.mycompany.myapp.domain.Book;
import com.mycompany.myapp.domain.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Spring Data JPA repository for the Author entity.
 */
public interface AuthorRepository extends JpaRepository<Author, Long> {
	
	  @Query("select author from Author author left join fetch author.books")
	    List<Author> findEagerRelationships();
}
