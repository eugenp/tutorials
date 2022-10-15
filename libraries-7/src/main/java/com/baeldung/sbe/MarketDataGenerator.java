package com.baeldung.sbe;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.baeldung.sbe.stub.Currency;
import com.baeldung.sbe.stub.Market;

public class MarketDataGenerator {

    private final List<MarketData> dataList = Arrays.asList(
        MarketData.builder()
            .amount(1)
            .market(Market.NASDAQ)
            .symbol("AAPL")
            .price(134.12)
            .currency(Currency.USD)
            .build(),
        MarketData.builder()
            .amount(2)
            .market(Market.NYSE)
            .symbol("IBM")
            .price(128.99)
            .currency(Currency.USD)
            .build(),
        MarketData.builder()
            .amount(1)
            .market(Market.NASDAQ)
            .symbol("AXP")
            .price(34.87)
            .currency(Currency.EUR)
            .build()
    );

    private final AtomicInteger index = new AtomicInteger(0);

    public MarketData getNext() {
        return dataList.get(index.getAndIncrement() % dataList.size());
    }

}
