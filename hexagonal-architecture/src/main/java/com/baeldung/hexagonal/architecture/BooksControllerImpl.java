package com.baeldung.hexagonal.architecture;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BooksControllerImpl implements BooksController {
    private BooksDao booksDao;

    public BooksControllerImpl(BooksDao booksDao) {
        this.booksDao = booksDao;
    }

    @GetMapping("/books")
    @Override
    public List<Book> getBooks() {
        return booksDao.findAllBooks();
    }
}
