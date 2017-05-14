package com.baeldung.bean;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserServiceBeanInjectionTest {

    private static ApplicationContext applicationContext;

    @BeforeClass
    public static void init() {
        applicationContext = new ClassPathXmlApplicationContext("application-context-injection-example.xml");
    }

    @Test
    public void testConstructorInjection() {
        UserService userService = (UserService) applicationContext.getBean("userService1");
        Assert.assertEquals(userService.getName(), "goodname");
        Assert.assertEquals(userService.getValue(), 0);
        Assert.assertNull(userService.getLastName());
        Assert.assertNull(userService.getUserRepository());
    }

    @Test
    public void testConstructorInjectionInt() {
        UserService userService = (UserService) applicationContext.getBean("userService2");
        Assert.assertNull(userService.getName());
        Assert.assertEquals(userService.getValue(), 100);
        Assert.assertNull(userService.getLastName());
        Assert.assertNull(userService.getUserRepository());
    }

    @Test
    public void testTwoArgConstructorInjection() {
        UserService userService = (UserService) applicationContext.getBean("userService3");
        Assert.assertEquals(userService.getValue(), 0);
        Assert.assertEquals(userService.getName(), "name1");
        Assert.assertEquals(userService.getLastName(), "name2");
        Assert.assertNull(userService.getUserRepository());
    }

    @Test
    public void testTwoArgConstructorInjectionWithIndexPos() {
        UserService userService = (UserService) applicationContext.getBean("userService4");
        Assert.assertEquals(userService.getValue(), 0);
        Assert.assertEquals(userService.getName(), "name2");
        Assert.assertEquals(userService.getLastName(), "name1");
        Assert.assertNull(userService.getUserRepository());
    }

    @Test
    public void testConstructorInjectionWithRef() {
        UserService userService = (UserService) applicationContext.getBean("userService5");
        Assert.assertEquals(userService.getValue(), 0);
        Assert.assertNull(userService.getName());
        Assert.assertNull(userService.getLastName());
        Assert.assertNotNull(userService.getUserRepository());
    }

    @Test
    public void testConstructorInjectionWithInnerBean() {
        UserService userService = (UserService) applicationContext.getBean("userService6");
        Assert.assertEquals(userService.getValue(), 0);
        Assert.assertNull(userService.getName());
        Assert.assertNull(userService.getLastName());
        Assert.assertNotNull(userService.getUserRepository());
    }

    //setter injection tests
    @Test
    public void testSetterInjection() {
        UserService userService = (UserService) applicationContext.getBean("userService7");
        Assert.assertEquals(userService.getName(), "myname");
        Assert.assertEquals(userService.getValue(), 0);
        Assert.assertNull(userService.getLastName());
        Assert.assertNull(userService.getUserRepository());
    }

    @Test
    public void testSetterInjectionWithRef() {
        UserService userService = (UserService) applicationContext.getBean("userService8");
        Assert.assertNull(userService.getName());
        Assert.assertEquals(userService.getValue(), 0);
        Assert.assertNull(userService.getLastName());
        Assert.assertNotNull(userService.getUserRepository());
    }

}
