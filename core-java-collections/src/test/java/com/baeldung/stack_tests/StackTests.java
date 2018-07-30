package com.baeldung.stack_tests;

import com.baeldung.thread_safe_lifo.DequeBasedSynchronizedStack;
import com.google.common.collect.Streams;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Stream;

import static java.util.stream.IntStream.range;

/**
 * These tests are to understand the Stack implementation in Java Collections.
 */
public class StackTests {

    @Test
    public void test_basic_with_stack() {
        Stack<String> namesStack = new Stack<>();

        namesStack.push("Bill Gates");
        namesStack.push("Elon Musk");

        Assert.assertEquals("Elon Musk", namesStack.peek());
        Assert.assertEquals("Elon Musk", namesStack.pop());
        Assert.assertEquals("Bill Gates", namesStack.pop());

        Assert.assertEquals(0, namesStack.size());
    }

    @Test
    public void test_basic_with_synchronized_deque() {
        DequeBasedSynchronizedStack<String> namesStack = new DequeBasedSynchronizedStack<>();

        namesStack.push("Bill Gates");
        namesStack.push("Elon Musk");

        Assert.assertEquals("Elon Musk", namesStack.peek());
        Assert.assertEquals("Elon Musk", namesStack.pop());
        Assert.assertEquals("Bill Gates", namesStack.pop());

        Assert.assertEquals(0, namesStack.size());
    }

    @Test
    public void test_basic_with_concurrent_linked_queue() {
        ConcurrentLinkedDeque<String> namesStack = new ConcurrentLinkedDeque<>();

        namesStack.push("Bill Gates");
        namesStack.push("Elon Musk");

        Assert.assertEquals("Elon Musk", namesStack.peek());
        Assert.assertEquals("Elon Musk", namesStack.pop());
        Assert.assertEquals("Bill Gates", namesStack.pop());

        Assert.assertEquals(0, namesStack.size());
    }
}
