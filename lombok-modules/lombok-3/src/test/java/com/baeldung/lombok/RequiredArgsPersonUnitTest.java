package com.baeldung.lombok;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class RequiredArgsPersonUnitTest {
    @Test
    public void whenUsingRequiredArgsConstructor_initializedFinalFieldsWillBeIgnored() {
        RequiredArgsPerson person = new RequiredArgsPerson("Hispanic", "Isabela");
        assertEquals("unknown", person.getNickname());
    }

    @Test
    public void whenUsingRequiredArgsConstructor_itShouldCheckNotNullFields() {
        assertThatThrownBy(() -> {
            new RequiredArgsPerson("Hispanic", null);
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining("name is marked non-null but is null");
    }
}