package com.baeldung.hexagonal.architecture.core.service;

import com.baeldung.hexagonal.architecture.core.domain.Book;
import com.baeldung.hexagonal.architecture.port.BookRepo;
import com.baeldung.hexagonal.architecture.port.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private BookRepo bookRepo;

    public BookServiceImpl(BookRepo bookRepo){
        this.bookRepo = bookRepo;
    }

    @Override
    public void create(Book book) {
        this.bookRepo.create(book);
    }

    @Override
    public void delete(long id) {
        this.bookRepo.delete(id);
    }

    @Override
    public Book get(long id) {
        return this.bookRepo.get(id);
    }
}
