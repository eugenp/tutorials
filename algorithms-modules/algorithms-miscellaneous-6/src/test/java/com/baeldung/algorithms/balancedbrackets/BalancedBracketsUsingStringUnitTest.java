package com.baeldung.algorithms.balancedbrackets;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BalancedBracketsUsingStringUnitTest {
    private BalancedBracketsUsingString balancedBracketsUsingString;

    @Before
    public void setup() {
        balancedBracketsUsingString = new BalancedBracketsUsingString();
    }

    @Test
    public void givenNullInput_whenCheckingForBalance_shouldReturnFalse() {
        boolean result = balancedBracketsUsingString.isBalanced(null);
        assertThat(result).isFalse();
    }

    @Test
    public void givenEmptyString_whenCheckingForBalance_shouldReturnTrue() {
        boolean result = balancedBracketsUsingString.isBalanced("");
        assertThat(result).isTrue();
    }

    @Test
    public void givenInvalidCharacterString_whenCheckingForBalance_shouldReturnFalse() {
        boolean result = balancedBracketsUsingString.isBalanced("abc[](){}");
        assertThat(result).isFalse();
    }

    @Test
    public void givenOddLengthString_whenCheckingForBalance_shouldReturnFalse() {
        boolean result = balancedBracketsUsingString.isBalanced("{{[]()}}}");
        assertThat(result).isFalse();
    }

    @Test
    public void givenEvenLengthString_whenCheckingForBalance_shouldReturnFalse() {
        boolean result = balancedBracketsUsingString.isBalanced("{{[]()}}}}");
        assertThat(result).isFalse();
    }

    @Test
    public void givenEvenLengthUnbalancedString_whenCheckingForBalance_shouldReturnFalse() {
        boolean result = balancedBracketsUsingString.isBalanced("{[(])}");
        assertThat(result).isFalse();
    }

    @Test
    public void givenAnotherEvenLengthUnbalancedString_whenCheckingForBalance_shouldReturnFalse() {
        boolean result = balancedBracketsUsingString.isBalanced("{{}(");
        assertThat(result).isFalse();
    }

    @Test
    public void givenEvenLengthBalancedString_whenCheckingForBalance_shouldReturnTrue() {
        boolean result = balancedBracketsUsingString.isBalanced("{[()]}");
        assertThat(result).isTrue();
    }

    @Test
    public void givenBalancedString_whenCheckingForBalance_shouldReturnTrue() {
        boolean result = balancedBracketsUsingString.isBalanced("{{[[(())]]}}");
        assertThat(result).isTrue();
    }

    @Test
    public void givenAnotherBalancedString_whenCheckingForBalance_shouldReturnTrue() {
        boolean result = balancedBracketsUsingString.isBalanced("{{([])}}");
        assertThat(result).isTrue();
    }

    @Test
    public void givenUnBalancedString_whenCheckingForBalance_shouldReturnFalse() {
        boolean result = balancedBracketsUsingString.isBalanced("{{)[](}}");
        assertThat(result).isFalse();
    }

}