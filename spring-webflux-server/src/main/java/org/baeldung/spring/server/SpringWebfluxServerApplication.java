package org.baeldung.spring.server;

import java.time.Duration;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

@SpringBootApplication
@RestController
public class SpringWebfluxServerApplication {

    private Random random = new Random();

    public static void main(String[] args) {
        SpringApplication.run(SpringWebfluxServerApplication.class, args);
    }

    @GetMapping(value = "/quotes", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Quote> getQuotes() {

        Flux<Quote> quotesFlux = Flux.interval(Duration.ofSeconds(1))
            .map(l -> new Quote("XYZ", 5.0 + random.nextDouble()));

        return quotesFlux;

    }

    static class Quote {

        String symbol;
        double value;

        public Quote() {
        }

        public Quote(String symbol, double value) {
            this.symbol = symbol;
            this.value = value;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

    }
}
