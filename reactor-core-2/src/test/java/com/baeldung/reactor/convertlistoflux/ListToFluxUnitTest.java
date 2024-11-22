package com.baeldung.reactor.convertlistoflux;

import java.util.List;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class ListToFluxUnitTest {

    @Test
    public void givenList_whenCallingFromIterableOperator_thenListItemsTransformedAsFluxAndEmitted() {

        List<Integer> list = List.of(1, 2, 3);
        Flux<Integer> flux = listToFluxUsingFromIterableOperator(list);

        StepVerifier.create(flux)
            .expectNext(1)
            .expectNext(2)
            .expectNext(3)
            .expectComplete()
            .verify();
    }

    @Test
    public void givenList_whenCallingCreateOperator_thenListItemsTransformedAsFluxAndEmitted() {

        Flux<Integer> flux = Flux.create(sink -> {
            Callback<List<Integer>> callback = list -> {
                list.forEach(sink::next);
                sink.complete();
            };
            asynchronousApiCall(callback);
        });

        StepVerifier.create(flux)
            .expectNext(1)
            .expectNext(2)
            .expectNext(3)
            .expectComplete()
            .verify();
    }

    private <T> Flux<T> listToFluxUsingFromIterableOperator(List<T> list) {
        return Flux.fromIterable(list)
            .log();
    }

    private void asynchronousApiCall(Callback<List<Integer>> callback) {
        Thread thread = new Thread(() -> {
            List<Integer> listGeneratedAsync = List.of(1, 2, 3);
            callback.onTrigger(listGeneratedAsync);
        });
        thread.start();
    }

}
