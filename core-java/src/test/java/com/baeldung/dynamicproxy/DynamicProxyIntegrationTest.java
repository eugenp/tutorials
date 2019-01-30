package com.baeldung.dynamicproxy;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * 动态代理继承测试
 */
public class DynamicProxyIntegrationTest {

    /**
     * 测试invoke方法
     */
    @Test
    public void givenDynamicProxy_thenPutWorks() {

        Map proxyInstance = (Map) Proxy.newProxyInstance(
                DynamicProxyIntegrationTest.class.getClassLoader(),
                new Class[] { Map.class },
                new DynamicInvocationHandler()
        );

        proxyInstance.put("hello", "world");
        proxyInstance.clear();
    }

    /**
     * 测试匹配的invoke方法
     */
    @Test
    public void givenInlineDynamicProxy_thenGetWorksOtherMethodsDoNot() {
        Map proxyInstance = (Map) Proxy.newProxyInstance(
                DynamicProxyIntegrationTest.class.getClassLoader(),
                new Class[] { Map.class },
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] methodArgs) throws Throwable {

                        if (method.getName().equals("get")) {
                            return 42;
                        }
                        else {
                            throw new UnsupportedOperationException("Unsupported method: " + method.getName());
                        }

                    }
                });

        int result = (int) proxyInstance.get("hello");

        Assert.assertEquals(42, result);

        try {
            proxyInstance.put("hello", "world");
            fail();
        }
        catch(UnsupportedOperationException e) {
            // expected
            e.printStackTrace();
        }
    }

    /**
     * 测试动态代理invoke耗时时间
     */
    @Test
    public void givenTimingDynamicProxy_thenMethodInvokationsProduceTiming() {
        Map mapProxyInstance = (Map) Proxy.newProxyInstance(
                DynamicProxyIntegrationTest.class.getClassLoader(),
                new Class[] { Map.class },
                new TimingDynamicInvocationHandler(new HashMap<>())
        );

        mapProxyInstance.put("hello", "world");
        Assert.assertEquals("world", mapProxyInstance.get("hello"));

        CharSequence csProxyInstance = (CharSequence)
                Proxy.newProxyInstance(DynamicProxyIntegrationTest.class.getClassLoader(),
                        new Class[] { CharSequence.class },
                        new TimingDynamicInvocationHandler("Hello World")
                );
        System.out.println("csProxyInstance:{}" + csProxyInstance);

        Assert.assertEquals('l', csProxyInstance.charAt(2));
        Assert.assertEquals(11, csProxyInstance.length());
    }

}
