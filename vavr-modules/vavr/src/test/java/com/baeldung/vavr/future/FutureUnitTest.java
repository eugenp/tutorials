package com.baeldung.vavr.future;

import io.vavr.Tuple;
import io.vavr.concurrent.Future;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.util.concurrent.Executors.newSingleThreadExecutor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FutureUnitTest {

    private static final String error = "Failed to get underlying value.";
    private static final String HELLO = "Welcome to Baeldung!";

    @Test
    public void whenChangeExecutorService_thenCorrect() {
        String result = Future.of(newSingleThreadExecutor(), () -> HELLO)
          .getOrElse(error);

        assertThat(result)
          .isEqualTo(HELLO);
    }

    @Test
    public void whenAppendData_thenCorrect1() {
        String result = Future.of(() -> HELLO)
          .getOrElse(error);

        assertThat(result)
          .isEqualTo(HELLO);
    }

    @Test
    public void whenAppendData_thenCorrect2() {
        Future<String> resultFuture = Future.of(() -> HELLO)
          .await();

        Option<Try<String>> futureOption = resultFuture.getValue();
        String result = futureOption.get().getOrElse(error);

        assertThat(result)
          .isEqualTo(HELLO);
    }

    @Test
    public void whenAppendData_thenSuccess() {
        String result = Future.of(() -> HELLO)
          .onSuccess(finalResult -> System.out.println("Successfully Completed - Result: " + finalResult))
          .onFailure(finalResult -> System.out.println("Failed - Result: " + finalResult))
          .getOrElse(error);

        assertThat(result)
          .isEqualTo(HELLO);
    }
    
    @Test
    public void whenTransform_thenCorrect() {
        Future<Object> future = Future.of(() -> 5)
          .transformValue(result -> Try.of(() -> HELLO + result.get()));
            
        assertThat(future.get()).isEqualTo(HELLO + 5);
    }

    @Test
    public void whenChainingCallbacks_thenCorrect() {
        Future.of(() -> HELLO)
          .andThen(r -> System.out.println("Completed - 1: " + r))
          .andThen(r -> System.out.println("Completed - 2: " + r));
    }

    @Test
    public void whenCallAwait_thenCorrect() {
        Future<String> resultFuture = Future.of(() -> HELLO)
          .await();
        String result = resultFuture.getValue().get().getOrElse(error);

        assertThat(result)
          .isEqualTo(HELLO);
    }

    @Test
    public void whenDivideByZero_thenGetThrowable1() {
        Future<Integer> resultFuture = Future.of(() -> 10 / 0);

        assertThatThrownBy(resultFuture::get)
          .isInstanceOf(ArithmeticException.class);
    }

    @Test
    public void whenDivideByZero_thenGetThrowable2() {
        Future<Integer> resultFuture = Future.of(() -> 10 / 0)
          .await();

        assertThat(resultFuture.getCause().get().getMessage())
          .isEqualTo("/ by zero");
    }

    @Test
    public void whenDivideByZero_thenCorrect() {
        Future<Integer> resultFuture = Future.of(() -> 10 / 0)
          .await();

        assertThat(resultFuture.isCompleted()).isTrue();
        assertThat(resultFuture.isSuccess()).isFalse();
        assertThat(resultFuture.isFailure()).isTrue();
    }

    @Test
    public void whenAppendData_thenFutureNotEmpty() {
        Future<String> resultFuture = Future.of(() -> HELLO)
          .await();

        assertThat(resultFuture.isEmpty())
          .isFalse();
    }

    @Test
    public void whenCallZip_thenCorrect() {
        Future<String> f1 = Future.of(() -> "hello1");
        Future<String> f2 = Future.of(() -> "hello2");

        assertThat(f1.zip(f2).get())
          .isEqualTo(Tuple.of("hello1", "hello2"));
    }

    @Test
    public void whenConvertToCompletableFuture_thenCorrect() throws InterruptedException, ExecutionException {
        CompletableFuture<String> convertedFuture = Future.of(() -> HELLO)
          .toCompletableFuture();

        assertThat(convertedFuture.get())
          .isEqualTo(HELLO);
    }

    @Test
    public void whenCallMap_thenCorrect() {
        Future<String> futureResult = Future.of(() -> "from Baeldung")
          .map(a -> "Hello " + a)
          .await();

        assertThat(futureResult.get())
          .isEqualTo("Hello from Baeldung");
    }
    
    @Test
    public void whenCallFlatMap_thenCorrect() {
        Future<Object> futureMap = Future.of(() -> 1)
          .flatMap((i) -> Future.of(() -> "Hello: " + i));
     
        assertThat(futureMap.get()).isEqualTo("Hello: 1");
    }

    @Test
    public void whenFutureFails_thenGetErrorMessage() {
        Future<String> future = Future.of(() -> "Hello".substring(-1))
          .recover(x -> "fallback value");

        assertThat(future.get())
          .isEqualTo("fallback value");
    }

    @Test
    public void whenFutureFails_thenGetAnotherFuture() {
        Future<String> future = Future.of(() -> "Hello".substring(-1))
          .recoverWith(x -> Future.of(() -> "fallback value"));

        assertThat(future.get())
          .isEqualTo("fallback value");
    }

    @Test
    public void whenBothFuturesFail_thenGetErrorMessage() {
        Future<String> f1 = Future.of(() -> "Hello".substring(-1));
        Future<String> f2 = Future.of(() -> "Hello".substring(-2));

        Future<String> errorMessageFuture = f1.fallbackTo(f2);
        Future<Throwable> errorMessage = errorMessageFuture.failed();

        assertThat(
          errorMessage.get().getMessage())
          .isEqualTo("begin -1, end 5, length 5");
    }
}
