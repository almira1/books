package com.mycompany.myapp.repository;

import java.util.List;
import java.util.Set;

import com.mycompany.myapp.domain.Comment;
import com.mycompany.myapp.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the Comment entity.
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

	@Query("select c from Comment c where c.book.id = :id")
    Set<Comment> getCommentsForBook(@Param("id") Long id);
}
