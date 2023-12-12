package com.baeldung.boot.daos;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.boot.Application;
import com.baeldung.boot.daos.user.UserRepository;
import com.baeldung.boot.domain.User;

@ActiveProfiles("tc-jdbc")
@SpringBootTest(classes = Application.class)
public class UserRepositoryTCJdbcLiveTest {

    final String USER_EMAIL = "email@example.com";
    final String USER_EMAIL2 = "email2@example.com";
    final String USER_EMAIL3 = "email3@example.com";
    final String USER_EMAIL4 = "email4@example.com";
    final Integer INACTIVE_STATUS = 0;
    final Integer ACTIVE_STATUS = 1;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    public void givenUsersInDB_WhenUpdateStatusForNameModifyingQueryAnnotationNative_ThenModifyMatchingUsers() {
        userRepository.save(new User("SAMPLE", LocalDate.now(), USER_EMAIL, ACTIVE_STATUS));
        userRepository.save(new User("SAMPLE1", LocalDate.now(), USER_EMAIL2, ACTIVE_STATUS));
        userRepository.save(new User("SAMPLE", LocalDate.now(), USER_EMAIL3, ACTIVE_STATUS));
        userRepository.save(new User("SAMPLE3", LocalDate.now(), USER_EMAIL4, ACTIVE_STATUS));
        userRepository.flush();

        int updatedUsersSize = userRepository.updateUserSetStatusForNameNativePostgres(INACTIVE_STATUS, "SAMPLE");

        assertThat(updatedUsersSize).isEqualTo(2);
    }
}
