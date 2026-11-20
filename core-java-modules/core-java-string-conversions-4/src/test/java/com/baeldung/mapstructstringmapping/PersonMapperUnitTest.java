package com.baeldung.mapstructstringmapping;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PersonMapperUnitTest {

    @Test
    void givenPerson_whenMapsToPersonDTO_thenFieldsAreCorrect() {

        Person person = new Person();
        person.setName("Alice");
        person.setAge(30);

        PersonDTO dto = PersonMapper.INSTANCE.toDTO(person);

        assertEquals("Alice", dto.getName());
        assertEquals("30", dto.getAge());
    }

    @Test
    void givenNullName_whenMapped_thenDefaultIsUsed() {

        Person person = new Person();
        person.setAge(25);

        PersonDTO dto = PersonMapper.INSTANCE.toDTO(person);

        assertEquals("Unknown", dto.getName());
        assertEquals("25", dto.getAge());
    }
}
