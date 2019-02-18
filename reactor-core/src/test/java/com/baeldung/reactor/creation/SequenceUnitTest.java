package com.baeldung.reactor.creation;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SequenceUnitTest {
    @Test
    public void whenGeneratingNumbersWithTuplesState_thenFibonacciSequenceIsProduced() {
        SequenceGenerator sequenceGenerator = new SequenceGenerator();

        StepVerifier.create(sequenceGenerator.generateFibonacciSequence(10))
                .expectNext(0, 1, 1, 2, 3, 5, 8)
                .expectComplete()
                .verify();
    }

    @Test
    public void whenGeneratingNumbersWithAtomicIntegerState_thenIntegerSequenceIsProduced() {
        SequenceGenerator sequenceGenerator = new SequenceGenerator();

        StepVerifier.create(sequenceGenerator.generateNumbersInAscendingOrder(5))
                .expectNext(0, 1, 2, 3, 4, 5)
                .expectComplete()
                .verify();
        assertThat(sequenceGenerator.finalState).isEqualTo(0);
    }

    @Test
    public void whenCreatingItemsWithTheCreateMethod_thenSequenceIsProducedAsynchronously() throws InterruptedException {
        SequenceCreator sequenceCreator = new SequenceCreator();
        List<String> list = new ArrayList<>();
        sequenceCreator.createStringSequence().subscribe(list::add);

        Thread producingThread1 = new Thread(
                () -> sequenceCreator.consumer.accept(Arrays.asList("baeldung"))
        );
        Thread producingThread2 = new Thread(
                () -> sequenceCreator.consumer.accept(Arrays.asList(".", "com"))
        );

        producingThread1.start();
        producingThread2.start();
        producingThread1.join();
        producingThread2.join();

        assertThat(list).containsExactlyInAnyOrder("baeldung", ".", "com");
    }

    @Test
    public void whenHandlingNumbersWithTheHandleMethod_thenSequenceIsMappedAndFiltered() {
        SequenceHandler sequenceHandler = new SequenceHandler();
        Flux<Integer> sequence = Flux.just(2, -1, 4, -3, 0, 6);

        StepVerifier.create(sequenceHandler.handleIntegerSequence(sequence))
                .expectNext(4, 8)
                .expectComplete()
                .verify();
    }
}
