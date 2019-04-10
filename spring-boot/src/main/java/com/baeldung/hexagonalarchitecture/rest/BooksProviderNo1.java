package com.baeldung.hexagonalarchitecture.rest;

import com.baeldung.hexagonalarchitecture.model.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class BooksProviderNo1 {

    @GetMapping("/technicalbooks")
    public List<Book> getBooks() {
        Book book1 = new Book("Spring in action", "Craig Walls");
        Book book2 = new Book("Clean Code", "Robert C. Martin");

        return Arrays.asList(book1, book2);
    }
}
