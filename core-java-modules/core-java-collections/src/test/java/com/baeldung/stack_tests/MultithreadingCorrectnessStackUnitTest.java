package com.baeldung.stack_tests;

import com.baeldung.thread_safe_lifo.DequeBasedSynchronizedStack;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.concurrent.ConcurrentLinkedDeque;

import static java.util.stream.IntStream.range;

/**
 * Correctness tests for Stack in multi threaded environment.
 */
public class MultithreadingCorrectnessStackUnitTest {

    @Test
    public void givenSynchronizedDeque_whenExecutedParallel_thenWorkRight() {

        DequeBasedSynchronizedStack<Integer> deque = new DequeBasedSynchronizedStack<>();

        // Serial execution of push on ConcurrentLinkedQueue will always result in correct execution.
        range(1, 10000).forEach(value -> deque.push(value));

        int sum = 0;
        while(deque.peek() != null) {
            sum += deque.pop();
        }

        Assert.assertEquals(49995000, sum);

        // Parallel execution of push on ConcurrentLinkedQueue will always result in correct execution.
        range(1, 10000).parallel().forEach(value -> deque.push(value));

        sum = 0;
        while(deque.peek() != null) {
            sum += deque.pop();
        }

        Assert.assertEquals(49995000, sum);
    }

    @Test
    public void givenConcurrentLinkedQueue_whenExecutedParallel_thenWorkRight() {

        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>();

        // Serial execution of push on ConcurrentLinkedQueue will always result in correct execution.
        range(1, 10000).forEach(value -> deque.push(value));

        int sum = 0;
        while(deque.peek() != null) {
            sum += deque.pop();
        }

        Assert.assertEquals(49995000, sum);

        // Parallel execution of push on ConcurrentLinkedQueue will always result in correct execution.
        range(1, 10000).parallel().forEach(value -> deque.push(value));

        sum = 0;
        while(deque.peek() != null) {
            sum += deque.pop();
        }

        Assert.assertEquals(49995000, sum);
    }

    @Test
    public void givenArrayDeque_whenExecutedParallel_thenShouldFail() {

        ArrayDeque<Integer> deque = new ArrayDeque<>();

        // Serial execution of push on ArrayDeque will always result in correct execution.
        range(1, 10000).forEach(value -> deque.push(value));

        int sum = 0;
        while(deque.peek() != null) {
            sum += deque.pop();
        }

        Assert.assertEquals(49995000, sum);

        // Parallel execution of push on ArrayDeque will not result in correct execution.
        range(1, 10000).parallel().forEach(value -> deque.push(value));

        sum = 0;
        while(deque.peek() != null) {
            sum += deque.pop();
        }

        // This shouldn't happen.
        if(sum == 49995000) {
            System.out.println("Something wrong in the environment, Please try some big value and check");
            // To safe-guard build without test failures.
            return;
        }

        Assert.assertNotEquals(49995000, sum);
    }
}
