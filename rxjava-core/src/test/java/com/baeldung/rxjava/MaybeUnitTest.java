package com.baeldung.rxjava;

import org.junit.Test;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

public class MaybeUnitTest {
    @Test
    public void whenEmitsSingleValue_thenItIsObserved() {
        Maybe<Integer> maybe = Flowable.just(1, 2, 3, 4, 5)
            .firstElement();

        maybe.map(x -> x + 7)
            .filter(x -> x > 0)
            .test()
            .assertResult(8)
            .assertComplete();
    }

    @Test
    public void whenEmitsNoValue_thenSignalsCompletionAndNoValueObserved() {
        Maybe<Integer> maybe = Flowable.just(1, 2, 3, 4, 5)
            .skip(5)
            .firstElement();

        maybe.test()
            .assertComplete()
            .assertNoValues();
    }

    @Test
    public void whenThrowsError_thenErrorIsRaised() {
        Maybe<Integer> maybe = Flowable.<Integer> error(new Exception("msg"))
            .firstElement();

        maybe.test()
            .assertErrorMessage("msg");
    }
}
