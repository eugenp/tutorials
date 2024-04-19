package com.baeldung.lombok;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SpecialPersonUnitTest {
    @Test
    public void usingRequiredArgsConstructorWithStaticName_shouldHideTheConstructor() {
        SpecialPerson person = SpecialPerson.construct("value1", "value2");
        Assertions.assertNotNull(person);
    }
}