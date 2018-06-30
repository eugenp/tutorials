package com.baeldung;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QuoteControllerTest {

	@Autowired
    QuoteController controller;

    @Test
    public void whenSubscribe_thenDeliverTheQuote() {
        controller.subscribe("AAPL")
          .next()
          .doOnNext(quote -> assertThat(quote.getSymbol()).isNotNull()
            .isNotEmpty()
            .isEqualTo("AAPL"))
          .doOnNext(quote -> assertThat(quote.getPrice()).isNotNull()
            .isGreaterThan(0.0));
    }
}
