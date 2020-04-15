package com.baeldung.hexagonal.controller;

import com.baeldung.hexagonal.domain.Book;
import com.baeldung.hexagonal.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {


    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping(value = "/book/{bookname}", method = RequestMethod.GET)
    public ResponseEntity findBookByName(@PathVariable String bookname) {
        Optional<List<Book>> bookList = bookService.findBookByName(bookname);
        return bookList.map(books -> new ResponseEntity(books, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>("Book with name " + bookname + " was not found", HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/book/all", method = RequestMethod.GET)
    public ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
    }

    @RequestMapping(value = "/book", method = RequestMethod.POST)
    public ResponseEntity<String> addBook(@RequestBody Book newBook) {
        int bookId = bookService.addBook(newBook);
        if (bookId >= 0) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }
}