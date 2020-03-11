package com.baeldung.hexbookstore.application.rest;

import com.baeldung.hexbookstore.core.BookStore;
import com.baeldung.hexbookstore.core.service.BookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/store")
public class BookStoreController {

    private final BookStoreService bookStoreService;

    @Autowired
    public BookStoreController(BookStoreService bookStoreService) {
        this.bookStoreService = bookStoreService;
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    void addBook(@RequestBody BookStore book) {
        bookStoreService.addBook(book);
    }

    @DeleteMapping(value = "/delete/{id}")
    void sellBook(@PathVariable Long id) {
        bookStoreService.sellBook(id);
    }

    @GetMapping(value = "/getAll")
    List<BookStore> listAllBooks() {
        return bookStoreService.listAllBooks();
    }
}
