package com.baeldung.algorithms.balancedbrackets;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalancedBracketsUsingDequeUnitTest {
    private BalancedBracketsUsingDeque balancedBracketsUsingDeque;

    @BeforeEach
    public void setup() {
        balancedBracketsUsingDeque = new BalancedBracketsUsingDeque();
    }

    @Test
    void givenNullInput_whenCheckingForBalance_shouldReturnFalse() {
        boolean result = balancedBracketsUsingDeque.isBalanced(null);
        assertThat(result).isFalse();
    }

    @Test
    void givenEmptyString_whenCheckingForBalance_shouldReturnTrue() {
        boolean result = balancedBracketsUsingDeque.isBalanced("");
        assertThat(result).isTrue();
    }

    @Test
    void givenInvalidCharacterString_whenCheckingForBalance_shouldReturnFalse() {
        boolean result = balancedBracketsUsingDeque.isBalanced("abc[](){}");
        assertThat(result).isFalse();
    }

    @Test
    void givenOddLengthString_whenCheckingForBalance_shouldReturnFalse() {
        boolean result = balancedBracketsUsingDeque.isBalanced("{{[]()}}}");
        assertThat(result).isFalse();
    }

    @Test
    void givenEvenLengthString_whenCheckingForBalance_shouldReturnFalse() {
        boolean result = balancedBracketsUsingDeque.isBalanced("{{[]()}}}}");
        assertThat(result).isFalse();
    }

    @Test
    void givenEvenLengthUnbalancedString_whenCheckingForBalance_shouldReturnFalse() {
        boolean result = balancedBracketsUsingDeque.isBalanced("{[(])}");
        assertThat(result).isFalse();
    }

    @Test
    void givenAnotherEvenLengthUnbalancedString_whenCheckingForBalance_shouldReturnFalse() {
        boolean result = balancedBracketsUsingDeque.isBalanced("{{}(");
        assertThat(result).isFalse();
    }

    @Test
    void givenEvenLengthBalancedString_whenCheckingForBalance_shouldReturnTrue() {
        boolean result = balancedBracketsUsingDeque.isBalanced("{[()]}");
        assertThat(result).isTrue();
    }

    @Test
    void givenBalancedString_whenCheckingForBalance_shouldReturnTrue() {
        boolean result = balancedBracketsUsingDeque.isBalanced("{{[[(())]]}}");
        assertThat(result).isTrue();
    }

    @Test
    void givenAnotherBalancedString_whenCheckingForBalance_shouldReturnTrue() {
        boolean result = balancedBracketsUsingDeque.isBalanced("{{([])}}");
        assertThat(result).isTrue();
    }

    @Test
    void givenUnBalancedString_whenCheckingForBalance_shouldReturnFalse() {
        boolean result = balancedBracketsUsingDeque.isBalanced("{{)[](}}");
        assertThat(result).isFalse();
    }

}