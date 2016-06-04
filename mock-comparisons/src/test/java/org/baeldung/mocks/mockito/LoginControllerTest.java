package org.baeldung.mocks.mockito;

import org.baeldung.mocks.testCase.LoginController;
import org.baeldung.mocks.testCase.LoginService;
import org.baeldung.mocks.testCase.UserForm;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

public class LoginControllerTest {

    @Mock
    private LoginService loginService;

    @InjectMocks
    private LoginController loginController;

    @Before
    public void setUp() throws Exception {
        loginController = new LoginController();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenNoUserForm_thenReturnErrorWithoutCallingService() throws Exception {
        // let's assert that no method has been called
        loginController.login(null);
        Mockito.verifyZeroInteractions(loginService);
    }

    @Test
    public void whenUserNotLogged_thenReturnKOAndDoNotSet() throws Exception {
        // let's assert that only one method has been called
        UserForm userForm = new UserForm();
        userForm.username = "foo";
        Mockito.when(loginService.login(userForm)).thenReturn(false);
        loginController.login(userForm);
        Mockito.verify(loginService).login(userForm);
        Mockito.verifyNoMoreInteractions(loginService);
    }
}
