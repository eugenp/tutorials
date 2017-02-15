package org.baeldung.bean.injection;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SetterBasedBeanInjectionTest {
	
	private static final String HELM_NAME = "HelmBrand";	    
	 
    @Test
    public void testConstructorScope() {
        final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beanInjection-setter.xml");
 
        final Ship shipSetterBean = (Ship) applicationContext.getBean("ship");
        Assert.assertEquals(HELM_NAME, shipSetterBean.getHelm().getBrandOfHelm());
    }
}
