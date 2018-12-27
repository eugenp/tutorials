package com.baeldung.algorithms.factorial;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;

public class FactorialUnitTest {

    Factorial factorial;

    @Before
    public void setup() {
        factorial = new Factorial();
    }

    @Test
    public void whenCalculatingFactorialUsingForLoop_thenCorrect() {
        int n = 5;

        assertThat(factorial.factorialUsingForLoop(n)).isEqualTo(120);
    }

    @Test
    public void whenCalculatingFactorialUsingStreams_thenCorrect() {
        int n = 5;

        assertThat(factorial.factorialUsingStreams(n)).isEqualTo(120);
    }

    @Test
    public void whenCalculatingFactorialUsingRecursion_thenCorrect() {
        int n = 5;

        assertThat(factorial.factorialUsingRecursion(n)).isEqualTo(120);
    }

    @Test
    public void whenCalculatingFactorialUsingMemoize_thenCorrect() {
        int n = 5;

        assertThat(factorial.factorialUsingMemoize(n)).isEqualTo(120);

        n = 6;

        assertThat(factorial.factorialUsingMemoize(n)).isEqualTo(720);
    }

    @Test
    public void whenCalculatingFactorialHavingLargeResult_thenCorrect() {
        int n = 22;

        assertThat(factorial.factorialHavingLargeResult(n)).isEqualTo(new BigInteger("1124000727777607680000"));
    }

    @Test
    public void whenCalculatingFactorialUsingApacheCommons_thenCorrect() {
        int n = 5;

        assertThat(factorial.factorialUsingApacheCommons(n)).isEqualTo(120);
    }

    @Test
    public void whenCalculatingFactorialUsingGuava_thenCorrect() {
        int n = 22;

        assertThat(factorial.factorialUsingGuava(n)).isEqualTo(new BigInteger("1124000727777607680000"));
    }

}
