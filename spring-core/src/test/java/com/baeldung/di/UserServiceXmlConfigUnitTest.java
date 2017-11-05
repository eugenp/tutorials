package com.baeldung.di;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
public class UserServiceXmlConfigUnitTest {
    @Autowired
    private UserService userService;

    @Test
    public void givenApplicationContext_whenUserServiceIsInstantiated_thenDependenciesShouldNotBeNull() {
        assertNotNull(userService);
        assertNotNull(userService.getUserRepository());
    }
}