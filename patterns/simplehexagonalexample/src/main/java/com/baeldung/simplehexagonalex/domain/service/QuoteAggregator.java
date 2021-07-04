package com.baeldung.simplehexagonalex.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.simplehexagonalex.domain.QuoteOfTheDay;
import com.baeldung.simplehexagonalex.domain.repository.QuoteOfTheDayFromProvider;

@Service
public class QuoteAggregator implements QuoteService {

    @Autowired
    private QuoteOfTheDayFromProvider quoteOfTheDayFromProvider;

    @Override
    public QuoteOfTheDay getQuote(String userName) {

        QuoteOfTheDay quoteOfTheDay = quoteOfTheDayFromProvider.getQuote();
        quoteOfTheDay.setUserName(userName);
        return quoteOfTheDay;
    }
}
