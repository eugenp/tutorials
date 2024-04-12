package com.baeldung.circularbuffer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CircularBufferUnitTest {

    private final String[] shapes = { "Circle", "Triangle", "Rectangle", "Square", "Rhombus", "Trapezoid", "Pentagon", "Pentagram", "Hexagon", "Hexagram" };
    private final int defaultCapacity = shapes.length;

    @Test
    public void givenCircularBuffer_whenAnElementIsEnqueued_thenSizeIsOne() {
        CircularBuffer<String> buffer = new CircularBuffer<>(defaultCapacity);

        assertTrue(buffer.offer("Square"));
        assertEquals(1, buffer.size());
    }

    @Test
    public void givenCircularBuffer_whenAnElementIsDequeued_thenElementMatchesEnqueuedElement() {
        CircularBuffer<String> buffer = new CircularBuffer<>(defaultCapacity);

        buffer.offer("Triangle");

        String shape = buffer.poll();
        assertEquals("Triangle", shape);
    }

    @Test
    public void givenCircularBuffer_whenAnElementIsEnqueuedAndDeququed_thenBufferIsEmpty() {

        CircularBuffer<String> buffer = new CircularBuffer<>(defaultCapacity);

        buffer.offer("Rectangle");

        assertFalse(buffer.isEmpty());
        assertEquals(1, buffer.size());

        buffer.poll();

        assertTrue(buffer.isEmpty());
    }

    @Test
    public void givenCircularBuffer_whenFilledToCapacity_thenNoMoreElementsCanBeEnqueued() {

        int capacity = shapes.length;
        CircularBuffer<String> buffer = new CircularBuffer<>(capacity);

        assertTrue(buffer.isEmpty());

        for (String shape : shapes) {
            buffer.offer(shape);
        }

        assertTrue(buffer.isFull());
        assertFalse(buffer.offer("Octagon"));
    }

    @Test
    public void givenCircularBuffer_whenBufferIsEmpty_thenReturnsNull() {

        CircularBuffer<String> buffer = new CircularBuffer<>(1);

        assertTrue(buffer.isEmpty());
        assertNull(buffer.poll());
    }
}
