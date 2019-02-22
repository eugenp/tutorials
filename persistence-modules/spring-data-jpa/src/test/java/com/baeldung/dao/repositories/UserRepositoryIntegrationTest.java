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
}
