package com.baeldung.autowired;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TypesOfBeanInjectionSpringIntegrationTest {
    @Autowired
    UserControllerConstructorInjection userControllerConstructorInjectionTest;

    @Autowired
    UserControllerSetterInjection userControllerSetterInjectionTest;

    @Autowired
    UserControllerFieldInjection userControllerFieldInjectionTest;

    private static final String[] expected = new String[] { "Snoopy", "Woodstock", "Charlie Brown" };

    @Test
    public void givenConstructorInjection_whenInjectObject_thenUserNamesAreListed() {
        Assert.assertArrayEquals(expected, userControllerConstructorInjectionTest.listUsers()
            .stream()
            .map(User::getName)
            .toArray());
    }

    @Test
    public void givenSetterInjection_whenInjectObject_thenUserNamesAreListed() {
        Assert.assertArrayEquals(expected, userControllerSetterInjectionTest.listUsers()
            .stream()
            .map(User::getName)
            .toArray());
    }

    @Test
    public void givenFieldInjection_whenInjectObject_thenUserNamesAreListed() {
        Assert.assertArrayEquals(expected, userControllerFieldInjectionTest.listUsers()
            .stream()
            .map(User::getName)
            .toArray());
    }

}
