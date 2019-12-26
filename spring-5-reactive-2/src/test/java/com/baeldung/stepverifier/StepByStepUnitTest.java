package com.baeldung.stepverifier;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class StepByStepUnitTest {

    Flux<String> source = Flux.just("John", "Monica", "Mark", "Cloe", "Frank", "Casper", "Olivia", "Emily", "Cate")
      .filter(name -> name.length() == 4)
      .map(String::toUpperCase);

    @Test
    public void shouldReturnForLettersUpperCaseStrings() {
        StepVerifier
          .create(source)
          .expectNext("JOHN")
          .expectNextMatches(name -> name.startsWith("MA"))
          .expectNext("CLOE", "CATE")
          .expectComplete()
          .verify();
    }

    @Test
    public void shouldThrowExceptionAfterFourElements() {
        Flux<String> error = source.concatWith(
          Mono.error(new IllegalArgumentException("Our message"))
        );

        StepVerifier
          .create(error)
          .expectNextCount(4)
          .expectErrorMatches(throwable -> throwable instanceof IllegalArgumentException &&
                  throwable.getMessage().equals("Our message")
          ).verify();
    }

}
