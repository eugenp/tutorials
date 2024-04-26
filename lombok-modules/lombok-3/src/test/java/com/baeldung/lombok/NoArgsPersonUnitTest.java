package com.baeldung.lombok;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

public class NoArgsPersonUnitTest {
    @Test
    void whenUsingNoArgsConstructor_thenAddDefaultValuesToUnInitializedFinalFields() {
        NoArgsPerson person = new NoArgsPerson();
        assertNull(person.getRace());
        assertEquals("unknown", person.getNickname());
    }
}