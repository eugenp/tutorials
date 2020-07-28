package com.baeldung.architecture.hexagonal.controllers;

import java.util.List;

import com.baeldung.architecture.hexagonal.domain.models.Quote;
import com.baeldung.architecture.hexagonal.domain.services.QuoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConsoleQuoteController {

    private QuoteService quoteService;

    @Autowired
    public ConsoleQuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    public void getRandomQuote() {
        log.info("Retrieve Single Quote");
        Quote quote = this.quoteService.retrieveRandomQuote();
        log.info("Quote: " + quote.toString());
    }

    public void getAllQuotes() {
        log.info("Retrieve All Quotes");
        List<Quote> quotes = this.quoteService.retrieveQuotes();
        quotes.forEach(quote -> log.info(quote.toString()));
    }

}
