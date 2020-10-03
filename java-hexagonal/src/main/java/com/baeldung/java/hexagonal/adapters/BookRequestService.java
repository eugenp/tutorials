package com.baeldung.java.hexagonal.adapters;


import com.baeldung.java.hexagonal.domain.ports.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookRequestService {

    @Autowired
    BookService bookService;

    public BookCreateResponse createBook(BookCreateRequest bookCreateRequest) {
        String id = bookService.createBook(bookCreateRequest.getName());
        return new BookCreateResponse().
                setId(id);
    }
}
