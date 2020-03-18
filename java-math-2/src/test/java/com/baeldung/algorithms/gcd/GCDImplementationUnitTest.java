package com.baeldung.algorithms.gcd;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GCDImplementationUnitTest {

    @Test
    public void whenCalculatingGCDByBruteForceMethod_thenCorrect() {
        int n1 = 60;
        int n2 = 90;
        int gcd = GCDImplementation.gcdByBruteForce(n1, n2);
        assertThat(gcd).isEqualTo(30);
    }

    @Test
    public void whenCalculatingGCDByEuclidsAlgorithm_thenCorrect() {
        int n1 = 60;
        int n2 = 90;
        int gcd = GCDImplementation.gcdByEuclidsAlgorithm(n1, n2);
        assertThat(gcd).isEqualTo(30);
    }

    @Test
    public void whenCalculatingGCDBySteinsAlgorithm_thenCorrect() {
        int n1 = 60;
        int n2 = 90;
        int gcd = GCDImplementation.gcdBySteinsAlgorithm(n1, n2);
        assertThat(gcd).isEqualTo(30);
    }
}
