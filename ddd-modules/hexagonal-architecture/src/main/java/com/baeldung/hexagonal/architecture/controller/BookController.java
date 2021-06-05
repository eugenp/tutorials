package com.baeldung.hexagonal.architecture.controller;

import com.baeldung.hexagonal.architecture.service.BookDTO;
import com.baeldung.hexagonal.architecture.service.BookServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {
    private BookServicePort bookService;

    @Autowired
    public BookController(final BookServicePort bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/filter")
    public ResponseEntity<BookDTO> findByFilters(
      @RequestParam String name, @RequestParam(name = "shelf_no") Integer shelfNo) {
        return ResponseEntity.ok(bookService.findBook(name, shelfNo));
    }
}
