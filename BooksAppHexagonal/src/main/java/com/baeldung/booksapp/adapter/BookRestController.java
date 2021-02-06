package com.baeldung.booksapp.adapter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.booksapp.core.domain.Book;
import com.baeldung.booksapp.port.BookService;
import com.baeldung.booksapp.web.BookRestUI;

@RestController
@RequestMapping("/book")
public class BookRestController implements BookRestUI {

    @Autowired
    private BookService bookService;

    @Override
    public void createBook(@RequestBody Book book) {
        bookService.createBook(book);
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
