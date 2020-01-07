package com.baeldung.jmockit;

import mockit.*;
import mockit.integration.junit4.JMockit;
import com.baeldung.testCase.LoginController;
import com.baeldung.testCase.LoginDao;
import com.baeldung.testCase.LoginService;
import com.baeldung.testCase.UserForm;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMockit.class)
public class LoginControllerIntegrationTest {

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
        new FullVerifications(loginService) {
        };
    }

    @Test
    public void assertTwoMethodsHaveBeenCalled() {
        final UserForm userForm = new UserForm();
        userForm.username = "foo";
        new Expectations() {{
            loginService.login(userForm);
            result = true;
            loginService.setCurrentUser("foo");
        }};

        String login = loginController.login(userForm);

        Assert.assertEquals("OK", login);
        new FullVerifications(loginService) {
        };
    }

    @Test
    public void assertOnlyOneMethodHasBeenCalled() {
        final UserForm userForm = new UserForm();
        userForm.username = "foo";
        new Expectations() {{
            loginService.login(userForm);
            result = false;
            // no expectation for setCurrentUser
        }};

        String login = loginController.login(userForm);

        Assert.assertEquals("KO", login);
        new FullVerifications(loginService) {
        };
    }

    @Test
    public void mockExceptionThrowing() {
        final UserForm userForm = new UserForm();
        new Expectations() {{
            loginService.login(userForm);
            result = new IllegalArgumentException();
            // no expectation for setCurrentUser
        }};

        String login = loginController.login(userForm);

        Assert.assertEquals("ERROR", login);
        new FullVerifications(loginService) {
        };
    }

    @Test
    public void mockAnObjectToPassAround(@Mocked final UserForm userForm) {
        new Expectations() {{
            userForm.getUsername();
            result = "foo";
            loginService.login(userForm);
            result = true;
            loginService.setCurrentUser("foo");
        }};

        String login = loginController.login(userForm);

        Assert.assertEquals("OK", login);
        new FullVerifications(loginService) {
        };
        new FullVerifications(userForm) {
        };
    }

    @Test
    public void argumentMatching() {
        final UserForm userForm = new UserForm();
        userForm.username = "foo";
        // default matcher
        new Expectations() {{
            loginService.login((UserForm) any);
            result = true;
            // complex matcher
            loginService.setCurrentUser(withArgThat(new BaseMatcher<String>() {
                @Override
                public boolean matches(Object item) {
                    return item instanceof String && ((String) item).startsWith("foo");
                }

                @Override
                public void describeTo(Description description) {
                    //NOOP
                }
            }));
        }};

        String login = loginController.login(userForm);

        Assert.assertEquals("OK", login);
        new FullVerifications(loginService) {
        };
    }

    
    @Test
    public void partialMocking() {

        LoginService partialLoginService = new LoginService();
        partialLoginService.setLoginDao(loginDao);
        loginController.loginService = partialLoginService;

        UserForm userForm = new UserForm();
        userForm.username = "foo";
        
        new Expectations(partialLoginService) {{
            //let's mock loginDao#login() call 
            loginDao.login(userForm); result = 1; 
            
            //no expectation for partialLoginService#login() so that real implementation is used 
            
            //mocking partialLoginService#setCurrentUser() 
            partialLoginService.setCurrentUser("foo");
        }};

        String login = loginController.login(userForm);

        Assert.assertEquals("OK", login);
        // verify that mocked partialLoginService#setCurrentUser("foo") is called 
        new Verifications() {
            {
                partialLoginService.setCurrentUser("foo");
            }
        };

    }
}
