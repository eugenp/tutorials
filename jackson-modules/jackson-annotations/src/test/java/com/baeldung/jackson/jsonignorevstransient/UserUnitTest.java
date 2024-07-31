package com.baeldung.jackson.jsonignorevstransient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class UserUnitTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void givenUser_whenSave_thenSkipTransientFields() {
        User user = new User(1L, "user", "newPassword123", "newPassword123");
        User savedUser = userRepository.save(user);

        assertNotNull(savedUser);
        assertNotNull(savedUser.getPassword());
        assertNull(savedUser.getRepeatedPassword());
    }

    @Test
    void givenUser_whenSerializing_thenTransientFieldNotIgnored() throws JsonProcessingException {
        User user = new User(1L, "user", "newPassword123", "newPassword123");
        String result = new ObjectMapper().writeValueAsString(user);

        assertThat(result, containsString("user"));
        assertThat(result, containsString("repeatedPassword"));
    }

}