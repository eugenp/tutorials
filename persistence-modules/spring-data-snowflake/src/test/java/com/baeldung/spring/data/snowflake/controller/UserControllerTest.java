package com.baeldung.spring.data.snowflake.controller;

import com.baeldung.spring.data.snowflake.entity.User;
import com.baeldung.spring.data.snowflake.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @MockBean
    UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        when(userRepository.findByUserId(1L)).thenReturn(getUser_1());
        when(userRepository.findAllUsers()).thenReturn(List.of(getUser_1(), getUser_2()));
    }

    @Test
    public void test_findAllUsers() {
        assertEquals(2, userController.findAll().size());
    }

    @Test
    public void test_findUserById() {
        assertEquals("Ryan", userController.findById(1L).getName());
    }

    private static User getUser_1() {
        User user = new User();
        user.setId(1L);
        user.setName("Ryan");
        return user;
    }

    private static User getUser_2() {
        User user = new User();
        user.setId(2L);
        user.setName("Matt");
        return user;
    }
}