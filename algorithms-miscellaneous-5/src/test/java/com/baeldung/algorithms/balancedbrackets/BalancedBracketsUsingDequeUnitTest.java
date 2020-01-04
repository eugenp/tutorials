package com.baeldung.algorithms.balancedbrackets;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BalancedBracketsUsingDequeUnitTest {
        private BalancedBracketsUsingDeque balancedBracketsUsingDeque;

        @Before
        public void setup() {
                balancedBracketsUsingDeque = new BalancedBracketsUsingDeque();
        }


        @Test
        public void givenNullInput_whenCheckingForBalance_shouldReturnFalse() {
                boolean result = balancedBracketsUsingDeque.isBalanced(null);
                assertThat(result).isFalse();
        }

        @Test
        public void givenEmptyString_whenCheckingForBalance_shouldReturnFalse() {
                boolean result = balancedBracketsUsingDeque.isBalanced("");
                assertThat(result).isFalse();
        }

        @Test
        public void givenInvalidCharacterString_whenCheckingForBalance_shouldReturnFalse() {
                boolean result = balancedBracketsUsingDeque.isBalanced("abc[](){}");
                assertThat(result).isFalse();
        }

        @Test
        public void givenOddLengthString_whenCheckingForBalance_shouldReturnFalse() {
                boolean result = balancedBracketsUsingDeque.isBalanced("{{[]()}}}");
                assertThat(result).isFalse();
        }

        @Test
        public void givenEvenLengthString_whenCheckingForBalance_shouldReturnFalse() {
                boolean result = balancedBracketsUsingDeque.isBalanced("{{[]()}}}}");
                assertThat(result).isFalse();
        }

        @Test
        public void givenEvenLengthUnbalancedString_whenCheckingForBalance_shouldReturnFalse() {
                boolean result = balancedBracketsUsingDeque.isBalanced("{[(])}");
                assertThat(result).isFalse();
        }


        @Test
        public void givenEvenLengthBalancedString_whenCheckingForBalance_shouldReturnTrue() {
                boolean result = balancedBracketsUsingDeque.isBalanced("{[()]}");
                assertThat(result).isTrue();
        }

        @Test
        public void givenBalancedString_whenCheckingForBalance_shouldReturnTrue() {
                boolean result = balancedBracketsUsingDeque.isBalanced("{{[[(())]]}}");
                assertThat(result).isTrue();
        }


        @Test
        public void givenAnotherBalancedString_whenCheckingForBalance_shouldReturnTrue() {
                boolean result = balancedBracketsUsingDeque.isBalanced("{{([])}}");
                assertThat(result).isTrue();
        }


        @Test
        public void givenUnBalancedString_whenCheckingForBalance_shouldReturnFalse() {
                boolean result = balancedBracketsUsingDeque.isBalanced("{{)[](}}");
                assertThat(result).isFalse();
        }

} 