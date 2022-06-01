package com.baeldung.cloning;

import java.util.ArrayDeque;
import java.util.Deque;

import static org.junit.Assert.*;
import org.junit.Test;

public class JavaCopyUnitTest {

    @Test(expected = CloneNotSupportedException.class)
    public void cloneNonCloneable() throws CloneNotSupportedException {

        // Given
        MyClass original = new MyClass(24, 'p', null);

        // When
        MyClass copy = (MyClass) original.clone();

        // Then Exception Gets Thrown
    }

    @Test
    public void cloneCloneable() throws CloneNotSupportedException {

        // Given
        CloneableMyClass original = new CloneableMyClass(24, 'p', null);

        // When
        CloneableMyClass copy = (CloneableMyClass) original.clone();

        // Then
        // Distinct objects - 'copy' and 'original'
        assertFalse(original.equals(copy));
        // Same Primitive values
        assertEquals(original.getI(), copy.getI());
        assertEquals(original.getC(), copy.getC());
        // Same Object Reference
        assertEquals(original.getO(), copy.getO());
    }

    @Test
    public void copyConstructorNonCloneable() {

        // Given
        MyClass original = new MyClass(24, 'p', null);

        // When
        MyClass copy = new MyClass(original);   // Invocation of copy constructor

        // Then
        // Distinct objects - 'copy' and 'original'
        assertFalse(original.equals(copy));
        // Same Primitive values
        assertEquals(original.getI(), copy.getI());
        assertEquals(original.getC(), copy.getC());
        // Same Object Reference
        assertEquals(original.getO(), copy.getO());
    }

    @Test
    public void copyConstructorCloneable() {

        // Given
        CloneableMyClass original = new CloneableMyClass(24, 'p', null);

        // When
        CloneableMyClass copy = new CloneableMyClass(original);   // Invocation of copy constructor

        // Then
        // Distinct objects - 'copy' and 'original'
        assertFalse(original.equals(copy));
        // Same Primitive values
        assertEquals(original.getI(), copy.getI());
        assertEquals(original.getC(), copy.getC());
        // Same Object Reference
        assertEquals(original.getO(), copy.getO());
    }
}