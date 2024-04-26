package com.baeldung.lombok;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class RequiredArgsPersonUnitTest {
    @Test
    void whenUsingRequiredArgsConstructor_thenInitializedFinalFieldsWillBeIgnored() {
        RequiredArgsPerson person = new RequiredArgsPerson("Hispanic", "Isabela");
        assertEquals("unknown", person.getNickname());
    }

    @Test
    void whenUsingRequiredArgsConstructor_thenCheckNotNullFields() {
        assertThatThrownBy(() -> {
            new RequiredArgsPerson("Hispanic", null);
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining("name is marked non-null but is null");
    }
}