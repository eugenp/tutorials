package com.baeldung.hexagonalarchitecture.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonalarchitecture.domain.Book;
import com.baeldung.hexagonalarchitecture.domain.IBookService;

@RestController
public class BookController {

    @Autowired
    private IBookService bookService;

    @GetMapping("/books")
    ResponseEntity<List<Book>> listAllBooks() {
        return ResponseEntity.ok(bookService.listAllBooks());
    }
}