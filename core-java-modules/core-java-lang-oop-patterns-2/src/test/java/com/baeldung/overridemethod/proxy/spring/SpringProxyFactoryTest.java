package com.baeldung.overridemethod.proxy.spring;

import com.baeldung.overridemethod.Calculator;
import com.baeldung.overridemethod.SimpleCalculator;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpringProxyFactoryTest {

    @Test
    void givenACalculator_whenUsingSpringProxyFactory_thenSpringProxyFactoryCanBeUsed() {
        SimpleCalculator simpleCalc = new SimpleCalculator();
        ProxyFactory factory = new ProxyFactory();
        
        factory.setTarget(simpleCalc);
        factory.addAdvice(new LoggingMethodInterceptor());

        Calculator proxyCalc = (Calculator) factory.getProxy();

        assertEquals(60, proxyCalc.add(50, 10));
        assertEquals(40, proxyCalc.subtract(50, 10));
    }
}
