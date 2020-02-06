package com.baeldung.hexagonalintro.adapters;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;

import static com.baeldung.hexagonalintro.adapters.DBUserDetailsAdapterUnitTest.Fixture.newPassword;
import static com.baeldung.hexagonalintro.adapters.DBUserDetailsAdapterUnitTest.Fixture.oldPassword;
import static com.baeldung.hexagonalintro.adapters.DBUserDetailsAdapterUnitTest.Fixture.username;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DBUserDetailsAdapterUnitTest {

    @InjectMocks
    private DBUserDetailsAdapter dbUserDetailsAdapter;

    @Mock
    private EntityManager entityManager;

    @Captor
    private ArgumentCaptor<UserDetailsEntity> userDetailsEntityArgumentCaptor;

    @Test
    void givenNoUserCouldBeFound_thenItShouldReturnFalse() {
        given(entityManager.find(UserDetailsEntity.class, username)).willReturn(null);

        boolean correctPassword = dbUserDetailsAdapter.isCorrectPassword(username, oldPassword);
        assertThat(correctPassword).isFalse();
    }

    @Test
    void givenThePasswordsMatch_thenItShouldReturnTrue() {
        given(entityManager.find(UserDetailsEntity.class, username)).willReturn(new UserDetailsEntity(null, oldPassword));

        boolean correctPassword = dbUserDetailsAdapter.isCorrectPassword(username, oldPassword);
        assertThat(correctPassword).isTrue();
    }

    @Test
    void givenThePasswordsDoNotMatch_thenItShouldReturnFalse() {
        given(entityManager.find(UserDetailsEntity.class, username)).willReturn(new UserDetailsEntity(null, oldPassword));

        boolean correctPassword = dbUserDetailsAdapter.isCorrectPassword(username, newPassword);
        assertThat(correctPassword).isFalse();
    }

    @Test
    void whenUpdatingThePassword_thenItShouldStoreTheExpectedUserDetails() {
        dbUserDetailsAdapter.updatePassword(username, newPassword);
        verify(entityManager).persist(userDetailsEntityArgumentCaptor.capture());
        UserDetailsEntity actualUserDetailsEntity = userDetailsEntityArgumentCaptor.getValue();
        assertThat(actualUserDetailsEntity.getUsername()).isEqualTo(username);
        assertThat(actualUserDetailsEntity.getPassword()).isEqualTo(newPassword);
    }

    interface Fixture {
        String username = "username";

        String oldPassword = "oldPassword";

        String newPassword = "newPassword";
    }
}