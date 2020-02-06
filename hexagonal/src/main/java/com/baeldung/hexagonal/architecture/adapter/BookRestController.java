package com.baeldung.hexagonal.architecture.adapter;

import com.baeldung.hexagonal.architecture.core.domain.Book;
import com.baeldung.hexagonal.architecture.port.BookService;
import com.baeldung.hexagonal.architecture.web.BookRestWeb;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookRestController implements BookRestWeb {

    private BookService bookService;

    public BookRestController(BookService bookService){
        this.bookService = bookService;
    }

    @Override
    public void create(@RequestBody Book book) {
        this.bookService.create(book);
    }

    @Override
    public void delete(@PathVariable long id) {
        this.bookService.delete(id);
    }

    @Override
    public Book get(@PathVariable long id) {
       return this.bookService.get(id);
    }
}
