package com.baeldung.eclipse.krazo;

import com.baeldung.eclipse.krazo.User;
import com.baeldung.eclipse.krazo.UserController;
import jakarta.mvc.Models;
import jakarta.mvc.binding.BindingResult;
import org.eclipse.krazo.core.ModelsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * The class contains unit tests. We do only unit tests. Most of the classes are mocked
 */
@DisplayName("Eclipse Krazo MVC 2.0 Test Suite")
class UserControllerUnitTest {

    @InjectMocks
    UserController userController = new UserController();

    @Mock
    Models models;

    @Mock
    BindingResult bindingResult;

    @BeforeEach
    public void setUpClass() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Test Show Form")
    void whenShowForm_thenReturnViewName() {
        assertNotNull(userController.showForm());
        assertEquals("user.jsp", userController.showForm());
    }

    @Test
    @DisplayName("Test Save User Success")
    void whenSaveUser_thenReturnSuccess() {
        when(bindingResult.isFailed()).thenReturn(false);

        User user = new User();

        assertNotNull(userController.saveUser(user));
        assertDoesNotThrow(() -> userController.saveUser(user));
        assertEquals("redirect:users/success", userController.saveUser(user));
    }

    @Test
    @DisplayName("Test Save User Binding Errors")
    void whenSaveUser_thenReturnError() {
        when(bindingResult.isFailed()).thenReturn(true);
        Models testModels = new ModelsImpl();
        when(models.put(anyString(), any())).thenReturn(testModels);
        User user = getUser();
        assertNotNull(userController.saveUser(user));
        assertDoesNotThrow(() -> userController.saveUser(user));
        assertEquals("user.jsp", userController.saveUser(user));
    }

    @Test
    @DisplayName("Test Save User Success View")
    void whenSaveUserSuccess_thenRedirectSuccess() {
        assertNotNull(userController.saveUserSuccess());
        assertDoesNotThrow(() -> userController.saveUserSuccess());
        assertEquals("success.jsp", userController.saveUserSuccess());
    }

    @Test
    @DisplayName("Test Get Users API")
    void whenGetUser_thenReturnUsers() {
        when(bindingResult.isFailed()).thenReturn(false);

        User user= getUser();

        assertNotNull(user);
        assertEquals(30, user.getAge());
        assertEquals("john doe", user.getName());
        assertEquals("anymail", user.getEmail());
        assertEquals("99887766554433", user.getPhone());
        assertEquals("1", user.getId());

        userController.saveUser(user);
        userController.saveUser(user);
        userController.saveUser(user);

        assertNotNull(userController.getUsers());
        assertDoesNotThrow(() -> userController.getUsers());
        assertEquals(3, userController.getUsers().size());

    }

    private User getUser() {
        User user = new User();
        user.setId("1");
        user.setName("john doe");
        user.setAge(30);
        user.setEmail("anymail");
        user.setPhone("99887766554433");
        return user;
    }

}
