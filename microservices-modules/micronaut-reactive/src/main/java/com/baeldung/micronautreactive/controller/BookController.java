package com.baeldung.micronautreactive.controller;

import com.baeldung.micronautreactive.dtos.BookNotFoundException;
import com.baeldung.micronautreactive.entity.Book;
import com.baeldung.micronautreactive.service.BookService;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.annotation.*;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import reactor.core.publisher.Flux;

@Controller("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Post
    public String createBook(@Valid @Body Book book) {
        @Nullable ObjectId bookId = bookService.save(book);
        if (null == bookId) {
            return "Book not created";
        } else {
            return "Book created with id: " + bookId;
        }
    }

    @Put
    public String updateBook(@Valid @Body Book book) {
        @Nullable ObjectId bookId = bookService.update(book);
        if (null == bookId) {
            return "Book not updated";
        } else {
            return "Book updated with id: " + bookId;
        }
    }

    @Delete("/{id}")
    public String deleteBook(String id) throws BookNotFoundException {
        Long bookId = bookService.deleteById(id);
        if (1 == bookId) {
            return "Book deleted";
        } else {
            throw new BookNotFoundException(id);
        }
    }

    @Get("/{id}")
    public Book findById(@PathVariable("id") String identifier) throws BookNotFoundException {
        Book book = bookService.findById(identifier);
        if (null == book) {
            throw new BookNotFoundException(identifier);
        } else {
            return book;
        }
    }

    @Get("/published-after")
    public Flux<Book> findByYearGreaterThan(@QueryValue(value = "year") int year) {
        return bookService.findByYearGreaterThan(year);
    }

    @Error(exception = ConstraintViolationException.class)
    public MutableHttpResponse<String> onSavedFailed(ConstraintViolationException ex) {
        return HttpResponse.badRequest(ex.getConstraintViolations().stream()
          .map(cv -> cv.getPropertyPath() + " " + cv.getMessage())
          .toList().toString());
    }

    @Error(exception = BookNotFoundException.class)
    public HttpResponse<String> onSavedFailed(BookNotFoundException ex) {
        return HttpResponse.notFound(ex.getMessage());
    }

}
