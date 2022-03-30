package com.baeldung.jackson.deductionbasedpolymorphism;

import static com.baeldung.jackson.deductionbasedpolymorphism.JsonStringFormatterUtil.formatJson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import com.fasterxml.jackson.databind.json.JsonMapper;

public class SimpleInferenceWithNameDeductionUnitTest {
    private final ObjectMapper objectMapper = JsonMapper.builder()
        .build();

    @Test
    void givenAKing_whenMapping_thenExpectAKingType() throws Exception {
        String kingJson = formatJson("{'land':'Boletaria', '@type':'KingV1'}");

        CharacterV1 character = objectMapper.readValue(kingJson, CharacterV1.class);
        assertTrue(character instanceof KingV1);
        assertSame(character.getClass(), KingV1.class);
        KingV1 king = (KingV1) character;
        assertEquals("Boletaria", king.getLand());
    }

    @Test
    void givenAKingWithoutType_whenMapping_thenExpectAnError() {
        String kingJson = formatJson("{'land':'Boletaria'}");
        assertThrows(InvalidTypeIdException.class, () -> objectMapper.readValue(kingJson, CharacterV1.class));
    }

}
