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
    public void givenNumber_whenCallingFactorialUsingLoop_calcualeFactorial() {
        int n = 5;
        assertThat(factorial.factorialUsingLoop(n)).isEqualTo(120);
    }

    @Test
    public void givenNumber_whenCallingFactorialUsingStreams_calcualeFactorial() {
        int n = 5;
        assertThat(factorial.factorialUsingStreams(n)).isEqualTo(120);
    }

    @Test
    public void givenNumber_whenCallingFactorialUsingRecursion_calcualeFactorial() {
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
    public void givenNumber_whenCallingFactorialHavingLargeResult_calcualeFactorial() {
        int n = 22;
        assertThat(factorial.factorialHavingLargeResult(n)).isEqualTo(new BigInteger("1124000727777607680000"));
    }

    @Test
    public void givenNumber_whenCallingFactorialUsingApacheCommons_calcualeFactorial() {
        int n = 5;
        assertThat(factorial.factorialUsingApacheCommons(n)).isEqualTo(120);
    }

    @Test
    public void givenNumber_whenCallingFactorialUsingGuava_calcualeFactorial() {
        int n = 22;
        assertThat(factorial.factorialUsingGuava(n)).isEqualTo(new BigInteger("1124000727777607680000"));
    }

}
