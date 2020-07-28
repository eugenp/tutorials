package com.baeldung.architecture.hexagonal.domain.repositories;

import java.util.List;

import com.baeldung.architecture.hexagonal.domain.models.Quote;

public interface QuoteRepository {

    public List<Quote> getAllQuotes();

}
