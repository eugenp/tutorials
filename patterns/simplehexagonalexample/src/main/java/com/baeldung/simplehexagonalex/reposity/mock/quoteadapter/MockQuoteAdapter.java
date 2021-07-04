package com.baeldung.simplehexagonalex.reposity.mock.quoteadapter;

import org.springframework.stereotype.Service;

import com.baeldung.simplehexagonalex.domain.QuoteOfTheDay;
import com.baeldung.simplehexagonalex.domain.repository.QuoteOfTheDayFromProvider;

@Service
public class MockQuoteAdapter implements QuoteOfTheDayFromProvider {

    @Override
    public QuoteOfTheDay getQuote() {

        QuoteOfTheDay quoteOfTheDay = new QuoteOfTheDay();
        quoteOfTheDay.setQuote("Mock quote of the day");
        quoteOfTheDay.setProvider("Mock Provider");

        return quoteOfTheDay;
    }
}
