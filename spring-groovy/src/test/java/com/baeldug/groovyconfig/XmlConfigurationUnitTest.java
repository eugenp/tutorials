package com.baeldug.groovyconfig;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlConfigurationUnitTest {
       
    @Test
    public void whenXmlConfig_thenCorrectPerson() {
        final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("xml-bean-config.xml");
        
        JavaPersonBean j = (JavaPersonBean) applicationContext.getBean("JavaPersonBean");
        
        assertEquals("30", j.getAge());
        assertEquals("brown", j.getEyesColor());
        assertEquals("brown", j.getHairColor());
        
    }

}
