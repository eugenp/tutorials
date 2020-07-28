package com.baeldung.architecture.hexagonal.domain.services;

import java.util.Arrays;
import java.util.List;

import com.baeldung.architecture.hexagonal.domain.models.Quote;
import com.baeldung.architecture.hexagonal.domain.repositories.QuoteRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class QuoteServiceImplUnitTest {

    private QuoteRepository quoteRepository;
    private QuoteService sut;
    private List<Quote> quotes;

    @BeforeEach
    void setUp() {
        quoteRepository = mock(QuoteRepository.class);
        sut = new QuoteServiceImpl(quoteRepository);

        this.quotes = Arrays.asList(new Quote(ObjectId.get(), "To be or not to be, that is the question", "William Shakespeare"), new Quote(ObjectId.get(), "Learning never exhausts the mind", "Leonardo da Vinci"),
            new Quote(ObjectId.get(), "Simplicity is the ultimate sophistication", "Leonardo da Vinci"), new Quote(ObjectId.get(), "Nothing is certain except for death and taxes", "Benjamin Franklin"),
            new Quote(ObjectId.get(), "Life is what happens when you're busy making other plans", "John Lennon"));
        Mockito.when(quoteRepository.getAllQuotes())
            .thenReturn(this.quotes);
    }

    @Test
    void givenQuotes_whenRetrievingAQuote_thenReturnARandomOne() {
        Quote quote = sut.retrieveRandomQuote();

        assertThat(quote).isNotNull();
    }

    @Test
    void givenQuotes_whenRetrievingAListOfQuotes_thenReturnAllQuotes() {
        List<Quote> quotes = sut.retrieveQuotes();

        assertThat(quotes).hasSize(this.quotes.size());
    }

}
