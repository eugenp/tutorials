package com.baeldung.circularbuffer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CircularBufferUnitTest {

    private final String[] shapes = { "Circle", "Triangle", "Rectangle", "Square", "Rhombus", "Trapezoid", "Pentagon", "Pentagram", "Hexagon", "Hexagram" };

    @Test
    public void givenCircularBuffer_whenAnElementIsAddedAndRemoved_thenBufferIsEmpty() {

        int capacity = 2;
        CircularBuffer<String> buffer = new CircularBuffer<>(capacity);

        assertEquals(capacity, buffer.capacity());

        buffer.offer("Rectangle");

        assertFalse(buffer.isEmpty());
        assertEquals(1, buffer.size());

        buffer.poll();

        assertTrue(buffer.isEmpty());
    }

    @Test
    public void givenCircularBuffer_whenFilledToCapacity_thenNoMoreElementsCanBeAdded() {

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
