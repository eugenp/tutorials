package com.baeldung.overridemethod.proxy.jdk;

import com.baeldung.overridemethod.Calculator;
import com.baeldung.overridemethod.SimpleCalculator;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Proxy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DynamicProxyTest {

    @Test
    void testJdkDynamicProxy() {
        Calculator simpleCalc = new SimpleCalculator();
        LoggingInvocationHandler handler = new LoggingInvocationHandler(simpleCalc);

        Calculator proxyCalc = (Calculator) Proxy.newProxyInstance(
            Calculator.class.getClassLoader(),
            new Class<?>[]{Calculator.class},
            handler
        );

        assertEquals(30, proxyCalc.add(20, 10));
    }
}

