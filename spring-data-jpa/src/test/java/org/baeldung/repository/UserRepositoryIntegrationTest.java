package org.baeldung.repository;

import org.baeldung.config.H2JpaConfig;
import org.baeldung.persistence.model.User;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by adam.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = H2JpaConfig.class)
public class UserRepositoryIntegrationTest {

    private final String USER_NAME_ADAM = "Adam";

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    public void givenUsersWithSameNameInDBWhenFindAllByNameThenReturnStreamOfUsers() {
        User user1 = new User();
        user1.setName(USER_NAME_ADAM);
        userRepository.save(user1);

        User user2 = new User();
        user2.setName(USER_NAME_ADAM);
        userRepository.save(user2);

        User user3 = new User();
        user3.setName(USER_NAME_ADAM);
        userRepository.save(user3);

        User user4 = new User();
        user4.setName("SAMPLE");
        userRepository.save(user4);

        try (Stream<User> foundUsersStream = userRepository.findAllByName(USER_NAME_ADAM)) {
            assertThat(foundUsersStream.count()).isEqualTo(3l);
        }
    }

    @After
    public void cleanUp() {
        userRepository.deleteAll();
    }

}
