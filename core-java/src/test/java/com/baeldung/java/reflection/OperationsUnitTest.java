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

    @Test(expected=IllegalAccessException.class)
    public void givenObject_whenInvokePrivatedMethod_thenFail() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        Method andInstanceMethod = Operations.class.getDeclaredMethod("and", boolean.class, boolean.class);

        Operations operationsInstance = new Operations();
        Boolean result = (Boolean)andInstanceMethod.invoke(operationsInstance, true, false);

        assertFalse(result);
    }

    @Test
    public void givenObject_whenInvokePrivateMethod_thenCorrect() throws Exception {
        Method andInstanceMethod = Operations.class.getDeclaredMethod("and", boolean.class, boolean.class);
        andInstanceMethod.setAccessible(true);

        Operations operationsInstance = new Operations();
        Boolean result = (Boolean)andInstanceMethod.invoke(operationsInstance, true, false);

        assertFalse(result);
    }

    @Test
    public void givenObject_whenInvokePublicMethod_thenCorrect() throws Exception {
        Method sumInstanceMethod = Operations.class.getMethod("sum", int.class, double.class);

        Operations operationsInstance = new Operations();
        Double result = (Double)sumInstanceMethod.invoke(operationsInstance, 1, 3);
        
        assertThat(result, equalTo(4.0));
    }
    
    @Test
    public void givenObject_whenInvokeStaticMethod_thenCorrect() throws Exception {
        Method multiplyStaticMethod = Operations.class.getDeclaredMethod("multiply",float.class, long.class);

        Double result = (Double)multiplyStaticMethod.invoke(null, 3.5f, 2);
        
        assertThat(result, equalTo(7.0));
    }

}
