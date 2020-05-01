package com.baeldung.guava;

import com.google.common.base.Suppliers;
import com.baeldung.guava.memoizer.CostlySupplier;
import com.baeldung.guava.memoizer.Factorial;
import com.baeldung.guava.memoizer.FibonacciSequence;
import org.junit.Test;

import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;

public class GuavaMemoizerUnitTest {

    @Test
    public void givenInteger_whenGetFibonacciNumber_thenShouldCalculateFibonacciNumber() {
        // given
        int n = 95;

        // when
        BigInteger fibonacciNumber = FibonacciSequence.getFibonacciNumber(n);

        // then
        BigInteger expectedFibonacciNumber = new BigInteger("31940434634990099905");
        assertThat(fibonacciNumber, is(equalTo(expectedFibonacciNumber)));
    }

    @Test
    public void givenInteger_whenGetFactorial_thenShouldCalculateFactorial() {
        // given
        int n = 95;

        // when
        BigInteger factorial = Factorial.getFactorial(n);

        // then
        BigInteger expectedFactorial = new BigInteger("10329978488239059262599702099394727095397746340117372869212250571234293987594703124871765375385424468563282236864226607350415360000000000000000000000");
        assertThat(factorial, is(equalTo(expectedFactorial)));
    }

    @Test
    public void givenMemoizedSupplier_whenGet_thenSubsequentGetsAreFast() {
        // given
        Supplier<BigInteger> memoizedSupplier;
        memoizedSupplier = Suppliers.memoize(CostlySupplier::generateBigNumber);

        // when
        BigInteger expectedValue = new BigInteger("12345");
        assertSupplierGetExecutionResultAndDuration(memoizedSupplier, expectedValue, 2000D);

        // then
        assertSupplierGetExecutionResultAndDuration(memoizedSupplier, expectedValue, 0D);
        assertSupplierGetExecutionResultAndDuration(memoizedSupplier, expectedValue, 0D);
    }

    @Test
    public void givenMemoizedSupplierWithExpiration_whenGet_thenSubsequentGetsBeforeExpiredAreFast() throws InterruptedException {
        // given
        Supplier<BigInteger> memoizedSupplier;
        memoizedSupplier = Suppliers.memoizeWithExpiration(CostlySupplier::generateBigNumber, 3, TimeUnit.SECONDS);

        // when
        BigInteger expectedValue = new BigInteger("12345");
        assertSupplierGetExecutionResultAndDuration(memoizedSupplier, expectedValue, 2000D);

        // then
        assertSupplierGetExecutionResultAndDuration(memoizedSupplier, expectedValue, 0D);
        // add one more second until memoized Supplier is evicted from memory
        TimeUnit.SECONDS.sleep(3);
        assertSupplierGetExecutionResultAndDuration(memoizedSupplier, expectedValue, 2000D);
    }

    private <T> void assertSupplierGetExecutionResultAndDuration(Supplier<T> supplier,
                                                                 T expectedValue,
                                                                 double expectedDurationInMs) {
        Instant start = Instant.now();
        T value = supplier.get();
        Long durationInMs = Duration.between(start, Instant.now()).toMillis();
        double marginOfErrorInMs = 100D;

        assertThat(value, is(equalTo(expectedValue)));
        assertThat(durationInMs.doubleValue(), is(closeTo(expectedDurationInMs, marginOfErrorInMs)));
    }

}
