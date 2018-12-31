package com.baeldung.hexagonal.bookstore.jpa;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.baeldung.hexagonal.bookstore.entity.Book;

public class JpaBookDao implements Dao<Book>{
    
    private final EntityManager entityManager;
    
    public JpaBookDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public Optional<Book> get(String isbn) {
        return Optional.ofNullable(entityManager.find(Book.class, isbn));
    }
    
    @Override
    public List<Book> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM Book e");
        return query.getResultList();
    }
    
    @Override
    public void save(Book book) {
        executeInsideTransaction(entityManager -> entityManager.persist(book));
    }
    
    @Override
    public void update(Book book, String[] params) {
        book.setAuthorName(Objects.requireNonNull(params[0], "Author name cannot be null"));
        book.setTitle(Objects.requireNonNull(params[1], "Title cannot be null"));
        executeInsideTransaction(entityManager -> entityManager.merge(book));
    }
    
    @Override 
    public void delete(Book book) {
        executeInsideTransaction(entityManager -> entityManager.remove(book));
    }
    
    private void executeInsideTransaction(Consumer<EntityManager> action) {
        final EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            action.accept(entityManager);
            tx.commit(); 
        }
        catch (RuntimeException e) {
            tx.rollback();
            throw e;
        }
    }    
}
