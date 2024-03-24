package com.baeldung.avro;

import com.example.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AvroCodeGenerationUnitTest {

    @Test
    void givenUserData_whenObjectCreated_thenDataShouldMatch() {
        final String firstName = "John";
        final String lastName = "Doe";
        final String phoneNumber = "+380659443235";

        User user = User.newBuilder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setPhoneNumber(phoneNumber)
                .build();

        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(phoneNumber, user.getPhoneNumber());
    }
}
