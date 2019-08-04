package org.baeldung.boot.client;

import org.baeldung.boot.domain.Book;
import org.baeldung.boot.domain.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BookProducer {
    private final static Logger LOGGER = LoggerFactory.getLogger(BookProducer.class);

    private BookService bookService;

    public BookProducer(BookService bookService) {
        this.bookService = bookService;
        produceBooks();
    }

    private void produceBooks() {
        Book book1 = new Book("Foundation", "Isaac Asimov");
        Book book2 = new Book("The man in the high castle", "Philip K. Dick");
        Book book3 = new Book("Norwegian wood", "Haruki Murakami");
        bookService.storeBook(book1);
        bookService.storeBook(book2);
        bookService.storeBook(book3);
        LOGGER.info("Created 3 books in the application");
    }

}