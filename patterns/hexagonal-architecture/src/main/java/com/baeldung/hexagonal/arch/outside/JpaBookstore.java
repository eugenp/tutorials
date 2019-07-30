package com.baeldung.hexagonal.arch.outside;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.baeldung.hexagonal.arch.inside.Book;
import com.baeldung.hexagonal.arch.inside.Bookstore;

public class JpaBookstore implements Bookstore {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void create(String title) {
        Book book = new Book();
        book.setTitle(title);
        entityManager.persist(book);
    }

    @Override
    public List<Book> getBooks() {
        return entityManager.createQuery("SELECT b from Book b", Book.class)
            .getResultList();
    }

}
