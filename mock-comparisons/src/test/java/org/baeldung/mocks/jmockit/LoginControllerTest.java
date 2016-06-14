package org.baeldung.mocks.mockito;

import org.baeldung.mocks.testCase.LoginController;
import org.baeldung.mocks.testCase.LoginDao;
import org.baeldung.mocks.testCase.LoginService;
import org.baeldung.mocks.testCase.UserForm;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

/**
 * <p>Test for LoginController using Mockito.</p>
 * Created by Alvaro on 12/06/2016.
 */
public class LoginControllerTest {

    @Injectable
    private LoginDao loginDao;

    @Injectable
    private LoginService loginService;

    @Tested
    private LoginController loginController;

    @Test
    public void assertThatNoMethodHasBeenCalled() {
        loginController.login(null);
        // no method called
        new FullVerifications(loginService) {};
    }

    @Test
    public void assertTwoMethodsHaveBeenCalled() {
      
    }

    @Test
    public void assertOnlyOneMethodHasBeenCalled() {
      
    }

    @Test
    public void mockExceptionThrowing() {
      
    }

    @Test
    public void stubAnObjectToPassAround() {
      
    }

    @Test
    public void argumentMatching() {
      
    }

    @Test
    public void partialMocking() {
      
    }
}
