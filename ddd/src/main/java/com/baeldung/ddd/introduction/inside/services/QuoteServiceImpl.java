package com.baeldung.ddd.introduction.inside.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baeldung.ddd.introduction.inside.QuoteRepo;
import com.baeldung.ddd.introduction.inside.QuoteService;
import com.baeldung.ddd.introduction.inside.model.Quote;

@Service
public class QuoteServiceImpl implements QuoteService {
	private QuoteRepo quoteRepo;

	@Override
	public void create(Quote quote) {
		quoteRepo.create(quote);
	}

	public List<Quote> getQuotes() {
		return quoteRepo.getQuotes();
	}
}
