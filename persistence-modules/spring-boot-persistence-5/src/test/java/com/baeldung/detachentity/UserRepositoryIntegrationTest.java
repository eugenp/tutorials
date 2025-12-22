package com.baeldung.detachentity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.baeldung.detachentity.domain.User;
import com.baeldung.detachentity.repository.UserRepository;


@DataJpaTest
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void givenValidUserIsDetached_whenUserSaveIsCalled_AndUpdated_thenUserIsNotUpdated() {
        User user = new User();
        user.setName("test1");
        user.setEmail("test1@mail.com");
        user.setActivated(true);

        userRepository.save(user);
        userRepository.detach(user);
        user.setName("test1_updated");
        entityManager.flush();

        Optional<User> savedUser = userRepository.findById(user.getId());

        assertNotNull(savedUser);
        assertTrue(savedUser.isPresent());
        assertEquals("test1", savedUser.get().getName());
        assertEquals("test1@mail.com", savedUser.get().getEmail());
        assertTrue(savedUser.get().isActivated());
    }

    @Test
    void givenUserIsNotDetached_whenUserSaveIsCalled_AndUpdated_thenUserIsUpdated() {
        User user = new User();
        user.setName("test2");
        user.setEmail("test2@mail.com");
        user.setActivated(true);

        userRepository.save(user);
        user.setName("test2_updated");
        entityManager.flush();

        Optional<User> savedUser = userRepository.findById(user.getId());

        assertNotNull(savedUser);
        assertTrue(savedUser.isPresent());
        assertEquals("test2_updated", savedUser.get().getName());
        assertEquals("test2@mail.com", savedUser.get().getEmail());
        assertTrue(savedUser.get().isActivated());
    }
}
