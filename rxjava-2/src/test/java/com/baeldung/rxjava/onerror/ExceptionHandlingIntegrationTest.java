package com.baeldung.rxjava.onerror;

import io.reactivex.Observable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.observers.TestObserver;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertTrue;

public class ExceptionHandlingIntegrationTest {

    private Error UNKNOWN_ERROR = new Error("unknown error");
    private Exception UNKNOWN_EXCEPTION = new Exception("unknown exception");

    @Test
    public void givenSubscriberAndError_whenHandleOnErrorReturn_thenResumed() {
        TestObserver<String> testObserver = new TestObserver<>();

        Observable
          .<String>error(UNKNOWN_ERROR)
          .onErrorReturn(Throwable::getMessage)
          .subscribe(testObserver);

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValueCount(1);
        testObserver.assertValue("unknown error");
    }

    @Test
    public void givenSubscriberAndError_whenHandleOnErrorResume_thenResumed() {
        TestObserver<String> testObserver = new TestObserver<>();

        Observable
          .<String>error(UNKNOWN_ERROR)
          .onErrorResumeNext(Observable.just("one", "two"))
          .subscribe(testObserver);

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValueCount(2);
        testObserver.assertValues("one", "two");
    }

    @Test
    public void givenSubscriberAndError_whenHandleOnErrorResumeItem_thenResumed() {
        TestObserver<String> testObserver = new TestObserver<>();

        Observable
          .<String>error(UNKNOWN_ERROR)
          .onErrorReturnItem("singleValue")
          .subscribe(testObserver);

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValueCount(1);
        testObserver.assertValue("singleValue");
    }

    @Test
    public void givenSubscriberAndError_whenHandleOnErrorResumeFunc_thenResumed() {
        TestObserver<String> testObserver = new TestObserver<>();

        Observable
          .<String>error(UNKNOWN_ERROR)
          .onErrorResumeNext(throwable -> {
              return Observable.just(throwable.getMessage(), "nextValue");
          })
          .subscribe(testObserver);

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValueCount(2);
        testObserver.assertValues("unknown error", "nextValue");
    }

    @Test
    public void givenSubscriberAndError_whenChangeStateOnError_thenErrorThrown() {
        TestObserver<String> testObserver = new TestObserver<>();
        final AtomicBoolean state = new AtomicBoolean(false);

        Observable
          .<String>error(UNKNOWN_ERROR)
          .doOnError(throwable -> state.set(true))
          .subscribe(testObserver);

        testObserver.assertError(UNKNOWN_ERROR);
        testObserver.assertNotComplete();
        testObserver.assertNoValues();
        assertTrue("state should be changed", state.get());
    }

    @Test
    public void givenSubscriberAndError_whenExceptionOccurOnError_thenCompositeExceptionThrown() {
        TestObserver<String> testObserver = new TestObserver<>();

        Observable
          .<String>error(UNKNOWN_ERROR)
          .doOnError(throwable -> {
              throw new RuntimeException("unexcepted");
          })
          .subscribe(testObserver);

        testObserver.assertError(CompositeException.class);
        testObserver.assertNotComplete();
        testObserver.assertNoValues();
    }

    @Test
    public void givenSubscriberAndException_whenHandleOnException_thenResumed() {
        TestObserver<String> testObserver = new TestObserver<>();

        Observable
          .<String>error(UNKNOWN_EXCEPTION)
          .onExceptionResumeNext(Observable.just("exceptionResumed"))
          .subscribe(testObserver);

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValueCount(1);
        testObserver.assertValue("exceptionResumed");
    }

    @Test
    public void givenSubscriberAndError_whenHandleOnException_thenNotResumed() {
        TestObserver<String> testObserver = new TestObserver<>();

        Observable
          .<String>error(UNKNOWN_ERROR)
          .onExceptionResumeNext(Observable.just("exceptionResumed"))
          .subscribe(testObserver);

        testObserver.assertError(UNKNOWN_ERROR);
        testObserver.assertNotComplete();
    }
}
