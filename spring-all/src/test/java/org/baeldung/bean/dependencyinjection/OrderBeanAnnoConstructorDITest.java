package org.baeldung.bean.dependencyinjection;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderBeanAnnoConstructorDITest {

    @Test
    public void givenConfigDI_whenUsingConstructorDI_thenExpectedProductIdAndDesc() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(DIConfig.class);

        OrderBeanAnnoConstructorDI orderBean = (OrderBeanAnnoConstructorDI) applicationContext.getBean(OrderBeanAnnoConstructorDI.class);

        Assert.assertEquals(100, 
          orderBean.getProduct().getProductId());
        Assert.assertEquals("OCP Study Guide",
          orderBean.getProduct().getProductDesc());
    }

}
