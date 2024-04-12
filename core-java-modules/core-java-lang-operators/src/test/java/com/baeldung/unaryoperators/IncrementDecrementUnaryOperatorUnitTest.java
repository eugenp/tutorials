package com.baeldung.unaryoperators;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class IncrementDecrementUnaryOperatorUnitTest {

    @Test
    public void givenAnOperand_whenUsingPreIncrementUnaryOperator_thenOperandIsIncrementedByOne() {
        int operand = 1;
        ++operand;
        assertThat(operand).isEqualTo(2);
    }

    @Test
    public void givenANumber_whenUsingPreIncrementUnaryOperatorInEvaluation_thenNumberIsIncrementedByOne() {
        int operand = 1;
        int number = ++operand;
        assertThat(number).isEqualTo(2);
    }

    @Test
    public void givenAnOperand_whenUsingPreDecrementUnaryOperator_thenOperandIsDecrementedByOne() {
        int operand = 1;
        --operand;
        assertThat(operand).isEqualTo(0);
    }

    @Test
    public void givenANumber_whenUsingPreDecrementUnaryOperatorInEvaluation_thenNumberIsDecrementedByOne() {
        int operand = 1;
        int number = --operand;
        assertThat(number).isEqualTo(0);
    }

    @Test
    public void givenAnOperand_whenUsingPostIncrementUnaryOperator_thenOperandIsIncrementedByOne() {
        int operand = 1;
        operand++;
        assertThat(operand).isEqualTo(2);
    }

    @Test
    public void givenANumber_whenUsingPostIncrementUnaryOperatorInEvaluation_thenNumberIsSameAsOldValue() {
        int operand = 1;
        int number = operand++;
        assertThat(number).isEqualTo(1);
    }

    @Test
    public void givenAnOperand_whenUsingPostDecrementUnaryOperator_thenOperandIsDecrementedByOne() {
        int operand = 1;
        operand--;
        assertThat(operand).isEqualTo(0);
    }

    @Test
    public void givenANumber_whenUsingPostDecrementUnaryOperatorInEvaluation_thenNumberIsSameAsOldValue() {
        int operand = 1;
        int number = operand--;
        assertThat(number).isEqualTo(1);
    }
}
