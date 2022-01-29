package com.baeldung.jackson.deduction_based_polymorphism;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static com.baeldung.jackson.deduction_based_polymorphism.JsonStringFormatterUtil.formatJson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CaseInsensitiveInferenceUnitTest {

    private final ObjectMapper objectMapper = JsonMapper.builder().configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true).build();

    @Test
    void givenAnCaseInsensitiveKnight_whenMapping_thenExpectKnight() throws Exception {
        String knightJson = formatJson("{'NaMe':'Ostrava, of Boletaria', 'WeaPON':'Rune Sword'}");
        NamedCharacter knight = objectMapper.readValue(knightJson, NamedCharacter.class);
        assertTrue(knight instanceof Knight);
        assertSame(knight.getClass(), Knight.class);
        assertEquals("Ostrava, of Boletaria", knight.getName());
        assertEquals("Rune Sword", ((Knight) knight).getWeapon());
    }

}
