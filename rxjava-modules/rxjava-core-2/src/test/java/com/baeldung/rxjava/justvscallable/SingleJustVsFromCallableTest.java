package com.baeldung.rxjava.justvscallable;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import rx.Single;
import rx.observers.TestSubscriber;

class SingleJustVsFromCallableUnitTest {

    public EmployeeRepository repository = mock(EmployeeRepository.class);

    @BeforeEach
    public void beforeEach() {
        reset(repository);
    }

    @Test
    void givenNoSubscriber_whenUsingJust_thenDataIsFetched() {
        Mockito.when(repository.findById(123L))
            .thenReturn("John Doe");

        Single<String> employee = Single.just(repository.findById(123L));

        Mockito.verify(repository, times(1))
            .findById(123L);
    }

    @Test
    void givenASubscriber_whenUsingJust_thenReturnTheCorrectValue() {
        TestSubscriber<String> testSubscriber = new TestSubscriber<>();
        Mockito.when(repository.findById(123L))
            .thenReturn("John Doe");

        Single<String> employee = Single.just(repository.findById(123L));
        employee.subscribe(testSubscriber);

        testSubscriber.assertValue("John Doe");
        testSubscriber.assertCompleted();
    }

    @Test
    void givenNoSubscriber_whenUsingFromCallable_thenNoDataIsFetched() {
        Single<String> employee = Single.fromCallable(() -> repository.findById(123L));

        Mockito.verify(repository, never())
            .findById(123L);
    }

    @Test
    void givenASubscriber_whenUsingFromCallable_thenReturnCorrectValue() {
        TestSubscriber<String> testSubscriber = new TestSubscriber<>();
        Mockito.when(repository.findById(123L))
            .thenReturn("John Doe");

        Single<String> employee = Single.fromCallable(() -> repository.findById(123L));
        employee.subscribe(testSubscriber);

        Mockito.verify(repository, times(1))
            .findById(123L);
        testSubscriber.assertCompleted();
        testSubscriber.assertValue("John Doe");
    }
}