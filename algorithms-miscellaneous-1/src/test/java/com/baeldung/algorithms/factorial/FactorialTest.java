package com.baeldung.algorithms.factorial;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;

public class FactorialTest {

    Factorial factorial;

    @Before
    public void setup() {
        factorial = new Factorial();
    }

    @Test
    public void givenNumber_whenUsingFactorialUsingLoop_calcualeFactorial() {
        int n = 5;
        assertThat(factorial.factorialUsingLoop(n)).isEqualTo(120);
    }

    @Test
    public void givenNumber_whenUsingFactorialUsingStreams_calcualeFactorial() {
        int n = 5;
        assertThat(factorial.factorialUsingStreams(n)).isEqualTo(120);
    }

    @Test
    public void givenNumber_whenUsingFactorialUsingRecursion_calcualeFactorial() {
        int n = 5;
        assertThat(factorial.factorialUsingRecursion(n)).isEqualTo(120);
    }

    @Test
    public void givenNumber_whenfactorialUsingMemoize_calcualeFactorial() {
        int n = 5;
        assertThat(factorial.factorialUsingMemoize(n)).isEqualTo(120);
        n = 6;
        assertThat(factorial.factorialUsingMemoize(n)).isEqualTo(720);

    }

    @Test
    public void givenNumber_whenUsingFactorialHavingLargeResult_calcualeFactorial() {
        int n = 22;
        assertThat(factorial.factorialHavingLargeResult(n)).isEqualTo(new BigInteger("1124000727777607680000"));
    }

}
