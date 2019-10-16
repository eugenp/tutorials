package com.baeldung.hexagonalarchitecture.adapter.repository;

import com.baeldung.hexagonalarchitecture.core.domain.Book;
import com.baeldung.hexagonalarchitecture.port.BookRepositoryPort;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
public class BookRepositoryAdapter implements BookRepositoryPort {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Book getBook(Long id) {
        return entityManager.find(Book.class, id);
    }

    public void insertBook(Book book) {
        entityManager.persist(book);
    }
}
