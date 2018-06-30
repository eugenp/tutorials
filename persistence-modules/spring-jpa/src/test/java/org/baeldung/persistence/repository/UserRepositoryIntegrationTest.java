package org.baeldung.persistence.repository;

import org.baeldung.config.PersistenceJPAConfigL2Cache;
import org.baeldung.persistence.model.User;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by adam.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = PersistenceJPAConfigL2Cache.class)
public class UserRepositoryIntegrationTest {

    private final String USER_NAME_ADAM = "Adam";
    private final String USER_NAME_PETER = "Peter";
    private final Integer INACTIVE_STATUS = 0;
    private final Integer ACTIVE_STATUS = 1;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void givenUsersInDBWhenFindAllWithQueryAnnotationThenReturnCollectionWithActiveUsers() {
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

        assertThat(allActiveUsers.size()).isEqualTo(2);
    }

    @Test
    public void givenUsersInDBWhenFindAllWithQueryAnnotationNativeThenReturnCollectionWithActiveUsers() {
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

        assertThat(allActiveUsers.size()).isEqualTo(2);
    }

    @Test
    public void givenUserInDBWhenFindUserByStatusWithQueryAnnotationThenReturnActiveUser() {
        User user = new User();
        user.setName(USER_NAME_ADAM);
        user.setStatus(ACTIVE_STATUS);
        userRepository.save(user);

        User userByStatus = userRepository.findUserByStatus(ACTIVE_STATUS);

        assertThat(userByStatus.getName()).isEqualTo(USER_NAME_ADAM);
    }

    @Test
    public void givenUserInDBWhenFindUserByStatusWithQueryAnnotationNativeThenReturnActiveUser() {
        User user = new User();
        user.setName(USER_NAME_ADAM);
        user.setStatus(ACTIVE_STATUS);
        userRepository.save(user);

        User userByStatus = userRepository.findUserByStatusNative(ACTIVE_STATUS);

        assertThat(userByStatus.getName()).isEqualTo(USER_NAME_ADAM);
    }

    @Test
    public void givenUsersInDBWhenFindUserByStatusAndNameWithQueryAnnotationIndexedParamsThenReturnOneUser() {
        User user = new User();
        user.setName(USER_NAME_ADAM);
        user.setStatus(ACTIVE_STATUS);
        userRepository.save(user);

        User user2 = new User();
        user2.setName(USER_NAME_PETER);
        user2.setStatus(ACTIVE_STATUS);
        userRepository.save(user2);

        User userByStatus = userRepository.findUserByStatusAndName(ACTIVE_STATUS, USER_NAME_ADAM);

        assertThat(userByStatus.getName()).isEqualTo(USER_NAME_ADAM);
    }

    @Test
    public void givenUsersInDBWhenFindUserByStatusAndNameWithQueryAnnotationNamedParamsThenReturnOneUser() {
        User user = new User();
        user.setName(USER_NAME_ADAM);
        user.setStatus(ACTIVE_STATUS);
        userRepository.save(user);

        User user2 = new User();
        user2.setName(USER_NAME_PETER);
        user2.setStatus(ACTIVE_STATUS);
        userRepository.save(user2);

        User userByStatus = userRepository.findUserByStatusAndNameNamedParams(ACTIVE_STATUS, USER_NAME_ADAM);

        assertThat(userByStatus.getName()).isEqualTo(USER_NAME_ADAM);
    }

    @Test
    public void givenUsersInDBWhenFindUserByStatusAndNameWithQueryAnnotationNativeNamedParamsThenReturnOneUser() {
        User user = new User();
        user.setName(USER_NAME_ADAM);
        user.setStatus(ACTIVE_STATUS);
        userRepository.save(user);

        User user2 = new User();
        user2.setName(USER_NAME_PETER);
        user2.setStatus(ACTIVE_STATUS);
        userRepository.save(user2);

        User userByStatus = userRepository.findUserByStatusAndNameNamedParamsNative(ACTIVE_STATUS, USER_NAME_ADAM);

        assertThat(userByStatus.getName()).isEqualTo(USER_NAME_ADAM);
    }

    @Test
    public void givenUsersInDBWhenFindUserByStatusAndNameWithQueryAnnotationNamedParamsCustomNamesThenReturnOneUser() {
        User user = new User();
        user.setName(USER_NAME_ADAM);
        user.setStatus(ACTIVE_STATUS);
        userRepository.save(user);

        User user2 = new User();
        user2.setName(USER_NAME_PETER);
        user2.setStatus(ACTIVE_STATUS);
        userRepository.save(user2);

        User userByStatus = userRepository.findUserByUserStatusAndUserName(ACTIVE_STATUS, USER_NAME_ADAM);

        assertThat(userByStatus.getName()).isEqualTo(USER_NAME_ADAM);
    }

    @Test
    public void givenUsersInDBWhenFindUserByNameLikeWithQueryAnnotationIndexedParamsThenReturnUser() {
        User user = new User();
        user.setName(USER_NAME_ADAM);
        user.setStatus(ACTIVE_STATUS);
        userRepository.save(user);

        User userByStatus = userRepository.findUserByNameLike("Ad");

        assertThat(userByStatus.getName()).isEqualTo(USER_NAME_ADAM);
    }

    @Test
    public void givenUsersInDBWhenFindUserByNameLikeWithQueryAnnotationNamedParamsThenReturnUser() {
        User user = new User();
        user.setName(USER_NAME_ADAM);
        user.setStatus(ACTIVE_STATUS);
        userRepository.save(user);

        User userByStatus = userRepository.findUserByNameLikeNamedParam("Ad");

        assertThat(userByStatus.getName()).isEqualTo(USER_NAME_ADAM);
    }

    @Test
    public void givenUsersInDBWhenFindUserByNameLikeWithQueryAnnotationNativeThenReturnUser() {
        User user = new User();
        user.setName(USER_NAME_ADAM);
        user.setStatus(ACTIVE_STATUS);
        userRepository.save(user);

        User userByStatus = userRepository.findUserByNameLikeNative("Ad");

        assertThat(userByStatus.getName()).isEqualTo(USER_NAME_ADAM);
    }

    @Test
    public void givenUsersInDBWhenFindAllWithSortByNameThenReturnUsersSorted() {
        userRepository.save(new User(USER_NAME_ADAM, ACTIVE_STATUS));
        userRepository.save(new User(USER_NAME_PETER, ACTIVE_STATUS));
        userRepository.save(new User("SAMPLE", INACTIVE_STATUS));

        List<User> usersSortByName = userRepository.findAll(new Sort(Sort.Direction.ASC, "name"));

        assertThat(usersSortByName
          .get(0)
          .getName()).isEqualTo(USER_NAME_ADAM);
    }

    @Test(expected = PropertyReferenceException.class)
    public void givenUsersInDBWhenFindAllSortWithFunctionThenThrowException() {
        userRepository.save(new User(USER_NAME_ADAM, ACTIVE_STATUS));
        userRepository.save(new User(USER_NAME_PETER, ACTIVE_STATUS));
        userRepository.save(new User("SAMPLE", INACTIVE_STATUS));

        userRepository.findAll(new Sort(Sort.Direction.ASC, "name"));

        List<User> usersSortByNameLength = userRepository.findAll(new Sort("LENGTH(name)"));

        assertThat(usersSortByNameLength
          .get(0)
          .getName()).isEqualTo(USER_NAME_ADAM);
    }

    @Test
    public void givenUsersInDBWhenFindAllSortWithFunctionQueryAnnotationJPQLThenReturnUsersSorted() {
        userRepository.save(new User(USER_NAME_ADAM, ACTIVE_STATUS));
        userRepository.save(new User(USER_NAME_PETER, ACTIVE_STATUS));
        userRepository.save(new User("SAMPLE", INACTIVE_STATUS));

        userRepository.findAllUsers(new Sort("name"));

        List<User> usersSortByNameLength = userRepository.findAllUsers(JpaSort.unsafe("LENGTH(name)"));

        assertThat(usersSortByNameLength
          .get(0)
          .getName()).isEqualTo(USER_NAME_ADAM);
    }

    @Test
    public void givenUsersInDBWhenFindAllWithPageRequestQueryAnnotationJPQLThenReturnPageOfUsers() {
        userRepository.save(new User(USER_NAME_ADAM, ACTIVE_STATUS));
        userRepository.save(new User(USER_NAME_PETER, ACTIVE_STATUS));
        userRepository.save(new User("SAMPLE", INACTIVE_STATUS));
        userRepository.save(new User("SAMPLE1", INACTIVE_STATUS));
        userRepository.save(new User("SAMPLE2", INACTIVE_STATUS));
        userRepository.save(new User("SAMPLE3", INACTIVE_STATUS));

        Page<User> usersPage = userRepository.findAllUsersWithPagination(new PageRequest(1, 3));

        assertThat(usersPage
          .getContent()
          .get(0)
          .getName()).isEqualTo("SAMPLE1");
    }

    @Test
    public void givenUsersInDBWhenFindAllWithPageRequestQueryAnnotationNativeThenReturnPageOfUsers() {
        userRepository.save(new User(USER_NAME_ADAM, ACTIVE_STATUS));
        userRepository.save(new User(USER_NAME_PETER, ACTIVE_STATUS));
        userRepository.save(new User("SAMPLE", INACTIVE_STATUS));
        userRepository.save(new User("SAMPLE1", INACTIVE_STATUS));
        userRepository.save(new User("SAMPLE2", INACTIVE_STATUS));
        userRepository.save(new User("SAMPLE3", INACTIVE_STATUS));

        Page<User> usersSortByNameLength = userRepository.findAllUsersWithPaginationNative(new PageRequest(1, 3));

        assertThat(usersSortByNameLength
          .getContent()
          .get(0)
          .getName()).isEqualTo("SAMPLE1");
    }

    @Test
    @Transactional
    public void givenUsersInDBWhenUpdateStatusForNameModifyingQueryAnnotationJPQLThenModifyMatchingUsers() {
        userRepository.save(new User("SAMPLE", ACTIVE_STATUS));
        userRepository.save(new User("SAMPLE1", ACTIVE_STATUS));
        userRepository.save(new User("SAMPLE", ACTIVE_STATUS));
        userRepository.save(new User("SAMPLE3", ACTIVE_STATUS));

        int updatedUsersSize = userRepository.updateUserSetStatusForName(INACTIVE_STATUS, "SAMPLE");

        assertThat(updatedUsersSize).isEqualTo(2);
    }

    @Test
    @Transactional
    public void givenUsersInDBWhenUpdateStatusForNameModifyingQueryAnnotationNativeThenModifyMatchingUsers() {
        userRepository.save(new User("SAMPLE", ACTIVE_STATUS));
        userRepository.save(new User("SAMPLE1", ACTIVE_STATUS));
        userRepository.save(new User("SAMPLE", ACTIVE_STATUS));
        userRepository.save(new User("SAMPLE3", ACTIVE_STATUS));
        userRepository.flush();

        int updatedUsersSize = userRepository.updateUserSetStatusForNameNative(INACTIVE_STATUS, "SAMPLE");

        assertThat(updatedUsersSize).isEqualTo(2);
    }

    @After
    public void cleanUp() {
        userRepository.deleteAll();
    }

}
