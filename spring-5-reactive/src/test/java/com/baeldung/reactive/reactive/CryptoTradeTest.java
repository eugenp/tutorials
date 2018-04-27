package com.baeldung.reactive.reactive;


import com.baeldung.reactive.Spring5ReactiveApplication;
import com.baeldung.reactive.model.CryptoCurrency;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = Spring5ReactiveApplication.class)
public class CryptoTradeTest {

    @Test
    public void testCryptoTrade() throws InterruptedException {

        Flux<CryptoCurrency> stream = WebClient.create("http://localhost:8080")
                .get().uri("/trade")
                .retrieve()
                .bodyToFlux(CryptoCurrency.class);
        stream.subscribe(sse ->
                Assert.assertTrue(
                        sse.getPrice().compareTo(BigDecimal.ZERO) > 0
                                && sse.getName().matches("BTC|BCC|ETH|ICX")
                )
        );

    }
}
