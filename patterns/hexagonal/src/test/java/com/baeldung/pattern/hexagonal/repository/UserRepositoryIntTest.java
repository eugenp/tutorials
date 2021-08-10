package com.baeldung.pattern.hexagonal.repository;

import com.baeldung.pattern.hexagonal.domain.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryIntTest {

    @InjectMocks
    private UserRepositoryImpl userRepository;

    private static final String email = "email@gmail.com";

    private static final User user = User.builder()
        .email(email)
        .phone("+39333222111")
        .build();

    @Test
    public void givenUserCreated_whenGetByEmail_thenUserFound() {

        userRepository.save(user);

        assertEquals(user, userRepository.getByEmail(email));
    }

    @Test
    public void whenNoUsersExist_thenNoUserFound() {

        assertNull(userRepository.getByEmail(email));
    }

    @Test
    public void givenUserCreated_whenGetByNotExistingUser_thenUserNull() {

        userRepository.save(user);

        assertNull(userRepository.getByEmail("anotheremail@gmail.com"));
    }
}
