package com.baeldung.beaninjectiontypes;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.assertNotNull;

public class FooTest {

    @Test
    public void givenXmlContext_WhenSetOnConstructor_ThenDependencyExists() {

      ApplicationContext context = new ClassPathXmlApplicationContext("bit-applicationContext.xml");
      FooService fooService = (FooService) context.getBean("fooService");

      assertNotNull(fooService.getFooCollaborator1());
    }

    @Test
    public void givenXmlContext_WhenSetOnSetter_ThenDependencyExists() {

      ApplicationContext context = new ClassPathXmlApplicationContext("bit-applicationContext.xml");
      FooService fooService = (FooService) context.getBean("fooService");

      assertNotNull(fooService.getFooCollaborator2());
    }

}