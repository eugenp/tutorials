package com.baeldung.ddd.introduction.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baeldung.ddd.introduction.model.Quote;
import com.baeldung.ddd.introduction.repos.QuoteRepo;

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
