package com.baeldung.ddd.hexagonal.app.adapters.out.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baeldung.ddd.hexagonal.app.domain.Book;
import com.baeldung.ddd.hexagonal.app.ports.out.BookRepository;

@Repository
public class BookRepositoryImpl implements BookRepository {
	
    @Autowired
    private JpaBookRepository jpaBookRepository;

    @Override
    public Book save(Book book) {
        return jpaBookRepository.save(book);
    }

    @Override
    public Book findById(Long bookId) {
        return jpaBookRepository.findById(bookId).orElse(null);
    }

    @Override
    public List<Book> findAllBooks() {
	    return jpaBookRepository.findAll();
    }
}
