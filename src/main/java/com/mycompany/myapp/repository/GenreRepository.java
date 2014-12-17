package com.mycompany.myapp.repository;

import java.util.List;
import java.util.Set;

import com.mycompany.myapp.domain.Author;
import com.mycompany.myapp.domain.Comment;
import com.mycompany.myapp.domain.Genre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Spring Data JPA repository for the Genre entity.
 */
public interface GenreRepository extends JpaRepository<Genre, Long> {

	 
}
