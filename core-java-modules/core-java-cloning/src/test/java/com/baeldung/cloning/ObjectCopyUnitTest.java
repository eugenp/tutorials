package com.baeldung.cloning;

import java.util.ArrayDeque;
import java.util.Deque;

import static org.junit.Assert.*;
import org.junit.Test;

public class ObjectCopyUnitTest {

    @Test
    public void simpleObjectCopy() {

        // Given
        String original = new String("Object");
        String copy = null;

        // When
        copy = original;                        // Does not copy object but only its reference

        // Then
        assertEquals(copy, original);           // Same Object Reference
    }
}