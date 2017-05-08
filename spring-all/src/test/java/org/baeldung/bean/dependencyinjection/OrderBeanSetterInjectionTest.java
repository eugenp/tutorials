package org.baeldung.bean.dependencyinjection;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class OrderBeanSetterInjectionTest {

    @Test
    public void givenBeanConfig_whenUsingSetterDI_thenExpectedProductIdAndDesc() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("dependencyInjectionForSetter.xml");

        OrderBeanSetterInjection orderBean = (OrderBeanSetterInjection) applicationContext.getBean("order");

        Assert.assertEquals(100, orderBean.getProduct().getProductId());
        Assert.assertEquals("OCP Study Guide", orderBean.getProduct().getProductDesc());
    }
}
