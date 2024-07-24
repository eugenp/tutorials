package com.baeldung.reactor.convertlistoflux;

import java.util.List;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class ListToFluxUnitTest {

    @Test
    public void givenList_whenCallingFromIterableOperator_thenListItemsTransformedAsFluxAndEmitted(){

        List<Integer> list = List.of(1, 2,3);
        Flux<Integer> flux = listToFluxUsingFromIterableOperator(list);

        StepVerifier.create(flux)
            .expectNext(1)
            .expectNext(2)
            .expectNext(3)
            .expectComplete()
            .verify();
    }

    @Test
    public void givenList_whenCallingCreateOperator_thenListItemsTransformedAsFluxAndEmitted(){

        List<Integer> list = List.of(1, 2,3);
        Flux<Integer> flux = listToFluxUsingCreateOperator(list);

        StepVerifier.create(flux)
            .expectNext(1)
            .expectNext(2)
            .expectNext(3)
            .expectComplete()
            .verify();
    }

    private <T> Flux<T> listToFluxUsingFromIterableOperator(List<T> list) {
        return Flux
            .fromIterable(list)
            .log();
    }

    private <T> Flux<T> listToFluxUsingCreateOperator(List<T> list) {
        return Flux.create(sink-> {
            list.forEach(sink::next);
            sink.complete();
        });
    }

}
