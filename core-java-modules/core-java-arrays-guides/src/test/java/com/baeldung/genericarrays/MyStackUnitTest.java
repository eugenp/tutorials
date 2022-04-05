package com.baeldung.genericarrays;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MyStackUnitTest {

    @Test
    public void givenStackWithTwoItems_whenPop_thenReturnLastAdded() {
        MyStack<String> myStack = new MyStack<>(String.class, 2);
        myStack.push("hello");
        myStack.push("example");

        assertEquals("example", myStack.pop());
    }

    @Test (expected = RuntimeException.class)
    public void givenStackWithFixedCapacity_whenExceedCapacity_thenThrowException() {
        MyStack<Integer> myStack = new MyStack<>(Integer.class, 2);
        myStack.push(100);
        myStack.push(200);
        myStack.push(300);
    }

    @Test(expected = RuntimeException.class)
    public void givenStack_whenPopOnEmptyStack_thenThrowException() {
        MyStack<Integer> myStack = new MyStack<>(Integer.class, 1);
        myStack.push(100);
        myStack.pop();
        myStack.pop();
    }

    @Test
    public void givenStackWithItems_whenGetAllElements_thenSizeShouldEqualTotal() {
        MyStack<String> myStack = new MyStack<>(String.class, 2);
        myStack.push("hello");
        myStack.push("example");

        String[] items = myStack.getAllElements();

        assertEquals(2, items.length);
    }
}
