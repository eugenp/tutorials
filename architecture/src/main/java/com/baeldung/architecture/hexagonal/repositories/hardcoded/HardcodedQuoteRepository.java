package com.baeldung.architecture.hexagonal.repositories.hardcoded;

import java.util.Arrays;
import java.util.List;

import com.baeldung.architecture.hexagonal.domain.models.Quote;
import com.baeldung.architecture.hexagonal.domain.repositories.QuoteRepository;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class HardcodedQuoteRepository implements QuoteRepository {

    private List<Quote> quotes;

    public HardcodedQuoteRepository() {
        this.quotes = Arrays.asList(
            new Quote(ObjectId.get(), "To be or not to be, that is the question", "William Shakespeare"),
            new Quote(ObjectId.get(), "Learning never exhausts the mind", "Leonardo da Vinci"),
            new Quote(ObjectId.get(), "Simplicity is the ultimate sophistication", "Leonardo da Vinci"),
            new Quote(ObjectId.get(), "Nothing is certain except for death and taxes", "Benjamin Franklin"),
            new Quote(ObjectId.get(), "Life is what happens when you're busy making other plans", "John Lennon"));
    }

    @Override
    public List<Quote> getAllQuotes() {
        return this.quotes;
    }

}
