package com.baeldung.chooseapi.controllers;

import com.baeldung.chooseapi.dtos.Book;
import com.baeldung.chooseapi.services.BooksService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.graphql.data.method.annotation.QueryMapping;

@RestController
public class BooksControllerGraphQL {

    private final BooksService booksService;

    public BooksControllerGraphQL(BooksService booksService) {
        this.booksService = booksService;
    }

    @QueryMapping
    public List<Book> books() {
        return booksService.getBooks();
    }

}

