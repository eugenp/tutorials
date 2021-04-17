package com.baeldung.spring.retry;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/stocks/data")
public class StockController {

    private final ExternalConnector externalConnector;

    @GetMapping("/{stockId}")
    public Mono<String> getData(@PathVariable String stockId) {
        return externalConnector.getData(stockId);
    }

}
