package com.baeldung.micronautreactive.controller;

import com.baeldung.micronautreactive.entity.Book;
import com.baeldung.micronautreactive.service.BookService;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.annotation.*;
import org.bson.types.ObjectId;
import reactor.core.publisher.Flux;

@Controller("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Post
    public String createBook(@Body Book book) {
        @Nullable ObjectId bookId = bookService.save(book);
        if (null == bookId) {
            return "Book not created";
        } else {
            return "Book created with id: " + bookId;
        }
    }

    @Put
    public String updateBook(@Body Book book) {
        @Nullable ObjectId bookId = bookService.update(book);
        if (null == bookId) {
            return "Book not updated";
        } else {
            return "Book updated with id: " + bookId;
        }
    }

    @Delete("/{id}")
    public String deleteBook(String id) {
        Long bookId = bookService.deleteById(id);
        if (0 == bookId) {
            return "Book not deleted";
        } else {
            return "Book deleted with id: " + bookId;
        }
    }

    @Get("/{id}")
    public Book findById(@PathVariable("id") String identifier) {
        return bookService.findById(identifier);
    }

    @Get("/published-after")
    public Flux<Book> findByYearGreaterThan(@QueryValue(value = "year") int year) {
        return bookService.findByYearGreaterThan(year);
    }

}
