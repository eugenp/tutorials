package com.baeldung.patterns.hexagonal_quick;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.baeldung.patterns.hexagonal_quick.persistence.InMemoryBookRepositoryAdapter;
import com.baeldung.patterns.hexagonal_quick.persistence.SpringDataBookRepositoryAdapter;
import com.baeldung.patterns.hexagonal_quick.port.BookOutputPort;

@Configuration
@EnableMongoRepositories(considerNestedRepositories = true)
public class HexArchQuickConfiguration {

//    @Bean
//    public BookOutputPort bookOutputPort() {
//        return new InMemoryBookRepositoryAdapter(new HashMap<>());
//    }

    @Bean
    public BookOutputPort bookOutputPort(
            @Autowired SpringDataBookRepositoryAdapter.MongoBookRepository bookRepository) {
        return new SpringDataBookRepositoryAdapter(bookRepository);
    }
}
