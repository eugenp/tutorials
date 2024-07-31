package com.baeldung.tutorials.openapi.quotes.controller;

import java.time.Clock;
import java.time.OffsetDateTime;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.baeldung.tutorials.openapi.quotes.api.QuotesApi;
import com.baeldung.tutorials.openapi.quotes.api.QuotesApiDelegate;
import com.baeldung.tutorials.openapi.quotes.api.model.QuoteResponse;
import com.baeldung.tutorials.openapi.quotes.service.BrokerService;

@Component
public class QuotesApiImpl implements QuotesApiDelegate {
    private final BrokerService broker;
    private final Clock clock;

    public QuotesApiImpl(BrokerService broker, Clock clock) {
        this.broker = broker;
        this.clock = clock;
    }


    /**
     * GET /quotes/{symbol} : Get current quote for a security
     *
     * @param symbol Security&#39;s symbol (required)
     * @return OK (status code 200)
     * @see QuotesApi#getQuote
     */
    @Override
    public ResponseEntity<QuoteResponse> getQuote(String symbol) {

        var price = broker.getSecurityPrice(symbol);

            var quote = new QuoteResponse();
            quote.setSymbol(symbol);
            quote.setPrice(price);
            quote.setTimestamp(OffsetDateTime.now(clock));
            return ResponseEntity.ok(quote);
    }
}
