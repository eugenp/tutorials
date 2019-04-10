package com.baeldung.hexagonalarchitecture.rest;

import com.baeldung.hexagonalarchitecture.model.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class BooksProviderNo2 {

    @GetMapping("/books/all")
    public List<Book> getBooks() {
        Book book1 = new Book("Lean in", "Sheryl Sandberg");
        Book book2 = new Book("Mindset", "Carol Dweck");

        return Arrays.asList(book1, book2);
    }
}
