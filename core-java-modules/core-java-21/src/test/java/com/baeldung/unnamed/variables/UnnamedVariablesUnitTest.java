package com.baeldung.unnamed.variables;

import static com.baeldung.unnamed.variables.UnnamedVariables.countCarsOverLimitWithNamedVariable;
import static com.baeldung.unnamed.variables.UnnamedVariables.countCarsOverLimitWithUnnamedVariable;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class UnnamedVariablesUnitTest {

    private final List<Car> cars = List.of(
        new Car("Mitsubishi"),
        new Car("Toyota"),
        new Car("Jaguar")
    );

    @Test
    public void whenCountingCarsOverLimitWithNamedVariables_thenShouldReturnOne() {
        assertEquals(1, countCarsOverLimitWithNamedVariable(cars, 2));
    }

    @Test
    public void whenCountingCarsOverLimitWithUnNamedVariables_thenShouldReturnOne() {
        assertEquals(1, countCarsOverLimitWithUnnamedVariable(cars, 2));
    }
}
