package org.baeldung.bean.injection;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SetterBasedInjectionWithXmlConfigTest {
	
    private static final String WHEEl_NAME = "WheelBrand";

    @Test
    public void givenXMLConfigFile_whenUsingSetterBasedBeanInjection_thenCorrectWheelName() {
        final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beanSetterInjection.xml");

        final Car carSetterBean = (Car) applicationContext.getBean("car");
        Assert.assertEquals(WHEEl_NAME, carSetterBean.getWheel().getBrandOfWheel());
    }

}
