package com.baeldung.lombok;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;

public class AllArgsPersonUnitTest {
    @Test
    void whenUsingAllArgsConstructor_thenCheckNotNullFields() {
        assertThatThrownBy(() -> {
            new AllArgsPerson(10, "Asian", null);
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining("name is marked non-null but is null");
    }
}