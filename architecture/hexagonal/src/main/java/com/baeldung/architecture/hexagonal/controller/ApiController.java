package com.baeldung.architecture.hexagonal.controller;

import com.baeldung.architecture.hexagonal.model.Book;
import com.baeldung.architecture.hexagonal.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping("/api/v1") public class ApiController {

        BookService bookService;

        @Autowired public ApiController(BookService bookService) {
                this.bookService = bookService;
        }

        @GetMapping("/books") List<Book> getAllBooks() {

                return bookService.getAllBooks();

        }

        @GetMapping("/book") Book getBookByIsbn(@RequestParam("isbn") Long isbn) {

                return bookService.getBook(isbn);

        }

        @PostMapping("/book") Book saveBook(@RequestBody Book book) {

                return bookService.saveBook(book);

        }
}
