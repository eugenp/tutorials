package com.baeldung.events;

import java.util.logging.Logger;
import com.baeldung.models.Author;
import com.baeldung.models.Book;
import org.apache.commons.logging.Log;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

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
