package com.baeldung.architecture.hexagonal.domain.services;

import java.util.List;

import com.baeldung.architecture.hexagonal.domain.models.Quote;

public interface QuoteService {

    public Quote retrieveRandomQuote();

    public List<Quote> retrieveQuotes();

}
