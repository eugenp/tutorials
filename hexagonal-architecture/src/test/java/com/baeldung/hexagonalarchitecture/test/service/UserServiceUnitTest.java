package com.baeldung.hexagonalarchitecture.test.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.hexagonalarchitecture.model.User;
import com.baeldung.hexagonalarchitecture.repository.UserRepositoryPort;
import com.baeldung.hexagonalarchitecture.service.UserService;

@RunWith(SpringRunner.class)
public class UserServiceUnitTest {

    @TestConfiguration
    static class UserServiceUnitTestContextConfiguration {

        @Bean
        public UserService userService() {
            return new UserService();
        }
    }

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepositoryPort userRepository;

    @Before
    public void setUp() {
        User user = new User();
        user.setUserName("baeldung");
        Mockito.doNothing()
            .when(userRepository)
            .create(user);
        Mockito.when(userRepository.getUser(1L))
            .thenReturn(user);
    }

    @Test
    public void givenUserID_whenGetUser_thenReturnUserWithGivenID() {
        User user = userService.getUser(1L);
        assertThat(user.getUserName()).isEqualTo("baeldung");
    }

}
