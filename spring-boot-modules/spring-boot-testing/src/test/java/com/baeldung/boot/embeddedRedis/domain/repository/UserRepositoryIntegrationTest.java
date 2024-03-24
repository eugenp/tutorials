package com.baeldung.boot.embeddedRedis.domain.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.baeldung.boot.embeddedRedis.TestRedisConfiguration;
import com.baeldung.boot.embeddedRedis.domain.User;


@SpringBootTest(classes = TestRedisConfiguration.class)
class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveUser_toRedis() {
        final UUID id = UUID.randomUUID();
        final User user = new User(id, "name");

        final User saved = userRepository.save(user);

        assertNotNull(saved);
    }
}