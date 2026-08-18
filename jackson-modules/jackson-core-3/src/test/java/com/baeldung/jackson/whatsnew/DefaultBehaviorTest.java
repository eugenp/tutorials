package com.baeldung.jackson.whatsnew;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultBehaviorTest {

    @Test
    void givenJsonWithAdditionalField_whenReadValue_thenSuccessWithoutUnknownField() {
        var json = "{\"name\":\"Alice\", \"unknownField\":\"ignored_value\"}";
        var mapper = MapperFactory.getMapper();

        var user = mapper.readValue(json, User.class);

        assertThat(user)
            .returns("Alice", User::name);
    }

    private record User(String name) {}
}
