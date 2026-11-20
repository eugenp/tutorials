package com.baeldung.spring.data.jpa.query.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("h2")
class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void givenUser_whenFindByGroup_thenReturnsUser() {
        User user = new User();
        user.setGroup("Admin");
        userRepository.save(user);

        // Validates that the escaped 'group' identifier works in JPQL
        List<User> result = userRepository.findByGroup("Admin");
        
        assertEquals(1, result.size());
        assertEquals("Admin", result.get(0).getGroup());
    }

    @Test
    void givenUser_whenFindByFirstName_thenReturnsUser() {
        User user = new User();
        user.setFirstName("John");
        userRepository.save(user);

        // Validates that the JPQL correctly references the Java 'firstName' attribute
        List<User> result = userRepository.findByFirstName("John");
        
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstName());
    }

    @Test
    void givenActiveUser_whenFindActiveUsers_thenReturnsUser() {
        User user = new User();
        user.setFirstName("Jane");
        user.setStatus(1);
        userRepository.save(user);

        // Validates the native SQL execution via nativeQuery = true
        List<User> result = userRepository.findActiveUsers();
        
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getStatus());
    }
}