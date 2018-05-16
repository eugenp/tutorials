package com.baeldung.events;

import com.baeldung.models.Author;
import com.baeldung.models.Book;
import org.springframework.data.rest.core.annotation.*;

@RepositoryEventHandler
public class AuthorEventHandler {
        public AuthorEventHandler(){
                super();
        }

        @HandleBeforeCreate
        public void handleAuthorBeforeCreate(Author author){
                System.out.println("Inside  Author Before Create....");
                String name = author.getName();
        }

        @HandleAfterCreate
        public void handleAuthorAfterCreate(Author author){
                System.out.println("Inside  Author After Create ....");
                String name = author.getName();
        }

        @HandleBeforeDelete
        public void handleAuthorBeforeDelete(Author author){
                System.out.println("Inside  Author Before Delete ....");
                String name = author.getName();
        }

        @HandleAfterDelete
        public void handleAuthorAfterDelete(Author author){
                System.out.println("Inside  Author After Delete ....");
                String name = author.getName();
        }

}
