package com.baeldung.mapstructstringmapping;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PersonMapperUnitTest {

    @Test
    void givenPerson_whenMapsToPersonDTO_thenFieldsAreCorrect() {

        Person person = new Person();
        person.setName("Alice");
        person.setAge(30);

        PersonDTO dto = PersonMapper.INSTANCE.toDTO(person);

        assertEquals("Alice", dto.getName());
        assertEquals("30", dto.getAge());
    }
}
