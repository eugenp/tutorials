package com.baeldung.hexagoninjava.adapter;


import java.util.List;

import com.baeldung.hexagoninjava.core.domain.Book;
import com.baeldung.hexagoninjava.port.BookService;
import com.baeldung.hexagoninjava.web.BookRestUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookRestController implements BookRestUI {

    @Autowired
    private BookService bookService;

    @Override
    public void createBook(@RequestBody Book pizza) {
        bookService.createBook(pizza);
    }

    @Override
    public Book getBook(@PathVariable String name) {
        return bookService.getBook(name);
    }

    @Override
    public List<Book> listBook() {
        return bookService.listBook();
    }
}