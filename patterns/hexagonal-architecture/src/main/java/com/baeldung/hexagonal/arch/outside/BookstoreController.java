package com.baeldung.hexagonal.arch.outside;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.arch.inside.Book;
import com.baeldung.hexagonal.arch.inside.Bookstore;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController("bookstoreController")
@RequestMapping("books")
public class BookstoreController {

    private final Bookstore bookstore;

    @GetMapping
    public List<Book> getBooks() {
        return bookstore.getBooks();
    }

    @PostMapping
    public void create(@RequestParam String title) {
        bookstore.create(title);
    }
}
