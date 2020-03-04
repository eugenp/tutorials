package com.baeldung.ddd.introduction.inside;

import java.util.List;

import com.baeldung.ddd.introduction.inside.model.Quote;

public interface QuoteService {
	void create(Quote quote);

	List<Quote> getQuotes();
}
