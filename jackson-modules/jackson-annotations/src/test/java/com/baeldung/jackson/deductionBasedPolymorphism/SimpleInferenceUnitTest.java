package com.baeldung.jackson.deductionBasedPolymorphism;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;

public class SimpleInferenceUnitTest extends JacksonDeductionBasedPolymorphismBaseUnitTest {

  private final ObjectMapper objectMapper = JsonMapper.builder().build();

  @Test
  void givenAKnight_whenMapping_thenExpectAKnightType() throws Exception {
    String knightJson = a2q("{'name':'Ostrava, of Boletaria', 'weapon':'Rune Sword'}");
    NamedCharacter knight = objectMapper.readValue(knightJson, NamedCharacter.class);
    assertTrue(knight instanceof Knight);
    assertSame(knight.getClass(), Knight.class);
    assertEquals("Ostrava, of Boletaria", knight.getName());
    assertEquals("Rune Sword", ((Knight) knight).getWeapon());
  }

  @Test
  void givenAKing_whenMapping_thenExpectAKingType() throws Exception {
    String kingJson = a2q("{'name':'Old King Allant', 'land':'Boletaria'}");
    NamedCharacter king = objectMapper.readValue(kingJson, NamedCharacter.class);
    assertTrue(king instanceof King);
    assertSame(king.getClass(), King.class);
    assertEquals("Old King Allant", king.getName());
    assertEquals("Boletaria", ((King) king).getLand());
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
