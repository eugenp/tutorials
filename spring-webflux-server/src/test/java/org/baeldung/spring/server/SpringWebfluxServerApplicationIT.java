package org.baeldung.spring.server;

import org.baeldung.spring.server.SpringWebfluxServerApplication.Quote;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

public class SpringWebfluxServerApplicationIT {

    private static Logger log = LoggerFactory.getLogger(SpringWebfluxServerApplicationIT.class);

    @Test
    public void quoter() {

        WebClient client = WebClient.create("http://localhost:8080");

        Flux<Quote> quotes = client.get()
            .uri("/quotes")
            .retrieve()
            .bodyToFlux(Quote.class);

        int quotesReceived = 0;
        for (Quote quote : quotes.toIterable()) {
            log.info("[I45] quote received: symbol={}, value={}", quote.getSymbol(), quote.getValue());
            quotesReceived++;
            if (quotesReceived == 10) {
                break;
            }
        }
    }

}
