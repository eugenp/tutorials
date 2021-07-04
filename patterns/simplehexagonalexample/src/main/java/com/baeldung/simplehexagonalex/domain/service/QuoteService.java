package com.baeldung.simplehexagonalex.domain.service;

import com.baeldung.simplehexagonalex.domain.QuoteOfTheDay;

public interface QuoteService {

    QuoteOfTheDay getQuote(String userName);
}
