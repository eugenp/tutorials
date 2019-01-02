package com.baeldung.typeerasure;

import org.junit.Test;

/**
 * 类型擦除单元测试
 */
public class TypeErasureUnitTest {

    @Test(expected = ClassCastException.class)
    public void givenIntegerStack_whenStringPushedAndAssignPoppedValueToInteger_shouldFail() {
        try{
            IntegerStack integerStack = new IntegerStack(5);
            Stack stack = integerStack;
            stack.push("Hello");
            Integer data = integerStack.pop();
            System.out.println("pop data:{}" + data);
        }
        catch (Exception e){
            System.out.println("Exception:{}" + e);
            throw e;
        }
    }

    @Test
    public void givenAnyArray_whenInvokedPrintArray_shouldSucceed() {
        Integer[] scores = new Integer[] { 100, 200, 10, 99, 20 };
        ArrayContentPrintUtil.printArray(scores);

        System.out.println("\n==================");
        String [] arrayString = new String[]{"hello " , "java " ,  "8! "};

        ArrayContentPrintUtil.printArray(arrayString);
    }


}
