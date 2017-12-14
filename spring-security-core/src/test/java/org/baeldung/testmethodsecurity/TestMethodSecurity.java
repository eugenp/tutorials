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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
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
    public void whenRoleViewer_callGetUserName_thenOK(){
        String userName = userRoleService.getUserName();
        assertEquals("john", userName);
    }
    
    @Test
    @WithMockUser(username="john",authorities={"SYS_ADMIN"})
    public void whenSysAdmin_callGetUserName_thenOK(){
        String userName = userRoleService.getUserName();
        assertEquals("john", userName);
    }
    
    @Test(expected=AccessDeniedException.class)
    @WithAnonymousUser
    public void whenAnomynous_callGetUserName_thenFail(){
        userRoleService.getUserName();
    }
    
    @Test
    @WithMockJohnViewer
    public void whenJohnViewer_callGetUserName_thenOK(){
        String userName = userRoleService.getUserName();
        assertEquals("john", userName);
    }
    
}