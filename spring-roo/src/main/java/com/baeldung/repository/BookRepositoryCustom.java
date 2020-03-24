package com.baeldung.repository;
import com.baeldung.domain.Book;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustom;

/**
 * = BookRepositoryCustom
 TODO Auto-generated class documentation
 *
 */
@RooJpaRepositoryCustom(entity = Book.class)
public interface BookRepositoryCustom {
}
