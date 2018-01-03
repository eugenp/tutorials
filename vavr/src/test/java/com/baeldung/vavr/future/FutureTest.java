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
    
    private static final String error = "Failed to get underlying value.";

    @Test
    public void whenChangeExecutorService_thenCorrect() throws InterruptedException {
        String initialValue = "Welcome to ";
        Future<String> resultFuture = Future.of(
          Executors.newSingleThreadExecutor(),
          () -> Util.appendData(initialValue));
        Thread.sleep(20);
        String result = resultFuture.getOrElse(error);

        assertThat(result).isEqualTo("Welcome to Baeldung!");
    }

    @Test
    public void whenAppendData_thenCorrect1() throws InterruptedException {
        String initialValue = "Welcome to ";
        Future<String> resultFuture = Future.of(() -> Util.appendData(initialValue));
        Thread.sleep(20);
        String result = resultFuture.getOrElse(new String(error));

        assertThat(result).isEqualTo("Welcome to Baeldung!");
    }

    @Test
    public void whenAppendData_thenCorrect2() throws InterruptedException {
        String initialValue = "Welcome to ";
        Future<String> resultFuture = Future.of(() -> Util.appendData(initialValue));
        Thread.sleep(20);
        resultFuture.await();
        Option<Try<String>> futureOption = resultFuture.getValue();
        Try<String> futureTry = futureOption.get();
        String result = futureTry.getOrElse(error);

        assertThat(result).isEqualTo("Welcome to Baeldung!");
    }

    @Test
    public void whenAppendData_thenSuccess() throws InterruptedException {
        String initialValue = "Welcome to ";
        Future<String> resultFuture = Future.of(() -> Util.appendData(initialValue))
          .onSuccess(finalResult -> System.out.println("Successfully Completed - Result: " + finalResult))
          .onFailure(finalResult -> System.out.println("Failed - Result: " + finalResult));
        Thread.sleep(20);
        String result = resultFuture.getOrElse(error);

        assertThat(result).isEqualTo("Welcome to Baeldung!");
    }

    @Test
    public void whenChainingCallbacks_thenCorrect() throws InterruptedException {
        String initialValue = "Welcome to ";
        Future<String> resultFuture = Future.of(() -> Util.appendData(initialValue))
          .andThen(finalResult -> System.out.println("Completed - 1: " + finalResult))
          .andThen(finalResult -> System.out.println("Completed - 2: " + finalResult));
        Thread.sleep(20);
        String result = resultFuture.getOrElse(error);

        assertThat(result).isEqualTo("Welcome to Baeldung!");
    }

    @Test
    public void whenCallAwait_thenCorrect() throws InterruptedException {
        String initialValue = "Welcome to ";
        Future<String> resultFuture = Future.of(() -> Util.appendData(initialValue));
        Thread.sleep(20);
        resultFuture = resultFuture.await();
        String result = resultFuture.getOrElse(error);

        assertThat(result).isEqualTo("Welcome to Baeldung!");
    }

    @Test
    public void whenDivideByZero_thenGetThrowable1() throws InterruptedException {
        Future<Integer> resultFuture = Future.of(() -> Util.divideByZero(10));
        Thread.sleep(20);
        Future<Throwable> throwableFuture = resultFuture.failed();
        Throwable throwable = throwableFuture.getOrElse(new Throwable());

        assertThat(throwable.getMessage()).isEqualTo("/ by zero");
    }

    @Test
    public void whenDivideByZero_thenGetThrowable2() throws InterruptedException {
        Future<Integer> resultFuture = Future.of(() -> Util.divideByZero(10));
        Thread.sleep(20);
        resultFuture.await();
        Option<Throwable> throwableOption = resultFuture.getCause();
        Throwable throwable = throwableOption.getOrElse(new Throwable());

        assertThat(throwable.getMessage()).isEqualTo("/ by zero");
    }

    @Test
    public void whenDivideByZero_thenCorrect() throws InterruptedException {
        Future<Integer> resultFuture = Future.of(() -> Util.divideByZero(10));
        Thread.sleep(20);
        resultFuture.await();

        assertThat(resultFuture.isCompleted()).isTrue();
        assertThat(resultFuture.isSuccess()).isFalse();
        assertThat(resultFuture.isFailure()).isTrue();
    }

    @Test
    public void whenAppendData_thenFutureNotEmpty() throws InterruptedException {
        String initialValue = "Welcome to ";
        Future<String> resultFuture = Future.of(() -> Util.appendData(initialValue));
        Thread.sleep(20);
        resultFuture.await();

        assertThat(resultFuture.isEmpty()).isFalse();
    }

    @Test
    public void whenCallZip_thenCorrect() throws InterruptedException {
        Future<Tuple2<String, Integer>> future = Future.of(() -> "John")
          .zip(Future.of(() -> new Integer(5)));
        Thread.sleep(20);
        future.await();

        assertThat(future.getOrElse(new Tuple2<String, Integer>(error, 0)))
          .isEqualTo(Tuple.of("John", new Integer(5)));
    }

    @Test
    public void whenConvertToCompletableFuture_thenCorrect() throws InterruptedException, ExecutionException {
        String initialValue = "Welcome to ";
        Future<String> resultFuture = Future.of(() -> Util.appendData(initialValue));
        Thread.sleep(20);
        CompletableFuture<String> convertedFuture = resultFuture.toCompletableFuture();

        assertThat(convertedFuture.get()).isEqualTo("Welcome to Baeldung!");
    }

    @Test
    public void whenCallMap_thenCorrect() throws InterruptedException {
        Future<String> futureResult = Future.of(() -> new StringBuilder("from Baeldung"))
          .map(a -> "Hello " + a);
        Thread.sleep(20);
        futureResult.await();

        assertThat(futureResult.getOrElse(error)).isEqualTo("Hello from Baeldung");
    }

    @Test
    public void whenFutureFails_thenGetErrorMessage() throws InterruptedException {
        Future<String> resultFuture = Future.of(() -> Util.getSubstringMinusOne("Hello"));
        Thread.sleep(20);
        Future<String> errorMessageFuture = resultFuture.recover(Throwable::getMessage);
        String errorMessage = errorMessageFuture.getOrElse(error);

        assertThat(errorMessage).isEqualTo("String index out of range: -1");
    }

    @Test
    public void whenFutureFails_thenGetAnotherFuture() throws InterruptedException {
        Future<String> resultFuture = Future.of(() -> Util.getSubstringMinusOne("Hello"));
        Thread.sleep(20);
        Future<String> errorMessageFuture = resultFuture.recoverWith(a -> Future.of(a::getMessage));
        String errorMessage = errorMessageFuture.getOrElse(error);

        assertThat(errorMessage).isEqualTo("String index out of range: -1");
    }

    @Test
    public void whenBothFuturesFail_thenGetErrorMessage() throws InterruptedException {
        Future<String> future1 = Future.of(() -> Util.getSubstringMinusOne("Hello"));
        Future<String> future2 = Future.of(() -> Util.getSubstringMinusTwo("Hello"));
        Thread.sleep(20);
        Future<String> errorMessageFuture = future1.fallbackTo(future2);
        Future<Throwable> errorMessage = errorMessageFuture.failed();
        
        assertThat(
          errorMessage.getOrElse(new Throwable()).getMessage())
          .isEqualTo("String index out of range: -1");
    }
}
