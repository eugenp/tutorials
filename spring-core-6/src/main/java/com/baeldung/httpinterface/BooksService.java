package com.baeldung.httpinterface;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

interface BooksService {

    @GetExchange("/books")
    List<Book> getBooks();

    @GetExchange("/books/{title}")
    Book getBook(@PathVariable String title);

}
