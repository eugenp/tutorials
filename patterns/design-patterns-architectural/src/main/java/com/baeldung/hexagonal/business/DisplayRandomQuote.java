package com.baeldung.hexagonal.business;

import java.util.function.Supplier;

public class DisplayRandomQuote implements Supplier<Object> {
    private QuoteRepository quoteRepository;

    public DisplayRandomQuote(QuoteRepository qr) {
        quoteRepository = qr;
    }

    @Override
    public Object get() {
        return quoteRepository.getQuote();
    }

    
}
