package com.baeldung.boot.customRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.baeldung.boot.customRepository.model.User;
import com.baeldung.boot.customRepository.repository.UserRepository;

@SpringBootTest(classes = CustomRepositoryApplication.class)
public class CustomRepositoryUnitTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void givenCustomRepository_whenInvokeCustomFindMethod_thenEntityIsFound() {
        User user = new User();
        user.setEmail("foo@gmail.com");
        user.setName("userName");

        User persistedUser = userRepository.save(user);

        assertEquals(persistedUser, userRepository.customFindMethod(user.getId()));
    }
}
