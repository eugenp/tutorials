package org.baeldung.boot.client;

import java.util.List;

import org.baeldung.boot.domain.Book;
import org.baeldung.boot.domain.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BookConsumer {
    private final static Logger LOGGER = LoggerFactory.getLogger(BookConsumer.class);

    BookService bookService;

    public BookConsumer(BookService bookService) {
        this.bookService = bookService;
        consumeBooks();
    }

    private void consumeBooks() {

        List<Book> booksByAuthor = bookService.getBooksByAuthor("Isaac Asimov");
        for (Book b : booksByAuthor) {
            LOGGER.info("Found book for {} : {}", b.getAuthor(), b.getName());
        }
    }

}
