package com.baeldung.hexagonalintro.adapters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.baeldung.hexagonalintro.adapters.MockUserDetailsAdapterTest.Fixture.otherPassword;
import static com.baeldung.hexagonalintro.adapters.MockUserDetailsAdapterTest.Fixture.password;
import static com.baeldung.hexagonalintro.adapters.MockUserDetailsAdapterTest.Fixture.username;
import static org.assertj.core.api.Assertions.assertThat;

class MockUserDetailsAdapterTest {

    private MockUserDetailsAdapter mockUserDetailsAdapter;

    @BeforeEach
    void setUp() {
        mockUserDetailsAdapter = new MockUserDetailsAdapter();
    }

    @Test
    void whenThePasswordsMatch_thenItShouldReturnTrue() {
        mockUserDetailsAdapter.updatePassword(username, password);
        boolean correctPassword = mockUserDetailsAdapter.isCorrectPassword(username, password);
        assertThat(correctPassword).isTrue();
    }

    @Test
    void whenThePasswordsDoNotMatch_thenItShouldReturnFalse() {
        mockUserDetailsAdapter.updatePassword(username, password);
        boolean correctPassword = mockUserDetailsAdapter.isCorrectPassword(username, otherPassword);
        assertThat(correctPassword).isFalse();
    }

    @Test
    void givenNoUsersExist_thenItShouldReturnFalse() {
        boolean correctPassword = mockUserDetailsAdapter.isCorrectPassword(username, password);
        assertThat(correctPassword).isFalse();
    }

    interface Fixture {
        String username = "username";

        String password = "password";

        String otherPassword = "otherPassword";
    }
}