package com.baeldung.spring.data.persistence.findvsget;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.baeldung.spring.data.persistence.findvsget.entity.User;
import com.baeldung.spring.data.persistence.findvsget.repository.NewTransactionUserRepository;
import com.baeldung.spring.data.persistence.findvsget.repository.SimpleUserRepository;
import com.baeldung.spring.data.persistence.findvsget.service.NonTransactionalUserReferenceService;
import com.baeldung.spring.data.persistence.findvsget.service.TransactionalUserReferenceService;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;


@DisplayName("getReferenceBy test:")
class GetReferenceIntegrationTest extends DatabaseConfigurationBaseTest {

    private static final long EXISTING_ID = 1L;

    @Nested
    @DisplayName("given non-transactional service, even if user exists")
    class GivenNonTransactionalService {

        @Autowired
        private NonTransactionalUserReferenceService service;

        @BeforeEach
        void configureService(@Autowired SimpleUserRepository repository) {
            service.setRepository(repository);
        }

        @Test
        void whenFindUserReferenceUsingOutsideServiceThenThrowsException() {
            final User user = service.findUserReference(EXISTING_ID);
            assertThatExceptionOfType(LazyInitializationException.class)
                .isThrownBy(user::getFirstName);
        }

        @Test
        void whenFindUserReferenceNotUsingOutsideServiceThenDontThrowException() {
            final User user = service.findUserReference(EXISTING_ID);
            assertThat(user).isNotNull();
        }

        @Test
        void whenFindUserReferenceUsingInsideServiceThenThrowsException() {
            assertThatExceptionOfType(LazyInitializationException.class)
                .isThrownBy(() -> service.findAndUseUserReference(EXISTING_ID));
        }
    }

    @Nested
    @DisplayName("given non-transactional service, even if user exists")
    class GivenTransactionalService {

        @Autowired
        private TransactionalUserReferenceService service;

        @BeforeEach
        void configureService(@Autowired SimpleUserRepository repository) {
            service.setRepository(repository);
        }

        @Test
        void whenFindUserReferenceUsingOutsideServiceThenThrowsException() {
            final User user = service.findUserReference(EXISTING_ID);
            assertThatExceptionOfType(LazyInitializationException.class)
                .isThrownBy(user::getFirstName);
        }

        @Test
        void whenFindUserReferenceNotUsingOutsideServiceThenDontThrowException() {
            final User user = service.findUserReference(EXISTING_ID);
            assertThat(user).isNotNull();
        }

        @ParameterizedTest
        @ArgumentsSource(UserProvider.class)
        void whenFindUserReferenceUsingInsideServiceThenReturnsUser(Long id, String firstName, String lastName) {
            final User expected = new User(id, firstName, lastName);
            final User actual = service.findAndUseUserReference(id);
            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("given non-transactional service with new transaction repository, even if user exists")
    class GivenTransactionalServiceWithNewTransactionRepository {

        @Autowired
        private TransactionalUserReferenceService service;

        @BeforeEach
        void configureService(@Autowired NewTransactionUserRepository repository) {
            service.setRepository(repository);
        }

        @Test
        void whenFindUserReferenceUsingOutsideServiceThenThrowsException() {
            final User user = service.findUserReference(EXISTING_ID);
            assertThatExceptionOfType(LazyInitializationException.class)
                .isThrownBy(user::getFirstName);
        }

        @Test
        void whenFindUserReferenceNotUsingOutsideServiceThenDontThrowException() {
            final User user = service.findUserReference(EXISTING_ID);
            assertThat(user).isNotNull();
        }

        @Test
        void whenFindUserReferenceUsingInsideServiceThenThrowsExceptionDueToSeparateTransactions() {
            final User user = service.findUserReference(EXISTING_ID);
            assertThatExceptionOfType(LazyInitializationException.class)
                .isThrownBy(user::getFirstName);
        }
    }
}
