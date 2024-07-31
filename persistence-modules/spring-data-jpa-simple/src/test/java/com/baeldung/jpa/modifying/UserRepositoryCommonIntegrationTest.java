package com.baeldung.jpa.modifying;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.jpa.modifying.repository.UserRepository;
import com.baeldung.jpa.modifying.model.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@SpringBootTest(classes= ModifyingApplication.class)
class UserRepositoryCommonIntegrationTest {

    final String USER_EMAIL = "email@example.com";
    final String USER_EMAIL2 = "email2@example.com";
    final String USER_EMAIL3 = "email3@example.com";
    final String USER_EMAIL4 = "email4@example.com";
    final Integer INACTIVE_STATUS = 0;
    final Integer ACTIVE_STATUS = 1;
    final String USER_NAME_ADAM = "Adam";
    final String USER_NAME_PETER = "Peter";

    @Autowired
    protected UserRepository userRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    void givenUsersWithSameNameInDB_WhenFindAllByName_ThenReturnStreamOfUsers() {
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
    void givenUsersInDB_WhenFindAllWithSortByName_ThenReturnUsersSorted() {
        userRepository.save(new User(USER_NAME_ADAM, LocalDate.now(), USER_EMAIL, ACTIVE_STATUS));
        userRepository.save(new User(USER_NAME_PETER, LocalDate.now(), USER_EMAIL2, ACTIVE_STATUS));
        userRepository.save(new User("SAMPLE", LocalDate.now(), USER_EMAIL3, INACTIVE_STATUS));

        List<User> usersSortByName = userRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));

        assertThat(usersSortByName.get(0)
          .getName()).isEqualTo(USER_NAME_ADAM);
    }

    @Test
    void givenUsersInDB_WhenFindAllSortWithFunction_ThenThrowException() {
        userRepository.save(new User(USER_NAME_ADAM, LocalDate.now(), USER_EMAIL, ACTIVE_STATUS));
        userRepository.save(new User(USER_NAME_PETER, LocalDate.now(), USER_EMAIL2, ACTIVE_STATUS));
        userRepository.save(new User("SAMPLE", LocalDate.now(), USER_EMAIL3, INACTIVE_STATUS));

        userRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));

        assertThrows(PropertyReferenceException.class, () -> userRepository.findAll(Sort.by("LENGTH(name)")));
    }

    @Test
    @Transactional
    void givenTwoUsers_whenFindByNameUsr01_ThenUserUsr01() {
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
    void givenTwoUsers_whenFindByNameUsr00_ThenNoUsers() {
        User usr01 = new User("usr01", LocalDate.now(), "usr01@baeldung.com", 1);
        User usr02 = new User("usr02", LocalDate.now(), "usr02@baeldung.com", 1);

        userRepository.save(usr01);
        userRepository.save(usr02);

        try (Stream<User> users = userRepository.findAllByName("usr00")) {
            assertEquals(0, users.count());
        }
    }

    @Test
    void givenTwoUsers_whenFindUsersWithGmailAddress_ThenUserUsr02() {
        User usr01 = new User("usr01", LocalDate.now(), "usr01@baeldung.com", 1);
        User usr02 = new User("usr02", LocalDate.now(), "usr02@gmail.com", 1);

        userRepository.save(usr01);
        userRepository.save(usr02);

        List<User> users = userRepository.findUsersWithGmailAddress();
        assertEquals(1, users.size());
        assertEquals(usr02, users.get(0));
    }

    @Test
    @Transactional
    void givenTwoUsers_whenDeleteAllByCreationDateAfter_ThenOneUserRemains() {
        User usr01 = new User("usr01", LocalDate.of(2018, 1, 1), "usr01@baeldung.com", 1);
        User usr02 = new User("usr02", LocalDate.of(2018, 6, 1), "usr02@baeldung.com", 1);

        userRepository.save(usr01);
        userRepository.save(usr02);

        userRepository.deleteAllByCreationDateAfter(LocalDate.of(2018, 5, 1));

        List<User> users = userRepository.findAll();

        assertEquals(1, users.size());
        assertEquals(usr01, users.get(0));
    }

    @Test
    @Transactional
    void givenTwoUsers_whenDeleteDeactivatedUsers_ThenUserUsr02Deleted() {
        User usr01 = new User("usr01", LocalDate.of(2018, 1, 1), "usr01@baeldung.com", 1);
        usr01.setLastLoginDate(LocalDate.now());
        User usr02 = new User("usr02", LocalDate.of(2018, 6, 1), "usr02@baeldung.com", 0);
        usr02.setLastLoginDate(LocalDate.of(2018, 7, 20));
        usr02.setActive(false);

        userRepository.save(usr01);
        userRepository.save(usr02);

        int deletedUsersCount = userRepository.deleteDeactivatedUsers();

        List<User> users = userRepository.findAll();
        assertEquals(1, users.size());
        assertEquals(usr01, users.get(0));
        assertEquals(1, deletedUsersCount);
    }

    @Test
    @Transactional
    void givenTwoUsers_whenDeleteDeactivatedUsersWithNoModifyingAnnotation_ThenException() {
        User usr01 = new User("usr01", LocalDate.of(2018, 1, 1), "usr01@baeldung.com", 1);
        usr01.setLastLoginDate(LocalDate.now());
        User usr02 = new User("usr02", LocalDate.of(2018, 6, 1), "usr02@baeldung.com", 0);
        usr02.setLastLoginDate(LocalDate.of(2018, 7, 20));
        usr02.setActive(false);

        userRepository.save(usr01);
        userRepository.save(usr02);

        assertThatThrownBy(() -> userRepository.deleteDeactivatedUsersWithNoModifyingAnnotation())
            .isInstanceOf(InvalidDataAccessApiUsageException.class);
    }

    @Test
    @Transactional
    void givenTwoUsers_whenAddDeletedColumn_ThenUsersHaveDeletedColumn() {
        User usr01 = new User("usr01", LocalDate.of(2018, 1, 1), "usr01@baeldung.com", 1);
        usr01.setLastLoginDate(LocalDate.now());
        User usr02 = new User("usr02", LocalDate.of(2018, 6, 1), "usr02@baeldung.org", 1);
        usr02.setLastLoginDate(LocalDate.of(2018, 7, 20));
        usr02.setActive(false);

        userRepository.save(usr01);
        userRepository.save(usr02);

        userRepository.addDeletedColumn();

        Query nativeQuery = entityManager.createNativeQuery("select deleted from USERS where NAME = 'usr01'");
        assertEquals(0, nativeQuery.getResultList().get(0));
    }

    @AfterEach
    void cleanUp() {
        userRepository.deleteAll();
    }
}
