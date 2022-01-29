package com.baeldung.jackson.deduction_based_polymorphism;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static com.baeldung.jackson.deduction_based_polymorphism.JsonStringFormatterUtil.formatJson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContainedInferenceUnitTest {

    private final ObjectMapper objectMapper = JsonMapper.builder().build();

    @Test
    void givenAKnightControlledCharacter_whenMapping_thenExpectAControlledCharacterWithKnight() throws Exception {
        String controlledCharacterJson = formatJson("{'character': {'name': 'Ostrava, of Boletaria', 'weapon': 'Rune Sword'}}");
        ControlledCharacter controlledCharacter = objectMapper.readValue(controlledCharacterJson, ControlledCharacter.class);
        Character character = controlledCharacter.getCharacter();
        assertTrue(character instanceof Knight);
        assertSame(character.getClass(), Knight.class);
        assertEquals("Ostrava, of Boletaria", ((NamedCharacter) character).getName());
        assertEquals("Rune Sword", ((Knight) character).getWeapon());
    }

    @Test
    void givenAKingControlledCharacter_whenMapping_thenExpectAControlledCharacterWithKing() throws Exception {
        String controlledCharacterJson = formatJson("{'character': {'name': 'King Allant', 'land': 'Boletaria'}}");
        ControlledCharacter controlledCharacter = objectMapper.readValue(controlledCharacterJson, ControlledCharacter.class);
        Character character = controlledCharacter.getCharacter();
        assertTrue(character instanceof King);
        assertSame(character.getClass(), King.class);
        assertEquals("King Allant", ((NamedCharacter) character).getName());
        assertEquals("Boletaria", ((King) character).getLand());
    }

    @Test
    void givenAnEmptySubtype_whenMapping_thenExpectImperialSpy() throws Exception {
        String controlledCharacterJson = formatJson("{'character': {}}");
        ControlledCharacter controlledCharacter = objectMapper.readValue(controlledCharacterJson, ControlledCharacter.class);
        assertTrue(controlledCharacter.getCharacter() instanceof ImperialSpy);
    }

    @Test
    void givenANullCharacter_whenMapping_thenExpectNullCharacter() throws Exception {
        String controlledCharacterJson = formatJson("{'character': null}");
        ControlledCharacter controlledCharacter = objectMapper.readValue(controlledCharacterJson, ControlledCharacter.class);
        assertNull(controlledCharacter.getCharacter());
    }

    @Test
    void givenAAnAbsentCharacter_whenMapping_thenExpectNullCharacter() throws Exception {
        String controlledCharacterJson = formatJson("{}");
        ControlledCharacter controlledCharacter = objectMapper.readValue(controlledCharacterJson, ControlledCharacter.class);
        assertNull(controlledCharacter.getCharacter());
    }
}
