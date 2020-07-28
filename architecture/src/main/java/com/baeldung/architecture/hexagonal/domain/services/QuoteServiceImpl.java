package com.baeldung.architecture.hexagonal.domain.services;

import java.util.List;
import java.util.Random;

import com.baeldung.architecture.hexagonal.domain.models.Quote;
import com.baeldung.architecture.hexagonal.domain.repositories.QuoteRepository;

public class QuoteServiceImpl implements QuoteService {

    private QuoteRepository quoteRepository;

    public QuoteServiceImpl(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    @Override
    public Quote retrieveRandomQuote() {
        List<Quote> quotes = this.quoteRepository.getAllQuotes();
        Random rand = new Random();
        return quotes.get(rand.nextInt(quotes.size()));
    }

    @Override
    public List<Quote> retrieveQuotes() {
        return quoteRepository.getAllQuotes();
    }

}
