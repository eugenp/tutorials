package com.baeldung.ddd.introduction.outside.repos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.baeldung.ddd.introduction.inside.QuoteRepo;
import com.baeldung.ddd.introduction.inside.model.Quote;

@Repository
public class QuoteRepoImpl implements QuoteRepo {
	private Map<String, Quote> quotes = new HashMap<String, Quote>();

	@Override
	public void create(Quote quote) {
		quotes.put(quote.getName(), quote);
	}

	public List<Quote> getQuotes() {
		return new ArrayList<>(quotes.values());
	}
}
