package com.baeldung.array.conversions.classcastexception;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Array;
import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassCastExceptionUnitTest {

    private static Logger LOG = LoggerFactory.getLogger(ClassCastExceptionUnitTest.class);

    Integer[] convertObjectArray() {
        Object[] objArray = new Object[3];
        objArray[0] = 1;
        objArray[1] = 2;
        objArray[2] = 3;
        return (Integer[]) objArray;
    }

    Integer[] getIntegerArray() {
        Integer[] intArray = new Integer[3];
        intArray[0] = 1;
        intArray[1] = 2;
        intArray[2] = 3;
        return intArray;
    }

    Integer[] objArrayToIntArray() {
        Object[] objArray = new Object[] { 1, 2, 3 };
        Integer[] intArray = new Integer[objArray.length];
        for (int i = 0; i < objArray.length; i++) {
            intArray[i] = (Integer) objArray[i];
        }
        return intArray;
    }

    <T> T[] convertFromObjectArray(Class<T> clazz, Object[] objArray) {
        T[] targetArray = (T[]) Array.newInstance(clazz, objArray.length);
        for (int i = 0; i < objArray.length; i++) {
            if (clazz.isInstance(objArray[i])) {
                targetArray[i] = clazz.cast(objArray[i]);
            } else {
                throw new ClassCastException("Element #" + i + ": Cannot cast " + objArray[i].getClass()
                    .getName() + " to " + clazz.getName());
            }
        }
        return targetArray;
    }

    @Test
    void whenCallingConvertObjectArray_thenClassCastException() {
        Exception ex = assertThrows(ClassCastException.class, this::convertObjectArray);
        LOG.error("The exception stacktrace:", ex);
    }

    @Test
    void whenCallingIntegerArray_thenCorrect() {
        assertArrayEquals(new Integer[] { 1, 2, 3 }, getIntegerArray());
    }

    @Test
    void whenCallingObjArrayToIntArray_thenCorrect() {
        assertArrayEquals(new Integer[] { 1, 2, 3 }, objArrayToIntArray());
    }

    @Test
    void whenCallingSingleElementArray_thenCorrect() {
        assertArrayEquals(new Integer[] { 1, 2, 3 }, convertFromObjectArray(Integer.class, new Object[] { 1, 2, 3 }));
        assertArrayEquals(new String[] { "I'm Kai", "I'm Liam", "I'm Kevin" },
            convertFromObjectArray(String.class, new Object[] { "I'm Kai", "I'm Liam", "I'm Kevin" }));
        //class cast ex:
        Exception ex = assertThrows(ClassCastException.class,
            () -> convertFromObjectArray(String.class, new Object[] { "I'm Kai", Instant.now(), "I'm Kevin" }));
        assertEquals("Element #1: Cannot cast java.time.Instant to java.lang.String", ex.getMessage());
    }

}