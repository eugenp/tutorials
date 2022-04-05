package com.baeldung.commons.collections.circularfifoqueue;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.junit.Assert;
import org.junit.Test;

public class CircularFifoQueueUnitTest {

    private static final int DEFAULT_SIZE = 32;

    private static final int FIXED_SIZE = 5;

    private static final int COLLECTION_SIZE = 7;

    private static final String TEST_COLOR = "Red";

    private static final String TEST_COLOR_BY_INDEX = "Blue";

    @Test
    public void whenUsingDefualtConstructor_correctSizeQueue() {
        CircularFifoQueue<String> bits = new CircularFifoQueue<>();

        Assert.assertEquals(DEFAULT_SIZE, bits.maxSize());
    }

    @Test
    public void givenAddElements_whenUsingIntConstructor_correctSizeQueue() {
        CircularFifoQueue<String> colors = new CircularFifoQueue<>(5);
        colors.add("Red");
        colors.add("Blue");
        colors.add("Green");
        colors.offer("White");
        colors.offer("Black");

        Assert.assertEquals(FIXED_SIZE, colors.maxSize());
    }

    @Test
    public void whenUsingCollectionConstructor_correctSizeQueue() {
        List<String> days = new ArrayList<>();
        days.add("Monday");
        days.add("Tuesday");
        days.add("Wednesday");
        days.add("Thursday");
        days.add("Friday");
        days.add("Saturday");
        days.add("Sunday");

        CircularFifoQueue<String> daysOfWeek = new CircularFifoQueue<>(days);

        Assert.assertEquals(COLLECTION_SIZE, daysOfWeek.maxSize());
    }

    @Test
    public void givenAddElements_whenGetElement_correctElement() {
        CircularFifoQueue<String> colors = new CircularFifoQueue<>(5);
        colors.add("Red");
        colors.add("Blue");
        colors.add("Green");
        colors.offer("White");
        colors.offer("Black");

        Assert.assertEquals(TEST_COLOR_BY_INDEX, colors.get(1));
    }

    @Test
    public void givenAddElements_whenPollElement_correctElement() {
        CircularFifoQueue<String> colors = new CircularFifoQueue<>(5);
        colors.add("Red");
        colors.add("Blue");
        colors.add("Green");
        colors.offer("White");
        colors.offer("Black");

        Assert.assertEquals(TEST_COLOR, colors.poll());
    }

    @Test
    public void givenAddElements_whenPeekQueue_correctElement() {
        CircularFifoQueue<String> colors = new CircularFifoQueue<>(5);
        colors.add("Red");
        colors.add("Blue");
        colors.add("Green");
        colors.offer("White");
        colors.offer("Black");

        Assert.assertEquals(TEST_COLOR, colors.peek());
    }

    @Test
    public void givenAddElements_whenElementQueue_correctElement() {
        CircularFifoQueue<String> colors = new CircularFifoQueue<>(5);
        colors.add("Red");
        colors.add("Blue");
        colors.add("Green");
        colors.offer("White");
        colors.offer("Black");

        Assert.assertEquals(TEST_COLOR, colors.element());
    }

    @Test
    public void givenAddElements_whenRemoveElement_correctElement() {
        CircularFifoQueue<String> colors = new CircularFifoQueue<>(5);
        colors.add("Red");
        colors.add("Blue");
        colors.add("Green");
        colors.offer("White");
        colors.offer("Black");

        Assert.assertEquals(TEST_COLOR, colors.remove());
    }

    @Test
    public void givenFullQueue_whenClearQueue_getIsEmpty() {
        CircularFifoQueue<String> colors = new CircularFifoQueue<>(5);
        colors.add("Red");
        colors.add("Blue");
        colors.add("Green");
        colors.offer("White");
        colors.offer("Black");

        colors.clear();

        Assert.assertEquals(true, colors.isEmpty());
    }

    @Test
    public void givenFullQueue_whenCheckFull_getIsFull() {
        CircularFifoQueue<String> colors = new CircularFifoQueue<>(5);
        colors.add("Red");
        colors.add("Blue");
        colors.add("Green");
        colors.offer("White");
        colors.offer("Black");

        Assert.assertEquals(false, colors.isFull());
    }

    @Test
    public void givenFullQueue_whenAddMoreElements_getIsAtFullCapacity() {
        CircularFifoQueue<String> colors = new CircularFifoQueue<>(5);
        colors.add("Red");
        colors.add("Blue");
        colors.add("Green");
        colors.offer("White");
        colors.offer("Black");

        colors.add("Orange");
        colors.add("Violet");
        colors.add("Pink");

        Assert.assertEquals(true, colors.isAtFullCapacity());
    }

}
