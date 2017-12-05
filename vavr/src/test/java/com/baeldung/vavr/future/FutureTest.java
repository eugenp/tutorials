package com.baeldung.vavr.future;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import org.junit.Test;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.concurrent.Future;
import io.vavr.control.Option;
import io.vavr.control.Try;

public class FutureTest {

    @Test
    public void whenChangeExecutorService_thenCorrect() {
        String initialValue = "Welcome to ";
        Future<String> resultFuture = Future.of(
          Executors.newSingleThreadExecutor(),
          () -> Util.appendData(initialValue));
        String result = resultFuture.get();

        assertThat(result).isEqualTo("Welcome to Baeldung!");
    }

    @Test
    public void whenAppendData_thenCorrect1() {
        String initialValue = "Welcome to ";
        Future<String> resultFuture = Future.of(() -> Util.appendData(initialValue));
        String result = resultFuture.get();

        assertThat(result).isEqualTo("Welcome to Baeldung!");
    }

    @Test
    public void whenAppendData_thenCorrect2() {
        String initialValue = "Welcome to ";
        Future<String> resultFuture = Future.of(() -> Util.appendData(initialValue));
        resultFuture.await();
        Option<Try<String>> futureOption = resultFuture.getValue();
        Try<String> futureTry = futureOption.get();
        String result = futureTry.get();

        assertThat(result).isEqualTo("Welcome to Baeldung!");
    }

    @Test
    public void whenAppendData_thenSuccess() {
        String initialValue = "Welcome to ";
        Future<String> resultFuture = Future.of(() -> Util.appendData(initialValue))
          .onSuccess(finalResult -> System.out.println("Successfully Completed - Result: " + finalResult))
          .onFailure(finalResult -> System.out.println("Failed - Result: " + finalResult));
        String result = resultFuture.get();

        assertThat(result).isEqualTo("Welcome to Baeldung!");
    }

    @Test
    public void whenChainingCallbacks_thenCorrect() {
        String initialValue = "Welcome to ";
        Future<String> resultFuture = Future.of(() -> Util.appendData(initialValue))
          .andThen(finalResult -> System.out.println("Completed - 1: " + finalResult))
          .andThen(finalResult -> System.out.println("Completed - 2: " + finalResult));
        String result = resultFuture.get();

        assertThat(result).isEqualTo("Welcome to Baeldung!");
    }

    @Test
    public void whenCallAwait_thenCorrect() {
        String initialValue = "Welcome to ";
        Future<String> resultFuture = Future.of(() -> Util.appendData(initialValue));
        resultFuture = resultFuture.await();
        String result = resultFuture.get();

        assertThat(result).isEqualTo("Welcome to Baeldung!");
    }

    @Test
    public void whenDivideByZero_thenGetThrowable1() {
        Future<Integer> resultFuture = Future.of(() -> Util.divideByZero(10));
        Future<Throwable> throwableFuture = resultFuture.failed();
        Throwable throwable = throwableFuture.get();

        assertThat(throwable.getMessage()).isEqualTo("/ by zero");
    }

    @Test
    public void whenDivideByZero_thenGetThrowable2() {
        Future<Integer> resultFuture = Future.of(() -> Util.divideByZero(10));
        resultFuture.await();
        Option<Throwable> throwableOption = resultFuture.getCause();
        Throwable throwable = throwableOption.get();

        assertThat(throwable.getMessage()).isEqualTo("/ by zero");
    }

    @Test
    public void whenDivideByZero_thenCorrect() throws InterruptedException {
        Future<Integer> resultFuture = Future.of(() -> Util.divideByZero(10));
        resultFuture.await();

        assertThat(resultFuture.isCompleted()).isTrue();
        assertThat(resultFuture.isSuccess()).isFalse();
        assertThat(resultFuture.isFailure()).isTrue();
    }

    @Test
    public void whenAppendData_thenFutureNotEmpty() {
        String initialValue = "Welcome to ";
        Future<String> resultFuture = Future.of(() -> Util.appendData(initialValue));
        resultFuture.await();

        assertThat(resultFuture.isEmpty()).isFalse();
    }

    @Test
    public void whenCallZip_thenCorrect() {
        Future<Tuple2<String, Integer>> future = Future.of(() -> "John")
          .zip(Future.of(() -> new Integer(5)));
        future.await();

        assertThat(future.get()).isEqualTo(Tuple.of("John", new Integer(5)));
    }

    @Test
    public void whenConvertToCompletableFuture_thenCorrect() throws InterruptedException, ExecutionException {
        String initialValue = "Welcome to ";
        Future<String> resultFuture = Future.of(() -> Util.appendData(initialValue));
        CompletableFuture<String> convertedFuture = resultFuture.toCompletableFuture();

        assertThat(convertedFuture.get()).isEqualTo("Welcome to Baeldung!");
    }

    @Test
    public void whenCallMap_thenCorrect() {
        Future<String> futureResult = Future.of(() -> new StringBuilder("from Baeldung"))
          .map(a -> "Hello " + a);
        futureResult.await();

        assertThat(futureResult.get()).isEqualTo("Hello from Baeldung");
    }

    @Test
    public void whenFutureFails_thenGetErrorMessage() {
        Future<String> resultFuture = Future.of(() -> Util.getSubstringMinusOne("Hello"));
        Future<String> errorMessageFuture = resultFuture.recover(Throwable::getMessage);
        String errorMessage = errorMessageFuture.get();

        assertThat(errorMessage).isEqualTo("String index out of range: -1");
    }

    @Test
    public void whenFutureFails_thenGetAnotherFuture() {
        Future<String> resultFuture = Future.of(() -> Util.getSubstringMinusOne("Hello"));
        Future<String> errorMessageFuture = resultFuture.recoverWith(a -> Future.of(a::getMessage));
        String errorMessage = errorMessageFuture.get();

        assertThat(errorMessage).isEqualTo("String index out of range: -1");
    }

    @Test
    public void whenBothFuturesFail_thenGetErrorMessage() {
        Future<String> future1 = Future.of(() -> Util.getSubstringMinusOne("Hello"));
        Future<String> future2 = Future.of(() -> Util.getSubstringMinusTwo("Hello"));
        Future<String> errorMessageFuture = future1.fallbackTo(future2);
        Future<Throwable> errorMessage = errorMessageFuture.failed();
        
        assertThat(
          errorMessage.get().getMessage())
          .isEqualTo("String index out of range: -1");
    }
}
