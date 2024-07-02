package com.baeldung.spring.data.jpa.uuidexception;

import static org.assertj.core.util.Throwables.getRootCause;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryLiveTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserWithConverterRepository userWithConverterRepository;

    @AfterEach
    public void clean() {
        userRepository.deleteUser();
    }


    @Test
    void givenUuidTypeVarchar_whenRetrieveByUuid_thenExceptionThrows() {
        UUID testId = UUID.fromString("c3917b5b-18ed-4a84-a6f7-6be7a8c21d66");
        User user = new User();
        user.setUuid(testId);
        user.setName("John Doeee");

        userRepository.save(user);

        Throwable throwable = assertThrows(InvalidDataAccessResourceUsageException.class,
            () -> userRepository.findByUuid(testId),
            "Expected ERROR: operator does not exist: character varying = uuid");
        assertTrue(getRootCause(throwable) instanceof PSQLException);
    }

    @Test
    void givenUuidTypeVarchar_whenRetrieveByUuidUsingCastFunction_thenNoExceptionThrows() {
        UUID testId = UUID.fromString("c3917b5b-18ed-4a84-a6f7-6be7a8c21d66");
        userRepository.insertUser(testId, "John Doe");

        Optional<User> userOptional = userRepository.findByUuidWithCastFunction(testId);
        assertThat(userOptional.isPresent(), is(true));
        assertThat(userOptional.get()
            .getUuid(), equalTo(testId));
    }

    @Test
    void givenUuidTypeVarchar_whenRetrieveByUuidUsingCastingOperator_thenNoExceptionThrows() {
        UUID testId = UUID.fromString("c3917b5b-18ed-4a84-a6f7-6be7a8c21d66");
        userRepository.insertUser(testId, "John Doe");

        Optional<User> userOptional = userRepository.findByUuidWithCastingOperator(testId);
        assertThat(userOptional.isPresent(), is(true));
        assertThat(userOptional.get()
            .getUuid(), equalTo(testId));
    }

    @Test
    void givenUuidTypeVarchar_whenRetrieveByUuidUsingConverter_thenNoExceptionThrows() {
        UUID testId = UUID.fromString("c3917b5b-18ed-4a84-a6f7-6be7a8c21d66");
        UserWithConverter user = new UserWithConverter();
        user.setUuid(testId);
        user.setName("John Doeee");

        userWithConverterRepository.save(user);

        Optional<UserWithConverter> userOptional = userWithConverterRepository.findByUuid(testId);
        assertThat(userOptional.isPresent(), is(true));
        assertThat(userOptional.get()
            .getUuid(), equalTo(testId));
    }

}
