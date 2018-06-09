package com.baeldung.reactive.controller;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.reactive.template.Stock;

import reactor.core.publisher.Flux;

@RestController
public class StocksController {
    private static Map<String, Stock> stocksDatabase = new ConcurrentHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(StocksController.class);

    static {
        stocksDatabase.put("BCOR", new Stock("BCOR", "Blucora Inc.", new BigDecimal("40.55"), System.currentTimeMillis()));
        stocksDatabase.put("CXDO", new Stock("CXDO", "Crexendo Inc.", new BigDecimal("2.85"), System.currentTimeMillis()));
        stocksDatabase.put("EDXC", new Stock("EDXC", "ENDEXX Corp.", new BigDecimal("0.05"), System.currentTimeMillis()));
        stocksDatabase.put("FB", new Stock("FB", "Facebook Inc. Cl A", new BigDecimal("190.97"), System.currentTimeMillis()));
        stocksDatabase.put("IPAS", new Stock("IPAS", "iPass Inc.", new BigDecimal("0.48"), System.currentTimeMillis()));
        stocksDatabase.put("MOMO", new Stock("MOMO", "Momo Inc.", new BigDecimal("50.62"), System.currentTimeMillis()));
        stocksDatabase.put("SINA", new Stock("SINA", "Sina Corp.", new BigDecimal("93.63"), System.currentTimeMillis()));
        stocksDatabase.put("TMMI", new Stock("TMMI", "TMM Inc.", new BigDecimal("0.04"), System.currentTimeMillis()));
        stocksDatabase.put("TWTR", new Stock("TWTR", "Twitter Inc.", new BigDecimal("40.16"), System.currentTimeMillis()));
        stocksDatabase.put("YOOIF", new Stock("YOOIF", "Yangaroo Inc", new BigDecimal("0.16"), System.currentTimeMillis()));
    }

    @GetMapping(value = "/stocks", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<Collection<Stock>>> liveStockChanges() {
        return Flux.interval(Duration.ofSeconds(1))
            .map(sec -> sec == 0 ? stocksDatabase.values() : getUpdatedStocks())
            .map(stocks -> ServerSentEvent.<Collection<Stock>> builder()
                .event("stock-changed")
                .data(stocks)
                .build());
    }

    private List<Stock> getUpdatedStocks() {
        LinkedList<Stock> updatedStocks = new LinkedList<>();
        stocksDatabase.values()
            .stream()
            .filter(stock -> stock.getLastUpdated() > System.currentTimeMillis() - 1000)
            .forEach(stock -> updatedStocks.add(stock));
        return updatedStocks;
    }

    @PutMapping("/stocks")
    public void updateStocks(@RequestBody List<Stock> stocks) {
        stocks.forEach(stock -> {
            stocksDatabase.put(stock.getSymbol(), stock);
            stock.setLastUpdated(System.currentTimeMillis());
        });
        logger.info("Stocks updated.");
    }
}
