package com.baeldung.events;

import com.baeldung.models.Author;
import com.baeldung.models.Book;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

@RepositoryEventHandler
public class BookEventHandler {

        @HandleBeforeCreate
        public void handleBookBeforeCreate(Book book){
                System.out.println("Inside Book Before Create ....");
                book.getAuthors();
        }

        @HandleBeforeCreate
        public void handleAuthorBeforeCreate(Author author){
                System.out.println("Inside Author Before Create ....");
                author.getBooks();
        }
}
