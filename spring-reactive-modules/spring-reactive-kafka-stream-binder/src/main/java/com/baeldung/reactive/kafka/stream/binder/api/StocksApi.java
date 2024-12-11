package com.baeldung.reactive.kafka.stream.binder.api;

import com.baeldung.reactive.kafka.stream.binder.domain.StockUpdate;
import com.baeldung.reactive.kafka.stream.binder.repository.ClickHouseRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;

import java.time.Instant;

@RestController
public class StocksApi {

    private final ClickHouseRepository repository;

    @Autowired
    public StocksApi(ClickHouseRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/stock-prices-out")
    public Flux<StockUpdate> getAvgStockPrices(@RequestParam("from") @NotNull Instant from,  @RequestParam("to") @NotNull Instant to) {

        if (from.isAfter(to)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "'from' must come before 'to'");
        }

        return repository.findMinuteAvgStockPrices(from, to);
    }
}