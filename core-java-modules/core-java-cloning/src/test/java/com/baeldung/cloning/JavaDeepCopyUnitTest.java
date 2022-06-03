package com.baeldung.cloning;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayDeque;
import java.util.Deque;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.SerializationUtils;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Ignore;

public class JavaDeepCopyUnitTest {

    @Test
    public void deepCopyViaCloneMethod() throws CloneNotSupportedException {

        // Given
        Ref2 objRef2 = new Ref2();
        Ref1 objRef1 = new Ref1(objRef2);
        DeepCloneableMyClass original = new DeepCloneableMyClass(24, 'p', objRef1);

        // When
        DeepCloneableMyClass copy = (DeepCloneableMyClass) original.clone();

        // Then
        // Distinct objects - 'copy' and 'original'
        assertNotEquals(original, copy);
        // Same Primitive values
        assertEquals(original.getI(), copy.getI());
        assertEquals(original.getC(), copy.getC());
        // Distinct Nested Object References
        assertNotEquals(original.getO(), copy.getO());
        assertNotEquals(original.getO().getP(), copy.getO().getP());
    }

    @Test
    public void deepCopyViaCopyConstructor() {

        // Given
        DeepCloneableMyClass original = new DeepCloneableMyClass(24, 'p', new Ref1(null));

        // When
        DeepCloneableMyClass copy = new DeepCloneableMyClass(original);   // Invocation of copy constructor

        // Then
        // Distinct objects - 'copy' and 'original'
        assertNotEquals(original, copy);
        // Same Primitive values
        assertEquals(original.getI(), copy.getI());
        assertEquals(original.getC(), copy.getC());
        // Distinct Nested Object References
        assertNotEquals(original.getO(), copy.getO());
        assertNotEquals(original.getO().getP(), copy.getO().getP());
    }
}