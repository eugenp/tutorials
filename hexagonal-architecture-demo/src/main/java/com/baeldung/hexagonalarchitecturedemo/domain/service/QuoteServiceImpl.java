package com.baeldung.hexagonalarchitecturedemo.domain.service;

import com.baeldung.hexagonalarchitecturedemo.domain.model.Quote;
import com.baeldung.hexagonalarchitecturedemo.port.inbound.IQuoteService;
import com.baeldung.hexagonalarchitecturedemo.port.outbound.IQuoteRepository;
import org.springframework.stereotype.Service;

@Service
public class QuoteServiceImpl implements IQuoteService {

    private final IQuoteRepository quoteRepository;

    public QuoteServiceImpl(IQuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    @Override
    public Quote getRandomQuote() {
        return quoteRepository.getRandomQuote();
    }
}
