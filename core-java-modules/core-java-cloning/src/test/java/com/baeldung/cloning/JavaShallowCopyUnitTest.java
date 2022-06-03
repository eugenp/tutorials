package com.baeldung.cloning;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayDeque;
import java.util.Deque;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.SerializationUtils;

import static org.junit.Assert.*;
import org.junit.Test;

public class JavaShallowCopyUnitTest {

    @Test
    public void shallowCopyViaCloneMethod() throws CloneNotSupportedException {

        // Given
        CloneableMyClass original = new CloneableMyClass(24, 'p', new Ref1(null));

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
    public void shallowCopyViaCopyConstructor() {

        // Given
        CloneableMyClass original = new CloneableMyClass(24, 'p', new Ref1(null));

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