package com.baeldung.detachentity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.baeldung.detachentity.client.UserApiClient;
import com.baeldung.detachentity.domain.User;
import com.baeldung.detachentity.repository.UserRepository;
import com.baeldung.detachentity.service.UserRegistrationService;
import com.baeldung.detachentity.service.UserDataService;

@SpringBootTest(classes = SpringJpaApplication.class)
public class UserRegistrationServiceIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private UserRegistrationService userRegistrationService;

    @Autowired
    private UserApiClient userApiClient;

    @TestConfiguration
    static class MockUserApiClientConfig {
        @Bean
        public UserApiClient userApiClient() {
            return Mockito.mock(UserApiClient.class);
        }
    }

    @Test
    void givenValidUser_whenUserIsRegistrationIsCalled_thenSaveActiveUser() {
        Mockito.when(userApiClient.verify(any())).thenReturn(true);

        User user = userRegistrationService.handleRegistration("test1", "test1@mail.com");
        Optional<User> savedUser = userRepository.findById(user.getId());

        assertNotNull(savedUser);
        assertTrue(savedUser.isPresent());
        assertEquals("test1", savedUser.get().getName());
        assertEquals("test1@mail.com", savedUser.get().getEmail());
        assertTrue(savedUser.get().isActivated());
    }

    @Test
    void givenInValidUser_whenUserIsRegistrationIsCalled_thenSaveInActiveUser() {
        Mockito.when(userApiClient.verify(any())).thenReturn(false);

        User user = userRegistrationService.handleRegistration("test2", "test2@mail.com");
        Optional<User> savedUser = userRepository.findById(user.getId());

        assertNotNull(savedUser);
        assertTrue(savedUser.isPresent());
        assertEquals("test2", savedUser.get().getName());
        assertEquals("test2@mail.com", savedUser.get().getEmail());
        assertFalse(savedUser.get().isActivated());
    }

    @Test
    void givenValidUser_whenUserIsRegistrationIsCalled_ExternalServiceFails_thenSaveInActiveUser() {
        Mockito.when(userApiClient.verify(any())).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> userRegistrationService.handleRegistration("test3", "test3@mail.com"));
    }
}
