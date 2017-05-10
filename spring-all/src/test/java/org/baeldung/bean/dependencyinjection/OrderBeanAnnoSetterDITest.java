package org.baeldung.bean.dependencyinjection;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderBeanAnnoSetterDITest {
    
    @Test
    public void givenConfigDI_whenUsingSetterDI_thenExpectedProductIdAndDesc() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(DIConfig.class);

        OrderBeanAnnoSetterDI orderBean = (OrderBeanAnnoSetterDI) applicationContext.getBean(OrderBeanAnnoSetterDI.class);

        Assert.assertEquals(100, 
          orderBean.getProduct().getProductId());
        Assert.assertEquals("OCP Study Guide",
          orderBean.getProduct().getProductDesc());
    }
}
