package com.baeldung.architecture.hexagonal.repositories.mondodb;

import java.util.List;

import com.baeldung.architecture.hexagonal.domain.models.Quote;
import com.baeldung.architecture.hexagonal.domain.repositories.QuoteRepository;
import org.springframework.stereotype.Component;

//@Primary
@Component
public class MongoDbQuoteRepository implements QuoteRepository {

    private SpringDataMongoQuoteRepository springDataMongoQuoteRepository;

    public MongoDbQuoteRepository(SpringDataMongoQuoteRepository springDataMongoQuoteRepository) {
        this.springDataMongoQuoteRepository = springDataMongoQuoteRepository;
    }

    @Override
    public List<Quote> getAllQuotes() {
        return this.springDataMongoQuoteRepository.findAll();
    }

}
