package org.baeldung.testmethodsecurity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Proxy;

import org.baeldung.testmethodsecurity.service.SystemPropImpl;
import org.baeldung.testmethodsecurity.service.SystemPropInterface;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class TestSystemProp{
    
    @Autowired
    SystemPropInterface systemProp;
    
    @Test
    @WithMockUser(username="test")
    public void checkSystemPropInstance(){
       assertFalse(systemProp instanceof SystemPropImpl);
       assertTrue(systemProp instanceof SystemPropInterface);
       assertTrue(systemProp instanceof Proxy);
       
       assertEquals("Method Security", systemProp.getSystemName());
    }
    
    @Test
    public void whenNotAuthentication_callSayHello_thenOK(){
        String hello = systemProp.sayHello();
        assertEquals("Hi", hello);
    }
    
    @Test(expected=AuthenticationCredentialsNotFoundException.class)
    public void whenNotAuthentication_callSayHi_thenFailed(){
        systemProp.sayHi();
    }
    
    @Configuration
    @ComponentScan("org.baeldung.testmethodsecurity.*")
    public static class SpringConfig {

    }
}
