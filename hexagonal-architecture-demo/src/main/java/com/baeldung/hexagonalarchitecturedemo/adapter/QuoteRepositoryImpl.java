package com.baeldung.hexagonalarchitecturedemo.adapter;

import com.baeldung.hexagonalarchitecturedemo.domain.model.Quote;
import com.baeldung.hexagonalarchitecturedemo.port.outbound.IQuoteRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class QuoteRepositoryImpl implements IQuoteRepository {

    List<Quote> quoteList = new CopyOnWriteArrayList<>();

    @Override
    public Quote getRandomQuote() {
        Random random = new Random();
        return quoteList.get(random.nextInt(quoteList.size()));
    }

    @Override
    public void saveQuote(Quote quote) {
        quoteList.add(quote);
    }
}
