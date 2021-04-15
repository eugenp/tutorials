package com.baeldung.hexagonalarchitecturedemo.domain.service;

import com.baeldung.hexagonalarchitecturedemo.adapter.QuoteRepositoryImpl;
import com.baeldung.hexagonalarchitecturedemo.domain.model.Quote;
import com.baeldung.hexagonalarchitecturedemo.port.outbound.IQuoteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class QuoteServiceImplTest {

    private IQuoteRepository quoteRepository = Mockito.mock(QuoteRepositoryImpl.class);

    private QuoteServiceImpl quoteService = new QuoteServiceImpl(quoteRepository);

    @BeforeEach
    public void setup() {
        Quote quote = new Quote("Stay hungry. Stay Foolish", "Steve Jobs");
        Mockito.when(quoteRepository.getRandomQuote()).thenReturn(quote);
    }

    @Test
    public void whenCallGetRandomQuote_shouldReturnAQuote() {
        Quote quote = quoteService.getRandomQuote();
        Assertions.assertEquals("Steve Jobs", quote.getAuthor());
    }
}
