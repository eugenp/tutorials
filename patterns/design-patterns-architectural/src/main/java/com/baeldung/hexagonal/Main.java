package com.baeldung.hexagonal;

import com.baeldung.hexagonal.application.WriteQuotetoConsoleAdapter;
import com.baeldung.hexagonal.infrastructure.InMemoryQuoteRepositoryAdapter;

public class Main {

    public static void main(String[] args) {

        InMemoryQuoteRepositoryAdapter quotes = new InMemoryQuoteRepositoryAdapter();
        
        WriteQuotetoConsoleAdapter showQuotes = new WriteQuotetoConsoleAdapter(quotes);
    
        showQuotes.writeQuote();
    }

}
