package com.baeldung.unnamed.variables;

import static com.baeldung.unnamed.variables.UnnamedVariables.countCarsOverLimitWithNamedVariable;
import static com.baeldung.unnamed.variables.UnnamedVariables.countCarsOverLimitWithUnnamedVariable;
import static com.baeldung.unnamed.variables.UnnamedVariables.getCarsByFirstLetterWithNamedVariables;
import static com.baeldung.unnamed.variables.UnnamedVariables.getCarsByFirstLetterWithUnnamedVariables;
import static com.baeldung.unnamed.variables.UnnamedVariables.handleCarExceptionWithNamedVariables;
import static com.baeldung.unnamed.variables.UnnamedVariables.handleCarExceptionWithUnnamedVariables;
import static com.baeldung.unnamed.variables.UnnamedVariables.obtainTransactionAndUpdateCarWithNamedVariables;
import static com.baeldung.unnamed.variables.UnnamedVariables.obtainTransactionAndUpdateCarWithUnnamedVariables;
import static com.baeldung.unnamed.variables.UnnamedVariables.removeThreeCarsAndReturnFirstRemovedWithNamedVariables;
import static com.baeldung.unnamed.variables.UnnamedVariables.removeThreeCarsAndReturnFirstRemovedWithUnnamedVariables;
import static com.baeldung.unnamed.variables.UnnamedVariables.sendNotificationToCarsWithNamedVariable;
import static com.baeldung.unnamed.variables.UnnamedVariables.sendNotificationToCarsWithUnnamedVariable;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;

import org.junit.jupiter.api.Test;

public class UnnamedVariablesUnitTest extends CarScenario {

    @Test
    public void whenCountingCarsOverLimitWithNamedVariables_thenShouldReturnOne() {
        assertEquals(1, countCarsOverLimitWithNamedVariable(cars, 2));
    }

    @Test
    public void whenCountingCarsOverLimitWithUnnamedVariables_thenShouldReturnOne() {
        assertEquals(1, countCarsOverLimitWithUnnamedVariable(cars, 2));
    }

    @Test
    public void whenNotifyingCarsWithNamedVariables_thenShouldNotFail() {
        assertDoesNotThrow(() -> sendNotificationToCarsWithNamedVariable(cars));
    }

    @Test
    public void whenNotifyingCarsWithUnnamedVariables_thenShouldNotFail() {
        assertDoesNotThrow(() -> sendNotificationToCarsWithUnnamedVariable(cars));
    }

    @Test
    public void whenPollingCarsWithNamedVariables_thenReturnFirstOneAndEmptyQueue() {
        var carQueue = new LinkedList<>(cars);
        assertEquals("Mitsubishi", removeThreeCarsAndReturnFirstRemovedWithNamedVariables(carQueue).name());
        assertEquals(0, carQueue.size());
    }

    @Test
    public void whenPollingCarsWithUnnamedVariables_thenReturnFirstOneAndEmptyQueue() {
        var carQueue = new LinkedList<>(cars);
        assertEquals("Mitsubishi", removeThreeCarsAndReturnFirstRemovedWithUnnamedVariables(carQueue).name());
        assertEquals(0, carQueue.size());
    }

    @Test
    public void whenHandlingExceptionWithNamedVariables_thenNoExceptionIsThrown() {
        assertDoesNotThrow(() -> handleCarExceptionWithNamedVariables(cars.get(0)));
    }

    @Test
    public void whenHandlingExceptionWithUnnamedVariables_thenNoExceptionIsThrown() {
        assertDoesNotThrow(() -> handleCarExceptionWithUnnamedVariables(cars.get(0)));
    }

    @Test
    public void whenHandlingTransactionUpdateWithNamedVariables_thenNoExceptionIsThrown() {
        assertDoesNotThrow(() -> obtainTransactionAndUpdateCarWithNamedVariables(cars.get(0)));
    }

    @Test
    public void whenHandlingTransactionUpdateWithUnnamedVariables_thenNoExceptionIsThrown() {
        assertDoesNotThrow(() -> obtainTransactionAndUpdateCarWithUnnamedVariables(cars.get(0)));
    }

    @Test
    public void whenGettingCarsByFirstLetterWithNamedVariables_thenHaveThreeKeys() {
        var carsByLetter = getCarsByFirstLetterWithNamedVariables(cars);
        assertEquals(1, carsByLetter.get("M").size());
        assertEquals(1, carsByLetter.get("T").size());
        assertEquals(1, carsByLetter.get("J").size());
    }

    @Test
    public void whenGettingCarsByFirstLetterWithUnnamedVariables_thenHaveThreeKeys() {
        var carsByLetter = getCarsByFirstLetterWithUnnamedVariables(cars);
        assertEquals(1, carsByLetter.get("M").size());
        assertEquals(1, carsByLetter.get("T").size());
        assertEquals(1, carsByLetter.get("J").size());
    }
}
