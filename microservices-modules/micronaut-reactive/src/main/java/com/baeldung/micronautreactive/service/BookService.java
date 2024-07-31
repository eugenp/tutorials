package com.baeldung.micronautreactive.service;

import com.baeldung.micronautreactive.entity.Book;
import com.baeldung.micronautreactive.repository.BookRepository;
import jakarta.inject.Singleton;
import org.bson.types.ObjectId;
import reactor.core.publisher.Flux;

@Singleton
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public ObjectId save(Book book) {
        Book savedBook = bookRepository.save(book).block();
        return null != savedBook ? savedBook.getId() : null;
    }

    public ObjectId update(Book book) {
        Book updatedBook = bookRepository.update(book).block();
        return null != updatedBook ? updatedBook.getId() : null;
    }

    public Long deleteById(String id) {
        return bookRepository.deleteById(new ObjectId(id)).block();
    }

    public Flux<Book> findByYearGreaterThan(int year) {
        return bookRepository.findByYearGreaterThan(year);
    }

    public Book findById(String id) {
        return bookRepository.findById(new ObjectId(id)).block();
    }
}
