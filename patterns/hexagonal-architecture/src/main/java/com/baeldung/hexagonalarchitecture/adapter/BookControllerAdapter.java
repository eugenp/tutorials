package com.baeldung.hexagonalarchitecture.adapter;

import com.baeldung.hexagonalarchitecture.core.domain.Book;
import com.baeldung.hexagonalarchitecture.core.port.BookControllerPort;
import com.baeldung.hexagonalarchitecture.core.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookControllerAdapter implements BookControllerPort {

    @Autowired
    private BookService bookService;

    @Override
    public Book getBook(Long id) {
        return bookService.getBook(id);
    }
}
