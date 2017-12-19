package org.baeldung.testmethodsecurity;

import static org.junit.Assert.assertEquals;

import org.baeldung.testmethodsecurity.entity.CustomUser;
import org.baeldung.testmethodsecurity.service.UserRoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration
public class TestWithUserDetails {
    
    @Autowired
    UserRoleService userService;
    
    @Configuration
    @ComponentScan("org.baeldung.testmethodsecurity.*")
    public static class SpringConfig {

    }
    
    @Test
    @WithUserDetails(value="john",userDetailsServiceBeanName="userDetailService")
    public void whenJohn_callLoadUserDetail_thenOK(){
        CustomUser user = userService.loadUserDetail("jane");
        assertEquals("jane",user.getNickName());
    }
}
