package com.baeldung.dao.repositories;

import com.baeldung.config.PersistenceConfiguration;
import com.baeldung.domain.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by adam.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PersistenceConfiguration.class)
@DirtiesContext
public class UserRepositoryIntegrationTest extends UserRepositoryCommon {

    @Test
    @Transactional
    public void givenUsersInDBWhenUpdateStatusForNameModifyingQueryAnnotationNativeThenModifyMatchingUsers() {
        userRepository.save(new User("SAMPLE", USER_EMAIL, ACTIVE_STATUS));
        userRepository.save(new User("SAMPLE1", USER_EMAIL2, ACTIVE_STATUS));
        userRepository.save(new User("SAMPLE", USER_EMAIL3, ACTIVE_STATUS));
        userRepository.save(new User("SAMPLE3", USER_EMAIL4, ACTIVE_STATUS));
        userRepository.flush();

        int updatedUsersSize = userRepository.updateUserSetStatusForNameNative(INACTIVE_STATUS, "SAMPLE");

        assertThat(updatedUsersSize).isEqualTo(2);
    }

    @Test
    public void givenUsersInDBWhenFindByEmailsWithDynamicQueryThenReturnCollection() {

        User user1 = new User();
        user1.setEmail(USER_EMAIL);
        userRepository.save(user1);

        User user2 = new User();
        user2.setEmail(USER_EMAIL2);
        userRepository.save(user2);

        User user3 = new User();
        user3.setEmail(USER_EMAIL3);
        userRepository.save(user3);

        Set<String> emails = new HashSet<>();
        emails.add(USER_EMAIL2);
        emails.add(USER_EMAIL3);

        Collection<User> usersWithEmails = userRepository.findUserByEmails(emails);

        assertThat(usersWithEmails.size()).isEqualTo(2);
    }

    @Test
    @Transactional
    public void whenInsertedWithQuery_ThenUserIsPersisted() {
        userRepository.insertUser(USER_NAME_ADAM, 1, ACTIVE_STATUS, USER_EMAIL);
        userRepository.insertUser(USER_NAME_PETER, 1, ACTIVE_STATUS, USER_EMAIL2);

        User userAdam = userRepository.findUserByNameLike(USER_NAME_ADAM);
        User userPeter = userRepository.findUserByNameLike(USER_NAME_PETER);

        assertThat(userAdam).isNotNull();
        assertThat(userAdam.getEmail()).isEqualTo(USER_EMAIL);
        assertThat(userPeter).isNotNull();
        assertThat(userPeter.getEmail()).isEqualTo(USER_EMAIL2);
    }

    @After
    public void cleanUp() {
        userRepository.deleteAll();
    }
}
