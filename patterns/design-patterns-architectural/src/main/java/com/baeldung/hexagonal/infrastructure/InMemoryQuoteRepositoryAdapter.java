package com.baeldung.hexagonal.infrastructure;

import com.baeldung.hexagonal.business.QuoteRepository;

public class InMemoryQuoteRepositoryAdapter implements QuoteRepository {

    final String[] quotes = { "The secret of getting ahead is getting started", 
            "If you tell the truth, you don't have to remember anything. ", 
            "Travel is fatal to prejudice, bigotry, and narrow-mindedness. ",
            "Kindness is the language which the deaf can hear and the blind can see. ", 
            "Whenever you find yourself on the side of the majority, it is time to pause and reflect. ", 
            "Lies, damned lies, and statistics ",
            "The difference between the right word and the almost right word is the difference between lightning and a lightning bug. ", 
            "The two most important days in your life are the day you are born and the day you find out why. ",
            "Truth is stranger than fiction, but it is because Fiction is obliged to stick to possibilities; Truth isn't. ",
            "Keep away from people who try to belittle your ambitions. Small people always do that, but the really great make you feel that you, too, can become great." };

    public String getQuote() {
        return quotes[(int) (Math.floor((Math.random() * quotes.length)))];
    }

}
