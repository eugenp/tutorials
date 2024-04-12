package com.baeldung.unnamed.variables;

import static com.baeldung.unnamed.variables.UnnamedPatterns.getEngineTypeWithNamedPattern;
import static com.baeldung.unnamed.variables.UnnamedPatterns.getEngineTypeWithUnnamedPattern;
import static com.baeldung.unnamed.variables.UnnamedPatterns.getObjectsColorWithNamedPattern;
import static com.baeldung.unnamed.variables.UnnamedPatterns.getObjectsColorWithSwitchAndNamedPattern;
import static com.baeldung.unnamed.variables.UnnamedPatterns.getObjectsColorWithSwitchAndUnnamedPattern;
import static com.baeldung.unnamed.variables.UnnamedPatterns.getObjectsColorWithUnnamedPattern;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UnnamedPatternsUnitTest extends CarScenario {

    @Test
    public void whenExtractingColorWithNamedPatterns_thenReturnBlue() {
        assertEquals("blue", getObjectsColorWithNamedPattern(cars.get(0)));
    }

    @Test
    public void whenExtractingColorWithUnnamedPatterns_thenReturnBlue() {
        assertEquals("blue", getObjectsColorWithUnnamedPattern(cars.get(0)));
    }

    @Test
    public void whenExtractingColorWithSwitchAndNamedPatterns_thenReturnBlue() {
        assertEquals("blue", getObjectsColorWithSwitchAndNamedPattern(cars.get(0)));
    }

    @Test
    public void whenExtractingColorWithSwitchAndUnnamedPatterns_thenReturnBlue() {
        assertEquals("blue", getObjectsColorWithSwitchAndUnnamedPattern(cars.get(0)));
    }

    @Test
    public void whenExtractingEngineTypeWithNamedPatterns_thenReturnGas() {
        assertEquals("gas", getEngineTypeWithNamedPattern(cars.get(0)));
    }

    @Test
    public void whenExtractingEngineTypeWithUnnamedPatterns_thenReturnGas() {
        assertEquals("gas", getEngineTypeWithUnnamedPattern(cars.get(0)));
    }
}
