package org.baeldung.testmethodsecurity;

import static org.junit.Assert.assertEquals;

import org.baeldung.testmethodsecurity.service.UserRoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration
public class TestMethodSecurity{
    
    @Autowired
    UserRoleService userRoleService;
    
    @Configuration
    @ComponentScan("org.baeldung.testmethodsecurity.*")
    public static class SpringConfig {

    }
    
    @Test
    @WithMockUser(username="john",roles={"VIEWER"})
    public void givenRoleViewer_whenCallGetUsername_thenReturnUsername(){
        String userName = userRoleService.getUsername();
        assertEquals("john", userName);
    }
    
    @Test
    @WithMockUser(username="john",authorities={"SYS_ADMIN"})
    public void givenAuthoritySysAdmin_whenCallGetUsername_thenReturnUsername(){
        String userName = userRoleService.getUsername();
        assertEquals("john", userName);
    }
    
    @Test(expected=AccessDeniedException.class)
    @WithAnonymousUser
    public void givenAnomynousUser_whenCallGetUsername_thenAccessDenied(){
        userRoleService.getUsername();
    }
    
    @Test
    @WithMockJohnViewer
    public void givenMockedJohnViewer_whenCallGetUsername_thenReturnUsername(){
        String userName = userRoleService.getUsername();
        assertEquals("john", userName);
    }
    
}