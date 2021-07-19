package com.baeldung.rxjava;

import org.junit.Test;
import rx.Observable;
import rx.Observable.Operator;
import rx.Observable.Transformer;
import rx.Subscriber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.baeldung.rxjava.operator.ToCleanString.toCleanString;
import static com.baeldung.rxjava.operator.ToLength.toLength;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class RxJavaCustomOperatorUnitTest {

    @Test
    public void whenUseCleanStringOperator_thenSuccess() {
        final List<String> list = Arrays.asList("john_1", "tom-3");
        final List<String> results = new ArrayList<>();

        final Observable<String> observable = Observable.from(list)
          .lift(toCleanString());

        // when
        observable.subscribe(results::add);

        // then
        assertThat(results, notNullValue());
        assertThat(results, hasSize(2));
        assertThat(results, hasItems("john1", "tom3"));
    }

    @Test
    public void whenUseToLengthOperator_thenSuccess() {
        final List<String> list = Arrays.asList("john", "tom");
        final List<Integer> results = new ArrayList<>();

        final Observable<Integer> observable = Observable.from(list)
          .compose(toLength());

        // when
        observable.subscribe(results::add);

        // then
        assertThat(results, notNullValue());
        assertThat(results, hasSize(2));
        assertThat(results, hasItems(4, 3));
    }

    @Test
    public void whenUseFunctionOperator_thenSuccess() {
        final Operator<String, String> cleanStringFn = subscriber -> new Subscriber<String>(subscriber) {
            @Override
            public void onCompleted() {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }

            @Override
            public void onError(Throwable t) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onError(t);
                }
            }

            @Override
            public void onNext(String str) {
                if (!subscriber.isUnsubscribed()) {
                    final String result = str.replaceAll("[^A-Za-z0-9]", "");
                    subscriber.onNext(result);
                }
            }
        };

        final List<String> results = new ArrayList<>();
        Observable.from(Arrays.asList("ap_p-l@e", "or-an?ge"))
          .lift(cleanStringFn)
          .subscribe(results::add);

        assertThat(results, notNullValue());
        assertThat(results, hasSize(2));
        assertThat(results, hasItems("apple", "orange"));
    }

    @Test
    public void whenUseFunctionTransformer_thenSuccess() {
        final Transformer<String, Integer> toLengthFn = source -> source.map(String::length);

        final List<Integer> results = new ArrayList<>();
        Observable.from(Arrays.asList("apple", "orange"))
          .compose(toLengthFn)
          .subscribe(results::add);

        assertThat(results, notNullValue());
        assertThat(results, hasSize(2));
        assertThat(results, hasItems(5, 6));
    }
}
