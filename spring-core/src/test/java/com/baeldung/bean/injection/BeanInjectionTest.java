package com.baeldung.bean.injection;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.bean.injection.constructor.annotation.Config;
import com.baeldung.bean.injection.constructor.annotation.User;

public class BeanInjectionTest {

    @Test
    public void givenAutowiredAnnotation_WhenSetOnConstructor_ThenDependencyValid() {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        User user = context.getBean("user", User.class);

        assertTrue(user.getOrganization()
            .getName()
            .equals("Baeldung"));
    }

    @Test
    public void givenAutowiredAnnotationUsingXml_WhenSetOnConstructor_ThenDependencyValid() {

        final ApplicationContext context = new ClassPathXmlApplicationContext("bean-injection/constructor-bean-injection.xml");

        com.baeldung.bean.injection.constructor.xml.User user = context.getBean("user", com.baeldung.bean.injection.constructor.xml.User.class);

        assertTrue(user.getOrganization()
            .getName()
            .equals("Baeldung"));
    }

    @Test
    public void givenAutowiredAnnotation_WhenSetOnSetter_ThenDependencyValid() {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(com.baeldung.bean.injection.setter.annotation.Config.class);
        com.baeldung.bean.injection.setter.annotation.User user = context.getBean("user", com.baeldung.bean.injection.setter.annotation.User.class);

        assertTrue(user.getOrganization()
            .getName()
            .equals("Baeldung"));
    }

    @Test
    public void givenAutowiredAnnotationUsingXml_WhenSetOnSetter_ThenDependencyValid() {

        final ApplicationContext context = new ClassPathXmlApplicationContext("bean-injection/setter-bean-injection.xml");

        com.baeldung.bean.injection.setter.xml.User user = context.getBean("user", com.baeldung.bean.injection.setter.xml.User.class);

        assertTrue(user.getOrganization()
            .getName()
            .equals("Baeldung"));
    }

}
