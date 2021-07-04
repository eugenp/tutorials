package com.baeldung.simplehexagonalex.repository.primaryQuoteProvider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.baeldung.simplehexagonalex.domain.QuoteOfTheDay;
import com.baeldung.simplehexagonalex.reposity.mock.quoteadapter.MockQuoteAdapter;

public class MockAccessProviderUnitTest {

    @Test
    public void givenProvider_whenConnect_thenResponse() throws Exception {

        MockQuoteAdapter provider = new MockQuoteAdapter();
        QuoteOfTheDay quote = provider.getQuote();
        assertNotNull(quote);
        assertEquals("Mock quote of the day", quote.getQuote());
        assertEquals("Mock Provider", quote.getProvider());
    }
}
