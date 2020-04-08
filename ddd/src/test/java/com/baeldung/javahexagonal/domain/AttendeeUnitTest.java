package com.baeldung.javahexagonal.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class AttendeeUnitTest {

    @Test
    void name_must_not_be_null() {
        assertThatThrownBy(() -> new Attendee(null, "you@example.org")).hasMessage("name must not be null");
    }

    @Test
    void mailAddress_must_not_be_null() {
        assertThatThrownBy(() -> new Attendee("John Doe", null)).hasMessage("mailAddress must not be null");
    }

    @Test
    void mailAddress_must_contain_at() {
        assertThatThrownBy(() -> new Attendee("John Doe", "hello")).hasMessage("mailAddress is not valid");
    }

    @Test
    void allows_valid_name_and_valid_mailAddress() {
        assertThatCode(() -> new Attendee("John Doe", "you@example.org")).doesNotThrowAnyException();
    }
}
