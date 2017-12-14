package org.baeldung.testmethodsecurity;

import static org.junit.Assert.assertEquals;

import org.baeldung.testmethodsecurity.service.UserRoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class TestCustomSecurityContext {
    
    @Autowired
    UserRoleService userRoleService;
    
    @Configuration
    @ComponentScan("org.baeldung.testmethodsecurity.*")
    public static class SpringConfig {

    }
    
    @Test
    @WithMockSysUser(systemUserName="jane")
    public void whenJane_callGetUserName_thenOK(){
        String userName = userRoleService.getUserName();
        assertEquals("jane",userName);
    }
    
    @Test(expected=AccessDeniedException.class)
    @WithMockSysUser(systemUserName="john")
    public void whenJohn_callGetUserName_thenFail(){
        userRoleService.getUserName();
    }

}
