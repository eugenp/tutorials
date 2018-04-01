package com.baeldung.beaninjectiontypes.setterinjection;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SetterInjectionTest {

    @Test
    public void givenXMLConfiguration_WhenSetterArgPassed_ThenDependencyValid() {
        ApplicationContext context = new ClassPathXmlApplicationContext("typesofdi-context.xml");
        User user = (User) context.getBean("userWithSI");

        String expectedOutput = "User Address : Washington D.C., USA";

        assertTrue(expectedOutput.equals(user.toString()));
    }

    @Test
    public void givenPOJOConfiguration_WhenSetterArgPassed_ThenDependencyValid() {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        User user = (User) context.getBean(User.class);

        String expectedOutput = "User Address : Los Angeles, USA";

        assertTrue(expectedOutput.equals(user.toString()));
    }

}
