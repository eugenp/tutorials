package com.baeldung.boot.daos;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.boot.daos.user.UserRepository;
import com.baeldung.boot.domain.User;

@SpringBootTest
public class UserRepositoryCommonIntegrationTest {

    final String USER_EMAIL = "email@example.com";
    final String USER_EMAIL2 = "email2@example.com";
    final String USER_EMAIL3 = "email3@example.com";
    final String USER_EMAIL4 = "email4@example.com";
    final String USER_NAME_ADAM = "Adam";

    @Autowired
    protected UserRepository userRepository;

    @Test
    @Transactional
    public void givenUsersWithSameNameInDB_WhenFindAllByName_ThenReturnStreamOfUsers() {
        User user1 = new User();
        user1.setName(USER_NAME_ADAM);
        user1.setEmail(USER_EMAIL);
        userRepository.save(user1);

        User user2 = new User();
        user2.setName(USER_NAME_ADAM);
        user2.setEmail(USER_EMAIL2);
        userRepository.save(user2);

        User user3 = new User();
        user3.setName(USER_NAME_ADAM);
        user3.setEmail(USER_EMAIL3);
        userRepository.save(user3);

        User user4 = new User();
        user4.setName("SAMPLE");
        user4.setEmail(USER_EMAIL4);
        userRepository.save(user4);

        try (Stream<User> foundUsersStream = userRepository.findAllByName(USER_NAME_ADAM)) {
            assertThat(foundUsersStream.count()).isEqualTo(3l);
        }
    }
    
    @Test
    @Transactional
    public void givenTwoUsers_whenFindByNameUsr01_ThenUserUsr01() {
        User usr01 = new User("usr01", LocalDate.now(), "usr01@baeldung.com", 1);
        User usr02 = new User("usr02", LocalDate.now(), "usr02@baeldung.com", 1);

        userRepository.save(usr01);
        userRepository.save(usr02);

        try (Stream<User> users = userRepository.findAllByName("usr01")) {
            assertTrue(users.allMatch(usr -> usr.equals(usr01)));
        }
    }

    @Test
    @Transactional
    public void givenTwoUsers_whenFindByNameUsr00_ThenNoUsers() {
        User usr01 = new User("usr01", LocalDate.now(), "usr01@baeldung.com", 1);
        User usr02 = new User("usr02", LocalDate.now(), "usr02@baeldung.com", 1);

        userRepository.save(usr01);
        userRepository.save(usr02);

        try (Stream<User> users = userRepository.findAllByName("usr00")) {
            assertEquals(0, users.count());
        }
    }

    @AfterEach
    public void cleanUp() {
        userRepository.deleteAll();
    }
}
