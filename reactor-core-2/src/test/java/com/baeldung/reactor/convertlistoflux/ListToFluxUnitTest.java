package com.baeldung.reactor.convertlistoflux;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class ListToFluxUnitTest {

    @Test
    public void givenList_whenCallingFromIterableOperator_thenListItemsTransformedAsFluxAndEmitted(){

        List<Integer> list = integerList();
        Flux<Integer> flux = listToFluxUsingFromIterableOperator(list);

        StepVerifier.create(flux)
            .expectNext(1)
            .expectNext(2)
            .expectNext(3)
            .expectComplete()
            .verify();
    }


    @Test
    public void givenListWrappedInsideCompletableFuture_whenCallingFlatMapIterableOperator_thenListItemsTransformedAsFluxAndEmitted(){

        CompletableFuture<List<Integer>> completableFuture = listToCompletableFutureWrappedList(integerList());
        Mono<List<Integer>> monoList = completableFutureWrappedListToMonoListUsingFromFutureOperator(completableFuture);
        Flux<Integer> flux = monoListToFluxUsingFlatMapIterableOperator(monoList);

        StepVerifier.create(flux)
            .expectNext(1)
            .expectNext(2)
            .expectNext(3)
            .expectComplete()
            .verify();
    }

    @Test
    public void givenList_whenCallingCreateOperator_thenListItemsTransformedAsFluxAndEmitted(){

        Supplier<List<Integer>> supplier = this::integerList;
        Flux<Integer> flux = listToFluxUsingCreateOperator(supplier);

        StepVerifier.create(flux)
            .expectNext(1)
            .expectNext(2)
            .expectNext(3)
            .expectComplete()
            .verify();
    }

    private List<Integer> integerList(){
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        return list;
    }

    private <T> Flux<T> listToFluxUsingFromIterableOperator(List<T> list) {
        return Flux
            .fromIterable(list)
            .log();
    }

    private <T> Flux<T> monoListToFluxUsingFlatMapIterableOperator(Mono<List<T>> monoList) {
        return monoList.flatMapIterable(l-> l).log();
    }

    private <T> Flux<T> listToFluxUsingCreateOperator(Supplier<List<T>> supplier) {
        return Flux.create(sink-> {
            supplier.get().forEach(sink::next);
            sink.complete();
        });
    }

    private <T> Mono<List<T>> completableFutureWrappedListToMonoListUsingFromFutureOperator(CompletableFuture<List<T>> completableFuture) {
        return Mono.fromFuture(completableFuture);
    }

    private <T> CompletableFuture<List<T>> listToCompletableFutureWrappedList(List<T> list) {
        return CompletableFuture.supplyAsync(()-> list);
    }

}
