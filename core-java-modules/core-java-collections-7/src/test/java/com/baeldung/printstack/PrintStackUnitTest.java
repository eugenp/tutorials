package com.baeldung.printstack;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

class PrintStackUnitTest {

    @Test
    void givenStack_whenUsingToString_thenPrintStack() {
        Stack<Integer> stack = new Stack<>();
        stack.push(10);
        stack.push(20);
        stack.push(30);
        assertEquals("[10, 20, 30]", stack.toString());
    }

    @Test
    void givenStack_whenUsingForEach_thenPrintStack() {
        Stack<Integer> stack = new Stack<>();
        stack.push(10);
        stack.push(20);
        stack.push(30);

        List<Integer> result = new ArrayList<>();
        for (Integer value : stack) {
            result.add(value);
        }
        assertEquals(Arrays.asList(10, 20, 30), result);
    }

    @Test
    void givenStack_whenUsingDirectForEach_thenPrintStack() {
        Stack<Integer> stack = new Stack<>();
        stack.push(10);
        stack.push(20);
        stack.push(30);

        StringBuilder result = new StringBuilder();
        stack.forEach(element -> result.append(element)
            .append("\n"));

        String expectedOutput = "10\n20\n30";
        assertEquals(expectedOutput, result.toString()
            .trim());
    }

    @Test
    void givenStack_whenUsingStreamReverse_thenPrintStack() {
        Stack<Integer> stack = new Stack<>();
        stack.push(10);
        stack.push(20);
        stack.push(30);

        String result = stack.stream()
            .sorted(Comparator.reverseOrder())
            .map(String::valueOf)
            .collect(Collectors.joining("\n"));

        String expectedOutput = "30\n20\n10";
        assertEquals(expectedOutput, result);
    }

    @Test
    void givenStack_whenUsingIterator_thenPrintStack() {
        Stack<String> stack = new Stack<>();
        stack.push("A");
        stack.push("B");
        stack.push("C");

        Iterator<String> iterator = stack.iterator();
        List<String> result = new ArrayList<>();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }
        assertEquals(Arrays.asList("A", "B", "C"), result);
    }

    @Test
    void givenStack_whenUsingListIteratorReverseOrder_thenPrintStack() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);

        ListIterator<Integer> iterator = stack.listIterator(stack.size());
        List<Integer> result = new ArrayList<>();
        while (iterator.hasPrevious()) {
            result.add(iterator.previous());
        }
        assertEquals(Arrays.asList(3, 2, 1), result);
    }

    @Test
    void givenStack_whenUsingDeque_thenPrintStack() {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);

        StringBuilder result = new StringBuilder();
        stack.forEach(value -> result.append(value)
            .append(" "));

        assertEquals("3 2 1", result.toString()
            .trim());
    }
}
