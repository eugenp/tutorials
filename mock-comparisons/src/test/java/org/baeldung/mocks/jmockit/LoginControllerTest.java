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

    @Mock
    private LoginDao loginDao;

    @Spy
    @InjectMocks
    private LoginService spiedLoginService;

    @Mock
    private LoginService loginService;

    @InjectMocks
    private LoginController loginController;

    @Before
    public void setUp() {
        loginController = new LoginController();
    }

    @Test
    public void assertThatNoMethodHasBeenCalled() {
      
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
