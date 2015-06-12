package com.baeldung.multitenancy.service;

import com.baeldung.multitenancy.entities.Book;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * MultitenantService-- we use the SessionFactory to save and fetch entities.
 * User: baeldung
 * Date: 9/06/15
 */

@Service
public class MultiTenantService {

    @AutoWired
    private SessionFactory sessionFactory;

    public void save(Book book) {
        sessionFactory.getCurrentSession().save(book);
    }

    public Book findBook(Long id) {
        return (Book) sessionFactory.
                getCurrentSession().get(Book.class, id);
    }

    public List<Book> findAllBooks() {
        return sessionFactory.
                getCurrentSession().createQuery("from Books").list();
    }

    public void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }
}
