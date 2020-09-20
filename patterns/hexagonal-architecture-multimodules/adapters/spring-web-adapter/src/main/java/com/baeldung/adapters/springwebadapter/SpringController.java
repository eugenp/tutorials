package com.baeldung.adapters.springwebadapter;

import com.baeldung.adapters.hexagonalarchitecturemultimodulescontroller.WebPort;
import domain.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
public class SpringController {

    private final WebPort webPort = new WebPort();

    @GetMapping("/author")
    public List<Book> findAllBooks(@RequestParam String authorName) {
        return webPort.findAllBooksFromAuthor(authorName);
    }

    @GetMapping
    public Book getBookById(@RequestParam String id) {
        return webPort.getBookById(id);
    }
}
