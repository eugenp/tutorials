package com.baeldung.hexagonal;

import com.baeldung.hexagonal.controllers.UserController;
import com.baeldung.hexagonal.entities.User;
import com.baeldung.hexagonal.repositories.UserRepository;
import com.baeldung.hexagonal.services.UserService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerUnitTest {

    private static UserController userController;
    private static UserService mockedUserService;
    private static BindingResult mockedBindingResult;
    private static Model mockedModel;

    @BeforeClass
    public static void setUpUserControllerInstance() {
        mockedUserService = mock(UserService.class);
        mockedBindingResult = mock(BindingResult.class);
        mockedModel = mock(Model.class);
        userController = new UserController(mockedUserService);
    }

    @Test
    public void whenCalledupdateUserAndValidUser_thenCorrect() {
        User user = new User("John", "john@domain.com");

        when(mockedBindingResult.hasErrors()).thenReturn(false);

        assertThat(userController.updateUser(1l, user, mockedBindingResult, mockedModel)).isEqualTo("index");
    }

    @Test
    public void whenCalledupdateUserAndInValidUser_thenCorrect() {
        User user = new User("John", "john@domain.com");

        when(mockedBindingResult.hasErrors()).thenReturn(true);

        assertThat(userController.updateUser(1l, user, mockedBindingResult, mockedModel)).isEqualTo("update-user");
    }

}
