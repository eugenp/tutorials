package org.baeldung.bean.dependencyinjection;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class OrderBeanXMLConstructorDITest {

    @Test
    public void givenBeanConfig_whenUsingConstructorDI_thenExpectedProductIdAndDesc() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("dependencyInjectionForConstructor.xml");

        OrderBeanXMLConstructorDI orderBean = (OrderBeanXMLConstructorDI) applicationContext.getBean("order");

        Assert.assertEquals(100, orderBean.getProduct().getProductId());
        Assert.assertEquals("OCP Study Guide", orderBean.getProduct().getProductDesc());
    }
}
