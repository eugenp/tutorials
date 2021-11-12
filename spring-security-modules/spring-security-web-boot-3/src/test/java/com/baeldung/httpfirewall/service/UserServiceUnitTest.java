package com.baeldung.httpfirewall.service;

import com.baeldung.httpfirewall.dao.InMemoryUserDao;

import com.baeldung.httpfirewall.model.User;
import com.baeldung.httpfirewall.utility.UserTestUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("UserService Unit Tests")
class UserServiceUnitTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private InMemoryUserDao userDao;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Check Create User")
    void whenCalledCreateUser_thenVerify() {
        User user = UserTestUtility.createUser();
        doNothing().when(userDao).save(user);

        userService.saveUser(user);
        verify(userDao, times(1)).save(user);
    }



    @Test
    @DisplayName("Check Get User")
    void givenUserId_whenCalledFindById_thenReturnUser() {
        User user = UserTestUtility.createUserWithId("1").orElse(new User("1", "jhondoe", "jhon.doe@gmail.com"));

        when(userDao.findById(user.getId())).thenReturn(Optional.of(user));

        User actualUser = userService.findById("1").get();

        assertNotNull(actualUser);
        assertEquals("jhondoe", actualUser.getUsername());
        verify(userDao, times(1)).findById(user.getId());
    }

    @Test
    @DisplayName("Check Get All Users")
    void whenCalledFindAll_thenReturnAllUsers() {
        List<User> users = UserTestUtility.createUsers().orElse(new ArrayList<>());

        when(userDao.findAll()).thenReturn(Optional.of(users));

        Optional<List<User>> actualUsers = userService.findAll();

        assertNotNull(actualUsers);
        assertEquals(2, users.size());
        verify(userDao, times(1)).findAll();
    }

    @Test
    @DisplayName("Check Delete Users")
    void givenId_whenCalledDeleteUser_thenDeleteUser() {
        User user = UserTestUtility.createUserWithId("1").orElse(new User());

        doNothing().when(userDao).delete(user.getId());
        userService.deleteUser(user.getId());
        verify(userDao, times(1)).delete(user.getId());
    }

}
