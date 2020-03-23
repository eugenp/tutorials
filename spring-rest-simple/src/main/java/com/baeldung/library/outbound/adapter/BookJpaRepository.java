package com.baeldung.library.outbound.adapter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.baeldung.library.domain.Book;
import com.baeldung.library.outbound.port.IBookPersistence;

@Repository
public class BookJpaRepository implements IBookPersistence {

    @PersistenceContext
    EntityManager entityManager;

    public void publishBook(Book book) {
        entityManager.persist(book);
    }
}
