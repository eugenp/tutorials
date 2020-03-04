package com.baeldung.ddd.introduction.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.ddd.introduction.model.Quote;
import com.baeldung.ddd.introduction.services.QuoteService;

@RestController
@RequestMapping("/quotes")
public class QuoteRestController {
	@Autowired
	private QuoteService quoteService;

	@PostMapping(consumes = APPLICATION_JSON_VALUE)
	public void createQuote(@RequestBody Quote quote) {
		quoteService.create(quote);
	}

	@GetMapping(produces = APPLICATION_JSON_VALUE)
	public List<Quote> getQuotes() {
		return quoteService.getQuotes();
	}
}
