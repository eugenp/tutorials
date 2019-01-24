package com.baeldung.java.reflection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

/**
 * 测试：反射操作举例
 */
public class OperationsUnitTest {

    public OperationsUnitTest() {
    }

    /**
     * 从这个例子中可以看出，使用{@link java.lang.reflect.Method#invoke(Object, Object...)}访问private修饰的方法，
     * 如果不设置isAccessible()为true，将会报错。
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    @Test(expected = IllegalAccessException.class)
    public void givenObject_whenInvokePrivateMethod_thenFail() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method andPrivateMethod = Operations.class.getDeclaredMethod("privateAnd", boolean.class, boolean.class);
        System.out.println("andPrivateMethod.isAccessible():{}" + andPrivateMethod.isAccessible());

        Operations operationsInstance = new Operations();
        try{
            Boolean result = (Boolean) andPrivateMethod.invoke(operationsInstance, true, false);
            System.out.println("result:{}" + result);
            assertFalse(result);
        }
        catch (Throwable e){
            e.printStackTrace();
        }
    }

    /**
     * 使用setAccessible设置为true时，通过方法的in
     * @throws Exception
     */
    @Test
    public void givenObject_whenInvokePrivateMethod_thenCorrect() throws Exception {
        Method andPrivatedMethod = Operations.class.getDeclaredMethod("privateAnd", boolean.class, boolean.class);
        andPrivatedMethod.setAccessible(true);

        Operations operationsInstance = new Operations();
        Boolean result = (Boolean) andPrivatedMethod.invoke(operationsInstance, true, false);
        System.out.println("result:{}" + result);

        assertFalse(result);
    }

    /**
     * 直接invoke的是public的方法的话，不需要设置setAccessible
     * @throws Exception
     */
    @Test
    public void givenObject_whenInvokePublicMethod_thenCorrect() throws Exception {
        Method sumInstanceMethod = Operations.class.getMethod("publicSum", int.class, double.class);

        Operations operationsInstance = new Operations();
        Double result = (Double) sumInstanceMethod.invoke(operationsInstance, 1, 3);
        System.out.println("result:{}" + result);
        assertThat(result, equalTo(4.0));
    }

    /**
     * invoke静态方法
     * @throws Exception
     */
    @Test
    public void givenObject_whenInvokeStaticMethod_thenCorrect() throws Exception {
        Method multiplyStaticMethod = Operations.class.getDeclaredMethod("publicStaticMultiply", float.class, long.class);

        Double result = (Double) multiplyStaticMethod.invoke(null, 3.5f, 2);
        System.out.println("result:{}" + result);

        assertThat(result, equalTo(7.0));
    }

}
