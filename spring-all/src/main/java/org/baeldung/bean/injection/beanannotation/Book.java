package org.baeldung.bean.injection.beanannotation;

import org.baeldung.bean.injection.Author;
import org.baeldung.bean.injection.Publisher;

public class Book {
    private Author author;

    private Publisher publisher;
    
    public Book(Author author, Publisher publisher) {
        this.author = author;
        this.publisher = publisher;
    }
}
