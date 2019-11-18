package com.baeldung.hexagonal.application;

import com.baeldung.hexagonal.business.DisplayRandomQuote;
import com.baeldung.hexagonal.business.QuoteRepository;
import com.baeldung.hexagonal.business.WriteQuote;

public class WriteQuotetoConsoleAdapter implements WriteQuote {

    private QuoteRepository quoteRepository;
    
    public WriteQuotetoConsoleAdapter(QuoteRepository qr) {
        quoteRepository = qr;
    }
    
    @Override
    public void writeQuote() {
        DisplayRandomQuote randomQuote = new DisplayRandomQuote(quoteRepository);
        System.out.println(randomQuote.get());        
    }

}
