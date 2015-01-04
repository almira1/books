package com.mycompany.myapp.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mycompany.myapp.domain.Book;
import com.mycompany.myapp.domain.Comment;
import com.mycompany.myapp.domain.Readlist;

public interface ReadlistRepository extends JpaRepository<Readlist, Long> {

	@Query("select r from Readlist r where r.book.id = :id")
    Set<Readlist> getReadlistsForBook(@Param("id") Long id);
	
}
