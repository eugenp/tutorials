package com.demo.controllers;

import com.demo.models.Book;
import com.demo.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;


    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Book> getAll() {
        return bookService.getAllBooks();
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UUID createChgRequest(@RequestBody Book newBook) {
        return bookService.createBook(newBook);
    }

    @GetMapping(value = "/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Book getBookById(@PathVariable UUID uuid) {
        Optional<Book> bookById = bookService.getBookById(uuid);
        return bookById.orElse(null);
    }

    @DeleteMapping(value = "/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteBookById(@PathVariable UUID uuid) {
        bookService.deleteBookById(uuid);
        return ResponseEntity.noContent().build();
    }
}
