package com.baeldung.jackson.whatsnew;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UncheckedExceptionTest {

    @Test
    void givenMapperAndUserRecord_whenReadValue_thenSuccessNoTryCatchNeeded() {
        var jsonPayloads = List.of("{\"name\":\"Matt\"}", "{\"name\":\"Jack\"}");
        var mapper = MapperFactory.getMapper();

        var users = jsonPayloads.stream()
            .map(json -> mapper.readValue(json, User.class))
            .toList();

        assertThat(users)
            .hasSize(2)
            .containsExactlyInAnyOrder(new User("Matt"), new User("Jack"));
    }

    private record User(String name) {}
}
