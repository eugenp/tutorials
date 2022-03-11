package com.baeldung.jackson.booleanAsInt;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BooleanAsIntegerUnitTest {

    private ObjectMapper mapper;

    @BeforeEach
    public void setup() {
        mapper = new ObjectMapper();
    }

    @Test
    public void givenBoolean_serializedAsInteger() throws Exception {
        GameAnnotatedByJsonFormat
          game = new GameAnnotatedByJsonFormat(1L, "My Game");
        game.setPaused(true);
        game.setOver(false);
        String json = mapper.writeValueAsString(game);

        assertThat(json)
          .isEqualTo("{\"id\":1,\"name\":\"My Game\",\"paused\":1,\"over\":0}");
    }

    @Test
    public void givenInteger_deserializedAsBooleanByDefault() throws Exception {
        // Integer "1" and "0" values deserialized correctly out of the box.
        // No configuration or @JsonFormat annotation needed.
        String json = "{\"id\":1,\"name\":\"My Game\",\"paused\":1,\"over\":0}";
        Game game = mapper.readValue(json, Game.class);

        assertThat(game.isPaused()).isEqualTo(true);
        assertThat(game.isOver()).isEqualTo(false);
    }

    @Test
    public void givenBoolean_serializedAsIntegerGlobally() throws Exception {
        // global configuration override for the type Boolean
        mapper.configOverride(Boolean.class)
          .setFormat(JsonFormat.Value.forShape(Shape.NUMBER));

        Game game = new Game(1L, "My Game");
        game.setPaused(true);
        game.setOver(false);
        String json = mapper.writeValueAsString(game);

        assertThat(json)
          .isEqualTo("{\"id\":1,\"name\":\"My Game\",\"paused\":1,\"over\":0}");
    }

    @Test
    public void givenBooleanWithCustomSerializer_serializedAsNumericString() throws Exception {
        GameAnnotatedByJsonSerializeDeserialize
          game = new GameAnnotatedByJsonSerializeDeserialize(1L, "My Game");
        game.setPaused(true);
        game.setOver(false);
        String json = mapper.writeValueAsString(game);

        assertThat(json)
          .isEqualTo("{\"id\":1,\"name\":\"My Game\",\"paused\":\"1\",\"over\":\"0\"}");
    }

    @Test
    public void givenNumericStringWithCustomDeserializer_deserializedAsBoolean() throws Exception {
        String json = "{\"id\":1,\"name\":\"My Game\",\"paused\":\"1\",\"over\":\"0\"}";
        GameAnnotatedByJsonSerializeDeserialize
          game = mapper.readValue(json, GameAnnotatedByJsonSerializeDeserialize.class);

        assertThat(game.isPaused()).isEqualTo(true);
        assertThat(game.isOver()).isEqualTo(false);
    }

    @Test
    public void givenBooleanWithCustomSerializer_serializedAsNumericStringGlobally() throws Exception {
        // setting serializers globally
        SimpleModule module = new SimpleModule();
        module.addSerializer(Boolean.class, new NumericBooleanSerializer());
        mapper.registerModule(module);

        Game game = new Game(1L, "My Game");
        game.setPaused(true);
        game.setOver(false);
        String json = mapper.writeValueAsString(game);

        assertThat(json)
          .isEqualTo("{\"id\":1,\"name\":\"My Game\",\"paused\":\"1\",\"over\":\"0\"}");
    }

    @Test
    public void givenNumericStringWithCustomDeserializer_deserializedAsBooleanGlobally() throws Exception {
        // setting deserializers globally
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Boolean.class, new NumericBooleanDeserializer());
        mapper.registerModule(module);

        String json = "{\"id\":1,\"name\":\"My Game\",\"paused\":\"1\",\"over\":\"0\"}";
        Game game = mapper.readValue(json, Game.class);

        assertThat(game.isPaused()).isEqualTo(true);
        assertThat(game.isOver()).isEqualTo(false);
    }

    @Test
    public void givenInvalidStringWithCustomDeserializer_throwsInvalidFormatException() {
        // another number other than "1" or "0"
        String json = "{\"id\":1,\"name\":\"My Game\",\"paused\":\"5\"}";
        InvalidFormatException e = Assertions.assertThrows(
          InvalidFormatException.class, () -> mapper.readValue(json, GameAnnotatedByJsonSerializeDeserialize.class)
        );

        assertThat(e.getValue()).isEqualTo("5");

        // non-numeric string
        String json2 = "{\"id\":1,\"name\":\"My Game\",\"paused\":\"xxx\"}";
        InvalidFormatException e2 = Assertions.assertThrows(
          InvalidFormatException.class, () -> mapper.readValue(json2, GameAnnotatedByJsonSerializeDeserialize.class)
        );

        assertThat(e2.getValue()).isEqualTo("xxx");
    }

}
