package com.baeldung.repository;
import com.baeldung.domain.Book;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;

/**
 * = BookRepository
 TODO Auto-generated class documentation
 *
 */
@RooJpaRepository(entity = Book.class)
public interface BookRepository {
}
