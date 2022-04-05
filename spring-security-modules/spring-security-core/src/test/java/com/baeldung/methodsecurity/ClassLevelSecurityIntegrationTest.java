package com.baeldung.methodsecurity;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.methodsecurity.service.SystemService;

@RunWith(SpringRunner.class)
@ContextConfiguration
public class ClassLevelSecurityIntegrationTest {
    
    @Autowired
    SystemService systemService;
    
    @Configuration
    @ComponentScan("com.baeldung.methodsecurity.*")
    public static class SpringConfig {

    }
    
    @Test
    @WithMockUser(username="john",roles={"ADMIN"})
    public void givenRoleAdmin_whenCallGetSystemYear_return2017(){
        String systemYear = systemService.getSystemYear();
        assertEquals("2017",systemYear);
    }
    
    @Test(expected=AccessDeniedException.class)
    @WithMockUser(username="john",roles={"VIEWER"})
    public void givenRoleViewer_whenCallGetSystemYear_returnAccessDenied(){
        String systemYear = systemService.getSystemYear();
        assertEquals("2017",systemYear);
    }
    
    @Test
    @WithMockUser(username="john",roles={"ADMIN"})
    public void givenRoleAdmin_whenCallGetSystemDate_returnDate(){
        String systemYear = systemService.getSystemDate();
        assertEquals("31-12-2017",systemYear);
    }
}
