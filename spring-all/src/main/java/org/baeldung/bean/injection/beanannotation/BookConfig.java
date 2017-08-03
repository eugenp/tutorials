package org.baeldung.bean.injection.beanannotation;

import org.baeldung.bean.injection.Author;
import org.baeldung.bean.injection.Publisher;
import org.springframework.context.annotation.Bean;

public class BookConfig {
    @Bean
    public Author author() {
        Author author = new Author();
        // some initializing logic for author;

        return author;
    }

    @Bean
    public Publisher publisher() {
        Publisher publisher = new Publisher();
        // some initializing logic for author;
        
        return publisher;
    }

    @Bean
    public Book book() {
        return new Book(author(), publisher());
    }
}
