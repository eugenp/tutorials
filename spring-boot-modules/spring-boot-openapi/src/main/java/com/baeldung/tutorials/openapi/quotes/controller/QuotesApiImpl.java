package com.baeldung.tutorials.openapi.quotes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.baeldung.tutorials.openapi.quotes.api.QuotesApiDelegate;
import com.baeldung.tutorials.openapi.quotes.api.model.QuoteResponse;
import com.baeldung.tutorials.openapi.quotes.service.BrokerService;

@Component
public class QuotesApiImpl implements QuotesApiDelegate {
    private final BrokerService broker;

    public QuotesApiImpl(BrokerService broker) {
        this.broker = broker;
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

        if ( price.isPresent()) {
            var quote = new QuoteResponse();
            quote.setSymbol(symbol);
            quote.setPrice(price.get());
            return ResponseEntity.ok(quote);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
