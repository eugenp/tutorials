package com.baeldung.hexagonal.architecture.infrastructure.driven.dataside.control;

import com.baeldung.hexagonal.architecture.application.boundary.BookRepository;
import com.baeldung.hexagonal.architecture.application.entity.Book;
import com.baeldung.hexagonal.architecture.infrastructure.driven.dataside.boundary.BookJpaRepository;
import com.baeldung.hexagonal.architecture.infrastructure.driven.dataside.entity.BookEntity;
import org.springframework.beans.factory.annotation.Autowired;

public class DatabaseBookRepository implements BookRepository {
    @Autowired
    BookJpaRepository bookJpaRepository;

    @Override
    public void save(Book book) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setAuthor(book.getAuthor());
        bookEntity.setTitle(book.getTitle());
        bookJpaRepository.save(bookEntity);
    }
}
