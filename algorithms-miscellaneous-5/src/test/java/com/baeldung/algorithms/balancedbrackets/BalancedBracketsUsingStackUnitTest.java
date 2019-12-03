package com.baeldung.algorithms.balancedbrackets;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BalancedBracketsUsingStackUnitTest {
        private BalancedBracketsUsingStack balancedBracketsUsingStack;

        @Before
        public void setup() {
                balancedBracketsUsingStack = new BalancedBracketsUsingStack();
        }


        @Test
        public void givenNullInput_whenCheckingForBalance_shouldReturnFalse() {
                boolean result = balancedBracketsUsingStack.isBalanced(null);
                assertThat(result).isFalse();
        }

        @Test
        public void givenEmptyString_whenCheckingForBalance_shouldReturnFalse() {
                boolean result = balancedBracketsUsingStack.isBalanced("");
                assertThat(result).isFalse();
        }

        @Test
        public void givenInvalidCharacterString_whenCheckingForBalance_shouldReturnFalse() {
                boolean result = balancedBracketsUsingStack.isBalanced("abc[](){}");
                assertThat(result).isFalse();
        }

        @Test
        public void givenOddLengthString_whenCheckingForBalance_shouldReturnFalse() {
                boolean result = balancedBracketsUsingStack.isBalanced("{{[]()}}}");
                assertThat(result).isFalse();
        }

        @Test
        public void givenEvenLengthString_whenCheckingForBalance_shouldReturnFalse() {
                boolean result = balancedBracketsUsingStack.isBalanced("{{[]()}}}}");
                assertThat(result).isFalse();
        }

        @Test
        public void givenEvenLengthUnbalancedString_whenCheckingForBalance_shouldReturnFalse() {
                boolean result = balancedBracketsUsingStack.isBalanced("{[(])}");
                assertThat(result).isFalse();
        }


        @Test
        public void givenEvenLengthBalancedString_whenCheckingForBalance_shouldReturnTrue() {
                boolean result = balancedBracketsUsingStack.isBalanced("{[()]}");
                assertThat(result).isTrue();
        }

        @Test
        public void givenBalancedString_whenCheckingForBalance_shouldReturnTrue() {
                boolean result = balancedBracketsUsingStack.isBalanced("{{[[(())]]}}");
                assertThat(result).isTrue();
        }


        @Test
        public void givenAnotherBalancedString_whenCheckingForBalance_shouldReturnTrue() {
                boolean result = balancedBracketsUsingStack.isBalanced("{{([])}}");
                assertThat(result).isTrue();
        }


        @Test
        public void givenUnBalancedString_whenCheckingForBalance_shouldReturnFalse() {
                boolean result = balancedBracketsUsingStack.isBalanced("{{)[](}}");
                assertThat(result).isFalse();
        }

} 