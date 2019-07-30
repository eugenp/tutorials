package com.baeldung.hexagonal.arch.outside;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.arch.inside.Book;
import com.baeldung.hexagonal.arch.inside.BookService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController("bookstoreController")
@RequestMapping("books")
public class BookstoreController {

    private final BookService bookstore;

    @GetMapping
    public List<Book> getBooks() {
        return bookstore.getBooks();
    }

    @PostMapping
    public void save(@RequestParam String title) {
        bookstore.save(title);
    }
}
