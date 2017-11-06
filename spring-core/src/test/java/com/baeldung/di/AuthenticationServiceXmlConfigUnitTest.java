package com.baeldung.di;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
public class AuthenticationServiceXmlConfigUnitTest {
    @Autowired
    private AuthenticationService authenticationService;

    @Test
    public void givenApplicationContext_whenAuthenticationServiceIsInstantiated_thenDependenciesShouldNotBeNull() {
        assertNotNull(authenticationService);
        assertNotNull(authenticationService.getUserService());
    }
}