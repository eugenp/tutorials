package org.baeldung.repository;

import org.baeldung.boot.Application;
import org.baeldung.model.User;
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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by adam.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserRepositoryTest {

    private final String USER_NAME_ADAM = "Adam";
    private final Integer ACTIVE_STATUS = 1;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldReturnEmptyOptionalWhenSearchByNameInEmptyDB() {
        Optional<User> foundUser = userRepository.findOneByName(USER_NAME_ADAM);

        assertThat(foundUser.isPresent(), equalTo(false));
    }

    @Test
    public void shouldReturnOptionalWithPresentUserWhenExistsWithGivenName() {
        User user = new User();
        user.setName(USER_NAME_ADAM);
        userRepository.save(user);

        Optional<User> foundUser = userRepository.findOneByName(USER_NAME_ADAM);

        assertThat(foundUser.isPresent(), equalTo(true));
        assertThat(foundUser.get()
            .getName(), equalTo(USER_NAME_ADAM));
    }

    @Test
    @Transactional
    public void shouldReturnStreamOfUsersWithNameWhenExistWithSameGivenName() {
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
            assertThat(foundUsersStream.count(), equalTo(3l));
        }
    }

    @Test
    public void shouldReturnUserWithGivenStatusAsync() throws ExecutionException, InterruptedException {
        User user = new User();
        user.setName(USER_NAME_ADAM);
        user.setStatus(ACTIVE_STATUS);
        userRepository.save(user);

        CompletableFuture<User> userByStatus = userRepository.findOneByStatus(ACTIVE_STATUS);

        assertThat(userByStatus.get()
            .getName(), equalTo(USER_NAME_ADAM));

    }

    @After
    public void cleanUp() {
        userRepository.deleteAll();
    }

}
