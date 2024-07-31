package com.baeldung.avro;

import avro.User;
import custom.avro.Pet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AvroCodeGenerationUnitTest {

    @Test
    void givenUserData_whenJavaClassGeneratedWithPlugin_thenDataShouldMatch() {
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

    @Test
    void givenUserData_whenJavaClassGeneratedWithTask_thenDataShouldMatch() {
        final String petId = "123";
        final String name = "Fluffy";
        final String species = "Cat";
        final int age = 3;

        Pet pet = Pet.newBuilder()
                .setPetId(petId)
                .setName(name)
                .setSpecies(species)
                .setAge(age)
                .build();

        assertEquals(petId, pet.getPetId());
        assertEquals(name, pet.getName());
        assertEquals(species, pet.getSpecies());
        assertEquals(age, pet.getAge());
    }
}
