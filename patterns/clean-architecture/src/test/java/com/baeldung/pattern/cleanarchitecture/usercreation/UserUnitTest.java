package com.baeldung.pattern.cleanarchitecture.usercreation;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class UserUnitTest {

    @Test
    void given123Password_whenPasswordIsNotValid_thenIsFalse() {
        User user = new CommonUser("Baeldung", "123");

        assertThat(user.passwordIsValid()).isFalse();
    }
}
