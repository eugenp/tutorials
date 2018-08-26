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
public class StackUnitTest {

    @Test
    public void givenStack_whenPushPopPeek_thenWorkRight() {
        Stack<String> namesStack = new Stack<>();

        namesStack.push("Bill Gates");
        namesStack.push("Elon Musk");

        Assert.assertEquals("Elon Musk", namesStack.peek());
        Assert.assertEquals("Elon Musk", namesStack.pop());
        Assert.assertEquals("Bill Gates", namesStack.pop());

        Assert.assertEquals(0, namesStack.size());
    }

    @Test
    public void givenSynchronizedDeque_whenPushPopPeek_thenWorkRight() {
        DequeBasedSynchronizedStack<String> namesStack = new DequeBasedSynchronizedStack<>();

        namesStack.push("Bill Gates");
        namesStack.push("Elon Musk");

        Assert.assertEquals("Elon Musk", namesStack.peek());
        Assert.assertEquals("Elon Musk", namesStack.pop());
        Assert.assertEquals("Bill Gates", namesStack.pop());

        Assert.assertEquals(0, namesStack.size());
    }

    @Test
    public void givenConcurrentLinkedDeque_whenPushPopPeek_thenWorkRight() {
        ConcurrentLinkedDeque<String> namesStack = new ConcurrentLinkedDeque<>();

        namesStack.push("Bill Gates");
        namesStack.push("Elon Musk");

        Assert.assertEquals("Elon Musk", namesStack.peek());
        Assert.assertEquals("Elon Musk", namesStack.pop());
        Assert.assertEquals("Bill Gates", namesStack.pop());

        Assert.assertEquals(0, namesStack.size());
    }
}
