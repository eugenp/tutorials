package com.baeldung.hexarchitecture.adapter.in;

import com.baeldung.hexarchitecture.application.port.in.CreateBookCommand;
import com.baeldung.hexarchitecture.application.port.in.BookService;
import com.baeldung.hexarchitecture.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public void createBook(@RequestBody CreateBookCommand createBookCommand) {
        bookService.createBook(createBookCommand);
    }

    @GetMapping(path = "/{id}")
    public Book createBook(@PathParam("id") String id) {
        return bookService.getBook(id);
    }

}
