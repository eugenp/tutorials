package com.baeldung.simplehexagonalex.repository.primaryQuoteProvider;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import com.baeldung.simplehexagonalex.domain.QuoteOfTheDay;
import com.baeldung.simplehexagonalex.domain.repository.QuoteOfTheDayFromProvider;

@SpringBootTest
public class PrimaryAccessProviderIntegrationTest {

    @Autowired
    @Qualifier("providerQuoteAdapter")
    QuoteOfTheDayFromProvider provider;

    @Test
    public void whenQuoteProvider_thenResponse() throws Exception {

        QuoteOfTheDay quote = provider.getQuote();
        assertNotNull(quote);
        assertThat(quote.getProvider()).contains("theysaidso");
    }
}
