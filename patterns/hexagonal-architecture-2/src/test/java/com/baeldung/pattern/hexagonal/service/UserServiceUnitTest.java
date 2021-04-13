package com.baeldung.pattern.hexagonal.service;

import com.baeldung.pattern.hexagonal.dao.User;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceUnitTest {

    @Autowired
    private UserService userService;

    @Test
    public void testUserFetchingByLogin() {
        User expectedUser = User.builder()
                .name("John")
                .surname("Snow")
                .login("jsnow")
                .build();
        Assertions.assertThat(userService.getUserByLogin("jsnow").get()).isEqualTo(expectedUser);
    }
}