package com.baeldung.hexagonal.architecture.adapter;

import com.baeldung.hexagonal.architecture.core.domain.Book;
import com.baeldung.hexagonal.architecture.port.BookService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookRestController {

    private BookService bookService;

    public BookRestController(BookService bookService){
        this.bookService = bookService;
    }

    @PostMapping
    public void create(@RequestBody Book book) {
        this.bookService.create(book);
    }

    @DeleteMapping ("/{id}")
    public void delete(@PathVariable long id) {
        this.bookService.delete(id);
    }

    @GetMapping("/{id}")
    public Book get(@PathVariable long id) {
       return this.bookService.get(id);
    }
}
