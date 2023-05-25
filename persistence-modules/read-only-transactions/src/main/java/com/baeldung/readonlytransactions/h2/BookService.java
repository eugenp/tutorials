package com.baeldung.readonlytransactions.h2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;

@Service
public class BookService {

    private EntityManagerFactory entityManagerFactory;

    public BookService(@Autowired @Qualifier("h2EntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Transactional(readOnly = true)
    public Book getBookById(long id) {
        return entityManagerFactory.createEntityManager()
            .find(Book.class, id);
    }
}
