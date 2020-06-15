package com.baeldung.circularbuffer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CircularBufferUnitTest {

    private final String[] shapes = { "Circle", "Triangle", "Rectangle", "Square", "Rhombus", "Trapezoid", "Pentagon", "Pentagram", "Hexagon", "Hexagram" };

    @Test
    public void givenCircularBuffer_WhenAnElementIsAddedAndRemoved_thenBufferIsEmpty() {

        int capacity = 2;
        CircularBuffer<String> buffer = new CircularBuffer<>(capacity);

        assertEquals(capacity, buffer.capacity());

        buffer.offer("Rectangle");

        assertEquals(false, buffer.isEmpty());
        assertEquals(1, buffer.size());

        buffer.poll();

        assertEquals(true, buffer.isEmpty());
    }

    @Test
    public void givenCircularBuffer_WhenElementsAreAddedToCapacity_thenBufferIsFull() {

        int capacity = shapes.length;
        CircularBuffer<String> buffer = new CircularBuffer<>(capacity);

        assertEquals(true, buffer.isEmpty());

        for (String shape : shapes) {
            buffer.offer(shape);
        }

        assertEquals(true, buffer.isFull());
    }
}
