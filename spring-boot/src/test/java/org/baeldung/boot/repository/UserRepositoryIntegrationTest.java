package org.baeldung.boot.repository;

import org.baeldung.boot.config.H2JpaConfig;
import org.baeldung.boot.domain.User;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by adam.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = H2JpaConfig.class)
public class UserRepositoryIntegrationTest {

    private final String USER_NAME_ADAM = "Adam";
    private final Integer ACTIVE_STATUS = 1;

    @Autowired private UserRepository userRepository;

    @Test
    public void givenEmptyDBWhenFindOneByNameThenReturnEmptyOptional() {
        Optional<User> foundUser = userRepository.findOneByName(USER_NAME_ADAM);

        assertThat(foundUser.isPresent()).isEqualTo(false);
    }

    @Test
    public void givenUserInDBWhenFindOneByNameThenReturnOptionalWithUser() {
        User user = new User();
        user.setName(USER_NAME_ADAM);
        userRepository.save(user);

        Optional<User> foundUser = userRepository.findOneByName(USER_NAME_ADAM);

        assertThat(foundUser.isPresent()).isEqualTo(true);

        assertThat(foundUser
          .get()
          .getName()).isEqualTo(USER_NAME_ADAM);
    }

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

    @Test
    public void givenUserInDBWhenFindOneByStatusAsyncThenReturnCompletableFutureUser() throws ExecutionException, InterruptedException {
        User user = new User();
        user.setName(USER_NAME_ADAM);
        user.setStatus(ACTIVE_STATUS);
        userRepository.save(user);

        CompletableFuture<User> userByStatus = userRepository.findOneByStatus(ACTIVE_STATUS);

        assertThat(userByStatus
          .get()
          .getName()).isEqualTo(USER_NAME_ADAM);
    }

    @After
    public void cleanUp() {
        userRepository.deleteAll();
    }

}
