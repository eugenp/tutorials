package org.baeldung.bean.injection.propertybased;

import org.baeldung.bean.injection.Author;
import org.baeldung.bean.injection.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Book {
    @Autowired
    private Author author;

    @Autowired
    private Publisher publisher;
    
    public Author getAuthor() {
        return author;
    }

    public Publisher getPublisher() {
        return publisher;
    }}
