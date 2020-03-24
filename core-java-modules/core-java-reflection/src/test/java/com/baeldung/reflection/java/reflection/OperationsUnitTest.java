package com.baeldung.java.reflection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

public class OperationsUnitTest {

    public OperationsUnitTest() {
    }

    @Test(expected = IllegalAccessException.class)
    public void givenObject_whenInvokePrivateMethod_thenFail() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method andPrivateMethod = Operations.class.getDeclaredMethod("privateAnd", boolean.class, boolean.class);

        Operations operationsInstance = new Operations();
        Boolean result = (Boolean) andPrivateMethod.invoke(operationsInstance, true, false);

        assertFalse(result);
    }

    @Test
    public void givenObject_whenInvokePrivateMethod_thenCorrect() throws Exception {
        Method andPrivatedMethod = Operations.class.getDeclaredMethod("privateAnd", boolean.class, boolean.class);
        andPrivatedMethod.setAccessible(true);

        Operations operationsInstance = new Operations();
        Boolean result = (Boolean) andPrivatedMethod.invoke(operationsInstance, true, false);

        assertFalse(result);
    }

    @Test
    public void givenObject_whenInvokePublicMethod_thenCorrect() throws Exception {
        Method sumInstanceMethod = Operations.class.getMethod("publicSum", int.class, double.class);

        Operations operationsInstance = new Operations();
        Double result = (Double) sumInstanceMethod.invoke(operationsInstance, 1, 3);

        assertThat(result, equalTo(4.0));
    }

    @Test
    public void givenObject_whenInvokeStaticMethod_thenCorrect() throws Exception {
        Method multiplyStaticMethod = Operations.class.getDeclaredMethod("publicStaticMultiply", float.class, long.class);

        Double result = (Double) multiplyStaticMethod.invoke(null, 3.5f, 2);

        assertThat(result, equalTo(7.0));
    }

}
