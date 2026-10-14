package com.baeldung.jackson.whatsnew;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class Java8FeaturesTest {

    @Test
    void givenJava8TypesAndNoConfiguration_whenWriteValueAsString_thenSuccess() {
        var now = LocalDateTime.now();
        var event = new Event("Tech Talk", Optional.of("Jackson 3 overview"), now);
        var mapper = MapperFactory.getMapper();

        var json = mapper.writeValueAsString(event);
        assertThat(json)
            .isEqualTo("""
                {
                  "title" : "Tech Talk",
                  "description" : "Jackson 3 overview",
                  "eventDate" : "<now>"
                }""".replace("<now>", now.toString()));
        var deserialized = mapper.readValue(json, Event.class);
        assertThat(deserialized)
            .isEqualTo(event);
    }

    private record Event(String title, Optional<String> description, LocalDateTime eventDate) {}
}
