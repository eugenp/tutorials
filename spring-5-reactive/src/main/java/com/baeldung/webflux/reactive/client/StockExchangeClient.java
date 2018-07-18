package com.baeldung.webflux.reactive.client;

import com.baeldung.webflux.reactive.model.StockQuote;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.atomic.AtomicInteger;

public class StockExchangeClient {

    private WebClient stockExchangeClient = WebClient.builder()
            .baseUrl("http://localhost:8090")
            .build();

    public Disposable lastTrade() {
        AtomicInteger counter = new AtomicInteger(0);
        return stockExchangeClient.get()
                .uri("/trading")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .publishOn(Schedulers.single())
                .flatMapMany(response -> response.bodyToFlux(StockQuote.class))
                .subscribe(s -> {
                            System.out.println(counter.incrementAndGet() + " >>>>>>>>>>>> " + s);
                        },
                        err -> System.out.println("Error on StockQuote stream: " + err),
                        () -> System.out.println("StockQuote stream stopped!"));

    }
}
