package com.baeldung.events;

import com.baeldung.models.Author;
import com.baeldung.models.Book;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

@RepositoryEventHandler
public class AuthorEventHandler {

        @HandleAfterDelete
        public void handleAuthorAfterDelete(Author author){
                System.out.println("Inside  Author After Delete ....");
        }

        @HandleBeforeCreate
        public void handleAuthorBeforeCreate(Author author){
                System.out.println("Inside  Author Before Create....");
        }

}
