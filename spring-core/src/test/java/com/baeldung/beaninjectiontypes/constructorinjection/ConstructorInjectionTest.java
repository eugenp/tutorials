package com.baeldung.beaninjectiontypes.constructorinjection;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConstructorInjectionTest {

    @Test
    public void givenXMLConfiguration_WhenConstructorArgPassed_ThenDependencyValid() {
        ApplicationContext context = new ClassPathXmlApplicationContext("typesofdi-context.xml");
        User user = (User) context.getBean("userWithCI");

        String expectedOutput = "User Address: California, USA";

        assertTrue(expectedOutput.equals(user.toString()));
    }

    @Test
    public void givenPOJOConfiguration_WhenConstructorArgPassed_ThenDependencyValid() {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        User user = (User) context.getBean(User.class);

        String expectedOutput = "User Address: New York City, USA";

        assertTrue(expectedOutput.equals(user.toString()));
    }

}
