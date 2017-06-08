package org.baeldung.persistence.repo;

import org.baeldung.persistence.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends CrudRepository<Book, Long> {
    Page<Book> findByTitle(@Param("title") String title, Pageable pageable);
}
