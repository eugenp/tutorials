package com.baeldung.typeerasure;

import org.junit.Test;

public class TypeErasureUnitTest {

    @Test(expected = ClassCastException.class)
    public void givenIntegerStack_whenStringPushedAndAssignPoppedValueToInteger_shouldFail() {
        IntegerStack integerStack = new IntegerStack(5);
        Stack stack = integerStack;
        stack.push("Hello");
        Integer data = integerStack.pop();
    }

    @Test
    public void givenAnyArray_whenInvokedPrintArray_shouldSucceed() {
        Integer[] scores = new Integer[] { 100, 200, 10, 99, 20 };
        ArrayContentPrintUtil.printArray(scores);
    }

}
