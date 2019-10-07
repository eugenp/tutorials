package com.baeldung.hexagonal.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.hexagonal.domain.Book;
import com.baeldung.hexagonal.port.BookOutboundPort;
import com.baeldung.hexagonal.repository.BookEntity;
import com.baeldung.hexagonal.repository.BookRepository;

@Component
public class BookRepositoryAdapter implements BookOutboundPort {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void addBook(Book book) {
        BookEntity bookEntity = new BookEntity(book.getName(), book.getAuthor(), book.getPrice());
        bookRepository.saveAndFlush(bookEntity);
    }

    @Override
    public Book getBook(String bookName) {
        BookEntity bookEntity = bookRepository.findByName(bookName);
        if (null != bookEntity) {
            return new Book(bookEntity.getName(), bookEntity.getAuthor(), bookEntity.getPrice());
        }
        return null;
    }
}
