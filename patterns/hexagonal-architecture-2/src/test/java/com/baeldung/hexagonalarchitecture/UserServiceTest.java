package com.baeldung.hexagonalarchitecture;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.hexagonalarchitecture.infrastructure.UserServiceImpl;
import com.baeldung.hexagonalarchitecture.infrastructure.dao.jpa.UserRepoJPAImpl;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    public static final String USERNAME = "john.doe";

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepoJPAImpl userRepository;

    @Test
    public void whenCreatingAUser_thenItIsSaved() {
        String expectedId = userService.createUser(USERNAME);
        Assert.assertEquals(USERNAME, userRepository.findById(expectedId)
            .getUsername());
    }
}
