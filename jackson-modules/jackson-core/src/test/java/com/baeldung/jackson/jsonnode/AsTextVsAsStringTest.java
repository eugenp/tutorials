package com.baeldung.jackson.jsonnode;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

class AsTextVsAsStringUnitTest {
    @Test
    void shouldUseAsText() throws JsonProcessingException {
        String json = "{\"name\":\"John\",\"age\":30}";
        JsonNode node = new ObjectMapper().readTree(json);

        String name = node.get("name")
          .asText();
        String age = node.get("age")
          .asText();
        String jsonText = node.asText();
        assertThat(jsonText).isEmpty();
        assertThat(name).isEqualTo("John");
        assertThat(age).isEqualTo("30");
    }

    @Test
    void shouldUseAsTextWithEscapeCharacters() throws JsonProcessingException {
        String specialCharsJson = "{\"text\":\"Hello \\\"world\\\" !\"}";
        JsonNode specialCharsNode = new ObjectMapper().readTree(specialCharsJson);
        String specialCharsJsonAsText = specialCharsNode.get("text")
          .asText();
        String specialCharsJsonToString = specialCharsNode.get("text")
          .toString();
        assertThat(specialCharsJsonAsText).isEqualTo("Hello \"world\" !");
        assertThat(specialCharsJsonToString).isEqualTo("\"Hello \\\"world\\\" !\"");
    }

    @Test
    void shouldUseToString() throws JsonProcessingException {
        String json = "{\"name\":\"John\",\"age\":30}";
        JsonNode node = new ObjectMapper().readTree(json);

        String jsonString = node.toString();
        String name = node.get("name")
          .toString();
        String age = node.get("age")
          .toString();
        assertThat(jsonString).isEqualTo("{\"name\":\"John\",\"age\":30}");
        assertThat(name).isEqualTo("\"John\"");
        assertThat(age).isEqualTo("30");
    }
}