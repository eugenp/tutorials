package org.baeldung.methodsecurity;

import static org.junit.Assert.assertEquals;

import org.baeldung.methodsecurity.service.UserRoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration
@WithMockUser(username = "john", roles = { "VIEWER" })
public class TestWithMockUserAtClassLevel {

    @Test
    public void givenRoleViewer_whenCallGetUsername_thenReturnUsername() {
        String currentUserName = userService.getUsername();
        assertEquals("john", currentUserName);
    }

    @Autowired
    UserRoleService userService;

    @Configuration
    @ComponentScan("org.baeldung.methodsecurity.*")
    public static class SpringConfig {

    }
}
