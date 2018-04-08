package org.baeldung.repository;

import org.baeldung.boot.config.H2JpaConfig;
import org.baeldung.model.User;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
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
@SpringBootTest(classes = H2JpaConfig.class)
public class UserRepositoryIntegrationTest {

    private final String USER_NAME_ADAM = "Adam";
    private final String USER_NAME_PETER = "Peter";
    private final Integer INACTIVE_STATUS = 0;
    private final Integer ACTIVE_STATUS = 1;

    @Autowired private UserRepository userRepository;

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
        assertThat(foundUser
          .get()
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

        assertThat(userByStatus
          .get()
          .getName(), equalTo(USER_NAME_ADAM));

    }

    @Test
    public void shouldReturnAllActiveUsersWhenUsingQueryAnnotation() {
        User user1 = new User();
        user1.setName(USER_NAME_ADAM);
        user1.setStatus(ACTIVE_STATUS);
        userRepository.save(user1);

        User user2 = new User();
        user2.setName(USER_NAME_ADAM);
        user2.setStatus(ACTIVE_STATUS);
        userRepository.save(user2);

        User user3 = new User();
        user3.setName(USER_NAME_ADAM);
        user3.setStatus(INACTIVE_STATUS);
        userRepository.save(user3);

        Collection<User> allActiveUsers = userRepository.findAllActiveUsers();

        assertThat(allActiveUsers.size(), equalTo(2));
    }

    @Test
    public void shouldReturnAllActiveUsersUsingWhenUsingQueryAnnotationNative() {
        User user1 = new User();
        user1.setName(USER_NAME_ADAM);
        user1.setStatus(ACTIVE_STATUS);
        userRepository.save(user1);

        User user2 = new User();
        user2.setName(USER_NAME_ADAM);
        user2.setStatus(ACTIVE_STATUS);
        userRepository.save(user2);

        User user3 = new User();
        user3.setName(USER_NAME_ADAM);
        user3.setStatus(INACTIVE_STATUS);
        userRepository.save(user3);

        Collection<User> allActiveUsers = userRepository.findAllActiveUsersNative();

        assertThat(allActiveUsers.size(), equalTo(2));
    }

    @Test
    public void shouldReturnOneUserWithStatusWhenUsingQueryAnnotation() {
        User user = new User();
        user.setName(USER_NAME_ADAM);
        user.setStatus(ACTIVE_STATUS);
        userRepository.save(user);

        User userByStatus = userRepository.findUserByStatus(ACTIVE_STATUS);

        assertThat(userByStatus.getName(), equalTo(USER_NAME_ADAM));
    }

    @Test
    public void shouldReturnOneUserWithStatusAndNameWhenUsingQueryAnnotation() {
        User user = new User();
        user.setName(USER_NAME_ADAM);
        user.setStatus(ACTIVE_STATUS);
        userRepository.save(user);

        User user2 = new User();
        user2.setName(USER_NAME_PETER);
        user2.setStatus(ACTIVE_STATUS);
        userRepository.save(user2);

        User userByStatus = userRepository.findUserByStatusAndName(ACTIVE_STATUS, USER_NAME_ADAM);

        assertThat(userByStatus.getName(), equalTo(USER_NAME_ADAM));
    }

    @Test
    public void shouldReturnOneUserWithStatusAndNameWhenUsingQueryAnnotationNamedParam() {
        User user = new User();
        user.setName(USER_NAME_ADAM);
        user.setStatus(ACTIVE_STATUS);
        userRepository.save(user);

        User user2 = new User();
        user2.setName(USER_NAME_PETER);
        user2.setStatus(ACTIVE_STATUS);
        userRepository.save(user2);

        User userByStatus = userRepository.findUserByStatusAndNameNamedParams(ACTIVE_STATUS, USER_NAME_ADAM);

        assertThat(userByStatus.getName(), equalTo(USER_NAME_ADAM));
    }

    @Test
    public void shouldReturnOneUserWithUserStatusAndUserNameWhenUsingQueryAnnotation() {
        User user = new User();
        user.setName(USER_NAME_ADAM);
        user.setStatus(ACTIVE_STATUS);
        userRepository.save(user);

        User user2 = new User();
        user2.setName(USER_NAME_PETER);
        user2.setStatus(ACTIVE_STATUS);
        userRepository.save(user2);

        User userByStatus = userRepository.findUserByUserStatusAndUserName(ACTIVE_STATUS, USER_NAME_ADAM);

        assertThat(userByStatus.getName(), equalTo(USER_NAME_ADAM));
    }

    @Test
    public void shouldReturnUserWithNameLikeWhenUsingQueryAnnotation() {
        User user = new User();
        user.setName(USER_NAME_ADAM);
        user.setStatus(ACTIVE_STATUS);
        userRepository.save(user);

        User userByStatus = userRepository.findUserByNameLike("Ad");

        assertThat(userByStatus.getName(), equalTo(USER_NAME_ADAM));
    }

    @Test
    public void shouldReturnUserWithNameLikeWhenUsingQueryAnnotationNamedParam() {
        User user = new User();
        user.setName(USER_NAME_ADAM);
        user.setStatus(ACTIVE_STATUS);
        userRepository.save(user);

        User userByStatus = userRepository.findUserByNameLikeNamedParam("Ad");

        assertThat(userByStatus.getName(), equalTo(USER_NAME_ADAM));
    }

    @Test
    public void shouldReturnUserWithNameLikeWhenUsingQueryAnnotationNative() {
        User user = new User();
        user.setName(USER_NAME_ADAM);
        user.setStatus(ACTIVE_STATUS);
        userRepository.save(user);

        User userByStatus = userRepository.findUserByNameLikeNative("Ad");

        assertThat(userByStatus.getName(), equalTo(USER_NAME_ADAM));
    }

    @Test
    public void shouldFindAllUsersSortByNameAsc() {
        userRepository.save(new User(USER_NAME_ADAM, ACTIVE_STATUS));
        userRepository.save(new User(USER_NAME_PETER, ACTIVE_STATUS));
        userRepository.save(new User("SAMPLE", INACTIVE_STATUS));

        List<User> usersSortByName = userRepository.findAll(new Sort(Sort.Direction.ASC, "name"));

        assertThat(usersSortByName
          .get(0)
          .getName(), equalTo(USER_NAME_ADAM));
    }

    @Test(expected = PropertyReferenceException.class)
    public void shouldThrownExceptionWhenSortWithFunctionJPQL() {
        userRepository.save(new User(USER_NAME_ADAM, ACTIVE_STATUS));
        userRepository.save(new User(USER_NAME_PETER, ACTIVE_STATUS));
        userRepository.save(new User("SAMPLE", INACTIVE_STATUS));

        userRepository.findAll(new Sort(Sort.Direction.ASC, "name"));

        List<User> usersSortByNameLength = userRepository.findAll(new Sort("LENGTH(name)"));

        assertThat(usersSortByNameLength
          .get(0)
          .getName(), equalTo(USER_NAME_ADAM));
    }

    @Test
    public void shouldAllowToSortWithFunctionForQueryAnnotationNative() {
        userRepository.save(new User(USER_NAME_ADAM, ACTIVE_STATUS));
        userRepository.save(new User(USER_NAME_PETER, ACTIVE_STATUS));
        userRepository.save(new User("SAMPLE", INACTIVE_STATUS));

        List<User> usersSortByNameLength = userRepository.findAllUsers(JpaSort.unsafe("LENGTH(name)"));

        assertThat(usersSortByNameLength
          .get(0)
          .getName(), equalTo(USER_NAME_ADAM));
    }

    @Test
    public void shouldAllowToSortWithMethodForQueryAnnotationNativeSQL() {
        userRepository.save(new User(USER_NAME_ADAM, ACTIVE_STATUS));
        userRepository.save(new User(USER_NAME_PETER, ACTIVE_STATUS));
        userRepository.save(new User("SAMPLE", INACTIVE_STATUS));
        userRepository.save(new User("SAMPLE1", INACTIVE_STATUS));
        userRepository.save(new User("SAMPLE2", INACTIVE_STATUS));
        userRepository.save(new User("SAMPLE3", INACTIVE_STATUS));

        Page<User> usersSortByNameLength = userRepository.findAllUsersWithPagination(new PageRequest(1, 3));

        assertThat(usersSortByNameLength
          .getContent()
          .get(0)
          .getName(), equalTo("SAMPLE1"));
    }

    @Test
    @Transactional
    public void shouldUpdateUserStatusForNameWhenUsingModifyingAnnotation() {
        userRepository.save(new User("SAMPLE", ACTIVE_STATUS));
        userRepository.save(new User("SAMPLE1", ACTIVE_STATUS));
        userRepository.save(new User("SAMPLE", ACTIVE_STATUS));
        userRepository.save(new User("SAMPLE3", ACTIVE_STATUS));

        int updatedUsersSize = userRepository.updateUserSetStatusForName(INACTIVE_STATUS, "SAMPLE");

        assertThat(updatedUsersSize, equalTo(2));
    }

    @After
    public void cleanUp() {
        userRepository.deleteAll();
    }

}
