package com.baeldung.jpa.postgres.uuid;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.baeldung.jpa.postgres.uuid.entity.User;
import com.baeldung.jpa.postgres.uuid.repository.UserRepository;
import com.baeldung.jpa.postgres.uuid.service.UserService;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    public UserServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void givenNewUser_whenCreateUser_thenIdIsUUID() {
        // Mock the repository's save method
        User user = new User();
        user.setName("Diana");
        user.setEmail("diana@example.com");

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(UUID.randomUUID()); // Simulate UUID generation
            return savedUser;
        });

        // Call the service's createUser method
        User createdUser = userService.createUser("Diana", "diana@example.com");

        // Verify the created user's ID is a valid UUID
        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getId()).isNotNull();
        assertThat(createdUser.getId()).isInstanceOf(UUID.class);

        // Verify the repository was called
        verify(userRepository, times(1)).save(any(User.class));
    }
}
