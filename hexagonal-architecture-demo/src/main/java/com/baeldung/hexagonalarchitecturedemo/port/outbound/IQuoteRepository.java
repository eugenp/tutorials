package com.baeldung.hexagonalarchitecturedemo.port.outbound;

import com.baeldung.hexagonalarchitecturedemo.domain.model.Quote;

public interface IQuoteRepository {

    public Quote getRandomQuote();

    public void saveQuote(Quote quote);
}
