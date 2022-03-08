package com.baeldung.jackson.deductionbasedpolymorphism;

import static com.baeldung.jackson.deductionbasedpolymorphism.JsonStringFormatterUtil.formatJson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

class SimpleInferenceUnitTest {

    private final ObjectMapper objectMapper = JsonMapper.builder()
        .build();

    @Test
    void givenAKnight_whenMapping_thenExpectAKnightType() throws Exception {
        String knightJson = formatJson("{'name':'Ostrava, of Boletaria', 'weapon':'Rune Sword'}");

        Character character = objectMapper.readValue(knightJson, Character.class);

        assertTrue(character instanceof Knight);
        assertSame(character.getClass(), Knight.class);
        Knight king = (Knight) character;
        assertEquals("Ostrava, of Boletaria", king.getName());
        assertEquals("Rune Sword", king.getWeapon());
    }

    @Test
    void givenAKing_whenMapping_thenExpectAKingType() throws Exception {
        String kingJson = formatJson("{'name':'Old King Allant', 'land':'Boletaria'}");

        Character character = objectMapper.readValue(kingJson, Character.class);

        assertTrue(character instanceof King);
        assertSame(character.getClass(), King.class);
        King king = (King) character;
        assertEquals("Old King Allant", king.getName());
        assertEquals("Boletaria", king.getLand());
    }

    @Test
    void givenAnEmptyObject_whenMapping_thenExpectAnImperialSpy() throws Exception {
        String imperialSpyJson = "{}";

        Character character = objectMapper.readValue(imperialSpyJson, Character.class);

        assertTrue(character instanceof ImperialSpy);
    }

    @Test
    void givenANullObject_whenMapping_thenExpectANullObject() throws Exception {
        Character character = objectMapper.readValue("null", Character.class);

        assertNull(character);
    }

}
