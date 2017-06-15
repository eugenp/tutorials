package org.baeldung.bean.injection;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConstructorBasedInjectionWithXmlConfigTest {
	
	private static final String WHEEL_NAME = "WheelBrand";

    @Test
    public void givenXMLConfigFile_whenUsingConstructorBasedBeanInjection_thenCorrectWheelName() {
        final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beanConstructorInjection.xml");

        final Car carConstructorBean = (Car) applicationContext.getBean("car");
        Assert.assertEquals(WHEEL_NAME, carConstructorBean.getWheel().getBrandOfWheel());
    }

}
