package com.baeldung.construction;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

class CoffeeMachineUnitTest {

    @Test
    void givenNoMockedContructor_whenCoffeeMade_thenRealDependencyReturned() {
        CoffeeMachine machine = new CoffeeMachine();
        assertEquals("Finished making a delicious Espresso made with Guatemalan beans", machine.makeCoffee());
    }

    @Test
    void givenMockedContructor_whenCoffeeMade_thenMockDependencyReturned() {
        try (MockedConstruction<WaterTank> mockTank = mockConstruction(WaterTank.class); MockedConstruction<Grinder> mockGrinder = mockConstruction(Grinder.class)) {

            CoffeeMachine machine = new CoffeeMachine();

            WaterTank tank = mockTank.constructed()
                .get(0);
            Grinder grinder = mockGrinder.constructed()
                .get(0);

            when(tank.isEspresso()).thenReturn(false);
            when(grinder.getBeans()).thenReturn("Peruvian");

            assertEquals("Finished making a delicious Americano made with Peruvian beans", machine.makeCoffee());
        }

    }

    @Test
    void givenMockedContructorWithArgument_whenCoffeeMade_thenMockDependencyReturned() {
        try (MockedConstruction<WaterTank> mockTank = mockConstruction(WaterTank.class, (mock, context) -> {
                int mils = (int) context.arguments().get(0);
                when(mock.getMils()).thenReturn(mils);         
            }); 
            MockedConstruction<Grinder> mockGrinder = mockConstruction(Grinder.class)) {

            CoffeeMachine machine = new CoffeeMachine(100);

            Grinder grinder = mockGrinder.constructed()
                .get(0);

            when(grinder.getBeans()).thenReturn("Kenyan");
            assertEquals("Finished making a delicious Americano made with Kenyan beans", machine.makeCoffee());
        }

    }

}
