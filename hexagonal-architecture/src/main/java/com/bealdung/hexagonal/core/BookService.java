package com.bealdung.hexagonal.core;

import com.bealdung.hexagonal.port.BookRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookRepositoryPort bookRepository;

    public void create(String name, String author, int pages) {
        bookRepository.create(name, author, pages);
    }

    public Book view(Long bookId) {
        return bookRepository.getBook(bookId);
    }
}
