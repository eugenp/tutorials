package com.baeldung.ddd.introduction.services;

import java.util.List;

import com.baeldung.ddd.introduction.model.Quote;

public interface QuoteService {
	void create(Quote quote);

	List<Quote> getQuotes();
}
