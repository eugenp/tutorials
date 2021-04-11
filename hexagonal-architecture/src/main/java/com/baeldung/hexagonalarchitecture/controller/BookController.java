package com.baeldung.hexagonalarchitecture.controller;

import com.baeldung.hexagonalarchitecture.models.Book;
import com.baeldung.hexagonalarchitecture.services.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class BookController {

    Logger logger = LoggerFactory.getLogger(BookController.class);

    private BookService service;

    @Autowired
    public BookController(BookService bookService) {
        service = bookService;
    }

    @PostMapping("/create")
    public ResponseEntity<Book> createBook(@RequestBody Book book){
        logger.info("Transactional remark");
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createBook(book));
    }

    @GetMapping("/list")
    public ResponseEntity<List<Book>> getAllBooks(){
        return ResponseEntity.ok().body(service.findAllBook());
    }


}
