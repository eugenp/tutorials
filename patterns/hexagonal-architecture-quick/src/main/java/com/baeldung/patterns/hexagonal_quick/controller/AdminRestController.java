package com.baeldung.patterns.hexagonal_quick.controller;

import org.springframework.web.bind.annotation.*;

import com.baeldung.patterns.hexagonal_quick.controller.model.ApiBook;
import com.baeldung.patterns.hexagonal_quick.domain.Book;
import com.baeldung.patterns.hexagonal_quick.port.AdminInputPort;

@RestController
@RequestMapping("/admin")
public class AdminRestController {
    private final AdminInputPort adminInputPort;

    public AdminRestController(AdminInputPort adminInputPort) {
        this.adminInputPort = adminInputPort;
    }

    @GetMapping("/book/{isbn}")
    public ApiBook lookupBook(@PathVariable String isbn) {
        final Book book = adminInputPort.getBook(isbn);
        return ApiBook.createFrom(book);
    }

    @PostMapping("/book")
    public ApiBook createBook(@RequestBody ApiBook createBookRequest) {
        final Book book = adminInputPort.addBook(createBookFrom(createBookRequest));
        return ApiBook.createFrom(book);
    }

    private static Book createBookFrom(ApiBook apiBook) {
        return new Book(apiBook.getIsbnNumber(), apiBook.getBookName(), apiBook.getAuthorNames());
    }
}
