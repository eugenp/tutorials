package org.baeldung.bean.injection;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConstructorBasedBeanInjectionTest {
	
	private static final String HELM_NAME = "HelmBrand";	    
	 
    @Test
    public void testConstructorScope() {
        final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beanInjection-constructor.xml");
 
        final Ship shipConstructorBean = (Ship) applicationContext.getBean("ship");
        Assert.assertEquals(HELM_NAME, shipConstructorBean.getHelm().getBrandOfHelm());
    }
}
