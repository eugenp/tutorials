package com.baeldung.cloning;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayDeque;
import java.util.Deque;
import org.apache.commons.beanutils.BeanUtils;

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

    @Test
    public void beanUtilsClone() throws InvocationTargetException, NoSuchMethodException,
                                        InstantiationException, IllegalAccessException {

        // Given
        MyClass originalMyClass = new MyClass(24, 'p', null);
        CloneableMyClass originalCloneableMyClass = new CloneableMyClass(24, 'p', null);

        // When
        MyClass copyMyClass = (MyClass) BeanUtils.cloneBean(originalMyClass);
        CloneableMyClass copyCloneableMyClass = (CloneableMyClass) BeanUtils.cloneBean(originalCloneableMyClass);

        // Then
        // Distinct objects - 'copy' and 'original'
        assertFalse(originalMyClass.equals(copyMyClass));
        assertFalse(originalCloneableMyClass.equals(copyCloneableMyClass));
        // Same Primitive values
        assertEquals(originalMyClass.getI(), copyMyClass.getI());
        assertEquals(originalMyClass.getC(), copyMyClass.getC());
        assertEquals(originalCloneableMyClass.getC(), copyCloneableMyClass.getC());
        assertEquals(originalCloneableMyClass.getC(), copyCloneableMyClass.getC());
        // Same Object Reference
        assertEquals(originalMyClass.getO(), copyMyClass.getO());
        assertEquals(originalCloneableMyClass.getO(), copyCloneableMyClass.getO());
    }
}