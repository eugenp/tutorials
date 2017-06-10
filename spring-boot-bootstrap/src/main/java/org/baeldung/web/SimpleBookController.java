package org.baeldung.web;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.baeldung.persistence.model.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class SimpleBookController {

    @GetMapping("/{id}")
    public Book getBook(@PathVariable Long id) {
        final Book book = new Book();
        book.setId(1L);
        book.setTitle(randomAlphabetic(10));
        book.setAuthor(randomAlphabetic(15));
        return book;
    }

}
