package com.baeldung.hexagonalarchitecturedemo.adapter;

import com.baeldung.hexagonalarchitecturedemo.domain.model.Quote;
import com.baeldung.hexagonalarchitecturedemo.port.inbound.IQuoteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quote")
public class QuoteController {

    private final IQuoteService quoteService;

    public QuoteController(IQuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping("/random")
    public Quote getRandomQuote() {
        return quoteService.getRandomQuote();
    }
}
