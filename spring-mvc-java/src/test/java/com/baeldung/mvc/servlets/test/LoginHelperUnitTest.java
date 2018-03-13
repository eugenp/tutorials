package com.baeldung.mvc.servlets.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.baeldung.mvc.helper.LoginHelper;

public class LoginHelperUnitTest {
    
    @Test
    public void whenValidCredentials_thenTrue() throws Exception {
       assertEquals(LoginHelper.authenticateUser("baeldung", "baeldung"), true);
    }
    
    @Test
    public void whenInvalidCredentials_thenFalse() throws Exception {
       assertEquals(LoginHelper.authenticateUser("invalid-id", "invalid-password"), false);
    }

}
