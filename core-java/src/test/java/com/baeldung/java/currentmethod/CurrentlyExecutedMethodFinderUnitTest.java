package com.baeldung.java.currentmethod;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * The class presents various ways of finding the name of currently executed method.
 * 测试：该类提供了各种方法来查找当前执行的方法的名称。
 */
public class CurrentlyExecutedMethodFinderUnitTest {

    @Test
    public void givenCurrentThread_whenGetStackTrace_thenFindMethod() {
        final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for(int i = 0 ;i < stackTrace.length ; i++){
            System.out.println("StackTraceElement:{}" + stackTrace[i].getMethodName());
        }
        assertEquals("givenCurrentThread_whenGetStackTrace_thenFindMethod", stackTrace[1].getMethodName());
    }

    @Test
    public void givenException_whenGetStackTrace_thenFindMethod() {
        String methodName = new Exception().getStackTrace()[0].getMethodName();
        System.out.println("methodName:{}" + methodName);
        assertEquals("givenException_whenGetStackTrace_thenFindMethod", methodName);
    }

    @Test
    public void givenThrowable_whenGetStacktrace_thenFindMethod() {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        System.out.println("stackTrace[0].getMethodName():{}" + stackTrace[0].getMethodName());
        assertEquals("givenThrowable_whenGetStacktrace_thenFindMethod", stackTrace[0].getMethodName());
    }

    @Test
    public void givenObject_whenGetEnclosingMethod_thenFindMethod() {
        String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
        System.out.println("methodName:{}" + methodName);
        assertEquals("givenObject_whenGetEnclosingMethod_thenFindMethod", methodName);
    }

    @Test
    public void givenLocal_whenGetEnclosingMethod_thenFindMethod() {
        class Local {};
        String methodName = Local.class.getEnclosingMethod().getName();
        System.out.println("methodName:{}" + methodName);
        assertEquals("givenLocal_whenGetEnclosingMethod_thenFindMethod", methodName);
    }

}
