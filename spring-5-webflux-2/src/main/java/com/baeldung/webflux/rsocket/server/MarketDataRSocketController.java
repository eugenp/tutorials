package com.baeldung.webflux.rsocket.server;

import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.baeldung.webflux.rsocket.model.MarketData;
import com.baeldung.webflux.rsocket.model.MarketDataRequest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class MarketDataRSocketController {

    private final MarketDataRepository marketDataRepository;

    public MarketDataRSocketController(MarketDataRepository marketDataRepository) {
        this.marketDataRepository = marketDataRepository;
    }

    @MessageMapping("currentMarketData")
    public Mono<MarketData> currentMarketData(MarketDataRequest marketDataRequest) {
        return marketDataRepository.getOne(marketDataRequest.getStock());
    }

    @MessageMapping("feedMarketData")
    public Flux<MarketData> feedMarketData(MarketDataRequest marketDataRequest) {
        return marketDataRepository.getAll(marketDataRequest.getStock());
    }

    @MessageMapping("collectMarketData")
    public Mono<Void> collectMarketData(MarketData marketData) {
        marketDataRepository.add(marketData);
        return Mono.empty();
    }

    @MessageExceptionHandler
    public Mono<MarketData> handleException(Exception e) {
        return Mono.just(MarketData.fromException(e));
    }
}
