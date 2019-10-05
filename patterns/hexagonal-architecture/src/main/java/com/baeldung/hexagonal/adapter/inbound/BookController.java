package com.baeldung.hexagonal.adapter.inbound;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.domain.Book;
import com.baeldung.hexagonal.exception.APIError;
import com.baeldung.hexagonal.exception.ApiRequestException;
import com.baeldung.hexagonal.port.inbound.BookInboundPort;
import com.baeldung.hexagonal.service.BookService;

@RestController
public class BookController implements BookInboundPort {

    @Autowired
    private BookService bookService;

    @PostMapping(value = "/book")
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody Book book) {
        try {
            bookService.addBook(book.getName(), book.getAuthor(), book.getPrice());
        } catch (RuntimeException exc) {
            throw new ApiRequestException(HttpStatus.BAD_REQUEST, new APIError("Bad Request", exc.getMessage()));
        }
    }

    @Override
    @GetMapping(value = "/book/{name}")
    public Book search(@PathVariable String name) {
        Book book = bookService.search(name);
        if (null == book) {
            throw new ApiRequestException(HttpStatus.NOT_FOUND, new APIError("Not Found", "book '" + name + "' is not found"));
        }
        return book;
    }
}
