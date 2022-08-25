package com.baeldung.streams.multiplefilters;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;

public class MultipleFiltersVsComplexConditionFilterOrderUnitTest {

    AtomicInteger numberOfOperations = new AtomicInteger();

    @Before
    public void beforeEach() {
        numberOfOperations.set(0);
    }

    @Test
    public void givenWrongFilterOrder_whenUsingMultipleFilters_shouldEvaluateManyConditions() {
        long filteredStreamSize = IntStream.range(0, 100)
            .boxed()
            .filter(this::isEvenNumber)
            .filter(this::isSmallerThanTwenty)
            .count();

        assertThat(filteredStreamSize).isEqualTo(10);
        assertThat(numberOfOperations).hasValue(150);
    }

    @Test
    public void givenWrongFilterOrder_whenUsingSingleFilters_shouldEvaluateManyConditions() {
        long filteredStreamSize = IntStream.range(0, 100)
            .boxed()
            .filter(i -> isEvenNumber(i) && isSmallerThanTwenty(i))
            .count();

        assertThat(filteredStreamSize).isEqualTo(10);
        assertThat(numberOfOperations).hasValue(150);
    }

    @Test
    public void givenCorrectFilterOrder_whenUsingMultipleFilters_shouldEvaluateFewerConditions() {
        long filteredStreamSize = IntStream.range(0, 100)
            .boxed()
            .filter(this::isSmallerThanTwenty)
            .filter(this::isEvenNumber)
            .count();

        assertThat(filteredStreamSize).isEqualTo(10);
        assertThat(numberOfOperations).hasValue(120);
    }

    @Test
    public void givenCorrectFilterOrder_whenUsingHavingASlowCondition_shouldEvaluateFewerConditions() {
        long filteredStreamSize = IntStream.range(0, 100)
            .boxed()
            .filter(this::isSmallerThanTwenty)
            .filter(this::isEvenNumber)
            .filter(this::verySlowFilter)
            .count();

        assertThat(filteredStreamSize).isEqualTo(10);
        assertThat(numberOfOperations).hasValue(130);
    }

    private boolean isEvenNumber(int i) {
        numberOfOperations.incrementAndGet();
        return i % 2 == 0;
    }

    private boolean isSmallerThanTwenty(int i) {
        numberOfOperations.incrementAndGet();
        return i < 20;
    }

    private boolean verySlowFilter(int i) {
        numberOfOperations.incrementAndGet();
        //        commented the Thread.sleep() not to slow down the pipeline.
        //        try {
        //            Thread.sleep(1000);
        //        } catch (InterruptedException e) {
        //            throw new RuntimeException(e);
        //        }
        return true;
    }

}