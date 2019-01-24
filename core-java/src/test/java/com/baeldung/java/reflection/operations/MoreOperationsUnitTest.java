package com.baeldung.java.reflection.operations;

import com.baeldung.java.reflection.*;
import static org.hamcrest.CoreMatchers.equalTo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;
import static org.junit.Assert.assertThat;

/**
 * 测试：三目运算
 */
public class MoreOperationsUnitTest {

    public MoreOperationsUnitTest() {
    }

    /**
     * 反射操作：受保护类型的方法
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    @Test(expected = IllegalAccessException.class)
    public void givenObject_whenInvokeProtectedMethod_thenFail() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method maxProtectedMethod = Operations.class.getDeclaredMethod("protectedMax", int.class, int.class);

        Operations operationsInstance = new Operations();
        try{
            Integer result = (Integer) maxProtectedMethod.invoke(operationsInstance, 2, 4);
            assertThat(result, equalTo(4));
        }
        catch (Throwable e){
            e.printStackTrace();
        }
    }

    /**
     * 需要使用{@link java.lang.reflect.AccessibleObject#setAccessible(boolean)}
     * @throws Exception
     */
    @Test
    public void givenObject_whenInvokeProtectedMethod_thenCorrect() throws Exception {
        Method maxProtectedMethod = Operations.class.getDeclaredMethod("protectedMax", int.class, int.class);
        maxProtectedMethod.setAccessible(true);

        Operations operationsInstance = new Operations();
        Integer result = (Integer) maxProtectedMethod.invoke(operationsInstance, 2, 4);

        assertThat(result, equalTo(4));
    }

}
