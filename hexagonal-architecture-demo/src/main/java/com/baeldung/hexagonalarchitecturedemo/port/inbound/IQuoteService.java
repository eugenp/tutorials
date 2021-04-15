package com.baeldung.hexagonalarchitecturedemo.port.inbound;

import com.baeldung.hexagonalarchitecturedemo.domain.model.Quote;

public interface IQuoteService {

    public Quote getRandomQuote();
}
