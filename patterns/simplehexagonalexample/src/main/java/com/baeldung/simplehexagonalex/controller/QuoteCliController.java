package com.baeldung.simplehexagonalex.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.simplehexagonalex.domain.QuoteOfTheDay;
import com.baeldung.simplehexagonalex.domain.service.QuoteService;

@Component
public class QuoteCliController {

    private static final Logger LOG = LoggerFactory.getLogger(QuoteCliController.class);

    @Autowired
    private QuoteService quoteService;

    public QuoteOfTheDay getQuote(String userId) {

        LOG.info("Getting quote");
        return quoteService.getQuote(userId);
    }
}
