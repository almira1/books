package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the Book entity.
 */
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select book from Book book left join fetch book.authors left join fetch book.genres left join fetch book.users where book.id = :id")
    Book findOneWithEagerRelationships(@Param("id") Long id);

}
