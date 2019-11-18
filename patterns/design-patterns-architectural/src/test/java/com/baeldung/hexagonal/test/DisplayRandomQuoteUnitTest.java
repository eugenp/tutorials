package com.baeldung.hexagonal.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.baeldung.hexagonal.business.DisplayRandomQuote;
import com.baeldung.hexagonal.business.QuoteRepository;
import com.baeldung.hexagonal.infrastructure.InMemoryQuoteRepositoryAdapter;

public class DisplayRandomQuoteUnitTest {

    private static QuoteRepository quoteRepository = new InMemoryQuoteRepositoryAdapter();

    @Test
    public void givenQuoteRepository_whenCalledget_thenOneAssertion() {
        DisplayRandomQuote randomQuote = new DisplayRandomQuote(quoteRepository);
        
        assertThat(randomQuote.get()).isInstanceOf(String.class);
    }
}
