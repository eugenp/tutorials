package com.baeldung.lombok;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

public class SpecialPersonUnitTest {
    @Test
    void whenUsingRequiredArgsConstructorWithStaticName_thenHideTheConstructor() {
        SpecialPerson person = SpecialPerson.construct("value1", "value2");
        assertNotNull(person.getName());
    }
}