package com.baeldung;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;

public class PrintStack {

    public static void givenStack_whenUsingToString_thenPrintStack() {
        Stack<Integer> stack = new Stack<>();
        stack.push(10);
        stack.push(20);
        stack.push(30);
        System.out.print(stack.toString());
    }

    public static void givenStack_whenUsingForEach_thenPrintStack() {
        Stack<Integer> stack = new Stack<>();
        stack.push(10);
        stack.push(20);
        stack.push(30);

        List<Integer> result = new ArrayList<>();
        for (Integer value : stack) {
            System.out.print(value + " ");
        }
    }

    public static void givenStack_whenUsingDirectForEach_thenPrintStack() {
        Stack<Integer> stack = new Stack<>();
        stack.push(10);
        stack.push(20);
        stack.push(30);

        stack.forEach(element -> System.out.println(element));
    }

    public static void givenStack_whenUsingStreamReverse_thenPrintStack() {
        Stack<Integer> stack = new Stack<>();
        stack.push(10);
        stack.push(20);
        stack.push(30);

        stack.stream()
            .sorted(Comparator.reverseOrder())
            .forEach(System.out::println);
    }

    public static void givenStack_whenUsingIterator_thenPrintStack() {
        Stack<String> stack = new Stack<>();
        stack.push("10");
        stack.push("20");
        stack.push("30");

        Iterator<String> iterator = stack.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
    }

    public static void givenStack_whenUsingListIteratorReverseOrder_thenPrintStack() {
        Stack<Integer> stack = new Stack<>();
        stack.push(10);
        stack.push(20);
        stack.push(30);

        ListIterator<Integer> iterator = stack.listIterator(stack.size());
        while (iterator.hasPrevious()) {
            System.out.print(iterator.previous() + " ");
        }
    }

    public static void givenStack_whenUsingDeque_thenPrintStack() {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(10);
        stack.push(20);
        stack.push(30);

        stack.forEach(e -> System.out.print(e + " "));
    }
}
