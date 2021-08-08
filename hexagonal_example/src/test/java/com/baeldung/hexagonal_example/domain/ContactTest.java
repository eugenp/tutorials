package com.baeldung.hexagonal_example.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ContactTest {

    private static final String EMAIL = "test@test.com";
    private static final String MOBILE_PHONE = "+1(651)784-8800";
    private static final String NAME = "James Santos";

    @Test
    void whenCreatingContactWithoutName_shouldThrowsDomainError() {
        assertThrows(
                DomainException.class,
                () ->  new Contact(null, EMAIL, MOBILE_PHONE),
                "The properties name, email and mobile phone are requited");
    }

    @Test
    void whenCreatingContactWithoutEmail_shouldThrowsDomainError() {
        assertThrows(
                DomainException.class,
                () ->  new Contact(NAME, null, MOBILE_PHONE),
                "The properties name, email and mobile phone are requited");
    }

    @Test
    void whenCreatingContactWithoutMobilePhone_shouldThrowsDomainError() {
        assertThrows(
                DomainException.class,
                () ->  new Contact(NAME, EMAIL, null),
                "The properties name, email and mobile phone are requited");
    }

    @Test
    void whenCreatingContactWithValidData_shouldBuildContact() {
        new Contact(NAME, EMAIL, MOBILE_PHONE);
    }

    @Test
    void whenCreatingContactWithInvalidMobilePhone_shouldThrowsDomainError() {
        assertThrows(
                DomainException.class,
                () ->  new Contact(NAME, EMAIL, "+1 456 777 55555"),
                "Invalid format, phone should be: +999(999)999-9999");
    }

    @Test
    void whenCreatingContactWithInvalidEmail_shouldThrowsDomainError() {
        assertThrows(
                DomainException.class,
                () ->  new Contact(NAME, "test1.test.com", MOBILE_PHONE),
                "Invalid email");
    }

    @Test
    void toDTO_shouldCopyAllValues() {
        Contact contact = new Contact(NAME, EMAIL, MOBILE_PHONE);

        ContactDTO dto = contact.toDTO();

        assertEquals(NAME, dto.getName());
        assertEquals(EMAIL, dto.getEmail());
        assertEquals(MOBILE_PHONE, dto.getMobilePhone());
    }
}