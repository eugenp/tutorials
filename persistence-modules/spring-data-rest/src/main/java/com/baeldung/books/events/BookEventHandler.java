package com.baeldung.books.events;

import java.util.logging.Logger;

import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import com.baeldung.books.models.Author;
import com.baeldung.books.models.Book;

@RepositoryEventHandler
public class BookEventHandler {
    Logger logger = Logger.getLogger("Class BookEventHandler");

    @HandleBeforeCreate
    public void handleBookBeforeCreate(Book book) {

        logger.info("Inside Book Before Create ....");
        book.getAuthors();
    }

    @HandleBeforeCreate
    public void handleAuthorBeforeCreate(Author author) {
        logger.info("Inside Author Before Create ....");
        author.getBooks();
    }
}
