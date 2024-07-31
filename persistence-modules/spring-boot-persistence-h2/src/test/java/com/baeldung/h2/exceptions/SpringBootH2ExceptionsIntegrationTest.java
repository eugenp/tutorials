package com.baeldung.h2.exceptions;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.h2.exceptions.models.User;
import com.baeldung.h2.exceptions.repos.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootH2Exceptions.class)
public class SpringBootH2ExceptionsIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void givenValidInitData_whenCallingFindAll_thenReturnData() {
        List<User> users = userRepository.findAll();

        assertThat(users).hasSize(2);
    }

}
