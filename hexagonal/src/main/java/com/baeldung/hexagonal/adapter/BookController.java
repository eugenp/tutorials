package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.core.domain.Book;
import com.baeldung.hexagonal.port.BookService;
import com.baeldung.hexagonal.web.BookUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;
    
    @PostMapping
    public void addBook(@RequestBody Book book) {
        bookService.addBook(book);
    }

    @GetMapping("/{title}")
    public Book getBook(@PathVariable String title) {
        return bookService.getBook(title);
    }
}
