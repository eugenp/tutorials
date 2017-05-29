package org.baeldung.bean.injection.constructorbased;

import org.baeldung.bean.injection.Author;
import org.baeldung.bean.injection.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Book {
    private Author author;

    private Publisher publisher;
    
    @Autowired
    public Book(Author author, Publisher publisher) {
        this.author = author;
        this.publisher = publisher;
    }
    
    // Some logic using author and publisher
}
