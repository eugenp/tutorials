package com.baeldung.books.events;

import org.springframework.data.rest.core.annotation.*;

import com.baeldung.books.models.Author;
import com.baeldung.books.models.Book;

import java.util.logging.Logger;

@RepositoryEventHandler
public class AuthorEventHandler {
    Logger logger = Logger.getLogger("Class AuthorEventHandler");

    public AuthorEventHandler() {
        super();
    }

    @HandleBeforeCreate
    public void handleAuthorBeforeCreate(Author author) {
        logger.info("Inside  Author Before Create....");
        String name = author.getName();
    }

    @HandleAfterCreate
    public void handleAuthorAfterCreate(Author author) {
        logger.info("Inside  Author After Create ....");
        String name = author.getName();
    }

    @HandleBeforeDelete
    public void handleAuthorBeforeDelete(Author author) {
        logger.info("Inside  Author Before Delete ....");
        String name = author.getName();
    }

    @HandleAfterDelete
    public void handleAuthorAfterDelete(Author author) {
        logger.info("Inside  Author After Delete ....");
        String name = author.getName();
    }

}
