package com.baeldung.sbe;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

import com.baeldung.sbe.stub.Currency;
import com.baeldung.sbe.stub.Market;

public class MarketDataSource implements Iterator<MarketData> {

    private final LinkedList<MarketData> dataQueue = new LinkedList<>();

    public MarketDataSource() {
        // adding some test data into queue
        this.dataQueue.addAll(Arrays.asList(MarketData.builder()
          .amount(1)
          .market(Market.NASDAQ)
          .symbol("AAPL")
          .price(134.12)
          .currency(Currency.USD)
          .build(), MarketData.builder()
          .amount(2)
          .market(Market.NYSE)
          .symbol("IBM")
          .price(128.99)
          .currency(Currency.USD)
          .build(), MarketData.builder()
          .amount(1)
          .market(Market.NASDAQ)
          .symbol("AXP")
          .price(34.87)
          .currency(Currency.EUR)
          .build()));
    }

    @Override
    public boolean hasNext() {
        return !this.dataQueue.isEmpty();
    }

    @Override
    public MarketData next() {
        final MarketData data = this.dataQueue.pop();
        this.dataQueue.add(data);
        return data;
    }
}
