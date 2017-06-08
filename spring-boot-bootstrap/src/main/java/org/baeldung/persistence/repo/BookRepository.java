package org.baeldung.persistence.repo;

import java.util.List;

import org.baeldung.persistence.model.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findByTitle(String title);
}
