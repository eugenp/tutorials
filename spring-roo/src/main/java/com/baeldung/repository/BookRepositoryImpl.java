package com.baeldung.repository;

import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
import com.baeldung.domain.Book;

/**
 * = BookRepositoryImpl
 *
 * TODO Auto-generated class documentation
 *
 */ 
@RooJpaRepositoryCustomImpl(repository = BookRepositoryCustom.class)
public class BookRepositoryImpl extends QueryDslRepositorySupportExt<Book> {

    /**
     * TODO Auto-generated constructor documentation
     */
    BookRepositoryImpl() {
        super(Book.class);
    }
}