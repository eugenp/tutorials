package org.baeldung.mocks.easymock;

import org.baeldung.mocks.testCase.LoginController;
import org.baeldung.mocks.testCase.LoginDao;
import org.baeldung.mocks.testCase.LoginService;
import org.baeldung.mocks.testCase.UserForm;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

/**
 * <p>Test for LoginController using EasyMock.</p>
 * Created by Alvaro on 12/06/2016.
 */
public class LoginControllerTest {

    private LoginDao loginDao;

    private LoginService spiedLoginService;

    private LoginService loginService;

    private LoginController loginController;

    @Before
    public void setUp() {
        loginController = new LoginController();
    }

    @Test
    public void assertThatNoMethodHasBeenCalled() {
        loginController.login(null);

        // no method called
        Mockito.verifyZeroInteractions(loginService);
    }

    @Test
    public void assertTwoMethodsHaveBeenCalled() {
        UserForm userForm = new UserForm();
        userForm.username = "foo";

        String login = loginController.login(userForm);

        Assert.assertEquals("OK", login);
    }

    @Test
    public void assertOnlyOneMethodHasBeenCalled() {
        UserForm userForm = new UserForm();
        userForm.username = "foo";

        String login = loginController.login(userForm);

        Assert.assertEquals("KO", login);
    }

    @Test
    public void mockExceptionThrowing() {
        UserForm userForm = new UserForm();

        String login = loginController.login(userForm);

        Assert.assertEquals("ERROR", login);
    }

    @Test
    public void stubAnObjectToPassAround() {

        String login = loginController.login(userForm);

        Assert.assertEquals("OK", login);
    }

    @Test
    public void argumentMatching() {
        UserForm userForm = new UserForm();
        userForm.username = "foo";
        // default matcher

        String login = loginController.login(userForm);

        Assert.assertEquals("OK", login);
        // complex matcher
    }

    @Test
    public void partialMocking() {
        // use partial mock
        UserForm userForm = new UserForm();
        userForm.username = "foo";
        // let service's login use implementation so let's mock DAO call

        String login = loginController.login(userForm);

        Assert.assertEquals("OK", login);
        // verify mocked call
    }
}
