package com.baeldung.jpa.postgres.uuid;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.baeldung.jpa.postgres.uuid.entity.User;
import com.baeldung.jpa.postgres.uuid.repository.UserRepository;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void givenUserEntity_whenSaved_thenIdIsUUID() {
        // Create and save a User entity
        User user = new User();
        user.setName("Alice");
        user.setEmail("alice@example.com");

        // Save the user to the database
        User savedUser = userRepository.save(user);

        // Verify the saved entity has a valid UUID
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getId()).isInstanceOf(UUID.class);
    }
    
    @Test
    public void givenSavedUser_whenFindById_thenUserIsRetrieved() {
        // Save a user
        User user = new User();
        user.setName("Jane Smith");
        user.setEmail("jane.smith@example.com");
        User savedUser = userRepository.save(user);

        // Retrieve the user by ID
        Optional<User> retrievedUser = userRepository.findById(savedUser.getId());

        // Verify the user is retrieved correctly
        assertThat(retrievedUser).isPresent();
        assertThat(retrievedUser.get().getId()).isEqualTo(savedUser.getId());
        assertThat(retrievedUser.get().getName()).isEqualTo("Jane Smith");
        assertThat(retrievedUser.get().getEmail()).isEqualTo("jane.smith@example.com");
        // Verify the Id is UUID
        assertThat(retrievedUser.get().getId()).isNotNull();
        assertThat(retrievedUser.get().getId()).isInstanceOf(UUID.class);
    }
}
