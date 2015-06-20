package com.baeldung.multitenancy.service;

import com.baeldung.multitenancy.entities.Book;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
