package org.baeldung.bean.injection.setterbased;

import org.baeldung.bean.injection.Author;
import org.baeldung.bean.injection.Publisher;
import org.springframework.beans.factory.annotation.Autowired;

public class Book {
    private Author author;

    private Publisher publisher;

    @Autowired
    public void setAuthor(Author author) {
        this.author = author;
    }

    @Autowired
    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Author getAuthor() {
        return author;
    }

    public Publisher getPublisher() {
        return publisher;
    }
}
