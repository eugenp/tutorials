package com.baeldung.hexagonalarchitecture.adapter;

import com.baeldung.hexagonalarchitecture.core.domain.Book;
import com.baeldung.hexagonalarchitecture.port.BookServicePort;
import com.baeldung.hexagonalarchitecture.web.BookUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookControllerAdapter implements BookUI {

    @Autowired
    private BookServicePort bookService;

    public Book getBook(Long id) {
        return bookService.getBook(id);
    }
}
