package com.baeldung.ddd.introduction.repos;

import java.util.List;

import com.baeldung.ddd.introduction.model.Quote;

public interface QuoteRepo {
	void create(Quote quote);

	List<Quote> getQuotes();
}
