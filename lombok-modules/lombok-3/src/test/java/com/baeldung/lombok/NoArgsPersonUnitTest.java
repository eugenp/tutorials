package com.baeldung.lombok;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

public class NoArgsPersonUnitTest {
    @Test
    public void whenUsingNoArgsConstructor_itAddsDefaultValuesToUnInitializedFinalFields() {
        NoArgsPerson person = new NoArgsPerson();
        assertNull(person.getRace());
        assertEquals("unknown", person.getNickname());
    }
}