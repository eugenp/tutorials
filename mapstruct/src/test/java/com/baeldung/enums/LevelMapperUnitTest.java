package com.baeldung.enums;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LevelMapperUnitTest {

    LevelMapper levelMapper = Mappers.getMapper(LevelMapper.class);

    @Test
    void givenHighInputLevel_WhenInputLevelToOutputLevel_ThenHighOutputLevel() {
        assertEquals(OutputLevel.HIGH, levelMapper.inputLevelToOutputLevel(InputLevel.HIGH));
    }

    @Test
    void givenMediumInputLevel_WhenInputLevelToOutputLevel_ThenThrows() {
        assertThrows(IllegalArgumentException.class, () -> levelMapper.inputLevelToOutputLevel(InputLevel.MEDIUM));
    }

    @Test
    void givenLowInputLevel_WhenInputLevelToOutputLevel_ThenLowOutputLevel() {
        assertEquals(OutputLevel.LOW, levelMapper.inputLevelToOutputLevel(InputLevel.LOW));
    }

}
