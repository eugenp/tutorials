package org.baeldung.guava;

import com.google.common.base.Suppliers;
import org.baeldung.guava.memoizer.CostlySupplier;
import org.baeldung.guava.memoizer.Factorial;
import org.baeldung.guava.memoizer.FibonacciSequence;
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
        Supplier<BigInteger> memoizedSupplier;
        memoizedSupplier = Suppliers.memoize(CostlySupplier::generateBigNumber);

        Instant start = Instant.now();
        BigInteger bigNumber = memoizedSupplier.get();
        Long duration = Duration.between(start, Instant.now()).toMillis();
        assertThat(bigNumber, is(equalTo(new BigInteger("90840813214259257091017657299038202013732367616052353832494971785771209455315189760000000000"))));
        assertThat(duration.doubleValue(), is(closeTo(2000D, 100D)));

        start = Instant.now();
        bigNumber = memoizedSupplier.get().add(BigInteger.ONE);
        duration = Duration.between(start, Instant.now()).toMillis();
        assertThat(bigNumber, is(equalTo(new BigInteger("90840813214259257091017657299038202013732367616052353832494971785771209455315189760000000001"))));
        assertThat(duration.doubleValue(), is(closeTo(0D, 100D)));

        start = Instant.now();
        bigNumber = memoizedSupplier.get().add(BigInteger.TEN);
        duration = Duration.between(start, Instant.now()).toMillis();
        assertThat(bigNumber, is(equalTo(new BigInteger("90840813214259257091017657299038202013732367616052353832494971785771209455315189760000000010"))));
        assertThat(duration.doubleValue(), is(closeTo(0D, 100D)));
    }

    @Test
    public void givenMemoizedSupplierWithExpiration_whenGet_thenSubsequentGetsBeforeExpiredAreFast() {
        Supplier<BigInteger> memoizedSupplier;
        memoizedSupplier = Suppliers.memoizeWithExpiration(CostlySupplier::generateBigNumber, 3, TimeUnit.SECONDS);

        Instant start = Instant.now();
        BigInteger bigNumber = memoizedSupplier.get();
        Long duration = Duration.between(start, Instant.now()).toMillis();
        assertThat(bigNumber, is(equalTo(new BigInteger("90840813214259257091017657299038202013732367616052353832494971785771209455315189760000000000"))));
        assertThat(duration.doubleValue(), is(closeTo(2000D, 100D)));

        start = Instant.now();
        bigNumber = memoizedSupplier.get().add(BigInteger.ONE);
        duration = Duration.between(start, Instant.now()).toMillis();
        assertThat(bigNumber, is(equalTo(new BigInteger("90840813214259257091017657299038202013732367616052353832494971785771209455315189760000000001"))));
        assertThat(duration.doubleValue(), is(closeTo(0D, 100D)));

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
        }

        start = Instant.now();
        bigNumber = memoizedSupplier.get().add(BigInteger.TEN);
        duration = Duration.between(start, Instant.now()).toMillis();
        assertThat(bigNumber, is(equalTo(new BigInteger("90840813214259257091017657299038202013732367616052353832494971785771209455315189760000000010"))));
        assertThat(duration.doubleValue(), is(closeTo(2000D, 100D)));
    }

}
