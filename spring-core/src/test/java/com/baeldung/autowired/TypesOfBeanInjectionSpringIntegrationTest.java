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
    UserService userService;

    private static final String[] expected = new String[] { "Snoopy", "Woodstock", "Charlie Brown" };

    @Test
    public void givenDI_whenInjectObject_thenUserNamesAreListed() {
        Assert.assertArrayEquals(expected, userService.listUsers()
            .stream()
            .map(User::getName)
            .toArray());
    }
}
