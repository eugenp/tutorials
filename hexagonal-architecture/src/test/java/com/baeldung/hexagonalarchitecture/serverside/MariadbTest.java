package com.baeldung.hexagonalarchitecture.serverside;

import com.baeldung.hexagonalarchitecture.businesslogic.dto.User;
import com.baeldung.hexagonalarchitecture.businesslogic.repository.UserRepository;
import com.baeldung.hexagonalarchitecture.serverside.mariadb.MariadbUserRepository;
import com.baeldung.hexagonalarchitecture.serverside.mariadb.SpringDataMariadbUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig
@SpringBootTest
public class MariadbTest {

    @Autowired
    private SpringDataMariadbUserRepository springDataMariadbUserRepository;

    UserRepository userRepository;


    @BeforeEach
    void setup() {
        userRepository = new MariadbUserRepository(springDataMariadbUserRepository);
        userRepository.deleteAll();
    }

    @Test
    void shouldCreateUser_thenReturnUser() {
        // given
        final UUID id = UUID.randomUUID();
        User user = new User(id, "test user name", 10, false);

        // when
        userRepository.save(user);
        User userFind = userRepository.findUser(id);
        assertEquals(user, userFind);
    }
}
