package com.baeldung.java.reflection.operations;

import com.baeldung.java.reflection.*;
import static org.hamcrest.CoreMatchers.equalTo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;
import static org.junit.Assert.assertThat;

public class MoreOperationsUnitTest {

    public MoreOperationsUnitTest() {
    }

    @Test(expected = IllegalAccessException.class)
    public void givenObject_whenInvokeProtectedMethod_thenFail() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method maxProtectedMethod = Operations.class.getDeclaredMethod("protectedMax", int.class, int.class);

        Operations operationsInstance = new Operations();
        Integer result = (Integer) maxProtectedMethod.invoke(operationsInstance, 2, 4);
        
        assertThat(result, equalTo(4));
    }

    @Test
    public void givenObject_whenInvokeProtectedMethod_thenCorrect() throws Exception {
        Method maxProtectedMethod = Operations.class.getDeclaredMethod("protectedMax", int.class, int.class);
        maxProtectedMethod.setAccessible(true);

        Operations operationsInstance = new Operations();
        Integer result = (Integer) maxProtectedMethod.invoke(operationsInstance, 2, 4);

        assertThat(result, equalTo(4));
    }

}
